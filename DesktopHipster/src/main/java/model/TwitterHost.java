package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterHost implements IHost {

	private static final String TMP_PATH = "/tmp/upload";
	private static final String CONSUMER_KEY = "NDeL5zA4X6ePH6Ah2x3dIdTRp";
	private static final String CONSUMER_SECRET = "0DPbqRPEXRhmS4UOILnBtOsNokRTemwZ48ImzdpepKQcLvuKUX";
	private static final String ACCESS_TOKEN = "168234981-62TkCNlvrTCjYAeW6KKYj6NB6IRBzmWFx8Sme6D3";
	private static final String ACCESS_SECRET = "xYF1ghJShCVnJNEhrDtETF3xic9u3oMF7rdREdQJog85i";
	private ConfigurationBuilder cb;
	private TwitterFactory tf;
	private Twitter twitter;
	private boolean authed;

	public TwitterHost() {
		cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey(CONSUMER_KEY)
				.setOAuthConsumerSecret(CONSUMER_SECRET)
				.setOAuthAccessToken(ACCESS_TOKEN)
				.setOAuthAccessTokenSecret(ACCESS_SECRET);
		tf = new TwitterFactory(cb.build());
		auth();
	}

	private boolean auth() {
		try {
			twitter = tf.getInstance();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public void uploadImage(BufferedImage image) throws UploadFailedException {
		if (!authed) {
			if (!auth()) {
				throw new UploadFailedException(
						"Could not authenticate with Twitter, please check your internet connection!");
			}
		}

		StatusUpdate status = new StatusUpdate("DesktopHipster");
		File tmp = new File(TMP_PATH);
		try {
			FileOutputStream out = new FileOutputStream(tmp);
			ImageIO.write(image, "png", out);
			out.flush();
			out.close();
		} catch (IOException e) {
			throw new UploadFailedException();
		}

		status.setMedia(tmp);

		try {
			twitter.updateStatus(status);
		} catch (TwitterException e) {
			throw new UploadFailedException(
					"Could not perform upload, Twitter says this: "
							+ e.getMessage());
		}
		tmp.delete();
	}

}
