package org.gluecoders.library.config;

import org.gluecoders.library.models.Book;

import java.util.Collection;

public class DataSet {
    private Collection<Book> books;

    public Collection<Book> getBooks() {
        return this.books;
    }
    public void setBooks(Collection<Book> list) {
        this.books = list;
    }

}