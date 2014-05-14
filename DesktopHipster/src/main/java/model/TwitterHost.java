package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterHost implements IHost {

	private static final String TMP_PATH		= "/tmp/upload";
	private static final String CONSUMER_KEY	= "NDeL5zA4X6ePH6Ah2x3dIdTRp";
	private static final String CONSUMER_SECRET	= "0DPbqRPEXRhmS4UOILnBtOsNokRTemwZ48ImzdpepKQcLvuKUX";
	private static final String ACCESS_TOKEN	= "168234981-62TkCNlvrTCjYAeW6KKYj6NB6IRBzmWFx8Sme6D3";
	private static final String ACCESS_SECRET	= "xYF1ghJShCVnJNEhrDtETF3xic9u3oMF7rdREdQJog85i";
	Twitter twitter;
	
	public TwitterHost(){
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
			.setOAuthConsumerKey(CONSUMER_KEY)
			.setOAuthConsumerSecret(CONSUMER_SECRET)
			.setOAuthAccessToken(ACCESS_TOKEN)
			.setOAuthAccessTokenSecret(ACCESS_SECRET);

		TwitterFactory tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();
	}
	
	
	@Override
	public boolean uploadImage(BufferedImage image) {
		StatusUpdate status = new StatusUpdate("DesktopHipster");
		File tmp = new File(TMP_PATH);
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(tmp);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		try {
			ImageIO.write(image, "png", out);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		status.setMedia(tmp);
		
		try {
			twitter.updateStatus(status);
		} catch (TwitterException e) {
			e.printStackTrace();
			return false;
		}
		tmp.delete();
		return true;
	}

}
