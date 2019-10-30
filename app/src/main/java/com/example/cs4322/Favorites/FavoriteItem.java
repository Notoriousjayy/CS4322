package com.example.cs4322.Favorites;

public class FavoriteItem {
    private String title;
    private String author;
    private String isbn;

    public FavoriteItem(String t1, String t2, String t3) {
        title = t1;
        author = t2;
        isbn = t3;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getISBN() {
        return isbn;
    }
}
