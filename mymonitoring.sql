-- phpMyAdmin SQL Dump
-- version 5.0.3
-- https://www.phpmyadmin.net/
--
-- Хост: 127.0.0.1
-- Время создания: Апр 01 2021 г., 17:48
-- Версия сервера: 10.4.14-MariaDB
-- Версия PHP: 7.4.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `mymonitoring`
--

-- --------------------------------------------------------

--
-- Структура таблицы `devices`
--

CREATE TABLE `devices` (
  `id` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `deviceName` text NOT NULL,
  `description` text DEFAULT NULL,
  `isPublic` tinyint(1) NOT NULL,
  `isSharedConfig` tinyint(1) NOT NULL,
  `longitude` int(11) NOT NULL,
  `latitude` int(11) NOT NULL,
  `altitude` int(11) NOT NULL,
  `apiKey` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Дамп данных таблицы `devices`
--

INSERT INTO `devices` (`id`, `userId`, `deviceName`, `description`, `isPublic`, `isSharedConfig`, `longitude`, `latitude`, `altitude`, `apiKey`) VALUES
(1, 1, 'virtualDevice', 'virtualDevice', 0, 0, 0, 0, 0, '1');

-- --------------------------------------------------------

--
-- Структура таблицы `measurements`
--

CREATE TABLE `measurements` (
  `id` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `devId` int(11) NOT NULL,
  `iot_name` varchar(16) NOT NULL,
  `name` varchar(128) NOT NULL DEFAULT 'Безимянный',
  `symbol` varchar(16) NOT NULL DEFAULT '???',
  `description` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Дамп данных таблицы `measurements`
--

INSERT INTO `measurements` (`id`, `userId`, `devId`, `iot_name`, `name`, `symbol`, `description`) VALUES
(1, 1, 1, 'varname', 'Безимянный', 'di/ck', NULL),
(2, 1, 1, 'varname_2', 'Безимянный', 'x/yi', NULL);

-- --------------------------------------------------------

--
-- Структура таблицы `measure_data`
--

CREATE TABLE `measure_data` (
  `id` int(11) NOT NULL,
  `devId` int(11) NOT NULL,
  `unixTimeStamp` bigint(20) NOT NULL,
  `measureId` int(11) NOT NULL,
  `data` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Дамп данных таблицы `measure_data`
--

INSERT INTO `measure_data` (`id`, `devId`, `unixTimeStamp`, `measureId`, `data`) VALUES
(1, 1, 1616885572, 1, 1),
(2, 1, 1616885572, 2, 1),
(3, 1, 1616885611, 1, 1.01),
(4, 1, 1616885611, 2, 1.01),
(5, 1, 1616960301, 1, 1.01),
(6, 1, 1616960301, 2, 1.01);

-- --------------------------------------------------------

--
-- Структура таблицы `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Дамп данных таблицы `users`
--

INSERT INTO `users` (`id`, `username`, `password`) VALUES
(1, 'root', 'root');

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `devices`
--
ALTER TABLE `devices`
  ADD PRIMARY KEY (`id`),
  ADD KEY `userId` (`userId`) USING BTREE;

--
-- Индексы таблицы `measurements`
--
ALTER TABLE `measurements`
  ADD PRIMARY KEY (`id`),
  ADD KEY `userId` (`userId`),
  ADD KEY `devId` (`devId`);

--
-- Индексы таблицы `measure_data`
--
ALTER TABLE `measure_data`
  ADD PRIMARY KEY (`id`),
  ADD KEY `measureId` (`measureId`),
  ADD KEY `devId` (`devId`);

--
-- Индексы таблицы `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `devices`
--
ALTER TABLE `devices`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT для таблицы `measurements`
--
ALTER TABLE `measurements`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT для таблицы `measure_data`
--
ALTER TABLE `measure_data`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT для таблицы `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `devices`
--
ALTER TABLE `devices`
  ADD CONSTRAINT `devices_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `measurements`
--
ALTER TABLE `measurements`
  ADD CONSTRAINT `measurements_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `measurements_ibfk_2` FOREIGN KEY (`devId`) REFERENCES `devices` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `measure_data`
--
ALTER TABLE `measure_data`
  ADD CONSTRAINT `measure_data_ibfk_1` FOREIGN KEY (`measureId`) REFERENCES `measurements` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `measure_data_ibfk_2` FOREIGN KEY (`devId`) REFERENCES `devices` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
