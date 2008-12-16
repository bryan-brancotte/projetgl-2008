package ihm.smartPhone;

import java.util.Iterator;
import java.util.Observable;

import graphNetwork.GraphNetworkBuilder;
import graphNetwork.KindRoute;
import graphNetwork.Service;
import graphNetwork.exception.ViolationOfUnicityInIdentificationException;
import iGoMaster.IHM;
import iGoMaster.Language;
import iGoMaster.Master;
import ihm.smartPhone.component.iGoSmartPhoneSkin;
import ihm.smartPhone.statePanels.IhmReceivingStates;
import ihm.smartPhone.tools.ExecMultiThread;
import xmlFeature.LanguageXML;

public class MainDemoIHM {

	protected static IHM ihm;

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws InterruptedException {
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
			public boolean askForATravel() {
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

			@Override
			public Iterator<KindRoute> getKindRoutes() {
				GraphNetworkBuilder gnb = new GraphNetworkBuilder();
				try {
					gnb.addRoute("1", "Train");
					gnb.addRoute("2", "Trolley");
					gnb.addRoute("3", "Subway");
					gnb.addRoute("4", "Foot");
				} catch (ViolationOfUnicityInIdentificationException e) {
				}
				return KindRoute.getKinds();
			}

			@Override
			public Iterator<Service> getServices() {
				GraphNetworkBuilder gnb = new GraphNetworkBuilder();
				try {
					gnb.addService(1, "Wheelchair accessible");
					gnb.addService(2, "Coffe");
					gnb.addService(3, "Flower");
					gnb.addService(4, "Parking");
				} catch (ViolationOfUnicityInIdentificationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return gnb.getActualGraphNetwork().getServices();
			}

			@Override
			public String getConfig(String key) {
				return config(key);
			}

			@Override
			public boolean setConfig(String key, String value) {
				System.out.println("Recording : <" + key + "> as \"" + value + "\"");
				return false;
			}

		}, iGoSmartPhoneSkin.PURPLE_LIGHT_WITH_LINE);/***************************************************************
														 * }, iGoSmartPhoneSkin.BLUE_WITH_LINE);/* //
														 * },iGoSmartPhoneSkin.WHITE_WITH_LINE);/*
														 * },iGoSmartPhoneSkin.BLACK_WITH_LINE);/
														 **************************************************************/
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

}
