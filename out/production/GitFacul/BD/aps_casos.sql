-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: aps
-- ------------------------------------------------------
-- Server version	8.0.41

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
-- Table structure for table `casos`
--

DROP TABLE IF EXISTS `casos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `casos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cidade_id` int DEFAULT NULL,
  `doenca_id` int DEFAULT NULL,
  `data_registro` date DEFAULT NULL,
  `qtd_infectados` int DEFAULT NULL,
  `qtd_mortos` int DEFAULT NULL,
  `qtd_curados` int DEFAULT NULL,
  `qtd_em_tratamento` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `cidade_id` (`cidade_id`),
  KEY `doenca_id` (`doenca_id`),
  CONSTRAINT `casos_ibfk_1` FOREIGN KEY (`cidade_id`) REFERENCES `cidades` (`id`),
  CONSTRAINT `casos_ibfk_2` FOREIGN KEY (`doenca_id`) REFERENCES `doencas` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `casos`
--

LOCK TABLES `casos` WRITE;
/*!40000 ALTER TABLE `casos` DISABLE KEYS */;
INSERT INTO `casos` VALUES (1,1,1,'2025-04-01',1500,30,1200,270),(2,2,1,'2025-04-01',800,10,700,90);
/*!40000 ALTER TABLE `casos` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-26 16:00:18
CREATE TABLE IF NOT EXISTS casos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome_paciente VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) NOT NULL,
    cidade VARCHAR(50) NOT NULL CHECK (cidade IN ('São Paulo', 'Guarulhos', 'Campinas', 'São Bernardo do Campo', 'Santo André')),
    tipo_diabetes ENUM('Tipo 1', 'Tipo 2') NOT NULL,
    estado_tratamento ENUM('Sem tratamento', 'Em tratamento', 'Internado', 'Óbito') NOT NULL,
    data_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

