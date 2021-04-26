-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 26-04-2021 a las 13:23:50
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
  `idVeterinario` int(11) DEFAULT NULL
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
(13);

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
  `idCliente` int(11) DEFAULT NULL
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
(1, b'1', 'admin', 'Admin', '$2a$10$yIiYpfdwG0X4Dcl9KOoE9ef/EA45GFMNPBWja4d5rj5g4xXhSAE5C', 'ROLE_ADMIN', '123456789', 'admin'),
(2, b'0', 'Sánchez Chozas', 'Nuria', '$2a$10$TnlzohZzvXuUhfUfL/xgF.lhVmk6KVrMFwx2f0n7dFLYkqaJ8HvIG', 'ROLE_CLIENTE', '675397345', 'nuria56'),
(3, b'0', 'López Pérez', 'Juan', '$2a$10$sg1DGchx98DDCxrhCVY8MeFDvbJpKuDu2BgJxCNS1yS4Rcg3fugeC', 'ROLE_CLIENTE', '678487512', 'juanlope95'),
(4, b'0', 'Gómez González', 'Ana Belén', '$2a$10$n87/Mp9.bM8KPUQqymkRgOCgUKinjU68tygiuAW186U6yPIlTPhH.', 'ROLE_CLIENTE', '678568545', 'anabelen54'),
(5, b'1', 'Traverso Carrero', 'Fabio', '$2a$10$qboszceSfZglcObRjC0w9.5lkhfal2tO9KkBbRreY9zko2iq/xyUa', 'ROLE_VETERINARIO', '672113415', 'fabitraver89'),
(6, b'1', 'López Chozas', 'Julián', '$2a$10$zsmmav0i14QcJ8sRrA2VOOhbCA084hH4iEFD8ZqvhT/rYscI16RUG', 'ROLE_VETERINARIO', '678224590', 'julianlope'),
(7, b'0', 'Gomero López', 'Francisco', '$2a$10$hE78bWT8vErRWqSeeqzzyOg6EcoHL5i3aqj.30NVGlGhMgz/cr1ze', 'ROLE_CLIENTE', '672781134', 'frango1992'),
(8, b'1', 'Gómez Gardón', 'Laura', '$2a$10$/rNd.Ul17ral3C/EowdBoOfK/QHYwAGwCIzp1dkk5PH7c.t0NPsAS', 'ROLE_VETERINARIO', '678983216', 'lau90'),
(9, b'0', 'Pérez Gardón', 'Julia', '$2a$10$WbtdJqJxWljs1TNjTC7JiOSBzOEr6oFAiA9Cd7/ZZJVg1JempoTuG', 'ROLE_CLIENTE', '956892190', 'julialv1995'),
(10, b'0', 'Reyes Gil', 'Álvaro', '$2a$10$uLZcdjnmAjhn4VGHdN4eye8sWv2k.4D/xSz0aFb8L277c/RVrstWe', 'ROLE_CLIENTE', '678443391', 'alvaroreyes54'),
(11, b'1', 'Castañeda López', 'Alfredo', '$2a$10$IE5ERtQJE5eEQuY56SamRODNUGj7DCj.4Ze6EpDVMgDBDTmAH0t1G', 'ROLE_VETERINARIO', '678998813', 'alfredocalo13'),
(12, b'1', 'López Reyes', 'Ignacio', '$2a$10$eAQltccoUfaITkGnett8l.SkphCoLKUniVsCjTS6kAn3JO.eSQpGq', 'ROLE_VETERINARIO', '678119021', 'ignaciolore90');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `citas`
--
ALTER TABLE `citas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKwqiyeoeu8hxtdt5uevekpnr` (`idMascota`),
  ADD KEY `FKlt58i83go6rgbqs2e37u0oshp` (`idVeterinario`);

--
-- Indices de la tabla `mascotas`
--
ALTER TABLE `mascotas`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_klqk2ag4wmmplflpp5dcl9s8h` (`foto`),
  ADD KEY `FK7ylvjmk6us6vdv0lktvxjtlie` (`idCliente`);

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
