INSERT INTO users (username, password, is_admin)
VALUES ('admin', '$2a$10$qG9.p.QqaA0nB0roaaQSVO6lKaQhR/eH7CRi9CGgiojgPN6256VBi', TRUE)
ON CONFLICT (username) DO UPDATE SET is_admin = TRUE;
