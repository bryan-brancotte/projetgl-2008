package ihm.smartPhone.statePanels;

import graphNetwork.Route;
import graphNetwork.Service;
import graphNetwork.Station;
import ihm.smartPhone.component.LowerBar;
import ihm.smartPhone.component.UpperBar;
import ihm.smartPhone.libPT.PTArea;
import ihm.smartPhone.libPT.PTScrollBar;
import ihm.smartPhone.libPT.PanelDoubleBufferingSoftwear;
import ihm.smartPhone.statePanels.component.PairPTArea;
import ihm.smartPhone.statePanels.component.ServiceToolTipText;
import ihm.smartPhone.tools.CodeExecutor;
import ihm.smartPhone.tools.CodeExecutor1P;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class ListingPanel extends PanelState {

	public String getStationSelected() {
		return stationSelected.getName();
	}

	protected HashMap<Route, PTArea> hashRouteArea;
	protected HashMap<Route, LinkedList<PairPTArea>> hashRouteStation;
	protected LinkedList<Route> routes;

	protected Station stationSelected = null;

	protected CodeExecutor okEndingAction = null;
	protected CodeExecutor cancelEndingAction = null;
	/**
	 * Conteneurs pour les zone d'aide des services
	 */
	protected LinkedList<ServiceToolTipText> serviceDisplayed;
	protected LinkedList<ServiceToolTipText> servicePooled;

	/**
	 * Barre de défilement
	 */
	protected PTScrollBar scrollBar;
	/**
	 * La valeur du scroll de la barre de défilement
	 */
	protected int deroullement;
	/**
	 * boolean permetant de savoir si à la fin du premier repaint, on doit en faire un second
	 */
	protected boolean shouldDoubleRepaint = true;

	public ListingPanel(IhmReceivingPanelState father, UpperBar upperBar, LowerBar nvLowerBar,
			CodeExecutor nvOkEndingAction, CodeExecutor nvCancelEndingAction) {
		super(father, upperBar, nvLowerBar);
		routes = new LinkedList<Route>();
		Iterator<Route> itR;
		Route route;
		Iterator<Station> itS = father.getStations();
		while (itS.hasNext()) {
			itR = (itS.next()).getRoutes();
			while (itR.hasNext())
				if (!routes.contains(route = itR.next()))
					routes.add(route);
		}
		Collections.sort(routes, new Comparator<Route>() {
			@Override
			public int compare(Route o1, Route o2) {
				return o1.getId().compareTo(o2.getId());
			}
		});

		hashRouteArea = new HashMap<Route, PTArea>();
		hashRouteStation = new HashMap<Route, LinkedList<PairPTArea>>();
		LinkedList<PairPTArea> lstStation;
		itR = routes.iterator();
		while (itR.hasNext()) {
			hashRouteArea.put(route = itR.next(), makeArea());
			hashRouteStation.put(route, lstStation = new LinkedList<PairPTArea>());
			itS = route.getStations();
			PairPTArea p;
			while (itS.hasNext()) {
				lstStation.add(p = new PairPTArea(makeArea(), itS.next()));
				clickAndMoveWarningAndArray.addInteractiveArea(p.area.getArea(), new CodeExecutor1P<PairPTArea>(p) {
					public void execute() {
						stationSelected = this.origine.station;
						okEndingAction.execute();
					};
				});
			}
		}
		this.addMouseMotionListener(new MouseMotionListener() {

			protected ServiceToolTipText overed = null;

			@Override
			public void mouseDragged(MouseEvent e) {
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				for (ServiceToolTipText s : serviceDisplayed)
					if (s.contains(e.getX(), e.getY())) {
						if (overed != s) {
							s.maybeOvered(e.getX(), e.getY());
							overed = s;
						}
						return;
					}
				if (overed == null)
					return;
				lowerBar.setLeftTitle("");
				lowerBar.setLeftValue("");
				lowerBar.repaint();
				overed = null;
			}
		});
		serviceDisplayed = new LinkedList<ServiceToolTipText>();
		servicePooled = new LinkedList<ServiceToolTipText>();
		this.cancelEndingAction = nvCancelEndingAction;
		this.okEndingAction = nvOkEndingAction;

		/***************************************************************************************************************
		 * ScrollBar
		 */
		scrollBar = makeScrollBar();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3801758712891550847L;

	@Override
	public void giveControle() {
		lowerBar.clearMessage();
		lowerBar.repaint();
		upperBar.clearMessage();
		upperBar.setMainTitle(father.lg("FindAStation"));
		upperBar.setLeftCmd(father.lg("Back"), new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (cancelEndingAction == null)
					father.setCurrentState(IhmReceivingStates.UNKNOWN);
				else
					cancelEndingAction.execute();
			}
		});
		upperBar.repaint();
		this.requestFocus();
	}

	@Override
	public void paint(Graphics g) {
		draw();
		if (shouldDoubleRepaint) {
			shouldDoubleRepaint = false;
			draw();
		}
		/***************************************************************************************************************
		 * fin du dessin en mémoire, on dessine le résultat sur l'écran
		 */
		g.drawImage(image, 0, 0, null);
	}

	/**
	 * Dessin en arrière plan de la fenetre
	 */
	public void draw() {
		int decalage = father.getSizeAdapteur().getSizeSmallFont();
		int decalageDemi = (decalage >> 1);
		int decalage2 = (decalage << 1);
		int ordonne = decalage - deroullement;

		/*****
		 * Swap des liste de zone de service
		 */
		// LinkedList<ServiceToolTipText> serviceSwap = servicePooled;
		servicePooled.addAll(serviceDisplayed);
		serviceDisplayed.clear();
		// serviceDisplayed = serviceSwap;

		if ((buffer == null) || (image.getWidth(null) != getWidth()) || (image.getHeight(null) != getHeight())) {
			image = createImage(getWidth(), getHeight());
			buffer = image.getGraphics();
			graphicsTunning(buffer);
			// buffer.setColor(father.getSkin().getColorLetter());
			// if (imageOk == null || imageOk.getIconHeight() != father.getSizeAdapteur().getSizeLargeFont()) {
			// imageOk = ImageLoader.getRessourcesImageIcone("button_ok", father.getSizeAdapteur().getSizeLargeFont(),
			// father.getSizeAdapteur().getSizeLargeFont());
			// imageDel = ImageLoader.getRessourcesImageIcone("button_cancel", father.getSizeAdapteur()
			// .getSizeIntermediateFont(), father.getSizeAdapteur().getSizeIntermediateFont());
			// imageFind = ImageLoader.getRessourcesImageIcone("logo", father.getSizeAdapteur()
			// .getSizeIntermediateFont(), father.getSizeAdapteur().getSizeIntermediateFont());
			// }
		} else {
			buffer.setColor(father.getSkin().getColorInside());
			buffer.fillRect(0, 0, getWidth(), getHeight());
		}

		int h, w;
		ServiceToolTipText sttt;
		PTArea ptArea;
		for (Route route : routes) {
			// if (ordonne > getHeight())
			// break;
			// La routes
			// route = e.getKey();
			ptArea = hashRouteArea.get(route);
			buffer.setFont(father.getSizeAdapteur().getSmallFont());
			h = getHeightString(route.getId(), buffer);
			ptArea.prepareArea(buffer, decalageDemi, ordonne, h + (h >> 1), getWidth() - decalage2);

			ptArea.draw(buffer, father.getNetworkColorManager().getColor(route), father.getSkin().getColorLetter());
			buffer.setColor(father.getSkin().getColorLetter());
			buffer.drawString(route.getId(), decalage, ordonne + h + (h >> 2));
			w = getWidthString(route.getKindRoute().getKindOf(), buffer);
			buffer.drawString(route.getKindRoute().getKindOf(), getWidth() - decalage2 - w, ordonne + h + (h >> 2));

			ordonne = ptArea.getArea().y + ptArea.getArea().height + decalageDemi;

			// et ses stations
			Iterator<Service> itService;
			Service service;
			String s;
			int xActu, yActu, taille = h + (h >> 2) + (h >> 3);
			for (PairPTArea p : hashRouteStation.get(route)) {
				h = getHeightString(p.name, buffer);

				p.area.prepareArea(buffer, decalage, ordonne, taille, getWidth() - decalage2 - decalageDemi);

				if (p.area.getArea().y + p.area.getArea().height > 0 && ordonne < getHeight()) {
					itService = p.station.getServices();
					xActu = getWidthString(p.name, buffer) + decalage2;
					yActu = ordonne;
					while (itService.hasNext()) {
						service = itService.next();
						s = service.getName().substring(0, 1);
						if (servicePooled.isEmpty())
							sttt = new ServiceToolTipText(new Rectangle(), lowerBar);
						else
							sttt = servicePooled.remove();
						serviceDisplayed.add(sttt);
						sttt.init(service);
						buffer.setColor(father.getNetworkColorManager().getColor(service));
						sttt.setBounds(xActu - 1, yActu - 1, taille + 2, taille + 2);
						buffer.fillOval(xActu - 1, yActu - 1, taille + 2, taille + 2);
						buffer.setColor(father.getSkin().getColorLetter());
						buffer.drawOval(xActu - 1, yActu - 1, taille + 2, taille + 2);
						buffer.drawString(s, xActu + (taille >> 1)
								- (PanelDoubleBufferingSoftwear.getWidthString(s, buffer) >> 1), yActu + (taille >> 1)
								+ (PanelDoubleBufferingSoftwear.getHeightString(s, buffer) >> 1));
						xActu += taille + (decalage >> 1);
					}
					p.area.getArea().width = xActu - p.area.getArea().x;
					// buffer.setColor(father.getSkin().getColorLetter());
					buffer.drawString(p.name, decalage + decalageDemi, ordonne + h + (h >> 3) + (h >> 4));
				} else {
					// System.out.println("notDrawn:" + p.name + " of " + route.getId());
				}
				ordonne = p.area.getArea().y + p.area.getArea().height + (decalageDemi >> 1);
			}
		}

		/***************************************************************************************************************
		 * ScrollBar
		 */
		scrollBar.update(buffer, getWidth() - 1 - father.getSizeAdapteur().getSizeIntermediateFont(), father
				.getSizeAdapteur().getSizeIntermediateFont(), ordonne + deroullement - getHeight(), deroullement,
				father.getSkin().getColorSubAreaInside(), father.getSkin().getColorLetter());
		shouldDoubleRepaint = (deroullement != scrollBar.getDeroullement());
		deroullement = scrollBar.getDeroullement();
		// System.out.println(serviceDisplayed.size() + " " + servicePooled.size() + " : "
		// + (serviceDisplayed.size() + servicePooled.size()));

	}
}
