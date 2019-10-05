package com.ygs.docview.dao;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name="saves")
public class SaveFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(max=255)
    private String path;

    @NotNull
    @Size(max=16)
    private String type;

    @ManyToOne
    @JoinColumn(name = "document_id" ,nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    DocumentDAO document;

    public SaveFile() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public DocumentDAO getDocument() {
        return document;
    }

    public void setDocument(DocumentDAO document) {
        this.document = document;
    }

    @Override
    public int hashCode() {
        return Objects.hash(path,type);
    }
    @Override
    public boolean equals(Object o){
        if(o instanceof SaveFile){
            SaveFile saveFile = (SaveFile) o;
            boolean equalTypes = saveFile.type.equals(type);
            boolean equalPaths = saveFile.path.equals(path);
            return equalTypes&&equalPaths;
        }
        return false;
    }

}
