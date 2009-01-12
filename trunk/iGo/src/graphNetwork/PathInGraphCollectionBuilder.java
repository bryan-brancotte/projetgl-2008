package graphNetwork;

/**
 * Wrapper regroupant les Builders
 * 
 * @author "iGo"
 * 
 */
public class PathInGraphCollectionBuilder {

	protected PathInGraphConstraintBuilder pathInGraphConstraintBuilder;

	protected PathInGraphResultBuilder pathInGraphResultBuilder;

	public PathInGraphConstraintBuilder getPathInGraphConstraintBuilder() {
		return pathInGraphConstraintBuilder;
	}

	public PathInGraphResultBuilder getPathInGraphResultBuilder() {
		return pathInGraphResultBuilder;
	}

	public PathInGraph getPathInGraph() {
		return pathInGraphResultBuilder.getCurrentPathInGraph();
	}

	protected PathInGraphCollectionBuilder(PathInGraphConstraintBuilder pathInGraphConstraintBuilder,
			PathInGraphResultBuilder pathInGraphResultBuilder) {
		super();
		this.pathInGraphConstraintBuilder = pathInGraphConstraintBuilder;
		this.pathInGraphResultBuilder = pathInGraphResultBuilder;
	}

}
