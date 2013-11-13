package ch.furthermore.demo.st.rest.model;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = Category.Fields.SELF)
@XmlAccessorType(XmlAccessType.NONE)
public class Donor {

    public static interface Fields {
        public static final String DonorUUID = "DonorUUID";
        public static final String DonorId = "DonorId";
        public static final String Category = "Category";
        public static final String DonorName = "DonorName";
        public static final String Amount = "Amount";
        public static final String DonationDate = "DonationDate";
    }

    @XmlElement(name=Fields.DonorUUID)
    private String DonorUUID;

    @XmlElement(name=Fields.DonorId)
    private Long DonorId;

    @XmlElement(name=Fields.Category)
    private String Category;

    @XmlElement(name=Fields.DonorName)
    private String DonorName;

    @XmlElement(name=Fields.Amount)
    private Long Amount;

    @XmlElement(name=Fields.DonationDate)
    private Long DonationDate;


    public String getDonorUUID() {
        return DonorUUID;
    }

    public Long getDonorId() {
        return DonorId;
    }

    public String getCategory() {
        return Category;
    }

    public Long getAmount() {
        return Amount;
    }

    public Long getDonationDate() {
        return DonationDate;
    }
    public String getDonorName() {
        return DonorName;
    }

    public void setDonorUUID(String donorUUID) {
        DonorUUID = donorUUID;
    }

    public void setDonorId(Long donorId) {
        DonorId = donorId;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public void setDonorName(String donorName) {
        DonorName = donorName;
    }

    public void setAmount(Long amount) {
        Amount = amount;
    }

    public void setDonationDate(Long donationDate) {
        DonationDate = donationDate;
    }
}
