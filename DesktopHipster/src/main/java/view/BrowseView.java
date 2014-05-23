package view;

import general.PropertyNames;

import java.awt.BorderLayout;
import java.awt.Cursor;
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

/**
 * One of the cards used in View.java. This jPanel represents the browse view
 * where the user browses all of his/hers images.
 * 
 * @author Robin Sveningson
 * @revised Simon Arneson
 * @revised Lovisa J��berg
 */
public class BrowseView extends Card implements PropertyChangeListener,
		DropTargetListener {
	private static final long serialVersionUID = 5488743145525577005L;

	private final PropertyChangeSupport pcs;

	private JButton proceedButton;
	private ThumbnailGrid grid;
	private TagsPanel tags;
	@SuppressWarnings("unused")
	private DropTarget dt;
	private JPanel southPanel, centerPanel;

	public BrowseView(PropertyChangeSupport pcs) {
		super();
		this.pcs = pcs;
		initialize();
		pcs.addPropertyChangeListener(grid);
	}

	@SuppressWarnings("serial")
	public void initialize() {
		setBackground(Constants.BACKGROUNDCOLOR.getColor());

		grid = new ThumbnailGrid(pcs);
		dt = new DropTarget(grid, this);
		tags = new TagsPanel(pcs);
		pcs.addPropertyChangeListener(tags);

		ImageIcon proceedImage = new ImageIcon(getClass().getResource(
				"/Images/chooseImage.png"));
		proceedButton = new JButton(proceedImage);
		proceedButton.setBorderPainted(false);
		proceedButton.setBorder(null);
		proceedButton.setBackground(Constants.BACKGROUNDCOLOR.getColor());

		southPanel = new JPanel(new BorderLayout()) {
			{
				add(proceedButton, BorderLayout.CENTER);
			}
		};
		southPanel.setBackground(Constants.BACKGROUNDCOLOR.getColor());
		addSouth(southPanel);

		centerPanel = new JPanel(new GridLayout(1, 1));
		{
			{
				add(grid);
			}
		}
		centerPanel.setBackground(Constants.BACKGROUNDCOLOR.getColor());
		addCenter(centerPanel);
		addWest(tags);

		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				grid.updateVisibleLayer(e);
			}
		});

		addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				grid.mouseWheelMoved(e);
			}
		});
	}

	public void propertyChange(PropertyChangeEvent evt) {
		switch (evt.getPropertyName()) {
		case PropertyNames.MODEL_ACTIVE_IMAGE_CHANGE:
			proceedButton.setIcon(new ImageIcon(getClass().getResource(
					"/Images/proceedButton.jpg")));
			proceedButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			proceedButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					pcs.firePropertyChange(
							PropertyNames.VIEW_REQUEST_CARD_CHANGE, null,
							View.CardState.EDIT.toString());
				}
			});
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
				System.out.println("Possible flavor: "
						+ flavors[i].getMimeType());
				// Check for file lists specifically
				if (flavors[i].isFlavorJavaFileListType()) {
					// Great! Accept copy drops...
					dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);

					// And add the list of file names to our text area
					@SuppressWarnings("rawtypes")
					java.util.List list = (java.util.List) tr
							.getTransferData(flavors[i]);
					for (int j = 0; j < list.size(); j++) {
						// ta.append(list.get(j) + "\n");
						pcs.firePropertyChange(
								PropertyNames.VIEW_ADD_NEW_IMAGE_TO_LIBRARY, null,
								list.get(j));
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
