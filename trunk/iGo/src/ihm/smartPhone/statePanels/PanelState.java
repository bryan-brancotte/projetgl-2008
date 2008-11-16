package ihm.smartPhone.statePanels;

import ihm.smartPhone.componante.LowerBar;
import ihm.smartPhone.componante.PanelDoubleBufferingSoftwear;
import ihm.smartPhone.componante.UpperBar;

public abstract class PanelState extends PanelDoubleBufferingSoftwear {

	@Override
	protected void finalize() throws Throwable {
		System.out.println("Endding of :  " + this.getClass().getSimpleName());
		super.finalize();
	}

	protected IhmReceivingPanelState father;

	protected UpperBar upperBar;

	protected LowerBar lowerBar;

	public PanelState(IhmReceivingPanelState father, UpperBar upperBar, LowerBar lowerBar) {
		this();
		System.out.println("Creating of : " + this.getClass().getSimpleName());
		this.father = father;
		this.upperBar = upperBar;
		this.lowerBar = lowerBar;
		this.setBackground(father.getSkin().getColorInside());
	}

	private PanelState() {
		super();
	}


	/**
	 * Signale au panel qu'il a le contrôle, il l'incite ainsi à mettre les barres supérieur et inférieur à sa
	 * convenance.
	 */
	public abstract void giveControle();
}
