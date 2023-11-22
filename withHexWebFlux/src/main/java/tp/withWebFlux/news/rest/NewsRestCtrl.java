package tp.withWebFlux.news.rest;

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
import tp.withWebFlux.news.core.domain.entity.News;
import tp.withWebFlux.news.core.reactive.api.ReactiveNewsService;

@RestController
@RequestMapping("/rest/api-news/news")
public class NewsRestCtrl {
	
	@Autowired
	private ReactiveNewsService newsService;
	
	
	
	//V2 (with reactive exception handler)
	@GetMapping("/{id}")
	public Mono<News> getNewsById(@PathVariable String id) {
		return newsService.getById(id);
	}
	
	
	//news
	//news?title=news1
	@GetMapping
	public Flux<News> getByCriteria(@RequestParam(name="title" , required = false)String title) {
	    if(title!=null)
	    	return newsService.queryByTitle(title);
	    else
	    	return newsService.queryAll();
	}
	
	//http://localhost:8181/withWebFlux/rest/api-news/news (POST)
	//{ "id" : null , "title" : "newsX" , "text" : "text of newsX" , "date" : null}
	@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<News> create(@RequestBody News newsDto){
        return newsService.create(newsDto);
    }
	
	//http://localhost:8181/withWebFlux/rest/api-news/news (PUT)
	//{ "id" : "651d32d98b8cf715579ac76c" , "title" : "updated newsX" , "text" : "changed text of newsX" , "date" : "2023-10-05"}
	@PutMapping
  	public Mono<ResponseEntity<News>> update(@RequestBody News newsDto){
        return newsService.update(newsDto)
        	   .map(ResponseEntity::ok);
    }
	
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> deleteNewsById(@PathVariable String id) {
	    return newsService.deleteById(id)
	    		.map( r -> ResponseEntity.ok().<Void>build());
	}
	

}
