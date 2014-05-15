package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBarFactory {
	public static void createMenuBar(final JFrame frame) {
		JMenuBar main = new JMenuBar();
		JMenu tools = new JMenu("Tools");
		JMenuItem settings = new JMenuItem("Settings");

		settings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

		tools.add(settings);
		main.add(tools);
		frame.setJMenuBar(main);
	}
}
