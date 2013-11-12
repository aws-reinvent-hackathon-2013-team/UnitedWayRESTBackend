package ch.furthermore.demo.st.rest.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = Category.Fields.SELF)
@XmlAccessorType(XmlAccessType.NONE)
public class Category {

	public static interface Fields {
		public static final String SELF = "category";
		public static final String ID = "id";
		public static final String NAME = "name";
	}

	@XmlAttribute(name = Fields.ID)
	private String id;

	@XmlElement(name = Fields.NAME)
	private String name;

	public String getId() {
		return id;
	}

	public Category setId(String id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Category setName(String name) {
		this.name = name;
		return this;
	}

}
