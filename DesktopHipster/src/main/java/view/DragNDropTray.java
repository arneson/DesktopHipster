package view;
/**
 * Class to represent and add the system tray icon of the application
 * 
 * @author Simon Arneson
 */

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
	private static int popPosition =(int)((Toolkit.getDefaultToolkit().getScreenSize().width)*0.8);
	public DragNDropTray() {
		TrayIcon trayIcon = null;
		popup.setVisible(false);

		if (SystemTray.isSupported()) {
			// get the SystemTray instance,
			SystemTray tray = SystemTray.getSystemTray();
			// load an image
			Image image = null;
			image = Toolkit.getDefaultToolkit().getImage(
					getClass().getResource("/desktop_hipster.png"));
			trayIcon = new TrayIcon(image, "Tray Demo", null);
			try {
				tray.add(trayIcon);
			} catch (AWTException e) {
				System.err.println(e);
			}
			trayIcon.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					if (!popup.isVisible()) {
						popup.setLocation(MouseInfo.getPointerInfo()
								.getLocation().x - 5, 24);
						popPosition =MouseInfo.getPointerInfo()
								.getLocation().x - 5;
						popup.setVisible(true);
						popup.setAlwaysOnTop(true);
					} else {
						popup.setVisible(false);
					}
				}
			});
		}
	}
	/**
	 * Used to add a PropertyChangeListener to the popup window.
	 * 
	 * @param listener
	 *            The Object to listen to changes in the popup(The drag and drop frame)
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		popup.addPropertyChangeListener(listener);
	}
	public int getPopPosition(){
		return popPosition;
	}
}
