package com.ygs.docview;

import com.ygs.docview.dao.DocumentEntity;
import com.ygs.docview.dao.Image;
import com.ygs.docview.repo.DocumentsRepo;
import com.ygs.docview.repo.FilesRepo;
import com.ygs.docview.repo.ImagesRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
@RunWith(SpringRunner.class)
@SpringBootTest

public class DBTests {
    @Autowired
    private DocumentsRepo documentsRepo;
    @Autowired
    private FilesRepo filesRepo;
    @Autowired
    private ImagesRepo imagesRepo;
    @Test
    public void insertData(){
        DocumentEntity documentEntity = new DocumentEntity();
        documentEntity.setTitle("Real  title");
        documentEntity.setText("src some");
        documentEntity.setUUID(UUID.randomUUID());
        Set<Image> images = new HashSet<>(12);
        Image img= new Image();
        img.setPath("any path");
        img.setDocument(documentEntity);

        Image img1= new Image();
        img1.setPath("any path 1");
        img1.setDocument(documentEntity);

        Image img2= new Image();
        img.setPath("any path 2");
        img.setDocument(documentEntity);

        images.add(img);
        images.add(img1);
        images.add(img2);

        documentsRepo.save(documentEntity);
        DocumentEntity documentEntity1 = documentsRepo.findByUuid(documentEntity.getUUID());

    }
}
