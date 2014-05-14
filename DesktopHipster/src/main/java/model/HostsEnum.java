package model;

import javax.swing.ImageIcon;

/**
 * Enum of all hosts. Add new hosts here.
 * 
 * @author Edvard Hübinette
 *
 */
public enum HostsEnum {

	TUMBLR(new TumblrHost(), new ImageIcon(new TumblrHost().getClass().getResource("/tumblr_icon.png"))),
	TWITTER(new TwitterHost(), new ImageIcon(new TwitterHost().getClass().getResource("/twitter_icon.png")));
	//FLICKR(new FlickrHost(), new ImageIcon(new FlickrHost().getClass().getResource("/flickr_icon.png")))
	
	private IHost host;
	private ImageIcon icon;
	private HostsEnum(IHost host, ImageIcon icon){
		this.host = host;
		this.icon = icon;
	}
	
	public IHost getHost(){
		return host;
	}
	
	public ImageIcon getIcon(){
		return icon;
	}
}
