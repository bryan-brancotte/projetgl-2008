package ihm.JReseau;

import java.util.Collection;
import java.util.LinkedList;

import javax.swing.JComponent;

public class JNetwork extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @uml.property   name="mouseListeners"
	 * @uml.associationEnd   multiplicity="(0 -1)" inverse="jReseau:ihm.JReseau.JNetworkMouseListener"
	 */
	private Collection<JNetworkMouseListener> mouseListeners;

	/**
	 * @uml.property   name="reseauModel"
	 * @uml.associationEnd   inverse="jReseau:ihm.JReseau.JNetworkModel"
	 */
	private JNetworkModel reseauModel;

	/**
	 * Getter of the property <tt>reseauModel</tt>
	 * 
	 * @return Returns the reseauModel.
	 * @uml.property name="reseauModel"
	 */
	public JNetworkModel getReseauModel() {
		return reseauModel;
	}

	/**
	 * Setter of the property <tt>reseauModel</tt>
	 * 
	 * @param reseauModel
	 *            The reseauModel to set.
	 * @uml.property name="reseauModel"
	 */
	public void setReseauModel(JNetworkModel reseauModel) {
		this.reseauModel = reseauModel;
	}

	/**
	 */
	public void addJReseauMouseListener(JNetworkMouseListener listener) {
		if (mouseListeners != null)
			mouseListeners = new LinkedList<JNetworkMouseListener>();
		mouseListeners.add(listener);
	}

	/**
	 */
	public void removeJReseauMouseListener(JNetworkMouseListener listener) {
		if (mouseListeners != null)
			mouseListeners.remove(listener);
	}

	/**
	 */
	public void removeJReseauMouseListener() {
		if (mouseListeners != null)
			mouseListeners.clear();
	}
}
