package tp.withWebFlux.r2dbc;

import java.time.LocalDate;
import java.util.function.BiFunction;

import org.junit.jupiter.api.Test;

import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import reactor.core.publisher.Mono;
import tp.withWebFlux.data.News;

public class DirectR2dbcNewsTest {
	
	
    private ConnectionFactory buildConnectionFactory() {
    	
        return new H2ConnectionFactory(
            H2ConnectionConfiguration.builder()
              //.url("mem:testdb;DB_CLOSE_DELAY=-1;")
              .url("~/r2dbc")
              .username("sa")
              .build()
        );
        
    	/*
    	return  ConnectionFactories
    			  .get("r2dbc:h2:file://sa@/~/r2dbc");
    	//r2dbc:driver[:protocol]://[user:password@]host[:port][/path][?option=value]}
    	*/
    }
    
	
	
	@Test()
	public void testR2dbc(){
		
		//private static final String CONNECTION_URL = "r2dbc:mysql://username:password@localhost:3306/database_name";
	     ConnectionFactory connectionFactory = buildConnectionFactory();

	     Mono<Connection> connectionMono = Mono.from(connectionFactory.create());
	     
	     
	     BiFunction<Row, RowMetadata, News> rowNewsMappingFunction = 
	    		     (row, rowMetaData) -> { 
	    		    	    return new News(row.get("id",Long.class),
       		                                  row.get("title",String.class),
       		                                  row.get("text",String.class),
       		                                  LocalDate.parse(row.get("pub_date",String.class)));
	    		    	     } ;
	   
	   
	   
	     connectionMono.flatMapMany(connection -> connection
	    		         .createStatement("SELECT id,title,text,pub_date FROM news")
	    		         .execute()
	                	  )
	                      .flatMap(result -> result.map(rowNewsMappingFunction) )
	                .doOnNext(System.out::println)
	                .doOnError(e -> e.printStackTrace())
	                .doOnComplete(() -> System.out.println("Query completed"))
	                .subscribe();

	        // Prevent the program from exiting immediately
	        try {
	            Thread.sleep(5000);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	}

}
