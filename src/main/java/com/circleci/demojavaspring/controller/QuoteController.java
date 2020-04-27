package com.circleci.demojavaspring.controller;

import java.io.IOException;
import java.util.List;

import com.circleci.demojavaspring.model.IndexedDocument;
import com.circleci.demojavaspring.model.Snippet;
import com.circleci.demojavaspring.repository.TextFileIndexer;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.circleci.demojavaspring.model.Quote;
import com.circleci.demojavaspring.repository.QuoteRepository;

@Controller
@RequestMapping
public class QuoteController {

    @Autowired
    private QuoteRepository quoteRepository;
//    @Autowired
//    private IndexSearcher searcher;
//    @Autowired
//    private final int maxHits = 10;
//    @Autowired
//    private TopDocs topDocs;
//    @Autowired
//    final String field = "docstring";


    @PostMapping("/quote/add")
    public ModelAndView addNewQuote(@RequestParam String quote) throws IOException {
        // delete the previous search result
        quoteRepository.deleteAll();
        IndexSearcher searcher;
        final int maxHits = 10;
        TopDocs topDocs;
        final String field = "docstring";
        final var queryStr = quote;
        String filePath = "./snippets";

//        IndexedDocument indexedDocument = new IndexedDocument();
//        indexedDocument.setDocstring("Hello World");
//        quoteRepository.save(indexedDocument);

        TextFileIndexer textFileIndexer = new TextFileIndexer();
        searcher = textFileIndexer.InitIndexReader(filePath);
        Query query = textFileIndexer.InitQuery(field, queryStr);
        topDocs = searcher.search(query, maxHits);

        for (int i = 0; i < topDocs.scoreDocs.length; i++) {
            int docId = topDocs.scoreDocs[i].doc;
            Document d = searcher.doc(docId);
            IndexedDocument indexedDocument = new IndexedDocument();
            indexedDocument.setDocstring(d.get("docstring"));
            quoteRepository.save(indexedDocument);
        }
        return new ModelAndView("redirect:/");
    }


    @GetMapping("/")
    public ModelAndView list() {
        List<IndexedDocument> indexedDocuments = quoteRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("quotes", indexedDocuments);
        return modelAndView;
    }
}

