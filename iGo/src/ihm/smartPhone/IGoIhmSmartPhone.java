package ihm.smartPhone;

import iGoMaster.Master;
import ihm.JMenuDock.JMenuDock;
import ihm.JNetwork.JNetwork;


public class IGoIhmSmartPhone implements IHM {

	public IGoIhmSmartPhone() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @uml.property  name="menuDock"
	 * @uml.associationEnd  inverse="iGoIhmSmartPhone:ihm.JMenuDock.JMenuDock"
	 */
	private JMenuDock menuDock;

	/**
	 * Getter of the property <tt>menuDock</tt>
	 * @return  Returns the menuDock.
	 * @uml.property  name="menuDock"
	 */
	public JMenuDock getMenuDock() {
		return menuDock;
	}

	/**
	 * Setter of the property <tt>menuDock</tt>
	 * @param menuDock  The menuDock to set.
	 * @uml.property  name="menuDock"
	 */
	public void setMenuDock(JMenuDock menuDock) {
		this.menuDock = menuDock;
	}

	/**
	 * @uml.property   name="master"
	 * @uml.associationEnd   inverse="iGoIhmSmartPhone:iGoMaster.Master"
	 */
	private Master master;

	/** 
	 * Getter of the property <tt>master</tt>
	 * @return  Returns the master.
	 * @uml.property  name="master"
	 */
	public Master getMaster() {
		return master;
	}

	/** 
	 * Setter of the property <tt>master</tt>
	 * @param master  The master to set.
	 * @uml.property  name="master"
	 */
	public void setMaster(Master master) {
		this.master = master;
	}

	/**
	 * @uml.property   name="reseau"
	 * @uml.associationEnd   inverse="iGoIhmSmartPhone:ihm.JNetwork.JNetwork"
	 */
	private JNetwork reseau;

	/**
	 * Getter of the property <tt>reseau</tt>
	 * @return  Returns the reseau.
	 * @uml.property  name="reseau"
	 */
	public JNetwork getReseau() {
		return reseau;
	}

	/**
	 * Setter of the property <tt>reseau</tt>
	 * @param reseau  The reseau to set.
	 * @uml.property  name="reseau"
	 */
	public void setReseau(JNetwork reseau) {
		this.reseau = reseau;
	}

}
