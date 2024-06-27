--
-- Table structure for table `users`
--
CREATE TABLE `users` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Data for table `users`
--
LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'test1@test.com','test1','$2a$10$swlALPLFMJ0w3C.6uEuT4e1814WdenhFjM/BsW4e7TLDwMGUuntHW'),(2,'test0@test.com','test0','$2a$10$zcwrF0ReHljVL5aXwqriQeY1MKPYVtr4Oa.mkT1jSDQqqXrLZPgSi');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `topics`
--
CREATE TABLE `topics` (
  `topic_id` bigint NOT NULL AUTO_INCREMENT,
  `description` text NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`topic_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Data for table `topics`
--
LOCK TABLES `topics` WRITE;
/*!40000 ALTER TABLE `topics` DISABLE KEYS */;
INSERT INTO `topics` VALUES (1,'Angular est un framework open source développé par Google, utilisé pour créer des applications web dynamiques. Il permet de construire des applications en utilisant des composants réutilisables et modulaires. Angular utilise TypeScript comme langage principal et offre des outils puissants pour la gestion de l\'architecture, la performance et les tests. Il est largement adopté pour sa capacité à créer des interfaces utilisateur riches et interactives.','Angular'),(2,'Java est un langage de programmation orienté objet, développé par Sun Microsystems (maintenant Oracle). Il est conçu pour être portable, sécurisé et robuste, permettant le développement d\'applications multiplateformes. Java utilise une machine virtuelle (JVM) pour exécuter le code, assurant ainsi la compatibilité entre différents systèmes d\'exploitation. Il est largement utilisé pour le développement de logiciels d\'entreprise, d\'applications mobiles Android et de systèmes embarqués.','Java'),(3,'JUnit est un framework de test open source pour Java, utilisé principalement pour écrire et exécuter des tests unitaires. Développé par Kent Beck et Erich Gamma, JUnit permet aux développeurs de tester des sections spécifiques de code de manière isolée, assurant ainsi la fiabilité et la qualité du logiciel. Il fournit des annotations et des assertions pour simplifier l\'écriture des tests. JUnit est largement adopté dans les pratiques de développement agile et de développement piloté par les tests (TDD).','JUnit'),(4,'\nJest est un framework de test open source développé par Facebook, utilisé principalement pour tester des applications JavaScript, en particulier celles utilisant React. Il offre des fonctionnalités avancées telles que les tests unitaires, les tests d\'intégration et les tests de snapshots. Jest est apprécié pour sa simplicité d\'utilisation, sa rapidité et sa capacité à générer des rapports de couverture de code. Il permet d\'écrire des tests de manière intuitive et assure la stabilité du code à travers des mises à jour et des modifications.','Jest');
/*!40000 ALTER TABLE `topics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_topic`
--
CREATE TABLE `user_topic` (
  `user_id` bigint NOT NULL,
  `topic_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`topic_id`),
  KEY `FK8a3sox8wsdmmpegof8fepfte7` (`topic_id`),
  CONSTRAINT `FK3qiv7jhn37ne73gdqnyalqnwf` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FK8a3sox8wsdmmpegof8fepfte7` FOREIGN KEY (`topic_id`) REFERENCES `topics` (`topic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Data for table `user_topic`
