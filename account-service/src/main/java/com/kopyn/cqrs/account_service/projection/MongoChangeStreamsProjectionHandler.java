package com.kopyn.cqrs.account_service.projection;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Class used to react to CustomerDeletedEvents to delete all accounts belonging to such customer.
 */
@Service
@Slf4j
public class MongoChangeStreamsProjectionHandler {
}
