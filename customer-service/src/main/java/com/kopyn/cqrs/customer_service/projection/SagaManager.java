package com.kopyn.cqrs.customer_service.projection;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.ReceiverRecord;

import static reactor.netty.http.HttpConnectionLiveness.log;

@Component
@RequiredArgsConstructor
public class SagaManager {

    private final ReactiveKafkaConsumerTemplate<String, String> reactiveKafkaConsumerTemplate;

    public Flux<String> consumeRecord() {
        return reactiveKafkaConsumerTemplate.receive()
                .map(ReceiverRecord::value)
                .doOnNext(msg -> log.info("Received: {}", msg))
                .doOnError(error -> log.error("Consumer error: {}", error.getMessage()));
    }
}
