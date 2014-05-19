package desktopHipster;

import static org.junit.Assert.*;

import javax.swing.ImageIcon;

import model.HostsEnum;
import model.IHost;
import model.TumblrHost;
import model.TwitterHost;

import org.junit.Test;

public class HostsEnumTest {

	@Test
	public void testCreationTumblr() {
		IHost host = HostsEnum.TUMBLR.getHost();
		assertTrue("If the host was instanciated properly and the icon exists",
				HostsEnum.TUMBLR.getIcon() instanceof ImageIcon && host instanceof TumblrHost);
	}

	@Test
	public void testCreationTwitter() {
		IHost host = HostsEnum.TWITTER.getHost();
		assertTrue("If the host was instanciated properly and the icon exists",
				HostsEnum.TWITTER.getIcon() instanceof ImageIcon && host instanceof TwitterHost);
	}

}
