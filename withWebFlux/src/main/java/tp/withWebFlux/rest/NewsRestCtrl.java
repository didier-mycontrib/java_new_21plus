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
import tp.withWebFlux.data.News;
import tp.withWebFlux.dto.Dto;
import tp.withWebFlux.service.NewsServiceInternal;
import tp.withWebFlux.service.NewsServiceWithDto;

@RestController
@RequestMapping("/rest/api-news/news")
public class NewsRestCtrl {
	
	@Autowired
	private NewsServiceWithDto newsService;
	
	@Autowired
	private NewsServiceInternal newsServiceInternal;
	
	/*
	@GetMapping("/{id}")
	public Mono<ResponseEntity<? extends Object>> getNewsById(@PathVariable String id) {
		return MyErrorUtil.monoResponseEntity(newsService.searchDtoById(id));
		
		//Mono<ResponseEntity<? extends Object>> mono = 
				//MyErrorUtil.withMonoOkRespEntity(newsService.searchDtoById(id));
				//newsService.searchDtoById(id).map(ResponseEntity::ok);
	 	//return mono.onErrorReturn(MyErrorUtil.buildErrorResponseEntity(HttpStatus.NOT_FOUND,"nf with id="+id));	
		//return MyErrorUtil.withMonoErrorRespEntity(mono,HttpStatus.NOT_FOUND);
	}
	*/
	
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
		    //return newsService.searchAll();
	    	return newsService.searchAllDto();
	}
	
	//http://localhost:8181/withWebFlux/rest/api-news/news (POST)
	//{ "id" : null , "title" : "newsX" , "text" : "text of newsX" }
	@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<News> create(@RequestBody News news){
        return newsServiceInternal.createNews(news);
    }
	
	//http://localhost:8181/withWebFlux/rest/api-news/news (PUT)
	//{ "id" : "651d32d98b8cf715579ac76c" , "title" : "updated newsX" , "text" : "changed text of newsX" }
	@PutMapping
    /*public Mono<News> update(@RequestBody News news){
        return newsService.updateNews(news);
    }*/
	public Mono<ResponseEntity<News>> update(@RequestBody News news){
        return newsServiceInternal.updateNews(news)
        	   .map(ResponseEntity::ok)
        	   .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
	
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> deleteNewsById(@PathVariable String id) {
	    return newsServiceInternal.deleteNews(id)
	    		.map( r -> ResponseEntity.ok().<Void>build())
	            .defaultIfEmpty(ResponseEntity.notFound().build());
	}
	

}
