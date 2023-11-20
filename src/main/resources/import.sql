INSERT INTO books (authors, created_at, isbn, publisher, synopsis, title, `year`, number_of_copies) VALUES('Frank Herbert', '2023-11-09 11:35:00', '1236547896541', 'Mondadori', '', 'Dune', 1962,5);
INSERT INTO books (authors, created_at, isbn, publisher, synopsis, title, `year`, number_of_copies) VALUES('Melville', '2023-11-09 11:37:00', '5556547896541', 'Penguin', '', 'Moby Dick', 1865,3);

INSERT INTO categories(name) VALUES('fiction');
INSERT INTO categories(name) VALUES('non-fiction');
INSERT INTO categories(name) VALUES('teenagers');
INSERT INTO categories(name) VALUES('computer science');

INSERT INTO roles (id, name) VALUES(1, 'ADMIN');
INSERT INTO roles (id, name) VALUES(2, 'USER');

INSERT INTO users (email, first_name, last_name, registered_at, password) VALUES('john@email.com', 'John', 'Doe', '2023-11-20 10:35', '{noop}john');
INSERT INTO users (email, first_name, last_name, registered_at, password) VALUES('jane@email.com', 'Jane', 'Smith', '2023-11-20 10:35','{noop}jane');

INSERT INTO users_roles (user_id, roles_id) VALUES(1, 1);
INSERT INTO users_roles (user_id, roles_id) VALUES(1, 2);
INSERT INTO users_roles (user_id, roles_id) VALUES(2, 2);