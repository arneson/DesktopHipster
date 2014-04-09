package dragNdrop;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.MouseInfo;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;


public class DragNDropTray {
    static DDFrame popup = new DDFrame();
    public DragNDropTray(){
        TrayIcon trayIcon = null;
        popup.setVisible(false);
        
         if (SystemTray.isSupported()) {
             // get the SystemTray instance
             SystemTray tray = SystemTray.getSystemTray();
             // load an image
             Image image = null;
			try {
				image = Toolkit.getDefaultToolkit().getImage(new URL("http://cdn1.iconfinder.com/data/icons/Hypic_Icon_Pack_by_shlyapnikova/16/forum_16.png"));
			} catch (MalformedURLException e1) {
				System.exit(0);
			}
            trayIcon = new TrayIcon(image, "Tray Demo", null);         
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.err.println(e);
            }
            trayIcon.addMouseListener(new MouseAdapter() {
                @Override
            	public void mousePressed(MouseEvent e) {
                    	 if(!popup.isVisible()){
		                	 popup.setLocation(MouseInfo.getPointerInfo().getLocation().x-5,24);
		                     popup.setVisible(true);
		                	 popup.setAlwaysOnTop(true);
                    	 }
                    	 else{
                    		 popup.setVisible(false);
                    	 }
                     }
             });
         } 
    }
    public void addPropertyChangeListener(PropertyChangeListener listener) {
		popup.addPropertyChangeListener(listener);
	}
}
