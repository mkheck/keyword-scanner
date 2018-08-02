package com.thehecklers.keywordscanner;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import twitter4j.Query;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TwitterController {
    private final Twitter twitter;

    public TwitterController(TwitterTemplate template) {
        this.twitter = template.getTwitter();
    }

    @PostMapping("/search")
    public String getTweets(@RequestParam(required = false) String keyword,
                            @RequestParam(required = false) String replace,
                            Model model) {
        List<Status> tweets;

        if (keyword != null) {
            Query query = new Query(keyword);
            try {
                tweets = twitter.search(query).getTweets();

                List<DistilledTweet> distilledTweets = new ArrayList<>();

                if (replace != null) {
                    for (Status tweet: tweets) {
                        distilledTweets.add(new DistilledTweet("https://twitter.com/" + tweet.getUser().getScreenName() + "/status/" + tweet.getId(),
                                tweet.getText().replaceAll("(?i)"+keyword, replace)));
                    }

                } else {
                    for (Status tweet: tweets) {
                        distilledTweets.add(new DistilledTweet("https://twitter.com/" + tweet.getUser().getScreenName() + "/status/" + tweet.getId(),
                                tweet.getText()));
                    }
                }
                model.addAttribute("Tweets", distilledTweets);

            } catch (TwitterException e) {
                e.printStackTrace();
            }
        }

        return "index";
    }
}
