package ch.furthermore.demo.st.rest.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = Agency.Fields.SELF)
@XmlAccessorType(XmlAccessType.NONE)
public class Agency {

	public static interface Fields {
		public static final String SELF = "agency";
		public static final String ID = "id";
		public static final String NAME = "name";
		public static final String PHONE = "phone";
		public static final String EMAIL = "email";
		public static final String URL = "url";
	}

	@XmlAttribute(name = Fields.ID)
	private String id;

	@XmlElement(name = Fields.NAME)
	private String name;
	
	@XmlElement(name=Fields.PHONE)
	private String phone;
	
	@XmlElement(name=Fields.EMAIL)
	private String email;
	
	@XmlElement(name=Fields.URL)
	private String url;

	@XmlElement(name=Location.Fields.SELF)
	private Location location;
	
	public Location getLocation() {
		return location;
	}

	public Agency setLocation(Location location) {
		this.location = location;
		return this;
	}

	public String getId() {
		return id;
	}

	public Agency setId(String id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Agency setName(String name) {
		this.name = name;
		return this;
	}

	public String getPhone() {
		return phone;
	}

	public Agency setPhone(String phone) {
		this.phone = phone;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public Agency setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getUrl() {
		return url;
	}

	public Agency setUrl(String url) {
		this.url = url;
		return this;
	}

	
}
