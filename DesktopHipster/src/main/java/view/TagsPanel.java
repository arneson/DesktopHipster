package view;

import general.PropertyNames;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import model.ExtendedImage;

@SuppressWarnings("serial")
public class TagsPanel extends JPanel implements PropertyChangeListener {
	private PropertyChangeSupport pcs;
	private TreeSet<String> tags;
	private List<JCheckBox> tagBoxes;
	private JPanel tagList;
	private ActionListener clickListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
        	JCheckBox src = (JCheckBox)e.getSource();
            pcs.firePropertyChange(PropertyNames.VIEW_TAGS_ON_IMAGE_CHANGED, null, src.getText() );
        }
    };
	public TagsPanel(PropertyChangeSupport p){
		super();
		setLayout(new BorderLayout());
		tagBoxes=new ArrayList<JCheckBox>();
		tagList = new JPanel();
		tagList.setLayout(new BoxLayout(tagList, BoxLayout.Y_AXIS));
		tagList.setBackground(Constants.BACKGROUNDCOLOR.getColor());
		pcs = p;
		pcs.firePropertyChange(PropertyNames.VIEW_ADD_NEW_TAG, null, "All");
		add(new NewTagTextField(pcs),BorderLayout.SOUTH);
	}
	public void loadActiveTagsFromImage(ExtendedImage img){
		for(JCheckBox cb:tagBoxes)
			cb.setSelected(false);
		for(String t:img.getTags()){
			for(JCheckBox cb:tagBoxes){
				if(cb.getText()==t){
					cb.setSelected(true);
				}
			}
		}
		repaint();
		revalidate();
	}
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		switch(evt.getPropertyName()){
		case PropertyNames.MODEL_ACTIVE_IMAGE_CHANGE:
			ExtendedImage img = (ExtendedImage) evt.getNewValue();
			loadActiveTagsFromImage(img);
			break;
		case PropertyNames.MODEL_TAGS_CHANGED:
			tags = (TreeSet<String>)evt.getNewValue();
			tagList.removeAll();
			tagBoxes.clear();
			for(String t:tags){
				JCheckBox newTag = new JCheckBox(t);
				newTag.addActionListener(clickListener);
				tagBoxes.add(newTag);
				tagList.add(newTag);
			}
			add(tagList,BorderLayout.CENTER);
			repaint();
			revalidate();
			break;
		}
		
	}
}
