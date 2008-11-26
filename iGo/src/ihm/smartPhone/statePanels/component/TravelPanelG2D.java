package ihm.smartPhone.statePanels.component;

import ihm.smartPhone.component.PanelDoubleBufferingSoftwearG2D;
import ihm.smartPhone.interfaces.TravelForTravelPanel;
import ihm.smartPhone.listener.MouseListenerClickAndMoveInArea;
import ihm.smartPhone.statePanels.IhmReceivingPanelState;
import ihm.smartPhone.statePanels.IhmReceivingStates;
import ihm.smartPhone.tools.CodeExecutor;
import ihm.smartPhone.tools.ImageLoader;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.Semaphore;

public class TravelPanelG2D extends PanelDoubleBufferingSoftwearG2D {

	private static final long serialVersionUID = 1L;

	protected Font fontInter = null;
	protected Font fontSmall = null;
	protected TravelForTravelPanel travel;
	protected static byte instance = 0;
	protected static IhmReceivingPanelState father = null;
	protected static Semaphore mutex = new Semaphore(1);
	protected static Dimension preferredSize = null;
	protected static Image imageFav = null;
	protected static Image imageNoFav = null;
	protected static Rectangle favArea = null;
	protected static Image imageEdit = null;
	protected static Rectangle editArea = null;
	protected static Image imageDel = null;
	protected static Rectangle delArea = null;
	protected static Rectangle allArea = null;
	protected boolean insideMe;

	// protected Graphics2D buffer;

	/**
	 * Surcharge de la méthode finalize afin de comptabilisé le nombre d'instance encore présentes en mémoire. Si
	 * l'instance qui disparait est la denière alors on met toutes les instance statique à null, de cette façon on
	 * permet au Gargabe Collector de récupérer la mémoire qu'on n'a pour l'instant plus besoin.
	 */
	@Override
	protected void finalize() throws Throwable {
		try {
			mutex.acquire();
		} catch (InterruptedException e1) {
		}
		if (--instance == 0) {
			System.out.println("Stopping the TravelPanels");
			father = null;
			preferredSize = null;
			imageFav = null;
			imageNoFav = null;
			favArea = null;
			imageEdit = null;
			editArea = null;
			imageDel = null;
			delArea = null;
			allArea = null;
		}
		mutex.release();
		super.finalize();
	}

