package com.kopyn.cqrs.customer_service.query.repository;

import com.kopyn.cqrs.customer_service.query.domain.CustomerView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CustomerQueryRepository {

    private final CustomerViewRepository customerViewRepository;

    public Mono<CustomerView> getCustomerById(UUID uuid) {
        return customerViewRepository.findById(uuid.toString());
    }

    public Flux<CustomerView> getAllCustomers() {
        return customerViewRepository.findAll();
    }
}
