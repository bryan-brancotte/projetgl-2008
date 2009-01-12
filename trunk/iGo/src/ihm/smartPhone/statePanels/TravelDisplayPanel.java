package ihm.smartPhone.statePanels;

import ihm.smartPhone.component.LowerBar;
import ihm.smartPhone.component.UpperBar;
import ihm.smartPhone.interfaces.TravelForDisplayPanel;
import ihm.smartPhone.libPT.MouseListenerClickAndMoveInArea;
import ihm.smartPhone.libPT.PanelDoubleBufferingSoftwear;
import ihm.smartPhone.tools.AbsolutLayout;
import ihm.smartPhone.tools.CodeExecutor;
import ihm.smartPhone.tools.CodeExecutor1P;
import ihm.smartPhone.tools.CodeExecutor2P;
import ihm.smartPhone.tools.ImageLoader;
import ihm.smartPhone.tools.SizeAdapteur.FontSizeKind;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;

public abstract class TravelDisplayPanel extends PanelState {

	/**
	 * ...
	 */
	private static final long serialVersionUID = -2413655590628034148L;
	protected TravelDisplayPanel me = this;
	protected TravelForDisplayPanel travel = null;
	protected IhmReceivingStates currentState = IhmReceivingStates.PREVISU_TRAVEL;

	protected static Image imageWarning;
	protected Rectangle iconeWarningArea = null;
	// protected MouseListenerClickAndMoveInArea clickAndMoveWarningAndArray;

	protected Rectangle changeStateArea = null;

	protected PopUpMessage popUpMessage;

	protected int sizeLargeFont = 1;
	/**
	 * Les composants d'un trajet favorit
	 */
	protected static Image imageFav = null;
	protected static Image imageNoFav = null;
	protected static Image imageMode = null;
	protected Rectangle iconeFavArea = null;

	// protected MouseListenerClickAndMoveInArea clickAndMoveWarningAndArray;

	public TravelDisplayPanel(IhmReceivingPanelState ihm, UpperBar nvUpperBar, LowerBar nvLowerBar,
			TravelForDisplayPanel travelForDisplayPanel) {
		super(ihm, nvUpperBar, nvLowerBar);
		this.travel = travelForDisplayPanel;
		// if (travel == null)
		// travel = new TravelForTravelPanelExemple();
		sizeLargeFont = father.getSizeAdapteur().getSizeLargeFont()
				+ (father.getSizeAdapteur().getSizeLargeFont() >> 1);
		if (imageWarning == null)
			imageWarning = ImageLoader.getRessourcesImageIcone("warning", sizeLargeFont, sizeLargeFont).getImage();
		iconeWarningArea = new Rectangle(0, 0, 0, 0);
		iconeFavArea = new Rectangle(0, 0, 0, 0);
		changeStateArea = new Rectangle(0, 0, 0, 0);

		MouseListenerClickAndMoveInArea clickAndMoveWarningAndArray = new MouseListenerClickAndMoveInArea(this);
		clickAndMoveWarningAndArray.addInteractiveArea(iconeWarningArea, new CodeExecutor1P<PanelState>(this) {
			@Override
			public void execute() {
				// System.out.println("warning");
				Container c = this.origine.getParent();
				c.removeAll();
				ListingEvent listingPanel = new ListingEvent(father, upperBar, lowerBar,
						new CodeExecutor2P<Container, PanelState>(c, this.origine) {
							public void execute() {
								this.origineA.removeAll();
								this.origineA.add(this.origineB);
								this.origineA.validate();
								this.origineB.giveControle();
							};
						});

				c.add(listingPanel);
				listingPanel.giveControle();
				c.validate();
				// popUpMessage.define("World of dégout", "C'est la question que l'on peut se poser après que 2D Boy, "
				// + "développeur du génialissime World of Goo, ait déclaré que 90 % "
				// + "des personnes qui y ont joué l'ont fait sur une version pirate. "
				// + "On ne sait trop comment le studio est parvenu à ce chiffre, mais "
				// + "ce qui est certain, c'est qu'il témoigne d'un véritable malaise "
				// + "dans le milieu du jeu PC. En l'occurrence, ni le manque d'originalité, "
				// + "ni le prix de vente (de seulement 20 dollars), ni la présence d'un "
				// + "quelconque système de protection rédhibitoire ne peuvent être invoqués "
				// + "pour justifier de se procurer le jeu par des voies détournées..."
				//
				// , null);
				// me.repaint();
			}
		});
		clickAndMoveWarningAndArray.addInteractiveArea(iconeFavArea, new CodeExecutor() {
			@Override
			public void execute() {
				travel.setFavorite(!travel.isFavorite());
				repaint();
			}
		});
		clickAndMoveWarningAndArray.addInteractiveArea(changeStateArea, new CodeExecutor() {
			@Override
			public void execute() {
				imageMode = null;
				actionToDoWhenChangeStateIsClicked();
			}
		});
		this.addMouseListener(clickAndMoveWarningAndArray);
		this.addMouseMotionListener(clickAndMoveWarningAndArray);
		popUpMessage = new PopUpMessage(this);
	}

