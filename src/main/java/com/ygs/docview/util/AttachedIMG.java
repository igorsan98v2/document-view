package com.ygs.docview.util;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class AttachedIMG {
    private List<MultipartFile> imgList;
    public AttachedIMG(){}
    public List<MultipartFile> getImgList() {
        return imgList;
    }

    public void setImgList(List<MultipartFile> imgList) {
        this.imgList = imgList;
    }
}
