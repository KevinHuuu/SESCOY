package com.circleci.demojavaspring;

import static org.junit.Assert.*;

import com.circleci.demojavaspring.model.Quote;
import com.circleci.demojavaspring.repository.TextFileIndexer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.MMapDirectory;
import org.apache.lucene.util.QueryBuilder;
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
    public void addExampleFile() throws IOException {
        StandardAnalyzer analyzer = new StandardAnalyzer();
        Path path = Paths.get("./documents");
        Directory index = new MMapDirectory(path);

        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        IndexWriter w = new IndexWriter(index, config);
        TextFileIndexer textFileIndexer = new TextFileIndexer();
        textFileIndexer.addDoc(w, "Lucene in Action", "193398817");
        textFileIndexer.addDoc(w, "Lucene for Dummies", "55320055Z");
        textFileIndexer.addDoc(w, "Managing Gigabytes", "55063554A");
        textFileIndexer.addDoc(w, "The Art of Computer Science", "9900333X");
        int numDocs = w.getDocStats().numDocs;
        // delete the test added document
        w.deleteAll();
        w.close();
        assertEquals(4, numDocs);
    }

    @Test
    public void searchExampleFile() throws IOException {
        // construct document
        StandardAnalyzer analyzer = new StandardAnalyzer();
        Path path = Paths.get("./documents");
        Directory index = new MMapDirectory(path);

        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        IndexWriter w = new IndexWriter(index, config);
        TextFileIndexer textFileIndexer = new TextFileIndexer();
//        textFileIndexer.addDoc(w, "Lucene in Action", "193398817");
//        textFileIndexer.addDoc(w, "Lucene for Dummies", "55320055Z");
//        textFileIndexer.addDoc(w, "Managing Gigabytes", "55063554A");
//        textFileIndexer.addDoc(w, "The Art of Computer Science", "9900333X");
//        textFileIndexer.addDoc(w, "The Art of Lacquer", "2900333X");
        int numDocs = w.getDocStats().numDocs;
        w.close();

        // search document by query
        final var queryStr = "Art";
        final int maxHits = 100;
//        Path path = Paths.get("./documents");
//        Directory index = new MMapDirectory(path);

        IndexReader indexReader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(indexReader);
//        Analyzer analyzer = new StandardAnalyzer();
        QueryBuilder builder = new QueryBuilder(analyzer);
        Query query = builder.createBooleanQuery("title", queryStr);
        TopDocs topDocs = searcher.search(query, maxHits);
        ScoreDoc[] hits = topDocs.scoreDocs;

        for (int i = 0; i < hits.length; i++) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            System.out.println(d.get("title") + " Score :" + hits[i].score);
        }
        System.out.println("Found " + hits.length);
        assertEquals(2, hits.length);
        //delete all docs
        w.deleteAll();
        w.close();
    }
}


