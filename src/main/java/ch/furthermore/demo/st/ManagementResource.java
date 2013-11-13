package ch.furthermore.demo.st;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import ch.furthermore.demo.st.rest.model.Donor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Path("/management")
public class ManagementResource { //FIXME authentication and authorization!!!
	@Autowired
	private Importer importer;

    @Autowired
    private DataAccess dataAccess;
	
	@GET
    @Path("/startImport")
	@Produces(MediaType.APPLICATION_JSON)
    public String startImport() {
		try {
			importer.importData();
			return "done ;-)";
		}
		catch (Exception e) {
			return "Sorry: "  + e;
		}
	}

    @GET
    @Path("/ImportDonationData")
    @Produces(MediaType.APPLICATION_JSON)
    public String ImportDonationData() {
        try {
            importer.ImportDonationData();
            return "done ;-)";
        }
        catch (Exception e) {
            return "Sorry: "  + e;
        }
    }

    //?uuid=d352d516-39f8-46a6-9e16-01237c01a6a7
    @GET
    @Path("/findDonor")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Donor> findDonor(@QueryParam("uuid") String uuid) {
        //try {
            return dataAccess.getDonorHistory(uuid);

        //}
//        catch (Exception e) {
//            return null;
//        }
    }

}
