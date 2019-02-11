/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.Set;
import java.util.ArrayList;

import org.junit.Test;

public class ExtractTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-17T12:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "NoshuHawk", "I don't have a pro account #noob", d1);
    private static final Tweet tweet2 = new Tweet(2, "DaniyalK123", "@NoshuHawk I have a pro account #pro", d2);
    private static final Tweet tweet3 = new Tweet(2, "Lalee10", "@DaniyalK123 @DaniyalK123 @NoshuHawk I have a pro account too #protoo", d3);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    
    @Test
    public void testGetTimespanZeroTweets() {
    	Timespan timespan = Extract.getTimespan(new ArrayList<Tweet>());
    	assertEquals(timespan.getStart(), Instant.MIN);
        assertEquals(timespan.getEnd(), Instant.MIN);         
    }
    
    @Test
    public void testGetTimespanOneTweet() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1));
        
        assertEquals("Expected start", d1, timespan.getStart());
        assertEquals("Expected end", d1, timespan.getEnd());
    }
    
    @Test
    public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }
    
    @Test
    public void testGetTimespanGreaterThanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet3));
        
        assertEquals("Expected start", d1, timespan.getStart());
        assertEquals("Expected end", d3, timespan.getEnd());
    }
    
    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
        
        assertTrue("Expected empty set", mentionedUsers.isEmpty());
    }
    
    @Test
    public void testGetMentionedUsersSingleMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet2));
        
        assertTrue("Expected username: `noshuhawk`", mentionedUsers.contains("noshuhawk"));
    }
    
    
    @Test
    public void testGetMentionedUsersMultipleMentions() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet3));
        
        assertTrue("Expected username: {`daniyalk123`, `noshuhawk`}", 
        		mentionedUsers.contains("daniyalk123") && 
        		mentionedUsers.contains("noshuhawk"));
    }
    
    @Test
    public void testGetMentionedUsersRepeatedMentions() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet3));
        
        assertTrue("Expected username: {`daniyalk123`, `noshuhawk`}", 
        		mentionedUsers.contains("daniyalk123") && 
        		mentionedUsers.contains("noshuhawk"));
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * Extract class that follows the spec. It will be run against several staff
     * implementations of Extract, which will be done by overwriting
     * (temporarily) your version of Extract with the staff's version.
     * DO NOT strengthen the spec of Extract or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Extract, because that means you're testing a
     * stronger spec than Extract says. If you need such helper methods, define
     * them in a different class. If you only need them in this test class, then
     * keep them in this test class.
     */

}
