package graphNetwork;

import String;
import java.util.LinkedList;

public class PathInGraphBuilder implements PathInGraphReader {

	protected LinkedList<Inter> inter;

	@Override
	public InterReader[] getInter() {
		// TODO Auto-generated method stub
		return inter.toArray(new Inter[0]);
	}

	@Override
	public float getCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte getTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Add at the end an element inter
	 * 
	 * @param inter element to add at the end
	 */
	public void add(Inter inter) {
		this.inter.addLast(inter);
	}

	/**
	 * Add at the begining of the path a inter
	 * 
	 * @param inter element to add at the begining
	 */
	public void addFront(InterReader inter) {
	}

	/**
	 * Flush the content of the path
	 */
	public void flush() {
	}

		
		/**
		 */
		public String exportPath(){
			return "";	
		}

			
			/**
			 */
			public void importPath(String pathInString){
			}

}
