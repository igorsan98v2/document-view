package com.ygs.docview.repo;

import com.ygs.docview.dao.SaveFile;
import org.springframework.data.repository.CrudRepository;

public interface FilesRepo extends CrudRepository<SaveFile,Long> {
}
