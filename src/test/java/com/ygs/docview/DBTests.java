package com.ygs.docview;

import com.ygs.docview.dao.DocumentEntity;
import com.ygs.docview.repos.DocumentsRepo;
import com.ygs.docview.repos.FilesRepo;
import com.ygs.docview.repos.ImagesRepo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
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
       // documentsRepo.save()
        
    }
}
