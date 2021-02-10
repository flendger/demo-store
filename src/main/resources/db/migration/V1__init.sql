CREATE TABLE users
(
    id         bigserial PRIMARY KEY,
    username   varchar(25)  not null unique,
    password   varchar(150) not null,
    email      varchar(150) unique,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

INSERT INTO users (username, password, email)
VALUES ('user', '$2y$12$gtPCUPZv7sjZtJkq1/ot7.VaBx4jLm8AMdS56/0gEyItLWBzRtSAa', 'user@mail.ru'),
       ('admin', '$2y$12$gtPCUPZv7sjZtJkq1/ot7.VaBx4jLm8AMdS56/0gEyItLWBzRtSAa', 'admin@mail.ru');

CREATE TABLE roles
(
    id         bigserial PRIMARY KEY,
    name       varchar(50) not null unique,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

INSERT INTO roles (name)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN');

CREATE TABLE user_roles
(
    user_id bigint not null references users (id),
    role_id bigint not null references roles (id)
);

INSERT INTO user_roles (user_id, role_id)
VALUES (1, 1),
       (2, 2);

CREATE TABLE products
(
    id         bigserial PRIMARY KEY,
    title      varchar(255),
    price      int,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

INSERT INTO products (title, price)
VALUES ('product_1', 1),
       ('product_2', 2),
       ('product_3', 3),
       ('product_4', 4),
       ('product_5', 5),
       ('product_6', 6),
       ('product_7', 7),
       ('product_8', 8),
       ('product_9', 9),
       ('product_10', 10),
       ('product_11', 11),
       ('product_12', 12),
       ('product_13', 13),
       ('product_14', 14),
       ('product_15', 15),
       ('product_16', 16),
       ('product_17', 17),
       ('product_18', 18),
       ('product_19', 19),
       ('product_20', 20);

CREATE TABLE order_addresses
(
    id         bigserial PRIMARY KEY,
    user_id    bigint       not null references users (id),
    address    varchar(255) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

CREATE TABLE orders
(
    id         bigserial PRIMARY KEY,
    date       date   not null,
    user_id    bigint not null references users (id),
    sum        int       default 0,
    address_id bigint not null references order_addresses (id),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

CREATE TABLE order_items
(
    id         bigserial PRIMARY KEY,
    order_id   bigint not null references orders (id),
    product_id bigint not null references products (id),
    quantity   int       default 0,
    price      int       default 0,
    sum        int       default 0,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);
