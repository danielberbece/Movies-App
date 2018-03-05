package com.projects.daniel.moviesapp.models;


import java.io.Serializable;

public class Review  implements Serializable{
    private String id;
    private String author;
    private String content;
    private String url;

    public Review(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public Review(String id, String author, String content, String url) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}
