package com.ygs.docview.service;

import com.ygs.docview.repo.DocumentsRepo;
import com.ygs.docview.util.WebDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Ihor Yutsyk
 *
 *this class used in document-upload conrtoller
 *
 * */
@Service
public class UploadService {
    @Autowired
    public DocumentsRepo documentsRepo;

    public UploadService(List<MultipartFile> fileList, WebDocument document) throws IOException{
        List <String> images_paths= new ArrayList<>(5);
        String path = new ClassPathResource("static/images").getFile().getAbsolutePath();

        document.setUUID(UUID.randomUUID());//TODO wrong place

        fileList.stream().forEach(image->{

            File file =new File(path+ File.separatorChar + image.getOriginalFilename()+"-"+ document.getUUID());

            try {
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
                byte[] bytes = image.getBytes();
                stream.write(bytes);
                stream.close();
                images_paths.add(file.getAbsolutePath());
            }
            catch (FileNotFoundException e){
                e.printStackTrace();
            }
            catch (IOException e){
                e.printStackTrace();
            } });
        //TODO it`s wrong place for this next lines of code
        document.setImages(images_paths);
        //documentsRepo.save(DocumentAdapter.getDocEntityFromWeb(document));



    }
}
