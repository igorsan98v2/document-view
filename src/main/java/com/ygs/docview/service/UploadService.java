package com.ygs.docview.service;

import com.ygs.docview.dao.Document;
import com.ygs.docview.dao.DocumentDAO;
import com.ygs.docview.repo.DocumentsRepo;
import com.ygs.docview.repo.FilesRepo;
import com.ygs.docview.repo.ImagesRepo;
import com.ygs.docview.util.WebDocument;
import com.ygs.docview.util.converter.DocumentConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.stream.Stream;

enum  IMG_TYPES{JPG,JPEG,BMP,GIF,PNG};


/**
 * @author Ihor Yutsyk
 *
 *this class used in document-upload conrtoller
 * and restful api controller
 *
 *<p><b>this class implements next functions:<b/><p/>
 * <ul>
 *     <li>upload document form to server<li/>
 *     <li>load documents from server by uuid</li>
 *     <li>load documents from server with  pagenation</li>//TODO check grammar
 *
 *
 * </ul>
 *
 * */

@Service
public class UploadService {
    @Autowired
    private DocumentsRepo documentsRepo;
    @Autowired
    private ImagesRepo imagesRepo;
    @Autowired
    private FilesRepo filesRepo;

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
        document.setUUID(UUID.randomUUID());
        logger.info("inside upload func file list size "+fileList.size());
        String path = new ClassPathResource("static/images").getFile().getAbsolutePath()+
                File.separatorChar
                +document.getUUID().getMostSignificantBits();
        DocumentDAO docDAO = null;

        if(document.getImages()==null){
            document.setImages(new ArrayList<String>(5));
        }
        if(makeDir(path,document.getUUID())){
            makeFilesCount(path);

            loadImages(fileList,document,path);
        }

        document.setTimestamp(Timestamp.from(Instant.now()));

        docDAO = documentsRepo.save(DocumentConverter.getDocEntityFromWeb(document));
        docDAO.getImages().stream().forEach(image -> imagesRepo.save(image));
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
        File file = new File(path);
        if(!file.exists()){
            return file.mkdir();
        }
        return true;//if dir was created before it`s still return true
    }

    private void loadImages(List<MultipartFile> fileList,WebDocument document,String path){
        List <String> images_paths= document.getImages();
        logger.info("load images"+fileList.size());
        fileList.stream().forEach(image->{
            String [] imageSplit = image.getOriginalFilename().split("\\.");
            String imageType= imageSplit[imageSplit.length-1];
            StringBuilder imageName=new StringBuilder("");
            logger.info("imageType"+imageType);

            if(isTypeOk(imageType)){
                if(imageSplit.length>1){
                    for(int i=0;i<imageSplit.length-1;i++){
                        imageName.append(imageSplit[i]);
                        logger.info("splitted"+imageSplit[i]);
                    }
                }
                else{
                    imageName=new StringBuilder(imageSplit[0]);
                }
                File file =new File(formImagePath(path,imageName.toString())+"."+imageType);
                logger.info("in upload service file "+ file.getAbsolutePath());
                try {
                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
                    byte[] bytes = image.getBytes();
                    stream.write(bytes);
                    stream.close();
                    images_paths.add("images/"+ file.getAbsolutePath().split("images/",2)[1]);

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

    public Collection<WebDocument> getDocumentsByPage(Pageable pageable) {
        Iterable<DocumentDAO> documentDAOs = documentsRepo.findAll(pageable);
        Set<WebDocument> webDocuments = new HashSet<>(40);
        documentDAOs.forEach(document -> {
            try {
                webDocuments.add(DocumentConverter.getWebFromDAO(document));
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        return webDocuments;
    }

    public WebDocument getWebDocumentByUUID(UUID uuid){
        WebDocument webDocument =  null;
        DocumentDAO documentDAO = documentsRepo.findByUuid(uuid);
        try {
            webDocument =  DocumentConverter.getWebFromDAO(documentDAO);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return webDocument;
    }
}
