-- Script de creacion de tablas para la base de datos del servidor P2P:

-- Tabla de Usuarios:
create table usuarios(
	nombreUsuario varchar(50) not null,
	contrasenha char(128) not null,

	primary key(nombreUsuario)

);

-- Tabla de SerAmigos
CREATE TABLE serAmigos (
    remitente VARCHAR(50) NOT NULL,
    receptor VARCHAR(50) NOT NULL,
    esAceptada BOOLEAN NOT NULL,
    PRIMARY KEY (remitente, receptor),
    FOREIGN KEY (remitente) REFERENCES usuarios(nombreUsuario) ON DELETE NO ACTION ON UPDATE CASCADE,
    FOREIGN KEY (receptor) REFERENCES usuarios(nombreUsuario) ON DELETE NO ACTION ON UPDATE CASCADE
);

-- Funcion que hashea las contrasenhas para guardarlas en la base de datos
CREATE EXTENSION IF NOT EXISTS pgcrypto;
create or replace function hash_password(password text) returns text as $$
begin 
  return encode(digest(password, 'sha512'), 'hex'); -- Equivalente a 128 char (hexadecimal)
end;
$$ language plpgsql;