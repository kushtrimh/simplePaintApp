import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.image.BufferedImage;

class PaintPanel extends JPanel implements MouseMotionListener, MouseListener{
	private ArrayList<ArrayList<Point>> pointList;
	private ArrayList<Point> currentList;
	private ArrayList<Float> lineWidthList;
	private ArrayList<Color> colorList;

	private float lineWidth;
	private Color lineColor;
	private BufferedImage loadImage;
	private boolean firstTimeLoading;

	public PaintPanel() {
		// Add the mouse listner
		setBackground(Color.WHITE);
		setFocusable(true);
		addMouseListener(this);
		addMouseMotionListener(this);

		// Initialize the lists that holds all drawn points
		// colors, and linewidths
		resetLists();

		// Initialize settings
		lineWidth = 1;
		lineColor = Color.BLACK;
		loadImage = null;
		firstTimeLoading = false;
	}

	private void resetLists() {
		// The list that holds all drawn points
		pointList = new ArrayList<>();
		lineWidthList = new ArrayList<>();
		colorList = new ArrayList<>();
		currentList = null;

	}

	public void setLineWidth(float width) {
		lineWidth = width;
	}

	public void setColor(Color c) {
		lineColor = c;
	}

	public void clean() {
		// Clean the background
		loadImage = null;
		resetLists();
		repaint();
	}

	public void displayImage(BufferedImage toLoad) {
		loadImage = toLoad;
		firstTimeLoading = true;
		repaint();
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
		requestFocusInWindow();
		currentList = new ArrayList<>();
		// Add the current line width
		// and the current color
		lineWidthList.add(lineWidth);
		colorList.add(lineColor);

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
		Graphics2D gg = (Graphics2D) g;

		// Check to see if an image is going to be loaded
		// if true, reset the lists and display the image
		// into the panel
		if (loadImage != null) {
			if (firstTimeLoading) {
				resetLists();
				firstTimeLoading = false;
			}

			gg.drawImage(loadImage, 0, 0, this);
		}

		// Draw all the points
		int index = 0;
		for (ArrayList<Point> lst: pointList) {
			Point last = null;
			// Get the line width for the current list
			// of drawn points
			float width = lineWidthList.get(index);
			Color color = colorList.get(index++);
			// Set the line width and color
			gg.setStroke(new BasicStroke(width));
			gg.setColor(color);
			for (Point p: lst) {
				// g.drawRect(p.x, p.y, 1, 1);
				if (last != null) {
					gg.drawLine(last.x, last.y, p.x, p.y);
				}
				last = p;
			}
		}
	}
}