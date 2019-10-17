package com.ygs.docview.controller;

import com.ygs.docview.dao.DocumentDAO;
import com.ygs.docview.repo.DocumentsRepo;
import com.ygs.docview.service.DownloadService;
import com.ygs.docview.service.UploadService;
import com.ygs.docview.util.AttachedIMG;
import com.ygs.docview.util.WebDocument;
import com.ygs.docview.util.converter.DocumentConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.*;
import java.util.ArrayList;
import java.util.UUID;


@Controller
public class DocumentController {
    @Autowired
    private DocumentsRepo documentsRepo;
    @Autowired
    private UploadService uploadService;


    @GetMapping("/document")
    public String documentEdit(Model model) {
        model.addAttribute("document", new WebDocument());
        model.addAttribute("attached",new AttachedIMG());
        model.addAttribute("documents",new ArrayList<WebDocument>(64));
        return "document";
    }

    @GetMapping("/document/{uuid}")
    public  String showDocByUUID(@PathVariable(name="uuid") UUID uuid,Model model){
        WebDocument webDocument = uploadService.getWebDocumentByUUID(uuid);
        model.addAttribute("document",webDocument);
        return "view";
    }




    @PostMapping("/document")
    public String documentSave(@ModelAttribute("attached") AttachedIMG attachedIMG,@ModelAttribute("document") WebDocument document){
        final Logger logger = LoggerFactory.getLogger(DocumentController.class);
       /* document.setAuthor("Igor Yutsyk");
        List <String> images_paths= new ArrayList<>(5);
        if(fileList!=null){
            try{
                String path = new  ClassPathResource("static/images").getFile().getAbsolutePath();
                logger.info("filelist not  null "+fileList.size());
                document.setUUID(UUID.randomUUID());//TODO wrong place
                logger.info(document.toString());
                fileList.stream().forEach(image->{

                    File file =new File(path+ File.separatorChar + image.getOriginalFilename()+"-"+ document.getUUID());
                    logger.info("file created by path"+file.getAbsolutePath());
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
                logger.info(document.toString());
                documentsRepo.save(DocumentConverter.getDocEntityFromWeb(document));
                logger.info("doc saved");

            }catch (IOException e){
                e.printStackTrace();
            }
            finally {
                document.setImgList(images_paths);
                return "view";
            }



        }
        else logger.info("file list is empty");
        */
        try {
            logger.info("attached file list"+attachedIMG.getImgList().size());
            DocumentDAO documentDAO = uploadService.upload(attachedIMG.getImgList(),document);
            document.getImages().stream().forEach(image->logger.info("image path on web "+image.toString()));
            documentDAO.getImages().stream().forEach(image->logger.info("image path on dao "+image.toString()));
            DocumentDAO fromJPA = documentsRepo.findByUuid(documentDAO.getUUID());
            fromJPA.getImages().stream().forEach(image->logger.info("image path on dao from jpa  "+image.toString()));
        }
        catch (IOException e){
            e.printStackTrace();
            return "error";
        }
        return "view";
    }

    @GetMapping("/documents")
    public String getAllDocuments(@ModelAttribute("documents") ArrayList<WebDocument> webDocuments){
        Iterable<DocumentDAO> documents = documentsRepo.findAll();

            documents.forEach(entity ->   {try {
                webDocuments.add(DocumentConverter.getWebFromDAO(entity));
            }catch (IOException e){
            e.printStackTrace(); }
            });


        return "show_all_test";
    }

}
