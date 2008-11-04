package ihm.smartPhone.composants;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;

public abstract class PanelDoubleBufferingSoftwear extends Panel {

	protected Graphics buffer = null;

	protected Image image = null;

	protected Panel me = this;

//	private SemaphorePaint renderingClamp = null;
//
//	private boolean enableRenderingClamp = false;
//
//	protected boolean isEnableRenderingClamp() {
//		return enableRenderingClamp;
//	}
//
//	protected void setEnableRenderingClamp(boolean enableRenderingClamp) {
//		this.enableRenderingClamp = enableRenderingClamp;
//		if (this.enableRenderingClamp)
//			renderingClamp = new SemaphorePaint();
//		else
//			renderingClamp = null;
//	}
//
//	protected boolean tryAcquireRenderingClamp() {
//		if (!enableRenderingClamp)
//			return true;
//		return renderingClamp.tryAcquire();
//	}
//
//	protected void releaseRenderingClamp() {
//		if (enableRenderingClamp)
//			renderingClamp.release();
//	}

	/**
	 * Surdéfinition de la fonction de redessinement de l'objet.
	 */
	public abstract void paint(Graphics g);

	/**
	 * Retourne pour un chaine donnée sa hauteur dessinée.
	 * 
	 * @param s
	 *            la chaine
	 * @param g
	 *            le Graphics où elle sera dessiné
	 * @param f
	 *            la police utilisé
	 * @return la hauteur dessiné de la chaine
	 */
	protected int getHeigthString(String s, Graphics g, Font f) {
		return f.getSize();
	}

	/**
	 * Retourne pour un chaine donnée sa longueur dessinée.
	 * 
	 * @param s
	 *            la chaine
	 * @param g
	 *            le Graphics où elle sera dessiné
	 * @param f
	 *            la police utilisé
	 * @return la longueur dessiné de la chaine
	 */
	protected int getWidthString(String s, Graphics g, Font f) {
		return (int) (s.length() * f.getSize() * 0.45);
	}

	protected String decomposeMinutesIntoHourMinutes(int minutes, String sHour, String sMinutes) {
		int i = minutes / 60;
		return i + " " + sHour + (minutes - i * 60) + " " + sMinutes;
	}

	/**
	 * Retourne l'image contenant le dessin du panel, si la classe implémentante s'en sert effectivement comme un panel
	 * avec double buffering softwear
	 * 
	 * @return l'image où est dessiner l'affichage du panel
	 */
	public Image getImage() {
		return image;
	}

//	class SemaphorePaint extends Thread {
//		private Semaphore renderingClamp = new Semaphore(1);
//
//		public SemaphorePaint() {
//			super();
//		}
//
//		public boolean tryAcquire() {
//			return renderingClamp.tryAcquire();
//		}
//
//		public void release() {
//			(new ExecMultiThread<Semaphore>(this.renderingClamp) {
//
//				@Override
//				public void run() {
//					try {
//						sleep(100);
//					} catch (InterruptedException e) {
//					}
//					this.origine.release();
//				}
//			}).start();
//
//			// renderingClamp.release();
//			//
//		}
//	}
}
