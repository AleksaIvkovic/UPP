package com.example.workflow.models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublishedBookDTO {
    private String title;
    private String writer;
    private String genre;
    private String synopsis;
    private int numberOfPages;
    private String placeOfPublication;
    private int yearOfPublication;
    private String publisher;
    private boolean isPlagiarism;
    private String fileName;
}
