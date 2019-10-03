package com.ygs.docview.dao;

import java.util.UUID;

public abstract class Document {
    private String title;
    private String text;
    private String author;

    public Document() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    public abstract UUID getUUID();
    public abstract void setUUID(UUID uuid);

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
