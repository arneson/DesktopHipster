package model;

/**
 * Enum of all hosts. Add new hosts here.
 * 
 * @author Edvard Hübinette
 *
 */
public enum HostsEnum {

	TUMBLR(new Tumblr()),
	FLICKR(new Flickr());
	
	
	private IHost host;
	private HostsEnum(IHost host){
		this.host = host;
	}
	
	public IHost getHost(){
		return host;
	}
}
