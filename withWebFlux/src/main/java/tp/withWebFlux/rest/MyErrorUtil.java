package tp.withWebFlux.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import reactor.core.publisher.Mono;
import tp.withWebFlux.dto.Dto;
import tp.withWebFlux.exception.NotFoundException;

public class MyErrorUtil {
	public static ResponseEntity<? extends Object> buildErrorResponseEntity(Dto.ApiError apiError){
		return new ResponseEntity<Dto.ApiError>(apiError, apiError.status());
	}
	
	public static ResponseEntity<? extends Object> buildErrorResponseEntity(HttpStatus status,String message){
		Dto.ApiError apiError=new Dto.ApiError(status,message);
		return new ResponseEntity<Dto.ApiError>(apiError, apiError.status());
	}
	
	public static Mono<ResponseEntity<? extends Object>> 
    	withMonoOkRespEntity(Mono<? extends Object> mono){
			return mono.map(ResponseEntity::ok);
    }
	
	public static Mono<ResponseEntity<? extends Object>> 
	           withMonoErrorRespEntity(Mono<ResponseEntity<? extends Object>> mono,HttpStatus status){
		return mono.onErrorResume(RuntimeException.class , 
		        err -> Mono.just(MyErrorUtil.buildErrorResponseEntity(status,err.getMessage()))
		        );
	}
	
	public static Mono<ResponseEntity<? extends Object>> 
	        monoResponseEntity(Mono<? extends Object> mono){
		Mono<ResponseEntity<? extends Object>> monoRespEntity = mono.map(ResponseEntity::ok);
		return monoRespEntity.onErrorResume(NotFoundException.class , 
		        err -> Mono.just(MyErrorUtil.buildErrorResponseEntity(HttpStatus.NOT_FOUND,err.getMessage()))
		        )
		        .onErrorResume(RuntimeException.class , 
		        err -> Mono.just(MyErrorUtil.buildErrorResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR,err.getMessage()))
		        );
	}
}
