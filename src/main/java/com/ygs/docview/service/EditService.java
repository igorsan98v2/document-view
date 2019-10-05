package com.ygs.docview.service;

import com.ygs.docview.dao.DocumentDAO;
import com.ygs.docview.dao.Image;
import com.ygs.docview.repo.DocumentsRepo;
import com.ygs.docview.util.WebDocument;
import com.ygs.docview.util.converter.DocumentConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class EditService {

    @Autowired
    private DocumentsRepo documentsRepo;

    public void edit(WebDocument webDocument){
        DocumentDAO entity = documentsRepo.findByUuid(webDocument.getUUID());
        entity.getImages().addAll(updateImgSet(webDocument,entity));

        /* Micro optimization simulation */
        if(isFieldNeedUpdate(entity.getTitle(),webDocument.getTitle())){
            entity.setTitle(webDocument.getTitle());
        }
        if(isFieldNeedUpdate(entity.getAuthor(),webDocument.getAuthor())){
            entity.setAuthor(webDocument.getAuthor());
        }
        try {
            String text = DocumentConverter.convertTextDAOtoWeb(entity);
            if(!text.equals(webDocument.getText())){
                String path = DocumentConverter.createFileFromText(webDocument);
                entity.setText(path);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        //documentsRepo.save(entity);

    }

    private boolean isFieldNeedUpdate(String original,String webInfo){
        if(original.equals(webInfo)){
            return true;
        }
        return false;
    }



    private Set<Image> updateImgSet(WebDocument webDocument, DocumentDAO entity){
        Set<Image> images = new HashSet<>(5);
        Set<Image> oldImages = DocumentConverter.convertImagesToDAO(webDocument.getImages(),entity);
        webDocument.getImages().stream().forEach(image_path->{
            Image candidate = new Image(image_path,entity);
            if(!oldImages.contains(candidate)){
                images.add(candidate);
            }
        });
        return images;
    }
}
