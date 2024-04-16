package programaCliente.cliente;

import java.rmi.RemoteException;
import java.util.Set;

/**
 * Esta interfaz define un objeto cliente p2p
 * @author pablo garcia fuentes
 * @author adrian martinez balea
 */

public interface P2PClientInterface
        extends java.rmi.Remote {

    /**
     * Metodo que utiliza el servidor para notificar al cliente
     * de que se ha conectado uno de sus amigos
     * @param nombreUsuario nombre de usuario del amigo que se ha conectado
     * @param objetoCliente interfaz cliente del amigo que se ha conectado
     * @param miContrasenha contrasenha del usuario que recibe la notificacion para verificar que proviene del servidor
     * @param token token entre el cliente y el amigo conectado asignado por el servidor
     * @throws RemoteException
     */
    void notifyConexion(String nombreUsuario, P2PClientInterface objetoCliente, String miContrasenha, String token)
            throws RemoteException;

    /**
     * Metodo que utiliza el servidor para notificar al cliente
     * de que se ha desconectado uno de sus amigos
     * @param nombreUsuario nombre de usuario del amigo que se ha desconectado
     * @param miContrasenha contrasenha del usuario que recibe la notificacion para verificar que proviene del servidor
     * @throws RemoteException
     */
    void notifyDesconexion(String nombreUsuario, String miContrasenha)
            throws RemoteException;

    /**
     * Metodo que actualiza la lista de amigos y solicitudes
     * de un cliente, sin necesidad de abrir y cerrar la ventana
     * @param amigos lista de amigos del cliente
     * @param solEnviadas lista de solicitudes enviadas del cliente
     * @param solRecibidas lista de solicitudes recibidas del cliente
     * @param miContrasenha contrasenha del usuario que recibe la notificacion para verificar que proviene del servidor
     * @throws RemoteException
     */
    void notifyActualizarListados(Set<String> amigos, Set<String> solEnviadas, Set<String> solRecibidas, String miContrasenha)
            throws RemoteException;

    /**
     * Metodo que utiliza un cliente para recibir un mensaje de otro cliente
     * @param nombreUsuario nombre del usuario que envio el mensaje
     * @param mensaje mensaje enviado por el usuario
     * @param token token del amigo que envia el mensaje para verificar su identidad
     * @throws RemoteException
     */
    void recibirMensaje(String nombreUsuario, String mensaje, String token)
            throws RemoteException;


}
