package com.ygs.docview;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;



public class Review {

    String nickname;
    String body;
    private List<MultipartFile> images;

    public Review() {
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<MultipartFile> getImages() {
        return images;
    }

    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }
}
