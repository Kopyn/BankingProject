package com.kopyn.cqrs.customer_service.query.repository;

import com.kopyn.cqrs.customer_service.query.domain.CustomerView;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Component
public class CustomerQueryRepository {

    private final CustomerViewRepository customerViewRepository;

    public CustomerQueryRepository(CustomerViewRepository customerViewRepository) {
        this.customerViewRepository = customerViewRepository;
    }

    public Mono<CustomerView> getCustomerById(UUID uuid) {
        return customerViewRepository.findById(uuid.toString());
    }

    public Flux<CustomerView> getAllCustomers() {
        return customerViewRepository.findAll();
    }
}
