-- Script de inserción de usuarios para la base de datos del servidor P2P:

-- Usuarios
insert into usuarios(nombreUsuario, contrasenha) VALUES
('adrianmartinezbalea', hash_password('amb123')),
('pablogarciafuentes', hash_password('pgf123')),
('aarongarciafilgueira', hash_password('agf123')),
('pablogeb', hash_password('pgeb123'));

-- Relaciones de amistad
insert into serAmigos(remitente, receptor, esAceptada) VALUES
('adrianmartinezbalea', 'pablogarciafuentes', true),
('adrianmartinezbalea', 'aarongarciafilgueira', false),
('pablogarciafuentes', 'aarongarciafilgueira', false),
('pablogarciafuentes', 'pablogeb', true);
