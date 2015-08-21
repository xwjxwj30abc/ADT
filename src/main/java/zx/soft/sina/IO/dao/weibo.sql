-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Aug 21, 2015 at 02:27 PM
-- Server version: 5.5.44-0ubuntu0.14.04.1
-- PHP Version: 5.5.9-1ubuntu4.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `test`
--

-- --------------------------------------------------------

--
-- Table structure for table `weibo`
--

CREATE TABLE IF NOT EXISTS `weibo` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `uid` varchar(32) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `mid` varchar(64) NOT NULL,
  `idstr` varchar(64) NOT NULL,
  `text` text NOT NULL,
  `source_allowclick` int(11) NOT NULL,
  `source_type` int(11) NOT NULL,
  `source` varchar(64) NOT NULL,
  `favorited` tinyint(1) NOT NULL,
  `truncated` tinyint(1) NOT NULL,
  `in_reply_to_status_id` varchar(128) NOT NULL,
  `in_reply_to_user_id` varchar(128) NOT NULL,
  `in_reply_to_screen_name` varchar(128) NOT NULL,
  `geo` varchar(64) NOT NULL,
  `reposts_count` int(11) NOT NULL,
  `comments_count` int(11) NOT NULL,
  `attitudes_count` int(11) NOT NULL,
  `mlevel` int(11) NOT NULL,
  `owid` bigint(20) NOT NULL,
  `ousername` bigint(20) NOT NULL,
  `lasttime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `pic_urls` varchar(64) DEFAULT NULL,
  `user` varchar(512) DEFAULT NULL,
  `darwin_tags` varchar(128) DEFAULT NULL,
  `visible_type` int(11) NOT NULL,
  `visible_list_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uid` (`uid`),
  KEY `uid_2` (`uid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='新浪微博数据表' AUTO_INCREMENT=1 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
