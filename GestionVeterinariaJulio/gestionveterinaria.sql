-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 23-04-2021 a las 14:31:00
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
(29);

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
(4, b'0', 'Gómez González', 'Ana Belén', '$2a$10$WyDi9x2K3uQH7610DmjZZOJex/ExxM/nv./pOpeVmBYTGsrPeHt.O', 'ROLE_CLIENTE', '678568545', 'anabelen54'),
(5, b'0', 'Traverso Carrero', 'Fabio', '$2a$10$ZsfnV9zmoJ6E61b4xEn4B.80yXEt.FRg7QgQq2/z.Yxni1KUx0f2i', 'ROLE_VETERINARIO', '672113415', 'fabitraver89'),
(6, b'0', 'López Chozas', 'Julián', '$2a$10$PlOTY3SplfVG6p5vAPbj9eF0cNYGlti5nwqLB/eumE.EPBKqBxB1e', 'ROLE_VETERINARIO', '678224590', 'julianlope'),
(7, b'0', 'Gomero López', 'Francisco', '$2a$10$SqvMjySy7mag9IGWdnrJVezBNBm.Rti8yUDiWzcMU2s/fu/j93bv.', 'ROLE_CLIENTE', '672781134', 'frango1995'),
(8, b'0', 'Gómez Gardón', 'Laura', '$2a$10$150UKo4RllwtEaFEyFUEReXZKg8OTyiSkLw.QigKjnuPPrlu/.Qi2', 'ROLE_VETERINARIO', '678983216', 'lau90'),
(9, b'0', 'Pérez Gardón', 'Julia', '$2a$10$0u0txk8XY6lpaQHTxwlsZOOetW1jNK/ySyqZYEjo4xemzFsVTHATm', 'ROLE_CLIENTE', '956892190', 'julialv1995'),
(10, b'0', 'Reyes Gil', 'Álvaro', '$2a$10$DnzF9.Ft/WMkf0/eQEIaGedEFrGS2UrCWRv8X/oFD4Q2dtp0lBGPS', 'ROLE_CLIENTE', '678443391', 'alvaroreyes54'),
(11, b'0', 'Castañeda López', 'Alfredo', '$2a$10$RPf8TKvOTMjz.V.YbZ17Mus2XZyzOn8cbBsYhKuae55W7p6bNvcgS', 'ROLE_VETERINARIO', '678998813', 'alfredocalo13');

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
