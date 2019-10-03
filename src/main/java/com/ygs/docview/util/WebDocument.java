package com.ygs.docview.util;

import com.ygs.docview.dao.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * @author Ihor Yutsyk
 *
 * class WebDocument is using for showing document in web page
 * it is more lite version of {@link com.ygs.docview.dao.DocumentEntity} class
 * @see com.ygs.docview.dao.DocumentEntity
 * */
public class WebDocument extends Document  implements Serializable {

    /** store data that contain list of images path url`s that`s use for showing attached to document image*/
    private List<String> images_paths;
    /**filed for showing doc in separated rout by route http://{service_name}/documents/{uuid}*/
    private UUID uuid;

    public WebDocument() {

    }


    /**
     * @return {@link #images_paths} of images path`s that attached to document
     * */
    public List<String> getImages() {
        return images_paths;
    }
    /**this method attaching to document list of images
     * @param images_paths contain paths to images sources
     * */
    public void setImages(List<String> images_paths) {
        this.images_paths = images_paths;
    }
    /**
     * @param image path to image src that will be added to {@link #images_paths}
     * */
    public void addImagePath(String image){
        if(images_paths==null){
            images_paths = new ArrayList<>(5);
        }
        images_paths.add(image);
    }
    /**@return {@link #uuid} of web-document*/
    public UUID getUUID(){
        return uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return String.format("[id=%8s title=%8s text=%16s]",uuid,getTitle(),getText());
    }


}
