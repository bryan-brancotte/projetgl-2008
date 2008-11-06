package ihm.smartPhone.statePanels;

import graphNetwork.PathInGraphReader;
import ihm.smartPhone.composants.LowerBar;
import ihm.smartPhone.composants.TravelPanel;
import ihm.smartPhone.composants.UpperBar;
import ihm.smartPhone.composants.VerticalFlowLayout;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class LoadTravelPanel extends PanelState {

	private static final long serialVersionUID = 1L;
	
	protected Panel inside ;

	public LoadTravelPanel(IhmReceivingPanelState ihm, UpperBar upperBar, LowerBar lowerBar,
			LinkedList<PathInGraphReader> paths) {
		super(ihm, upperBar, lowerBar);
		ScrollPane sp = new ScrollPane();
		inside = new Panel(); 
		inside.setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP, true, false));
		inside.add(new TravelPanel(null, father));
		inside.add(new TravelPanel(null, father));
		inside.add(new TravelPanel(null, father));
		inside.add(new TravelPanel(null, father));
		inside.add(new TravelPanel(null, father));
		inside.add(new TravelPanel(null, father));
		inside.add(new TravelPanel(null, father));
		sp.add(inside);
		this.setLayout(new BorderLayout());
		this.add(sp);
	}

	@Override
	public void paint(Graphics g) {
		//g.drawString(this.getClass().getSimpleName(), 0, this.getHeight());
	}

	@Override
	public void giveControle() {
		upperBar.clearMessage();
		upperBar.setMainTitle(father.lg("LoadTravel"));
		upperBar.repaint();

		lowerBar.clearMessage();
		lowerBar.setCenterIcone("home", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				father.setActualState(IhmReceivingStates.MAIN_INTERFACE);
			}
		});
		lowerBar.repaint();
	}

}
