package dragNdrop;
import java.awt.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;

import javax.swing.*;

import general.PropertyNames;

@SuppressWarnings("serial")
public class DDFrame extends JFrame implements DropTargetListener {

  DropTarget dt;
  JTextArea ta;

  public DDFrame() {
    super("DesktopHipster");
    setSize(300,300);
    addWindowListener(null);
    setUndecorated(true);

    getContentPane().add(
        new JLabel("Drop a list from your file chooser here:"),
  BorderLayout.NORTH);
    ta = new JTextArea();
    ta.setBackground(Color.white);
    add(ta, BorderLayout.CENTER);

    // Set up our text area to recieve drops...
    // This class will handle drop events
    dt = new DropTarget(ta, this);
    setVisible(true);
  }

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
		    ta.setText("Successful file list drop.\n\n");
		    
		    // And add the list of file names to our text area
		    java.util.List list = (java.util.List)tr.getTransferData(flavors[i]);
		    for (int j = 0; j < list.size(); j++) {
		      //ta.append(list.get(j) + "\n");
		    	firePropertyChange(PropertyNames.ADD_NEW_IMAGE_TO_LIBRARY,null,list.get(j));
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
} 
