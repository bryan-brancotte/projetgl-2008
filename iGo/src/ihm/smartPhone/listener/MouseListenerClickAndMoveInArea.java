package ihm.smartPhone.listener;

import ihm.smartPhone.tools.CodeExecutor;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;

public class MouseListenerClickAndMoveInArea implements MouseListener, MouseMotionListener {

	protected Component origin;
	protected LinkedList<AreaAndCodeExecutor> listAreaAndCodeExecutor;
	protected AreaAndCodeExecutor areaAndCodeExecutorPressed = null;
	protected Point PointPressed = null;

	public MouseListenerClickAndMoveInArea(Component origin) {
		super();
		this.origin = origin;
		listAreaAndCodeExecutor = new LinkedList<AreaAndCodeExecutor>();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		for (AreaAndCodeExecutor areaAndCodeExecutor : listAreaAndCodeExecutor) {
			if (areaAndCodeExecutor.area.contains(e.getPoint())) {
				areaAndCodeExecutor.codeExecutor.execute();
				areaAndCodeExecutorPressed = null;
				return;
			}
		}
		areaAndCodeExecutorPressed = null;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for (AreaAndCodeExecutor areaAndCodeExecutor : listAreaAndCodeExecutor) {
			if (areaAndCodeExecutor.area.contains(e.getPoint())) {
				areaAndCodeExecutorPressed = areaAndCodeExecutor;
				PointPressed = e.getPoint();
				return;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (areaAndCodeExecutorPressed == null)
			return;
		if (areaAndCodeExecutorPressed.area.contains(e.getPoint()) && (!PointPressed.equals(e.getPoint()))) {
			areaAndCodeExecutorPressed.codeExecutor.execute();
		}
		areaAndCodeExecutorPressed = null;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		for (AreaAndCodeExecutor areaAndCodeExecutor : listAreaAndCodeExecutor) {
			if (areaAndCodeExecutor.showHand && areaAndCodeExecutor.area.contains(e.getPoint())) {
				this.origin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				return;
			}
		}
		this.origin.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	protected class AreaAndCodeExecutor {
		public Rectangle area;
		public CodeExecutor codeExecutor;
		public boolean showHand;

		public AreaAndCodeExecutor(Rectangle area, CodeExecutor codeExecutor, boolean showHand) {
			super();
			this.area = area;
			this.codeExecutor = codeExecutor;
			this.showHand = showHand;
		}
	}

	public void addInteractiveArea(Rectangle area, CodeExecutor codeExecutor, boolean showHand) {
		listAreaAndCodeExecutor.add(new AreaAndCodeExecutor(area, codeExecutor, showHand));
	}

	public void addInteractiveArea(Rectangle area, CodeExecutor codeExecutor) {
		this.addInteractiveArea(area, codeExecutor, true);
	}

}
