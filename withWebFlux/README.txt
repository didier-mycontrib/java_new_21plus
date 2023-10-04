Spring webflux (utilisant reactor netty en interne)
s'appuie à fond sur la programmation réactive .

Pour que tout soit cohérent , il faut que :
- les @RestController exposent des Mono<T> et Flux<T>
- les @Repository soient en mode "reactive"
  ReactiveMongoRepository et ReactiveCrudRepository existent bien
  ReactiveJpaRepository n'existe pas mais ....
  ReactiveCrudRepository expose des Mono<T> et Flux<T>
- les @Service doivent eux aussi exposer des Mono<T> et Flux<T>