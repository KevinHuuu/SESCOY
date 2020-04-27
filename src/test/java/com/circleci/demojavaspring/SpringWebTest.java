package com.circleci.demojavaspring;

import com.circleci.demojavaspring.model.IndexedDocument;
import com.circleci.demojavaspring.repository.TextFileIndexer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class SpringWebTest {
    @Test
    public void generateSnippetsFile() throws IOException {
        String filePath = "./snippets";
        TextFileIndexer textFileIndexer = new TextFileIndexer();
        IndexWriter w = textFileIndexer.InitIndexWriter(filePath);
        textFileIndexer.ReadJsonlToIndexWriter(filePath, w);

        int numDocs = w.getDocStats().numDocs;
        assertTrue(numDocs > 10000);
        textFileIndexer.CloseIndexWriter(w);
    }

    @Test
    public void IndexDocumentTest() throws InterruptedException, IOException {
        String filePath = "./snippets";
        TextFileIndexer textFileIndexer = new TextFileIndexer();
        IndexWriter w = textFileIndexer.InitIndexWriter(filePath);
        textFileIndexer.ReadJsonlToIndexWriter(filePath, w);

        int numDocs = w.getDocStats().numDocs;
        assertTrue(numDocs > 10000);
        textFileIndexer.CloseIndexWriter(w);

        IndexSearcher searcher;
        final int maxHits = 10;
        TopDocs topDocs;
        final String field = "docstring";
        final var queryStr = "int";
//        String filePath = "./snippets";

//        TextFileIndexer textFileIndexer = new TextFileIndexer();
        searcher = textFileIndexer.InitIndexReader(filePath);
        Query query = textFileIndexer.InitQuery(field, queryStr);
        topDocs = searcher.search(query, maxHits);
        for (int i = 0; i < topDocs.scoreDocs.length; i++) {
            int docId = topDocs.scoreDocs[i].doc;
            Document d = searcher.doc(docId);
            IndexedDocument indexedDocument = new IndexedDocument();
            indexedDocument.setDocstring(d.get("docstring"));
            System.out.println("Original Docstring: " + d.get("docstring"));
            System.out.println("Our Docstring: " + indexedDocument.getDocstring());

        }
        assertTrue(true);
    }

}
