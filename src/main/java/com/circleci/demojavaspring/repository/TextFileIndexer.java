package com.circleci.demojavaspring.repository;

import com.circleci.demojavaspring.model.Snippet;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.MMapDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.QueryBuilder;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

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

    public IndexSearcher InitIndexReader(String stringPath) throws IOException {
        Path path = Paths.get(stringPath);
        Directory index = new MMapDirectory(path);
        IndexReader indexReader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(indexReader);
        return searcher;
    }
    public Query InitQuery (String field, String queryStr) {
        Analyzer analyzer = new StandardAnalyzer();
        QueryBuilder builder = new QueryBuilder(analyzer);
        Query query = builder.createBooleanQuery("title", queryStr);
        return query;
    }

    public void ReadJsonlToIndexWriter (String stringPath, IndexWriter w) {
        try (
                FileReader fileReader = new FileReader(("./snippetsJson/python/final/jsonl/train/python_train_0.jsonl"));
                BufferedReader bufferedReader = new BufferedReader(fileReader);
        ) {
            String currentLine;
            int i = 0;
            while ((currentLine = bufferedReader.readLine()) != null) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                Snippet snippet = mapper.readValue(currentLine, Snippet.class);
                this.addSnippetDoc(w, snippet);
                i = i + 1;
            }
            System.out.println('i' + i);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
