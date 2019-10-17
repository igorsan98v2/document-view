package com.ygs.docview.dao;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity

@Table(name = "documents")
public class DocumentDAO implements Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private UUID uuid;
    @NotNull
    @Size(max=255)
    private String text;
    @NotNull
    @Size(max=144)
    private String title;
    @Size(max = 144)
    private String author;

    @NotNull
    private Timestamp timestamp;

   @OneToMany(fetch = FetchType.EAGER,mappedBy = "document")
    private Set<Image> images =new HashSet<>(5);

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "document")
    private Set<SaveFile> saveFiles = new HashSet<>(5);

    public DocumentDAO() {

    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public void setAuthor(String author) {
        this.author = author;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("[id:%d uuid:%8s title:%8s text:%8s]",id,uuid.toString(),getTitle(),getText());
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    public Set<SaveFile> getSaveFiles() {
        return saveFiles;
    }

    public void setSaveFiles(Set<SaveFile> saveFiles) {
        this.saveFiles = saveFiles;
    }


    @Override
    public int hashCode() {
        return Objects.hash(uuid,author);
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof DocumentDAO){
            DocumentDAO entity = (DocumentDAO) o;
            return entity.getUUID().equals(uuid);
        }
        return false;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
