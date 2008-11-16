package ihm.smartPhone.statePanels.component;

import ihm.smartPhone.tools.ImageLoader;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Label;
import java.awt.LayoutManager;
import java.awt.Panel;

import javax.swing.JButton;

public class CollapsablePanel extends Panel {

	private static final long serialVersionUID = 1L;

	protected Panel inside;

	public CollapsablePanel(LayoutManager layout, String title) {
		protectionOnInsidePanel();
		if (layout != null)
			inside.setLayout(layout);
		Panel up = new Panel();
		up.setLayout(new BorderLayout());
		up.add(new Label(title), BorderLayout.CENTER);
		JButton cmdExpCol = new JButton();
		cmdExpCol.setIcon(ImageLoader.getRessourcesImageIcone("button_less", 16, 16));
		cmdExpCol.setSize(18, 18);
		up.add(cmdExpCol, BorderLayout.EAST);
		super.setLayout(new BorderLayout());
		super.add(up, BorderLayout.NORTH);
		super.add(inside, BorderLayout.CENTER);
	}

	public CollapsablePanel(String title) {
		this(null, title);
	}

	protected void makeMe(String title) {
	}

	protected void protectionOnInsidePanel() {
		if (inside == null)
			inside = new Panel();
	}

	@Override
	public Component add(Component comp) {
		return inside.add(comp);
	}

	@Override
	public Component add(Component comp, int index) {
		return inside.add(comp, index);
	}

	@Override
	public LayoutManager getLayout() {
		return inside.getLayout();
	}

	@Override
	public void remove(Component comp) {
		inside.remove(comp);
	}

	@Override
	public void remove(int index) {
		inside.remove(index);
	}

	@Override
	public void removeAll() {
		inside.removeAll();
	}

	@Override
	public void setLayout(LayoutManager mgr) {
		protectionOnInsidePanel();
		inside.setLayout(mgr);
	}
}
