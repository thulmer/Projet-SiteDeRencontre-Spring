-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: sitederencontre
-- ------------------------------------------------------
-- Server version	8.0.27

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `utilisateur`
--

DROP TABLE IF EXISTS `utilisateur`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `utilisateur` (
  `id` int NOT NULL AUTO_INCREMENT,
  `prenom` varchar(45) NOT NULL,
  `age` int NOT NULL,
  `localisation` varchar(60) NOT NULL,
  `sexe` tinyint unsigned NOT NULL DEFAULT '0',
  `preference` tinyint unsigned NOT NULL DEFAULT '0',
  `description` varchar(500) DEFAULT NULL,
  `img_link` varchar(255) DEFAULT NULL,
  `email` varchar(120) NOT NULL,
  `password` varchar(120) NOT NULL,
  `privilege` tinyint unsigned NOT NULL DEFAULT '0',
  `admin` tinyint unsigned NOT NULL DEFAULT '0',
  `en_ligne` tinyint unsigned NOT NULL DEFAULT '0',
  `recevoir_emails` tinyint unsigned NOT NULL DEFAULT '0',


  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_rma38wvnqfaf66vvmi57c71lo` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utilisateur`
--

LOCK TABLES `utilisateur` WRITE;
/*!40000 ALTER TABLE `utilisateur` DISABLE KEYS */;
INSERT INTO `utilisateur` VALUES (1,'Admin',26,'MONTREAL',1,1,'','','admin@gmail.com','admin',0,1,0,0),
(2,'Svetlana',24,'MONTREAL',1,0,'No time to waste','Svetlana Stasovska.png','svet@test.com','svet123',0,0,0,0),
(3,'Satya',45,'MONTREAL',0,1,NULL,'Satya Narayana.png','satya@gmail.com','satya123',0,0,0,0),
(4,'Pedro',40,'QUEBEC',0,1,'Donde esta la biblioteca?','Pedro Quintero.png','pedro@hotmail.com','pedro123',0,0,0,0),
(5,'Nathi',34,'QUEBEC',1,0,'Célibatante','Nathi_Sangweni.png','nathi@gmail.com','nathi123',0,0,0,0),
(6,'Tyler',21,'MONTREAL',0,1,'Sup party people?','Muhammad Evran.png','tyler@gmail.com','tyler123',0,0,0,0),
(7,'Momo',20,'QUEBEC',0,0,NULL,'Mohamed Zirri.jpg','momo@gmail.com','momo123',1,0,0,0),
(8,'Mike',36,'MONTREAL',0,0,'I like dogs, the beach, re-watching the Office and BBQ!','Mike Gates.png','mike@gmail.com','mike123',0,0,0,0),
(9,'Matt',28,'MONTREAL',0,1,NULL,'Matthew.png','matt@gmail.com','matt123',0,0,0,0),
(10,'Kent',65,'MONTREAL',0,0,'Il n\'est jamais assez tard pour trouver l\'amour.','KentHervey.png','kent@gmail.com','kent123',0,0,0,0),
(11,'Jack',29,'MONTREAL',0,0,NULL,'Jack Bruce.png','jack@gmail.com','jack123',0,0,0,0),
(12,'Isaac',24,'MONTREAL',0,1,NULL,'Isaac Henry.jpg','isaac@gmail.com','isaac123',0,0,0,0),
(13,'Freddy',34,'MONTREAL',0,1,NULL,'Frederick Santos.png','fred@gmail.com','fred123',0,0,0,0),
(14,'Bruce',58,'MONTREAL',0,1,NULL,'Bruce Kitchell.png','bruce@gmail.com','bruce123',0,0,0,0),
(15,'Anna',25,'QUEBEC',1,1,'Be proud','Anna Maria.jpg','anna@gmail.com','anna123',1,0,0,0),
(16,'Ammanolla',23,'MONTREAL',1,0,'Those are fake glasses lol','Ammanolla.png','amma@gmail.com','amma123',0,0,0,0),
(17,'Amina',29,'MONTREAL',1,0,'Looking for a catch.','Amina Elshal.png','amina@gmail.com','amina123',1,0,0,0),
(18,'Allada',30,'MONTREAL',0,1,NULL,'Allada Pavan.png','allada@gmail.com','allada123',0,0,0,0),
(19,'Ali',28,'MONTREAL',0,1,NULL,'Ali Raza.png','ali@gmail.com','ali123',0,0,0,0),
(20,'Alfie',24,'MONTREAL',0,0,NULL,'Alfred.png','alfie@gmail.com','alfie123',0,0,0,0),
(21,'Kat',26,'MONTREAL',1,1,'Not a cat.','Cat.jpg','kate@gmail.com','kat123',0,0,0,0),
(22,'Will',54,'MONTREAL',0,1,'A vessel for love','Will Smith.png','will@gmail.com','will123',1,0,0,0);
/*!40000 ALTER TABLE `utilisateur` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-05-22 19:27:04
