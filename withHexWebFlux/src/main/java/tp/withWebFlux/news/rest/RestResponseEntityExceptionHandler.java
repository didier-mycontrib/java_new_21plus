package tp.withWebFlux.news.rest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

import tp.withWebFlux.news.core.exception.NotFoundException;
import tp.withWebFlux.news.rest.dto.Dto.ApiError;

/*
 * NB: this ExceptionHandler inherits from a reactive version of ResponseEntityExceptionHandler
 *     le declenchement s'effectue lorsqu'une méthode d'un @RestController
 *     remonte un Mono ou Flux comportant une erreur/exception 
 *     (que l'on pourrait gérer via onError callback du .subscribe)
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{
	
	private Logger logger = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);
	

	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(apiError, apiError.status());
	}

	@ExceptionHandler(NotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFound(NotFoundException ex) {
		logger.debug("ExceptionHandler(NotFoundException) ex="+ex.getMessage());
		return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, ex.getMessage()));
	}
	
}
