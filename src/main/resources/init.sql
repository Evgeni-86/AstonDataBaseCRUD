DROP TABLE IF EXISTS topic_messages;
DROP TABLE IF EXISTS topics;
CREATE TABLE topics
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50),
    CONSTRAINT topic_name_unique UNIQUE (name)
);
CREATE TABLE topic_messages
(
    id       SERIAL PRIMARY KEY,
    topic_id INTEGER      NOT NULL,
    title    VARCHAR(50) NOT NULL,
    body     TEXT         NOT NULL,
    CONSTRAINT message_unique UNIQUE (id, topic_id),
    FOREIGN KEY (topic_id) REFERENCES topics (id) ON DELETE CASCADE
);
CREATE INDEX messages_idx ON topic_messages (topic_id)