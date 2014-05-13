package view;

import general.PropertyNames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.LineBorder;

import model.Flickr;
import model.HostsEnum;
import model.Tumblr;

/**
 * The upload view is a card that is used by View.java. It's main
 * purpose is to select which site to upload the image to.
 * 
 * @author Robin Sveningson
 * @revised Lovisa Jäberg
 */

@SuppressWarnings("serial")
public class UploadView extends Card implements PropertyChangeListener {
	private final PropertyChangeSupport pcs;

	private JButton libraryButton;
	private JButton saveToDiscButton, backButton;
	private JLabel logo;
	private JTextField imageName;

	public UploadView(PropertyChangeSupport pcs) {
		super();
		initialize();
		this.pcs = pcs;
	}

	public void initialize() {
		setBackground(Constants.BACKGROUNDCOLOR.getColor());

		libraryButton = new JButton("Back to library");
		ImageIcon downloadImage = new ImageIcon(getClass().getResource("/down.png"));
		saveToDiscButton = new JButton(downloadImage);
		saveToDiscButton.setBorder(null);
		
		imageName = new JTextField("Add name..",50);

		imageName.addMouseListener(myMouseListener);

		LayoutManager layout = new GridLayout(createHostButtons().size(),1);
		
		JPanel centerPanel = new JPanel(layout);
		
		for (JButton btn : createHostButtons()){
			centerPanel.add(btn);
		}

		
		/*ImageIcon logoImage = new ImageIcon(getClass().getResource("/desktophipster_logo.png"));
		logo = new JLabel(logoImage);
		logo.setOpaque(true);
		logo.setBackground(Constants.BACKGROUNDCOLOR.getColor());
*/

		ImageIcon backImage = new ImageIcon(getClass().getResource("/left.png"));
		backButton = new JButton(backImage);
		backButton.setBorder(new LineBorder(Color.WHITE,10));
		backButton.setBackground(Constants.BACKGROUNDCOLOR.getColor());
		backButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				pcs.firePropertyChange(PropertyNames.VIEW_REQUEST_CARD_CHANGE, null, View.SubView.EDIT);				
			}
		});
		
		
		/*addNorth(new JPanel(new BorderLayout()){{
			add(logo,BorderLayout.NORTH);
		}});
		*/
		
		addCenter(centerPanel);
		addWest(new JPanel(new BorderLayout()){{add(backButton,BorderLayout.CENTER);}});
		addSouth(new JPanel(){{add(imageName);}});
		
		libraryButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				pcs.firePropertyChange(PropertyNames.VIEW_REQUEST_CARD_CHANGE, null, View.SubView.BROWSE);
			}
		});
		
		//TODO 
		saveToDiscButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(imageName.getText().equals("") || imageName.getText().equals("Add name..")){
					pcs.firePropertyChange(PropertyNames.VIEW_SAVE_IMAGE_TO_DISC, null, e.getID());
				} else {
					pcs.firePropertyChange(PropertyNames.VIEW_SAVE_IMAGE_TO_DISC, null, imageName.getText());
				}
			}
		});

	}

	private MouseListener myMouseListener = new MouseListener() {

		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getSource().equals(imageName)){
				imageName.setText("");
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	};
	
	/**
	 * Returns a list of buttons for all existing hosts.
	 * @return List of all host buttons
	 */
	private List<JButton> createHostButtons(){
		ArrayList<JButton> list = new ArrayList<JButton>();
		for(final HostsEnum host : HostsEnum.values()){
			JButton btn = new JButton();
			btn.setSize(new Dimension(250, 250));
			btn.setIcon(host.getIcon());
			btn.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					pcs.firePropertyChange(PropertyNames.VIEW_UPLOAD_ACTIVE_IMAGE, null, host.getHost());
				}
			});
			list.add(btn);
		}
		return list;
	}
	
	public void propertyChange(PropertyChangeEvent evt) {

	}

}
