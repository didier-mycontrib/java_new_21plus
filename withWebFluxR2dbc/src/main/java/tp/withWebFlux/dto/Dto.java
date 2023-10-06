package tp.withWebFlux.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class Dto {
	public record NewsL0 (String id,String title,String text,String date) {
		
	}
	
	public record ApiError(HttpStatus status,String message,LocalDateTime timestamp){
		public ApiError(HttpStatus status,String message) {
			this(status,message,LocalDateTime.now());
		}
	}
}
