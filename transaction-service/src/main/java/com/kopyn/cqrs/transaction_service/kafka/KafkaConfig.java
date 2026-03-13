package com.kopyn.cqrs.transaction_service.kafka;

import domain.saga_commands.SagaCommand;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.SenderOptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    private static final String TRANSACTION_CHANNEL = "transaction_channel";

    @Value("${spring.kafka.bootstrap-servers}")
    public String bootstrapServers;

    public Map<String, Object> consumerConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "transaction-service");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        config.put(JsonDeserializer.TRUSTED_PACKAGES, "com.kopyn.cqrs.*");
        config.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, SagaCommand.class);
        return config;
    }

    @Bean
    @Qualifier("transactionCommandConsumer")
    public ReactiveKafkaConsumerTemplate<String, SagaCommand> transactionsKafkaConsumerTemplate() {
        return new ReactiveKafkaConsumerTemplate<>(transactionEventsReceiverOptions());
    }

    private ReceiverOptions<String, SagaCommand> transactionEventsReceiverOptions() {
        JsonDeserializer<SagaCommand> deserializer = new JsonDeserializer<>(SagaCommand.class);
        deserializer.addTrustedPackages("com.kopyn.cqrs.*");

        return ReceiverOptions.<String, SagaCommand>create(consumerConfig())
                .withValueDeserializer(deserializer)
                .subscription(Collections.singletonList(TRANSACTION_CHANNEL));
    }

    // PRODUCER
    public Map<String, Object> producerConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        // Optional but recommended for polymorphic Command hierarchy
        config.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);

        return config;
    }

    @Bean
    public ReactiveKafkaProducerTemplate<String, SagaCommand> reactiveKafkaProducerTemplate() {
        SenderOptions<String, SagaCommand> senderOptions =
                SenderOptions.create(producerConfig());

        return new ReactiveKafkaProducerTemplate<>(senderOptions);
    }

}
