package com.ygs.docview.repos;


import com.ygs.docview.dao.Image;
import org.springframework.data.repository.CrudRepository;

public interface ImagesRepo extends CrudRepository<Image,Long> {
}
