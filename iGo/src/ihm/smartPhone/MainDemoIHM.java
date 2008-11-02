package ihm.smartPhone;

import iGoMaster.Language;
import iGoMaster.Master;
import ihm.smartPhone.tools.iGoSmartPhoneSkin;
import tools.xmlFeature.LanguageXML;

public class MainDemoIHM {

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub 
		IHM ihm = new IGoIhmSmartPhone(new Master() {

			protected Language lang = new LanguageXML();

			@Override
			public String lg(String key) {
				return lang.lg(key);
			}

			@Override
			public void stop() {
			}

		}, iGoSmartPhoneSkin.WHITE_WITH_LINE);/*
		}, iGoSmartPhoneSkin.BLACK_WITH_LINE);/**/
		ihm.start(true,8);
		int t = 100;
		Thread.currentThread().sleep(t);
		ihm.showMessageSplashScreen("simplet");
		Thread.currentThread().sleep(t);
		ihm.showMessageSplashScreen("atchoum");
		Thread.currentThread().sleep(t);
		ihm.showMessageSplashScreen("grincheux");
		Thread.currentThread().sleep(t);
		ihm.showMessageSplashScreen("triste");
		//ihm.setMaxStepInSplashScreen(5);
		Thread.currentThread().sleep(t);
		ihm.showMessageSplashScreen("timide");
		ihm.setMaxStepInSplashScreen(8);
		Thread.currentThread().sleep(t);
		ihm.showMessageSplashScreen("prof");
		Thread.currentThread().sleep(t);
		ihm.showMessageSplashScreen("dormeur");
		Thread.currentThread().sleep(t);
		ihm.showMessageSplashScreen("get readyyyyyyyy");
		Thread.currentThread().sleep(400);
		ihm.endSplashScreen();
		//ihm.stop();
	}

}
