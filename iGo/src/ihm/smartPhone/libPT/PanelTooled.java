package ihm.smartPhone.libPT;

import ihm.smartPhone.tools.CodeExecutor;
import ihm.smartPhone.tools.CodeExecutor2P;
import ihm.smartPhone.tools.CodeExecutor3P;
import ihm.smartPhone.tools.CodeExecutor4P;

import java.awt.Rectangle;

public abstract class PanelTooled extends PanelDoubleBufferingSoftwear {
	/**
	 * ...
	 */
	private static final long serialVersionUID = -3099033334326367565L;

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

	public PTReactiveArea makeReactiveArea(CodeExecutor action) {
		if (action == null)
			throw new NullPointerException();
		return new PTReactiveArea(this, clickAndMoveWarningAndArray.addInteractiveArea(new Rectangle(), action, false));
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
		grp.add(radioBox);
		radioBox.areaCodEx = clickAndMoveWarningAndArray
				.addInteractiveArea(area, new CodeExecutor4P<PTRadioBoxGroup, PTRadioBox, CodeExecutor, PanelTooled>(
						grp, radioBox, action, this) {
					@Override
					public void execute() {
						this.origineA.setAllNotClicked();
						this.origineB.setClicked(true);
						this.origineC.execute();
						this.origineD.repaint();
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
		grp.add(radioBox);
		radioBox.areaCodEx = clickAndMoveWarningAndArray.addInteractiveArea(area,
				new CodeExecutor3P<PTRadioBoxGroup, PTRadioBox, PanelTooled>(grp, radioBox, this) {
					@Override
					public void execute() {
						this.origineA.setAllNotClicked();
						this.origineB.setClicked(true);
						this.origineC.repaint();
					}
				});
		return radioBox;
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
	public PTRadioBox makeRadioButton(PTRadioBoxGroup[] grp, CodeExecutor action) {
		if (action == null)
			return makeRadioButton(grp);
		Rectangle area = new Rectangle();
		PTRadioBox radioBox = new PTRadioBox(this, area);
		for (PTRadioBoxGroup g : grp)
			g.add(radioBox);
		radioBox.areaCodEx = clickAndMoveWarningAndArray.addInteractiveArea(area,
				new CodeExecutor4P<PTRadioBoxGroup[], PTRadioBox, CodeExecutor, PanelTooled>(grp, radioBox, action,
						this) {
					@Override
					public void execute() {
						for (PTRadioBoxGroup g : this.origineA)
							g.setAllNotClicked();
						this.origineB.setClicked(true);
						this.origineC.execute();
						this.origineD.repaint();
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
	public PTRadioBox makeRadioButton(PTRadioBoxGroup[] grp) {
		if (grp == null)
			throw new NullPointerException();
		Rectangle area = new Rectangle();
		PTRadioBox radioBox = new PTRadioBox(this, area);
		for (PTRadioBoxGroup g : grp)
			g.add(radioBox);
		radioBox.areaCodEx = clickAndMoveWarningAndArray.addInteractiveArea(area,
				new CodeExecutor3P<PTRadioBoxGroup[], PTRadioBox, PanelTooled>(grp, radioBox, this) {
					@Override
					public void execute() {
						for (PTRadioBoxGroup g : this.origineA)
							g.setAllNotClicked();
						this.origineB.setClicked(true);
						this.origineC.repaint();
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
		checkBox.areaCodEx = clickAndMoveWarningAndArray.addInteractiveArea(area,
				new CodeExecutor3P<PTCheckBox, CodeExecutor, PanelTooled>(checkBox, action, this) {
					@Override
					public void execute() {
						this.origineA.changeClicked();
						this.origineB.execute();
						this.origineC.repaint();
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
		checkBox.areaCodEx = clickAndMoveWarningAndArray.addInteractiveArea(area,
				new CodeExecutor2P<PTCheckBox, PanelTooled>(checkBox, this) {
					@Override
					public void execute() {
						this.origineA.changeClicked();
						this.origineB.repaint();
					}
				});
		return checkBox;
	}

	/**
	 * Crée un zone retractable.
	 * 
	 * @return
	 */
	public PTCollapsableArea makeCollapsableArea() {
		Rectangle area = new Rectangle();
		PTCollapsableArea collapsableArea = new PTCollapsableArea(this, area);
		return collapsableArea;
	}

	/**
	 * Crée un simple zone
	 * 
	 * @return
	 */
	public PTArea makeArea() {
		Rectangle area = new Rectangle();
		PTArea pta = new PTArea(this, area);
		return pta;
	}

	/**
	 * Crée un scrollBar vertical avec un défilement vertical par la souris de 15 pxl
	 * 
	 * @return
	 */
	public PTScrollBar makeScrollBar() {
		Rectangle area = new Rectangle();
		PTScrollBar sb = new PTScrollBar(this, area);
		return sb;
	}

	/**
	 * Crée un scrollBar vertical
	 * 
	 * @return
	 */
	public PTScrollBar makeScrollBar(int mouseWhelling) {
		Rectangle area = new Rectangle();
		PTScrollBar sb = new PTScrollBar(this, area, mouseWhelling);
		return sb;
	}

	/**
	 * Créé une textBox avec l'autocompletion
	 * 
	 * @param fields
	 *            les champs possible
	 * @return
	 */
	public PTAutoCompletionTextBox makeAutoCompletionTextBox(String[] fields) {
		if (fields == null)
			return null;
		Rectangle area = new Rectangle();
		PTAutoCompletionTextBox ac = new PTAutoCompletionTextBox(this, area, fields, null, null);
		return ac;
	}

	/**
	 * Créé une textBox avec l'autocompletion.
	 * 
	 * @param fields
	 *            les champs possible
	 * @param action
	 *            l'action qui sera exécuté si on modifie le contenu
	 * @return
	 */
	public PTAutoCompletionTextBox makeAutoCompletionTextBox(String[] fields, CodeExecutor action) {
		if (fields == null)
			return null;
		Rectangle area = new Rectangle();
		PTAutoCompletionTextBox ac = new PTAutoCompletionTextBox(this, area, fields, action, null);
		return ac;
	}

	/**
	 * Créé une textBox avec l'autocompletion.
	 * 
	 * @param fields
	 *            les champs possible
	 * @param action
	 *            l'action qui sera exécuté si on modifie le contenu
	 * @param action
	 *            l'action qui sera exécuté si on appuie sur entrée
	 * @return
	 */
	public PTAutoCompletionTextBox makeAutoCompletionTextBox(String[] fields, CodeExecutor actionOnChange,
			CodeExecutor actionOnEnter) {
		if (fields == null)
			return null;
		Rectangle area = new Rectangle();
		PTAutoCompletionTextBox ac = new PTAutoCompletionTextBox(this, area, fields, actionOnChange, actionOnEnter);
		return ac;
	}

}
