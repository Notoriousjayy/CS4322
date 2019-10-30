package com.example.cs4322.Search;

public class BookItem {

    private String title;
    private String isbn;
    private String author;

    public BookItem(){}

    public BookItem(String t1, String t2, String t3) {
        title = t1;
        isbn = t2;
        author = t3;
    }

    public String getTitle() {
        return title;
    }

    public String getISBN() {
        return isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setTitle(String t1) {
        title = t1;
    }

    public void setISBN(String t2) {
        isbn = t2;
    }

    public void setAuthor(String t3) {
        author = t3;
    }
}
