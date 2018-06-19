CREATE DATABASE IF NOT EXISTS `hotel_russia` CHARACTER SET utf8 COLLATE utf8_general_ci;

USE hotel_russia;

CREATE TABLE IF NOT EXISTS  `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_non_expired` bit(1) DEFAULT NULL,
  `account_non_locked` bit(1) DEFAULT NULL,
  `credentials_non_expired` bit(1) DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS  `user_authorities` (
  `user_id` int(11) NOT NULL,
  `authorities` int(11) DEFAULT NULL,
  KEY `FKmj13d0mnuj4cd8b6htotbf9mm` (`user_id`),
  CONSTRAINT `FKmj13d0mnuj4cd8b6htotbf9mm` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS  `room_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS  `room` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cleaning_required` bit(1) DEFAULT NULL,
  `no` int(11) DEFAULT NULL,
  `type_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKf5vbgyps3ubaknn710nk2m5o5` (`type_id`),
  CONSTRAINT `FKf5vbgyps3ubaknn710nk2m5o5` FOREIGN KEY (`type_id`) REFERENCES `room_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS  `booking` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `arrival_date` datetime DEFAULT NULL,
  `canceled` bit(1) DEFAULT NULL,
  `date_buking` datetime DEFAULT NULL,
  `date_of_departure` datetime DEFAULT NULL,
  `last_day_cleaning` datetime DEFAULT NULL,
  `room_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKq83pan5xy2a6rn0qsl9bckqai` (`room_id`),
  KEY `FKkgseyy7t56x7lkjgu3wah5s3t` (`user_id`),
  CONSTRAINT `FKkgseyy7t56x7lkjgu3wah5s3t` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKq83pan5xy2a6rn0qsl9bckqai` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;
