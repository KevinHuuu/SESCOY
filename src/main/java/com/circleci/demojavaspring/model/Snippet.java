package com.circleci.demojavaspring.model;


import java.util.ArrayList;

public class Snippet {
    private String path;
    private String original_string;
    private String code;
    private String partition;
    private String docstring;
    private ArrayList docstring_tokens;
    private String repo;
    private ArrayList code_tokens;
    private String language;
    private String sha;
    private String url;
    private String func_name;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getOriginal_string() {
        return original_string;
    }

    public void setOriginal_string(String original_string) {
        this.original_string = original_string;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPartition() {
        return partition;
    }

    public void setPartition(String partition) {
        this.partition = partition;
    }

    public String getDocstring() {
        return docstring;
    }

    public void setDocstring(String docstring) {
        this.docstring = docstring;
    }

    public ArrayList getDocstring_tokens() {
        return docstring_tokens;
    }

    public void setDocstring_tokens(ArrayList docstring_tokens) {
        this.docstring_tokens = docstring_tokens;
    }

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

    public ArrayList getCode_tokens() {
        return code_tokens;
    }

    public void setCode_tokens(ArrayList code_tokens) {
        this.code_tokens = code_tokens;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFunc_name() {
        return func_name;
    }

    public void setFunc_name(String func_name) {
        this.func_name = func_name;
    }
}
