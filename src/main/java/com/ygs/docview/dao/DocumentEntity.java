package com.ygs.docview.dao;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "DOCUMENTS")
public class DocumentEntity extends Document {
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

    @OneToMany(mappedBy = "image")
    private Set<Image> images;

    @OneToMany(mappedBy = "save_file")
    private Set<SaveFile> saveFiles;

    public DocumentEntity() {

    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("[id:%d uuid:%s title:%s text:%s]",id,uuid.toString(),getTitle(),getText());
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
}
