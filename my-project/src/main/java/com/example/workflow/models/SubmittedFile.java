package com.example.workflow.models;


import javax.persistence.*;

@Entity
public class SubmittedFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;

    @Column(nullable = false)
    private  String  name;

    @Column(nullable = false)
    private String procesId;

    @Column(nullable = false)
    private  String writerUsername;

    public SubmittedFile() { }

    public SubmittedFile(String name, String procesId, String writerUsername) {
        this.name = name;
        this.procesId = procesId;
        this.writerUsername = writerUsername;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProcesId() {
        return procesId;
    }

    public void setProcesId(String procesId) {
        this.procesId = procesId;
    }

    public String getWriterUsername() {
        return writerUsername;
    }

    public void setWriterUsername(String writerUsername) {
        this.writerUsername = writerUsername;
    }
}
