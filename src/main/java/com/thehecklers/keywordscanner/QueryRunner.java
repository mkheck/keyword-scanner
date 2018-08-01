package com.thehecklers.keywordscanner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import twitter4j.*;

import java.util.List;

@Component
public class QueryRunner implements ApplicationRunner {
    private final String CLI_PARAMETER_NAME = "keyword";
    private final String CLI_REPLACE_WITH = "replace";
    private Twitter twitter;

    public QueryRunner(TweetController controller) {
        this.twitter = controller.getTwitter();
    }

    @Override
    public void run(ApplicationArguments args) {
        String keyword;

        if (args.getOptionNames().contains(CLI_PARAMETER_NAME)) {
            keyword = args.getOptionValues(CLI_PARAMETER_NAME).get(0); // For now, just accept one keyword
        } else {
            keyword = "cryptography"; // Just run the query for tweets containing this word
        }

        Query query = new Query(keyword);
        try {
            QueryResult result = twitter.search(query);
            List<Status> tweets = result.getTweets();

            if (args.getOptionNames().contains(CLI_REPLACE_WITH)) {
                String replaceWith = args.getOptionValues(CLI_REPLACE_WITH).get(0);
                tweets.stream()
                        .forEach(tweet -> System.out.println(tweet.getText().replaceAll("(?i)"+keyword, replaceWith)));
            } else {
                tweets.stream().forEach(tweet -> System.out.println(tweet.getText()));
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }
}
