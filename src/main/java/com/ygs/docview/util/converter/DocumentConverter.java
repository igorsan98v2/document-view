package com.ygs.docview.util.converter;

import com.ygs.docview.dao.DocumentDAO;
import com.ygs.docview.dao.Image;
import com.ygs.docview.util.WebDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

final public class DocumentConverter {
    private static Logger logger = LoggerFactory.getLogger(DocumentConverter.class);
    private DocumentConverter(){}
    public static WebDocument getWebFromDAO(DocumentDAO documentDAO) throws IOException {
        WebDocument webDocument = new WebDocument();
        webDocument.setTitle(documentDAO.getTitle());
        webDocument.setAuthor(documentDAO.getAuthor());
        webDocument.setUUID(documentDAO.getUUID());
        logger.info(documentDAO.toString());
        String text = convertTextDAOtoWeb(documentDAO);
        webDocument.setText(text);
        webDocument.setImages(convertImagesToWeb(documentDAO));
        return webDocument;
    }
    public static Set<Image> convertImagesToDAO(Collection<String> images_paths, DocumentDAO entity){
        Set<Image> images = new HashSet<>(5);
        images_paths.stream().forEach(image_path->images.add(new Image(image_path,entity)));
        return images;
    }

    public static String convertTextDAOtoWeb(DocumentDAO documentDAO) throws IOException{
        List<String> fileLines = Files.readAllLines(Paths.get(documentDAO.getText()), StandardCharsets.UTF_8);
        StringBuilder text= new StringBuilder();
        for(String textLine:fileLines){
            text.append("\n"+textLine);
        }
        return text.toString();
    }
    /**
     * create file with text from webform
    * @return path to file with text
    * */
    public static String createFileFromText(WebDocument webDocument)throws IOException {
        String path = new ClassPathResource("static/texts").getFile().getAbsolutePath()+
                File.separatorChar+ webDocument.getUUID()+".txt";
        logger.info("text path"+ path);
        File file = new File(path);//create text if it not exist
        List<String> lines = Arrays.asList(webDocument.getText().split("\n"));//get all text from web file
        Files.write(Paths.get(path),lines);//TODO make it in another thread
        return file.getAbsolutePath();
    }
    private static List<String> convertImagesToWeb(DocumentDAO entity){
        List<String> paths = new ArrayList<>(5);
        entity.getImages().stream().forEach(image -> paths.add(image.getPath()));
        return paths;
    }


    public static DocumentDAO getDocEntityFromWeb(WebDocument webDocument) throws IOException{
        DocumentDAO documentDAO = new DocumentDAO();
        documentDAO.setUUID(webDocument.getUUID());
        documentDAO.setTitle(webDocument.getTitle());
        documentDAO.setAuthor(webDocument.getAuthor());
        documentDAO.setText(createFileFromText(webDocument));
        documentDAO.setImages(convertImagesToDAO(webDocument.getImages(), documentDAO));
        logger.info("from web"+ documentDAO);
        return documentDAO;
    }

    public static String convertSaveFromAbsoluteToResource(String savePath){
        String[] splitted = savePath.split("/saved_files",2);
        if(splitted.length>1) {
            String path = "/saved_files/" + splitted[1];
            return path;
        }
        else return null;

    }
}
