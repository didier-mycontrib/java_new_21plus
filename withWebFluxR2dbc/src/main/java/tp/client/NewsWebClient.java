package tp.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tp.withWebFlux.dto.Dto;
import tp.withWebFlux.dto.Dto.NewsL0;

public class NewsWebClient {
	
	static private final String WS_URI = "/withWebFlux/rest/api-news/news";
	
	static private WebClient client = WebClient.create("http://localhost:8181");

	public static void retreiveAllNews() {
		Flux<NewsL0> newsFlux = client.get()
				  .uri(WS_URI)
				  .retrieve()
				  .bodyToFlux(NewsL0.class);
				        
		newsFlux.subscribe(System.out::println);
	}
	
	
	public static void crudNews() {
		//nouvelle news:
		Dto.NewsL0 newsXyzDto = new Dto.NewsL0(null, "news Xyz", "texte de news xyz", null);
		//envoyer cela via un appel en POST
		Mono<Dto.NewsL0> postReactiveStream = client.post().uri(WS_URI)
		.body(Mono.just(newsXyzDto), Dto.NewsL0.class)
		.retrieve()
		.bodyToMono(Dto.NewsL0.class)
		.onErrorReturn(new Dto.NewsL0("?","not saved !!",null,null));
		Dto.NewsL0 savedNews = postReactiveStream.block();
		System.out.println("savedNews=" + savedNews.toString());
		
		//modifier news:
		Dto.NewsL0 changedNews = new Dto.NewsL0(savedNews.id(),savedNews.title()+ "*",
				                 "nouveau texte de news xyz", "2023-01-01");
		//envoyer cela via un appel en PUT
		Mono<Dto.NewsL0> putReactiveStream = client.put().uri(WS_URI)
		.body(Mono.just(changedNews), Dto.NewsL0.class)
		.retrieve()
		.bodyToMono(Dto.NewsL0.class)
		.onErrorReturn(new Dto.NewsL0("?","not updated !!",null,null));
		Dto.NewsL0 updatedNews = putReactiveStream.block();
		System.out.println("updatedNews=" + updatedNews.toString());
		
		//supprimer la news:
		Mono<ResponseEntity<Void>> deleteReactiveStream = client.delete()
				  .uri(WS_URI + "/" + updatedNews.id())
				  .retrieve()
				  .toBodilessEntity();
		deleteReactiveStream.subscribe((resp)->System.out.println("sucessful delete status=" + resp.getStatusCode()),
				                       (err)->System.out.println("delete err=" + err.toString()));
				
	}
	
	public static void main(String[] args) {
		crudNews();
		retreiveAllNews();
		
		try {
			Thread.sleep(2000); //pause de 2s pour laisser le temps suffisant
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
