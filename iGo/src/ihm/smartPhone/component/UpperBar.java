package ihm.smartPhone.component;

import ihm.smartPhone.IGoIhmSmartPhone;
import ihm.smartPhone.libPT.MouseListenerClickAndMoveInArea;
import ihm.smartPhone.libPT.PanelDoubleBufferingSoftwear;
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

public class UpperBar extends AbstractBar {

	private static final long serialVersionUID = 1L;
	protected int oldHeigth = -1;

	protected String icone;
	protected Image imageIcone;
	protected String mainTitle;
	protected FontSizeKind mainTitleSize;
	protected String upperTitle;
	protected FontSizeKind upperTitleSize;
	protected String leftSubTitle;
	protected FontSizeKind leftSubTitleSize;
	protected String rigthSubTitle;
	protected FontSizeKind rigthSubTitleSize;

	protected String leftCmd;
	protected FontSizeKind leftCmdSize;
	protected Rectangle leftCmdArea = null;
	protected ActionListener leftCmdActionListener = null;

	protected String leftRecCmd;
	protected FontSizeKind leftRecCmdSize;
	protected Rectangle leftRecCmdArea = null;
	protected ActionListener leftRecCmdActionListener = null;

	protected String rigthCmd;
	protected FontSizeKind rigthCmdSize;
	protected Rectangle rigthCmdArea = null;
	protected ActionListener rigthCmdActionListener = null;

