package com.ygs.docview.repo;

import com.ygs.docview.dao.DocumentEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface DocumentsRepo extends CrudRepository<DocumentEntity,Long> {
   // List<DocumentEntity> findByLastDate(Date date);
   // List<DocumentEntity> findByDatePeriod(Date startDate, Date endDate);
    DocumentEntity findById(long id);
    DocumentEntity findByUuid(UUID uuid);
    @Override
    void delete(DocumentEntity documentEntity);

}
