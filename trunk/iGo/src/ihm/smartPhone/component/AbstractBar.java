package ihm.smartPhone.component;

import libPT.PanelDoubleBufferingSoftwear;
import ihm.smartPhone.IGoIhmSmartPhone;

public abstract class AbstractBar extends PanelDoubleBufferingSoftwear {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected IGoIhmSmartPhone ihm;

	public AbstractBar(IGoIhmSmartPhone ihm) {
		this.ihm = ihm;
	}

	public abstract void clearMessage();
}
