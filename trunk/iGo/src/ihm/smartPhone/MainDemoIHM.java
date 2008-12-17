package ihm.smartPhone;

import graphNetwork.GraphNetworkBuilder;
import graphNetwork.KindRoute;
import graphNetwork.PathInGraphBuilder;
import graphNetwork.Service;
import graphNetwork.Station;
import graphNetwork.exception.ImpossibleValueException;
import graphNetwork.exception.ViolationOfUnicityInIdentificationException;
import iGoMaster.Configuration;
import iGoMaster.IHM;
import iGoMaster.Language;
import iGoMaster.Master;
import ihm.smartPhone.component.iGoSmartPhoneSkin;
import ihm.smartPhone.statePanels.IhmReceivingStates;
import ihm.smartPhone.tools.ExecMultiThread;

import java.util.Iterator;
import java.util.Observable;

import xmlFeature.ConfigurationXML;
import xmlFeature.LanguageXML;

public class MainDemoIHM {

	protected static IHM ihm;

	static GraphNetworkBuilder gnb;

	protected static Configuration conf;

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws InterruptedException {
		conf = new ConfigurationXML();
		ihm = new IGoIhmSmartPhone(new Master() {

			protected Language lang = new LanguageXML();

			@Override
			public String lg(String key) {
				return lang.lg(key);
			}

			@Override
			public void stop() {
			}

			@Override
			public String config(String key) {

				System.out.println("Reading : <" + key + ">");
				if (key.compareTo("GRAPHIC_OR_ARRAY_MODE") == 0)
					return IhmReceivingStates.GRAPHIC_MODE.toString();
				if (key.compareTo("TRAVEL_CRITERIA") == 0)
					return "1";
				if (key.compareTo("TRAVEL_MODE_Trolley") == 0)
					return "1";
				if (key.compareTo("TRAVEL_MODE_Foot") == 0)
					return "1";
				return "";
			}

			@Override
			public void update(Observable o, Object arg) {
			}

			@Override
			public Iterator<KindRoute> getKindRoutes() {
				if (gnb == null)
					makeGNB();
				return KindRoute.getKinds();
			}

			@Override
			public Iterator<Service> getServices() {
				if (gnb == null)
					makeGNB();
				return gnb.getCurrentGraphNetwork().getServices();
			}

			@Override
			public String getConfig(String key) {
				return conf.getValue(key);
			}

			@Override
			public boolean setConfig(String key, String value) {
				System.out.println("Recording : <" + key + "> as \"" + value + "\"");
				conf.setValue(key, value);
				conf.save();
				return true;
			}

			@Override
			public Iterator<Station> getStations() {
				if (gnb == null)
					makeGNB();
				return gnb.getCurrentGraphNetwork().getStations();
			}

			@Override
			public boolean askForATravel(PathInGraphBuilder pathInGraphBuidable) {
				(new ExecMultiThread<IHM>(ihm) {
					@Override
					public void run() {
						try {
							Thread.currentThread().sleep(1000);
							System.out.println("tada!");
							ihm.returnPathAsked(null, "pas trouvé");
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}).start();
				return true;
			}

			// }, iGoSmartPhoneSkin.PURPLE_LIGHT_WITH_LINE);
			// }, iGoSmartPhoneSkin.BLUE_WITH_LINE);
		}, iGoSmartPhoneSkin.WHITE_WITH_LINE);
		// },iGoSmartPhoneSkin.BLACK_WITH_LINE);
		ihm.start(true, 8);
		new ExecMultiThread<IHM>(ihm) {

			@Override
			public void run() {
				int t = 1;
				try {
					Thread.currentThread().sleep(t * 10);
					this.origine.showMessageSplashScreen("simplet");
					Thread.currentThread().sleep(t);
					this.origine.showMessageSplashScreen("atchoum");
					Thread.currentThread().sleep(t);
					this.origine.showMessageSplashScreen("grincheux");
					Thread.currentThread().sleep(t);
					this.origine.showMessageSplashScreen("triste");
					// ihm.setMaxStepInSplashScreen(5);
					Thread.currentThread().sleep(t);
					this.origine.showMessageSplashScreen("timide");
					this.origine.setMaxStepInSplashScreen(8);
					Thread.currentThread().sleep(t);
					this.origine.showMessageSplashScreen("prof");
					Thread.currentThread().sleep(t);
					this.origine.showMessageSplashScreen("dormeur");
					Thread.currentThread().sleep(t);
					this.origine.showMessageSplashScreen("get readyyyyyyyy");
					Thread.currentThread().sleep(400);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				this.origine.endSplashScreen();
			}
		}.start();
		// ihm.stop();
	}

	protected static void makeGNB() {
		try {
			gnb = new GraphNetworkBuilder();
			gnb.addRoute("RerB", "Train");
			gnb.addRoute("2", "Trolley");
			gnb.addRoute("3", "Subway");
			gnb.addRoute("4", "Foot");
			gnb.addService(1, "Wheelchair accessible");
			gnb.addService(2, "Coffee");
			gnb.addService(3, "Flower");
			gnb.addService(4, "Parking");
			gnb.addStation(1, "Massy");
			gnb.addStation(2, "Antony");
			gnb.addStation(3, "Le Guichet");
			gnb.addStationToRoute(gnb.getCurrentGraphNetwork().getRoute("RerB"), gnb.getCurrentGraphNetwork()
					.getStation(1), 0);
			gnb.addStationToRoute(gnb.getCurrentGraphNetwork().getRoute("RerB"), gnb.getCurrentGraphNetwork()
					.getStation(2), 5);
			gnb.addStationToRoute(gnb.getCurrentGraphNetwork().getRoute("RerB"), gnb.getCurrentGraphNetwork()
					.getStation(3), 7);
			gnb.addServiceToStation(gnb.getCurrentGraphNetwork().getStation(1), gnb.getCurrentGraphNetwork()
					.getService(1));
			gnb.addServiceToStation(gnb.getCurrentGraphNetwork().getStation(2), gnb.getCurrentGraphNetwork()
					.getService(1));
			gnb.addServiceToStation(gnb.getCurrentGraphNetwork().getStation(3), gnb.getCurrentGraphNetwork()
					.getService(1));
			gnb.addServiceToStation(gnb.getCurrentGraphNetwork().getStation(3), gnb.getCurrentGraphNetwork()
					.getService(3));
			gnb.addServiceToStation(gnb.getCurrentGraphNetwork().getStation(2), gnb.getCurrentGraphNetwork()
					.getService(2));
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (ViolationOfUnicityInIdentificationException e) {
			e.printStackTrace();
		} catch (ImpossibleValueException e) {
			e.printStackTrace();
		}
	}

}
