/*
 *  ============================================================================================
 *  A1.java : Extends JFrame and contains a panel where shapes move around on the screen.
 *  YOUR UPI: CCOR065 - added FillActionListener, HeightActionListener, WidthActionListener
 *	methods.
 *  ============================================================================================
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.ArrayList;

public class A2  extends JFrame {
	private AnimationViewer panel;  // panel for bouncing area
	JButton fillButton;
	JComboBox<ShapeType> shapesComboBox;
	JComboBox<PathType> pathComboBox;
	JTextField heightTextField, widthTextField;
	/** main method for A2 */
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new A2();
			}
		});
	}
	/** constructor to initialise components */
	public A2() {
		super("Bouncing Application");
		panel = new AnimationViewer(true);
		add(panel, BorderLayout.CENTER);
		panel.setPreferredSize(new Dimension(Shape.DEFAULT_MARGIN_WIDTH, Shape.DEFAULT_MARGIN_HEIGHT));
		add(setUpToolsPanel(), BorderLayout.NORTH);
		addComponentListener(
			new ComponentAdapter() { // resize the frame and reset all margins for all shapes
				public void componentResized(ComponentEvent componentEvent) {
					panel.resetMarginSize();
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = getSize();
		setLocation((d.width - frameSize.width) / 2, (d.height - frameSize.height) / 2);
		pack();
		setVisible(true);
	}
	/** Set up the tools panel
	* @return toolsPanel		the Panel */
	public JPanel setUpToolsPanel() {
		shapesComboBox = new JComboBox<ShapeType>();
		shapesComboBox.setModel(new DefaultComboBoxModel<ShapeType>(ShapeType.values()));
		shapesComboBox.setToolTipText("Set shape");
		shapesComboBox.addActionListener( new ShapeActionListener()) ;
		pathComboBox = new JComboBox<PathType>();
		pathComboBox.setModel(new DefaultComboBoxModel<PathType>(PathType.values()));
		pathComboBox.addActionListener( new PathActionListener());
		heightTextField = new JTextField(""+ Shape.DEFAULT_HEIGHT);
		heightTextField.setToolTipText("Set Height");
		heightTextField.addActionListener( new HeightActionListener());
		///et up the width TextField
		widthTextField = new JTextField(""+ Shape.DEFAULT_WIDTH);
		widthTextField.setToolTipText("Set Width");
		widthTextField.addActionListener( new WidthActionListener());
		//Set up the fill colour button
		fillButton = new JButton("Fill");
		fillButton.setToolTipText("Set Fill Color");
		fillButton.setForeground(panel.getCurrentColor());
		fillButton.addActionListener( new FillActionListener());

		JPanel toolsPanel = new JPanel();
		toolsPanel.setLayout(new BoxLayout(toolsPanel, BoxLayout.X_AXIS));
		toolsPanel.add(new JLabel(" Shape: ", JLabel.RIGHT));
		toolsPanel.add(shapesComboBox);
		toolsPanel.add(new JLabel(" Path: ", JLabel.RIGHT));
		toolsPanel.add(pathComboBox);
		toolsPanel.add(new JLabel(" Width: ", JLabel.RIGHT));
		toolsPanel.add(widthTextField);
		toolsPanel.add( new JLabel(" Height: ", JLabel.RIGHT));
		toolsPanel.add(heightTextField);
		toolsPanel.add(fillButton);
		return toolsPanel;
	}
	class ShapeActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			panel.setCurrentShapeType(shapesComboBox.getSelectedIndex());
		}
	}
	class PathActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			panel.setCurrentPathType(pathComboBox.getSelectedIndex());
		}
	}

	class WidthActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
		int prevWidth = panel.getCurrentWidth();
		try {
			int width = Integer.parseInt(widthTextField.getText());
			if(width >=1 && width <= (Shape.DEFAULT_MARGIN_WIDTH/2)){
				panel.setCurrentWidth(width);
				widthTextField.setText(width + "");
			} else {throw new Exception();}
			}catch (Exception f){
				panel.setCurrentWidth(prevWidth);
				widthTextField.setText(prevWidth + "");
			}
		}
	}

	class HeightActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
		int prevHeight = panel.getCurrentHeight();
		try {
			int height = Integer.parseInt(heightTextField.getText());
			if(height >=1 && height <= (Shape.DEFAULT_MARGIN_HEIGHT/2)){
				panel.setCurrentHeight(height);
				heightTextField.setText(height + "");
			} else {throw new Exception();}
			} catch (Exception f){
				panel.setCurrentHeight(prevHeight);
				heightTextField.setText(prevHeight + "");
			}
		}
	}
	class FillActionListener implements ActionListener {
	public void actionPerformed(ActionEvent e){
		try {
			Color initial = panel.getCurrentColor();
			Color newColor = JColorChooser.showDialog(null, "Fill Color", initial);
			if(newColor == null){throw new Exception();}
			panel.setCurrentColor(newColor);
			fillButton.setForeground(newColor);
		} catch (Exception ex){
			panel.setCurrentColor(panel.getCurrentColor());
			fillButton.setForeground(panel.getCurrentColor());
		}
	}
}




}
