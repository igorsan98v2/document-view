package com.ygs.docview.service;

import com.ygs.docview.repo.DocumentsRepo;
import com.ygs.docview.repo.ImagesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
public class DeleteService {
    @Autowired
    DocumentsRepo documentsRepo;
    @Autowired
    ImagesRepo imagesRepo;


    public void delete(UUID uuid){
        documentsRepo.deleteByUuid(uuid);
    }

    public void deleteImages(Collection<String> images){
        for(String image_path:images){
            imagesRepo.deleteByPath(image_path);
        }
    }
}