	/**
	 * Constructeur de TravelPanel, il a besoin de connaitre le trajet qu'il va décire, et l'IhmReceivingPanelState afin
	 * d'obtenir des informations comme les tailles et les couleurs. Si le nombre d'instance présente en mémoire est à 0
	 * alors on va initialisé l'ensemble des variables statique.
	 * 
	 * @param nvTravel
	 *            le trajet qu'il va décrire. Si nvTravel est à null, on jete un NullPointerException
	 * @param ihmFather
	 *            l'IhmReceivingPanelState qui l'accueil
	 */
	public TravelPanelG2D(TravelForTravelPanel nvTravel, IhmReceivingPanelState ihmFather) {
		super();
		TravelPanelG2D.father = ihmFather;
		this.travel = nvTravel;
		if (travel == null)
			throw new NullPointerException(
					"A travelPanel have been created with a TravelForTravelPanel at null. It's strictly forbidden.");
		try {
			mutex.acquire();
		} catch (InterruptedException e1) {
		}
		if (instance++ == 0) {
			System.out.println("Starting the TravelPanels");
			preferredSize = new Dimension(10, 10);
			favArea = new Rectangle();
			editArea = new Rectangle();
			delArea = new Rectangle();
			allArea = new Rectangle(0, 0, 0, 0);
		}
		mutex.release();
		this.setBackground(father
				.getSkin()
				.getColorSubAreaInside());

		MouseListenerClickAndMoveInArea l = new MouseListenerClickAndMoveInArea(this);
		l.addInteractiveArea(favArea, new CodeExecutor() {
			@Override
			public void execute() {
				travel.setFavorite(!travel.isFavorite());
				me.repaint();
			}
		});
		l.addInteractiveArea(editArea, new CodeExecutor() {
			@Override
			public void execute() {
				System.out.println("edit");
			}
		});
		l.addInteractiveArea(delArea, new CodeExecutor() {
			@Override
			public void execute() {
				System.out.println("del");
				Container parent = me.getParent();
				parent.remove(me);
				parent.doLayout();
			}
		});
		l.addInteractiveArea(allArea, new CodeExecutor() {
			@Override
			public void execute() {
				System.out.println("go!!!");
				father.setActualState(IhmReceivingStates.PREVISU_TRAVEL);
			}
		}, false);
		this.addMouseListener(l);
		this.addMouseMotionListener(l);
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				insideMe = true;
				me.repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				insideMe = false;
				me.repaint();
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});
	}

	/**
	 * On surcharge cette méthode afin d'avoir la panel à la taille que l'on souhaite. La hauteur est alors définit à 4
	 * fois la taille de la petite police + la taille de la police intermédiare
	 * 
	 * @return la taille préféré de l'objet
	 */
	@Override
	public Dimension getPreferredSize() {
		preferredSize.setSize(10, father.getSizeAdapteur().getSizeIntermediateFont()
				+ father.getSizeAdapteur().getSizeSmallFont() * 4);
		return preferredSize;
	}

	@Override
	public void paint(Graphics g) {
		int x, nextX, i;
		String tmp1, tmp2;
		/***************************************************************************************************************
		 * On regarde si la taille à changer, dans ce cas on modifie le buffer en conséquence
		 */
		if ((buffer == null) || (image.getWidth(null) != getWidth()) || (image.getHeight(null) != getHeight())) {
			image = createImage(getWidth(), getHeight());
			buffer = (Graphics2D)image.getGraphics();
			buffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			buffer.setColor(father.getSkin().getColorLetter());
			fontInter = father.getSizeAdapteur().getIntermediateFont();
			fontSmall = father.getSizeAdapteur().getSmallFont();
		}

		/***************************************************************************************************************
		 * on voit à recharger les images utils à cette classes;
		 */
		imageLoader(this.getWidth(), this.getHeight());

		buffer.clearRect(0, 0, this.getWidth(), this.getHeight());
		buffer.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
		if (insideMe)
			buffer.drawRect(1, 1, this.getWidth() - 3, this.getHeight() - 3);

		/***************************************************************************************************************
		 * Dessin du champs départ et arrivée
		 */
		buffer.setFont(fontInter);
		x = father.getSizeAdapteur().getSizeIntermediateFont() + father.getSizeAdapteur().getSizeSmallFont();
		nextX = 0;
		buffer.drawString(travel.getName(), x, father.getSizeAdapteur().getSizeIntermediateFont()
				+ (father.getSizeAdapteur().getSizeSmallFont() >> 1));

		buffer.setFont(fontSmall);
		buffer.drawString(father.lg("From") + " :", x, father.getSizeAdapteur().getSizeIntermediateFont()
				+ father.getSizeAdapteur().getSizeSmallFont() * 2);
		i = this.getWidthString(father.lg("From") + " : ", g, fontSmall) + x;
		if (nextX < i)
			nextX = i;
		buffer.drawString(father.lg("To") + " :", x, father.getSizeAdapteur().getSizeIntermediateFont()
				+ (father.getSizeAdapteur().getSizeSmallFont() * 7  >>1));
		i = this.getWidthString(father.lg("To") + " : ", g, fontSmall) + x;
		if (nextX < i)
			nextX = i;

		/***************************************************************************************************************
		 * Dessin des gares de départ et d'arrivée
		 */
		x = nextX;
		buffer.drawString(travel.getOrigine(), x, father.getSizeAdapteur().getSizeIntermediateFont()
				+ father.getSizeAdapteur().getSizeSmallFont() * 2);
		buffer.drawString(travel.getDestination(), x, father.getSizeAdapteur().getSizeIntermediateFont()
				+ (father.getSizeAdapteur().getSizeSmallFont() * 7 >> 1));

		/***************************************************************************************************************
		 * Dessin du coût et du temps
		 */
		tmp1 = travel.getTotalCost() + " " + father.lg("Money");
		tmp2 = decomposeMinutesIntoHourMinutes(travel.getTotalTime(), father.lg("LetterForHour"), father
				.lg("LetterForMinute"));

		x = /* editArea.x/ */getWidth()/**/- father.getSizeAdapteur().getSizeSmallFont();
		nextX = x - this.getWidthString(tmp1, g, fontSmall);

		i = x - this.getWidthString(tmp2, g, fontSmall);
		if (i < nextX)
			x = i;
		else
			x = nextX;

		buffer.drawString(tmp1, x, father.getSizeAdapteur().getSizeIntermediateFont()
				+ father.getSizeAdapteur().getSizeSmallFont() * 2);
		buffer.drawString(tmp2, x, father.getSizeAdapteur().getSizeIntermediateFont()
				+ (father.getSizeAdapteur().getSizeSmallFont() * 7 >> 1));

		/***************************************************************************************************************
		 * Dessin du champs coût et temps
		 */
		tmp1 = father.lg("Cost") + " : ";
		tmp2 = father.lg("Time") + " : ";
		nextX = x - father.getSizeAdapteur().getSizeSmallFont();
		i = x - getWidthString(tmp1, g, fontSmall);
		if (i < nextX)
			nextX = i;
		i = x - getWidthString(tmp2, g, fontSmall);
		if (i < nextX)
			x = i;
		else
			x = nextX;

		buffer.drawString(tmp1, x, father.getSizeAdapteur().getSizeIntermediateFont()
				+ father.getSizeAdapteur().getSizeSmallFont() * 2);
		buffer.drawString(tmp2, x, father.getSizeAdapteur().getSizeIntermediateFont()
				+ (father.getSizeAdapteur().getSizeSmallFont() * 7 >>1));

		/***************************************************************************************************************
		 * Dessin des images
		 */
		if (travel.isFavorite())
			buffer.drawImage(imageFav, favArea.x, favArea.y, null);
		else
			buffer.drawImage(imageNoFav, favArea.x, favArea.y, null);
		buffer.drawImage(imageEdit, editArea.x, editArea.y, null);
		buffer.drawImage(imageDel, delArea.x, delArea.y, null);

		/***************************************************************************************************************
		 * dessin du nouvelle affichage
		 */
		g.drawImage(image, 0, 0, this);
	}

	/**
	 * Procédure static permettant de charger les images de façon économique : on considère que ces objet auront tous la
	 * même taille, les images incluses à l'interrieur auront donc la même taille. on charge alors une unique fois
	 * chaque image, et on laisse toutes les instances de la classe les lire.
	 * 
	 * @param father
	 *            l'interface qui accueil les instances
	 * @param thisDotGetWidth
	 *            la largueur d'une des instances, c'est à dire la hauteur de toute les instances
	 * @param thisDotGetHeight
	 *            la hauteur d'une des instances, c'est à dire la hauteur de toute les instances
	 */
	protected static void imageLoader(int thisDotGetWidth, int thisDotGetHeight) {
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			return;
		}
		if ((imageEdit == null) || (imageEdit.getWidth(null) != father.getSizeAdapteur().getSizeIntermediateFont())) {
			favArea.setBounds(father.getSizeAdapteur().getSizeSmallFont()  >>1, father.getSizeAdapteur()
					.getSizeSmallFont()  >>1, father.getSizeAdapteur().getSizeIntermediateFont(), father
					.getSizeAdapteur().getSizeIntermediateFont());
			imageFav = ImageLoader.getRessourcesImageIcone("fav", father.getSizeAdapteur().getSizeIntermediateFont(),
					father.getSizeAdapteur().getSizeIntermediateFont()).getImage();
			imageNoFav = ImageLoader.getRessourcesImageIcone("fav-no",
					father.getSizeAdapteur().getSizeIntermediateFont(),
					father.getSizeAdapteur().getSizeIntermediateFont()).getImage();
			imageEdit = ImageLoader.getRessourcesImageIcone("button_config",
					father.getSizeAdapteur().getSizeIntermediateFont(),
					father.getSizeAdapteur().getSizeIntermediateFont()).getImage();
			imageDel = ImageLoader.getRessourcesImageIcone("button_cancel",
					father.getSizeAdapteur().getSizeIntermediateFont(),
					father.getSizeAdapteur().getSizeIntermediateFont()).getImage();
		}
		if (thisDotGetWidth != allArea.width) {
			editArea.setBounds(thisDotGetWidth - father.getSizeAdapteur().getSizeSmallFont()
					- father.getSizeAdapteur().getSizeIntermediateFont() * 2, father.getSizeAdapteur()
					.getSizeSmallFont()  >>1, father.getSizeAdapteur().getSizeIntermediateFont(), father
					.getSizeAdapteur().getSizeIntermediateFont());
			delArea.setBounds(thisDotGetWidth - (father.getSizeAdapteur().getSizeSmallFont()  >>1)
					- father.getSizeAdapteur().getSizeIntermediateFont(),
					father.getSizeAdapteur().getSizeSmallFont()  >>1,
					father.getSizeAdapteur().getSizeIntermediateFont(), father.getSizeAdapteur()
							.getSizeIntermediateFont());
			allArea.setSize(thisDotGetWidth, thisDotGetHeight);

		}
		mutex.release();
	}
}
