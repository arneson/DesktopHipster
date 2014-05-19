package view;

import general.PropertyNames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
 * @revised Lovisa Jäberg
 */

@SuppressWarnings("serial")
public class UploadView extends Card implements PropertyChangeListener {
	private final PropertyChangeSupport pcs;

	private JButton saveToDiscButton, backButton, libraryButton;
	private JPanel centerPanel, uploadLogo, saveLogo, saveIcon;
	private JTextField imageName;
	private JFileChooser fileChooser;

	private MouseAdapter myMouseListener = new MouseAdapter() {

		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getSource().equals(imageName)){
				imageName.setText("");
			} else if(e.getSource().equals(saveToDiscButton)){
				saveDialog();
			} else if(e.getSource().equals(libraryButton)){
				//pcs.firePropertyChange(PropertyNames.VIEW_REQUEST_CARD_CHANGE, null, View.SubView.BROWSE);
			} //else if(e.getSource().equals(obj))
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

		libraryButton = new JButton("Back to library");
		libraryButton.addMouseListener(myMouseListener);

		saveToDiscButton = new JButton(new ImageIcon(getClass().getResource("/down.png")));
		saveToDiscButton.setBorder(null);
		saveToDiscButton.setBackground(Constants.BACKGROUNDCOLOR.getColor());
		saveToDiscButton.addMouseListener(myMouseListener);

		/*imageName = new JTextField("Add name..",10);
		imageName.setPreferredSize(new Dimension(50, 20));
		imageName.setFont(new Font("Avenir Next Ultra Light", Font.PLAIN, 14));
		imageName.addMouseListener(myMouseListener);*/

		uploadLogo = new JPanel(new BorderLayout()){{
			add((new JLabel(new ImageIcon(getClass().getResource("/upload.png")))),BorderLayout.CENTER);}};
			uploadLogo.setBackground(Constants.BACKGROUNDCOLOR.getColor());
			uploadLogo.setBackground(Constants.BACKGROUNDCOLOR.getColor());

			centerPanel = new JPanel(new GridLayout(createHostButtons().size()+3,1));
			centerPanel.add(uploadLogo);
			
			for (JButton btn : createHostButtons()){
				centerPanel.add(btn);
			}
			saveLogo = new JPanel(new BorderLayout()){{
				add((new JLabel(new ImageIcon(getClass().getResource("/save.png")))),BorderLayout.CENTER);}};
				saveLogo.setBackground(Constants.BACKGROUNDCOLOR.getColor());

				saveIcon = new JPanel(new BorderLayout()){{add(saveToDiscButton);}};
				saveIcon.setBackground(Constants.BACKGROUNDCOLOR.getColor());

				centerPanel.add(saveLogo);
				centerPanel.add(saveIcon);
				centerPanel.setPreferredSize(new Dimension(200,200));

				backButton = new JButton(new ImageIcon(getClass().getResource("/left.png")));
				backButton.setBorder(new LineBorder(Color.WHITE,10));

				backButton.setBackground(Constants.BACKGROUNDCOLOR.getColor());
				backButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						pcs.firePropertyChange(PropertyNames.VIEW_REQUEST_CARD_CHANGE, null, 
								View.CardState.EDIT.toString());				
					}

				});

				addCenter(centerPanel);
				addWest(new JPanel(new BorderLayout()){{add(backButton,BorderLayout.CENTER);}});
				/*addSouth(new JPanel(){{
					add(imageName);
				}});*/
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
		logChoice(saveChoice, new File(addFileExtIfNecessary(
				fileChooser.getSelectedFile().toPath().toString(),".png")));
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

			btn.setIcon(host.getIcon());
			btn.setPreferredSize(new Dimension(200,100));
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
		file=file.toLowerCase();
		if(!file.endsWith(ext))
			file += ext;

		return file;
	}


	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub

	}
}
