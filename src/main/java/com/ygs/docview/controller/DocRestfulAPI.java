package com.ygs.docview.controller;

import com.ygs.docview.dao.DocumentDAO;
import com.ygs.docview.repo.DocumentsRepo;
import com.ygs.docview.service.EditService;
import com.ygs.docview.service.UploadService;
import com.ygs.docview.util.DBContentGenerator;
import com.ygs.docview.util.WebDocument;
import com.ygs.docview.util.converter.DocumentConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static com.ygs.docview.util.converter.DocumentConverter.getDocEntityFromWeb;
import static com.ygs.docview.util.converter.DocumentConverter.getWebFromDAO;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class DocRestfulAPI {
    Logger logger = LoggerFactory.getLogger(DocRestfulAPI.class);

    @Autowired
    private DocumentsRepo documentsRepo;

    @Autowired
    private EditService editService;

    @Autowired
    private UploadService uploadService;

    @DeleteMapping("api/documents/{uuid}/delete")
    Object deleteByUUID(@PathVariable String uuid){
       documentsRepo.deleteByUuid(UUID.fromString(uuid));
       return "ok";
    }


   // @GetMapping("api/documents/page={page_num}&page_size={page_size}")
   // @RequestMapping(method=RequestMethod.GET, value={"page","page_size"})


    @RequestMapping(
            value = "/api/documents/generator/",
            params = { "page", "page_size" },
            method = GET)
    @ResponseBody
    void generateContent(@RequestParam(name="page") int pageNum, @RequestParam("page_size") int pageSize){

        DBContentGenerator dbContentGenerator = new DBContentGenerator(pageNum,pageSize,documentsRepo);
        dbContentGenerator.generate(5);
    }

   @RequestMapping(
           value = "/api/documents/view/",
           params = { "page", "page_size" },
           method = GET)
   @ResponseBody
    Collection<WebDocument> getDocumentByPage(@RequestParam(name="page") int pageNum, @RequestParam("page_size") int pageSize){
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        Collection<WebDocument> webDocuments = uploadService.getDocumentsByPage(pageable);
        return webDocuments;
    }
    @GetMapping("/api/documents/{uuid}")
    Object getDocument(@PathVariable String uuid){
        DocumentDAO documentDAO = documentsRepo.findByUuid(UUID.fromString(uuid));
        logger.info("images in dao after call from another place"+documentDAO.getImages().size());
        documentDAO.getImages().stream().forEach(image->logger.info("image path on dao from jpa by restfulAPI "+image.toString()));
        WebDocument webDocument = null;
        try {
            webDocument = DocumentConverter.getWebFromDAO(documentDAO);
        }
        catch (IOException e){
            e.printStackTrace();
            return "{'message':'bad doc'}";
        }
        return webDocument;
    }
    @PutMapping("/api/documents/{uuid}/edit")
    DocumentDAO edit(@RequestBody WebDocument webDocument, @PathVariable UUID uuid){
        editService.edit(webDocument);
        return new DocumentDAO();//@FixMe
    }


}
