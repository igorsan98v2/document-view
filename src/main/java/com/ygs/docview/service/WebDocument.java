package com.ygs.docview.service;

import com.ygs.docview.dao.Document;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;



public class WebDocument extends Document  implements Serializable {

    private List<String > images_paths;

    private UUID uuid;
    public WebDocument() {

    }



    public List<String> getImages() {
        return images_paths;
    }

    public void setImages(List<String> images_paths) {
        this.images_paths = images_paths;
    }

    public UUID getUUID(){
        return uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return String.format("[id=%24s\ttitle=%32s\ntext=%64s]",uuid,getTitle(),getText());
    }


}
