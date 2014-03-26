package filter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;


@SuppressWarnings("serial")
public class FilterTestClass extends JFrame {
	private JPanel actionPanel;
	private JButton orig, grayscale, sepia, invertedGrayscale, external;
	private Canvas canvas;
	private Filter filter;
	
	public FilterTestClass() throws IOException {
		initialize();	
	}
	
	private void initialize() {
		canvas = new Canvas();
		filter = new Filter();
		actionPanel = new JPanel();
		
		actionPanel.setPreferredSize(new Dimension(0, 50));
		setBounds(new Rectangle(0,0,1000,700));
		setPreferredSize(new Dimension(400,400));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ImageIcon icon = new ImageIcon(getClass().getResource("/robin.jpg"));
		BufferedImage img = new BufferedImage(icon.getIconWidth(),icon.getIconHeight(),BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.createGraphics();
		icon.paintIcon(null, g, 0, 0);
		canvas.setOriginalImage(img);
		
		orig = new JButton("Orignal");
		orig.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				canvas.setOriginalVisible(true);
			}
			@Override public void mousePressed(MouseEvent e) {}
			@Override public void mouseReleased(MouseEvent e) {}
			@Override public void mouseEntered(MouseEvent e) {}
			@Override public void mouseExited(MouseEvent e) {}
		});
		
		grayscale = new JButton("Grayscale");
		grayscale.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				canvas.setFilterImage(filter.applyGrayscale(canvas.getOriginalImage()));
			}
			@Override public void mousePressed(MouseEvent e) {}
			@Override public void mouseReleased(MouseEvent e) {}
			@Override public void mouseEntered(MouseEvent e) {}
			@Override public void mouseExited(MouseEvent e) {}
		});
		
		sepia = new JButton("Sepia");
		sepia.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				canvas.setFilterImage(filter.applySepia(canvas.getOriginalImage()));
			}
			@Override public void mousePressed(MouseEvent e) {}
			@Override public void mouseReleased(MouseEvent e) {}
			@Override public void mouseEntered(MouseEvent e) {}
			@Override public void mouseExited(MouseEvent e) {}
		});
		
		invertedGrayscale = new JButton("Inverted grayscale");
		invertedGrayscale.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				canvas.setFilterImage(filter.applyInvertedGrayscale(canvas.getOriginalImage()));
			}
			@Override public void mousePressed(MouseEvent e) {}
			@Override public void mouseReleased(MouseEvent e) {}
			@Override public void mouseEntered(MouseEvent e) {}
			@Override public void mouseExited(MouseEvent e) {}
		});
		
		final JFrame f = this;
		external = new JButton("External");
		external.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser fc = new JFileChooser();
				int result = fc.showOpenDialog(f);
		        if (result == JFileChooser.APPROVE_OPTION) {
		        	File file = fc.getSelectedFile();
		        	try {
						BufferedImage img = ImageIO.read(file);
						canvas.setFilterImage(filter.applyLayer(canvas.getOriginalImage(),img));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
		        }
			}
			@Override public void mousePressed(MouseEvent e) {}
			@Override public void mouseReleased(MouseEvent e) {}
			@Override public void mouseEntered(MouseEvent e) {}
			@Override public void mouseExited(MouseEvent e) {}
		});
		
		actionPanel.add(orig);
		actionPanel.add(grayscale);
		actionPanel.add(sepia);
		actionPanel.add(invertedGrayscale);
		actionPanel.add(external);
		
		setLayout(new BorderLayout());
		add(actionPanel, BorderLayout.NORTH);
		add(canvas, BorderLayout.CENTER);
		setVisible(true);
	}
	public static void main(String[] args) throws IOException {
		@SuppressWarnings("unused")
		FilterTestClass m = new FilterTestClass();
	}
}
