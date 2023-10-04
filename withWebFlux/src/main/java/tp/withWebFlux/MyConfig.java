package tp.withWebFlux;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

//@Configuration
//@EnableReactiveMongoRepositories
public class MyConfig {
	
	@Bean
    public MongoClient mongoClient() {
        return MongoClients.create();
    }

}
