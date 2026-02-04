package com.kopyn.cqrs.customer_service.query.service;

import com.kopyn.cqrs.customer_service.query.api.messages.Query;
import com.kopyn.cqrs.customer_service.query.domain.CustomerView;
import com.kopyn.cqrs.customer_service.query.handler.QueryHandler;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class CustomerQueryBus {

    private final Map<Class<? extends Query>, QueryHandler<? extends Query, ?>> handlers = new HashMap<>();

    public CustomerQueryBus(List<QueryHandler<?, ?>> handlers) {
        handlers
                .forEach(handler -> {
                    log.info("Registering query handler: " + handler.getQueryType());
                    registerHandler(handler.getQueryType(), handler);
                });
    }

    public <Q extends Query, R> void registerHandler(Class<Q> queryType, QueryHandler<?, R> handler) {
        handlers.put(queryType, handler);
    }

    @SuppressWarnings("unchecked")
    public Publisher<CustomerView> handleQuery(Query query) {
        QueryHandler<Query, CustomerView> handler = (QueryHandler<Query, CustomerView>) handlers.get(query.getClass());

        if (handler == null) {
            return Mono.error(new IllegalStateException("No handler for query: " + query.getClass()));
        }

        return handler.handle(query);
    }

}
