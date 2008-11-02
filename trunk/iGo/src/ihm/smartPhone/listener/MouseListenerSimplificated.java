package ihm.smartPhone.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public abstract class MouseListenerSimplificated<T> implements MouseListener {

	protected T origin;

	@Override
	public abstract void mouseClicked(MouseEvent e);

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public abstract void mousePressed(MouseEvent e);

	@Override
	public abstract void mouseReleased(MouseEvent e);

	public MouseListenerSimplificated(T origin) {
		super();
		this.origin = origin;
	}

}
