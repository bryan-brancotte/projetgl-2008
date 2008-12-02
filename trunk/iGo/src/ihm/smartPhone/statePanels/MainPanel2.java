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

public class MainPanel2 extends PanelState2 {

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
	public MainPanel2(IhmReceivingPanelState ihm, UpperBar upperBar, LowerBar lowerBar) {
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
		if (font == null)
			this.displayableAreaResized(g);
		int miniWidth = this.getWidth() >> 2;
		int miniHeight = this.getHeight() >> 2;
		int miniSide = (miniWidth > miniHeight) ? miniHeight : miniWidth;
		int twoThirdImagesHeight=(int) (miniSide * 0.667);
		String msg;

		/***************************************************************************************************************
		 * Menu "New"
		 */
		g.drawImage(imageNew, imageNewArea.x, imageNewArea.y, null);
		msg = father.lg("New");
		g.drawString(msg, miniWidth - (getWidthString(msg, g, font) >> 1), miniHeight + twoThirdImagesHeight
				/ 2 + getHeigthString(msg, g, font));

		/***************************************************************************************************************
		 * Menu "Load"
		 */
		g.drawImage(imageLoad, imageLoadArea.x, imageLoadArea.y, null);
		msg = father.lg("MainLoad");
		g.drawString(msg, miniWidth * 3 - (getWidthString(msg, g, font) >> 1), miniHeight
				+ (twoThirdImagesHeight >> 1) + getHeigthString(msg, g, font));

		/***************************************************************************************************************
		 * Menu "Settings"
		 */
		g.drawImage(imageSettings, imageSettingsArea.x, imageSettingsArea.y, null);
		msg = father.lg("Settings");
		g.drawString(msg, miniWidth - (getWidthString(msg, g, font) >> 1), miniHeight * 3
				+ getHeigthString(msg, g, font));

		/***************************************************************************************************************
		 * Menu "Favorites"
		 */
		g.drawImage(imageFavorites, imageFavoritesArea.x, imageFavoritesArea.y, null);
		msg = father.lg("Favorites");
		g.drawString(msg, miniWidth * 3 - (getWidthString(msg, g, font) >> 1), miniHeight * 3
				+ getHeigthString(msg, g, font));

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

	@Override
	public void displayableAreaResized(Graphics g) {
		System.out.println("displayableAreaResized");
		int miniWidth = this.getWidth() >> 2;
		int miniHeight = this.getHeight() >> 2;
		int miniSide = (miniWidth > miniHeight) ? miniHeight : miniWidth;
		int halfImagesWidth=miniSide >> 1;
		int twoThirdImagesHeight=(int) (miniSide * 0.667);
		// System.out.println(getWidth());
		g.setFont(font = father.getSizeAdapteur().getIntermediateFont());
		g.setColor(father.getSkin().getColorLetter());

		if ((imageNew == null) || (miniHeight != imageNewArea.getWidth())
				|| (miniHeight != imageNewArea.getHeight()))
			imageNew = ImageLoader.getRessourcesImageIcone("mainNew", miniSide, miniSide).getImage();
		//halfImagesWidth = miniSide >> 1;
		//twoThirdImagesHeight = (int) (miniSide * 0.667);
		imageNewArea.setBounds(miniWidth - halfImagesWidth, miniHeight - twoThirdImagesHeight, miniSide, miniSide);

		if ((imageLoad == null) || (miniHeight != imageLoadArea.getWidth())
				|| (miniHeight != imageLoadArea.getHeight()))
			imageLoad = ImageLoader.getRessourcesImageIcone("mainLoad", miniSide, miniSide).getImage();
		imageLoadArea.setBounds(miniWidth * 3 - halfImagesWidth, imageNewArea.y, imageNewArea.width,
				imageNewArea.height);

		if ((imageSettings == null) || (miniHeight != imageSettingsArea.getWidth())
				|| (miniHeight != imageSettingsArea.getHeight()))
			imageSettings = ImageLoader.getRessourcesImageIcone("mainSettings", miniSide, miniSide).getImage();
		imageSettingsArea.setBounds(imageNewArea.x, miniHeight * 3 - miniSide, imageNewArea.width,
				imageNewArea.height);

		if ((imageFavorites == null) || (miniHeight != imageFavoritesArea.getWidth())
				|| (miniHeight != imageFavoritesArea.getHeight()))
			imageFavorites = ImageLoader.getRessourcesImageIcone("mainFavorites", miniSide, miniSide).getImage();
		imageFavoritesArea.setBounds(imageLoadArea.x, imageSettingsArea.y, imageNewArea.width, imageNewArea.height);
	}
}
