-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 21-05-2024 a las 12:29:28
-- Versión del servidor: 10.4.28-MariaDB
-- Versión de PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+01:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `Libreria`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Libreria`
--

CREATE TABLE `Libreria` (
  `Nombre` varchar(50) NOT NULL,
  `Tipo` varchar(20) NOT NULL,
  `Fecha fin` date NOT NULL,
  `Puntuacion` float NOT NULL,
  `IMDB/Metacritic` varchar(20) DEFAULT NULL,
  `Imagen` varchar(999) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `Libreria`
--

INSERT INTO `Libreria` (`Nombre`, `Tipo`, `Fecha fin`, `Puntuacion`, `IMDB/Metacritic`, `Imagen`) VALUES
('Animal well', 'Videojuego', '2024-03-31', 6, 'Metacritic : 9.1', 'https://cdn2.steamgriddb.com/thumb/583caf9a777234cbee3c6a9977fbdc9c.jpg'),
('Cyberpunk 2077', 'Videojuego', '2024-05-01', 6, 'Metacritic : 8.6', 'https://cdn2.steamgriddb.com/thumb/4030e2eebb977639f8836aa25a293e40.jpg'),
('dead cells', 'Videojuego', '2024-05-17', 4, 'Metacritic : 8.9', 'https://m.media-amazon.com/images/M/MV5BNzRjY2VhZDgtZGJlZi00ZmM2LTk1MTQtNDg4ZTY4YmFkN2I3XkEyXkFqcGdeQXVyMTA0MTM5NjI2._V1_FMjpg_UX1000_.jpg'),
('Hollow Knight', 'Videojuego', '2023-09-28', 8, 'Metacritic : 9.0', 'https://cdn2.steamgriddb.com/thumb/d18c832e8c956b4ef8b92862e6bf470d.jpg'),
('Stardew Valley', 'Videojuego', '2024-05-08', 3, 'Metacritic : 8.9', 'https://m.media-amazon.com/images/M/MV5BYmZiMWNlOWMtODMyNi00ZThiLTk0ZjYtOTQwMGRiNzE2NjFhXkEyXkFqcGdeQXVyNTgyNTA4MjM@._V1_FMjpg_UX1000_.jpg'),
('TBOI', 'Videojuego', '2024-05-15', 1, 'Metacritic : 8.8', 'https://m.media-amazon.com/images/M/MV5BZmZjMTYxM2UtYjYxNi00YTIwLWI1ZWMtNWZiNTU4NmM1ZDRjXkEyXkFqcGdeQXVyMTA0MTM5NjI2._V1_FMjpg_UX1000_.jpg'),
('The witcher 3', 'Videojuego', '2024-03-31', 7, '9.2', 'https://cdn2.steamgriddb.com/thumb/4904f82c12cecf6ec070fe77d7e913ce.jpg');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `Libreria`
--
ALTER TABLE `Libreria`
  ADD PRIMARY KEY (`Nombre`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
