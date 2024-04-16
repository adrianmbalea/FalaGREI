package programaServidor.servidor;

import programaCliente.cliente.P2PClientInterface;
import java.rmi.*;
import java.util.Set;

/**
 * Interfaz remota para el servidor que gestiona las conexiones de los clientes
 * @author pablo garcia fuentes
 * @author adrian martinez balea
 */

public interface P2PServerInterface extends Remote {

    /**
     * Este metodo remoto permite a un cliente registrarse en el servidor
     * para indicar al resto de clientes que esta en linea
     * @param nombreUsuario nombre de usuario del cliente que se quiere registrar
     * @param contrasenha contrasenha del cliente que se quiere registrar
     * @param callbackClientObject objeto cliente que se registra
     * @throws java.rmi.RemoteException
     */
    void conectarse(
            String nombreUsuario,
            String contrasenha,
            P2PClientInterface callbackClientObject)
            throws java.rmi.RemoteException;


    /**
     * Este metodo remoto permite a un cliente desuscribirse
     * para indicar al resto de clientes que se desconecta
     * @param nombreUsuario nombre de usuario del cliente que se quiere eliminar del registro
     * @param contrasenha contrasenha del cliente que se quiere eliminar del registro
     * @throws java.rmi.RemoteException
     */
    void desconectarse(
            String nombreUsuario,
            String contrasenha)
            throws java.rmi.RemoteException;

    /**
     * Este metodo remoto permite validar las
     * credenciales de un usuario
     * @param nombreUsuario nombre de usuario del usuario a validar
     * @param contrasenha contrasenha del usuario a validar
     * @return true si las credenciales son correctas, false en otro caso
     * @throws java.rmi.RemoteException
     */
    boolean validarUsuario(String nombreUsuario, String contrasenha)
            throws java.rmi.RemoteException;


    /**
     * Este metodo remoto permite listar todos los amigos
     * de un usuario determinado
     * @param nombreUsuario nombre de usuario del usuario en cuestion
     * @param contrasenha contrasenha del usuario en cuestion
     * @return lista de nombres de usuarios de los amigos del usuario
     * @throws java.rmi.RemoteException
     */
    Set<String> listarAmigos(String nombreUsuario, String contrasenha)
            throws java.rmi.RemoteException;


    /**
     * Este metodo remoto permite listar todas las solicitudes de amistad
     * enviadas por un usuario y que todavia no han sido respondidas
     * @param nombreUsuario nombre de usuario del usuario en cuestion
     * @param contrasenha contrasenha del usuario en cuestion
     * @return lista de nombres de usuarios de los usuarios que han recibido su solicitud
     * @throws java.rmi.RemoteException
     */
    Set<String> listarSolicitudesEnviadas(String nombreUsuario, String contrasenha)
            throws java.rmi.RemoteException;

    /**
     * Este metodo remoto permite listar todas las solicitudes de amistad
     * recibidas por un usuario y que todavia no han sido respondidas
     * @param nombreUsuario nombre de usuario del usuario en cuestion
     * @param contrasenha contrasenha del usuario en cuestion
     * @return lista de nombres de usuarios de los usuarios que le han enviado una solicitud
     * @throws java.rmi.RemoteException
     */
    Set<String> listarSolicitudesRecibidas(String nombreUsuario, String contrasenha)
            throws java.rmi.RemoteException;

    /**
     * Este metodo remoto permite enviar una solicitud de amistad,
     * por parte de un usuario a otro
     * @param nombreUsuario nombre de usuario del usuario que envia la solicitud
     * @param contrasenha contrasenha del usuario que envia la solicitud
     * @param nombreUsuarioReceptor nombre de usuario del usuario que recibe la solicitud
     * @return true si la solicitud se ha enviado correctamente, false en cualquier otro caso
     * @throws java.rmi.RemoteException
     */
    boolean enviarSolicitudAmistad(String nombreUsuario, String contrasenha, String nombreUsuarioReceptor)
            throws java.rmi.RemoteException;

    /**
     * Este metodo remoto permite aceptar una solicitud de amistad,
     * a un usuario que haya recibido la solicitud de otro
     * @param nombreUsuario nombre de usuario del usuario que recibio la solicitud
     * @param contrasenha contrasenha del usuario que recibio la solicitud
     * @param nombreUsuarioRemitente nombre de usuario del usuario que envio la solicitud
     * @return true si la solicitud se ha aceptado correctamente, false en cualquier otro caso
     * @throws java.rmi.RemoteException
     */
    boolean aceptarSolicitudAmistad(String nombreUsuario, String contrasenha, String nombreUsuarioRemitente)
            throws java.rmi.RemoteException;

    /**
     * Este metodo remoto permite rechazar una solicitud de amistad,
     * a un usuario que haya recibido la solicitud de otro
     * @param nombreUsuario nombre de usuario del usuario que recibio la solicitud
     * @param contrasenha contrasenha del usuario que recibio la solicitud
     * @param nombreUsuarioRemitente nombre de usuario del usuario que envio la solicitud
     * @return true si la solicitud se ha aceptado correctamente, false en cualquier otro caso
     * @throws java.rmi.RemoteException
     */
    boolean rechazarSolicitudAmistad(String nombreUsuario, String contrasenha, String nombreUsuarioRemitente)
            throws java.rmi.RemoteException;

    /**
     * Este metodo remoto permite a un usuario cancelar
     * o eliminar su amistad con otro usuario
     * @param nombreUsuario nombre de usuario del usuario que desea cancelar su amistad con otro usuario
     * @param contrasenha contrasenha del usuario que desea cancelar su amistad con otro usuario
     * @param nombreUsuarioAmigo nombre de usuario con el usuario que se quiere romper la amistad
     * @return true si se ha podido eliminar la amistad correctamente, false en cualquier otro caso
     * @throws java.rmi.RemoteException
     */
    boolean eliminarAmistad(String nombreUsuario, String contrasenha, String nombreUsuarioAmigo)
            throws java.rmi.RemoteException;

    /**
     * Este metodo remoto permite cancelar una solicitud
     * de amistad que un usuario haya enviado a otro
     * @param nombreUsuario nombre de usuario del usuario que quiere cancelar la solicitud
     * @param contrasenha contrasenha del usuario que quiere cancelar la solicitud
     * @param nombreUsuarioReceptor nombre de usuario del usuario que recibio la solicitud
     * @return true si la solicitud se ha aceptado correctamente, false en cualquier otro caso
     * @throws java.rmi.RemoteException
     */
    boolean cancelarSolicitudAmistad(String nombreUsuario, String contrasenha, String nombreUsuarioReceptor)
            throws java.rmi.RemoteException;

    /**
     * Este metodo remoto permite registrar a un usuario
     * @param nombreUsuario nombre de usuario del nuevo usuario
     * @param contrasenha contrasenha del nuevo usuario
     * @return true si se ha añadido correctamente, false en otro caso
     * @throws java.rmi.RemoteException
     */
    boolean registrarUsuario(String nombreUsuario, String contrasenha)
            throws java.rmi.RemoteException;

}
