package view;

import java.awt.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

import general.PropertyNames;

/**
 * Frame allowing files to be dropped on it and then sends them to the application 
 * if they can be processed there.
 * @author Simon Arneson
 */

@SuppressWarnings("serial")
public class DDFrame extends JFrame implements DropTargetListener {

  DropTarget dt;
  JTextArea ta;
  JLabel dropArea;
	
	/**
	 * Creates a new DDFrame and sets up the DropEvent listeners.
	 * 
	 */
  public DDFrame() {
    super("DesktopHipster");
    setSize(200,140);
    addWindowListener(null);
    setUndecorated(true);

    ta = new JTextArea();
    
    dropArea = new JLabel(new ImageIcon(getClass().getResource("/AddPanel.png")));
    
    ta.setBackground(Color.white);
    add(dropArea, BorderLayout.CENTER);

    dt = new DropTarget(dropArea, this);
    setVisible(true);
  }
	/**
	 * This method is fired when a file/file list is dropped in the DDFrame.
	 * It fires an event saying a new image has requested to be added to the library
	 * @param dtde
	 *            The DropTargetEvent containing the tranferable file
	 */
  public void drop(DropTargetDropEvent dtde) {
    try {
      Transferable tr = dtde.getTransferable();
      DataFlavor[] flavors = tr.getTransferDataFlavors();
      //Checks if the dropped object is a Java File List Type
      for (int i = 0; i < flavors.length; i++) {
		  if (flavors[i].isFlavorJavaFileListType()) {
		    dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
		    //Adds all the files dropped to a list
		    java.util.List droppedFiles = (java.util.List)tr.getTransferData(flavors[i]);
		    boolean valid = true;
		    for (int j = 0; j < droppedFiles.size(); j++) {
		    	if(isAcceptedImage(droppedFiles.get(j))){
		    		firePropertyChange(PropertyNames.VIEW_ADD_NEW_IMAGE_TO_LIBRARY,null,droppedFiles.get(j));
		    		setDropOKLogo();
		    	}
		    	else{
		    		setDropNotOKLogo();
		    	}
		    }
		    dtde.dropComplete(true);
		    return;
		  }
      }
      dtde.rejectDrop();
    } catch (Exception e) {
      e.printStackTrace();
      dtde.rejectDrop();
    }
  }

	/**
	 * The method is used to check if an file can be handled by the Application or not
	 * 
	 * @param object
	 *            The object to be controlled
	 * @returns Whether the file is allowed or not
	 */
	public boolean isAcceptedImage(Object object) {
	try {
		File imgFile = (File)object;
	    Image img =ImageIO.read(imgFile);
	    if (img == null || imgFile.getAbsolutePath().endsWith(".tiff")) {
	        return false;
	    }
	    else{
	    	return true;
	    }
	} catch(IOException ex) {
	    return false;
	}
}

	@Override
	public void dragEnter(DropTargetDragEvent dtde) {
		//Used to show the user if he has dropped and accepted file or not.
		dropArea.setIcon(new ImageIcon(getClass()
				.getResource("/AddPanelOK.png")));
	}
	@Override
	public void dragOver(DropTargetDragEvent dtde) {
		//Has to be here to implement interface.
	}

	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {
		//Has to be here to implement interface.
	}

	@Override
	public void dragExit(DropTargetEvent dte) {
		dropArea.setIcon(new ImageIcon(getClass().getResource("/AddPanel.png")));
	}
	/**
	 * Sets the Drop Logo in the DDFrame to accepted file
	 * 
	 */
	public void setDropOKLogo() {
		dropArea.setIcon(new ImageIcon(getClass().getResource("/AddPanel.png")));
	}

	/**
	 * Sets the Drop Logo in the DDFrame to unaccepted file
	 * 
	 */
	public void setDropNotOKLogo() {
		dropArea.setIcon(new ImageIcon(getClass().getResource(
				"/AddPanelNOK.png")));
	}
}
