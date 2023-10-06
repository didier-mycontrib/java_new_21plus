package tp.withWebFlux;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;

import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;

//@Profile("r2dbc")
//@Configuration
//@EnableR2dbcRepositories(basePackages = "tp.withWebFlux.repository")
public class MyR2DBCConfig extends AbstractR2dbcConfiguration {
	
	private static Logger logger = LoggerFactory.getLogger(MyR2DBCConfig.class);
	
    @Bean
    public H2ConnectionFactory connectionFactory() {
    	logger.debug("MyR2DBCConfig");
        return new H2ConnectionFactory(
            H2ConnectionConfiguration.builder()
              //.url("mem:testdb;DB_CLOSE_DELAY=-1;")
              //.url("~/r2dbc")
              //.inMemory("testdb")
              .file("~/r2dbc")
              .username("sa")
              .build()
        );
    }
    
    @Bean
    ReactiveTransactionManager transactionManager(ConnectionFactory connectionFactory) {
        return new R2dbcTransactionManager(connectionFactory);
    }
}
