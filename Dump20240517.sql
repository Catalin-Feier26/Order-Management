CREATE DATABASE  IF NOT EXISTS `ordermanagement` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `ordermanagement`;
-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: ordermanagement
-- ------------------------------------------------------
-- Server version	8.0.37

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
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `client` (
  `clientId` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `contactNumber` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`clientId`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES (1,'John Doe','123 Main Street, Anytown, USA','123-456-7890'),(2,'Jane Smith','456 Elm Street, Sometown, USA','234-567-8901'),(5,'Emma Davis','654 Pine Road, Somewhere, USA','567-890-1234'),(6,'Michael Wilson','987 Cedar Lane, Anywhere, USA','678-901-2345'),(7,'Sarah Taylor','246 Birch Boulevard, Elsewhere, USA','789-012-3456'),(8,'Davide','Espagna','999-999-999'),(9,'Jennifer Garcia','135 Cherry Lane, Anyplace, USA','901-234-5678'),(10,'James Rodriguez','468 Apple Street, Elseplace, USA','012-345-6789'),(44,'Catalin','Satu Mare','-1111111111'),(48,'Catalin','Satu Mare','123-999-88'),(53,'Nora','noragmail','123-333-222');
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orderdetail`
--

DROP TABLE IF EXISTS `orderdetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orderdetail` (
  `ordertableId` int NOT NULL,
  `productId` int NOT NULL,
  `quantity` int NOT NULL,
  `orderdetailId` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`orderdetailId`),
  KEY `ordertableId` (`ordertableId`),
  KEY `productId` (`productId`),
  CONSTRAINT `orderdetail_ibfk_1` FOREIGN KEY (`ordertableId`) REFERENCES `ordertable` (`ordertableId`),
  CONSTRAINT `orderdetail_ibfk_2` FOREIGN KEY (`productId`) REFERENCES `product` (`productId`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderdetail`
--

LOCK TABLES `orderdetail` WRITE;
/*!40000 ALTER TABLE `orderdetail` DISABLE KEYS */;
INSERT INTO `orderdetail` VALUES (1,2,3,11),(1,3,2,12),(24,2,2,14),(30,6,2,26),(39,12,20,28);
/*!40000 ALTER TABLE `orderdetail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ordertable`
--

DROP TABLE IF EXISTS `ordertable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ordertable` (
  `ordertableId` int NOT NULL AUTO_INCREMENT,
  `clientId` int NOT NULL,
  `orderDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ordertableId`),
  KEY `clientId` (`clientId`),
  CONSTRAINT `ordertable_ibfk_1` FOREIGN KEY (`clientId`) REFERENCES `client` (`clientId`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ordertable`
--

LOCK TABLES `ordertable` WRITE;
/*!40000 ALTER TABLE `ordertable` DISABLE KEYS */;
INSERT INTO `ordertable` VALUES (1,1,'2024-05-13 20:49:30'),(2,1,'2024-05-13 20:50:31'),(24,5,'2024-05-16 10:01:58'),(25,6,'2024-05-16 10:01:58'),(28,8,'2024-05-16 11:40:39'),(29,8,'2024-05-16 11:40:47'),(30,1,'2024-05-16 11:54:26'),(32,8,'2024-05-16 11:54:56'),(33,48,'2024-05-16 12:59:58'),(35,8,'2024-05-16 14:14:35'),(39,44,'2024-05-17 05:30:56');
/*!40000 ALTER TABLE `ordertable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `productId` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `price` decimal(10,2) NOT NULL,
  `stockQuantity` int NOT NULL,
  PRIMARY KEY (`productId`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'Widget','A versatile widget for all your needs',9.99,100),(2,'Gadget','A handy gadget with multiple functions',19.99,150),(3,'Thingamajig','The ultimate thingamajig for your projects',14.99,180),(5,'Whatchamacallit','Get your hands on this whatchamacallit now!',29.99,77),(6,'Contraption','An ingenious contraption for your tasks',24.99,248),(7,'Doodad','Everyone needs a doodad in their toolbox',11.99,178),(8,'Widgetizer','The widgetizer that everyone is talking about',17.99,289),(9,'Thingummy','A thingummy that will simplify your life',21.99,147),(11,'Doohickey Deluxe','Upgrade to the doohickey deluxe today!',39.99,100),(12,'Widget Wonder','Experience the wonder of this widget',8.99,195),(14,'Thingamajig Supreme','The supreme thingamajig you have been waiting for',22.99,160),(15,'Doodad Dream','Make your dreams come true with this doodad',10.99,190),(44,'Peach','Fresh, tasty',20.00,30),(45,'Grape','Plentiful',4.99,184);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-17 10:55:35
