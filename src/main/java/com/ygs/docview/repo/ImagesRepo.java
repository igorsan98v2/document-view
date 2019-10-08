package com.ygs.docview.repo;


import com.ygs.docview.dao.Image;
import org.springframework.data.repository.CrudRepository;

public interface ImagesRepo extends CrudRepository<Image,Long> {
    void deleteByPath(String path);
}
