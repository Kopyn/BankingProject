package com.kopyn.cqrs.account_service.projection;

import jakarta.annotation.PostConstruct;
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

    @PostConstruct
    public void startKafkaConsumer() {
        consumeRecord()
                .doOnError(e -> log.error("Kafka consumer crashed", e))
                .retry()
                .subscribe();
    }


    public Flux<String> consumeRecord() {
        return reactiveKafkaConsumerTemplate.receive()
                .map(ReceiverRecord::value)
                .doOnNext(System.out::println)
                .doOnError(error -> log.error("Consumer error: {}", error.getMessage()));
    }
}
