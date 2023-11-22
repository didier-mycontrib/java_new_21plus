startMongoDB.bat with mongodb.cfg
can be useful to display data after Junit/SpringBootTest/MongoReactiveRepositoryTest
with embedded mongo db
-------------
NB: port=27018 plutot que 27017 pour ne pas etre en conflit avec le service mongoDB
démarré automatiquement.

URL pour visualisation des données avec MongoDB Compass:
mongodb://localhost:27018

Attention:
pour un bon fonctionnement des tests unitaires en mode Junit/SpringBootTest/MongoReactiveRepositoryTest
il faut pas que la base de données soit verrouillée/utilisée par un autre serveur (qui doit être arrêté)

config de ce pc (au 6/10/2023):
---------------

Mongo5 installé dans MONGO_HOME=C:\Program Files\MongoDB\Server\5.0
cohérent avec option 
de.flapdoodle.mongodb.embedded.version=5.0.0
de application.properties

ET
dbpath=C:\tp\local-git-mycontrib-repositories\java_new_21plus\withWebFlux\my_mongo_tmp_dir
cohérent avec
@SpringBootTest(
	properties = {
		"de.flapdoodle.mongodb.embedded.databaseDir=./my_mongo_tmp_dir"
	}
)


