import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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

		// Open menu item
		JMenuItem fileOpenItem = new JMenuItem("Open");

		// Exit menu item
		JMenuItem fileExitItem = new JMenuItem("Exit");
		fileExitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.dispose();
				System.exit(0);
			}
		});


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
		JToolBar toolBar = new JToolBar();

		JTextField sizeField = new JTextField();

		toolBar.add(sizeField);

		frame.add(toolBar, BorderLayout.NORTH);
	}

}