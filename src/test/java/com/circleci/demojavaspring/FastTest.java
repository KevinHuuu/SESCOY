package com.circleci.demojavaspring;

import static org.junit.Assert.*;

import com.circleci.demojavaspring.model.Quote;
import org.junit.Test;

public class FastTest {

    @Test
    public void fastTest() throws InterruptedException {
        assertTrue(true);
    }
    @Test
    public void newQuotesTest() throws InterruptedException {
        Quote quote = new Quote();
        quote.setQuote("Hey");
        assertEquals(quote.getQuote(), "Hey");
    }

}
