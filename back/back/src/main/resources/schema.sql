CREATE TABLE level
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    type           VARCHAR(255),
    discount       DECIMAL               NOT NULL,
    extra_discount DECIMAL               NOT NULL,
    min_limit      DECIMAL,
    max_limit      DECIMAL,
    CONSTRAINT pk_level PRIMARY KEY (id)
);

ALTER TABLE level
    ADD CONSTRAINT uc_level_type UNIQUE (type);

CREATE TABLE card
(
    number              BIGINT AUTO_INCREMENT NOT NULL,
    total_purchases_sum DECIMAL               NOT NULL,
    level_id            BIGINT,
    CONSTRAINT pk_card PRIMARY KEY (number)
);

ALTER TABLE card
    ADD CONSTRAINT uc_card_number UNIQUE (number);

ALTER TABLE card
    ADD CONSTRAINT FK_CARD_ON_LEVEL FOREIGN KEY (level_id) REFERENCES level (id);