package model;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JOptionPane;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.FlickrApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

public class Flickr implements IHost {

	private static final String API_KEY = "c06a492a32f8896e4c9c9598c8d17cbb";
	private static final String API_SECRET = "32ac7ec38b1df570";
	private Token accessToken;
	private String authUrl;
	private OAuthService service;
	
	public Flickr(){
		service = new ServiceBuilder()
										.provider(FlickrApi.class)
										.apiKey(API_KEY)
										.apiSecret(API_SECRET)
										.build();
						
		Token requestToken = service.getRequestToken();
		authUrl = service.getAuthorizationUrl(requestToken);
		System.out.println(authUrl+"&perms_read");
		
		
		try {
			Desktop.getDesktop().browse(new URI(authUrl+"&perms=read"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		Verifier verifier = new Verifier(new JOptionPane("", JOptionPane.INFORMATION_MESSAGE).showInputDialog("Paste verifier key here:"));
		
		accessToken = service.getAccessToken(requestToken, verifier);
		
		OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.flickr.com/services/rest/");
	    request.addQuerystringParameter("method", "flickr.test.login");
	    service.signRequest(accessToken, request);
	    Response response = request.send();
	    System.out.println(response.getBody());
	    
	   
		
		
	}
	@Override
	public boolean uploadImage(BufferedImage image) {
		OAuthRequest uploadRequest = new OAuthRequest(Verb.POST, "https://up.flickr.com/services/upload/");
		uploadRequest.addQuerystringParameter("title", "DesktopHipster");
		service.signRequest(accessToken, uploadRequest);
		//uploadRequest.addQuerystringParameter("photo", );
		return false;
	}
}
