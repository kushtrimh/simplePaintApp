import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;

public class PaintApp {

	private JFrame frame;
	private PaintPanel paint;
	private JPanel mainPanel;

	public PaintApp() {
		frame = new JFrame();
		mainPanel = new JPanel();
		paint = new PaintPanel();

		setGUI();

		// Set the frame options
		frame.setSize(960, 640);
		frame.setTitle("PaintApp");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new PaintApp();
	}

	/* Saves the drawing created in the drawing panel */
	private void save() {
		// Create the BufferedImage instance
		BufferedImage image = new BufferedImage(paint.getWidth(), 
												paint.getHeight(), 
												BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		paint.paint(g);

		// Chose a filename for the image
		JFileChooser fch = new JFileChooser();
		fch.showSaveDialog(frame);
		File fl = fch.getSelectedFile();

		// If filename was not choosed
		// do nothing and return
		if (fl == null) {
			return;
		}

		try {
			// Save the file to the choosen name
			ImageIO.write(image, "png", fl);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/* Loads an image to display it on the Paint panel */
	private void load() {
		// Get the image we want to load
		JFileChooser fch = new JFileChooser();
		// Add filter to open only png files
		fch.setFileFilter(new FileNameExtensionFilter("PNG Files", "png"));
		fch.showOpenDialog(frame);

		File fl = fch.getSelectedFile();
		if (fl == null) {
			return;
		}

		// Read the image and pass it into the
		// paint panel to diplay it
		BufferedImage img = null;
		try {
			img = ImageIO.read(fl);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		paint.displayImage(img);
	}

	private void setGUI() {
		// Add menu
		buildMenu();

		// Add toolbar
		buildToolbar();

		// Add the paint panel to our frame
		frame.add(paint, BorderLayout.CENTER);
	}

    /*
	* Builds the Menu for the paint app
	* and sets actions for each menu item.
    */
	private void buildMenu() {
		JMenuBar menuBar = new JMenuBar();

		/* File menu section */
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);

		// Save menu item
		JMenuItem fileSaveItem = new JMenuItem("Save");
		fileSaveItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});

		// Open menu item
		JMenuItem fileOpenItem = new JMenuItem("Open");
		fileOpenItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				load();
			}
		});

		// Exit menu item
		JMenuItem fileExitItem = new JMenuItem("Exit");
		fileExitItem.addActionListener(new ExitAction());


		fileMenu.add(fileSaveItem);
		fileMenu.add(fileOpenItem);
		fileMenu.addSeparator();
		fileMenu.add(fileExitItem);

		menuBar.add(fileMenu);
		/* End of file menu section */


		// Add menubar to frame
		frame.setJMenuBar(menuBar);
	}

	/*
	* Builds the toolbar and sets event listeners
	* for buttons and text fields in the toolbar.
	*/
	public void buildToolbar() {
		JToolBar toolbar = new JToolBar();
		toolbar.setLayout(new FlowLayout(FlowLayout.LEADING));
		toolbar.setFloatable(false);

		// Add the exit button
		JButton exitBtn = new JButton();
		exitBtn.addActionListener(new ExitAction());
		exitBtn.setIcon(new ImageIcon("images/exitimg.png"));
		exitBtn.setToolTipText("Exit");
		
		toolbar.add(exitBtn);

		// Clear painting panel button
		JButton clearButton = new JButton("Clear");
		clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				paint.clean();
			}
		});

		toolbar.add(clearButton);

		// Change color button
		JButton colorButton = new JButton();
		colorButton.setIcon(new ImageIcon("images/color.png"));
		colorButton.setToolTipText("Choose Color");

		colorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Color c = JColorChooser.showDialog(frame, "Select Line Color", 
														Color.WHITE);
				// If the returned color is not null
				// set the line color as the selected color
				if (c != null) {
					paint.setColor(c);
				}
			}
		});

		toolbar.add(colorButton);

		// Pencil Button
		JButton pencilButton = new JButton();
		pencilButton.setIcon(new ImageIcon("images/pencil.png"));
		pencilButton.addActionListener(new ChangeColorListener(Color.BLACK));

		toolbar.add(pencilButton);

		// Eraser Button
		JButton eraserButton = new JButton();
		eraserButton.setIcon(new ImageIcon("images/eraser.png"));
		eraserButton.addActionListener(new ChangeColorListener(Color.WHITE));

		toolbar.add(eraserButton);

		// Set focus event for the sizeField so once
		// the focus is out of this field the size of 
		// the line width is changed automatically
		JTextField sizeField = new JTextField("Size", 10);

		sizeField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				// Try to change the text we got into float
				try {
					float width = Float.parseFloat(sizeField.getText());
					paint.setLineWidth(width);
				} catch (NumberFormatException ex) {}
			}

			@Override
			public void focusGained(FocusEvent e) {}
		});
		toolbar.add(sizeField);

		frame.add(toolbar, BorderLayout.NORTH);
		frame.requestFocus();
	}

	// Listener classes
	private class ChangeColorListener implements ActionListener {
		Color color;

		public ChangeColorListener(Color c) {
			color = c;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			paint.setColor(color);
		}
	}

	private class ExitAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.setVisible(false);
			frame.dispose();
			System.exit(0);
		}
	}
}