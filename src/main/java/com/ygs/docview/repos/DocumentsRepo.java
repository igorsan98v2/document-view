package com.ygs.docview.repos;

import com.ygs.docview.dao.DocumentEntity;
import com.ygs.docview.service.WebDocument;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface DocumentsRepo extends CrudRepository<DocumentEntity,Long> {
   // List<DocumentEntity> findByLastDate(Date date);
   // List<DocumentEntity> findByDatePeriod(Date startDate, Date endDate);
    DocumentEntity findById(long id);

    @Override
    void delete(DocumentEntity documentEntity);

}
