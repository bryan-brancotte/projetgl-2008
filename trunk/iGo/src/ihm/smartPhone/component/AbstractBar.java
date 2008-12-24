package ihm.smartPhone.component;

import ihm.smartPhone.IGoIhmSmartPhone;
import ihm.smartPhone.libPT.PanelDoubleBufferingSoftwear;

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
