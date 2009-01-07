package ihm.smartPhone.component;

import ihm.smartPhone.IGoIhmSmartPhone;
import ihm.smartPhone.libPT.MouseListenerClickAndMoveInArea;
import ihm.smartPhone.libPT.PanelDoubleBufferingSoftwear;
import ihm.smartPhone.tools.CodeExecutor;
import ihm.smartPhone.tools.ImageLoader;
import ihm.smartPhone.tools.SizeAdapteur.FontSizeKind;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LowerBar extends AbstractBar {

	private static final long serialVersionUID = 1L;
	protected int oldHeigth = -1;

	protected GradientPaint gradientPaint = null;

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

	protected String leftCenteredCmd;
	protected FontSizeKind leftCenteredCmdSize;
	protected Rectangle leftCenteredCmdArea = null;
	protected ActionListener leftCenteredCmdActionListener = null;

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
		leftCenteredCmdArea = new Rectangle();
		rigthCmdArea = new Rectangle();
		if (demo) {
			setCenterIcone("home", null);
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
		l.addInteractiveArea(iconeCmdArea, new CodeExecutor() {
			@Override
			public void execute() {
				iconeCmdActionListener.actionPerformed(new ActionEvent(me, 0, ""));
			}
		});
		l.addInteractiveArea(leftCmdArea, new CodeExecutor() {
			@Override
			public void execute() {
				leftCmdActionListener.actionPerformed(new ActionEvent(me, 0, ""));
			}
		});
		l.addInteractiveArea(leftCenteredCmdArea, new CodeExecutor() {
			@Override
			public void execute() {
				leftCenteredCmdActionListener.actionPerformed(new ActionEvent(me, 0, ""));
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

		if (gradientPaint == null || gradientPaint.getColor1() != ihm.getSkin().getColorOutside()
				|| gradientPaint.getColor2() != ihm.getSkin().getColorInside())
			gradientPaint = new GradientPaint(0, getHeight(), ihm.getSkin().getColorOutside(), 0, 0, ihm.getSkin()
					.getColorInside());

		//dessin du dégradé
		((Graphics2D) buffer).setPaint(gradientPaint);
		buffer.fillRect(0, 0, getWidth(), getHeight());

		buffer.setColor(ihm.getSkin().getColorLetter());
		int roundRect = this.getWidth() >> 6;
		drawStrings(buffer, FontSizeKind.LARGE, roundRect);
		drawStrings(buffer, FontSizeKind.INTERMEDIATE, roundRect);
		drawStrings(buffer, FontSizeKind.SMALL, roundRect);
		if (icone.length() != 0) {
			if ((imageIcone == null) || (oldHeigth != this.getHeight())) {
				imageIcone = ImageLoader.getRessourcesImageIcone(icone, this.getWidth(), this.getHeight() - 2)
						.getImage();
			}
			iconeCmdArea.setBounds(this.getWidth() - imageIcone.getWidth(null) >> 1, 1, imageIcone.getHeight(null),
					imageIcone.getWidth(null));
			buffer.drawImage(imageIcone, iconeCmdArea.x, iconeCmdArea.y, null);
			oldHeigth = this.getHeight();
		}
	}

	/**
	 * Ecriture de chacun de chaque message qui a pour police, le type passé en paramètre.
	 * 
	 * @param g
	 *            le Graphics où les chaines seront dessinés
	 * @param fontSizeKind
	 *            le type de le police pour cette affichage
	 */
	protected void drawStrings(Graphics g, final FontSizeKind fontSizeKind, final int roundRect) {
		Font font = null;
		int[] xs;
		int[] ys;
		int hs, ws, hs23, ws11;
		Color colorFont = g.getColor();
		if (fontSizeKind == FontSizeKind.LARGE)
			font = ihm.getSizeAdapteur().getLargeFont();
		if (fontSizeKind == FontSizeKind.INTERMEDIATE)
			font = ihm.getSizeAdapteur().getIntermediateFont();
		if (fontSizeKind == FontSizeKind.SMALL)
			font = ihm.getSizeAdapteur().getSmallFont();
		if (font == null)
			return;
		g.setFont(font);

		if ((mainTitleSize == fontSizeKind) && (mainTitle != ""))
			g.drawString(mainTitle, this.getWidth() - getWidthString(mainTitle, g, font) >> 1, this.getHeight()
					+ getHeightString(mainTitle, g, font) >> 1);

		if ((leftTitleSize == fontSizeKind) && (leftTitle != ""))
			g.drawString(leftTitle, 0, getHeightString(leftTitle, g, font));

		if ((leftValueSize == fontSizeKind) && (leftValue != ""))
			g.drawString(leftValue, (this.getWidth() >> 2) - (getWidthString(leftValue, g, font) >> 1), this
					.getHeight()
					+ getHeightString(leftValue, g, font) >> 1);

		if ((rigthTitleSize == fontSizeKind) && (rigthTitle != ""))
			g.drawString(rigthTitle, this.getWidth() - getWidthString(rigthTitle, g, font), getHeightString(rigthTitle,
					g, font));

		if ((rigthValueSize == fontSizeKind) && (rigthValue != ""))
			g.drawString(rigthValue, 3 * (this.getWidth() >> 2) - (getWidthString(rigthValue, g, font) >> 1), this
					.getHeight()
					+ getHeightString(rigthValue, g, font) >> 1);

		if ((leftCenteredCmdSize == fontSizeKind) && (leftCenteredCmd != "")) {
			ws11 = (ws = getWidthString(leftCenteredCmd, g, font)) << 1;
			hs = getHeightString(leftCenteredCmd, g, font);
			// hs23 = hs + (hs >> 2);
			g.setColor(ihm.getSkin().getColorInside());
			leftCenteredCmdArea.setBounds((this.getWidth() >> 2) - (ws11 >> 1), (this.getHeight() - hs) >> 1, ws11, hs
					+ (hs >> 2));
			g.fillRoundRect(leftCenteredCmdArea.x, leftCenteredCmdArea.y, leftCenteredCmdArea.width,
					leftCenteredCmdArea.height, ihm.getSizeAdapteur().getSizeSmallFont() >> 1, ihm.getSizeAdapteur()
							.getSizeSmallFont() >> 1);
			g.setColor(ihm.getSkin().getColorLine());
			g.drawRoundRect(leftCenteredCmdArea.x, leftCenteredCmdArea.y, leftCenteredCmdArea.width,
					leftCenteredCmdArea.height, ihm.getSizeAdapteur().getSizeSmallFont() >> 1, ihm.getSizeAdapteur()
							.getSizeSmallFont() >> 1);
			g.setColor(ihm.getSkin().getColorLetter());
			g.drawString(leftCenteredCmd, (this.getWidth() >> 2) - (ws >> 1), this.getHeight() + hs >> 1);
			// g.setColor(ihm.getSkin().getColorInside());
			// hs = getHeightString(leftCenteredCmd, g, font);
			// hs11 = (int) (hs * 0.667);
			// ws11 = (int) (getWidthString(leftCenteredCmd, g, font) * 1.1);
			// xs = new int[] { hs23 + 1, hs + ws11, hs + ws11, hs23 + 1, 1 };
			// ys = new int[] { (this.getHeight() >> 1) - hs23, (this.getHeight() >> 1) - hs23,
			// (this.getHeight() >> 1) + hs23 + 1, (this.getHeight() >> 1) + hs23 + 1, this.getHeight() >> 1 };
			// leftCenteredCmdArea.setBounds(xs[0], ys[0], xs[2] - xs[0], ys[2] - ys[0]);
			// g.fillPolygon(xs, ys, xs.length);
			// g.setColor(ihm.getSkin().getColorLine());
			// ys[2]--;
			// ys[3]--;
			// g.drawPolygon(xs, ys, xs.length);
			// g.setColor(colorFont);
			// g.drawString(leftCenteredCmd, hs + 1, this.getHeight() + hs >> 1);
		}

		if ((leftCmdSize == fontSizeKind) && (leftCmd != "")) {
			g.setColor(ihm.getSkin().getColorInside());
			hs = getHeightString(leftCmd, g, font);
			hs23 = hs + (hs >> 1) + (hs >> 2);
			ws = getWidthString(leftCmd, g, font);
			ws11 = ws + (roundRect << 2) + roundRect;
			leftCmdArea.setBounds(roundRect, (this.getHeight() - hs23 >> 1), ws11, hs23);
			g.fillRoundRect(leftCmdArea.x + 1, leftCmdArea.y + 1, leftCmdArea.width - 1, leftCmdArea.height - 1,
					roundRect, roundRect);
			g.setColor(ihm.getSkin().getColorLine());
			g.drawRoundRect(leftCmdArea.x, leftCmdArea.y, leftCmdArea.width, leftCmdArea.height, roundRect, roundRect);
			g.setColor(colorFont);
			xs = new int[3];
			xs[0] = leftCmdArea.x + roundRect;
			xs[1] = xs[0] + (roundRect << 1);
			xs[2] = xs[0] + (roundRect << 1);
			ys = new int[3];
			ys[0] = leftCmdArea.y + (leftCmdArea.height >> 1) + 1;
			ys[1] = ys[0] - roundRect;
			ys[2] = ys[0] + roundRect;
			System.out.println(leftCmdArea.y);
			System.out.println(ys[0]);
			System.out.println(leftCmdArea.y + leftCmdArea.height);
			g.fillPolygon(xs, ys, 3);
			g.drawString(leftCmd, leftCmdArea.x + (roundRect << 2), leftCmdArea.y + (leftCmdArea.height + hs >> 1));
		}


		if ((rigthCmdSize == fontSizeKind) && (rigthCmd != "")) {
			g.setColor(ihm.getSkin().getColorInside());
			hs = getHeightString(rigthCmd, g, font);
			hs23 = hs + (hs >> 1) + (hs >> 2);
			ws = getWidthString(rigthCmd, g, font);
			ws11 = ws + (roundRect << 2) + roundRect;
			rigthCmdArea.setBounds(this.getWidth() - roundRect-ws11, (this.getHeight() - hs23 >> 1), ws11, hs23);
			g.fillRoundRect(rigthCmdArea.x + 1, rigthCmdArea.y + 1, rigthCmdArea.width - 1, rigthCmdArea.height - 1,
					roundRect, roundRect);
			g.setColor(ihm.getSkin().getColorLine());
			g.drawRoundRect(rigthCmdArea.x, rigthCmdArea.y, rigthCmdArea.width, rigthCmdArea.height, roundRect, roundRect);
			g.setColor(colorFont);
			xs = new int[3];
			xs[0] = rigthCmdArea.x +rigthCmdArea.width- roundRect;
			xs[1] = xs[0] - (roundRect << 1);
			xs[2] = xs[0] - (roundRect << 1);
			ys = new int[3];
			ys[0] = rigthCmdArea.y + (rigthCmdArea.height >> 1)+1;
			ys[1] = ys[0] - roundRect;
			ys[2] = ys[0] + roundRect;
			System.out.println(rigthCmdArea.y);
			System.out.println(ys[0]);
			System.out.println(rigthCmdArea.y + rigthCmdArea.height);
			g.fillPolygon(xs, ys, 3);
			g.drawString(rigthCmd, rigthCmdArea.x +  roundRect  , rigthCmdArea.y + (rigthCmdArea.height + hs >> 1));
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
	 * @param fontSizeKind
	 *            le type de taille
	 */
	public void setMainTitle(String mainTitle, FontSizeKind fontSizeKind) {
		this.mainTitle = mainTitle;
		this.mainTitleSize = fontSizeKind;
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
	 * @param fontSizeKind
	 *            le type de taille
	 */
	public void setLeftTitle(String leftTitle, FontSizeKind fontSizeKind) {
		this.leftTitle = leftTitle;
		this.leftTitleSize = fontSizeKind;
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
	 * @param fontSizeKind
	 *            le type de taille
	 */
	public void setLeftValue(String leftValue, FontSizeKind fontSizeKind) {
		this.leftValue = leftValue;
		this.leftValueSize = fontSizeKind;
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
	 * @param fontSizeKind
	 *            le type de taille
	 */
	public void setRightTitle(String rigthTitle, FontSizeKind fontSizeKind) {
		this.rigthTitle = rigthTitle;
		this.rigthTitleSize = fontSizeKind;
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
	 * @param fontSizeKind
	 *            le type de taille
	 */
	public void setRightValue(String rigthValue, FontSizeKind fontSizeKind) {
		this.rigthValue = rigthValue;
		this.rigthValueSize = fontSizeKind;
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
	 * @param fontSizeKind
	 *            la type de taille
	 */
	public void setLeftCmd(String leftCmd, ActionListener l, FontSizeKind fontSizeKind) {
		leftCmdActionListener = l;
		this.leftCmd = leftCmd;
		this.leftCmdSize = fontSizeKind;
		this.leftCmdArea.setBounds(0, 0, 0, 0);
	}

	/**
	 * Définit la chaine de la commande de gauche avec la taille spécifiée
	 * 
	 * @param mainTitle
	 *            le titre
	 * @param fontSizeKind
	 *            la type de taille
	 */
	public void setLeftCenteredCmd(String leftCenteredCmd, ActionListener l, FontSizeKind fontSizeKind) {
		leftCenteredCmdActionListener = l;
		this.leftCenteredCmd = leftCenteredCmd;
		this.leftCenteredCmdSize = fontSizeKind;
		this.leftCenteredCmdArea.setBounds(0, 0, 0, 0);
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
	 * Définit la chaine de la commande de gauche avec la taille par défaut
	 * 
	 * @param mainTitle
	 *            le titre
	 */
	public void setLeftCenteredCmd(String leftCenteredCmd, ActionListener l) {
		setLeftCenteredCmd(leftCenteredCmd, l, FontSizeKind.INTERMEDIATE);
	}

	/**
	 * Définit la chaine de la commande de droite avec la taille spécifiée
	 * 
	 * @param mainTitle
	 *            le titre
	 * @param fontSizeKind
	 *            la type de taille
	 */
	public void setRightCmd(String rigthCmd, ActionListener l, FontSizeKind fontSizeKind) {
		rigthCmdActionListener = l;
		this.rigthCmd = rigthCmd;
		this.rigthCmdSize = fontSizeKind;
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
		lookDraw.acquireUninterruptibly();
		if (getClass().getResource("/images/" + icone + ".png") != null) {
			iconeCmdActionListener = l;
			if (this.icone.compareToIgnoreCase(icone) != 0)
				imageIcone = null;
			this.icone = icone;
		} else {
			this.icone = "";
			imageIcone = null;
			oldHeigth = -1;
			this.iconeCmdArea.setBounds(0, 0, 0, 0);
		}
		lookDraw.release();
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
		this.setLeftCenteredCmd("", null);
		this.setRightCmd("", null);
	}
}
