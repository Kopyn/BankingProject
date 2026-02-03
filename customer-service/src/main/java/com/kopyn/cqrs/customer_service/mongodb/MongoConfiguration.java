package com.kopyn.cqrs.customer_service.mongodb;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;

@Configuration
public class MongoConfiguration {

//    @Bean
//    public ReactiveMongoDatabaseFactory mongoDatabaseFactory() {
//        return new SimpleReactiveMongoDatabaseFactory(MongoClients.create(), "eventstore");
//    }

}
