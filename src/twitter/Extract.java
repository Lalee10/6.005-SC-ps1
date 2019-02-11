/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.util.List;
import java.util.Set;
import java.time.Instant;
import java.util.HashSet;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
    	Instant start = Instant.MAX;
    	Instant end = Instant.MIN;
    	
    	if(tweets.size() == 0) {
    		return new Timespan(Instant.MIN, Instant.MIN);
    	}
    	
    	for(Tweet tweet: tweets){
    		if(tweet.getTimestamp().compareTo(start) <= 0) {
    			start = tweet.getTimestamp();
    		}
    		if(tweet.getTimestamp().compareTo(end) >= 0) {
    			end = tweet.getTimestamp();
    		}
    	}
    	
    	return new Timespan(start, end);
    }

    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        
    	Set<String> stringSet = new HashSet<String>();
    	
    	for(Tweet tweet: tweets) {
        	String text = tweet.getText();
        	Pattern pattern = Pattern.compile("(?<=^|(?<=[^a-zA-Z0-9-_\\.]))@([A-Za-z]+[A-Za-z0-9-_]+)");
        	Matcher matcher = pattern.matcher(text);
        	
        	while(matcher.find()){
        		String username = text.substring(matcher.start() + 1, matcher.end());
        		stringSet.add(username.toLowerCase());
        	}
        }
        return stringSet;
        
    }

}
