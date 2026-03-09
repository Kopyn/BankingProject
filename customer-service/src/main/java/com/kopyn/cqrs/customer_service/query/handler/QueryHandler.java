package com.kopyn.cqrs.customer_service.query.handler;

import com.kopyn.cqrs.customer_service.query.api.messages.Query;
import org.reactivestreams.Publisher;

public interface QueryHandler<Q extends Query, R> {

    Class<Q> getQueryType();
    Publisher<R> handle(Q query);
}
