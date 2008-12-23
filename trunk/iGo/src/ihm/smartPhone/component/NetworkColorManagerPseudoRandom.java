package ihm.smartPhone.component;

import graphNetwork.Route;
import graphNetwork.Service;
import graphNetwork.Station;

import iGoMaster.Master;

import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class NetworkColorManagerPseudoRandom implements NetworkColorManager {
	Master master;
	HashMap<Route, Color> colorRoutes;
	HashMap<Service, Color> colorServices;
	LinkedList<Color> colorList;

	protected void initColor() {
		colorRoutes = new HashMap<Route, Color>();
		colorServices = new HashMap<Service, Color>();
		colorList = new LinkedList<Color>();
		colorList.add(new Color(242, 130, 38));// Orange
		colorList.add(new Color(73, 12, 139));// pourpre foncé
		colorList.add(new Color(133, 242, 38));// Vert jeune pousse
		colorList.add(new Color(114, 159, 220));// Bleu clair
		colorList.add(new Color(208, 38, 242));// rose clair
		colorList.add(new Color(12, 52, 139));// Bleu marrine
		colorList.add(Color.cyan);
		colorList.add(new Color(139, 69, 12));// marront
		colorList.add(new Color(12, 128, 139));// Bleu turquoise
		colorList.add(new Color(254, 170, 52));// Orange pastel
		colorList.add(new Color(189, 107, 247));// violet pastel
		colorList.add(new Color(139, 12, 65));// bordeau
		colorList.add(new Color(242, 239, 38));// Jaune
		colorList.add(new Color(141, 207, 80));// Vert clair
		colorList.add(new Color(137, 12, 139));// violet foncé
		colorList.add(new Color(52, 52, 254));// Bleu foncé
		colorList.add(new Color(80, 139, 12));// Vert foncé
		colorList.add(new Color(38, 116, 224));// Bleu
		colorList.add(new Color(137, 38, 242));// pourpre
	}

	protected void initHash() {
		try {
			Iterator<Route> itR;
			Iterator<Station> itS = master.getStations();
			Iterator<Color> itC = colorList.iterator();
			Iterator<Service> itSer = master.getServices();
			Route route;
			Service service;
			while (itS.hasNext()) {
				itR = itS.next().getRoutes();
				while (itR.hasNext()) {
					if (!colorRoutes.containsKey(route = itR.next())) {
						if (!itC.hasNext())
							itC = colorList.iterator();
						colorRoutes.put(route, itC.next());
					}
				}
			}

			while (itSer.hasNext()) {
				if (!colorRoutes.containsKey(service = itSer.next())) {
					if (!itC.hasNext())
						itC = colorList.iterator();
					colorServices.put(service, itC.next());
				}
			}
		} catch (Exception e) {
			System.err.println("Dans le cadre de la gestion des couleurs le master ne peut fournir les services et route, nous retenterons plus tard");
		}
	}

	public NetworkColorManagerPseudoRandom(Master master) {
		this.master = master;
		initColor();
		initHash();
	}

	@Override
	public Color getColor(Route r) {
		if (colorRoutes.isEmpty())
			initHash();
		Color ret = colorRoutes.get(r);
		if (ret == null)
			return Color.blue;
		return ret;
	}

	@Override
	public Color getColor(Service s) {
		if (colorServices.isEmpty())
			initHash();
		Color ret = colorServices.get(s);
		if (ret == null)
			return Color.red;
		return ret;
	}

}
