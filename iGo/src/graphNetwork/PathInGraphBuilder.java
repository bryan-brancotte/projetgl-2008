package graphNetwork;


/**
 * 
 * @author iGo
 */
public class PathInGraphBuilder {

	/**
	 * @uml.property name="inter"
	 * @uml.associationEnd multiplicity="(0 -1)" ordering="true" inverse="pathInGraph:graphNetwork.Inter"
	 */
	private PathInGraph actualPathInGraph;

	@SuppressWarnings("unused")
	private PathInGraphBuilder() {
	}

	protected PathInGraphBuilder(PathInGraph path) {
		actualPathInGraph = path;
	}

	protected void addFront(Inter inter) {
	}

	protected void addLast(Inter inter) {
		actualPathInGraph.inter.addLast(inter);
	}

	protected void flush() {
	}

	protected void importPath(String pathInString) {
		// TODO Auto-generated method stub
	}

}
