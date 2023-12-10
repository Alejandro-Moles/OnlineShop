package com.javaschool.onlineshop.init;

import com.javaschool.onlineshop.models.StatusModel;
import com.javaschool.onlineshop.repositories.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class StatusDataInitializer  implements CommandLineRunner {

    private final StatusRepository statusRepository;

    @Override
    public void run(String... args) throws Exception {
        insertStatuses();
    }

    private void insertStatuses() {
        if (statusRepository.count() == 0) {

            StatusModel status1 = new StatusModel();
            status1.setStatusUuid(UUID.randomUUID());
            status1.setType("DELIVERED");
            status1.setIsDeleted(false);
            statusRepository.save(status1);

            StatusModel status2 = new StatusModel();
            status2.setStatusUuid(UUID.randomUUID());
            status2.setType("PENDING_PAYMENT");
            status2.setIsDeleted(false);
            statusRepository.save(status2);

            StatusModel status3 = new StatusModel();
            status3.setStatusUuid(UUID.randomUUID());
            status3.setType("PENDING_SHIPMENT");
            status3.setIsDeleted(false);
            statusRepository.save(status3);

            StatusModel status4 = new StatusModel();
            status4.setStatusUuid(UUID.randomUUID());
            status4.setType("SHIPPED");
            status4.setIsDeleted(false);
            statusRepository.save(status4);
        }
    }
}