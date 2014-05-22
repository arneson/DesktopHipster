package view;

import general.PropertyNames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.HostsEnum;

/**
 * The upload view is a card that is used by View.java. It's main purpose is to
 * select which site to upload the image to.
 * 
 * @author Robin Sveningson
 * @revised Lovisa J��berg
 */

@SuppressWarnings("serial")
public class UploadView extends Card implements PropertyChangeListener {
	private final PropertyChangeSupport pcs;

	private JButton saveToDiscButton, backButton, libraryButton;
	private JPanel hostButtonPanel, centerPanel, uploadLogo, saveLogo, saveIcon;
	private JTextField imageName;
	private JFileChooser fileChooser;
	private final int ICONWIDTH = 350;

	private MouseAdapter myMouseListener = new MouseAdapter() {

		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getSource().equals(imageName)){
				imageName.setText("");
			} else if(e.getSource().equals(saveToDiscButton)){
				saveDialog();
			} else if(e.getSource().equals(libraryButton)){
				pcs.firePropertyChange(PropertyNames.VIEW_REQUEST_CARD_CHANGE, null, View.CardState.BROWSE.toString());
				pcs.firePropertyChange(PropertyNames.VIEW_ACTIVE_FILTER_CHANGE, null, null);
			}
		}
	};

	public UploadView(PropertyChangeSupport pcs) {
		super();
		initialize();
		this.pcs = pcs;

	}

	public void initialize() {
		setBackground(Constants.BACKGROUNDCOLOR.getColor());

		fileChooser = new JFileChooser();
		libraryButton = new JButton(new ImageIcon(getClass().getResource("/libraryImage.png")));
		libraryButton.setPreferredSize(new Dimension(100,100));
		libraryButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		libraryButton.addMouseListener(myMouseListener);
		libraryButton.setBorder(new LineBorder(Color.WHITE,10));
		libraryButton.setBackground(Constants.BACKGROUNDCOLOR.getColor());

		saveToDiscButton = new JButton(new ImageIcon(getClass().getResource("/down_mint.png")));
		saveToDiscButton.setBorder(null);
		saveToDiscButton.setBackground(Constants.BACKGROUNDCOLOR.getColor());
		saveToDiscButton.addMouseListener(myMouseListener);
		saveToDiscButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

		uploadLogo = new JPanel(new BorderLayout()){{
			add((new JLabel(new ImageIcon(getClass().getResource("/upload.png")))),BorderLayout.CENTER);}};
			uploadLogo.setBackground(Constants.BACKGROUNDCOLOR.getColor());

			centerPanel = new JPanel(new BorderLayout());
			centerPanel.add(uploadLogo,BorderLayout.NORTH);

			hostButtonPanel = new JPanel(new GridLayout(createHostButtons().size()+1,1));
			hostButtonPanel.setBackground(Constants.BACKGROUNDCOLOR.getColor());
			for (JButton btn : createHostButtons()){
				hostButtonPanel.add(btn);
			}
			saveLogo = new JPanel(new BorderLayout()){{
				add((new JLabel(new ImageIcon(getClass().getResource("/save.png")))),BorderLayout.CENTER);
			}};
			saveLogo.setBackground(Constants.BACKGROUNDCOLOR.getColor());

			saveIcon = new JPanel(new BorderLayout()){{add(saveToDiscButton);}};
			saveIcon.setBackground(Constants.BACKGROUNDCOLOR.getColor());

			hostButtonPanel.add(saveLogo);

			backButton = new JButton(new ImageIcon(getClass().getResource("/left.png")));
			backButton.setBorder(new LineBorder(Color.WHITE,10));
			backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			backButton.setBackground(Constants.BACKGROUNDCOLOR.getColor());
			backButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					pcs.firePropertyChange(PropertyNames.VIEW_REQUEST_CARD_CHANGE, null, 
							View.CardState.EDIT.toString());				
				}

			});

			centerPanel.add(hostButtonPanel,BorderLayout.CENTER);
			addCenter(centerPanel);
			addWest(new JPanel(new BorderLayout()){{add(backButton,BorderLayout.CENTER);}});
			addEast(new JPanel(new BorderLayout()){{
				add(libraryButton,BorderLayout.CENTER);
			}});
			addSouth(new JPanel(new BorderLayout()){{
			add(saveIcon,BorderLayout.NORTH);
			}});
	}

	/**
	 * Shows save dialog to be able to save file as you wish.
	 */
	private void saveDialog()
	{
		fileChooser.setAcceptAllFileFilterUsed(false);

		FileFilter imageFilter = new FileNameExtensionFilter(
				"Image files", ImageIO.getReaderFileSuffixes());
		fileChooser.addChoosableFileFilter(imageFilter);

		int saveChoice = fileChooser.showSaveDialog(this);
		if(saveChoice == JFileChooser.APPROVE_OPTION || 
				saveChoice == JFileChooser.CANCEL_OPTION ||
				saveChoice == JFileChooser.ERROR_OPTION) {
			logChoice(saveChoice, new File(addFileExtIfNecessary(
					fileChooser.getSelectedFile().toPath().toString(),".png")));
		}

	}

	/**
	 * Logs the choice done by the user according to 
	 * where to save the file and in which name.
	 * @param choice
	 * @param fileName
	 */
	private void logChoice(int choice, File file)
	{
		switch (choice)
		{
		case JFileChooser.CANCEL_OPTION:
			break;
		case JFileChooser.APPROVE_OPTION:
			pcs.firePropertyChange(PropertyNames.VIEW_SAVE_IMAGE_TO_DISC, null, file);
			break;
		case JFileChooser.ERROR_OPTION:
			new JOptionPane();
			JOptionPane.showMessageDialog(null, "alert", "alert", JOptionPane.ERROR_MESSAGE);
			break;
		}
	}

	/**
	 * Returns a list of buttons for all existing hosts.
	 * 
	 * @return List of all host buttons
	 */
	private List<JButton> createHostButtons() {
		ArrayList<JButton> list = new ArrayList<JButton>();
		for(final HostsEnum host : HostsEnum.values()){
			JButton btn = new JButton(host.getIcon());
			Dimension btnDimension = new Dimension(ICONWIDTH, -1);
			btn.setIcon(host.getScaledIcon(btnDimension));
			btn.setPreferredSize(btnDimension);
			btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
			btn.setBorder(null);
			btn.setBackground(Constants.BACKGROUNDCOLOR.getColor());
			btn.setOpaque(true);
			btn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					pcs.firePropertyChange(
							PropertyNames.VIEW_UPLOAD_ACTIVE_IMAGE, null,
							host.getHost());
				}
			});
			list.add(btn);
		}
		return list;
	}

	private String addFileExtIfNecessary(String file,String ext) {
		//file=file.toLowerCase();
		if(!file.endsWith(ext))
			file += ext;
		return file;
	}


	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub

	}
}
