Sans la dépendance de.flapdoodle.embed.mongo.spring30x (dans pom.xml)
Lors des tests unitaires (RepositoryTest with mongoDB) , 
La base de données utilisées est celle configurée dans application.properties
(ex: spring.data.mongodb.uri=mongodb://localhost:27017/test
     spring.data.mongodb.database=test)
Dans ce mode de fonctionnement:
  - le serveur mongoDB doit être préalablement installé/démarré
  - les données générées par les tests ne sont pas effacées
  
_______________________

Si l'on ajoute la dépendance de.flapdoodle.embed.mongo.spring30x (dans pom.xml)
Une base de données mongoDB temporaire est automatiquement utilisée
lors de l'execution des tests unitaires. 
Dans ce mode de fonctionnement:
  - le serveur mongoDB n'a pas besoin d'être préalablement installé/démarré
    (une version basée sur la propriété de.flapdoodle.mongodb.embedded.version=5.0.0 ou autre
     sera automatiquement utilisée)
  - les données générées par les tests seront automatiquement effacées
    lors du futur lancement d'un paquet de tests
  - la base spécifiée par la proprieté spring.data.mongodb.uri=mongodb://localhost:27017/test)
    n'est pas utilisée et donc pas modifiée.   