CREATE TABLE IF NOT EXISTS `athlete2` (
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