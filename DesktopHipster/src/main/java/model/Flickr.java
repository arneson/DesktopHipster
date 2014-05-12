package model;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import org.apache.axis.utils.ByteArrayOutputStream;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.FlickrApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;


public class Flickr implements IHost {

	//Hardcoded to specific user in this implementation
	private static final String API_KEY 	= "c06a492a32f8896e4c9c9598c8d17cbb";
	private static final String API_SECRET 	= "32ac7ec38b1df570";
	private Token accessToken;
	private String authUrl;
	private OAuthService service;
	
	public Flickr(){
		service = new ServiceBuilder()
										.provider(FlickrApi.class)
										.apiKey(API_KEY)
										.apiSecret(API_SECRET)
										.build();
	}
	
	private void auth(){
		Token requestToken = service.getRequestToken();
		authUrl = service.getAuthorizationUrl(requestToken);
		System.out.println("Auth URL:  " + authUrl+"&perms=write");
		
		try {
			Desktop.getDesktop().browse(new URI(authUrl+"&perms=write"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		new JOptionPane("", JOptionPane.INFORMATION_MESSAGE);
		Verifier verifier = new Verifier(JOptionPane.showInputDialog("Paste verifier key here:"));
		accessToken = service.getAccessToken(requestToken, verifier);
		
		OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.flickr.com/services/rest/");
	    request.addQuerystringParameter("method", "flickr.test.login");
	    service.signRequest(accessToken, request);
	    Response response = request.send();
	    System.out.println(response.getBody());
	}
	
	@Override
	public boolean uploadImage(BufferedImage image) {
		auth();
		OAuthRequest uploadRequest = new OAuthRequest(Verb.POST, "https://up.flickr.com/services/upload/");
		//uploadRequest.addQuerystringParameter("title", "DesktopHipster");
		service.signRequest(accessToken, uploadRequest);
		
		File file = new File(TMPFILE_PATH);

		try {
			ImageIO.write(image, "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		
		InputStream in = null;
		try {
			in = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int i;
		byte[] buffer = new byte[1024];
		
		try {
			while ((i =  in.read(buffer)) != -1){
				out.write(buffer, 0, i);
			}
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		byte data[] = out.toByteArray();
		
		FileOutputStream fos = null;
		File outputFile = new File("/tmp/derp");
		
		try {
			fos = new FileOutputStream(outputFile);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			fos.write(data);
			fos.close();
			out.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		//uploadRequest.addBodyParameter("photo", );
		//uploadRequest.addQuerystringParameter("photo", outputFile.getAbsolutePath() );
		//uploadRequest.addBodyParameter("photo", outputFile.getAbsolutePath());;
		Response response = uploadRequest.send();
		System.out.println(response.getBody());
		return true;
	}
}