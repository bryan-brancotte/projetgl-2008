package ihm.smartPhone.tools;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

public abstract class ImageLoader {

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
		if (lowQuality || height <= 32 && width <= 32)
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
