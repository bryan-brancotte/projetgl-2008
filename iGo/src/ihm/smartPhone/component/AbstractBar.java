package ihm.smartPhone.component;

import java.awt.Graphics;
import java.util.concurrent.Semaphore;

import ihm.smartPhone.IGoIhmSmartPhone;
import ihm.smartPhone.libPT.PanelDoubleBufferingSoftwear;

public abstract class AbstractBar extends PanelDoubleBufferingSoftwear {
	
	/**
	 * Utilise ce vérrou dès que vous touchez à une image
	 */
	protected Semaphore lookDraw = new Semaphore(1);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected IGoIhmSmartPhone ihm;

	public AbstractBar(IGoIhmSmartPhone ihm) {
		this.ihm = ihm;
	}

	public abstract void clearMessage();
	
	/**
	 * Méthode de dessin appelé par draw
	 */
	public abstract void draw();

	@Override
	public void paint(Graphics g) {
		lookDraw.acquireUninterruptibly();
		draw();
		/***************************************************************************************************************
		 * fin du dessin en mémoire, on dessine le résultat sur l'écran
		 */
		g.drawImage(image, 0, 0, null);
		lookDraw.release();
	}
}
