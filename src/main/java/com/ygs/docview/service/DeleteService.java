package com.ygs.docview.service;

import com.ygs.docview.repo.DocumentsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteService {
    @Autowired
    DocumentsRepo documentsRepo;
    public void delete(UUID uuid){
        documentsRepo.deleteByUuid(uuid);
    }
}