--
LOCK TABLES `user_topic` WRITE;
/*!40000 ALTER TABLE `user_topic` DISABLE KEYS */;
INSERT INTO `user_topic` VALUES (1,1),(2,1),(1,2),(2,2),(2,3);
/*!40000 ALTER TABLE `user_topic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posts`
--
CREATE TABLE `posts` (
  `post_id` bigint NOT NULL AUTO_INCREMENT,
  `content` longtext NOT NULL,
  `created_at` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `topic_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`post_id`),
  KEY `FKrfchr8dax0kfngvvkbteh5n7h` (`topic_id`),
  KEY `FK5lidm6cqbc7u4xhqpxm898qme` (`user_id`),
  CONSTRAINT `FK5lidm6cqbc7u4xhqpxm898qme` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FKrfchr8dax0kfngvvkbteh5n7h` FOREIGN KEY (`topic_id`) REFERENCES `topics` (`topic_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Data for table `posts`
--
LOCK TABLES `posts` WRITE;
/*!40000 ALTER TABLE `posts` DISABLE KEYS */;
INSERT INTO `posts` VALUES (1,'Tester les applications est une étape cruciale du développement logiciel pour garantir leur robustesse et leur fiabilité. JUnit, combiné avec Spring Boot, offre un environnement puissant pour effectuer des tests unitaires et d\'intégration. Voici un guide pour vous aider à écrire des tests efficaces avec JUnit dans un projet Spring Boot.\n\nPrérequis\nAvant de commencer, assurez-vous d\'avoir installé les éléments suivants :\n\nJDK (Java Development Kit)\nMaven ou Gradle (outil de gestion de dépendances)\nUn IDE compatible avec Spring Boot (comme IntelliJ IDEA ou Eclipse)\nÉtape 1 : Configuration du Projet\nCréez un nouveau projet Spring Boot ou utilisez un projet existant. Ajoutez les dépendances nécessaires pour JUnit et Spring Boot dans votre fichier pom.xml (pour Maven) ou build.gradle (pour Gradle).\n\nPour Maven :\n\nxml\nCopier le code\n<dependency>\n    <groupId>org.springframework.boot</groupId>\n    <artifactId>spring-boot-starter-test</artifactId>\n    <scope>test</scope>\n</dependency>\nPour Gradle :\n\ngroovy\nCopier le code\ntestImplementation \'org.springframework.boot:spring-boot-starter-test\'\nCes dépendances incluent JUnit ainsi que d\'autres bibliothèques utiles pour le test, comme Mockito et AssertJ.\n\nÉtape 2 : Créer une Classe de Test\nSupposons que nous ayons un service simple appelé CalculatorService avec une méthode add :\n\njava\nCopier le code\n@Service\npublic class CalculatorService {\n    public int add(int a, int b) {\n        return a + b;\n    }\n}\nNous allons maintenant créer une classe de test pour cette service. Créez un fichier dans le répertoire src/test/java correspondant à la structure de votre projet, par exemple com.example.demo.\n\njava\nCopier le code\npackage com.example.demo;\n\nimport static org.junit.jupiter.api.Assertions.assertEquals;\n\nimport org.junit.jupiter.api.Test;\nimport org.springframework.beans.factory.annotation.Autowired;\nimport org.springframework.boot.test.context.SpringBootTest;\n\n@SpringBootTest\npublic class CalculatorServiceTest {\n\n    @Autowired\n    private CalculatorService calculatorService;\n\n    @Test\n    public void testAdd() {\n        int result = calculatorService.add(2, 3);\n        assertEquals(5, result);\n    }\n}\nÉtape 3 : Comprendre l\'Annotation @SpringBootTest\nL\'annotation @SpringBootTest est utilisée pour charger le contexte de l\'application Spring Boot. Cela permet d\'intégrer facilement les composants Spring dans les tests.\n\nÉtape 4 : Exécuter les Tests\nLa plupart des IDE modernes vous permettent d\'exécuter les tests directement. Dans IntelliJ IDEA, vous pouvez cliquer sur l\'icône verte à côté de la méthode de test ou du nom de la classe pour exécuter les tests.\n\nÉtape 5 : Tester les Composants Complexes\nPour les services dépendant d\'autres composants, utilisez des mocks pour isoler les unités de test. Par exemple, si CalculatorService dépendait d\'un autre service, vous pourriez utiliser Mockito pour créer des mocks :\n\njava\nCopier le code\nimport static org.mockito.Mockito.when;\nimport static org.mockito.Mockito.mock;\n\nimport org.junit.jupiter.api.BeforeEach;\nimport org.mockito.InjectMocks;\nimport org.mockito.Mock;\nimport org.mockito.MockitoAnnotations;\n\npublic class CalculatorServiceTest {\n\n    @Mock\n    private AnotherService anotherService;\n\n    @InjectMocks\n    private CalculatorService calculatorService;\n\n    @BeforeEach\n    public void setUp() {\n        MockitoAnnotations.openMocks(this);\n    }\n\n    @Test\n    public void testAdd() {\n        when(anotherService.someMethod()).thenReturn(someValue);\n        int result = calculatorService.add(2, 3);\n        assertEquals(5, result);\n    }\n}\nConclusion\nJUnit et Spring Boot forment un duo puissant pour écrire des tests unitaires et d\'intégration. En configurant correctement votre projet et en utilisant les annotations appropriées, vous pouvez tester efficacement vos applications pour assurer leur qualité et leur fiabilité. N\'oubliez pas de tester non seulement les cas de réussite, mais aussi les cas d\'échec pour garantir que votre application se comporte comme prévu dans toutes les situations.','2024-06-24T14:29:40.446759700Z','Écrire un Test avec JUnit dans un Projet Spring Boot',3,1),(2,'DTOs or Data Transfer Objects are objects that carry data between processes in order to reduce the number of methods calls. The pattern was first introduced by Martin Fowler in his book EAA.\n\nFowler explained that the pattern’s main purpose is to reduce roundtrips to the server by batching up multiple parameters in a single call. This reduces the network overhead in such remote operations.\n\nAnother benefit is the encapsulation of the serialization’s logic (the mechanism that translates the object structure and data to a specific format that can be stored and transferred). It provides a single point of change in the serialization nuances. It also decouples the domain models from the presentation layer, allowing both to change independently.','2024-06-24T14:40:30.414613400Z','What are DTO in Java',2,1),(3,'Un article sur cypress, le framework de test qui permet de réaliser des tests end-to-end.','2024-06-27T11:24:38.463928800Z','Cypress un article',3,2);
/*!40000 ALTER TABLE `posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comments`
--
CREATE TABLE `comments` (
  `comment_id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(255) NOT NULL,
  `created_at` varchar(255) NOT NULL,
  `post_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `FKh4c7lvsc298whoyd4w9ta25cr` (`post_id`),
  KEY `FK8omq0tc18jd43bu5tjh6jvraq` (`user_id`),
  CONSTRAINT `FK8omq0tc18jd43bu5tjh6jvraq` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FKh4c7lvsc298whoyd4w9ta25cr` FOREIGN KEY (`post_id`) REFERENCES `posts` (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Data for table `comments`
--
LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (1,'Thanks for this great post introducing DTO in Java.','2024-06-27T12:16:23.581007700Z',2,2);
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;