package ihm.smartPhone.tools;

import java.awt.Image;
import java.net.URL;
import java.util.concurrent.Semaphore;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;

public class ImageLoader extends Thread {

	private static int nbThreadMax = 2;

	protected String path;

	protected JComponent target;

	protected int width;

	protected int height;

	private static Semaphore available = new Semaphore(nbThreadMax, false);

	public ImageLoader(JComponent target, String path, int width, int height) {
		super();
		this.path = path;
		this.target = target;
		this.width = width;
		this.height = height;

		if (target instanceof JLabel) {
			((JLabel) target).setIcon(getImageIcone(getClass().getResource("/images/time.png"), 16, 16));
			// horizontalAlignment = ((JLabel) target).getHorizontalAlignment();
			((JLabel) target).setHorizontalAlignment(JLabel.CENTER);
		}
		if (target instanceof JButton) {
			((JButton) target).setIcon(getImageIcone(getClass().getResource("/images/time.png"), 16, 16));
			// horizontalAlignment = ((JButton) target).getHorizontalAlignment();
			((JButton) target).setHorizontalAlignment(JLabel.CENTER);
		}
		target.updateUI();
		this.setPriority(3);
	}

	@Override
	public void run() {
		Semaphore old_available = available;
		try {
			old_available.acquire();
		} catch (InterruptedException e) {
			((JLabel) target).setIcon(getImageIcone(getClass().getResource("/images/button_cancel.png")));
			e.printStackTrace();
		}
		if (target instanceof JLabel) {
			((JLabel) target).setIcon(getImageIcone(path, width, height));
			// ((JLabel) target).setHorizontalAlignment(horizontalAlignment);
			target.updateUI();
		}
		if (target instanceof JButton) {
			((JButton) target).setIcon(getImageIcone(path, width, height));
			// ((JButton) target).setHorizontalAlignment(horizontalAlignment);
			target.updateUI();
		}
		old_available.release();
	}

	public static int getNbThreadMax() {
		return nbThreadMax;
	}

	/**
	 * On remplace l'ancien nombre de thread par le nouveau. Tout changement de taille crée une nouvelle file d'attente
	 * qui sera exécuté en parallèle de l'ancienne. Une fois un nouvelle file d'attente créé, l'ancienne file d'attente
	 * se videra puis s'effacera d'elle même.
	 * 
	 * @param nbThreadMax
	 * @throws Exception
	 *             Jette un exception si le nombre de thread est inférieur à 1
	 */
	public static void setNbThreadMax(int nbThreadMax) throws Exception {
		if (nbThreadMax < 1)
			throw new Exception();
		// on remplace l'ancien sémaphore par le nouveau
		// tout nouvelle ajout à la file sera fait indépendament de l'ancienne
		// il est donc important de ne pas changer à la
		ImageLoader.nbThreadMax = nbThreadMax;
		available = new Semaphore(nbThreadMax, false);
	}

	/**
	 * On remplace l'ancien nombre de thread par le nouveau. Tout changement de taille crée une nouvelle file d'attente
	 * qui sera exécuté en parallèle de l'ancienne. Une fois un nouvelle file d'attente créé, l'ancienne file d'attente
	 * se videra puis s'effacera d'elle même.
	 * 
	 * @param nbThreadMax
	 * @throws Exception
	 *             Jette un exception si le nombre de thread est inférieur à 1
	 */
	public static void setNbThreadMax(String sNbThreadMax) throws Exception {
		if (sNbThreadMax == "")
			return;
		try {
			ImageLoader.setNbThreadMax(Integer.parseInt(sNbThreadMax));
		} catch (NumberFormatException e) {
			throw new Exception();
		}
	}

	public static boolean fastLoadingOfImages = false;

	public static ImageIcon getImageIcone(URL path, int width, int height) {
		if ((width == 0) || (height == 0))
			return null;
		ImageIcon ico = getImageIcone(path);
		if ((width == ico.getIconWidth()) && (height == ico.getIconHeight()))
			return ico;
		return getImageIcone(ico, Math.min(width / (float) ico.getIconWidth(), height / (float) ico.getIconHeight()));
	}

	public static ImageIcon getImageIcone(String path, int width, int height) {
		if ((width == 0) || (height == 0))
			return null;
		ImageIcon ico = getImageIcone(path);
		if ((width == ico.getIconWidth()) && (height == ico.getIconHeight()))
			return ico;
		return getImageIcone(ico, Math.min(width / (float) ico.getIconWidth(), height / (float) ico.getIconHeight()));
	}

	public static ImageIcon getRessourcesImageIcone(String name, int width, int height, boolean lowQuality) {
		if (lowQuality)
			return getImageIcone(name.getClass().getResource("/images/" + name + ".png"), width, height);
		else
			return getImageIcone(name.getClass().getResource("/images/" + name + ".128.png"), width, height);
	}

	public static ImageIcon getRessourcesImageIcone(String name, int width, int height) {
		return getRessourcesImageIcone(name, width, height, fastLoadingOfImages);
		// System.out.println("nv Icone : " + name);
	}

	protected static ImageIcon getImageIcone(URL path, int ratio) {
		return getImageIcone(getImageIcone(path), ratio);
	}

	protected static ImageIcon getImageIcone(ImageIcon ico, float ratio) {
		return new ImageIcon(ico.getImage().getScaledInstance((int) (ico.getIconWidth() * ratio),
				(int) (ico.getIconHeight() * ratio), Image.SCALE_SMOOTH));
	}

	public static ImageIcon getImageIcone(URL path) {
		return new ImageIcon(path);
	}

	public static ImageIcon getImageIcone(String path) {
		return new ImageIcon(path);
	}

	public static boolean isFastLoadingOfImages() {
		return fastLoadingOfImages;
	}

	public static void setFastLoadingOfImages(boolean fastLoadingOfImages) {
		ImageLoader.fastLoadingOfImages = fastLoadingOfImages;
	}
}
