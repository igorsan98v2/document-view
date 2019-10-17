package com.ygs.docview.service;

import com.ygs.docview.dao.DocumentDAO;
import com.ygs.docview.repo.DocumentsRepo;
import com.ygs.docview.util.WebDocument;
import com.ygs.docview.util.converter.DocumentConverter;
import com.ygs.docview.util.factory.SaveFile;
import com.ygs.docview.util.factory.SaveFileFactory;
import com.ygs.docview.util.factory.SaveType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class DownloadService {
    @Autowired
    DocumentsRepo documentsRepo;


    public String download(UUID uuid,String fileType) throws IOException {
       DocumentDAO document = documentsRepo.findByUuid(uuid);

       com.ygs.docview.dao.SaveFile file = new com.ygs.docview.dao.SaveFile();
        String path= new ClassPathResource("static/saved_files").getFile().getAbsolutePath()
                + File.separatorChar+uuid+fileType;

       file.setPath(path);
       file.setDocument(document);
       file.setType(fileType);
       if(!document.getSaveFiles().contains(file)){
            SaveFile  saveFile = SaveFileFactory.saveFile(document, SaveType.valueOf(fileType));
            document.getSaveFiles().add(file);//TODO FIX IT!!!
        }
       path =  DocumentConverter.convertSaveFromAbsoluteToResource(path);
       return path;
    }

    public Set<WebDocument>getWebDocuments(){
        Set <WebDocument> webDocuments = new HashSet<>(50);
        Iterable <DocumentDAO> documentDAOs = documentsRepo.findAll();
        
        return webDocuments;
    }
}
