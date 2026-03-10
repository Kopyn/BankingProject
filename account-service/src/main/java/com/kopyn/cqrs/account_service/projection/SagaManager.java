package com.kopyn.cqrs.account_service.projection;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.ReceiverRecord;

import static reactor.netty.http.HttpConnectionLiveness.log;

@Component
public class SagaManager {

    private final ReactiveKafkaConsumerTemplate<String, String> customersKafkaConsumerTemplate;
    private final ReactiveKafkaConsumerTemplate<String, String> transactionsKafkaConsumerTemplate;

    public SagaManager(@Qualifier("customerEventsConsumer")
                       ReactiveKafkaConsumerTemplate<String, String> reactiveKafkaConsumerTemplate,
                       @Qualifier("transactionEventsConsumer")
                       ReactiveKafkaConsumerTemplate<String, String> transactionsKafkaConsumerTemplate) {
        this.customersKafkaConsumerTemplate = reactiveKafkaConsumerTemplate;
        this.transactionsKafkaConsumerTemplate = transactionsKafkaConsumerTemplate;

    }

    @PostConstruct
    public void startKafkaConsumer() {
        customerMessagesFlux()
                .doOnError(e -> log.error("Kafka customer_events consumer crashed", e))
                .retry()
                .subscribe();

        transactionMessagesFlux()
                .doOnError(e -> log.error("Kafka transaction_events consumer crashed", e))
                .retry()
                .subscribe();
    }

    public Flux<String> customerMessagesFlux() {
        return customersKafkaConsumerTemplate.receive()
                .map(ReceiverRecord::value)
                .doOnNext(System.out::println)
                .doOnError(error -> log.error("Consumer error: {}", error.getMessage()));
    }

    public Flux<String> transactionMessagesFlux() {
        return transactionsKafkaConsumerTemplate.receive()
                .map(ReceiverRecord::value)
                .doOnNext(System.out::println)
                .doOnError(error -> log.error("Transaction error: {}", error.getMessage()));
    }
}
