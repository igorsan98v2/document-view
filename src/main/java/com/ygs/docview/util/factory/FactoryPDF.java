package com.ygs.docview.util.factory;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.ygs.docview.dao.DocumentEntity;
import com.ygs.docview.util.WebDocument;
import com.ygs.docview.util.adapter.DocumentAdapter;
import org.springframework.core.io.ClassPathResource;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

final class FactoryPDF implements SaveFile{
    public String path=null;
    public FactoryPDF(DocumentEntity document) throws FileNotFoundException,DocumentException,IOException{
        WebDocument webDocument = DocumentAdapter.getDocFromDaoToWeb(document);
        init(webDocument);
    }
    public FactoryPDF(WebDocument webDocument) throws FileNotFoundException,DocumentException,IOException{
      init(webDocument);
    }
    private void init(WebDocument webDocument)throws FileNotFoundException,DocumentException,IOException{
        path= new ClassPathResource("static/saved_files").getFile().getAbsolutePath()
                + File.separatorChar+webDocument.getUUID()+".pdf";
        Document pdf = new Document();
        PdfWriter pdfWriter = PdfWriter.getInstance(pdf,new FileOutputStream(path));
        pdf.open();
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, BaseColor.BLACK);
        pdf.addTitle(webDocument.getTitle());
        pdf.addAuthor(webDocument.getAuthor());
        Chunk chunk = new Chunk(webDocument.getText(), font);
        pdf.add(chunk);
        Set<Image> images = new HashSet<>(5);
        List<String> imagesPaths = webDocument.getImages();
        for(String imgPath:imagesPaths){
            images.add(Image.getInstance(imgPath));
        }
        for(Image image:images){
            pdf.add(image);
        }
        pdf.close();
    }
    @Override
    public String getAbsolutePath() {
        return path;
    }
}