	public void setCurrentState(IhmReceivingStates actualState) {
		if ((actualState == IhmReceivingStates.EXPERIMENT_TRAVEL)
				&& (this.currentState == IhmReceivingStates.PREVISU_TRAVEL)) {
			this.currentState = actualState;
			startStationDone();
			giveControle();
		}
	}

	/**
	 * Les classes implémentante donneront ainsi le contenue qu'elle veullent mettre dans le bouton en bas à droite, qui
	 * est normalement utilisé pour passé d'un mode à l'autre
	 * 
	 * @return le message à afficher.
	 */
	@Deprecated
	protected abstract String getMessageChangeState();

	/**
	 * Les classes implémentante donneront ainsi le contenue qu'elle veullent mettre comme icone pour passé d'un mode à
	 * l'autre
	 * 
	 * @return l'icone à afficher
	 */
	protected abstract String getIconeChangeState();

	/**
	 * Action qui sera effectuée lorsque l'on cliquera sur le bouton.
	 */
	protected abstract void actionToDoWhenChangeStateIsClicked();

	/**
	 * Action qui sera effectuée lorsque l'utilisateur demand à passé au tronçon suivant
	 */
	protected abstract void nextStationDone();

	/**
	 * Action qui sera effectuée lorsque l'utilisateur demarre le parcourt
	 */
	protected abstract void startStationDone();

	@Override
	public void paint(Graphics g) {
		if (currentQuality != PanelDoubleBufferingSoftwear.getQuality()) {
			currentQuality = PanelDoubleBufferingSoftwear.getQuality();
			imageWarning = null;
			imageFav = null;
			imageNoFav = null;
			imageMode = null;
		}
		sizeLargeFont = father.getSizeAdapteur().getSizeLargeFont()
				+ (father.getSizeAdapteur().getSizeLargeFont() >> 2);
		if (imageMode == null || imageMode.getHeight(null) != (sizeLargeFont)) {
			imageMode = ImageLoader.getRessourcesImageIcone(getIconeChangeState(), sizeLargeFont, sizeLargeFont)
					.getImage();
		}
		if (imageWarning == null || imageNoFav == null || imageWarning.getHeight(null) != (sizeLargeFont)) {
			imageWarning = ImageLoader.getRessourcesImageIcone("warning", sizeLargeFont, sizeLargeFont).getImage();
			imageFav = ImageLoader.getRessourcesImageIcone("fav", sizeLargeFont, sizeLargeFont).getImage();
			imageNoFav = ImageLoader.getRessourcesImageIcone("fav-no", sizeLargeFont, sizeLargeFont).getImage();
		}
		iconeFavArea.setBounds(1, getHeight() - sizeLargeFont, sizeLargeFont, sizeLargeFont);
		if (travel.isFavorite())
			g.drawImage(imageFav, iconeFavArea.x, iconeFavArea.y, null);
		else
			g.drawImage(imageNoFav, iconeFavArea.x, getHeight() - sizeLargeFont, null);
		iconeWarningArea.setBounds(iconeFavArea.x, getHeight() - sizeLargeFont - sizeLargeFont, sizeLargeFont, sizeLargeFont);
		g.drawImage(imageWarning, iconeFavArea.x, iconeWarningArea.y, null);
		changeStateArea.setBounds(iconeFavArea.x, getHeight() - sizeLargeFont * 3, sizeLargeFont, sizeLargeFont);
		g.drawImage(imageMode, iconeFavArea.x, changeStateArea.y, null);
		g.setFont(father.getSizeAdapteur().getSmallFont());
		if (popUpMessage.isActiveMessage()) {
			popUpMessage.paint(g);
		}
	}

	public void displayPopUpMessage(String title, String message, CodeExecutor actionAfterOkButton) {
		popUpMessage.define(title, message, actionAfterOkButton);
		repaint();
	}

