-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 18-04-2021 a las 15:03:07
-- Versión del servidor: 10.4.14-MariaDB
-- Versión de PHP: 7.4.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `gestionveterinaria`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `citas`
--

CREATE TABLE `citas` (
  `id` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `informe` varchar(100) NOT NULL,
  `motivo` varchar(100) NOT NULL,
  `realizada` bit(1) NOT NULL,
  `idMascota` int(11) DEFAULT NULL,
  `idUsuario` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(9);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mascotas`
--

CREATE TABLE `mascotas` (
  `id` int(11) NOT NULL,
  `fechaNacimiento` date NOT NULL,
  `foto` varchar(100) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `raza` varchar(30) NOT NULL,
  `tipo` varchar(30) NOT NULL,
  `idUsuario` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL,
  `activado` bit(1) NOT NULL,
  `apellidos` varchar(50) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `password` varchar(100) NOT NULL,
  `rol` varchar(20) NOT NULL,
  `telefono` varchar(10) NOT NULL,
  `username` varchar(30) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `activado`, `apellidos`, `nombre`, `password`, `rol`, `telefono`, `username`) VALUES
(1, b'1', 'admin', 'Admin', '$2a$10$R3gTOJF9t0CynhJ3nb1XKO5nMFCCxPEa/bkWHyQP00DL1DHVKUtHi', 'ROLE_ADMIN', '123456789', 'admin'),
(2, b'1', 'Sánchez Chozas', 'Nuria', '$2a$10$s4QCRZa0.ZBULUctIepJZO9B6ZLl/x289.j22sRT1NgW1/kuIueGu', 'ROLE_CLIENTE', '675397345', 'nuria56'),
(3, b'0', 'López Pérez', 'Juan', '$2a$10$LBJnkqtnfxW/qvsQnPQQ6uQC7K6GhvELQaJhsSpFLzq1/Qa4SbSwu', 'ROLE_CLIENTE', '678487512', 'juanlope95'),
(4, b'1', 'Gómez González', 'Ana Belén', '$2a$10$mcbR/iCe8lNS/IdO6sG1VOvzaH41yxq9oZJg//8rOK4twP7yBpkYO', 'ROLE_CLIENTE', '678568545', 'anabelen54'),
(5, b'0', 'Ibáñez Fernández', 'Sandra', '$2a$10$5HEGBha2E.Cv75VWsBH1VOJ9.zonteVRn6LfCwLrmFxtDPdH5yHdG', 'ROLE_CLIENTE', '649328511', 'sanbullo67'),
(6, b'0', 'Álvarez Castrillo', 'Julio', '$2a$10$oI/vvkWTvVK88bGOvezoqeVFXdFJmpVgA/eyksqpwmWLMez/wxVsC', 'ROLE_CLIENTE', '978437214', 'julialv1995'),
(7, b'0', 'Castrillo López', 'Ignacio', '$2a$10$Jaln8Vivf3SaeRRhT7VunO7ZAUUSoesyFR0K6NghkM4YM.nyb8w7W', 'ROLE_CLIENTE', '876457315', 'igna94');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `citas`
--
ALTER TABLE `citas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKwqiyeoeu8hxtdt5uevekpnr` (`idMascota`),
  ADD KEY `FK8aqm41ttp6x0xurwukeyqtqi7` (`idUsuario`);

--
-- Indices de la tabla `mascotas`
--
ALTER TABLE `mascotas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK1q3qw419m37m8nvvv5ti3wbun` (`idUsuario`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_7a6nioycwi246dw0dn2etj3ye` (`telefono`),
  ADD UNIQUE KEY `UK_m2dvbwfge291euvmk6vkkocao` (`username`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
