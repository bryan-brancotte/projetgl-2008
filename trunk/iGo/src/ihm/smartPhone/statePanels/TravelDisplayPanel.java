package ihm.smartPhone.statePanels;

import ihm.classesExemples.TravelForTravelPanelExemple;
import ihm.smartPhone.component.LowerBar;
import ihm.smartPhone.component.UpperBar;
import ihm.smartPhone.interfaces.TravelForDisplayPanel;
import ihm.smartPhone.listener.MouseListenerClickAndMoveInArea;
import ihm.smartPhone.tools.AbsolutLayout;
import ihm.smartPhone.tools.CodeExecutor;
import ihm.smartPhone.tools.ImageLoader;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public abstract class TravelDisplayPanel extends PanelState {

	protected TravelDisplayPanel me = this;
	protected TravelForDisplayPanel travel = null;
	protected IhmReceivingStates actualState = IhmReceivingStates.PREVISU_TRAVEL;

	protected static Image imageWarning;
	protected Rectangle iconeWarningArea = null;
	protected MouseListenerClickAndMoveInArea clickAndMoveWarningAndArray;

	protected Rectangle changeStateArea = null;

	protected PopUpMessage popUpMessage;

	protected int sizeLargeFont = 1;

	public TravelDisplayPanel(IhmReceivingPanelState ihm, UpperBar upperBar, LowerBar lowerBar,
			TravelForDisplayPanel travelForDisplayPanel) {
		super(ihm, upperBar, lowerBar);
		this.travel = travelForDisplayPanel;
		if (travel == null)
			travel = new TravelForTravelPanelExemple();
		sizeLargeFont = father.getSizeAdapteur().getSizeLargeFont();
		if (imageWarning == null)
			imageWarning = ImageLoader.getRessourcesImageIcone("warning", sizeLargeFont, sizeLargeFont).getImage();
		iconeWarningArea = new Rectangle(0, 0, 0, 0);
		changeStateArea = new Rectangle(0, 0, 0, 0);

		MouseListenerClickAndMoveInArea clickAndMoveWarningAndArray = new MouseListenerClickAndMoveInArea(this);
		clickAndMoveWarningAndArray.addInteractiveArea(iconeWarningArea, new CodeExecutor() {
			@Override
			public void execute() {
				// System.out.println("warning");
				popUpMessage.define("World of dégout", "C'est la question que l'on peut se poser après que 2D Boy, "
						+ "développeur du génialissime World of Goo, ait déclaré que 90 % "
						+ "des personnes qui y ont joué l'ont fait sur une version pirate. "
						+ "On ne sait trop comment le studio est parvenu à ce chiffre, mais "
						+ "ce qui est certain, c'est qu'il témoigne d'un véritable malaise "
						+ "dans le milieu du jeu PC. En l'occurrence, ni le manque d'originalité, "
						+ "ni le prix de vente (de seulement 20 dollars), ni la présence d'un "
						+ "quelconque système de protection rédhibitoire ne peuvent être invoqués "
						+ "pour justifier de se procurer le jeu par des voies détournées. "
						+ "Vivrions-nous donc dans une société de profiteurs, même pas fichus de "
						+ "respecter le travail débordant de créativité d'un petit studio indépendant ? "
						+ "C'est la question qu'on est en droit de se poser, sans compter que les "
						+ "partisans des DRM trouveront là matière à justifier des systèmes de "
						+ "protection de plus en plus lourds et de plus en plus contraignants. Il "
						+ "faudrait vraiment savoir ce que veut le joueur PC. 2D Boy précise que les "
						+ "ventes effectuées sur Wiiware et sur Steam sauveront heureusement le studio "
						+ "de la banqueroute, mais cela reste une bien triste nouvelle en ce vendredi après-midi.",
						null);
				me.repaint();
			}
		});
		clickAndMoveWarningAndArray.addInteractiveArea(changeStateArea, new CodeExecutor() {
			@Override
			public void execute() {
				actionToDoWhenChangeStateIsClicked();
			}
		});
		this.addMouseListener(clickAndMoveWarningAndArray);
		this.addMouseMotionListener(clickAndMoveWarningAndArray);
		popUpMessage = new PopUpMessage(this);
	}

	public void setActualState(IhmReceivingStates actualState) {
		if ((actualState == IhmReceivingStates.EXPERIMENT_TRAVEL) || (actualState == IhmReceivingStates.PREVISU_TRAVEL)) {
			this.actualState = actualState;
			giveControle();
		}
	}

	/**
	 * Les classes implémentante donneront ainsi le contenue qu'elle veullent mettre dans le bouton en bas à droite, qui
	 * est normalement utilisé pour passé d'un mode à l'autre
	 * 
	 * @return le message à afficher.
	 */
	protected abstract String getMessageChangeState();

	/**
	 * Action qui sera effectuée lorsque l'on cliquera sur le bouton.
	 */
	protected abstract void actionToDoWhenChangeStateIsClicked();

	@Override
	public void paint(Graphics g) {
		if (imageWarning.getHeight(null) != sizeLargeFont)
			imageWarning = ImageLoader.getRessourcesImageIcone("warning", sizeLargeFont, sizeLargeFont).getImage();
		g.drawImage(imageWarning, 0, getHeight() - imageWarning.getHeight(null), null);
		iconeWarningArea.setBounds(0, getHeight() - imageWarning.getHeight(null), imageWarning.getHeight(null),
				imageWarning.getWidth(null));
		g.setFont(father.getSizeAdapteur().getSmallFont());
		int w = me.getWidthString(getMessageChangeState(), g, g.getFont());
		int h = me.getHeigthString(getMessageChangeState(), g, g.getFont());
		g.setColor(father.getSkin().getColorSubAreaInside());
		changeStateArea.setBounds(getWidth() - (int) (w * 1.25) - 2, getHeight() - (int) (h * 1.25) - 2,
				(int) (w * 1.25), (int) (h * 1.25));
		g.fillRect(changeStateArea.x, changeStateArea.y, changeStateArea.width, changeStateArea.height);
		g.setColor(father.getSkin().getColorLetter());
		g.drawRect(changeStateArea.x, changeStateArea.y, changeStateArea.width, changeStateArea.height);
		g.drawString(getMessageChangeState(), changeStateArea.x + 2, getHeight() - (int) (h * 0.1667) - 2);
		// popUpMessage.define("", null);
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
		upperBar.clearMessage();
		if (actualState == IhmReceivingStates.PREVISU_TRAVEL) {
			upperBar.setLeftCmd(father.lg("Edit"), new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null, "Edit soon avaible...");
				}
			});
			upperBar.setRightCmd(father.lg("Start"), new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					me.setActualState(IhmReceivingStates.EXPERIMENT_TRAVEL);
					// father.setActualState(IhmReceivingStates.EXPERIMENT_TRAVEL);
				}
			});
			upperBar.setUpperTitle(father.lg("Destination"));
			upperBar.setMainTitle(travel.getDestination());
		} else {
			upperBar.setLeftCmd(father.lg("Lost"), new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null, "Lost soon avaible...");
				}
			});
			upperBar.setRightCmd(father.lg("Next"), new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null, "Next soon avaible...");
				}
			});
			upperBar.setUpperTitle(father.lg("NextStop"));
			upperBar.setMainTitle(travel.getNextStop());
		}
		upperBar.repaint();

		lowerBar.clearMessage();
		if (actualState == IhmReceivingStates.PREVISU_TRAVEL) {
			lowerBar.setCenterIcone("button_save", new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null, "Saving soon avaible...");
				}
			});
			lowerBar.setLeftTitle(father.lg("TotalCost"));
			lowerBar.setRightTitle(father.lg("TotalTime"));
			lowerBar.setLeftValue(travel.getTotalCost() + father.lg("Money"));
			lowerBar.setRightValue(decomposeMinutesIntoHourMinutes(travel.getTotalTime(), father.lg("LetterForHour"),
					father.lg("LetterForMinute")));
		} else {
			lowerBar.setCenterIcone("home", new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (true || JOptionPane.showConfirmDialog(me, father.lg("DoYouWantToQuitYourActualTravel"), father
							.lg("ProgName"), JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
						father.setActualState(IhmReceivingStates.MAIN_INTERFACE);
				}
			});
			lowerBar.setRightTitle(father.lg("RemainingTime"));
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
		// TODO utilisation de swing en 1.4???
		// protected TextArea textMessageArea;/*
		protected JTextArea textMessageArea;
		protected ScrollPane scrollPane;

		/**
		 * Constructeur d'un PopUpMessage, on précise le panel qui l'accueil afin de controler les zones clicables dans
		 * ce panel, mais aussi pour ajouter un composant dans ce panel. Ce panel doit être vide, et le changement du
		 * layout ne doit pas bous géner. par défaut le message n'est pas dans l'état "à afficher".
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
					scrollPane.setVisible(false);
					me.repaint();
				}
			});
			panelParent.setLayout(new AbsolutLayout());
			textMessageArea = new JTextArea();
			textMessageArea.setEditable(false);
			// textMessageArea.setWrapStyleWord(true);
			textMessageArea.setLineWrap(true);
			textMessageArea.setAutoscrolls(false);
			// textMessageArea.setAutoscrolls(true);
			// textMessageArea.setEnabled(false);
			// textMessageArea.setBackground(panelParent.getBackground());
			// textMessageArea.setVisible(false);
			// scrollPane.setLayout(new BorderLayout());
			scrollPane = new ScrollPane();// textMessageArea);
			Panel inside = new Panel(new BorderLayout(0, 0));
			inside.add(textMessageArea);
			scrollPane.add(inside);
			scrollPane.setVisible(false);
			panelParent.add(scrollPane);
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
			scrollPane.setVisible(true);
			scrollPane.setBounds((getWidth() >> 4) + 10, (int) (getHeight() * 0.35), (int) (getWidth() * 0.875 - 20),
					(int) (getHeight() * 0.35));

			// textMessageArea.setColumns(10);//
			// textMessageArea.getWidth()/father.getSizeAdapteur().getSizeIntermediateFont());
			// textMessageArea.setRows(20);
			textMessageArea.setText(message);
			// scrollPane.setBounds(0,0,10,10);
			// scrollPane.repaint();
			// textMessageArea.validate();
			// textMessageArea.getParent().validate();
			scrollPane.validate();
			// scrollPane.repaint();
			// textMessageArea.getParent().repaint();
			// textMessageArea.repaint();
			// scrollPane.doLayout();
			// textMessageArea.getParent().doLayout();
			// scrollPane.setScrollPosition(0,0);
			// scrollPane.repaint();
		}

		/**
		 * Fonction de dessin du message.
		 * 
		 * @param g
		 *            le Graphics sur lequel il doit dessiner
		 */
		public void paint(Graphics g) {
			sizeLargeFont = father.getSizeAdapteur().getSizeLargeFont();
			if (imageButtonOk.getHeight(null) != sizeLargeFont)
				imageButtonOk = ImageLoader.getRessourcesImageIcone("button_ok", sizeLargeFont, sizeLargeFont)
						.getImage();
			g.setColor(father.getSkin().getColorSubAreaInside());
			g.fillRect(getWidth() >> 4, (int) (getHeight() * 0.2), (int) (getWidth() * 0.875), (int) (getHeight() * 0.6));
			g.setColor(father.getSkin().getColorLetter());
			g.drawRect(getWidth() >> 4, (int) (getHeight() * 0.2), (int) (getWidth() * 0.875), (int) (getHeight() * 0.6));
			scrollPane.setBounds((getWidth() >> 4) + 10, (int) (getHeight() * 0.35), (int) (getWidth() * 0.875 - 20),
					(int) (getHeight() * 0.35));
			// textMessageArea.setFont(father.getSizeAdapteur().getSmallFont());/*
			textMessageArea.setFont(father.getSizeAdapteur().getIntermediateFont());/**/
			imageButtonOkArea.setBounds((getWidth() * 15 >> 4) - sizeLargeFont - 10, (int) (getHeight() * 0.8
					- sizeLargeFont - 10), sizeLargeFont, sizeLargeFont);
			// imageButtonOkArea.setBounds(getWidth() * 0.875, getHeight() * 0.8, sizeLargeFont, sizeLargeFont);
			g.drawImage(imageButtonOk, imageButtonOkArea.x, imageButtonOkArea.y, null);
			g.setFont(father.getSizeAdapteur().getLargeFont());
			g.drawString(title, getWidth() - getWidthString(title, g) >> 1,
					(int) (getHeight() * 0.275 + (getHeigthString(title, g) >> 1)));
			scrollPane.repaint();
		}

		public MouseListenerClickAndMoveInArea getClickAndMoveListener() {
			return null;
		}
	}
}