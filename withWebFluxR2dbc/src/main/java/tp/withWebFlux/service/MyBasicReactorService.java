package tp.withWebFlux.service;

import java.util.List;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

@Service
public class MyBasicReactorService {
     public Flux<String> smallStringInUppercase(List<String> sList , int maxLength){
    	 return Flux.fromIterable(sList)
    			  .filter(name -> name.length() <= maxLength)
    			  .map(String::toUpperCase);
     }
}
