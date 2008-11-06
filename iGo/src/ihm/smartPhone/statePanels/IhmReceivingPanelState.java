package ihm.smartPhone.statePanels;

import ihm.smartPhone.tools.SizeAdapteur;
import ihm.smartPhone.tools.iGoSmartPhoneSkin;

public interface IhmReceivingPanelState {

	public iGoSmartPhoneSkin getSkin();

	public SizeAdapteur getSizeAdapteur();

	public void stop();

	public String lg(String key);
	
	public void setActualState(IhmReceivingStates actualState);
	
	public IhmReceivingStates getActualState();
}
