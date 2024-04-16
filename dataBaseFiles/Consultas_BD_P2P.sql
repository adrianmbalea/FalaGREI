-- Consultas para la base de datos del servidor P2P:


-- Consulta 1: Comprobar existencia de un usuario
-- Parametros: "nombre_usuario" es el nombre de usuario a comprobar
SELECT nombreUsuario
FROM usuarios
WHERE nombreUsuario = 'nombre_usuario';


-- Consulta 2: Validar un usuario
-- Parametros: "nombre_usuario" es el nombre de usuario del usuario a validar y "contrasenha", su contrasenha
SELECT nombreUsuario
FROM usuarios
WHERE nombreUsuario = 'nombre_usuario' AND contrasenha = hash_password('contrasenha');


-- Consulta 3: Comprueba que exista una solicitud de amistad pendiente entre dos usuarios
-- Parametros: "remitente" es el remitente de la solicitud y "receptor", el receptor
SELECT *
FROM serAmigos
WHERE (remitente = 'remitente' AND receptor = 'receptor' AND esAceptada = false)


-- Consulta 4: Comprueba que dos usuarios sean amigos
-- Parametros: "usuario1" y "usuario2" son los usuarios a comprobar si son amigos
SELECT *
FROM serAmigos
WHERE (remitente = 'usuario1' AND receptor = 'usuario2' AND esAceptada = true)
       OR (remitente = 'usuario2' AND receptor = 'usuario1' AND esAceptada = true)


-- Consulta 5: Devuelve los amigos de un determinado usuario
-- Parametros: "usuario" es el nombre de usuario del usuario en cuestion
SELECT CASE
         WHEN remitente = 'usuario' THEN receptor
         ELSE remitente
       END AS amigo
FROM serAmigos
WHERE (remitente = 'usuario' OR receptor = 'usuario')
  AND esAceptada = true;


-- Consulta 6: Devuelve las solicitudes pendientes enviadas por un usuario
-- Parametros: "usuario" es el nombre de usuario del usuario en cuestion
SELECT receptor
FROM serAmigos
WHERE remitente = 'usuario' AND esAceptada = false;


-- Consulta 7: Devuelve las solicitudes pendientes recibidas por un usuario
-- Parametros: "usuario" es el nombre de usuario del usuario en cuestion
SELECT remitente
FROM serAmigos
WHERE receptor = 'usuario' AND esAceptada = false;

