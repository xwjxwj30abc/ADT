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
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) NOT NULL,
  `idstr` varchar(32) NOT NULL,
  `uclass` int(11) NOT NULL,
  `screen_name` varchar(64) NOT NULL,
  `name` varchar(64) NOT NULL,
  `province` int(11) NOT NULL,
  `city` int(11) NOT NULL,
  `location` varchar(128) NOT NULL,
  `description` varchar(128) NOT NULL,
  `url` varchar(128) NOT NULL,
  `profile_image_url` varchar(128) NOT NULL,
  `profile_url` varchar(128) NOT NULL,
  `domain` varchar(128) NOT NULL,
  `weihao` varchar(128) NOT NULL,
  `gender` varchar(128) NOT NULL,
  `followers_count` int(11) NOT NULL,
  `friends_count` int(11) NOT NULL,
  `pagefriends_count` int(11) NOT NULL,
  `statuses_count` int(11) NOT NULL,
  `favourites_count` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `following` tinyint(1) NOT NULL,
  `allow_all_act_msg` tinyint(1) NOT NULL,
  `geo_enabled` tinyint(1) NOT NULL,
  `verified` tinyint(1) NOT NULL,
  `verified_type` int(11) NOT NULL,
  `remark` varchar(32) NOT NULL,
  `ptype` int(11) NOT NULL,
  `allow_all_comment` tinyint(1) NOT NULL,
  `avatar_large` varchar(32) NOT NULL,
  `avatar_hd` varchar(32) NOT NULL,
  `verified_reason` varchar(64) NOT NULL,
  `verified_trade` int(11) NOT NULL,
  `verified_reason_url` varchar(128) NOT NULL,
  `verified_source` varchar(128) NOT NULL,
  `verified_source_url` varchar(128) NOT NULL,
  `verified_state` int(11) NOT NULL,
  `verified_level` int(11) NOT NULL,
  `verified_reason_modified` varchar(64) NOT NULL,
  `verified_contact_name` varchar(64) NOT NULL,
  `verified_contact_email` varchar(64) NOT NULL,
  `verified_contact_mobile` varchar(64) NOT NULL,
  `follow_me` tinyint(1) NOT NULL,
  `online_status` int(11) NOT NULL,
  `bi_followers_count` int(11) NOT NULL,
  `lang` varchar(32) NOT NULL,
  `star` int(11) NOT NULL,
  `mbtype` int(11) NOT NULL,
  `mbrank` int(11) NOT NULL,
  `block_word` int(11) NOT NULL,
  `block_app` int(11) NOT NULL,
  `credit_score` int(11) NOT NULL,
  `user_ability` int(11) NOT NULL,
  `urank` int(11) NOT NULL,
  `lasttime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uid` (`uid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='新浪用户信息表' AUTO_INCREMENT=1 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
