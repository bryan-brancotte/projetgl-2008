package ihm.smartPhone.statePanels.component;

import graphNetwork.Route;
import graphNetwork.Service;
import ihm.smartPhone.component.LowerBar;
import ihm.smartPhone.statePanels.IhmReceivingPanelState;
import ihm.smartPhone.tools.SizeAdapteur.FontSizeKind;

import java.awt.Point;
import java.awt.Rectangle;

public class ServiceOrRouteToolTipText {

	protected Rectangle area;

	protected IhmReceivingPanelState father;
	protected LowerBar lowerBar;

	protected Route route;
	protected Service service;

	public ServiceOrRouteToolTipText(IhmReceivingPanelState father, Rectangle area, LowerBar lowerBar) {
		this.lowerBar = lowerBar;
		this.area = area;
		this.father = father;
	}

	public boolean contains(int x, int y) {
		return area.contains(x, y);
	}

	public Rectangle getArea() {
		return area;
	}

	public void init(Route route) {
		this.route = route;
		this.service = null;
	}

	public void init(Service service) {
		this.service = service;
		this.route = null;
	}

	/**
	 * Méthode pour informé le sttt que le pointeur la survole peut-être, si c'est effectuivement le cas il transforme
	 * le pointeur en pointeur "main", et affiche dans le lower bar le nom de la route/service
	 * 
	 * @param x
	 *            l'abcisse
	 * @param y
	 *            l'ordonne
	 * @return
	 */
	public boolean maybeOvered(int x, int y) {
		if (!area.contains(x, y))
			return false;
		if (service != null) {
			if (service.getShortDescription() != null && !(service.getShortDescription().compareTo("") == 0)) {
				lowerBar.setLeftTitle(service.getName(), FontSizeKind.INTERMEDIATE);
				lowerBar.setLeftValue(service.getShortDescription(), FontSizeKind.SMALL, false);
			} else {
				lowerBar.setLeftValue(service.getName(), FontSizeKind.INTERMEDIATE, false);
			}
		} else {
			lowerBar.setLeftValue(father.lg("Route") + " " + route.getId(), FontSizeKind.INTERMEDIATE, false);
		}
		lowerBar.repaint();
		return true;
	}

	public boolean maybeOvered(Point p) {
		return maybeOvered(p.x, p.y);
	}

	public void setBounds(int x, int y, int width, int height) {
		area.setBounds(x, y, width, height);
	}
}
