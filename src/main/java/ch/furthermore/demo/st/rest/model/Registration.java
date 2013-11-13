package ch.furthermore.demo.st.rest.model;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = Category.Fields.SELF)
@XmlAccessorType(XmlAccessType.NONE)
public class Registration {

    public static interface Fields {
        public static final String DonorUUID = "DonorUUID";
        public static final String OpportunityId = "OpportunityId";
        public static final String Category = "Category";
        public static final String Timestamp = "Timestamp";
    }

    @XmlElement(name=Fields.DonorUUID)
    private String DonorUUID;

    @XmlElement(name=Fields.OpportunityId)
    private String OpportunityId;

    @XmlElement(name=Fields.Category)
    private String Category;

    @XmlElement(name=Fields.Timestamp)
    private Long Timestamp;

    public void setDonorUUID(String donorUUID) {
        DonorUUID = donorUUID;
    }

    public void setOpportunityId(String donorId) {
        OpportunityId = donorId;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getDonorUUID() {
        return DonorUUID;
    }

    public String getOpportunityId() {
        return OpportunityId;
    }

    public String getCategory() {
        return Category;
    }

    public Long getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(Long timestamp) {
        Timestamp = timestamp;
    }
}
