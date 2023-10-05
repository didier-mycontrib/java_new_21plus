package tp.withWebFlux.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tp.withWebFlux.dto.Dto;
import tp.withWebFlux.dto.Dto.NewsL0;
import tp.withWebFlux.service.NewsServiceWithDto;

@RestController
@RequestMapping("/rest/api-news/news")
public class NewsRestCtrl {
	
	@Autowired
	private NewsServiceWithDto newsService;
	
	/*
	//V1 (without reactive exception handler)
	
	public static ResponseEntity<Object> buildErrorResponseEntity(HttpStatus status , String message) {
		ApiError apiError = new ApiError(status, message);
		return new ResponseEntity<>(apiError, apiError.status());
	}
	
	public static Mono<ResponseEntity<? extends Object>> asMonoResponseEntity(Mono<? extends Object> mono){
			Mono<ResponseEntity<? extends Object>> monoRespEntity = mono.map(ResponseEntity::ok);
			return monoRespEntity.onErrorResume(NotFoundException.class , 
						err -> Mono.just(buildErrorResponseEntity(
								              HttpStatus.NOT_FOUND,err.getMessage()))
					)
					.onErrorResume(RuntimeException.class , 
						err -> Mono.just(buildErrorResponseEntity(
								              HttpStatus.INTERNAL_SERVER_ERROR,err.getMessage()))
					);
    }
		
	@GetMapping("/{id}")
	public Mono<ResponseEntity<? extends Object>> getNewsById(@PathVariable String id) {
		return asMonoResponseEntity(newsService.searchDtoById(id));
	}
	*/
	
	//V2 (with reactive exception handler)
	@GetMapping("/{id}")
	public Mono<Dto.NewsL0> getNewsById(@PathVariable String id) {
		return newsService.searchDtoById(id);
	}
	
	
	//news
	//news?title=news1
	@GetMapping
	public Flux<Dto.NewsL0> getByCriteria(@RequestParam(name="title" , required = false)String title) {
	    if(title!=null)
	    	return newsService.searchDtoByTitle(title);
	    else
	    	return newsService.searchAllDto();
	}
	
	//http://localhost:8181/withWebFlux/rest/api-news/news (POST)
	//{ "id" : null , "title" : "newsX" , "text" : "text of newsX" , "date" : null}
	@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<NewsL0> create(@RequestBody NewsL0 newsDto){
        return newsService.createWithDto(newsDto);
    }
	
	//http://localhost:8181/withWebFlux/rest/api-news/news (PUT)
	//{ "id" : "651d32d98b8cf715579ac76c" , "title" : "updated newsX" , "text" : "changed text of newsX" , "date" : "2023-10-05"}
	@PutMapping
  	public Mono<ResponseEntity<NewsL0>> update(@RequestBody NewsL0 newsDto){
        return newsService.updateWithDto(newsDto)
        	   .map(ResponseEntity::ok);
    }
	
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> deleteNewsById(@PathVariable String id) {
	    return newsService.deleteNewsById(id)
	    		.map( r -> ResponseEntity.ok().<Void>build());
	}
	

}
