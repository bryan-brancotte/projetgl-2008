package graphNetwork;

/**
 *  
 * @author iGo
 */
public interface PathInGraphBuilder extends PathInGraphReader {

	/**
	 * Add at the begining of the path a inter
	 * 
	 * @param inter
	 *            element to add at the begining
	 */
	public abstract void addFront(InterReader inter);

	/**
	 * Add at the end an element inter
	 * 
	 * @param inter
	 *            element to add at the end
	 */
	public abstract void addLast(Inter inter);

	/**
	 * Flush the content of the path
	 */
	public abstract void flush();

	/**
	 * Import depuis un chaîne un chemin, cette chaine doit avoir été faite avec la fonction PathInGraphReader.exportPath()
	 * @param pathInString
	 */
	public abstract void importPath(String pathInString);

}