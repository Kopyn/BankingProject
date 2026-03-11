package com.kopyn.cqrs.account_service.kafka;

import com.kopyn.cqrs.account_service.api.commands.Command;
import domain.saga_commands.SagaCommand;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    public String bootstrapServers;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic customerChannelTopic() {
        return new NewTopic("customer_channel", 1, (short) 1);
    }

    @Bean
    public NewTopic transactionChannelTopic() {
        return new NewTopic("transaction_channel", 1, (short) 1);
    }

    // CONSUMER
    public Map<String, Object> consumerConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "reactive-consumer-group");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        config.put(JsonDeserializer.TRUSTED_PACKAGES, "com.kopyn.cqrs.*");
        config.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, Command.class);
        return config;
    }

    @Bean
    @Qualifier("customerEventsConsumer")
    public ReactiveKafkaConsumerTemplate<String, String> customersKafkaConsumerTemplate() {
        return new ReactiveKafkaConsumerTemplate<>(customerEventsReceiverOptions());
    }

    private ReceiverOptions<String, String> customerEventsReceiverOptions() {
        Map<String, Object> consumerConfig = consumerConfig();
        ReceiverOptions<String, String> receiverOptions = ReceiverOptions.create(consumerConfig);
        return receiverOptions.subscription(Collections.singletonList("customer_channel"));
    }

    @Bean
    @Qualifier("transactionEventsConsumer")
    public ReactiveKafkaConsumerTemplate<String, SagaCommand> transactionsKafkaConsumerTemplate() {
        return new ReactiveKafkaConsumerTemplate<>(transactionEventsReceiverOptions());
    }

    private ReceiverOptions<String, SagaCommand> transactionEventsReceiverOptions() {
        JsonDeserializer<SagaCommand> deserializer = new JsonDeserializer<>(SagaCommand.class);
        deserializer.addTrustedPackages("com.kopyn.cqrs.*");

        return ReceiverOptions.<String, SagaCommand>create(consumerConfig())
                .withValueDeserializer(deserializer)
                .subscription(Collections.singletonList("transaction_channel"));
    }
}
