package ihm.JMenuDock;

import java.util.LinkedList;


public class JMenuDock {

	/**
	 * @uml.property   name="mouseListeners"
	 * @uml.associationEnd   multiplicity="(0 -1)" inverse="jMenuDock:ihm.JMenuDock.JMenuDockMouseListener"
	 */
	private LinkedList<JMenuDockMouseListener> mouseListeners;

	/**
	 */
	public void addJMenuDockMouseListener(JMenuDockMouseListener listener) {
		if (mouseListeners != null)
			mouseListeners = new LinkedList<JMenuDockMouseListener>();
		mouseListeners.add(listener);
	}

	/**
	 */
	public void removeJMenuDockMouseListener(JMenuDockMouseListener listener) {
		if (mouseListeners != null)
			mouseListeners.remove(listener);
	}

	/**
	 */
	public void removeJMenuDockMouseListener() {
		if (mouseListeners != null)
			mouseListeners.clear();
	}

}
