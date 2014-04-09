/**
    HW #3:    GUI - Writing a Java application with Swing components
    Filename: BasicWindowMonitor.java - A program that uses a simple 
              extension to the WindowAdapter class to close the window
              on cue.
    @date:    September 27, 2000 
    @author:  Tiehu Jiang
    @see:     Book "JAVA Swing", Robert Eckstein, etc. pp.24, 1998, O'Reilly
 */
 
import java.awt.event.*;
import java.awt.Window;

public class BasicWindowMonitor extends WindowAdapter {
    public void windowClosing(WindowEvent e) {
        Window w = e.getWindow();
        w.setVisible(false);
        w.dispose();
        System.exit(0);
    }
}
