package com.ygs.docview.repo;

import com.ygs.docview.dao.DocumentDAO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;


import java.util.List;
import java.util.UUID;

public interface DocumentsRepo extends CrudRepository<DocumentDAO,Long> {
   // List<DocumentDAO> findByLastDate(Date date);
   // List<DocumentDAO> findByDatePeriod(Date startDate, Date endDate);
    DocumentDAO findById(long id);
    DocumentDAO findByUuid(UUID uuid);
    Iterable<DocumentDAO> findAll(Pageable pageable);
    @Override
    void delete(DocumentDAO documentDAO);

    void deleteByUuid(UUID uuid);
}