	@Override
	public void giveControle() {
		if (travel == null) {
			father.setErrorState(father.lg("ERROR_Impossible"), father.lg("ERROR_ReturnNullTravelDetails"));
			return;
		}
		upperBar.clearMessage();
		if (currentState == IhmReceivingStates.PREVISU_TRAVEL) {
			upperBar.setLeftCmd(father.lg("Edit"), new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					father.setCurrentState(IhmReceivingStates.EDIT_TRAVEL, travel.getPath());
				}
			});
			upperBar.setRightCmd(father.lg("Start"), new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					me.setCurrentState(IhmReceivingStates.EXPERIMENT_TRAVEL);
				}
			});
			upperBar.setUpperTitle(travel.getOrigine(), FontSizeKind.LARGE);
			upperBar.setMainTitle(father.lg("To"), FontSizeKind.INTERMEDIATE);
			upperBar.setLowerTitle(travel.getDestination(), FontSizeKind.LARGE);
		} else {
			upperBar.setLeftRecCmd(father.lg("Lost"), new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					father.setCurrentState(IhmReceivingStates.LOST_IN_TRAVEL, travel.getPathClone()
							.getCurrentPathInGraph());
				}
			});
			upperBar.setUpAndDownAtRightCmd((travel.hasPrevious()) ? new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					nextStationDone();
					travel.previous();
					giveControle();
				}
			} : null, (travel.hasNext()) ? new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					nextStationDone();
					travel.next();
					giveControle();
				}
			} : null);
			upperBar.setLowerTitle("");
			if (travel.hasNext()) {
				upperBar.setUpperTitle(father.lg("NextStop"));
				upperBar.setMainTitle(travel.getNextStop());
			} else
				upperBar.setMainTitle(father.lg("EndOfTravel"));
		}
		upperBar.repaint();

		lowerBar.clearMessage();
		if (currentState == IhmReceivingStates.PREVISU_TRAVEL) {
			lowerBar.setCenterIcone("home", new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					father.setCurrentState(IhmReceivingStates.MAIN_INTERFACE);
				}
			});
			// lowerBar.setLeftTitle(father.lg("TotalCost"));
			// lowerBar.setRightTitle(father.lg("TotalTime"));
			lowerBar.setLeftValue(travel.getTotalCost() + father.lg("Money"));
			lowerBar.setRightValue(decomposeMinutesIntoHourMinutes(travel.getTotalTime(), father.lg("LetterForHour"),
					father.lg("LetterForMinute")));
		} else {
			// lowerBar.setLeftCenteredCmd(father.lg("Lost"), new ActionListener() {
			// @Override
			// public void actionPerformed(ActionEvent e) {
			// father.setCurrentState(IhmReceivingStates.LOST_IN_TRAVEL, travel.getPath());
			// }
			// });
			lowerBar.setCenterIcone("home", new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// if (true || JOptionPane.showConfirmDialog(me, father.lg("DoYouWantToQuitYourActualTravel"),
					// father
					// .lg("ProgName"), JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
					father.setCurrentState(IhmReceivingStates.MAIN_INTERFACE);
				}
			});
			// lowerBar.setRightTitle(father.lg("RemainingTime"));
			lowerBar.setRightValue(decomposeMinutesIntoHourMinutes(travel.getRemainingTime(), father
					.lg("LetterForHour"), father.lg("LetterForMinute")));
		}
		lowerBar.repaint();
		this.requestFocus();
		this.repaint();
	}

	protected class PopUpMessage {
		protected String message = "";
		protected String title = "";
		protected CodeExecutor actionAfterOkButton;
		protected boolean activeMessage = false;
		protected MouseListenerClickAndMoveInArea l;
		protected Rectangle imageButtonOkArea;
		protected MouseListener[] ml;
		protected MouseMotionListener[] mml;
		protected MouseWheelListener[] mwl;
		protected Image imageButtonOk = null;

		/**
		 * Constructeur d'un PopUpMessage, on précise le panel qui l'accueil afin de controler les zones clicables dans
		 * ce panel. Par défaut le message n'est pas dans l'état "à afficher".
		 * 
		 * @param panelParent
		 *            le panel qui accueil le message
		 */
		public PopUpMessage(TravelDisplayPanel panelParent) {
			super();
			this.l = new MouseListenerClickAndMoveInArea(me);
			this.imageButtonOkArea = new Rectangle(0, 0, 0, 0);
			this.l.addInteractiveArea(imageButtonOkArea, new CodeExecutor() {
				@Override
				public void execute() {
					System.out.println("ok of message");
					me.removeMouseListener(l);
					me.removeMouseMotionListener(l);
					for (MouseListener m : ml)
						me.addMouseListener(m);
					for (MouseMotionListener m : mml)
						me.addMouseMotionListener(m);
					for (MouseWheelListener m : mwl)
						me.addMouseWheelListener(m);
					if (actionAfterOkButton != null)
						actionAfterOkButton.execute();
					me.requestFocus();
					activeMessage = false;
					// scrollPane.setVisible(false);
					me.repaint();
				}
			});
			panelParent.setLayout(new AbsolutLayout());
		}

		/**
		 * Permet de savoir si le message est actif, i.e : s'il doit être affiché.
		 * 
		 * @return true si le message doit être affiché.
		 */
		public boolean isActiveMessage() {
			return activeMessage;
		}

		/**
		 * Définit le message avec les paamèter courant et le marque comme étant à afficher.
		 * 
		 * @param title
		 *            le titre du message. Aucun retour à la ligne n'est effecuté. ne mettez pas un tire trop long
		 * @param message
		 *            ce paramètre représent le messag à afficher. Il est mit dans un zone de texte qui gère le retour à
		 *            la ligne automatique, et la présence d'un barre de défilement si la texte ne rentre pas dans
		 *            l'esapce prévus.
		 * @param actionAfterOkButton
		 *            action effectué après pression du bouton OK. Dans tout les cas, la pression du bouton fera
		 *            disparaitre le message de l'écran. Vous pouvez ensuite ajouter de l'IHM change d'état.
		 */
		public void define(String title, String message, CodeExecutor actionAfterOkButton) {
			this.message = message;
			this.title = title;
			this.actionAfterOkButton = actionAfterOkButton;
			this.activeMessage = true;
			this.imageButtonOk = ImageLoader.getRessourcesImageIcone("button_ok", sizeLargeFont, sizeLargeFont)
					.getImage();
			ml = me.getMouseListeners();
			mml = me.getMouseMotionListeners();
			mwl = me.getMouseWheelListeners();
			for (MouseListener m : ml)
				me.removeMouseListener(m);
			for (MouseMotionListener m : mml)
				me.removeMouseMotionListener(m);
			for (MouseWheelListener m : mwl)
				me.removeMouseWheelListener(m);
			me.addMouseListener(l);
			me.addMouseMotionListener(l);
		}

		/**
		 * Fonction de dessin du message.
		 * 
		 * @param g
		 *            le Graphics sur lequel il doit dessiner
		 */
		public void paint(Graphics g) {
			int heigth;
			int left;
			int top;
			sizeLargeFont = father.getSizeAdapteur().getSizeLargeFont();
			if (imageButtonOk.getHeight(null) != sizeLargeFont)
				imageButtonOk = ImageLoader.getRessourcesImageIcone("button_ok", sizeLargeFont, sizeLargeFont)
						.getImage();
			g.setColor(father.getSkin().getColorSubAreaInside());
			top = (getHeight() >> 3);
			left = getWidth() >> 4;
			heigth = getHeight() - (top << 1);
			g.fillRect(left, top, getWidth() - (left << 1), heigth);
			g.setColor(father.getSkin().getColorLetter());
			g.drawRect(left, top, getWidth() - (left << 1), heigth);
			g.setFont(father.getSizeAdapteur().getLargeFont());
			top += sizeLargeFont + (sizeLargeFont >> 1);
			g.drawString(title, getWidth() - getWidthString(title, g) >> 1, top);

			g.setFont(father.getSizeAdapteur().getSmallFont());
			String[] cut = decoupeChaine(message, g, (getWidth() >> 1) + (getWidth() >> 2));
			top += sizeLargeFont >> 1;
			g.drawLine(0, top, 1000, top);
			int heigthTmp = getHeightString(message, g);
			// on décale le texte pour qu'il soit centré dans la fenetre.
			top += getHeight() - (top << 1) - heigthTmp * (cut.length + 1) >> 1;
			for (String tmp : cut) {
				g.drawString(tmp, getWidth() - getWidthString(tmp, g) >> 1, top + heigthTmp);
				top += heigthTmp + 1;
			}
			imageButtonOkArea.setBounds(getWidth() - left - sizeLargeFont - heigthTmp, getHeight() - (getHeight() >> 3)
					- sizeLargeFont - heigthTmp, sizeLargeFont, sizeLargeFont);
			g.drawImage(imageButtonOk, imageButtonOkArea.x, imageButtonOkArea.y, null);
		}

		public MouseListenerClickAndMoveInArea getClickAndMoveListener() {
			return null;
		}
	}
}