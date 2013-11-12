package ch.furthermore.demo.st.rest.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = Opportunity.Fields.SELF)
@XmlAccessorType(XmlAccessType.NONE)
public class Opportunity {
	public static interface Fields {
		public static final String SELF = "agency";
		public static final String ID = "id";
		public static final String TITLE = "title";
		public static final String DESCRIPTION = "description";
		public static final String TIME_START = "timeStart";
		public static final String TIME_END = "timeEnd";
	}

	@XmlAttribute(name = Fields.ID)
	private String id;

	@XmlElement(name = Fields.TITLE)
	private String title;
	
	@XmlElement(name=Fields.DESCRIPTION)
	private String description;
	
	@XmlElement(name=Fields.TIME_START)
	private Date timeStart;
	
	@XmlElement(name=Fields.TIME_END)
	private Date timeEnd;
	
	@XmlElement(name=Agency.Fields.SELF)
	private Agency agency;
	
	@XmlElement(name=Location.Fields.SELF)
	private Location location;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(Date timeStart) {
		this.timeStart = timeStart;
	}

	public Date getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(Date timeEnd) {
		this.timeEnd = timeEnd;
	}

	public Agency getAgency() {
		return agency;
	}

	public void setAgency(Agency agency) {
		this.agency = agency;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
}
