package ihm.smartPhone.tools;

import java.awt.Color;

public abstract class ColorTools {

	public static Color getColorFromHSV(int H, int S, int V) {
		return getColorFromHSV(H, S / 100.0F, V / 100.0F);
	}

	protected static Color newColor(float r, float v, float b) {
		if (r > 1)
			r = 1;
		if (v > 1)
			v = 1;
		if (b > 1)
			b = 1;
		return new Color(r, v, b);
	}

	public static Color getColorFromHSV(int H, float S, float V) {
		int hi = (int) ((H / 60.0F) % 6);
		float f = (H / 60.0F) - hi;
		float p = V * (1F - S);
		switch (hi) {
		case 0:
			// System.out.println(H + ":" + V + "," + V * (1 - (S - f) * S) + "," + p);
			return newColor(V, V * (1 - (S - f) * S), p);
		case 1:
			// System.out.println(H + ":" + V * (1 - f * S) + "," + V + "," + p);
			return newColor(V * (1 - f * S), V, p);
		case 2:
			// System.out.println(H + ":" + p + "," + V + "," + V * (1 - (S - f) * S));
			return newColor(p, V, V * (1 - (S - f) * S));
		case 3:
			// System.out.println(H + ":" + p + "," + V * (1 - f * S) + "," + V);
			return newColor(p, V * (1 - f * S), V);
		case 4:
			// System.out.println(H + ":" + V * (1 - (S - f) * S) + "," + p + "," + V);
			return newColor(V * (1 - (S - f) * S), p, V);
		default:
			// System.out.println(H + ":" + V + "," + p + "," + V * (1 - f * S));
			return newColor(V, p, V * (1 - f * S));
		}
	}
}
