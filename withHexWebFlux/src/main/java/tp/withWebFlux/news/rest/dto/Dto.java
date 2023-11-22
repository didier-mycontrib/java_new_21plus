package tp.withWebFlux.news.rest.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class Dto {
	
	public record ApiError(HttpStatus status,String message,LocalDateTime timestamp){
		public ApiError(HttpStatus status,String message) {
			this(status,message,LocalDateTime.now());
		}
	}
}
