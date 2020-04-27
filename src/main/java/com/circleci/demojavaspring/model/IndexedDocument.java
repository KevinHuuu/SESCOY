package com.circleci.demojavaspring.model;

import org.apache.lucene.document.Document;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import static java.lang.Integer.min;

@Entity
public class IndexedDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private String docstring;

    public String getDocstring() {
        return docstring;
    }

    //debugging with string too long data exception
    public void setDocstring(String docstring) {
        this.docstring = docstring.substring(0, min(100, docstring.length() - 1));
    }


    public Integer getId() {
        return Id;
    }
}
