package com.ygs.docview.dao;

import java.util.UUID;

public interface Document {


    String getTitle();

    void setTitle(String title);

     String getText();

     void setText(String text) ;
     UUID getUUID();
     void setUUID(UUID uuid);

     String getAuthor();

     void setAuthor(String author);


}
