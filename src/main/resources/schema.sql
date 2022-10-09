create table board
(
    board_id        INT AUTO_INCREMENT PRIMARY KEY,
    board_title     VARCHAR(100)           NOT NULL,
    board_content   VARCHAR(500)           NOT NULL,
    board_user_name VARCHAR(10)            NOT NULL,
    board_password  VARCHAR(20)            NOT NULL,
    create_at       DATETIME               NOT NULL,
    delete_YN       VARCHAR(2) DEFAULT 'N' NOT NULL
);


create table comment
(
    comment_id        INT AUTO_INCREMENT PRIMARY KEY,
    board_id          INT          NOT NULL,
    comment           VARCHAR(100) NOT NULL,
    comment_user_name VARCHAR(10)  NOT NULL,
    comment_password  VARCHAR(20)  NOT NULL,
    ref               INT          NOT NULL,
    step              INT          NOT NULL,
    ref_order         INT          NOT NULL,
    answer_num        INT          NOT NULL,
    parent_num        INT          NOT NULL,
    FOREIGN KEY (board_id) REFERENCES board (board_id)
);