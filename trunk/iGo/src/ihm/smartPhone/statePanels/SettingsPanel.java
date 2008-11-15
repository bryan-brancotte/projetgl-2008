package ihm.smartPhone.statePanels;

import ihm.smartPhone.composants.LowerBar;
import ihm.smartPhone.composants.UpperBar;
import ihm.smartPhone.tools.VerticalFlowLayout;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SettingsPanel extends PanelState {

	private static final long serialVersionUID = 1L;

	protected Document settings = null;
	
	protected Panel insidePanel;

	public SettingsPanel(IhmReceivingPanelState ihm, UpperBar upperBar, LowerBar lowerBar, Document settings) {
		super(ihm, upperBar, lowerBar);
		try {
			if (settings == null)
				this.settings = buildExempleSettingXML();
			else
				this.settings = settings;
		} catch (ParserConfigurationException e) {
			return;
		}
		this.setLayout(new BorderLayout());
		insidePanel = new Panel(new VerticalFlowLayout());
		ScrollPane scrollPane =new ScrollPane();
		scrollPane.add(insidePanel);
		this.add(scrollPane);
		buildInterfaceFromDomDocument();
	}
	
	protected void buildInterfaceFromDomDocument(){
		insidePanel.removeAll();
	}

	protected Document buildExempleSettingXML() throws ParserConfigurationException {
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		Element root, elt, child;

		root = document.createElement("Settings");
		document.appendChild(root);

		elt = document.createElement("Setting");
		elt.setAttribute("text", "TravelCriteria");
		elt.setAttribute("type", "radiobox");
		elt.setAttribute("value", "1");
		child=document.createElement("enum");
		child.setAttribute("text", "Cheaper");
		child.setAttribute("value", "1");
		elt.appendChild(child);
		child=document.createElement("enum");
		child.setAttribute("text", "Faster");
		child.setAttribute("value", "2");
		elt.appendChild(child);
		child=document.createElement("enum");
		child.setAttribute("text", "FewerChanges");
		child.setAttribute("value", "3");
		elt.appendChild(child);
		root.appendChild(elt);

		elt = document.createElement("Setting");
		elt.setAttribute("text", "TravelMode");
		elt.setAttribute("type", "checkbox");
		elt.setAttribute("value", "");
		child=document.createElement("enum");
		child.setAttribute("text", "Trolley");
		child.setAttribute("value", "1");
		elt.appendChild(child);
		child=document.createElement("enum");
		child.setAttribute("text", "Subway");
		child.setAttribute("value", "1");
		elt.appendChild(child);
		child=document.createElement("enum");
		child.setAttribute("text", "Train");
		child.setAttribute("value", "1");
		elt.appendChild(child);
		root.appendChild(elt);
		
		return document;
	}

	@Override
	public void paint(Graphics g) {
		g.drawString(this.getClass().getSimpleName(), 0, this.getHeight());
	}

	@Override
	public void giveControle() {
		upperBar.clearMessage();
		upperBar.setMainTitle(father.lg("Settings"));
		upperBar.repaint();

		lowerBar.clearMessage();
		lowerBar.setCenterIcone("home", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				father.setActualState(IhmReceivingStates.MAIN_INTERFACE);
			}
		});
		lowerBar.repaint();
	}

}
