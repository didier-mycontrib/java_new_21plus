Variante "architecture_hexagonale" de 
Spring webflux (utilisant reactor netty en interne)
==========================

Rapide retour d'expérience
------------------------
Bien que techniquement possible l'architecture hexagonale se prête assez peu 
à la programmation réactive car :

- toutes des api/spi doivent être en mode "reactive" et donc PAS AGNOSTIQUE , PEU PORTABLE/REUTILISABLE
- l'architecture hexagonale n'a de réelle valeur ajoutée que si elle est acompagnée d'une logique métier conséquente
  et pour l'instant spring_web_flux / mongoDB_reactive est plutôt approprié pour des cas simples  
  
On peut néanmoins faire l'effort de structurer correctement le code en distinguant
- la petite partie invariable "core.domain.entity..." (News, éventuels sous services purement métier sans I/O en synchrone immédiat)
- la partie spécifiquement en more réactif "core.reactive..."  