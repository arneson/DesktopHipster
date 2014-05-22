package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import javax.imageio.ImageIO;

import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.exceptions.JumblrException;
import com.tumblr.jumblr.types.Blog;
import com.tumblr.jumblr.types.Photo;
import com.tumblr.jumblr.types.PhotoPost;
import com.tumblr.jumblr.types.User;

/**
 * Implementation for uploading images to Tumblr web archive
 * 
 * @author Edvard Hübinette
 * 
 */
public class TumblrHost implements IHost {

	private User user;
	private JumblrClient client;
	private boolean authed;

	public TumblrHost() {
		auth();
	}

	private boolean auth() {
		try {
			client = new JumblrClient(
					"axzIbckazyEegTEcxfMC9PxYvw9I3wxJZohAabAz5uRubK7dCm",
					"S6udACXRQQ7xb3ihAlt7MLHQsVUQ8i60doMjmZM4J3L2qkPASF");

			client.setToken(
					"06knuERQ7Mcw6t4hxAgula4aZI2NjfNju3ZyuCQFuM9kgsVdds",
					"6VeVPyAITqWk1v52VV0n6d1PggTDhKKtv0t5vH97RCnDXpxqxu");
			user = client.user();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void uploadImage(BufferedImage image) throws UploadFailedException {
		if (!authed) {
			if (!auth()) {
				throw new UploadFailedException(
						"Could not authenticate with Tumblr, please check your internet connection.");
			}
		}

		File file = new File(TMPFILE_PATH);

		try {
			ImageIO.write(image, "png", file);
		} catch (IOException e) {
			throw new UploadFailedException();
		}

		try {
			Blog blog = user.getBlogs().get(0);
			PhotoPost post = blog.newPost(PhotoPost.class);
			post.setPhoto(new Photo(file));
			post.setClient(client);
			post.setDate(Calendar.getInstance().getTime());
			post.save();
		} catch (JumblrException e) {
			throw new UploadFailedException(
					"Could not perform upload, Tumblr says this: "
							+ e.getMessage());
		} catch (IOException | InstantiationException | IllegalAccessException e1) {
			throw new UploadFailedException();
		}

		file.delete();
	}
}