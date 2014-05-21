package view;

import general.PropertyNames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import model.ExtendedImage;

/**
 * Panel in GUI containing the tags and actions made on tags
 * 
 * @authour Simon Arneson
 * @revised Robin Sveningson
 */

@SuppressWarnings("serial")
public class TagsPanel extends JPanel implements PropertyChangeListener {
	private PropertyChangeSupport pcs;
	private TreeSet<String> tags;
	private List<TagItem> items;
	private JPanel tagPanel;
	private JScrollPane scroll;
	private boolean imageChosen = false;
	private TreeSet<String> tagsToShow = new TreeSet<String>();;
	private final int height = 40;
	private MouseListener ma = new MouseAdapter() {
		@Override
		public void mouseReleased(MouseEvent e) {
			TagItem src = (TagItem) e.getSource();
			src.setSelected(!src.isSelected());
			if (imageChosen){
				pcs.firePropertyChange(
						PropertyNames.VIEW_TAGS_ON_IMAGE_CHANGED,
						src.isSelected(), src.getText());
			}
			else {
				tagsToShow.clear();
				for (TagItem item : getItems()) {
					if (item.isSelected()){
						tagsToShow.add(item.getText());
					}	
				}
				pcs.firePropertyChange(
						PropertyNames.VIEW_SHOW_IMAGES_WITH_TAGS, null,
						tagsToShow);
			}
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			TagItem src = (TagItem) e.getSource();
			src.setRemoveIconVisible(true);
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			TagItem src = (TagItem) e.getSource();
			src.setRemoveIconVisible(false);
		}
	};

	public TagsPanel(PropertyChangeSupport p) {
		super();
		pcs = p;
		items = new ArrayList<TagItem>();
		initialize();
	}
	
	private void initialize() {
		setLayout(new BorderLayout());
		
		tagPanel = new JPanel();
		tagPanel.setBackground(Constants.BACKGROUNDCOLOR.getColor());
		tagPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
		
		scroll = new JScrollPane(tagPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBorder(null);
		
		add(scroll, BorderLayout.CENTER);
		add(new NewTagTextField(pcs, height), BorderLayout.SOUTH);
	}
	/**
	 * Method in charge of determining which tags should be 
	 * marked when an image is selected
	 * @Param The ExtendedImage from which tags should be loaded from.
	 */
	public void loadActiveTagsFromImage(ExtendedImage img) {
		for (TagItem item : getItems()){
			item.setSelected(false);
		}
		if (img != null) {
			for (String t : img.getTags()) {
				for (TagItem item : getItems()) {
					if (item.getText().equals(t)) {
						item.setSelected(true);
					}
				}
			}
		} else {
			for(String t : tagsToShow) {
				for(TagItem item : getItems()) {
					if(item.getText().equals(t)) {
						item.setSelected(true);
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		switch (evt.getPropertyName()) {
		//Fires when the model changes it's active image
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
		//Fires when a tag is added or removed from the library
		case PropertyNames.MODEL_TAGS_CHANGED:
			tags = (TreeSet<String>) evt.getNewValue();
			updateTagList(tags);
			break;
		}

	}
	/**
	 * Method in charge of marking the tags which are sent in.
	 * @Param Tags to be marked in TagsPanel
	 */
	private void updateTagList(TreeSet<String> tags) {
		getItems().clear();
		
		tagPanel.setLayout(new BoxLayout(tagPanel, BoxLayout.PAGE_AXIS));
		tagPanel.setPreferredSize(new Dimension(getWidth(), tags.size()*height));
		tagPanel.removeAll();
		
		int i = 0;
		for(String t : tags) {
			TagItem item = new TagItem(pcs, t, height, getWidth(), i%2==0);
			item.addMouseListener(ma);
			getItems().add(item);
			tagPanel.add(item);
			i++;
		}
		
		tagPanel.revalidate();
		tagPanel.repaint();
	}

	public List<TagItem> getItems() {
		return items;
	}

	public int getNrOfTags() {
		return items.size();
	}

	public int getNrOfSelectedTags() {
		int count = 0;
		for(TagItem ti: items)
			if(ti.isSelected())
				count++;
		return count;
	}
	

}
