package com.thehecklers.keywordscanner;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DistilledTweet {
    private String uri;
    private String text;
}
