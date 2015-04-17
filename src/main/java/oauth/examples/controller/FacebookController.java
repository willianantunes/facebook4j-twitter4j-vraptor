package oauth.examples.controller;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oauth.examples.component.SessionObjects;

import org.apache.log4j.Logger;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.FacebookException;

@Controller
public class FacebookController
{
	private static Logger logger = Logger.getLogger(FacebookController.class);
	
	@Inject private Result result;
	@Inject private HttpServletRequest request;
	@Inject private HttpServletResponse response;
	@Inject private SessionObjects sessionObjects;
	
	@Get("/")
	public void index()
	{
		
	}
	
	@Get("/facebook/")
	public void initial()
	{
		
	}
	
	@Get("/facebook/show")
	public void show()
	{
		
	}
	
	@Get("/facebook/signin")
	public void signin() throws IOException
	{
		Facebook facebook = new FacebookFactory().getInstance();
		
		// Storing the facebook object for further use
		sessionObjects.setFacebook(facebook);
		
		// Building the correct URL to return to our application
        StringBuffer callbackURL = request.getRequestURL();
        callbackURL.replace(callbackURL.lastIndexOf("/"), callbackURL.length(), "").append("/callback");
        
        // URL to ask for the acceptance of permissions
        // It attaches the URL to return to your application
        String authAuthorizationURL = facebook.getOAuthAuthorizationURL(callbackURL.toString());
        
        result.redirectTo(authAuthorizationURL);
	}
	
	@Get("/facebook/callback")
	public void callback(String code) throws FacebookException
	{
		sessionObjects.getFacebook().getOAuthAccessToken(code);
		result.redirectTo(this).show();
	}
	
	@Get("/facebook/logout")
	public void logout() throws IOException
	{
		String accessToken = sessionObjects.getFacebook().getOAuthAccessToken().getToken();
		
		// Flush up the session
        request.getSession().invalidate();

        // Log out of action
        StringBuffer next = request.getRequestURL();
        next.replace((next.lastIndexOf("/") + 1), next.length(), "");
        result.redirectTo("http://www.facebook.com/logout.php?next=" + next.toString() + "&access_token=" + accessToken);
	}
	
	@Post("/facebook/post")
	public void post(String message) throws FacebookException
	{
		sessionObjects.getFacebook().postStatusMessage(message);
		result.include("messageSuccess", "See your timeline! The message has been posted in your timeline!");
		
		result.redirectTo(this).show();
	}
}
