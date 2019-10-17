package com.ygs.docview.util;

import com.ygs.docview.controller.DocumentController;
import com.ygs.docview.dao.Document;
import com.ygs.docview.dao.DocumentDAO;
import com.ygs.docview.repo.DocumentsRepo;
import com.ygs.docview.util.converter.DocumentConverter;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;

public class DBContentGenerator {

    DocumentsRepo documentsRepo;
    int pages;
    int pageSize;
    public DBContentGenerator(int pages,int pageSize,DocumentsRepo documentsRepo){
        this.pageSize=pageSize;
        this.pages = pages;
        this.documentsRepo =documentsRepo;
    }
    public void generate(int endDocCond){
        for(int i=0;i<pages;i++){
            for(int j=0;j<pageSize;j++){
                if(pages==i+1&&j==endDocCond)return;
                WebDocument webDocument = new WebDocument();
                webDocument.setText( String.format("this is usual text for %d-%d",i+1,j));
                webDocument.setTitle(String.format("%d-%d",i+1,j));
                webDocument.setUUID(UUID.randomUUID());
                webDocument.setAuthor("admin");
                webDocument.setTimestamp(Timestamp.from(Instant.now()));
                webDocument.setImages(new ArrayList<>(0));
                try {
                   documentsRepo.save(  DocumentConverter.getDocEntityFromWeb(webDocument));
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

}
