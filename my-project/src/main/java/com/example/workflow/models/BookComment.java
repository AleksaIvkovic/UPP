package com.example.workflow.models;

import javax.persistence.*;

@Entity
public class BookComment {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    String comment;

    @ManyToOne
    PublishedBook publishedBook;

    @ManyToOne
    SysUser user;

    public BookComment(String comment, PublishedBook publishedBook, SysUser user) {
        this.comment = comment;
        this.publishedBook = publishedBook;
        this.user = user;
    }

    public BookComment() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public PublishedBook getPublishedBook() {
        return publishedBook;
    }

    public void setPublishedBook(PublishedBook publishedBook) {
        this.publishedBook = publishedBook;
    }

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }
}
