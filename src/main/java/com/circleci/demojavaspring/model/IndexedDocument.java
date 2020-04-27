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
    private String code;
    private String path;
    private String url;


    //debugging with string too long data exception
    public void setDocstring(String docstring) {
        this.docstring = docstring.substring(0, min(100, docstring.length() - 1));
    }


    public void setCode(String code) {
        this.code = code.substring(0, min(254, code.length() - 1));
    }
    public void setPath(String path) {
        this.path = path.substring(0, min(254, path.length() - 1));
    }
    public void setUrl(String url) {
        this.url = url.substring(0, min(254, url.length() - 1));
    }

    public Integer getId() {
        return Id;
    }

    public String getDocstring() {
        return docstring;
    }

    public String getCode() {
        return code;
    }

    public String getPath() {
        return path;
    }

    public String getUrl() {
        return url;
    }
}
