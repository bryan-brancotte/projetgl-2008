package ihm.smartPhone.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public abstract class MouseMotionListenerSimplificated<T> implements MouseMotionListener {

	protected T origin; 
	
	public MouseMotionListenerSimplificated(T origin) {
		super();
		this.origin = origin;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public abstract void mouseMoved(MouseEvent e);

}
