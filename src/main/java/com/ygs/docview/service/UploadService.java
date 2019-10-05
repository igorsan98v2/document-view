package com.ygs.docview.service;

import com.ygs.docview.dao.DocumentDAO;
import com.ygs.docview.repo.DocumentsRepo;
import com.ygs.docview.util.WebDocument;
import com.ygs.docview.util.converter.DocumentConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

enum  IMG_TYPES{JPG,JPEG,BMP,GIF,PNG};


/**
 * @author Ihor Yutsyk
 *
 *this class used in document-upload conrtoller
 *
 * */

@Service
public class UploadService {
    @Autowired
    private DocumentsRepo documentsRepo;
    private Long imgCount=0l;
    private Logger logger = LoggerFactory.getLogger(UploadService.class);

    public UploadService(){}
    /*
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
        document.setImgList(images_paths);
        //documentsRepo.save(DocumentConverter.getDocEntityFromWeb(document));
    }
    */
    public DocumentDAO upload(List<MultipartFile> fileList, WebDocument document) throws IOException{

        String path = new ClassPathResource("static/images").getFile().getAbsolutePath()+
                File.separatorChar
                +document.getUUID().getMostSignificantBits();
        DocumentDAO docDAO = null;
        document.setUUID(UUID.randomUUID());//TODO wrong place
        if(document.getImages()==null){
            document.setImages(new ArrayList<>(5));
        }
        if(makeDir(path,document.getUUID())){
            makeFilesCount(path);
            loadImages(fileList,document,path);
        }


        docDAO = documentsRepo.save(DocumentConverter.getDocEntityFromWeb(document));
        return  docDAO;
    }
    public boolean isTypeOk(String imgType){
       try {
           IMG_TYPES.valueOf(imgType.toUpperCase());
       }
       catch (IllegalArgumentException e){
           e.printStackTrace();
           return false;
       }

       return true;
    }
    private boolean makeDir(String path,UUID uuid){
        File file = new File(path+File.separatorChar+uuid.getMostSignificantBits());
        if(!file.exists()){
            return file.mkdir();
        }
        return true;//if dir was created before it`s still return true
    }

    private void loadImages(List<MultipartFile> fileList,WebDocument document,String path){
        List <String> images_paths= document.getImages();
        fileList.stream().forEach(image->{
            String [] imageSplit = image.getName().split(".");
            String imageType= imageSplit[imageSplit.length-1];
            String imageName="";
            if(isTypeOk(imageType)){
                if(imageSplit.length>1){
                    for(int i=0;i<imageSplit.length-1;i++){
                        imageName += imageSplit[i];
                    }
                }
                else{
                    imageName=imageSplit[0];
                }
                File file =new File(formImagePath(path,imageName)+imageType);

                try {
                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
                    byte[] bytes = image.getBytes();
                    stream.write(bytes);
                    stream.close();
                    images_paths.add("images/"+ file.getAbsolutePath().split("images/",2)[0]);

                }
                catch (FileNotFoundException e){
                    e.printStackTrace();
                }
                catch (IOException e){
                    e.printStackTrace();
                } }
            });
    }
    private String formImagePath(String pathToFolder,String imageName){
        return pathToFolder+File.separatorChar+imageName+(++imgCount);
    }
    private void makeFilesCount(String path){
        try (Stream<Path> files = Files.list(Paths.get(path))) {
            imgCount = files.count();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
