package ihm.smartPhone.tools;

import ihm.smartPhone.listener.MouseListenerClickAndMoveInArea;

import java.awt.Rectangle;

public abstract class PanelTooled extends PanelDoubleBufferingSoftwear {
	protected MouseListenerClickAndMoveInArea clickAndMoveWarningAndArray;

	/**
	 * Crée un PanelTooled soit un panel outillé. Ce constructeur initialise les paramètres internes.
	 */
	public PanelTooled() {
		super();
		clickAndMoveWarningAndArray = new MouseListenerClickAndMoveInArea(this);
		this.addMouseListener(clickAndMoveWarningAndArray);
		this.addMouseMotionListener(clickAndMoveWarningAndArray);
	}

	/**
	 * Crée un boutton dont l'action exécuté sera celle passé en paramètre.
	 * 
	 * @param action
	 *            evenement déclanché lors du clic sur le bouton, ce dernier ne doit pas être null
	 * @return retourne le boutton
	 */
	public PTButton makeButton(CodeExecutor action) {
		if (action == null)
			throw new NullPointerException();
		return new PTButton(this, clickAndMoveWarningAndArray.addInteractiveArea(new Rectangle(), action));
	}

	/**
	 * Créé un radioBox dans un group spécifique, lorsque l'on cliquera sur ce RadioBox, les autres seront décochés. En
	 * plus de cette action, on s'autorise à lancer une seconde action que l'utilisateur spécifira par le paramètre
	 * action.
	 * 
	 * @param grp
	 *            group dans lequel le radiobouton à une existance
	 * @param action
	 *            evenement déclanché lors du clic sur le bouton, ce dernier est optionel.
	 * @return le PTRadioButtton créé
	 */
	public PTRadioBox makeRadioButton(PTRadioBoxGroup grp, CodeExecutor action) {
		if (action == null)
			return makeRadioButton(grp);
		Rectangle area = new Rectangle();
		PTRadioBox radioBox = new PTRadioBox(this, area);
		clickAndMoveWarningAndArray.addInteractiveArea(area,
				new CodeExecutor3P<PTRadioBoxGroup, PTRadioBox, CodeExecutor>(grp, radioBox, action) {
					@Override
					public void execute() {
						this.origineA.setAllNotClicked();
						this.origineB.setClicked(true);
						this.origineC.execute();
					}
				});
		return radioBox;
	}

	/**
	 * Créé un radioBox dans un group spécifique, lorsque l'on cliquera sur ce RadioBox, les autres seront décochés
	 * 
	 * @param grp
	 *            le groupe du RadioBox. ce paramètre ne peut être null
	 * @return la RadioBox créé.
	 */
	public PTRadioBox makeRadioButton(PTRadioBoxGroup grp) {
		if (grp == null)
			throw new NullPointerException();
		Rectangle area = new Rectangle();
		PTRadioBox radioBox = new PTRadioBox(this, area);
		clickAndMoveWarningAndArray.addInteractiveArea(area, new CodeExecutor2P<PTRadioBoxGroup, PTRadioBox>(grp,
				radioBox) {
			@Override
			public void execute() {
				this.origineA.setAllNotClicked();
				this.origineB.setClicked(true);
			}
		});
		return radioBox;
	}

	/**
	 * Créé une CheckBox, et execute l'action passé en paramètre après avoir effectué son traitement (changement de
	 * l'état)
	 * 
	 * @param action
	 *            l'action à exécuter. Si le paramètre est null, cela revient à appeller makeCheckBox() sans paramètre.
	 * @return la CheckBox créé
	 */
	public PTCheckBox makeCheckBox(CodeExecutor action) {
		if (action == null)
			return makeCheckBox();
		Rectangle area = new Rectangle();
		PTCheckBox checkBox = new PTCheckBox(this, area);
		clickAndMoveWarningAndArray.addInteractiveArea(area, new CodeExecutor2P<PTCheckBox, CodeExecutor>(checkBox,
				action) {
			@Override
			public void execute() {
				this.origineA.changeClicked();
				this.origineB.execute();
			}
		});
		return checkBox;
	}

	/**
	 * Créé un CheckBox.
	 * 
	 * @return la retourne
	 */
	public PTCheckBox makeCheckBox() {
		Rectangle area = new Rectangle();
		PTCheckBox checkBox = new PTCheckBox(this, area);
		clickAndMoveWarningAndArray.addInteractiveArea(new Rectangle(), new CodeExecutor1P<PTCheckBox>(checkBox) {
			@Override
			public void execute() {
				this.origine.changeClicked();
			}
		});
		return checkBox;
	}

	public PTCollapsableArea makeCollapsableArea() {
		Rectangle area = new Rectangle();
		PTCollapsableArea collapsableArea = new PTCollapsableArea(this, area);
		return collapsableArea;
	}

	// TODO champs avec completion, et la fenêtre des choix comme chrome?

	// TODO combo box ou pas?

}
