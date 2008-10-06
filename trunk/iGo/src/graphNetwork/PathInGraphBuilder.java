package graphNetwork;

public interface PathInGraphBuilder extends PathInGraphReader {

	/**
	 * Add at the end an element inter
	 * 
	 * @param inter
	 *            element to add at the end
	 */
	public abstract void add(Inter inter);

	/**
	 * Add at the begining of the path a inter
	 * 
	 * @param inter
	 *            element to add at the begining
	 */
	public abstract void addFront(InterReader inter);

	/**
	 * Flush the content of the path
	 */
	public abstract void flush();

	/**
	 */
	public abstract void importPath(String pathInString);

}