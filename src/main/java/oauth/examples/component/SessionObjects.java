package oauth.examples.component;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import twitter4j.Twitter;
import twitter4j.auth.RequestToken;
import facebook4j.Facebook;

@SessionScoped
@Named("sessionObjects")
public class SessionObjects implements Serializable
{
	private static final long serialVersionUID = -4479049427907244695L;
	private Facebook facebook;
	private Twitter twitter;
	private RequestToken twitterRequestToken;

	public Facebook getFacebook()
	{
		return facebook;
	}

	public void setFacebook(Facebook facebook)
	{
		this.facebook = facebook;
	}

	public Twitter getTwitter()
	{
		return twitter;
	}

	public void setTwitter(Twitter twitter)
	{
		this.twitter = twitter;
	}

	public RequestToken getTwitterRequestToken()
	{
		return twitterRequestToken;
	}

	public void setTwitterRequestToken(RequestToken twitterRequestToken)
	{
		this.twitterRequestToken = twitterRequestToken;
	}
}
