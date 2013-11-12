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
}
