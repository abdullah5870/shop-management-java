-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Sep 01, 2016 at 08:19 PM
-- Server version: 10.1.13-MariaDB
-- PHP Version: 7.0.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `std`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `username` varchar(20) NOT NULL,
  `password` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`username`, `password`) VALUES
('user1', '123456'),
('user2', '654321');

-- --------------------------------------------------------

--
-- Table structure for table `items`
--

CREATE TABLE `items` (
  `name` varchar(30) NOT NULL,
  `uppername` varchar(40) NOT NULL,
  `price` int(11) NOT NULL,
  `quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `items`
--

INSERT INTO `items` (`name`, `uppername`, `price`, `quantity`) VALUES
('ASUS selfie', 'ASUS SELFIE', 17000, 50),
('ASUS thinkpad', 'ASUS THINKPAD', 35000, 50),
('ASUS Zenfone 5', 'ASUS ZENFONE 5', 13500, 45),
('HTC desire 826', 'HTC DESIRE 826', 17500, 50),
('HUAWEI GR5 mini', 'HUAWEI GR5 MINI', 19500, 50),
('iMAC', 'IMAC', 120000, 40),
('iPad Pro', 'IPAD PRO', 109700, 45),
('iPhone 6', 'IPHONE 6', 80000, 50),
('iPhone 7', 'IPHONE 7', 90000, 50),
('iPOD Nano', 'IPOD NANO', 15000, 50),
('macbook air', 'MACBOOK AIR', 150000, 50),
('qwerty', 'QWERTY', 100, 100),
('Sony Xperia Z1', 'SONY XPERIA Z1', 20000, 50),
('Tp-Link', 'TP-LINK', 1200, 45);

-- --------------------------------------------------------

--
-- Table structure for table `salesman`
--

CREATE TABLE `salesman` (
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `salesman`
--

INSERT INTO `salesman` (`username`, `password`) VALUES
('user10', '0'),
('user3', '0123'),
('user4', '3210'),
('user5', '1234');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`username`);

--
-- Indexes for table `items`
--
ALTER TABLE `items`
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `salesman`
--
ALTER TABLE `salesman`
  ADD UNIQUE KEY `username` (`username`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
