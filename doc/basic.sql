-- MySQL dump 10.13  Distrib 8.0.17, for Win64 (x86_64)
--
-- Host: localhost    Database: baisc
-- ------------------------------------------------------
-- Server version	5.7.27-log

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
-- Table structure for table `college`
--

DROP TABLE IF EXISTS `college`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `college` (
  `c_id` int(11) NOT NULL,
  `c_name_ch` varchar(255) NOT NULL,
  `c_name_en` varchar(255) NOT NULL,
  `c_country` varchar(255) NOT NULL,
  `c_site` varchar(255) NOT NULL,
  `c_info` longtext NOT NULL,
  `c_pic` varchar(255) NOT NULL,
  `c_tofel` decimal(10,0) DEFAULT NULL,
  `c_gre` decimal(10,0) DEFAULT NULL,
  `c_GPA` decimal(10,0) DEFAULT NULL,
  `c_fee` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`c_id`),
  UNIQUE KEY `c_id_UNIQUE` (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `college`
--

LOCK TABLES `college` WRITE;
/*!40000 ALTER TABLE `college` DISABLE KEYS */;
/*!40000 ALTER TABLE `college` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sharing`
--

DROP TABLE IF EXISTS `sharing`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sharing` (
  `s_id` int(11) NOT NULL,
  `s_title` varchar(255) NOT NULL,
  `s_date` date NOT NULL,
  `s_content` text NOT NULL,
  `s_up` int(11) DEFAULT '0',
  `user_u_id` int(11) NOT NULL,
  PRIMARY KEY (`s_id`),
  KEY `fk_sharing_user1_idx` (`user_u_id`),
  CONSTRAINT `fk_sharing_user1` FOREIGN KEY (`user_u_id`) REFERENCES `user` (`u_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sharing`
--

LOCK TABLES `sharing` WRITE;
/*!40000 ALTER TABLE `sharing` DISABLE KEYS */;
/*!40000 ALTER TABLE `sharing` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `u_id` int(11) NOT NULL,
  `u_username` varchar(255) NOT NULL,
  `u_name` varchar(255) DEFAULT NULL,
  `u_password` varchar(45) NOT NULL,
  `college_c_id` int(11) NOT NULL,
  `u_GPA` decimal(10,0) DEFAULT NULL,
  `u_tofel` decimal(10,0) DEFAULT NULL,
  `u_gre` decimal(10,0) DEFAULT NULL,
  `u_wechat` varchar(45) DEFAULT NULL,
  `u_email` varchar(45) DEFAULT NULL,
  `u_info` text,
  `u_major` varchar(45) DEFAULT NULL,
  `u_state` int(11) NOT NULL DEFAULT '0',
  `u_avatar` blob NOT NULL,
  PRIMARY KEY (`u_id`),
  UNIQUE KEY `iduser_UNIQUE` (`u_id`),
  KEY `fk_user_college_idx` (`college_c_id`),
  CONSTRAINT `fk_user_college` FOREIGN KEY (`college_c_id`) REFERENCES `college` (`c_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_stars_college`
--

DROP TABLE IF EXISTS `user_stars_college`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_stars_college` (
  `user_u_id` int(11) NOT NULL,
  `college_c_id` int(11) NOT NULL,
  PRIMARY KEY (`user_u_id`,`college_c_id`),
  KEY `fk_user_has_college_college1_idx` (`college_c_id`),
  KEY `fk_user_has_college_user1_idx` (`user_u_id`),
  CONSTRAINT `fk_user_has_college_college1` FOREIGN KEY (`college_c_id`) REFERENCES `college` (`c_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_college_user1` FOREIGN KEY (`user_u_id`) REFERENCES `user` (`u_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_stars_college`
--

LOCK TABLES `user_stars_college` WRITE;
/*!40000 ALTER TABLE `user_stars_college` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_stars_college` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'baisc'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-09-01 14:39:16
