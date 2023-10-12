package com.javaschool.onlineshop.Services;

import com.javaschool.onlineshop.Models.Status;
import com.javaschool.onlineshop.Repositories.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusService {
    private final StatusRepository statusRepository;

    @Autowired
    public StatusService(StatusRepository statusRepository){
        this.statusRepository = statusRepository;
    }

    public void saveStatus(Status status){
        statusRepository.save(status);
    }
}
