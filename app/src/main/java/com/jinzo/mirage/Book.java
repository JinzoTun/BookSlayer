package com.jinzo.mirage;

// Book.java
public class Book {
    private String title;
    private String author;
    private String coverImageUrl;
    private String pdfUrl;

    // Empty constructor required by Firebase
    public Book() {
    }

    public Book(String title, String author, String coverImageUrl, String pdfUrl) {
        this.title = title;
        this.author = author;
        this.coverImageUrl = coverImageUrl;
        this.pdfUrl = pdfUrl;
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }
}
