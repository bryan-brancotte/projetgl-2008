package ihm.smartPhone.statePanels;

import ihm.classesExemples.TravelForTravelPanelExemple;
import ihm.smartPhone.composants.LowerBar;
import ihm.smartPhone.composants.UpperBar;
import ihm.smartPhone.interfaces.TravelForDisplayPanel;
import ihm.smartPhone.listener.MouseListenerClickAndMoveInArea;
import ihm.smartPhone.tools.CodeExecutor;
import ihm.smartPhone.tools.ImageLoader;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JOptionPane;

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
			imageWarning = ImageLoader.getRessourcesImageIcone("warning", sizeLargeFont,
					sizeLargeFont).getImage();
		iconeWarningArea = new Rectangle(0, 0, 0, 0);
		changeStateArea = new Rectangle(0, 0, 0, 0);

		MouseListenerClickAndMoveInArea clickAndMoveWarningAndArray = new MouseListenerClickAndMoveInArea(this);
		clickAndMoveWarningAndArray.addInteractiveArea(iconeWarningArea, new CodeExecutor() {
			@Override
			public void execute() {
				// System.out.println("warning");
				popUpMessage.define("toto", null);
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
		popUpMessage = new PopUpMessage();
	}

	public void setActualState(IhmReceivingStates actualState) {
		if ((actualState == IhmReceivingStates.EXPERIMENT_TRAVEL) || (actualState == IhmReceivingStates.PREVISU_TRAVEL)) {
			this.actualState = actualState;
			giveControle();
		}
	}

	/**
	 * Les classes implémentante donneront ainsi e contenue qu'elle veullent mettre dans le bouton en bas à droite, qui
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
			imageWarning = ImageLoader.getRessourcesImageIcone("warning", sizeLargeFont,
					sizeLargeFont).getImage();
		g.drawImage(imageWarning, 0, getHeight() - imageWarning.getHeight(null), null);
		iconeWarningArea.setBounds(0, getHeight() - imageWarning.getHeight(null), imageWarning.getHeight(null),
				imageWarning.getWidth(null));
		g.setFont(father.getSizeAdapteur().getSmallFont());/*
		g.setFont(father.getSizeAdapteur().getIntermediateFont());/**/
		int w = me.getWidthString(getMessageChangeState(), g, g.getFont());
		int h = me.getHeigthString(getMessageChangeState(), g, g.getFont());
		g.setColor(father.getSkin().getColorSubAreaInside());
		changeStateArea.setBounds(getWidth() - w * 5 / 4 - 2, getHeight() - h * 5 / 4 - 2, w * 5 / 4, h * 5 / 4);
		g.fillRect(changeStateArea.x, changeStateArea.y, changeStateArea.width, changeStateArea.height);
		g.setColor(father.getSkin().getColorLetter());
		g.drawRect(changeStateArea.x, changeStateArea.y, changeStateArea.width, changeStateArea.height);
		g.drawString(getMessageChangeState(), getWidth() - w * 9 / 8, getHeight() - h / 4);
		// popUpMessage.define("", null);
		if (popUpMessage.isActiveMessage())
			popUpMessage.paint(g);
	}

	public void displayPopUpMessage(String message, CodeExecutor actionAfterOkButton) {
		popUpMessage.define(message, actionAfterOkButton);
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

		this.repaint();
	}

	protected class PopUpMessage {
		protected String message = "";
		protected CodeExecutor actionAfterOkButton;
		protected boolean activeMessage = false;
		protected MouseListenerClickAndMoveInArea l;
		protected Rectangle imageButtonOkArea;
		protected MouseListener[] ml;
		protected MouseMotionListener[] mml;
		protected Image imageButtonOk = null;

		public boolean isActiveMessage() {
			return activeMessage;
		}

		public void define(String message, CodeExecutor actionAfterOkButton) {
			this.message = message;
			this.actionAfterOkButton = actionAfterOkButton;
			this.activeMessage = true;
			this.imageButtonOk = ImageLoader.getRessourcesImageIcone("button_ok",
					sizeLargeFont, sizeLargeFont)
					.getImage();
			ml = me.getMouseListeners();
			mml = me.getMouseMotionListeners();
			for (MouseListener m : ml)
				me.removeMouseListener(m);
			for (MouseMotionListener m : mml)
				me.removeMouseMotionListener(m);
			me.addMouseListener(l);
			me.addMouseMotionListener(l);
		}

		public PopUpMessage() {
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
					if (actionAfterOkButton != null)
						actionAfterOkButton.execute();
					activeMessage = false;
					me.repaint();
				}
			});
		}

		public void paint(Graphics g) {
			if (imageButtonOk.getHeight(null) != sizeLargeFont)
				imageButtonOk = ImageLoader.getRessourcesImageIcone("button_ok",
						sizeLargeFont, sizeLargeFont)
						.getImage();
			g.setColor(father.getSkin().getColorSubAreaInside());
			g.fillRect(getWidth() / 5, getHeight() / 5, getWidth() * 3 / 5, getHeight() * 3 / 5);
			g.setColor(father.getSkin().getColorLetter());
			g.drawRect(getWidth() / 5, getHeight() / 5, getWidth() * 3 / 5, getHeight() * 3 / 5);
			imageButtonOkArea.setBounds(getWidth() * 4 / 5 - sizeLargeFont * 2,
					getHeight() * 4 / 5 - sizeLargeFont * 2, father.getSizeAdapteur()
							.getSizeLargeFont(), sizeLargeFont);
			g.drawImage(imageButtonOk, imageButtonOkArea.x, imageButtonOkArea.y, null);
		}

		public MouseListenerClickAndMoveInArea getClickAndMoveListener() {
			return null;
		}
	}
}