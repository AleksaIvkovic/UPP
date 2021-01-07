package com.example.workflow.models.DBs;


import javax.persistence.*;

@Entity
public class SubmittedFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String processId;

    @Column(nullable = false)
    private String writerUsername;

    public SubmittedFile() { }

    public SubmittedFile(String name, String processId, String writerUsername) {
        this.name = name;
        this.processId = processId;
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

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getWriterUsername() {
        return writerUsername;
    }

    public void setWriterUsername(String writerUsername) {
        this.writerUsername = writerUsername;
    }
}
