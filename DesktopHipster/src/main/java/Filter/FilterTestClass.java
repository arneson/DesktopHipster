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
	
	public FilterTestClass() throws IOException {
		initialize();	
	}
	
	private void initialize() {
		canvas = new Canvas();
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
			public void mouseClicked(MouseEvent e) {
				canvas.setOriginalVisible(true);
			}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
		});
		
		grayscale = new JButton("Grayscale");
		grayscale.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				canvas.setFilterImage(ImageTools.applyGrayscale(canvas.getOriginalImage()));
			}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
		});
		
		sepia = new JButton("Sepia");
		sepia.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				canvas.setFilterImage(ImageTools.applySepia(canvas.getOriginalImage()));
			}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
		});
		
		invertedGrayscale = new JButton("Inverted grayscale");
		invertedGrayscale.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				//canvas.setFilterImage(ImageTools.applyInvertedGrayscale(canvas.getOriginalImage()));
				//canvas.setFilterImage(ImageTools.applyEnhancedColors(canvas.getOriginalImage()));
				ImageIcon oldImageIcon = new ImageIcon(getClass().getResource("/OldImageLayer.jpg"));
				BufferedImage oldImageLayer = new BufferedImage(oldImageIcon.getIconWidth(),oldImageIcon.getIconHeight(),
						BufferedImage.TYPE_INT_ARGB);
				Graphics g = oldImageLayer.createGraphics();
				oldImageIcon.paintIcon(null, g, 0, 0);
				canvas.setFilterImage(Filter.oldImage(canvas.getOriginalImage(), oldImageLayer));
			}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
		});
		
		final JFrame f = this;
		external = new JButton("External");
		external.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				JFileChooser fc = new JFileChooser();
				int result = fc.showOpenDialog(f);
		        if (result == JFileChooser.APPROVE_OPTION) {
		        	File file = fc.getSelectedFile();
		        	try {
						BufferedImage img = ImageIO.read(file);
						canvas.setFilterImage(ImageTools.applyLayer(canvas.getOriginalImage(),img));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
		        }
			}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
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
