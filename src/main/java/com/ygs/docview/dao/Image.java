package com.ygs.docview.dao;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name="images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(max=255)
    String path;

    @ManyToOne
    @JoinColumn(name = "document_id" ,nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    DocumentDAO document;

    public Image() {
    }
    public Image(String path, DocumentDAO document){
        this.path = path;
        this.document = document;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public DocumentDAO getDocument() {
        return document;
    }

    public void setDocument(DocumentDAO document) {
        this.document = document;
    }

    @Override
    public int hashCode() {
        return Objects.hash(path,document);
    }

    @Override
    public boolean equals(Object o) {
        if(  o instanceof Image){
            Image image = (Image)o;
            boolean areDocsSame = image.getDocument().equals(document);
            boolean arePathsSame = image.getPath().equals(path);
            return areDocsSame&&arePathsSame;
        }
        return false;
    }
}
