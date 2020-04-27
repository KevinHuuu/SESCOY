package com.circleci.demojavaspring;

import com.circleci.demojavaspring.model.Quote;
import com.circleci.demojavaspring.model.Snippet;
import com.circleci.demojavaspring.repository.TextFileIndexer;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        TextFileIndexer textFileIndexer = new TextFileIndexer();
        IndexWriter w = textFileIndexer.InitIndexWriter("./documents");
        textFileIndexer.addExampleDoc(w, "Lucene in Action", "193398817");
        textFileIndexer.addExampleDoc(w, "Lucene for Dummies", "55320055Z");
        textFileIndexer.addExampleDoc(w, "Managing Gigabytes", "55063554A");
        textFileIndexer.addExampleDoc(w, "The Art of Computer Science", "9900333X");
        int numDocs = w.getDocStats().numDocs;
        // delete the test added document
        textFileIndexer.IndexWriterDeleteAll(w);
        textFileIndexer.CloseIndexWriter(w);
        assertEquals(4, numDocs);
    }


    @Test
    public void searchExampleFile() throws IOException {
        String filePath = "./documents";
        // construct document
        TextFileIndexer textFileIndexer = new TextFileIndexer();
        IndexWriter w = textFileIndexer.InitIndexWriter(filePath);
        textFileIndexer.addExampleDoc(w, "Lucene in Action", "193398817");
        textFileIndexer.addExampleDoc(w, "Lucene for Dummies", "55320055Z");
        textFileIndexer.addExampleDoc(w, "Managing Gigabytes", "55063554A");
        textFileIndexer.addExampleDoc(w, "The Art of Computer Science", "9900333X");
        textFileIndexer.addExampleDoc(w, "The Art of Lacquer", "2900333X");
        int numDocs = w.getDocStats().numDocs;
        textFileIndexer.CloseIndexWriter(w);

        // search document by query
        final var queryStr = "Art";
        final int maxHits = 100;
        final String field = "title";
        IndexSearcher searcher = textFileIndexer.InitIndexReader(filePath);
        Query query = textFileIndexer.InitQuery(field, queryStr);
        TopDocs topDocs = searcher.search(query, maxHits);
        ScoreDoc[] hits = topDocs.scoreDocs;

        for (int i = 0; i < hits.length; i++) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            System.out.println(d.get("title") + " Score :" + hits[i].score);
        }
        System.out.println("Found " + hits.length);
        assertEquals(2, hits.length);


        // Delete all generated docs after the test is finished
        IndexWriter wForDeleteAll = textFileIndexer.InitIndexWriter(filePath);
        textFileIndexer.IndexWriterDeleteAll(wForDeleteAll);
        textFileIndexer.CloseIndexWriter(wForDeleteAll);
    }


    @Test
    public void addSnippetsFile() throws IOException {
        String filePath = "./snippets";
        TextFileIndexer textFileIndexer = new TextFileIndexer();
        IndexWriter w = textFileIndexer.InitIndexWriter(filePath);
        textFileIndexer.ReadJsonlToIndexWriter(filePath, w);

        int numDocs = w.getDocStats().numDocs;
        assertTrue(numDocs > 10000);
        // delete the test added document
        textFileIndexer.IndexWriterDeleteAll(w);
        textFileIndexer.CloseIndexWriter(w);
    }

    @Test
    public void searchSnippetsFile() throws IOException {
        String filePath = "./snippets";
        TextFileIndexer textFileIndexer = new TextFileIndexer();
        IndexWriter w = textFileIndexer.InitIndexWriter(filePath);
        textFileIndexer.ReadJsonlToIndexWriter(filePath, w);

        int numDocs = w.getDocStats().numDocs;
        assertTrue(numDocs > 10000);
        textFileIndexer.CloseIndexWriter(w);

        // search document by query
        final var queryStr = "int to string";
        final int maxHits = 10;
        final String field = "docstring";
        IndexSearcher searcher = textFileIndexer.InitIndexReader(filePath);
        Query query = textFileIndexer.InitQuery(field, queryStr);
        TopDocs topDocs = searcher.search(query, maxHits);
        ScoreDoc[] hits = topDocs.scoreDocs;

        for (int i = 0; i < hits.length; i++) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            System.out.println(d.get(field) + " Score :" + hits[i].score);
        }
        System.out.println("Found " + hits.length);
        assertTrue(hits.length >= 10);

        // Delete all generated docs after the test is finished
        IndexWriter wForDeleteAll = textFileIndexer.InitIndexWriter(filePath);
        textFileIndexer.IndexWriterDeleteAll(wForDeleteAll);
        textFileIndexer.CloseIndexWriter(wForDeleteAll);
    }



}


