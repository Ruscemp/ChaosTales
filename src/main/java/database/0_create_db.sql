DROP DATABASE IF EXISTS chaosTales;
CREATE DATABASE chaosTales;
USE chaosTales;

CREATE TABLE IF NOT EXISTS game_data(
    id VARCHAR(32) NOT NULL,
    points INT NOT NULL DEFAULT 0,
    PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS characters(
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(60) NOT NULL,
    gender VARCHAR(6) NOT NULL,
    race VARCHAR(5) NOT NULL,
    class VARCHAR(7) NOT NULL,
    god VARCHAR(22) NOT NULL,
    strength INT NOT NULL DEFAULT 0,
    dexterity INT NOT NULL DEFAULT 0,
    constitution INT NOT NULL DEFAULT 0,
    intelligence INT NOT NULL DEFAULT 0,
    wisdom INT NOT NULL DEFAULT 0,
    will INT NOT NULL DEFAULT 0,
    charisma INT NOT NULL DEFAULT 0,
    luck INT NOT NULL DEFAULT 0,
    magic INT NOT NULL DEFAULT 0,
    game_id VARCHAR(32) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (game_id) REFERENCES game_data (id) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE IF NOT EXISTS abilities(
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL,
    owner_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (owner_id) REFERENCES characters (id) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE IF NOT EXISTS items(
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(28) NOT NULL,
    owner_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (owner_id) REFERENCES characters (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS adventure_data(
    id INT NOT NULL AUTO_INCREMENT,
    questType VARCHAR(7),
    quest VARCHAR(52),
    questPlace INT,
    questTarget INT,
    progressCur INT,
    questProgressStep INT,
    progressMax INT,
    earnedPoints INT,
    curCharacter INT NOT NULL,
    game_id VARCHAR(32) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (curCharacter) REFERENCES characters (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (game_id) REFERENCES game_data (id) ON DELETE CASCADE ON UPDATE CASCADE
);
