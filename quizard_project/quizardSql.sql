DROP DATABASE IF EXISTS quizard;
CREATE DATABASE quizard;
USE quizard;

CREATE TABLE IF NOT EXISTS Quizes(
                                     quiz_id INT UNSIGNED AUTO_INCREMENT,
                                     quiz_name VARCHAR(255),
    description VARCHAR(255),
    category VARCHAR(255),
    creator_id INT,
    amount_taken INT,
    random_order BOOL,
    time_created DATETIME,
    multiple_pages BOOL,
    max_points INT,
    immediate_correction BOOL,
    practice_mode bool,
    time_limit INT,
    PRIMARY KEY(quiz_id)
    );

CREATE TABLE IF NOT EXISTS Questions(
                                        question_id INT AUTO_INCREMENT,
                                        quiz_id INT UNSIGNED,
                                        question VARCHAR(225),
    question_type enum('Question_Response','Fill_In_The_Blank','Multiple_Choice','Picture_Response', 'Multi_Answer', 'Multiple_Choice_Multiple_Answers'),
    PRIMARY KEY (question_id)
    );

CREATE TABLE IF NOT EXISTS Answers(
                                      question_id INT,
                                      answer VARCHAR(225),
                                      is_correct bool,
    PRIMARY KEY (question_id)
    );

CREATE TABLE IF NOT EXISTS Quiz_history(
                                           performance_id INT AUTO_INCREMENT,
                                           quiz_id INT UNSIGNED,
                                           user_id INT,
                                           score INT,
                                           start_time DATETIME,
                                           end_time DATETIME,
                                           quiz_max_score INT,
                                           PRIMARY KEY (performance_id)
    );

CREATE TABLE IF NOT EXISTS quiz_categories (
                                               category_id INT AUTO_INCREMENT,
                                               category_name VARCHAR(255),
                                               PRIMARY KEY (category_id)
);

CREATE TABLE IF NOT EXISTS quiz_tags (
                                               tag_id INT AUTO_INCREMENT,
                                               quiz_id INT UNSIGNED,
                                               tag_name VARCHAR(255),
                                               PRIMARY KEY (tag_id)
);

CREATE TABLE IF NOT EXISTS Users(
                                    user_id INT AUTO_INCREMENT,
                                    user_email VARCHAR(225),
    user_name VARCHAR(225),
    is_admin BOOL,
    user_password_hashcode VARCHAR(225),
    PRIMARY KEY (user_id)
    );

CREATE TABLE IF NOT EXISTS friends(
                                      friend1_id INT,
                                      friend2_id INT,
                                      friends_since DATETIME,
                                      UNIQUE KEY friend1_id(friend1_id, friend2_id),
    UNIQUE KEY friend2_id(friend2_id, friend1_id)
    );

CREATE TABLE IF NOT EXISTS messages(
                                       message_id INT AUTO_INCREMENT,
                                       from_id INT,
                                       to_id INT,
                                       message_type enum('CHALLENGE', 'NOTE','FRIEND_REQUEST'),
    message VARCHAR(225),
    time_sent DATETIME,
    PRIMARY KEY (message_id)
    );

CREATE TABLE IF NOT EXISTS announcements (
                                             user_id INT,
                                             announcement_id INT UNSIGNED AUTO_INCREMENT,
                                             title VARCHAR(225),
    description VARCHAR(255),
    time_posted DATETIME,
    PRIMARY KEY (announcement_id)
    );

CREATE TABLE IF NOT EXISTS achievements (
                                            achievement_id INT UNSIGNED AUTO_INCREMENT,
                                            user INT,
                                            achievment VARCHAR(255),
    PRIMARY KEY (achievement_id)
    );
ALTER TABLE Users ADD CONSTRAINT USERNAME_UNIQUE UNIQUE (user_name);
ALTER TABLE quiz_categories ADD CONSTRAINT CATEGORY_NAME_UNIQUE UNIQUE (category_name);
ALTER TABLE quiz_tags ADD CONSTRAINT TAG_NAME_UNIQUE UNIQUE (quiz_id, tag_name);
ALTER TABLE Quizes ADD CONSTRAINT USER_TO_QUIZ FOREIGN KEY (creator_id) REFERENCES Users (user_id) ON DELETE CASCADE;
ALTER TABLE Quizes ADD CONSTRAINT CATEGORY_TO_QUIZ FOREIGN KEY (category) REFERENCES quiz_categories (category_name) ON DELETE CASCADE;
ALTER TABLE Questions ADD CONSTRAINT QUIZ_TO_QUESTION FOREIGN KEY (quiz_id) REFERENCES Quizes (quiz_id) ON DELETE CASCADE;
ALTER TABLE Answers ADD CONSTRAINT QUESTION_TO_ANSWER FOREIGN KEY (question_id) REFERENCES Questions (question_id) ON DELETE CASCADE;
ALTER TABLE Quiz_history ADD CONSTRAINT USER_TO_QUIZ_HISTORY FOREIGN KEY (user_id) REFERENCES Users (user_id) ON DELETE CASCADE;
ALTER TABLE Quiz_history ADD CONSTRAINT QUIZ_TO_QUIZ_HISTORY FOREIGN KEY (quiz_id) REFERENCES Quizes (quiz_id) ON DELETE CASCADE;
ALTER TABLE friends ADD CONSTRAINT USER_TO_FRIENDS_FIRST FOREIGN KEY (friend1_id) REFERENCES Users (user_id) ON DELETE CASCADE;
ALTER TABLE friends ADD CONSTRAINT USER_TO_FRIENDS_SECOND FOREIGN KEY (friend2_id) REFERENCES Users (user_id) ON DELETE CASCADE;
ALTER TABLE messages ADD CONSTRAINT USER_TO_MESSAGES_FIRST FOREIGN KEY (from_id) REFERENCES Users (user_id) ON DELETE CASCADE;
ALTER TABLE messages ADD CONSTRAINT USER_TO_MESSAGES_SECOND FOREIGN KEY (to_id) REFERENCES Users (user_id) ON DELETE CASCADE;
ALTER TABLE announcements ADD CONSTRAINT USER_TO_ANNOUNCEMENTS FOREIGN KEY (user_id) REFERENCES Users (user_id) ON DELETE CASCADE;
ALTER TABLE achievements ADD CONSTRAINT USER_TO_ACHIEVEMENTS FOREIGN KEY (user) REFERENCES Users (user_id) ON DELETE CASCADE;
aLTER TABLE quiz_tags ADD CONSTRAINT QUIZ_TO_TAGS FOREIGN KEY (quiz_id) REFERENCES Quizes (quiz_id) ON DELETE CASCADE;
