package tp.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tp.withWebFlux.news.core.domain.entity.News;

public class NewsWebClient {
	
	static private final String WS_URI = "/withWebFlux/rest/api-news/news";
	
	static private WebClient client = WebClient.create("http://localhost:8181");

	public static void retreiveAllNews() {
		Flux<News> newsFlux = client.get()
				  .uri(WS_URI)
				  .retrieve()
				  .bodyToFlux(News.class);
				        
		newsFlux.subscribe(System.out::println);
	}
	
	
	public static void crudNews() {
		//nouvelle news:
		News newsXyzDto = new News(null, "news Xyz", "texte de news xyz", null);
		//envoyer cela via un appel en POST
		Mono<News> postReactiveStream = client.post().uri(WS_URI)
		.body(Mono.just(newsXyzDto), News.class)
		.retrieve()
		.bodyToMono(News.class)
		.onErrorReturn(new News("?","not saved !!",null,null));
		News savedNews = postReactiveStream.block();
		System.out.println("savedNews=" + savedNews.toString());
		
		//modifier news:
		News changedNews = new News(savedNews.getId(),savedNews.getTitle()+ "*",
				                 "nouveau texte de news xyz", "2023-01-01");
		//envoyer cela via un appel en PUT
		Mono<News> putReactiveStream = client.put().uri(WS_URI)
		.body(Mono.just(changedNews), News.class)
		.retrieve()
		.bodyToMono(News.class)
		.onErrorReturn(new News("?","not updated !!",null,null));
		News updatedNews = putReactiveStream.block();
		System.out.println("updatedNews=" + updatedNews.toString());
		
		//supprimer la news:
		Mono<ResponseEntity<Void>> deleteReactiveStream = client.delete()
				  .uri(WS_URI + "/" + updatedNews.getId())
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
