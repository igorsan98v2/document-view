package com.ygs.docview.dao;

import java.util.List;
import java.util.UUID;

public abstract class Document {
    private String title;
    private String text;

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


}
