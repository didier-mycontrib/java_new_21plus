Dans le contexte de webflux (programation reactive),
les exception seront sont encapsulée dans un Mono ou un Flux
via un code de ce type:

.switchIfEmpty(Mono.error(new NotFoundException("no entity found with id="+id)));

===========

Lorqu'une erreur/exception remontera de la couche service vers la couche api_rest
de manière imbriquée dans un objet Mono ou Flux,

le @RestController ou bien le reactive exceptionHandler 
pourront alors déclencher un code de ce type:

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
	
-----

//V2 (with reactive exception handler)
	@GetMapping("/{id}")
	public Mono<Dto.NewsL0> getNewsById(@PathVariable String id) {
		return newsService.searchDtoById(id);
	}
	
and with 
...
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
...
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