	/**
	 * Constructeur basique de l'objet. L'option demo permet d'initialisé chaque champs à son nom.
	 * 
	 * @param ihm
	 *            l'IGoIhmSmartPhone qui est le parent
	 * @param demo
	 *            true si on veux initialiser chaque champs à son nom, permet ainsi de maitrisé facilement l'objet et
	 *            ses multpile champs
	 */
	public UpperBar(IGoIhmSmartPhone ihm, boolean demo) {
		super(ihm);
		leftCmdArea = new Rectangle();
		leftRecCmdArea = new Rectangle();
		rigthCmdArea = new Rectangle();
		if (demo) {
			setCenterIcone("iGo");
			setMainTitle("mainTitle");
			setUpperTitle("upperTitle");
			setLeftCmd("leftCmd", null);
			setLeftSubTitle("leftSubTitle");
			setRightCmd("rigthCmd", null);
			setRightSubTitle("rigthSubTitle");
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
		l.addInteractiveArea(leftRecCmdArea, new CodeExecutor() {
			@Override
			public void execute() {
				leftRecCmdActionListener.actionPerformed(new ActionEvent(me, 0, ""));
			}
		});
		l.addInteractiveArea(rigthCmdArea, new CodeExecutor() {
			@Override
			public void execute() {
				rigthCmdActionListener.actionPerformed(new ActionEvent(me, 0, ""));
			}
		});
		this.addMouseListener(l);
		this.addMouseMotionListener(l);
	}

	@Override
	public void draw() {

		/***
		 * Gestion du buffer mémoire
		 */
		if (currentQuality != PanelDoubleBufferingSoftwear.getQuality()) {
			currentQuality = PanelDoubleBufferingSoftwear.getQuality();
			buffer = null;
			imageIcone = null;
		}
		if ((buffer == null) || (image.getWidth(null) != getWidth()) || (image.getHeight(null) != getHeight())) {
			image = createImage(getWidth(), getHeight());
			buffer = image.getGraphics();
			graphicsTunning(buffer);
		} else {
			buffer.setColor(ihm.getSkin().getColorInside());
			buffer.fillRect(0, 0, getWidth(), getHeight());
		}

		int outsideR = ihm.getSkin().getColorOutside().getRed();
		int outsideG = ihm.getSkin().getColorOutside().getGreen();
		int outsideB = ihm.getSkin().getColorOutside().getBlue();
		int deltaR = ihm.getSkin().getColorInside().getRed() - outsideR;
		int deltaG = ihm.getSkin().getColorInside().getGreen() - outsideG;
		int deltaB = ihm.getSkin().getColorInside().getBlue() - outsideB;
		float prog;
		for (int i = 0; i < this.getHeight(); i++) {
			prog = (float) i / this.getHeight();
			buffer.setColor(new Color(outsideR + (int) (deltaR * prog), outsideG + (int) (deltaG * prog), outsideB
					+ (int) (deltaB * prog)));
			buffer.drawLine(0, i, this.getWidth(), i);
		}

		if (icone != "") {
			if (oldHeigth != getHeight())
				imageIcone = ImageLoader.getRessourcesImageIcone(icone, this.getWidth(), this.getHeight() - 2)
						.getImage();
			buffer.drawImage(imageIcone, this.getWidth() - imageIcone.getWidth(null) >> 1, 1, null);
			oldHeigth = getHeight();
		}

		buffer.setColor(ihm.getSkin().getColorLetter());
		drawStrings(buffer, FontSizeKind.LARGE);
		drawStrings(buffer, FontSizeKind.INTERMEDIATE);
		drawStrings(buffer, FontSizeKind.SMALL);
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

		if ((mainTitleSize == fontKindSize) && (mainTitle != "")) {
			int heigthTmp;
			// if (leftCmd != "" || rigthCmd != "")
			heigthTmp = -getWidthString(leftCmd + rigthCmd, g);
			heigthTmp += heigthTmp >> 3;
			heigthTmp += getWidth() - (getWidth() >> 3);
			String[] cut = decoupeChaine(mainTitle, g, heigthTmp);
			heigthTmp = getHeightString(mainTitle, g);
			int heigth = this.getHeight() - heigthTmp * cut.length - (heigthTmp * (cut.length - 1) >> 2) >> 1;
			if (cut.length > 1 && upperTitle != "")
				heigth += (heigthTmp >> 2);
			for (String tmp : cut) {
				g.drawString(tmp, getWidth() - getWidthString(tmp, g) >> 1, heigth + heigthTmp);
				heigth += (heigthTmp >> 2) + heigthTmp;
			}
			// g.drawString(mainTitle, this.getWidth() - getWidthString(mainTitle, g, font) >> 1, this.getHeight()
			// + getHeightString(mainTitle, g, font) >> 1);
		}

		if ((upperTitleSize == fontKindSize) && (upperTitle != ""))
			g.drawString(upperTitle, this.getWidth() - getWidthString(upperTitle, g, font) >> 1, getHeightString(
					upperTitle, g, font));

		if ((leftSubTitleSize == fontKindSize) && (leftSubTitle != ""))
			g.drawString(leftSubTitle, 0, this.getHeight() - 1);

		if ((rigthSubTitleSize == fontKindSize) && (rigthSubTitle != ""))
			g.drawString(rigthSubTitle, this.getWidth() - getWidthString(rigthSubTitle, g, font), this.getHeight() - 1);

		if ((leftCmdSize == fontKindSize) && (leftCmd != "")) {
			g.setColor(ihm.getSkin().getColorInside());
			hs = getHeightString(leftCmd, g, font);
			hs23 = (hs >> 1) + (hs >> 2) - (hs >> 3) + (hs >> 4);
			ws = getWidthString(leftCmd, g, font);
			ws11 = ws + (ws >> 3);
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

		if ((leftRecCmdSize == fontKindSize) && (leftRecCmd != "")) {
			g.setColor(ihm.getSkin().getColorInside());
			hs = getHeightString(leftRecCmd, g, font);
			hs23 = (hs >> 1) + (hs >> 2) - (hs >> 3) + (hs >> 4);
			ws = getWidthString(leftRecCmd, g, font);
			ws11 = ws + ((ws >> 4) << 1);
			leftRecCmdArea.setBounds(3, (this.getHeight() >> 1) - hs23, ws11, hs23 << 1);
			g.fillRect(leftRecCmdArea.x, leftRecCmdArea.y, leftRecCmdArea.width, leftRecCmdArea.height);
			g.setColor(ihm.getSkin().getColorLine());
			g.drawRect(leftRecCmdArea.x, leftRecCmdArea.y, leftRecCmdArea.width, leftRecCmdArea.height);
			g.setColor(colorFont);
			g.drawString(leftRecCmd, (ws >> 4) + 3, this.getHeight() + hs >> 1);
		}

		if ((rigthCmdSize == fontKindSize) && (rigthCmd != "")) {
			g.setColor(ihm.getSkin().getColorInside());
			hs = getHeightString(rigthCmd, g, font);
			hs23 = (hs >> 1) + (hs >> 2) - (hs >> 3) + (hs >> 4);
			ws = getWidthString(rigthCmd, g, font);
			ws11 = ws + (ws >> 3);
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
	 *            la type de taille
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
		setMainTitle(mainTitle, FontSizeKind.LARGE);
	}

	/**
	 * Définit le titre supérieur avec la taille spécifiée
	 * 
	 * @param mainTitle
	 *            le titre
	 * @param fontKindSize
	 *            la type de taille
	 */
	public void setUpperTitle(String upperTitle, FontSizeKind fontKindSize) {
		this.upperTitle = upperTitle;
		this.upperTitleSize = fontKindSize;
	}

	/**
	 * Définit le titre supérieur avec la taille par défaut
	 * 
	 * @param mainTitle
	 *            le titre
	 */
	public void setUpperTitle(String upperTitle) {
		setUpperTitle(upperTitle, FontSizeKind.INTERMEDIATE);
	}

	/**
	 * Définit le sous titre de gauche avec la taille spécifiée
	 * 
	 * @param mainTitle
	 *            le titre
	 * @param fontKindSize
	 *            la type de taille
	 */
	public void setLeftSubTitle(String leftSubTitle, FontSizeKind fontKindSize) {
		this.leftSubTitle = leftSubTitle;
		this.leftSubTitleSize = fontKindSize;
	}

	/**
	 * Définit le sous titre de gauche avec la taille par défaut
	 * 
	 * @param mainTitle
	 *            le titre
	 */
	public void setLeftSubTitle(String leftSubTitle) {
		setLeftSubTitle(leftSubTitle, FontSizeKind.INTERMEDIATE);
	}

	/**
	 * Définit le sous titre de droite avec la taille spécifiée
	 * 
	 * @param mainTitle
	 *            le titre
	 * @param fontKindSize
	 *            la type de taille
	 */
	public void setRightSubTitle(String rigthSubTitle, FontSizeKind fontKindSize) {
		this.rigthSubTitle = rigthSubTitle;
		this.rigthSubTitleSize = fontKindSize;
	}

	/**
	 * Définit le sous titre de droite avec la taille par défaut
	 * 
	 * @param mainTitle
	 *            le titre
	 */
	public void setRightSubTitle(String rigthSubTitle) {
		setRightSubTitle(rigthSubTitle, FontSizeKind.INTERMEDIATE);
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
	 * Définit la chaine de la commande de gauche avec la taille spécifiée
	 * 
	 * @param mainTitle
	 *            le titre
	 * @param fontKindSize
	 *            la type de taille
	 */
	public void setLeftRecCmd(String leftRecCmd, ActionListener l, FontSizeKind fontKindSize) {
		leftRecCmdActionListener = l;
		this.leftRecCmd = leftRecCmd;
		this.leftRecCmdSize = fontKindSize;
		this.leftRecCmdArea.setBounds(0, 0, 0, 0);
	}

	/**
	 * Définit la chaine de la commande de gauche avec la taille par défaut
	 * 
	 * @param mainTitle
	 *            le titre
	 */
	public void setLeftRecCmd(String leftRecCmd, ActionListener l) {
		setLeftRecCmd(leftRecCmd, l, FontSizeKind.INTERMEDIATE);
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
	public void setCenterIcone(String icone) {
		lookDraw.acquireUninterruptibly();
		if (getClass().getResource("/images/" + icone + ".png") != null) {
			if (this.icone.compareToIgnoreCase(icone) != 0)
				imageIcone = null;
			this.icone = icone;
		} else {
			this.icone = "";
			oldHeigth = -1;
			imageIcone = null;
		}
		lookDraw.release();
	}

	@Override
	public void clearMessage() {
		this.setMainTitle("");
		this.setUpperTitle("");
		this.setLeftSubTitle("");
		this.setRightSubTitle("");
		this.setLeftCmd("", null);
		this.setLeftRecCmd("", null);
		this.setRightCmd("", null);
		this.setCenterIcone("");
	}
}
