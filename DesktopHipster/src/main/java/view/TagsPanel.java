package view;

import general.PropertyNames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.Border;

import model.ExtendedImage;

@SuppressWarnings("serial")
public class TagsPanel extends JPanel implements PropertyChangeListener {
	private PropertyChangeSupport pcs;
	private TreeSet<String> tags;
	private List<JCheckBox> tagBoxes;
	private JPanel tagList;
	private boolean imageChosen = false;
	private ActionListener clickListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			JCheckBox src = (JCheckBox) e.getSource();
			if (imageChosen){
				pcs.firePropertyChange(
						PropertyNames.VIEW_TAGS_ON_IMAGE_CHANGED,
						src.isSelected(), src.getText());
				if(src.isSelected())
					src.setBackground(Constants.MARKEDTAGCOLOR.getColor());
				else
					src.setBackground(Constants.BACKGROUNDCOLOR.getColor());
			}
			else {
				TreeSet<String> tagsToShow = new TreeSet<String>();
				for (JCheckBox jcb : tagBoxes) {
					if (jcb.isSelected()){
						tagsToShow.add(jcb.getText());
						//jcb.setBorderPainted(true);
						jcb.setBackground(Constants.MARKEDTAGCOLOR.getColor());
						jcb.repaint();
						jcb.validate();
					}
					else{
						jcb.setBackground(Constants.BACKGROUNDCOLOR.getColor());
						//jcb.setBorderPainted(false);
						jcb.repaint();
						jcb.validate();
					}
						
				}
				pcs.firePropertyChange(
						PropertyNames.VIEW_SHOW_IMAGES_WITH_TAGS, null,
						tagsToShow);
			}
		}
	};

	public TagsPanel(PropertyChangeSupport p) {
		super();
		setLayout(new BorderLayout());
		tagBoxes = new ArrayList<JCheckBox>();
		tagList = new JPanel();
		tagList.setLayout(new BoxLayout(tagList, BoxLayout.Y_AXIS));
		tagList.setBackground(Constants.BACKGROUNDCOLOR.getColor());
		pcs = p;
		pcs.firePropertyChange(PropertyNames.VIEW_ADD_NEW_TAG, null, "All");
		add(new NewTagTextField(pcs), BorderLayout.SOUTH);
	}

	public void loadActiveTagsFromImage(ExtendedImage img) {
		for (JCheckBox cb : tagBoxes){
			cb.setSelected(false);
			//cb.setBorderPainted(false);
			cb.setBackground(Constants.BACKGROUNDCOLOR.getColor());
			cb.repaint();
			cb.validate();
		}
		if (img != null) {
			for (String t : img.getTags()) {
				for (JCheckBox cb : tagBoxes) {
					if (cb.getText().equals(t)) {
						cb.setSelected(true);
						//cb.setBorderPainted(true);
						cb.setBackground(Constants.MARKEDTAGCOLOR.getColor());
						cb.repaint();
						cb.validate();
					}
				}
			}
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		switch (evt.getPropertyName()) {
		case PropertyNames.MODEL_ACTIVE_IMAGE_CHANGE:
			if (evt.getNewValue() != null) {
				ExtendedImage img = (ExtendedImage) evt.getNewValue();
				loadActiveTagsFromImage(img);
				imageChosen = true;
			} else {
				loadActiveTagsFromImage(null);
				imageChosen = false;
			}
			break;
		case PropertyNames.MODEL_TAGS_CHANGED:
			tags = (TreeSet<String>) evt.getNewValue();
			tagList.removeAll();
			tagBoxes.clear();
			for (String t : tags) {
				JCheckBox newTag = new JCheckBox(t);
				giveCheckBoxRightLook(newTag);
				newTag.addActionListener(clickListener);
				tagBoxes.add(newTag);
				tagList.add(newTag);
			}
			add(tagList, BorderLayout.CENTER);
			repaint();
			revalidate();
			break;
		}

	}
	private void giveCheckBoxRightLook(JCheckBox cb){
		cb.setIcon(new ImageIcon(getClass().getResource("/blank.png")));
		cb.setPressedIcon(new ImageIcon(getClass().getResource("/blank.png")));
		cb.setBackground(Constants.BACKGROUNDCOLOR.getColor());
		cb.setOpaque(true);
		
		/*Border raisedbevel = BorderFactory.createRaisedBevelBorder();
		Border loweredbevel = BorderFactory.createLoweredBevelBorder();
		Border compound = BorderFactory.createCompoundBorder(
                raisedbevel, loweredbevel);
		cb.setBorder(compound);*/
	}
}
