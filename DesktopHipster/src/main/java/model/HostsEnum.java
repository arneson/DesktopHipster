package model;

import javax.swing.ImageIcon;

/**
 * Enum of all hosts. Add new hosts here.
 * 
 * @author Edvard Hübinette
 *
 */
public enum HostsEnum {

	TUMBLR(new Tumblr(), new ImageIcon(new Tumblr().getClass().getResource("/tumblr_icon.png"))),
	FLICKR(new Flickr(), new ImageIcon(new Flickr().getClass().getResource("/flickr_icon.png")));
	
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
