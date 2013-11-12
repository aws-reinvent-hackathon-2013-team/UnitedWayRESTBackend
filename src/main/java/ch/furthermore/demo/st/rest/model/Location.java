package ch.furthermore.demo.st.rest.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = Location.Fields.SELF)
@XmlAccessorType(XmlAccessType.NONE)
public class Location {

	public static interface Fields {
		public static final String SELF = "location";
		public static final String ADDRESS1 = "address1";
		public static final String ADDRESS2 = "address2";
		public static final String CITY = "city";
		public static final String STATE = "state";
		public static final String ZIP = "zip";
	}

	@XmlElement(name=Fields.ADDRESS1)
	private String address1;
	
	@XmlElement(name=Fields.ADDRESS2)
	private String address2;
	
	@XmlElement(name=Fields.CITY)
	private String city;
	
	@XmlElement(name=Fields.STATE)
	private String state;
	
	@XmlElement(name=Fields.ZIP)
	private Integer zip;

	public String getAddress1() {
		return address1;
	}

	public Location setAddress1(String address1) {
		this.address1 = address1;
		return this;
	}

	public String getAddress2() {
		return address2;
	}

	public Location setAddress2(String address2) {
		this.address2 = address2;
		return this;
	}

	public String getCity() {
		return city;
	}

	public Location setCity(String city) {
		this.city = city;
		return this;
	}

	public String getState() {
		return state;
	}

	public Location setState(String state) {
		this.state = state;
		return this;
	}

	public Integer getZip() {
		return zip;
	}

	public Location setZip(Integer zip) {
		this.zip = zip;
		return this;
	}
	
	
}
