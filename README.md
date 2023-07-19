# Jatyta
Es un servicio de registro de compras para CLT S.A.

# DB Config
El nombre y la configuración de la conexión a la base de datos se encuentra en el archivo application.properties
Default DB: jatyta

# Script de la DB
create table users (
id bigserial not null primary key,
email varchar(50) not null,
password varchar(120) not null,
username varchar(50) not null
);

create table roles(
id serial not null primary key,
name varchar(20) not null
);

create table user_roles (
user_id bigint not null,
role_id int not null,
primary key(user_id, role_id),
foreign key(user_id) references users(id) on delete cascade on update no action,
foreign key(role_id) references roles(id) on delete cascade on update no action
);

INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_MODERATOR');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');