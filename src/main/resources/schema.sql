-- Drop existing schema if exists
DROP SCHEMA IF EXISTS `pa_project`;

-- Create new schema
CREATE SCHEMA IF NOT EXISTS `pa_project` DEFAULT CHARACTER SET utf8mb4;
USE
`pa_project`;

-- Table `User`
CREATE TABLE IF NOT EXISTS `Users`
(
    `id`
    INT
    NOT
    NULL
    AUTO_INCREMENT,
    `username`
    VARCHAR
(
    40
) NOT NULL UNIQUE,
    `password` VARCHAR
(
    40
),
    PRIMARY KEY
(
    `id`
)
    ) ENGINE = InnoDB;

-- Table `Auctions`
CREATE TABLE IF NOT EXISTS `Auctions`
(
    `id`
    INT
    NOT
    NULL
    AUTO_INCREMENT,
    `title`
    VARCHAR
(
    100
) NOT NULL,
    `description` TEXT NOT NULL,
    `initial_price` INT NOT NULL,
    `deadline` DATE NOT NULL,
    `time` TIME NOT NULL,
    `users_id` INT NOT NULL,
    PRIMARY KEY
(
    `id`
),
    INDEX `fk_Auctions_Users_idx`
(
    `users_id`
    ASC
),
    CONSTRAINT `fk_Auctions_Users`
    FOREIGN KEY
(
    `users_id`
)
    REFERENCES `Users`
(
    `id`
)
    ON DELETE NO ACTION
        ON UPDATE NO ACTION
    ) ENGINE = InnoDB;

-- Table `Bids`
CREATE TABLE IF NOT EXISTS `Final_Bids`
(
    `id`
    INT
    NOT
    NULL
    AUTO_INCREMENT,
    `price`
    INT
    NOT
    NULL,
    `auctions_id`
    INT
    NOT
    NULL,
    `users_id`
    INT
    NOT
    NULL,
    PRIMARY
    KEY
(
    `id`
),
    INDEX `fk_Bids_Auctions_idx`
(
    `auctions_id`
    ASC
),
    INDEX `fk_Bids_Users_idx`
(
    `users_id`
    ASC
),
    CONSTRAINT `fk_Bids_Auctions`
    FOREIGN KEY
(
    `auctions_id`
)
    REFERENCES `Auctions`
(
    `id`
)
    ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    CONSTRAINT `fk_Bids_Users`
    FOREIGN KEY
(
    `users_id`
)
    REFERENCES `Users`
(
    `id`
)
    ON DELETE NO ACTION
        ON UPDATE NO ACTION
    ) ENGINE = InnoDB;

-- Table `Image`
CREATE TABLE IF NOT EXISTS `Images`
(
    `id`
    INT
    NOT
    NULL
    AUTO_INCREMENT,
    `extension`
    VARCHAR
(
    6
) NOT NULL,
    `auctions_id` INT NOT NULL,
    PRIMARY KEY
(
    `id`
),
    INDEX `fk_Images_Auctions_idx`
(
    `auctions_id`
    ASC
),
    CONSTRAINT `fk_Images_Auctions`
    FOREIGN KEY
(
    `auctions_id`
)
    REFERENCES `Auctions`
(
    `id`
)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
    ) ENGINE = InnoDB;

