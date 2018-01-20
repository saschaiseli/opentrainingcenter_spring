-- --------------------------------------------------------
-- Host:                         192.168.1.55
-- Server Version:               10.0.32-MariaDB - Source distribution
-- Server Betriebssystem:        Linux
-- HeidiSQL Version:             9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Exportiere Struktur von Tabelle otc_db_prod.athlete
CREATE TABLE IF NOT EXISTS `athlete` (
  `id` bigint(20) NOT NULL,
  `birthday` date DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_login` datetime DEFAULT NULL,
  `last_name` varchar(255) NOT NULL,
  `maxheartrate` int(11) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_jd5vk1rxpqgb5jy45xhfxec77` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Daten Export vom Benutzer nicht ausgewählt
-- Exportiere Struktur von Tabelle otc_db_prod.health
CREATE TABLE IF NOT EXISTS `health` (
  `id` bigint(20) NOT NULL,
  `cardio` int(11) DEFAULT NULL,
  `dateofmeasure` date DEFAULT NULL,
  `weight` int(11) DEFAULT NULL,
  `id_fk_athlete` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKp3cdytuartqemfxld4bwn56rt` (`id_fk_athlete`),
  CONSTRAINT `FKp3cdytuartqemfxld4bwn56rt` FOREIGN KEY (`id_fk_athlete`) REFERENCES `athlete` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Daten Export vom Benutzer nicht ausgewählt
-- Exportiere Struktur von Tabelle otc_db_prod.lap_info
CREATE TABLE IF NOT EXISTS `lap_info` (
  `id` bigint(20) NOT NULL,
  `lap_end` int(11) DEFAULT NULL,
  `geschwindigkeit` varchar(255) DEFAULT NULL,
  `heart_beat` int(11) NOT NULL,
  `lap` int(11) NOT NULL,
  `pace` varchar(255) DEFAULT NULL,
  `lap_start` int(11) DEFAULT NULL,
  `time` bigint(20) NOT NULL,
  `id_fk_training` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKorovn81dkakwpyfh9jvgnnpwk` (`id_fk_training`),
  CONSTRAINT `FKorovn81dkakwpyfh9jvgnnpwk` FOREIGN KEY (`id_fk_training`) REFERENCES `training` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Daten Export vom Benutzer nicht ausgewählt
-- Exportiere Struktur von Tabelle otc_db_prod.route
CREATE TABLE IF NOT EXISTS `route` (
  `id` bigint(20) NOT NULL,
  `beschreibung` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `id_fk_athlete` bigint(20) NOT NULL,
  `id_fk_training` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKd8lym2mmmhng4c50p223muq5f` (`id_fk_athlete`),
  KEY `FKg2rto0bgepkra7oc8u03hdqkw` (`id_fk_training`),
  CONSTRAINT `FKd8lym2mmmhng4c50p223muq5f` FOREIGN KEY (`id_fk_athlete`) REFERENCES `athlete` (`id`),
  CONSTRAINT `FKg2rto0bgepkra7oc8u03hdqkw` FOREIGN KEY (`id_fk_training`) REFERENCES `training` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Daten Export vom Benutzer nicht ausgewählt
-- Exportiere Struktur von Tabelle otc_db_prod.rule
CREATE TABLE IF NOT EXISTS `rule` (
  `id` bigint(20) NOT NULL,
  `type` int(11) DEFAULT NULL,
  `unit` int(11) DEFAULT NULL,
  `value` bigint(20) NOT NULL,
  `id_fk_athlete` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKswp1nr9ebhu4uwldyjfvpoq8f` (`id_fk_athlete`),
  CONSTRAINT `FKswp1nr9ebhu4uwldyjfvpoq8f` FOREIGN KEY (`id_fk_athlete`) REFERENCES `athlete` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Daten Export vom Benutzer nicht ausgewählt
-- Exportiere Struktur von Tabelle otc_db_prod.shoes
CREATE TABLE IF NOT EXISTS `shoes` (
  `id` bigint(20) NOT NULL,
  `imageicon` varchar(255) DEFAULT NULL,
  `kaufdatum` datetime DEFAULT NULL,
  `preis` int(11) NOT NULL,
  `schuhname` varchar(255) NOT NULL,
  `id_fk_athlete` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKshksk33mkaely6ogj9hghmw6m` (`id_fk_athlete`),
  CONSTRAINT `FKshksk33mkaely6ogj9hghmw6m` FOREIGN KEY (`id_fk_athlete`) REFERENCES `athlete` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Daten Export vom Benutzer nicht ausgewählt
-- Exportiere Struktur von Tabelle otc_db_prod.tracktrainingproperty
CREATE TABLE IF NOT EXISTS `tracktrainingproperty` (
  `id` bigint(20) NOT NULL,
  `altitude` int(11) NOT NULL,
  `distance` double NOT NULL,
  `heartbeat` int(11) NOT NULL,
  `lap` int(11) NOT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `zeit` bigint(20) NOT NULL,
  `id_fk_training` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKlvt2crs5xb8gr4f87rgdi8cps` (`id_fk_training`),
  CONSTRAINT `FKlvt2crs5xb8gr4f87rgdi8cps` FOREIGN KEY (`id_fk_training`) REFERENCES `training` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Daten Export vom Benutzer nicht ausgewählt
-- Exportiere Struktur von Tabelle otc_db_prod.training
CREATE TABLE IF NOT EXISTS `training` (
  `id` bigint(20) NOT NULL,
  `average_heart_beat` int(11) NOT NULL,
  `date_of_import` datetime NOT NULL,
  `dauer` bigint(20) NOT NULL,
  `down_meter` int(11) DEFAULT NULL,
  `geo_quality` int(11) DEFAULT NULL,
  `laenge_in_meter` bigint(20) NOT NULL,
  `max_heart_beat` int(11) NOT NULL,
  `max_speed` double NOT NULL,
  `note` varchar(255) DEFAULT NULL,
  `sport` int(11) DEFAULT NULL,
  `training_effect` int(11) DEFAULT NULL,
  `training_type` int(11) DEFAULT NULL,
  `up_meter` int(11) DEFAULT NULL,
  `id_fk_athlete` bigint(20) NOT NULL,
  `id_fk_route` bigint(20) DEFAULT NULL,
  `id_fk_shoes` bigint(20) DEFAULT NULL,
  `id_fk_weather` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK468etkjirbwabf9q9hpyfam3l` (`id_fk_athlete`),
  KEY `FKbynllvxawabxgkvdn3096nqvp` (`id_fk_route`),
  KEY `FKkvgfj6ng101nhc4tphpr57p3n` (`id_fk_shoes`),
  KEY `FKo1oind61xe14jg1q08jl2iylj` (`id_fk_weather`),
  CONSTRAINT `FK468etkjirbwabf9q9hpyfam3l` FOREIGN KEY (`id_fk_athlete`) REFERENCES `athlete` (`id`),
  CONSTRAINT `FKbynllvxawabxgkvdn3096nqvp` FOREIGN KEY (`id_fk_route`) REFERENCES `route` (`id`),
  CONSTRAINT `FKkvgfj6ng101nhc4tphpr57p3n` FOREIGN KEY (`id_fk_shoes`) REFERENCES `shoes` (`id`),
  CONSTRAINT `FKo1oind61xe14jg1q08jl2iylj` FOREIGN KEY (`id_fk_weather`) REFERENCES `weather` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Daten Export vom Benutzer nicht ausgewählt
-- Exportiere Struktur von Tabelle otc_db_prod.weather
CREATE TABLE IF NOT EXISTS `weather` (
  `id` bigint(20) NOT NULL,
  `imageicon` varchar(255) DEFAULT NULL,
  `wetter` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Daten Export vom Benutzer nicht ausgewählt
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
