
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
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JMenuItem;
import javax.swing.JPanel;

import javax.swing.JPopupMenu;


public class SystemTrayTestMain {
    static DDFrame popup = new DDFrame();
    public static void main(String[] args) throws MalformedURLException {
        TrayIcon trayIcon = null;
        popup.setVisible(false);
        
         if (SystemTray.isSupported()) {
             // get the SystemTray instance
             SystemTray tray = SystemTray.getSystemTray();
             // load an image
             Image image = Toolkit.getDefaultToolkit().getImage(new URL("http://cdn1.iconfinder.com/data/icons/Hypic_Icon_Pack_by_shlyapnikova/16/forum_16.png"));
             // create a action listener to listen for default action executed on the tray icon
             ActionListener listener = new ActionListener() {
                 public void actionPerformed(ActionEvent e) {
                     System.out.println("action");
                     // execute default action of the application
                     // ...
                 }
             };
             // create a popup menu

             // create menu item for the default action
             //JMenuItem defaultItem = new JMenuItem("Do the action");
             //defaultItem.addActionListener(listener);
             //DDFrame testPanel = new DDFrame();
             //testPanel.setPreferredSize(new Dimension(300,300));
             //popup.add(testPanel);
             /// ... add other items
             // construct a TrayIcon
             trayIcon = new TrayIcon(image, "Tray Demo", null);
             // set the TrayIcon properties
             trayIcon.addActionListener(listener);
             // ...
             // add the tray image
             
             try {
                 tray.add(trayIcon);
             } catch (AWTException e) {
                 System.err.println(e);
             }
             trayIcon.addMouseListener(new MouseAdapter() {
                 @Override
            	 public void mousePressed(MouseEvent e) {
                	 //System.out.print("test");
                     if (e.isPopupTrigger()) {
                         //popup.setLocation(e.getX(), e.getY());
                    	 System.out.print(e.getY());
                    	 popup.setLocation(MouseInfo.getPointerInfo().getLocation().x-5,24);
                         //popup.setInvoker(popup);
                         popup.setVisible(true);
                         //System.out.print("test");
                     }
                 }
                 
             });
             // ...
         } else {
             // disable tray option in your application or
             // perform other actions
             //...
         }
         // ...
         // some time later
         // the application state has changed - update the image
         if (trayIcon != null) {
             //trayIcon.setImage(updatedImage);
         }

    }
}
