package com.kopyn.cqrs.customer_service.query.handler;

import com.kopyn.cqrs.customer_service.query.api.messages.GetAllCustomersQuery;
import com.kopyn.cqrs.customer_service.query.domain.CustomerView;
import com.kopyn.cqrs.customer_service.query.repository.CustomerQueryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@AllArgsConstructor
public class GetAllCustomersHandler implements QueryHandler<GetAllCustomersQuery, CustomerView> {

    private final CustomerQueryRepository customerQueryRepository;

    @Override
    public Class<GetAllCustomersQuery> getQueryType() {
        return GetAllCustomersQuery.class;
    }

    public Flux<CustomerView> handle(GetAllCustomersQuery query) {
        return customerQueryRepository.getAllCustomers();
    }
}
