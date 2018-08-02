package com.thehecklers.keywordscanner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import javax.annotation.PostConstruct;

@Component
public class TwitterTemplate {
    private Twitter twitter;

    private final String consumerKey, consumerSecret, accessToken, accessTokenSecret;

    public TwitterTemplate(@Value("${twitter.key:''}") final String consumerKey,
                           @Value("${twitter.secret:''}") final String consumerSecret,
                           @Value("${twitter.token:''}") final String accessToken,
                           @Value("${twitter.token.secret:''}") final String accessTokenSecret) {
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.accessToken = accessToken;
        this.accessTokenSecret = accessTokenSecret;
    }

    @PostConstruct
    public void init() {
        this.twitter = TwitterFactory.getSingleton();
        this.twitter.setOAuthConsumer(this.consumerKey, this.consumerSecret);
        this.twitter.setOAuthAccessToken(new AccessToken(this.accessToken, this.accessTokenSecret));
    }

    public final Twitter getTwitter() {
        return twitter;
    }
}
