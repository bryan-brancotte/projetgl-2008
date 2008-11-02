package ihm.smartPhone.statePanel;

import ihm.smartPhone.composants.LowerBar;
import ihm.smartPhone.composants.UpperBar;
import ihm.smartPhone.listener.MouseListenerClickAndMoveInArea;
import ihm.smartPhone.listener.MouseListenerClickAndMoveInArea.CodeExecutor;
import ihm.smartPhone.tools.ImageLoader;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

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
				JOptionPane.showMessageDialog(null, "it could be great? yeah, but it's not avaible yet...");
			}
		});
		this.addMouseListener(l);
		this.addMouseMotionListener(l);
	}

	/**
	 * Surdéfinition de la fonction de redessinement de l'objet.
	 */
	public void paint(Graphics g) {
		int miniWidth = this.getWidth() / 4;
		int miniHeight = this.getHeight() / 4;
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
			
			imageNew = ImageLoader.getRessourcesImageIcone("mainNew", miniWidth, miniHeight).getImage();
			halfImagesWidth = imageNew.getWidth(null) / 2;
			twoThirdImagesHeight = imageNew.getHeight(null) * 2 / 3;
			imageNewArea.setBounds(miniWidth - halfImagesWidth, miniHeight - twoThirdImagesHeight, imageNew
					.getWidth(null), imageNew.getHeight(null));
			
			imageLoad = ImageLoader.getRessourcesImageIcone("mainLoad", miniWidth, miniHeight).getImage();
			imageLoadArea.setBounds(miniWidth * 3 - halfImagesWidth, imageNewArea.y, imageNewArea.width,
					imageNewArea.height);
			
			imageSettings = ImageLoader.getRessourcesImageIcone("mainSettings", miniWidth, miniHeight).getImage();
			imageSettingsArea.setBounds(imageNewArea.x, miniHeight * 3 - imageNew.getHeight(null), imageNewArea.width,
					imageNewArea.height);
			
			imageFavorites = ImageLoader.getRessourcesImageIcone("mainFavorites", miniWidth, miniHeight).getImage();
			imageFavoritesArea.setBounds(imageLoadArea.x, imageSettingsArea.y, imageNewArea.width,
					imageNewArea.height);
		} else {
			halfImagesWidth = imageNew.getWidth(null) / 2;
			twoThirdImagesHeight = imageNew.getHeight(null) * 2 / 3;
		}
		/***************************************************************************************************************
		 * Menu "New"
		 */
		buffer.drawImage(imageNew, imageNewArea.x, imageNewArea.y, null);
		msg = father.lg("New");
		buffer.drawString(msg, miniWidth - getWidthString(msg, buffer, font) / 2, miniHeight + twoThirdImagesHeight / 2
				+ getHeigthString(msg, buffer, font));

		/***************************************************************************************************************
		 * Menu "Load"
		 */
		buffer.drawImage(imageLoad, imageLoadArea.x, imageLoadArea.y, null);
		msg = father.lg("Load");
		buffer.drawString(msg, miniWidth * 3 - getWidthString(msg, buffer, font) / 2, miniHeight + twoThirdImagesHeight
				/ 2 + getHeigthString(msg, buffer, font));

		/***************************************************************************************************************
		 * Menu "Settings"
		 */
		buffer.drawImage(imageSettings, imageSettingsArea.x, imageSettingsArea.y, null);
		msg = father.lg("Settings");
		buffer.drawString(msg, miniWidth - getWidthString(msg, buffer, font) / 2, miniHeight * 3
				+ getHeigthString(msg, buffer, font));

		/***************************************************************************************************************
		 * Menu "Favorites"
		 */
		buffer.drawImage(imageFavorites, imageFavoritesArea.x, imageFavoritesArea.y, null);
		msg = father.lg("Favorites");
		buffer.drawString(msg, miniWidth * 3 - getWidthString(msg, buffer, font) / 2, miniHeight * 3
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
