-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: n2_ecommerce
-- ------------------------------------------------------
-- Server version	8.0.29

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
-- Table structure for table `tbusuarioxenderecos`
--

DROP TABLE IF EXISTS `tbusuarioxenderecos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbusuarioxenderecos` (
  `id_usuario` varchar(40) NOT NULL,
  `id_endereco` varchar(40) NOT NULL,
  PRIMARY KEY (`id_usuario`,`id_endereco`),
  KEY `id_endereco` (`id_endereco`),
  CONSTRAINT `tbusuarioxenderecos_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `tbusuario` (`id`),
  CONSTRAINT `tbusuarioxenderecos_ibfk_2` FOREIGN KEY (`id_endereco`) REFERENCES `tbenderecos` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbusuarioxenderecos`
--

LOCK TABLES `tbusuarioxenderecos` WRITE;
/*!40000 ALTER TABLE `tbusuarioxenderecos` DISABLE KEYS */;
INSERT INTO `tbusuarioxenderecos` VALUES ('c9e34f36-65c9-4e2c-ac9b-61d1f3f0f6c6','087094ca-1c41-4155-b8f1-2698853aea79');
/*!40000 ALTER TABLE `tbusuarioxenderecos` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-05-26 19:29:31
