package view;

import general.PropertyNames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.Color;


/**
 * One of the cards used in View.java. This jPanel represents
 * the browse view where the user browses all of his/hers 
 * images.
 * 
 * @author Robin Sveningson
 * @revised Simon Arneson
 * @revised Lovisa J��berg
 */
@SuppressWarnings("serial")
public class BrowseView extends Card implements PropertyChangeListener,DropTargetListener {
	private final PropertyChangeSupport pcs;

	private JButton proceedButton;
	private ThumbnailGrid grid;
	private TagsPanel tags;
	private DropTarget dt;
	private JLabel logo;

	public BrowseView(PropertyChangeSupport pcs) {
		super();
		this.pcs = pcs;
		initialize();
		pcs.addPropertyChangeListener(grid);
	}

	public void initialize() {
		setBackground(Constants.BACKGROUNDCOLOR.getColor());

		ImageIcon proceedImage = new ImageIcon(getClass().getResource("/proceedButton.jpg"));
		proceedButton = new JButton(proceedImage);
		proceedButton.setBorderPainted(false);
		proceedButton.setBorder(null);
		proceedButton.setBackground(Constants.BACKGROUNDCOLOR.getColor());
		proceedButton.setEnabled(false);
		proceedButton.setSize(500, 200);

		grid = new ThumbnailGrid(pcs);	
		dt = new DropTarget(grid,this);
		tags = new TagsPanel(pcs);
		pcs.addPropertyChangeListener(tags);

		/*ImageIcon logoImage = new ImageIcon(getClass().getResource("/desktophipster_logo.png"));
		logo = new JLabel(logoImage);
		logo.setOpaque(true);
		logo.setBackground(Constants.BACKGROUNDCOLOR.getColor());
		JPanel northPanel = new JPanel(new BorderLayout());{{
			add(logo,BorderLayout.NORTH);
			add(proceedButton,BorderLayout.SOUTH);
		}};
		northPanel.setBorder(new LineBorder(Color.WHITE, 50));
		northPanel.setBackground(Constants.BACKGROUNDCOLOR.getColor());
		 */
		JPanel centerPanel = new JPanel(new GridLayout(1,1));{{
			add(grid);}}
		centerPanel.setBackground(Constants.BACKGROUNDCOLOR.getColor());
		addCenter(centerPanel);
		addWest(tags);
		JPanel southPanel = new JPanel(new BorderLayout()){{
			add(proceedButton,BorderLayout.CENTER);
		}};
		addSouth(southPanel);

		proceedButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				pcs.firePropertyChange(PropertyNames.VIEW_REQUEST_CARD_CHANGE, null, View.SubView.EDIT);
			}
		});


		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				grid.updateVisibleLayer(e);
			}
		});

		addMouseWheelListener(new MouseWheelListener(){
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				grid.mouseWheelMoved(e);
			}
		});
	}

	public void propertyChange(PropertyChangeEvent evt) {
		switch(evt.getPropertyName()){
		case PropertyNames.MODEL_ACTIVE_IMAGE_CHANGE:
			proceedButton.setEnabled(true);
			break;
		}
	}

	public void calculateGridWidth() {
		grid.frameRezise();
	}

	@Override
	public void dragEnter(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dragOver(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dragExit(DropTargetEvent dte) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drop(DropTargetDropEvent dtde) {
		try {
			// Ok, get the dropped object and try to figure out what it is
			Transferable tr = dtde.getTransferable();
			DataFlavor[] flavors = tr.getTransferDataFlavors();
			for (int i = 0; i < flavors.length; i++) {
				System.out.println("Possible flavor: " + flavors[i].getMimeType());
				// Check for file lists specifically
				if (flavors[i].isFlavorJavaFileListType()) {
					// Great!  Accept copy drops...
					dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);

					// And add the list of file names to our text area
					java.util.List list = (java.util.List)tr.getTransferData(flavors[i]);
					for (int j = 0; j < list.size(); j++) {
						//ta.append(list.get(j) + "\n");
						pcs.firePropertyChange(PropertyNames.ADD_NEW_IMAGE_TO_LIBRARY,null,list.get(j));
					}

					dtde.dropComplete(true);
					return;
				}
			}
			// Not a file-list dropped
			dtde.rejectDrop();
		} catch (Exception e) {
			e.printStackTrace();
			dtde.rejectDrop();
		}

	}
}
