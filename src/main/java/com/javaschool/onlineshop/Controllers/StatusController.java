package com.javaschool.onlineshop.Controllers;

import com.javaschool.onlineshop.DTO.StatusRequestDTO;
import com.javaschool.onlineshop.Models.Status;
import com.javaschool.onlineshop.Services.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status")
public class StatusController {
    private final StatusService statusService;

    @Autowired
    public StatusController(StatusService statusService){
        this.statusService = statusService;
    }

    @PostMapping
    public ResponseEntity<String> createStatus(@RequestBody StatusRequestDTO statusDTO) {
        Status status = new Status();
        status.setType(statusDTO.getType());
        status.setIsDeleted(false);

        statusService.saveStatus(status);

        return ResponseEntity.ok("Status created with ID: " + status.getStatus_uuid());
    }
}
