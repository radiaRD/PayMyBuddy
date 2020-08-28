package com.payMyBuddy.payMyBuddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpringBootApplication
@EnableJpaAuditing
public class PayMyBuddyApplication {
    private static final Logger logger = LogManager.getLogger(PayMyBuddyApplication.class);
    public static void main(String[] args) {
        logger.info("Initializing PayMyBuddy");
        SpringApplication.run(PayMyBuddyApplication.class, args);
    }

}
