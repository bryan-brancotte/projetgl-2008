package ihm.smartPhone.component;

import ihm.smartPhone.IGoIhmSmartPhone;
import ihm.smartPhone.listener.MouseListenerClickAndMoveInArea;
import ihm.smartPhone.tools.CodeExecutor;
import ihm.smartPhone.tools.ImageLoader;
import ihm.smartPhone.tools.SizeAdapteur.FontSizeKind;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LowerBar extends AbstractBar {

	private static final long serialVersionUID = 1L;
	protected int oldHeigth = -1;

	protected String mainTitle;
	protected FontSizeKind mainTitleSize;
	protected String leftTitle;
	protected FontSizeKind leftTitleSize;
	protected String leftValue;
	protected FontSizeKind leftValueSize;
	protected String rigthTitle;
	protected FontSizeKind rigthTitleSize;
	protected String rigthValue;
	protected FontSizeKind rigthValueSize;

	protected String leftCmd;
	protected FontSizeKind leftCmdSize;
	protected Rectangle leftCmdArea = null;
	protected ActionListener leftCmdActionListener = null;

	protected String rigthCmd;
	protected FontSizeKind rigthCmdSize;
	protected Rectangle rigthCmdArea = null;
	protected ActionListener rigthCmdActionListener = null;

	protected String icone;
	protected Image imageIcone;
	protected Rectangle iconeCmdArea = null;
	protected ActionListener iconeCmdActionListener = null;

	/**
	 * Constructeur basique de l'objet. L'option demo permet d'initialisé chaque champs à son nom.
	 * 
	 * @param ihm
	 *            l'IGoIhmSmartPhone qui est le parent
	 * @param demo
	 *            true si on veux initialiser chaque champs à son nom, permet ainsi de maitrisé facilement l'objet et
	 *            ses multpile champs
	 */
	public LowerBar(IGoIhmSmartPhone ihm, boolean demo) {
		super(ihm);
		iconeCmdArea = new Rectangle();
		leftCmdArea = new Rectangle();
		rigthCmdArea = new Rectangle();
		if (demo) {
			setCenterIcone("button_save", null);
			setMainTitle("mainTitle");
			setLeftTitle("leftTitle");
			setLeftValue("leftValue");
			setRightTitle("rigthTitle");
			setRightValue("rigthValue");
			setLeftCmd("leftCmd", null);
			setRightCmd("rigthCmd", null);
		} else {
			clearMessage();
		}
		MouseListenerClickAndMoveInArea l = new MouseListenerClickAndMoveInArea(this);
		l.addInteractiveArea(leftCmdArea, new CodeExecutor() {
			@Override
			public void execute() {
				leftCmdActionListener.actionPerformed(new ActionEvent(me, 0, ""));
			}
		});
		l.addInteractiveArea(rigthCmdArea, new CodeExecutor() {
			@Override
			public void execute() {
				rigthCmdActionListener.actionPerformed(new ActionEvent(me, 0, ""));
			}
		});
		l.addInteractiveArea(iconeCmdArea, new CodeExecutor() {
			@Override
			public void execute() {
				iconeCmdActionListener.actionPerformed(new ActionEvent(me, 0, ""));
			}
		});
		this.addMouseListener(l);
		this.addMouseMotionListener(l);
	}

	@Override
	public void paint(Graphics g) {
		int insideR = ihm.getSkin().getColorInside().getRed();
		int insideG = ihm.getSkin().getColorInside().getGreen();
		int insideB = ihm.getSkin().getColorInside().getBlue();
		int deltaR = ihm.getSkin().getColorOutside().getRed() - insideR;
		int deltaG = ihm.getSkin().getColorOutside().getGreen() - insideG;
		int deltaB = ihm.getSkin().getColorOutside().getBlue() - insideB;
		float prog;
		for (int i = 0; i < this.getHeight(); i++) {
			prog = (float) i / this.getHeight();
			g.setColor(new Color(insideR + (int) (deltaR * prog), insideG + (int) (deltaG * prog), insideB
					+ (int) (deltaB * prog)));
			g.drawLine(0, i, this.getWidth(), i);
		}

		g.setColor(ihm.getSkin().getColorLetter());
		drawStrings(g, FontSizeKind.LARGE);
		drawStrings(g, FontSizeKind.INTERMEDIATE);
		drawStrings(g, FontSizeKind.SMALL);
		if (icone != "") {
			if ((imageIcone == null) || (oldHeigth != this.getHeight())) {
				imageIcone = ImageLoader.getRessourcesImageIcone(icone, this.getWidth(), this.getHeight() - 2)
						.getImage();
			}
			iconeCmdArea.setBounds(this.getWidth() - imageIcone.getWidth(null) >> 1, 1, imageIcone.getHeight(null),
					imageIcone.getWidth(null));
			(g).drawImage(imageIcone, iconeCmdArea.x, iconeCmdArea.y, null);
			oldHeigth = this.getHeight();
		}
	}

	/**
	 * Ecriture de chacun de chaque message qui a pour police, le type passé en paramètre.
	 * 
	 * @param g
	 *            le Graphics où les chaines seront dessinés
	 * @param fontKindSize
	 *            le type de le police pour cette affichage
	 */
	protected void drawStrings(Graphics g, FontSizeKind fontKindSize) {
		Font font = null;
		int[] xs;
		int[] ys;
		int hs, ws, hs23, ws11;
		Color colorFont = g.getColor();
		if (fontKindSize == FontSizeKind.LARGE)
			font = ihm.getSizeAdapteur().getLargeFont();
		if (fontKindSize == FontSizeKind.INTERMEDIATE)
			font = ihm.getSizeAdapteur().getIntermediateFont();
		if (fontKindSize == FontSizeKind.SMALL)
			font = ihm.getSizeAdapteur().getSmallFont();
		if (font == null)
			return;
		g.setFont(font);

		if ((mainTitleSize == fontKindSize) && (mainTitle != ""))
			g.drawString(mainTitle, this.getWidth() - getWidthString(mainTitle, g, font) >> 1, this.getHeight()
					+ getHeigthString(mainTitle, g, font) >> 1);

		if ((leftTitleSize == fontKindSize) && (leftTitle != ""))
			g.drawString(leftTitle, 0, getHeigthString(leftTitle, g, font));

		if ((leftValueSize == fontKindSize) && (leftValue != ""))
			g.drawString(leftValue, (this.getWidth() >> 2) - (getWidthString(leftValue, g, font) >> 1), this
					.getHeight()
					+ getHeigthString(leftValue, g, font) >> 1);

		if ((rigthTitleSize == fontKindSize) && (rigthTitle != ""))
			g.drawString(rigthTitle, this.getWidth() - getWidthString(rigthTitle, g, font), getHeigthString(rigthTitle,
					g, font));

		if ((rigthValueSize == fontKindSize) && (rigthValue != ""))
			g.drawString(rigthValue, 3 * (this.getWidth() >> 2) - (getWidthString(rigthValue, g, font) >> 1), this
					.getHeight()
					+ getHeigthString(rigthValue, g, font) >> 1);

		if ((leftCmdSize == fontKindSize) && (leftCmd != "")) {
			g.setColor(ihm.getSkin().getColorInside());
			hs = getHeigthString(leftCmd, g, font);
			hs23 = (int) (hs * 0.667);
			ws11 = (int) (getWidthString(leftCmd, g, font) * 1.1);
			xs = new int[] { hs23 + 1, hs + ws11, hs + ws11, hs23 + 1, 1 };
			ys = new int[] { (this.getHeight() >> 1) - hs23, (this.getHeight() >> 1) - hs23,
					(this.getHeight() >> 1) + hs23 + 1, (this.getHeight() >> 1) + hs23 + 1, this.getHeight() >> 1 };
			leftCmdArea.setBounds(xs[0], ys[0], xs[2] - xs[0], ys[2] - ys[0]);
			g.fillPolygon(xs, ys, xs.length);
			g.setColor(ihm.getSkin().getColorLine());
			ys[2]--;
			ys[3]--;
			g.drawPolygon(xs, ys, xs.length);
			g.setColor(colorFont);
			g.drawString(leftCmd, hs + 1, this.getHeight() + hs >> 1);
		}

		if ((rigthCmdSize == fontKindSize) && (rigthCmd != "")) {
			g.setColor(ihm.getSkin().getColorInside());
			hs = getHeigthString(rigthCmd, g, font);
			hs23 = (int) (hs * 0.667);
			ws = getWidthString(rigthCmd, g, font);
			ws11 = (int) (ws * 1.1);
			xs = new int[] { this.getWidth() - hs23 - 1, this.getWidth() - hs - ws11, this.getWidth() - hs - ws11,
					this.getWidth() - hs23 - 1, this.getWidth() - 1 };
			ys = new int[] { (this.getHeight() >> 1) - hs23, (this.getHeight() >> 1) - hs23,
					(this.getHeight() >> 1) + hs23 + 1, (this.getHeight() >> 1) + hs23 + 1, this.getHeight() >> 1 };
			rigthCmdArea.setBounds(xs[2], ys[0], xs[0] - xs[2], ys[2] - ys[0]);
			g.fillPolygon(xs, ys, xs.length);
			g.setColor(ihm.getSkin().getColorLine());
			ys[2]--;
			ys[3]--;
			g.drawPolygon(xs, ys, xs.length);
			g.setColor(colorFont);
			g.drawString(rigthCmd, this.getWidth() - hs - 1 - ws, this.getHeight() + hs >> 1);
		}

	}

	/*******************************************************************************************************************
	 * Setteurs des messages et commande
	 */

	/**
	 * Définit le titre principale avec la taille spécifiée
	 * 
	 * @param mainTitle
	 *            le titre
	 * @param fontKindSize
	 *            le type de taille
	 */
	public void setMainTitle(String mainTitle, FontSizeKind fontKindSize) {
		this.mainTitle = mainTitle;
		this.mainTitleSize = fontKindSize;
	}

	/**
	 * Définit le titre principale avec la taille par défaut
	 * 
	 * @param mainTitle
	 *            le titre
	 */
	public void setMainTitle(String mainTitle) {
		setMainTitle(mainTitle, FontSizeKind.INTERMEDIATE);
	}

	/**
	 * Définit le titre de gauche avec la taille spécifiée
	 * 
	 * @param leftTitle
	 *            le titre
	 * @param fontKindSize
	 *            le type de taille
	 */
	public void setLeftTitle(String leftTitle, FontSizeKind fontKindSize) {
		this.leftTitle = leftTitle;
		this.leftTitleSize = fontKindSize;
	}

	/**
	 * Définit le titre de gauche avec la taille par défaut
	 * 
	 * @param leftTitle
	 *            le titre
	 */
	public void setLeftTitle(String leftTitle) {
		setLeftTitle(leftTitle, FontSizeKind.SMALL);
	}

	/**
	 * Définit la valeur de gauche avec la taille spécifiée
	 * 
	 * @param leftValue
	 *            la valeur
	 * @param fontKindSize
	 *            le type de taille
	 */
	public void setLeftValue(String leftValue, FontSizeKind fontKindSize) {
		this.leftValue = leftValue;
		this.leftValueSize = fontKindSize;
	}

	/**
	 * Définit la valeur de gauche avec la taille par défaut
	 * 
	 * @param leftValue
	 *            la valeur
	 */
	public void setLeftValue(String leftValue) {
		setLeftValue(leftValue, FontSizeKind.LARGE);
	}

	/**
	 * Définit le titre de droite avec la taille spécifiée
	 * 
	 * @param rigthTitle
	 *            le titre
	 * @param fontKindSize
	 *            le type de taille
	 */
	public void setRightTitle(String rigthTitle, FontSizeKind fontKindSize) {
		this.rigthTitle = rigthTitle;
		this.rigthTitleSize = fontKindSize;
	}

	/**
	 * Définit le titre de droite avec la taille par défaut
	 * 
	 * @param mainTitle
	 *            le titre
	 */
	public void setRightTitle(String rigthTitle) {
		setRightTitle(rigthTitle, FontSizeKind.SMALL);
	}

	/**
	 * Définit la valeur de droite avec la taille spécifiée
	 * 
	 * @param rigthValue
	 *            la valeur
	 * @param fontKindSize
	 *            le type de taille
	 */
	public void setRightValue(String rigthValue, FontSizeKind fontKindSize) {
		this.rigthValue = rigthValue;
		this.rigthValueSize = fontKindSize;
	}

	/**
	 * Définit la valeur de droite avec la taille par défaut
	 * 
	 * @param rigthValue
	 *            la valeur
	 */
	public void setRightValue(String rigthValue) {
		setRightValue(rigthValue, FontSizeKind.LARGE);
	}

	/**
	 * Définit la chaine de la commande de gauche avec la taille spécifiée
	 * 
	 * @param mainTitle
	 *            le titre
	 * @param fontKindSize
	 *            la type de taille
	 */
	public void setLeftCmd(String leftCmd, ActionListener l, FontSizeKind fontKindSize) {
		leftCmdActionListener = l;
		this.leftCmd = leftCmd;
		this.leftCmdSize = fontKindSize;
		this.leftCmdArea.setBounds(0, 0, 0, 0);
	}

	/**
	 * Définit la chaine de la commande de gauche avec la taille par défaut
	 * 
	 * @param mainTitle
	 *            le titre
	 */
	public void setLeftCmd(String leftCmd, ActionListener l) {
		setLeftCmd(leftCmd, l, FontSizeKind.INTERMEDIATE);
	}

	/**
	 * Définit la chaine de la commande de droite avec la taille spécifiée
	 * 
	 * @param mainTitle
	 *            le titre
	 * @param fontKindSize
	 *            la type de taille
	 */
	public void setRightCmd(String rigthCmd, ActionListener l, FontSizeKind fontKindSize) {
		rigthCmdActionListener = l;
		this.rigthCmd = rigthCmd;
		this.rigthCmdSize = fontKindSize;
		this.rigthCmdArea.setBounds(0, 0, 0, 0);
	}

	/**
	 * Définit la chaine de la commande de droite avec la taille par défaut
	 * 
	 * @param mainTitle
	 *            le titre
	 */
	public void setRightCmd(String rigthCmd, ActionListener l) {
		setRightCmd(rigthCmd, l, FontSizeKind.INTERMEDIATE);
	}

	/**
	 * Définit l'icone centrale
	 * 
	 * @param icone
	 *            le nom de l'icone
	 */
	public void setCenterIcone(String icone, ActionListener l) {
		if (getClass().getResource("/images/" + icone + ".png") != null) {
			iconeCmdActionListener = l;
			this.icone = icone;
		} else {
			this.icone = "";
			oldHeigth = -1;
			imageIcone = null;
			this.iconeCmdArea.setBounds(0, 0, 0, 0);
		}
	}

	@Override
	public void clearMessage() {
		this.setCenterIcone("", null);
		this.setMainTitle("");
		this.setLeftTitle("");
		this.setLeftValue("");
		this.setRightTitle("");
		this.setRightValue("");
		this.setLeftCmd("", null);
		this.setRightCmd("", null);
	}
}
