package com.circleci.demojavaspring.controller;

import java.io.IOException;
import java.util.List;

import com.circleci.demojavaspring.model.IndexedDocument;
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
import org.springframework.web.servlet.ModelAndView;

import com.circleci.demojavaspring.repository.SearchResultsRepository;

@Controller
@RequestMapping
public class QuerySearchController {

    @Autowired
    private SearchResultsRepository searchResultsRepository;
//    @Autowired
//    private IndexSearcher searcher;
//    @Autowired
//    private final int maxHits = 10;
//    @Autowired
//    private TopDocs topDocs;
//    @Autowired
//    final String field = "docstring";


    @PostMapping("/query/add")
    public ModelAndView search(@RequestParam String query) throws IOException {
        // delete the previous search result
        searchResultsRepository.deleteAll();
        IndexSearcher searcher;
        final int maxHits = 10;
        TopDocs topDocs;
        final String field = "docstring";
        final var queryStr = query;

        String filePath = "./snippets";

        TextFileIndexer textFileIndexer = new TextFileIndexer();
        searcher = textFileIndexer.InitIndexReader(filePath);
        Query newQuery;
        newQuery = textFileIndexer.InitQuery(field, queryStr);
        topDocs = searcher.search(newQuery, maxHits);

        for (int i = 0; i < topDocs.scoreDocs.length; i++) {
            int docId = topDocs.scoreDocs[i].doc;
            Document d = searcher.doc(docId);
            IndexedDocument indexedDocument = new IndexedDocument();
            indexedDocument.setDocstring(d.get("docstring"));
            indexedDocument.setCode(d.get("code"));
            indexedDocument.setUrl(d.get("url"));
            indexedDocument.setPath(d.get("path"));

            searchResultsRepository.save(indexedDocument);
            System.out.println("Our Docstring: " + indexedDocument.getDocstring());
        }
        return new ModelAndView("redirect:/");
    }


    @GetMapping("/")
    public ModelAndView list() {
        List<IndexedDocument> indexedDocuments = searchResultsRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("searchResults", indexedDocuments);
        return modelAndView;
    }
}

