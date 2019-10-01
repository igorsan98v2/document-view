package com.ygs.docview.uttils.adapters;

import com.ygs.docview.dao.Document;
import com.ygs.docview.dao.DocumentEntity;
import com.ygs.docview.service.WebDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class DocumentAdapter {
    private static Logger logger = LoggerFactory.getLogger(DocumentAdapter.class);
    private DocumentAdapter(){}
    public static WebDocument getDocFromDaoToWeb(DocumentEntity documentEntity) throws IOException {
        WebDocument webDocument = new WebDocument();
        webDocument.setTitle(documentEntity.getTitle());
        logger.info(documentEntity.toString());
        List<String> fileLines = Files.readAllLines(Paths.get(documentEntity.getText()), StandardCharsets.UTF_8);
        String text="";
        for(String textLine:fileLines){
            text+="\n"+textLine;
        }
        webDocument.setText(text);
        return webDocument;
    }
    public static  DocumentEntity getDocEntityFromWeb(WebDocument webDocument) throws IOException{
        DocumentEntity documentEntity = new DocumentEntity();
        documentEntity.setUUID(webDocument.getUUID());
        documentEntity.setTitle(webDocument.getTitle());

        String path = new ClassPathResource("static/texts").getFile().getAbsolutePath()+
                File.separatorChar+ documentEntity.getUUID()+".txt";
        logger.info("text path"+ path);
        documentEntity.setText(path);//set path to text actually
        File file = new File(path);//create text if it not exist
        List<String> lines = Arrays.asList(webDocument.getText().split("\n"));//get all text from web file
        Files.write(Paths.get(path),lines);//TODO make it in another thread
        documentEntity.setText(file.getAbsolutePath());

        logger.info("from web"+documentEntity);
        return documentEntity;
    }
}
