package com.example.workflow.models.DBs;

import javax.persistence.*;
import java.util.List;

@Entity
public class PublishedBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;

    @Column(nullable = false)
    private  String  title;

    @ManyToOne
    private SysUser writer;

    @ManyToOne
    private Genre genre;

    @Column
    private  String ISBN;

    @Column
    private String  keyWords;

    @Column
    private  String publisher;

    @Column
    private Integer yearOfPublication;

    @Column
    private  String  placeOfPublication;

    @Column
    private  Integer numberOfPages;

    @Column
    private String fileName;

    @Column(nullable = false)
    private String synopsis;

    @Column(nullable = false)
    private boolean published;

    @Column
    private boolean isPlagiarism;

    @ManyToMany
    private List<BookComment> bookComments;

    public PublishedBook() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SysUser getWriter() {
        return writer;
    }

    public void setWriter(SysUser user) {
        this.writer = user;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getYearOfPublication() { return yearOfPublication; }

    public void setYearOfPublication(Integer yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public String getPlaceOfPublication() { return placeOfPublication; }

    public void setPlaceOfPublication(String placeOfPublication) {
        this.placeOfPublication = placeOfPublication;
    }

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public String getFileName() { return fileName; }

    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public boolean isPublished() { return published; }

    public void setPublished(boolean published) { this.published = published; }

    public boolean isPlagiarism() { return isPlagiarism; }

    public void setPlagiarism(boolean plagiarism) { isPlagiarism = plagiarism; }

    public List<BookComment> getBookComments() {
        return bookComments;
    }

    public void setBookComments(List<BookComment> bookComments) {
        this.bookComments = bookComments;
    }
}
