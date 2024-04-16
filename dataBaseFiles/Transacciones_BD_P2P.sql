-- Transacciones para la base de datos del servidor P2P:


-- Transaccion 1: Enviar una solicitud de amistad, añadiendo una fila a serAmigos
-- Parametros: "usuario1" es el nombre de usuario del remitente de la solicitud y "usuario2", del receptor
BEGIN TRANSACTION;
    INSERT INTO serAmigos (remitente, receptor, esAceptada)
    VALUES ('usuario1', 'usuario2', false);
COMMIT TRANSACTION;


-- Transaccion 2: Aceptar una solicitud de amistad, modificando el atributo esAceptada de la relacion serAmigos
-- Parametros: "remitente" y "receptor" son los nombres de usuario de los usuarios en cuestión
BEGIN TRANSACTION;
    UPDATE serAmigos
    SET esAceptada = true
    WHERE (remitente = 'remitente' AND receptor = 'receptor' AND esAceptada = false);
COMMIT TRANSACTION;


-- Transaccion 3: Rechazar una solicitud de amistad, eliminando la relacion de la tabla
-- Parametros: "remitente" y "receptor" son los nombres de usuario de los usuarios en cuestión
BEGIN TRANSACTION;
DELETE FROM serAmigos
WHERE (remitente = 'remitente' AND receptor = 'receptor' AND esAceptada = false);
COMMIT TRANSACTION;


-- Transaccion 4: Eliminar una relacion de amistad
-- Parametros: "usuario1" y "usuario2" son los nombres de usuario de los usuarios en cuestión
BEGIN TRANSACTION;
DELETE FROM serAmigos
WHERE (remitente = 'usuario1' AND receptor = 'usuario2' AND esAceptada = true)
   OR (remitente = 'usuario2' AND receptor = 'usuario1' AND esAceptada = true);
COMMIT TRANSACTION;