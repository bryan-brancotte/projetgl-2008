package ihm.smartPhone;

import iGoMaster.IHM;
import iGoMaster.Language;
import iGoMaster.Master;
import ihm.smartPhone.statePanels.IhmReceivingStates;
import ihm.smartPhone.tools.ExecMultiThread;
import ihm.smartPhone.tools.iGoSmartPhoneSkin;
import xmlFeature.LanguageXML;

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

			@Override
			public String config(String key) {
				//return IhmReceivingStates.ARRAY_MODE.toString();/*
				return IhmReceivingStates.GRAPHIC_MODE.toString();/**/
			}

		}, iGoSmartPhoneSkin.WHITE_WITH_LINE);/*
		}, iGoSmartPhoneSkin.BLACK_WITH_LINE);/**/
		ihm.start(true,8);
		new ExecMultiThread<IHM>(ihm){

			@Override
			public void run() {
				int t = 100;
				try {
					Thread.currentThread().sleep(t*10);
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
			}}.start();
		//ihm.stop();
	}

}
