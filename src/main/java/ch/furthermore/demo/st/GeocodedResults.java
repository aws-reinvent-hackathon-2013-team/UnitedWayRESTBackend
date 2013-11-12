package ch.furthermore.demo.st;

public class GeocodedResults {
	private long needId; 
	private String needTitle;
	private String description;
	private long agencyIdBit;
	private String agencyName;
	private String category;
	private String agencyAddress;
	private String agencyCity;
	private String agencyState;
	private int agencyZip;
	
	private double latitude;
	private double longitude;
	
	private String phone;
	private String email;

	public String getRangeKey() {
		return "" + needId;
	}
	
	public void setRangeKey(String s) {
		this.needId = Integer.parseInt(s);
	}
	
	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public long getNeedId() {
		return needId;
	}

	public void setNeedId(long needId) {
		this.needId = needId;
	}

	public String getNeedTitle() {
		return needTitle;
	}

	public void setNeedTitle(String needTitle) {
		this.needTitle = needTitle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getAgencyIdBit() {
		return agencyIdBit;
	}

	public void setAgencyIdBit(long agencyIdBit) {
		this.agencyIdBit = agencyIdBit;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getAgencyAddress() {
		return agencyAddress;
	}

	public void setAgencyAddress(String agencyAddress) {
		this.agencyAddress = agencyAddress;
	}

	public String getAgencyCity() {
		return agencyCity;
	}

	public void setAgencyCity(String agencyCity) {
		this.agencyCity = agencyCity;
	}

	public String getAgencyState() {
		return agencyState;
	}

	public void setAgencyState(String agencyState) {
		this.agencyState = agencyState;
	}

	public int getAgencyZip() {
		return agencyZip;
	}

	public void setAgencyZip(int agencyZip) {
		this.agencyZip = agencyZip;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
