package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import javax.imageio.ImageIO;

import org.scribe.exceptions.OAuthConnectionException;

import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.types.Blog;
import com.tumblr.jumblr.types.Photo;
import com.tumblr.jumblr.types.PhotoPost;
import com.tumblr.jumblr.types.User;

public class Tumblr implements IHost {

	User user;
	JumblrClient client;

	public Tumblr(){
		// Authenticate via OAuth
		client = new JumblrClient(
		  "axzIbckazyEegTEcxfMC9PxYvw9I3wxJZohAabAz5uRubK7dCm",
		  "S6udACXRQQ7xb3ihAlt7MLHQsVUQ8i60doMjmZM4J3L2qkPASF"
		);

		client.setToken(
		  "06knuERQ7Mcw6t4hxAgula4aZI2NjfNju3ZyuCQFuM9kgsVdds",
		  "6VeVPyAITqWk1v52VV0n6d1PggTDhKKtv0t5vH97RCnDXpxqxu"
		);
		user = client.user();
	}
	
	public boolean uploadImage(BufferedImage image) {
		File file = new File("/tmp/DHtmp");
		try {
			ImageIO.write(image, "jpg", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Blog blog = user.getBlogs().get(0);
		PhotoPost post = null;
		
		try {
			post = blog.newPost(PhotoPost.class);
		} catch (IllegalAccessException e1) {
			// This should really not happen
			System.out.println("Boom1");
		} catch (InstantiationException e1) {
			// This should really not happen
			System.out.println("Boom2");
		}
		
		post.setPhoto(new Photo(file));
		post.setClient(client);
		post.setDate(Calendar.getInstance().getTime());
		
		try {
			post.save();
		} catch (IOException e) {
			// This should really not happen
			e.printStackTrace();
		}
		// Needed?
		//file.delete();
		return false;
	}
}