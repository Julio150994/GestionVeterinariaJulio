-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 13-05-2021 a las 19:15:47
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
  `idMascota` int(11) NOT NULL,
  `idVeterinario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `citas`
--

INSERT INTO `citas` (`id`, `fecha`, `informe`, `motivo`, `realizada`, `idMascota`, `idVeterinario`) VALUES
(1, '2021-05-09', 'Tratamiento para las uñas dañadas', 'Daño leve en las uñas', b'0', 2, 6),
(2, '2021-05-09', 'Tratamiento mediante vacunación', 'Urgencia', b'0', 1, 6),
(3, '2021-05-13', 'Elaborado para su curación', 'Lesión en la pata', b'1', 3, 6),
(4, '2021-06-15', 'Defaults', 'Urgencia', b'0', 3, 8),
(5, '2021-06-23', 'Requerimiento de tratamiento para la irritación y posible dolor de ojos', 'Irritación de los ojos', b'0', 4, 11),
(6, '2021-05-12', 'Tratamiento por sobrepeso con alimentos sanos', 'Cuidados en la alimentación', b'0', 4, 6),
(7, '2021-05-13', 'Elaborado para repuración de lesión', 'Lesión en la pata derecha', b'1', 3, 6),
(8, '2021-05-13', 'Informe diario sobre reacciones de Epi', 'Vacuna de la rabia', b'0', 1, 6),
(9, '2021-05-13', 'Tratamiento diario para el dolor', 'Dolor grave en el estómago', b'0', 5, 8);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(41);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mascotas`
--

CREATE TABLE `mascotas` (
  `id` int(11) NOT NULL,
  `fechaNacimiento` date NOT NULL,
  `foto` varchar(100) DEFAULT NULL,
  `nombre` varchar(30) NOT NULL,
  `raza` varchar(30) NOT NULL,
  `tipo` varchar(30) NOT NULL,
  `idCliente` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `mascotas`
--

INSERT INTO `mascotas` (`id`, `fechaNacimiento`, `foto`, `nombre`, `raza`, `tipo`, `idCliente`) VALUES
(1, '2020-03-05', 'http://localhost:8080/mascotasImg/foto_epi.png', 'Epi', 'Boxer', 'Perro', 2),
(2, '2021-01-02', 'http://localhost:8080/mascotasImg/gato_montes.png', 'Blas', 'Montés', 'Gato', 2),
(3, '2021-02-12', 'http://localhost:8080/mascotasImg/tortuga_galapago.png', 'Duran', 'Galápago', 'Tortuga', 2),
(4, '2018-01-12', 'http://localhost:8080/mascotasImg/doctor.png', 'Salazar', 'Pitón', 'Serpiente', 7),
(5, '2018-05-12', 'http://localhost:8080/mascotasImg/conejo_blanco.png', 'López', 'Blanco', 'Conejo', 7);

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `activado`, `apellidos`, `nombre`, `password`, `rol`, `telefono`, `username`) VALUES
(1, b'1', 'admin', 'Admin', '$2a$10$/ZSsjGn3EkBjLKlQf02Ctu0vKOnyaYy4RUHOWSxotkHkhGI9gQHfW', 'ROLE_ADMIN', '123456789', 'admin'),
(2, b'0', 'Sánchez Chozas', 'Nuria', '$2a$10$F9xuAnBs8p6jE99eovASee32q4gRTxD5WijWpx8dMd5xZ3pFjIBjS', 'ROLE_CLIENTE', '675397345', 'nuria56'),
(3, b'0', 'López Pérez', 'Juan', '$2a$10$1tk3miZzu1TQZhPOsV49vesnX8aQFSyt.i0TvLG5aJV8VGiDVRq/y', 'ROLE_CLIENTE', '678487512', 'juanlope95'),
(4, b'0', 'Gómez González', 'Ana Belén', '$2a$10$zDG07Ejbku313IGM.08KtOmjT4SZORUPi9gCwPwW0RB03pKfNgE2K', 'ROLE_CLIENTE', '678568545', 'anabelen54'),
(5, b'1', 'Traverso Carrero', 'Fabio', '$2a$10$SzHNqfFZZ/FPzMEXLsVLqeGnKI4GQN/NfwOPpHN2RklNUe0Injfeq', 'ROLE_VETERINARIO', '672113415', 'fabitraver89'),
(6, b'1', 'López Chozas', 'Julián', '$2a$10$HHfXM6e6pOaOSTWFFwFWBuhEvlSUcCTMSgLX.VhPr5zAYRLswfNf2', 'ROLE_VETERINARIO', '678224590', 'julianlope'),
(7, b'0', 'Gomero López', 'Francisco', '$2a$10$2X2TmiBYA.8w0eTZgJUnUOWyc0H1l5iue8Lc9L0QM0qJL1akXhNmm', 'ROLE_CLIENTE', '672781134', 'frango1992'),
(8, b'1', 'Gómez Gardón', 'Laura', '$2a$10$4r6x.y6hlkgxzGE/iC9Znuo.Mx3GBjJuM.jKOKw7pNTY/dylfvRHG', 'ROLE_VETERINARIO', '678983216', 'lau90'),
(9, b'0', 'Pérez Gardón', 'Julia', '$2a$10$KIWFf1.8Y4EaHYRBHy3w8.24nroaG3ZsBmwFsEsuLG7fXPlNq9n7C', 'ROLE_CLIENTE', '956892190', 'julialv1995'),
(10, b'0', 'Reyes Gil', 'Álvaro', '$2a$10$XC9sRN1KqATRyirL0fQwWOrR7P9crPCK73ED6h7IqgQCqzyBFMoX2', 'ROLE_CLIENTE', '678443391', 'alvaroreyes54'),
(11, b'1', 'Castañeda López', 'Alfredo', '$2a$10$JzCT.T62QH6iKLFP3U8.wOJmsO904KzP7r1YnRi5CAV0Df032Uiv2', 'ROLE_VETERINARIO', '678998813', 'alfredocalo13'),
(12, b'1', 'López Reyes', 'Ignacio', '$2a$10$oUSReGrOHkcP8SCacIAk2OLVmvZ/aviKPCH1AoFghQ3KXB1Aumye2', 'ROLE_VETERINARIO', '678119021', 'ignaciolore90');

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
  ADD UNIQUE KEY `nombre` (`nombre`),
  ADD UNIQUE KEY `UK_klqk2ag4wmmplflpp5dcl9s8h` (`foto`),
  ADD KEY `FK7ylvjmk6us6vdv0lktvxjtlie` (`idCliente`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_7a6nioycwi246dw0dn2etj3ye` (`telefono`),
  ADD UNIQUE KEY `UK_m2dvbwfge291euvmk6vkkocao` (`username`);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `citas`
--
ALTER TABLE `citas`
  ADD CONSTRAINT `FKlt58i83go6rgbqs2e37u0oshp` FOREIGN KEY (`idVeterinario`) REFERENCES `usuarios` (`id`),
  ADD CONSTRAINT `FKwqiyeoeu8hxtdt5uevekpnr` FOREIGN KEY (`idMascota`) REFERENCES `mascotas` (`id`);

--
-- Filtros para la tabla `mascotas`
--
ALTER TABLE `mascotas`
  ADD CONSTRAINT `FK7ylvjmk6us6vdv0lktvxjtlie` FOREIGN KEY (`idCliente`) REFERENCES `usuarios` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
