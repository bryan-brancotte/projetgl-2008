package ihm.smartPhone.tools;

import java.awt.Font;
import java.awt.Toolkit;

public interface SizeAdapteur {

	public static final double VERTICAL_RATIO = 0.75;

	public static final int maxHeightForScalling = 640;
	// public static final int DEFAULT_HEIGTH = 220;public static final int DEFAULT_WIDTH = 140;/*
	// public static final int DEFAULT_HEIGTH = 320;public static final int DEFAULT_WIDTH = 240;/*
	public static final int DEFAULT_HEIGTH = 640;
	public static final int DEFAULT_WIDTH = 480;/**/

	public static final int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;

	public static final int screenHeigth = Toolkit.getDefaultToolkit().getScreenSize().height;

	public boolean isFullScreen();

	public int getHeight();

	public int getWidth();

	public int getSizeLargeFont();
	
	public int getSizeIntermediateFont();

	public int getSizeSmallFont();

	public Font getLargeFont();
	
	public Font getIntermediateFont();

	public Font getSmallFont();

	public int getSizeUpperBar();

	public int getSizeLowerBar();

	public int getSizeLine();

	public boolean isEnableSizeLine();
	

	public enum FontSizeKind {
		LARGE(1), INTERMEDIATE(2), SMALL(3);

		protected int val;

		private FontSizeKind(int val) {
			this.val = val;
		}

		public int getValue() {
			return this.val;
		}

		/**
		 * Surcharge de equals pour s'assuré que la comparaison sera bien faite.
		 */
		public boolean equals(FontSizeKind ev) {
			return (this.getValue() == ev.getValue());
		}

	}

}
