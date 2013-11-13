package ch.furthermore.demo.st;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.PostConstruct;

import ch.furthermore.demo.st.rest.model.Donor;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataAccess {
	@Autowired
	private DynamoServiceFactory dynamoGeoServiceFactory;
	private DynamoGeoService geoService;

    private  String doantionDataTableName ="DonationData" ;
	
	@PostConstruct
	public void init() {
		String tableName = "geocodedResults5"; //FIXME System.getProperty("PARAM2", "geocodedResults");
		geoService = dynamoGeoServiceFactory.createDynamoGeoService(tableName);
	}
	
	public void insertgetOpportunity(GeocodedResults r) {
		PointData pd = new PointData();
		pd.setRangeKey(r.getRangeKey());
		pd.setLatitude(r.getLatitude());
		pd.setLongitude(r.getLongitude());
		pd.withKeyValue("needTitle", r.getNeedTitle());
		pd.withKeyValue("description", r.getDescription());
		pd.withKeyValue("agencyIdBit", "" + r.getAgencyIdBit());
		pd.withKeyValue("agencyName", r.getAgencyName());
		pd.withKeyValue("category", r.getCategory());
		pd.withKeyValue("agencyAddress", r.getAgencyAddress());
		pd.withKeyValue("agencyCity", r.getAgencyCity());
		pd.withKeyValue("agencyState", r.getAgencyState());
		pd.withKeyValue("agencyZip", "" + r.getAgencyZip());
		pd.withKeyValue("phone", r.getPhone());
		pd.withKeyValue("email", r.getEmail());
		
		//System.out.println(r.getLatitude() + "/" + r.getLongitude()); //TODO remove DEBUG code
		
		geoService.putPoint(pd);
	}
	
	public Collection<GeocodedResults> getOpportunities( float lat, float longitude, float radius ) {
		Collection<GeocodedResults> results = new LinkedList<GeocodedResults>();
		for (PointData pd : geoService.getPointsWithinRadius(lat,  longitude, radius,
				"needTitle",
				"description",
				"agencyIdBit",
				"agencyName",
				"category",
				"agencyAddress",
				"agencyCity",
				"agencyState",
				"agencyZip",
				"phone",
				"email")) 
		{
			GeocodedResults r = new GeocodedResults();
			r.setRangeKey(pd.getRangeKey());
			r.setLatitude(pd.getLatitude());
			r.setLongitude(pd.getLongitude());
			
			r.setNeedTitle(pd.getData().get("needTitle"));
			r.setDescription(pd.getData().get("description"));
			r.setAgencyIdBit(Long.parseLong(pd.getData().get("agencyIdBit")));
			r.setAgencyName(pd.getData().get("agencyName"));
			r.setCategory(pd.getData().get("category"));
			r.setAgencyAddress(pd.getData().get("agencyAddress"));
			r.setAgencyCity(pd.getData().get("agencyCity"));
			r.setAgencyState(pd.getData().get("agencyState"));
			r.setAgencyZip(Integer.parseInt(pd.getData().get("agencyZip")));
			r.setPhone(pd.getData().get("phone"));
			r.setEmail(pd.getData().get("email"));
			
			results.add(r);
		}
		return results; 
	}

    protected void CreateDonationDataTable (AmazonDynamoDBClient client) {

        ArrayList<AttributeDefinition> attributeDefinitions= new ArrayList<AttributeDefinition>();
        attributeDefinitions.add(new AttributeDefinition().withAttributeName("DonorId").withAttributeType("N"));
        attributeDefinitions.add(new AttributeDefinition().withAttributeName("Category").withAttributeType("S"));

        ArrayList<KeySchemaElement> ks = new ArrayList<KeySchemaElement>();
        ks.add(new KeySchemaElement().withAttributeName("DonorId").withKeyType(KeyType.HASH));
        ks.add(new KeySchemaElement().withAttributeName("Category").withKeyType(KeyType.RANGE));

        ProvisionedThroughput provisionedThroughput = new ProvisionedThroughput()
                .withReadCapacityUnits(10L)
                .withWriteCapacityUnits(10L);

        CreateTableRequest request = new CreateTableRequest()
                .withTableName(doantionDataTableName)
                .withAttributeDefinitions(attributeDefinitions)
                .withKeySchema(ks)
                .withProvisionedThroughput(provisionedThroughput);

        CreateTableResult result = client.createTable(request);

        //wait until the table is Active
        try {
        DescribeTableResult describeTableResult = client.describeTable(new DescribeTableRequest(doantionDataTableName));
        while (describeTableResult.getTable().getTableStatus()!="ACTIVE")
        {
            Thread.sleep(500);
        }
        }
        catch (Exception e)
        {
            throw new RuntimeException();
        }

    }

    public  void importDonationData () {


        DynamoServiceFactory dsf = new DynamoServiceFactory();
        AmazonDynamoDBClient client = dsf.createDynamoClient();

        try {
            //TODO: it could be tat we get here while tble is creating (i.e. not active), this code will not work!!!!!
            DescribeTableResult describeTableResult = client.describeTable(new DescribeTableRequest(doantionDataTableName));
        }
        catch (Exception e)
        {
            //create table
            CreateDonationDataTable(client);
        }


         try {

                HashMap<String,String> donorIdUUidMap = new  HashMap<String,String>();

                //populate table
                BufferedReader in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("DonationData.csv")));


                for (String line = in.readLine(); line != null; line = in.readLine()) {
                    String [] fields = line.split(",");
                    String donorId  = fields[0].trim();
                    String Category = fields[5].trim();
                    float Amount = Float.parseFloat(fields[4].trim());
                    String DonorName =  fields[1].trim() + " " + fields[2].trim() ;

                    SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy");
                    Date d = f.parse(fields[3].trim());

                    long DonationDate =  d.getTime();

                    String uuid = UUID.randomUUID().toString();
                    String existingUid = donorIdUUidMap.get(donorId);

                    if (existingUid!=null) {
                        uuid = existingUid;
                    }
                    else {
                        donorIdUUidMap.put(donorId,uuid);
                    }


                    Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
                    item.put("DonorUUID", new AttributeValue().withS(uuid));
                    item.put("DonorId", new AttributeValue().withN(donorId));
                    item.put("Category", new AttributeValue().withS(Category));
                    item.put("Amount", new AttributeValue().withN(String.valueOf(Amount)));
                    item.put("DonorName", new AttributeValue().withS(DonorName));
                    item.put("DonationDate", new AttributeValue().withN(String.valueOf(DonationDate)));


                    PutItemRequest putItemRequest = new PutItemRequest()
                            .withTableName(doantionDataTableName)
                            .withItem(item);

                    PutItemResult result = client.putItem(putItemRequest);

                }
            }
            catch (Exception e1)
            {

            }

        }

    protected Donor CreateDonorFrom(java.util.Map<String,AttributeValue> d) {
        Donor newDonor = new Donor();

        newDonor.setDonorUUID(d.get(Donor.Fields.DonorUUID).getS()) ;
        newDonor.setDonorId(Long.parseLong( d.get(Donor.Fields.DonorId).getN()));
        newDonor.setDonorName(d.get(Donor.Fields.DonorName).getS());
        newDonor.setAmount(Long.parseLong( d.get(Donor.Fields.Amount).getN()));
        newDonor.setCategory(d.get(Donor.Fields.Category).getS());
        newDonor.setDonationDate(Long.parseLong(d.get(Donor.Fields.DonationDate).getN()));

        return newDonor;
    }

    public List<Donor> getDonorHistory (String donorUUID) {

        DynamoServiceFactory dsf = new DynamoServiceFactory();
        AmazonDynamoDBClient client = dsf.createDynamoClient();

        Map<String,KeysAndAttributes> batchReq = new HashMap<String, KeysAndAttributes>();


        HashMap<String,AttributeValue > attrib = new HashMap<String,AttributeValue >();
        attrib.put("DonorUUID", new AttributeValue(donorUUID));

        KeysAndAttributes kav = new KeysAndAttributes().withKeys(attrib);

        batchReq.put(doantionDataTableName,kav);

        BatchGetItemRequest req = new BatchGetItemRequest(batchReq);

        BatchGetItemResult res = client.batchGetItem(req);

        java.util.List<java.util.Map<String,AttributeValue>> donors = res.getResponses().get(doantionDataTableName);

        ArrayList<Donor> donorsCreated =  new ArrayList<Donor>();

        System.out.printf("Found %d records", donors.size());

        for (java.util.Map<String,AttributeValue> d : donors) {

            donorsCreated.add( CreateDonorFrom (d));

        }

        return donorsCreated;

    }

}
