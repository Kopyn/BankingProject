package com.kopyn.cqrs.customer_service.query.handler;

import com.kopyn.cqrs.customer_service.query.api.messages.GetCustomerByUUIDQuery;
import com.kopyn.cqrs.customer_service.query.domain.CustomerView;
import com.kopyn.cqrs.customer_service.query.repository.CustomerQueryRepository;
import lombok.AllArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GetCustomerByUUIDHandler implements QueryHandler<GetCustomerByUUIDQuery, CustomerView> {

    private final CustomerQueryRepository customerQueryRepository;

    @Override
    public Class<GetCustomerByUUIDQuery> getQueryType() {
        return GetCustomerByUUIDQuery.class;
    }

    @Override
    public Publisher<CustomerView> handle(GetCustomerByUUIDQuery query) {
        return customerQueryRepository.getCustomerById(query.uuid());
    }
}
