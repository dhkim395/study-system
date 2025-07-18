create database study;
use study;
CREATE TABLE study_user(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);
CREATE TABLE study_board(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    max_participants INT NOT NULL,
    created_by BIGINT,
	FOREIGN KEY (created_by) REFERENCES study_user(id)
);

CREATE TABLE study_join(
    study_board_id BIGINT,
    study_user_id BIGINT,
    PRIMARY KEY (study_board_id, study_user_id),
    FOREIGN KEY (study_board_id) REFERENCES study_board(id) ON DELETE CASCADE,
    FOREIGN KEY (study_user_id) REFERENCES study_user(id) ON DELETE CASCADE
);

ALTER TABLE study_board ADD COLUMN deadline DATE;