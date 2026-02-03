package com.kopyn.cqrs.customer_service.mongodb;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

import java.util.Collections;

@Configuration
public class MongoAppConfig {

//    @Override
//    public String getDatabaseName() {
//        return "eventstore";
//    }
//
//    @Override
//    protected void configureClientSettings(MongoClientSettings.Builder builder) {
//        builder
//                .credential(MongoCredential.createCredential("myuser", "eventstore",
//                        "mypassword".toCharArray()))
//                .applyToClusterSettings(settings ->
//                        settings.hosts(Collections.singletonList(new ServerAddress("127.0.0.1", 27017))));
//    }
}
