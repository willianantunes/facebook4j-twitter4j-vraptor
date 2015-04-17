package oauth.examples.controller;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oauth.examples.component.SessionObjects;

import org.apache.log4j.Logger;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;

@Controller
public class TwitterController
{
	private static Logger logger = Logger.getLogger(TwitterController.class);
	
	@Inject private Result result;
	@Inject private HttpServletRequest request;
	@Inject private HttpServletResponse response;
	@Inject private SessionObjects sessionObjects;
	
	@Get("/twitter/")
	public void initial()
	{
		
	}
	
	@Get("/twitter/show")
	public void show()
	{
		
	}
	
	@Get("/twitter/signin")
	public void signin() throws TwitterException
	{		
		Twitter twitter = new TwitterFactory().getInstance();
		
		// Storing the twitter object for further use
		sessionObjects.setTwitter(twitter);
		
		// Building the correct URL to return to our application
        StringBuffer callbackURL = request.getRequestURL();
        callbackURL.replace(callbackURL.lastIndexOf("/"), callbackURL.length(), "").append("/callback");
        
        // URL to ask for the acceptance of permissions
        // It attaches the URL to return to your application
        RequestToken requestToken = twitter.getOAuthRequestToken(callbackURL.toString());
        sessionObjects.setTwitterRequestToken(requestToken);
        
        result.redirectTo(requestToken.getAuthenticationURL());
	}
	
	@Get("/twitter/callback")
	public void callback(String oauth_verifier) throws TwitterException
	{
		sessionObjects.getTwitter().getOAuthAccessToken(sessionObjects.getTwitterRequestToken(), oauth_verifier);
		sessionObjects.setTwitterRequestToken(null);
		
		result.redirectTo(this).show();
	}
	
	@Get("/twitter/logout")
	public void logout()
	{
		// Flushs up the session
        request.getSession().invalidate();

        result.redirectTo(this).initial();
	}
	
	@Post("/twitter/post")
	public void post(String message) throws TwitterException
	{
		sessionObjects.getTwitter().updateStatus(message);		
		result.include("messageSuccess", "See your twitter account! <br />The message has been posted in it!");
		
		result.redirectTo(this).show();
	}
}
