-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         8.0.15 - MySQL Community Server - GPL
-- SO del servidor:              Win64
-- HeidiSQL Versión:             11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para loans_db
CREATE DATABASE IF NOT EXISTS `loans_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
USE `loans_db`;

-- Volcando estructura para tabla loans_db.loan
CREATE TABLE IF NOT EXISTS `loan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `total` decimal(19,2) NOT NULL,
  `user` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrtt9h8020twky6par0dgxximc` (`user`),
  CONSTRAINT `FKrtt9h8020twky6par0dgxximc` FOREIGN KEY (`user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla loans_db.loan: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `loan` DISABLE KEYS */;
INSERT INTO `loan` (`id`, `total`, `user`) VALUES
	(1, 3500.00, 1),
	(2, 4500.00, 1),
	(3, 5500.00, 1),
	(4, 6500.00, 2),
	(5, 7500.00, 2),
	(6, 100.00, 5),
	(7, 500.00, 5),
	(8, 700.00, 5),
	(9, 1100.00, 5),
	(10, 2000.00, 8),
	(11, 3000.00, 8),
	(12, 6000.00, 8),
	(13, 450.00, 10);
/*!40000 ALTER TABLE `loan` ENABLE KEYS */;

-- Volcando estructura para tabla loans_db.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla loans_db.user: ~1 rows (aproximadamente)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `email`, `first_name`, `last_name`) VALUES
	(1, 'arodriguez@test.com', 'Andres', 'Rodriguez'),
	(2, 'speres@test.com', 'Susana', 'Perez'),
	(3, 'cfernandez@test.com', 'Claudio', 'Fernandez'),
	(4, 'mvazquez@test.com', 'Maria', 'Vazquez'),
	(5, 'ctorres@test.com', 'Camila', 'Torres'),
	(6, 'pfuentes@test.com', 'Pedro', 'Fuentes'),
	(7, 'clopez@test.com', 'Camilo', 'Lopez'),
	(8, 'mdiaz@test.com', 'Miriam', 'Diaz'),
	(9, 'smontes@test.com', 'Sofia', 'Montes'),
	(10, 'vgaleano@test.com', 'Vanessa', 'Galeano');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
