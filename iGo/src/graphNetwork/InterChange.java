package graphNetwork;


public class InterChange extends Inter {

	/**
	 * @uml.property  name="pedestrian"
	 */
	private boolean pedestrian;

	/**
	 * Getter of the property <tt>pedestrian</tt>
	 * @return  Returns the pedestrian.
	 * @uml.property  name="pedestrian"
	 */
	public boolean isPedestrian() {
		return pedestrian;
	}

	/**
	 * Setter of the property <tt>pedestrian</tt>
	 * @param pedestrian  The pedestrian to set.
	 * @uml.property  name="pedestrian"
	 */
	public void setPedestrian(boolean pedestrian) {
		this.pedestrian = pedestrian;
	}

	@Override
	public String getKindOfInter() {
		// TODO Auto-generated method stub
		return null;
	}

}
