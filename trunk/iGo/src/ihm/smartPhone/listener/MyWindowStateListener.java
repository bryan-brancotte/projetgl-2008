package ihm.smartPhone.listener;

import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

public abstract class MyWindowStateListener<T> implements WindowStateListener {

	protected T origin;
	
	@Override
	public abstract void windowStateChanged(WindowEvent e) ;

	public MyWindowStateListener(T origin) {
		super();
		this.origin = origin;
	}

}
