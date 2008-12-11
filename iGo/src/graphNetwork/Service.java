package graphNetwork;

/**
 * 
 * @author iGo
 */
public class Service {

	private int id;//@uml.property name="id"
	private String shortDescription = "";//@uml.property name="shortDescription"

	protected Service(int id, String shortDescription) {
		super();
		this.id = id;
		this.shortDescription = shortDescription;
	}

	public int getId() {
		return id;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	protected void setId(int id) {
		this.id = id;
	}

	protected void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

}
