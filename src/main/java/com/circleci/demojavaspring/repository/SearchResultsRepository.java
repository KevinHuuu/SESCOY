package com.circleci.demojavaspring.repository;

import java.util.List;

import com.circleci.demojavaspring.model.IndexedDocument;
import org.springframework.data.repository.CrudRepository;

import com.circleci.demojavaspring.model.Quote;


public interface SearchResultsRepository extends CrudRepository<IndexedDocument, Long> {

    @Override
    List<IndexedDocument> findAll();

}