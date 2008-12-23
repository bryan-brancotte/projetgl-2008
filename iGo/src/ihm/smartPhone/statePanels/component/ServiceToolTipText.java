package ihm.smartPhone.statePanels.component;

import graphNetwork.Service;
import ihm.smartPhone.component.LowerBar;
import ihm.smartPhone.tools.SizeAdapteur.FontSizeKind;

import java.awt.Point;
import java.awt.Rectangle;

public class ServiceToolTipText {

	public ServiceToolTipText(Rectangle area, LowerBar lowerBar) {
		this.lowerBar = lowerBar;
		this.area = area;
	}

	protected LowerBar lowerBar;

	protected Service service;

	protected Rectangle area;

	public void init(Service service) {
		this.service = service;
	}

	public void maybeOvered(Point p) {
		maybeOvered(p.x, p.y);
	}

	public boolean maybeOvered(int x, int y) {
		if (!area.contains(x, y))
			return false;
		lowerBar.setLeftTitle(service.getName(), FontSizeKind.SMALL);
		if (service.getShortDescription() != null && !service.getShortDescription().isEmpty())
			lowerBar.setLeftValue(service.getShortDescription(), FontSizeKind.SMALL);
		lowerBar.repaint();
		return true;
	}

	public void setBounds(int x, int y, int width, int height) {
		area.setBounds(x, y, width, height);
	}
}
