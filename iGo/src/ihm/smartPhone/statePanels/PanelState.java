package ihm.smartPhone.statePanels;

import ihm.smartPhone.component.LowerBar;
import ihm.smartPhone.component.UpperBar;
import ihm.smartPhone.tools.PanelDoubleBufferingSoftwear;

public abstract class PanelState extends PanelDoubleBufferingSoftwear {

	/**
	 * On surcharge la métode finalize afin d'informer en console que les objets vont être effacé. Utiliser pour le
	 * développement
	 */
	@Override
	protected void finalize() throws Throwable {
		System.out.println("Endding of :  " + this.getClass().getSimpleName());
		super.finalize();
	}

	/**
	 * l'herbergeant de l'état.
	 */
	protected IhmReceivingPanelState father;

	/**
	 * la barre supérieure de l'IHM
	 */
	protected UpperBar upperBar;

	/**
	 * la barre inférieure de l'IHM
	 */
	protected LowerBar lowerBar;

	/**
	 * Constructeur de base d'un objet PanelState. Il sera possible de l'étendre par la suite, mais il est recommandé de
	 * concerver ces 3 paramètre afin de founir un état riche.
	 * 
	 * @param father
	 *            l'objet qui heberge l'instance d'un PanelState. Il fournit un certain nombre de service.
	 * @param upperBar
	 *            la barre supérieure de l'IHM
	 * @param lowerBar
	 *            la barre inférieure de l'IHM
	 */
	public PanelState(IhmReceivingPanelState father, UpperBar upperBar, LowerBar lowerBar) {
		this();
		System.out.println("Creating of : " + this.getClass().getSimpleName());
		this.father = father;
		this.upperBar = upperBar;
		this.lowerBar = lowerBar;
		this.setBackground(father.getSkin().getColorInside());
	}

	/**
	 * Constructeur par défaut. Il est mit en privé afin d'obliger des instanciation de classe fille avec les argument
	 * que l'on trouve dans les autres constructeur
	 */
	private PanelState() {
		super();
	}

	/**
	 * Signale au panel qu'il a le contrôle, il l'incite ainsi à mettre les barres supérieur et inférieur à sa
	 * convenance.
	 */
	public abstract void giveControle();
}