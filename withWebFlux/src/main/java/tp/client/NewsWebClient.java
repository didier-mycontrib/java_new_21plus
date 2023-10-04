package tp.client;

import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import tp.withWebFlux.data.News;

public class NewsWebClient {
	
	static private WebClient client = WebClient.create("http://localhost:8181");

	public static void retreiveAllNews() {
		Flux<News> newsFlux = client.get()
				  .uri("/withWebFlux/rest/api-news/news")
				  .retrieve()
				  .bodyToFlux(News.class);
				        
		newsFlux.subscribe(System.out::println);
	}
	
	public static void main(String[] args) {
		retreiveAllNews();
		
		try {
			Thread.sleep(2000); //pause de 2s pour laisser le temps suffisant
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
