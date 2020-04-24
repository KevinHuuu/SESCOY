package com.circleci.demojavaspring;

import static org.junit.Assert.*;

import com.circleci.demojavaspring.model.Quote;
import com.circleci.demojavaspring.repository.TextFileIndexer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.MMapDirectory;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    @Test
    public void addExampleFile () throws IOException {
        StandardAnalyzer analyzer = new StandardAnalyzer();
        Path path = Paths.get("./documents");
        Directory index = new MMapDirectory(path);

        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        IndexWriter w = new IndexWriter(index, config);
        TextFileIndexer textFileIndexer = new TextFileIndexer();
        int numDocs = w.getDocStats().numDocs;
        w.close();
        assertEquals(numDocs, 4);
        w.deleteAll();
        w.close();
    }

}
