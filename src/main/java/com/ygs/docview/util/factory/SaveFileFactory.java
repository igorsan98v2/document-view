package com.ygs.docview.util.factory;


import com.itextpdf.text.DocumentException;
import com.ygs.docview.dao.DocumentEntity;
import com.ygs.docview.util.WebDocument;

import java.io.FileNotFoundException;
import java.io.IOException;


final public class SaveFileFactory {
    private SaveFileFactory(){ };
    public static SaveFile saveFile(DocumentEntity document, SaveType type){
        try {
            switch (type){
                case DOC:
                    break;
                case DOCX:
                    break;
                case ODT:
                    break;
                case PDF:
                    return new FactoryPDF(document);
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (DocumentException e){
            e.printStackTrace();
        }
        catch (IOException e){e.printStackTrace();}
        return null;
    }
    public static SaveFile saveFile(WebDocument document, SaveType type){
        try {
            switch (type){
                case DOC:
                    break;
                case DOCX:
                    break;
                case ODT:
                    break;
                case PDF:
                    return new FactoryPDF(document);
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (DocumentException e){
            e.printStackTrace();
        }
        catch (IOException e){e.printStackTrace();}
        return null;
    }

}
