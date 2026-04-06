-- Insert Authors
INSERT INTO authors (id, name) VALUES (1, 'John Doe');
INSERT INTO authors (id, name) VALUES (2, 'Jane Smith');

-- Insert Books (with foreign key author_id)
INSERT INTO books (id, title, author_id) VALUES (1, 'Spring Boot Basics', 1);
INSERT INTO books (id, title, author_id) VALUES (2, 'Hibernate Deep Dive', 1);
INSERT INTO books (id, title, author_id) VALUES (3, 'Java Fundamentals', 2);