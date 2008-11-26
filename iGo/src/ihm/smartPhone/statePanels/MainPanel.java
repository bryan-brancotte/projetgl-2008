package ihm.smartPhone.statePanels;

import ihm.smartPhone.component.LowerBar;
import ihm.smartPhone.component.UpperBar;
import ihm.smartPhone.listener.MouseListenerClickAndMoveInArea;
import ihm.smartPhone.tools.CodeExecutor;
import ihm.smartPhone.tools.ImageLoader;
import ihm.smartPhone.tools.SizeAdapteur.FontSizeKind;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends PanelState {

	protected Image imageNew = null;
	protected Rectangle imageNewArea = null;
	protected Image imageLoad = null;
	protected Rectangle imageLoadArea = null;
	protected Image imageSettings = null;
	protected Rectangle imageSettingsArea = null;
	protected Image imageFavorites = null;
	protected Rectangle imageFavoritesArea = null;
	protected Font font = null;

	private static final long serialVersionUID = 1L;

	/**
	 * L'unique constructeur de cette classe. On a besoin l'ihm qui l'heberge afin d'afficher les bonnes couleurs via le
	 * getSkin() et les bonnes tailles via le getSizeAdapteur().
	 * 
	 * @param ihm
	 *            l'ihm réceptrice
	 * @param upperBar
	 *            la barre supérieur
	 * @param lowerBar
	 *            la barre inférieur
	 */
	public MainPanel(IhmReceivingPanelState ihm, UpperBar upperBar, LowerBar lowerBar) {
		super(ihm, upperBar, lowerBar);
		imageNewArea = new Rectangle();
		imageLoadArea = new Rectangle();
		imageSettingsArea = new Rectangle();
		imageFavoritesArea = new Rectangle();

		MouseListenerClickAndMoveInArea l = new MouseListenerClickAndMoveInArea(this);
		l.addInteractiveArea(imageNewArea, new CodeExecutor() {
			@Override
			public void execute() {
				father.setActualState(IhmReceivingStates.NEW_TRAVEL);
			}
		});
		l.addInteractiveArea(imageLoadArea, new CodeExecutor() {
			@Override
			public void execute() {
				father.setActualState(IhmReceivingStates.LOAD_TRAVEL);
			}
		});
		l.addInteractiveArea(imageSettingsArea, new CodeExecutor() {
			@Override
			public void execute() {
				father.setActualState(IhmReceivingStates.SETTINGS);
			}
		});
		l.addInteractiveArea(imageFavoritesArea, new CodeExecutor() {
			@Override
			public void execute() {
				father.setActualState(IhmReceivingStates.FAVORITES);
			}
		});
		this.addMouseListener(l);
		this.addMouseMotionListener(l);
	}

	/**
	 * Surdéfinition de la fonction de redessinement de l'objet.
	 */
	public void paint(Graphics g) {
		int miniWidth = this.getWidth() >> 2;
		int miniHeight = this.getHeight() >> 2;
		int halfImagesWidth;
		int twoThirdImagesHeight;
		String msg;
		/***************************************************************************************************************
		 * 
		 */
		if ((buffer == null) || (image.getWidth(null) != getWidth()) || (image.getHeight(null) != getHeight())) {
			image = createImage(getWidth(), getHeight());
			buffer = image.getGraphics();
			buffer.setFont(font = father.getSizeAdapteur().getIntermediateFont());
			buffer.setColor(father.getSkin().getColorLetter());

			if ((imageNew == null) || (miniWidth == imageNewArea.getWidth())
					|| (miniHeight == imageNewArea.getHeight()))
				imageNew = ImageLoader.getRessourcesImageIcone("mainNew", miniWidth, miniHeight).getImage();
			halfImagesWidth = imageNew.getWidth(null) >> 1;
			twoThirdImagesHeight = (int) (imageNew.getHeight(null) * 0.667);
			imageNewArea.setBounds(miniWidth - halfImagesWidth, miniHeight - twoThirdImagesHeight, imageNew
					.getWidth(null), imageNew.getHeight(null));

			if ((imageLoad == null) || (miniWidth == imageLoadArea.getWidth())
					|| (miniHeight == imageLoadArea.getHeight()))
				imageLoad = ImageLoader.getRessourcesImageIcone("mainLoad", miniWidth, miniHeight).getImage();
			imageLoadArea.setBounds(miniWidth * 3 - halfImagesWidth, imageNewArea.y, imageNewArea.width,
					imageNewArea.height);

			if ((imageSettings == null) || (miniWidth == imageSettingsArea.getWidth())
					|| (miniHeight == imageSettingsArea.getHeight()))
				imageSettings = ImageLoader.getRessourcesImageIcone("mainSettings", miniWidth, miniHeight).getImage();
			imageSettingsArea.setBounds(imageNewArea.x, miniHeight * 3 - imageNew.getHeight(null), imageNewArea.width,
					imageNewArea.height);

			if ((imageFavorites == null) || (miniWidth == imageFavoritesArea.getWidth())
					|| (miniHeight == imageFavoritesArea.getHeight()))
				imageFavorites = ImageLoader.getRessourcesImageIcone("mainFavorites", miniWidth, miniHeight).getImage();
			imageFavoritesArea.setBounds(imageLoadArea.x, imageSettingsArea.y, imageNewArea.width, imageNewArea.height);
		} else {
			halfImagesWidth = imageNew.getWidth(null) >> 1;
			twoThirdImagesHeight = (int) (imageNew.getHeight(null) * 0.667);
		}
		/***************************************************************************************************************
		 * Menu "New"
		 */
		buffer.drawImage(imageNew, imageNewArea.x, imageNewArea.y, null);
		msg = father.lg("New");
		buffer.drawString(msg, miniWidth - (getWidthString(msg, buffer, font) >> 1), miniHeight + twoThirdImagesHeight
				/ 2 + getHeigthString(msg, buffer, font));

		/***************************************************************************************************************
		 * Menu "Load"
		 */
		buffer.drawImage(imageLoad, imageLoadArea.x, imageLoadArea.y, null);
		msg = father.lg("MainLoad");
		buffer.drawString(msg, miniWidth * 3 - (getWidthString(msg, buffer, font) >> 1), miniHeight
				+ (twoThirdImagesHeight >> 1) + getHeigthString(msg, buffer, font));

		/***************************************************************************************************************
		 * Menu "Settings"
		 */
		buffer.drawImage(imageSettings, imageSettingsArea.x, imageSettingsArea.y, null);
		msg = father.lg("Settings");
		buffer.drawString(msg, miniWidth - (getWidthString(msg, buffer, font) >> 1), miniHeight * 3
				+ getHeigthString(msg, buffer, font));

		/***************************************************************************************************************
		 * Menu "Favorites"
		 */
		buffer.drawImage(imageFavorites, imageFavoritesArea.x, imageFavoritesArea.y, null);
		msg = father.lg("Favorites");
		buffer.drawString(msg, miniWidth * 3 - (getWidthString(msg, buffer, font) >> 1), miniHeight * 3
				+ getHeigthString(msg, buffer, font));

		/***************************************************************************************************************
		 * dessin du nouvelle affichage
		 */
		g.drawImage(image, 0, 0, this);

	}

	@Override
	public void giveControle() {
		upperBar.clearMessage();
		upperBar.setCenterIcone("iGo");
		upperBar.setRightSubTitle(father.lg("iGoVersion"), FontSizeKind.SMALL);
		upperBar.repaint();

		lowerBar.clearMessage();
		lowerBar.setCenterIcone("quit", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				father.stop();
			}
		});
		lowerBar.repaint();
	}
}
