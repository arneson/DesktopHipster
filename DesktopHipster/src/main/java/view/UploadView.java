package view;

import general.PropertyNames;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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

	private JButton proceedButton;
	private JButton saveToDiscButton;
	private JLabel desc;
	private JButton tumblrButton;
	private JButton flickrButton;
	private JTextField imageName;

	public UploadView(PropertyChangeSupport pcs) {
		super();
		initialize();
		this.pcs = pcs;
	}

	public void initialize() {
		setBackground(Constants.BACKGROUNDCOLOR.getColor());
		
		proceedButton = new JButton("proceed");
		saveToDiscButton = new JButton("Save to disc");
		desc = new JLabel("UploadView");
		imageName = new JTextField("Add name..",50);

		imageName.addMouseListener(myMouseListener);

		addNorth(new JPanel(){{add(desc);
		add(proceedButton);
		add(saveToDiscButton);
		}});

		JPanel centerPanel = new JPanel(new GridLayout(2,2));
			//TODO Change size 
			//centerPanel.setPreferredSize(new Dimension(500,500));
		
		for (JButton btn : createHostButtons()){
			centerPanel.add(btn);
		}
		
		addCenter(centerPanel);

		addSouth(new JPanel(){{add(imageName);}});

		proceedButton.addActionListener(new ActionListener(){
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
