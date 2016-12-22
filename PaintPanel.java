import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class PaintPanel extends JPanel implements MouseMotionListener, MouseListener{
	ArrayList<ArrayList<Point>> pointList;
	ArrayList<Point> currentList;

	public PaintPanel() {
		// Add the mouse listner
		setBackground(Color.WHITE);
		addMouseListener(this);
		addMouseMotionListener(this);

		// The list that holds all drawn points
		pointList = new ArrayList<>();
		currentList = null;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		currentList.add(e.getPoint());
		// Paint the new point on the screen
		revalidate();
		repaint(getBounds());
	}

	@Override
	public void mouseMoved(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		currentList = new ArrayList<>();

		pointList.add(currentList);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		currentList = null;
	}

	@Override
	public void	mouseClicked(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Draw all the points
		for (ArrayList<Point> lst: pointList) {
			Point last = null;
			for (Point p: lst) {
				// g.drawRect(p.x, p.y, 1, 1);
				if (last != null) {
					g.drawLine(last.x, last.y, p.x, p.y);
				}
				last = p;
			}
		}
	}
}