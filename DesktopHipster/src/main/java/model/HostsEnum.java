package model;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

/**
 * Enum of all hosts. Add new hosts here.
 * 
 * @author Edvard Hübinette
 * 
 */
public enum HostsEnum {

	TUMBLR(new TumblrHost(), new ImageIcon(new TumblrHost().getClass()
			.getResource("/Images/tumblr_icon.png"))), 
			
			
	TWITTER(new TwitterHost(),
			new ImageIcon(new TwitterHost().getClass().getResource(
					"/Images/twitter_icon.png")));


	private IHost host;
	private ImageIcon icon;

	private HostsEnum(IHost host, ImageIcon icon) {
		this.host = host;
		this.icon = icon;
	}

	public IHost getHost() {
		return host;
	}

	public ImageIcon getIcon() {
		return icon;
	}
	
	public ImageIcon getScaledIcon(Dimension d){
		Image image = this.getIcon().getImage();
		BufferedImage bufImage = filter.ImageTools.toBufferedImage(image);
		return new ImageIcon(bufImage.getScaledInstance((int)d.getWidth(), (int)d.getHeight(), BufferedImage.SCALE_SMOOTH));
	}
}
