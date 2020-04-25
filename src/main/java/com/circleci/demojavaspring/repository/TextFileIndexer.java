package com.circleci.demojavaspring.repository;

import com.circleci.demojavaspring.model.Snippet;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.MMapDirectory;
import org.apache.lucene.store.RAMDirectory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TextFileIndexer {
    public static void addExampleDoc(IndexWriter w, String title, String isbn) throws IOException {
        Document doc = new Document();
        doc.add(new TextField("title", title, Field.Store.YES));
        doc.add(new StringField("isbn", isbn, Field.Store.YES));
        w.addDocument(doc);
    }

    public static void addSnippetDoc(IndexWriter w, Snippet snp) throws IOException {
        Document doc = new Document();
        doc.add(new TextField("docstring", snp.getDocstring(), Field.Store.YES));
        doc.add(new TextField("code", snp.getCode(), Field.Store.YES));
        doc.add(new StringField("url", snp.getUrl(), Field.Store.YES));
        doc.add(new StringField("path", snp.getPath(), Field.Store.YES));
        w.addDocument(doc);
    }

    public IndexWriter InitIndexWriter(String stringPath) throws IOException {
        Path path = Paths.get(stringPath);
        StandardAnalyzer analyzer = new StandardAnalyzer();
        Directory index = new MMapDirectory(path);
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter w = new IndexWriter(index, config);
        return w;
    }

    public void CloseIndexWriter(IndexWriter w) throws IOException {
        w.close();
    }

    public void IndexWriterDeleteAll(IndexWriter w) throws IOException {
        w.deleteAll();
    }


//    private void indexExampleTextFile () throws IOException {
//        StandardAnalyzer analyzer = new StandardAnalyzer();
//        Path path = Paths.get("./");
//        Directory index = new MMapDirectory(path);
//
//        IndexWriterConfig config = new IndexWriterConfig(analyzer);
//
//        IndexWriter w = new IndexWriter(index, config);
//        addDoc(w, "Lucene in Action", "193398817");
//        addDoc(w, "Lucene for Dummies", "55320055Z");
//        addDoc(w, "Managing Gigabytes", "55063554A");
//        addDoc(w, "The Art of Computer Science", "9900333X");
//        w.close();
//    }

}
