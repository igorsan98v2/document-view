package com.ygs.docview;

import com.ygs.docview.dao.DocumentEntity;
import com.ygs.docview.repos.DocumentsRepo;
import com.ygs.docview.service.WebDocument;
import com.ygs.docview.uttils.adapters.DocumentAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Controller
public class DocumentController {
    @Autowired
    DocumentsRepo documentsRepo;

    @GetMapping("/document")
    public String documentEdit(Model model) {
        model.addAttribute("document", new WebDocument());
        model.addAttribute("documents",new ArrayList<WebDocument>(64));
        return "document";
    }
    @PostMapping("/document")
    public String documentSave(@RequestParam("images") List<MultipartFile> fileList,@ModelAttribute("document") WebDocument document){
        final Logger logger = LoggerFactory.getLogger(DocumentController.class);

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
                }

            });
                //TODO it`s wrong place for this next lines of code
                logger.info(document.toString());
                documentsRepo.save(DocumentAdapter.getDocEntityFromWeb(document));
                logger.info("doc saved");

            }catch (IOException e){
                e.printStackTrace();
            }
            finally {
                document.setImages(images_paths);
                return "view";
            }



        }
        else logger.info("file list is empty");

        return "view";
    }

    @GetMapping("/documents")
    public String getAllDocuments(@ModelAttribute("documents") ArrayList<WebDocument> webDocuments){
        Iterable<DocumentEntity> documents = documentsRepo.findAll();

            documents.forEach(entity ->   {try {
                webDocuments.add(DocumentAdapter.getDocFromDaoToWeb(entity));
            }catch (IOException e){
            e.printStackTrace(); }
            });


        return "show_all_test";
    }

}
