-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: blbgize3nkw4xj3pclkb-mysql.services.clever-cloud.com    Database: blbgize3nkw4xj3pclkb
-- ------------------------------------------------------
-- Server version	8.0.22-13

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
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ 'a05a675a-1414-11e9-9c82-cecd01b08c7e:1-491550428,
a38a16d0-767a-11eb-abe2-cecd029e558e:1-570022870';

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `id_cliente` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) DEFAULT NULL,
  `apellido` varchar(50) DEFAULT NULL,
  `telefono` varchar(15) DEFAULT NULL,
  `correo` varchar(100) DEFAULT NULL,
  `direccion` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`id_cliente`)
) ENGINE=InnoDB AUTO_INCREMENT=1754203057 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (654,'Pablo','Eras','1234','@Correo','Direccion X'),(55555,'Jorge','Ulloa','456789','@correosss','Carigan'),(789987,'Camila','Loyola','088899999','@Camila','Carigan'),(11224455,'Juan','Perez','456','@juan','Cardinan'),(987654321,'Ana','Villa','123','@ana','Vallle'),(1111122222,'Ana','Villa','123','@ana','Vallle'),(1122334455,'Alberto','Matias','123456789','@ejemplo','Margaritas'),(1233556688,'Nando','Velez','0985654720','nando@gmail.com','Monjas'),(1234567890,'nomb','apell','123456','@ejemplo2','Direc'),(1414141414,'clienteprueba','prueba','098563562','prueba2@gmail.com','suiza'),(1725451202,'Alexander','Paredes','0985323054','alexp@gmail.com','monjas'),(1727575767,'Jose','Cañizares','098654235','jose@gmail.com','madrigal'),(1728292987,'Maria','Velez','0952352658','mariav@gmail.com','guamani'),(1754020232,'Luis','Muñoz','0986365632','luism@gmail.com','solanda'),(1754203056,'Angelica','Lopez','0956325685','angelo@gmail.com','condado');
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalle_servicio`
--

DROP TABLE IF EXISTS `detalle_servicio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalle_servicio` (
  `id_detalle` int NOT NULL AUTO_INCREMENT,
  `id_factura` int DEFAULT NULL,
  `id_servicio` int DEFAULT NULL,
  `cantidad` int DEFAULT NULL,
  `precio_unitario` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id_detalle`),
  KEY `id_factura` (`id_factura`),
  KEY `id_servicio` (`id_servicio`),
  CONSTRAINT `detalle_servicio_ibfk_1` FOREIGN KEY (`id_factura`) REFERENCES `factura` (`id_factura`),
  CONSTRAINT `detalle_servicio_ibfk_2` FOREIGN KEY (`id_servicio`) REFERENCES `producto` (`id_producto`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_servicio`
--

LOCK TABLES `detalle_servicio` WRITE;
/*!40000 ALTER TABLE `detalle_servicio` DISABLE KEYS */;
INSERT INTO `detalle_servicio` VALUES (1,1,1,1,12.50),(2,2,2,2,35.90),(3,3,3,1,9.75),(4,4,4,1,18.40),(5,5,5,3,7.99),(6,9,3,0,9.75),(7,10,3,1,10.00),(8,11,3,1,10.00),(9,12,3,1,10.00),(10,13,8,2,10.00),(11,14,2,2,35.90),(12,15,3,2,10.00);
/*!40000 ALTER TABLE `detalle_servicio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `factura`
--

DROP TABLE IF EXISTS `factura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `factura` (
  `id_factura` int NOT NULL AUTO_INCREMENT,
  `fecha` date DEFAULT NULL,
  `tipo_venta` varchar(50) DEFAULT NULL,
  `total` decimal(10,2) DEFAULT NULL,
  `id_cliente` int DEFAULT NULL,
  PRIMARY KEY (`id_factura`),
  KEY `id_cliente` (`id_cliente`),
  CONSTRAINT `factura_ibfk_1` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id_cliente`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `factura`
--

LOCK TABLES `factura` WRITE;
/*!40000 ALTER TABLE `factura` DISABLE KEYS */;
INSERT INTO `factura` VALUES (1,'2024-07-10','efectivo',45.90,1727575767),(2,'2024-07-12','tarjeta',79.30,1728292987),(3,'2024-07-14','efectivo',92.15,1725451202),(4,'2024-07-15','tarjeta',60.00,1754203056),(5,'2024-07-18','efectivo',37.75,1754020232),(6,'2025-07-25','vacuna antirrabica x1 ($12.5)',12.50,1122334455),(7,'2025-05-05','Venta',9.75,1122334455),(8,'2025-05-25','Venta',18.40,1122334455),(9,'2025-05-05','Venta',0.00,1122334455),(10,'2025-05-05','Venta',10.00,1122334455),(11,'2025-05-05','Venta',10.00,1122334455),(12,'2025-05-25','Venta',10.00,1122334455),(13,'2025-07-27','Venta',20.00,1414141414),(14,'2025-07-27','Venta',71.80,789987),(15,'2025-07-27','Venta',20.00,654);
/*!40000 ALTER TABLE `factura` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mascota`
--

DROP TABLE IF EXISTS `mascota`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mascota` (
  `id_mascota` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) DEFAULT NULL,
  `especie` varchar(30) DEFAULT NULL,
  `raza` varchar(50) DEFAULT NULL,
  `edad` int DEFAULT NULL,
  `id_cliente` int DEFAULT NULL,
  PRIMARY KEY (`id_mascota`),
  KEY `id_cliente` (`id_cliente`),
  CONSTRAINT `mascota_ibfk_1` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id_cliente`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mascota`
--

LOCK TABLES `mascota` WRITE;
/*!40000 ALTER TABLE `mascota` DISABLE KEYS */;
INSERT INTO `mascota` VALUES (1,'Rocky','Perro','Labrador',4,1727575767),(2,'Nina','Gato','Siames',3,1728292987),(3,'Toby','Perro','Golden Retriever',5,1725451202),(4,'Luna','Conejo','Mini Lop',2,1754203056),(5,'Max','Gato','Persa',1,1754020232),(6,'Luna','Gato','Gata',2,1122334455),(7,'Maximus','Gato','Siames',3,1234567890),(8,'Zamuka','Felino','siames',5,1233556688),(10,'Pelusa','Gato','Gato',4,55555),(11,'Pelos','Gato','Gatito',7,11224455),(12,'mascotaprueba','canino','mestizo',4,1414141414),(13,'Maximus','Perrito','Pequeño',4,789987),(14,'Doritos','Gato','Siames',2,654);
/*!40000 ALTER TABLE `mascota` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `producto`
--

DROP TABLE IF EXISTS `producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `producto` (
  `id_producto` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) DEFAULT NULL,
  `categoria` varchar(50) DEFAULT NULL,
  `precio` decimal(10,2) DEFAULT NULL,
  `cantidad` int DEFAULT NULL,
  PRIMARY KEY (`id_producto`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producto`
--

LOCK TABLES `producto` WRITE;
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
INSERT INTO `producto` VALUES (1,'Pástillas','Desinflamante',5.00,2),(2,'Alimento Premium Canino','Alimento',35.90,50),(3,'Shampoo','Limpieza',10.00,5),(4,'Collar antiparasitario','Accesorio',18.40,15),(5,'Juguete interactivo para gatos','Entretenimiento',7.99,25),(6,'Shampoo Levacan','Higiene',7.50,12),(8,'Talco para pulgas','Higiene',10.00,10),(10,'gorro navideño gato','vestimenta',3.00,10),(11,'Crema','Crema',56.00,3);
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id_usuario` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) DEFAULT NULL,
  `contraseña` varchar(100) DEFAULT NULL,
  `rol` enum('Administrador','Cajero') DEFAULT NULL,
  PRIMARY KEY (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (4,'Miguel','Admin1234','Administrador'),(5,'Jorge','Caj1234','Cajero'),(6,'prueba','prueba2','Administrador'),(7,'prueba3','prueba3','Cajero'),(8,'usuarioprueba4','0000','Cajero'),(9,'prueba5','5555','Administrador'),(10,'','','Administrador'),(11,'Erik','1234','Cajero'),(12,'prueba7','4444','Administrador'),(13,'Erick','1234','Administrador'),(14,'Esthela','1234','Cajero'),(15,'pruebafinal','141414','Administrador'),(16,'Josue','00000','Cajero'),(17,'Matias','4444','Cajero');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-28 20:43:27
