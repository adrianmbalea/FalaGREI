package programaServidor.servidor;

import programaCliente.cliente.P2PClientInterface;
import programaServidor.baseDatos.ConexionBD;
import java.rmi.*;
import java.rmi.server.*;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Set;

/**
 * Esta clase implementa la interfaz remota P2PServerInterface
 * @author pablo garcia fuentes
 * @author adrian martinez balea
 */

public class P2PServerImpl extends UnicastRemoteObject
        implements P2PServerInterface {

    // HashMap que almacena los clientes en linea (nombre de usuario - referencia del objeto)
    private HashMap<String, P2PClientInterface> clientesConectados;
    // HashMap que almacena las contrasenhas de los clientes conectados (nombre de usuario - contrasenha)
    private HashMap<String, String> contrasenhasClientes;
    // Clase de conexion con la base de datos
    private ConexionBD conexionBD;


    /**
     * Constructor de la clase P2PServerImpl
     * @throws RemoteException
     */
    public P2PServerImpl() throws RemoteException {
        super();
        clientesConectados = new HashMap<>();
        contrasenhasClientes = new HashMap<>();
        conexionBD = new ConexionBD("dataBaseFiles/baseDatos.properties");
    }

    /**
     * Este metodo remoto permite a un cliente registrarse en el servidor
     * para indicar al resto de clientes que esta en linea
     * @param nombreUsuario nombre de usuario del cliente que se quiere registrar
     * @param contrasenha contrasenha del cliente que se quiere registrar
     * @param callbackClientObject objeto cliente que se registra
     * @throws java.rmi.RemoteException
     */
    public synchronized void conectarse(String nombreUsuario, String contrasenha, P2PClientInterface callbackClientObject)
            throws java.rmi.RemoteException {

        if(!this.conexionBD.validarUsuario(nombreUsuario, contrasenha)){
            System.out.println("register: incorrect login");
            return ;
        }

        // Guarda el objeto cliente p2p en la lista
        if ((!clientesConectados.containsKey(nombreUsuario))) {

            clientesConectados.put(nombreUsuario, callbackClientObject);
            contrasenhasClientes.put(nombreUsuario, contrasenha);

            // Se actualiza la lista de clientes conectados de cada cliente
            // Tambien se notifica al nuevo cliente de todos sus amigos ya conectados
            for(String cliente : this.clientesConectados.keySet()) {
                if(this.conexionBD.sonAmigos(nombreUsuario, cliente)){

                    // Creamos un token unico entre cada par de amigos
                    // Se envia el mismo token a los dos amigos y no se almacena en el servidor
                    String token = generarToken(32);

                    // Notificamos a ambos clientes con el mismo token
                    this.clientesConectados.get(cliente).notifyConexion(nombreUsuario, callbackClientObject, this.contrasenhasClientes.get(cliente), token);
                    callbackClientObject.notifyConexion(cliente, this.clientesConectados.get(cliente), this.contrasenhasClientes.get(nombreUsuario), token);
                }
            }
            System.out.println("Registered new client");
        } else {
            System.out.println(
                    "register: client was already registered.");
        }
    }


    /**
     * Este metodo remoto permite a un cliente desuscribirse
     * para indicar al resto de clientes que se desconecta
     * @param nombreUsuario nombre de usuario del cliente que se quiere eliminar del registro
     * @param contrasenha contrasenha del cliente que se quiere eliminar del registro
     * @throws java.rmi.RemoteException
     */
    public synchronized void desconectarse(String nombreUsuario, String contrasenha)
            throws java.rmi.RemoteException {
        if(!this.conexionBD.validarUsuario(nombreUsuario, contrasenha)){
            System.out.println("unregister: incorrect login");
            return;
        }

        if (this.clientesConectados.remove(nombreUsuario)!=null) {
            // Se actualiza la lista de clientes conectados de cada cliente
            for(String cliente : this.clientesConectados.keySet()) {
                if(this.conexionBD.sonAmigos(nombreUsuario, cliente)){
                    this.clientesConectados.get(cliente).notifyDesconexion(nombreUsuario, this.contrasenhasClientes.get(cliente));
                }
            }
            System.out.println("Unregistered client ");
        } else {
            System.out.println(
                    "unregister: client wasn't registered.");
        }
    }

    /**
     * Este metodo remoto permite validar
     * las credenciales de un usuario
     * @param nombreUsuario nombre de usuario del usuario a validar
     * @param contrasenha contrasenha del usuario a validar
     * @return true si las credenciales son correctas, false en otro caso
     */
    public boolean validarUsuario(String nombreUsuario, String contrasenha)
            throws java.rmi.RemoteException{
        return this.conexionBD.validarUsuario(nombreUsuario, contrasenha);
    }

    /**
     * Este metodo remoto permite listar todos
     * los amigos de un usuario determinado
     * @param nombreUsuario nombre de usuario del usuario en cuestion
     * @param contrasenha contrasenha del usuario en cuestion
     * @return lista de nombres de usuario de los amigos del usuario
     * @throws java.rmi.RemoteException
     */
    public synchronized Set<String> listarAmigos(String nombreUsuario, String contrasenha)
            throws java.rmi.RemoteException{
        if(!this.conexionBD.validarUsuario(nombreUsuario, contrasenha)){
            return null;
        }
        return this.conexionBD.listarAmigos(nombreUsuario);
    }

    /**
     * Este metodo remoto permite listar todas las solicitudes de amistad
     * enviadas por un usuario y que todavia no han sido respondidas
     * @param nombreUsuario nombre de usuario del usuario en cuestion
     * @param contrasenha contrasenha del usuario en cuestion
     * @return lista de nombres de usuarios de los usuarios que han recibido su solicitud
     * @throws java.rmi.RemoteException
     */
    public synchronized Set<String> listarSolicitudesEnviadas(String nombreUsuario, String contrasenha)
            throws java.rmi.RemoteException{
        if(!this.conexionBD.validarUsuario(nombreUsuario, contrasenha)){
            return null;
        }
        return this.conexionBD.listarSolicitudesEnviadas(nombreUsuario);
    }

    /**
     * Este metodo remoto permite listar todas las solicitudes de amistad
     * recibidas por un usuario y que todavia no han sido respondidas
     * @param nombreUsuario nombre de usuario del usuario en cuestion
     * @param contrasenha contrasenha del usuario en cuestion
     * @return lista de nombres de usuarios de los usuarios que le han enviado una solicitud
     * @throws java.rmi.RemoteException
     */
    public synchronized Set<String> listarSolicitudesRecibidas(String nombreUsuario, String contrasenha)
            throws java.rmi.RemoteException{
        if(!this.conexionBD.validarUsuario(nombreUsuario, contrasenha)){
            return null;
        }
        return this.conexionBD.listarSolicitudesRecibidas(nombreUsuario);
    }

    /**
     * Este metodo remoto permite enviar una solicitud de amistad,
     * por parte de un usuario a otro
     * @param nombreUsuario nombre de usuario del usuario que envia la solicitud
     * @param contrasenha contrasenha del usuario que envia la solicitud
     * @param nombreUsuarioReceptor nombre de usuario del usuario que recibe la solicitud
     * @return true si la solicitud se ha enviado correctamente, false en cualquier otro caso
     * @throws java.rmi.RemoteException
     */
    public synchronized boolean enviarSolicitudAmistad(String nombreUsuario, String contrasenha, String nombreUsuarioReceptor)
            throws java.rmi.RemoteException{
        if(!this.conexionBD.validarUsuario(nombreUsuario, contrasenha)){
            return false;
        }

        if(!this.conexionBD.enviarSolicitudAmistad(nombreUsuario, nombreUsuarioReceptor)){
            return false;
        }

        // Actualizamos las listas de amigos y solicitudes de ambos usuarios
        if(this.clientesConectados.containsKey(nombreUsuarioReceptor)){
            P2PClientInterface usuarioReceptor = this.clientesConectados.get(nombreUsuarioReceptor);
            usuarioReceptor.notifyActualizarListados(this.conexionBD.listarAmigos(nombreUsuarioReceptor),
                    this.conexionBD.listarSolicitudesEnviadas(nombreUsuarioReceptor),
                    this.conexionBD.listarSolicitudesRecibidas(nombreUsuarioReceptor),
                    this.contrasenhasClientes.get(nombreUsuarioReceptor));
        }

        return true;
    }

    /**
     * Este metodo remoto permite aceptar una solicitud de amistad,
     * a un usuario que haya recibido la solicitud de otro
     * @param nombreUsuario nombre de usuario del usuario que recibio la solicitud
     * @param contrasenha contrasenha del usuario que recibio la solicitud
     * @param nombreUsuarioRemitente nombre de usuario del usuario que envio la solicitud
     * @return true si la solicitud se ha aceptado correctamente, false en cualquier otro caso
     * @throws java.rmi.RemoteException
     */
    public synchronized boolean aceptarSolicitudAmistad(String nombreUsuario, String contrasenha, String nombreUsuarioRemitente)
            throws java.rmi.RemoteException{
        if(!this.conexionBD.validarUsuario(nombreUsuario, contrasenha)){
            return false;
        }

        if(!this.conexionBD.aceptarSolicitudAmistad(nombreUsuarioRemitente, nombreUsuario)){
            return false;
        }

        // Si el usuario al que aceptamos la solicitud esta conectado, actualizamos sus listas de amigos conectados
        if(this.clientesConectados.containsKey(nombreUsuarioRemitente)){
            P2PClientInterface usuarioRemitente = this.clientesConectados.get(nombreUsuarioRemitente);
            P2PClientInterface usuarioReceptor = this.clientesConectados.get(nombreUsuario);

            // Creamos un token unico para el par de amigos
            String token = generarToken(32);

            usuarioReceptor.notifyConexion(nombreUsuarioRemitente, usuarioRemitente, this.contrasenhasClientes.get(nombreUsuario), token);
            usuarioRemitente.notifyConexion(nombreUsuario, usuarioReceptor, this.contrasenhasClientes.get(nombreUsuarioRemitente), token);

            usuarioRemitente.notifyActualizarListados(this.conexionBD.listarAmigos(nombreUsuarioRemitente),
                    this.conexionBD.listarSolicitudesEnviadas(nombreUsuarioRemitente),
                    this.conexionBD.listarSolicitudesRecibidas(nombreUsuarioRemitente),
                    this.contrasenhasClientes.get(nombreUsuarioRemitente));
        }

        return true;
    }

    /**
     * Este metodo remoto permite rechazar una solicitud de amistad,
     * a un usuario que haya recibido la solicitud de otro
     * @param nombreUsuario nombre de usuario del usuario que recibio la solicitud
     * @param contrasenha contrasenha del usuario que recibio la solicitud
     * @param nombreUsuarioRemitente nombre de usuario del usuario que envio la solicitud
     * @return true si la solicitud se ha aceptado correctamente, false en cualquier otro caso
     * @throws java.rmi.RemoteException
     */
    public synchronized boolean rechazarSolicitudAmistad(String nombreUsuario, String contrasenha, String nombreUsuarioRemitente)
            throws java.rmi.RemoteException{
        if(!this.conexionBD.validarUsuario(nombreUsuario, contrasenha)){
            return false;
        }

        if(!this.conexionBD.rechazarSolicitudAmistad(nombreUsuarioRemitente, nombreUsuario)){
            return false;
        }

        // Si el usuario al que aceptamos la solicitud esta conectado, actualizamos sus listas de amigos conectados
        if(this.clientesConectados.containsKey(nombreUsuarioRemitente)){
            P2PClientInterface usuarioRemitente = this.clientesConectados.get(nombreUsuarioRemitente);
            usuarioRemitente.notifyActualizarListados(this.conexionBD.listarAmigos(nombreUsuarioRemitente),
                    this.conexionBD.listarSolicitudesEnviadas(nombreUsuarioRemitente),
                    this.conexionBD.listarSolicitudesRecibidas(nombreUsuarioRemitente),
                    this.contrasenhasClientes.get(nombreUsuarioRemitente));
        }

        return true;
    }

    /**
     * Este metodo remoto permite a un usuario cancelar
     * o eliminar su amistad con otro usuario
     * @param nombreUsuario nombre de usuario del usuario que desea cancelar su amistad con otro usuario
     * @param contrasenha contrasenha del usuario que desea cancelar su amistad con otro usuario
     * @param nombreUsuarioAmigo nombre de usuario con el usuario que se quiere romper la amistad
     * @return true si se ha podido eliminar la amistad correctamente, false en cualquier otro caso
     * @throws java.rmi.RemoteException
     */
    public synchronized boolean eliminarAmistad(String nombreUsuario, String contrasenha, String nombreUsuarioAmigo)
            throws java.rmi.RemoteException{
        if(!this.conexionBD.validarUsuario(nombreUsuario, contrasenha)){
            return false;
        }

        if(!this.conexionBD.eliminarAmistad(nombreUsuario, nombreUsuarioAmigo)){
            return false;
        }

        P2PClientInterface usuario1 = this.clientesConectados.get(nombreUsuario);
        usuario1.notifyDesconexion(nombreUsuarioAmigo, this.contrasenhasClientes.get(nombreUsuario));

        if(this.clientesConectados.containsKey(nombreUsuarioAmigo)){
            P2PClientInterface usuario2 = this.clientesConectados.get(nombreUsuarioAmigo);
            usuario2.notifyDesconexion(nombreUsuario, this.contrasenhasClientes.get(nombreUsuarioAmigo));
            usuario2.notifyActualizarListados(this.conexionBD.listarAmigos(nombreUsuarioAmigo),
                    this.conexionBD.listarSolicitudesEnviadas(nombreUsuarioAmigo),
                    this.conexionBD.listarSolicitudesRecibidas(nombreUsuarioAmigo),
                    this.contrasenhasClientes.get(nombreUsuarioAmigo));
        }

        return true;
    }

    /**
     * Este metodo remoto permite cancelar una solicitud
     * de amistad que un usuario haya enviado a otro
     * @param nombreUsuario nombre de usuario del usuario que quiere cancelar la solicitud
     * @param contrasenha contrasenha del usuario que quiere cancelar la solicitud
     * @param nombreUsuarioReceptor nombre de usuario del usuario que recibio la solicitud
     * @return true si la solicitud se ha aceptado correctamente, false en cualquier otro caso
     * @throws java.rmi.RemoteException
     */
    public synchronized boolean cancelarSolicitudAmistad(String nombreUsuario, String contrasenha, String nombreUsuarioReceptor)
            throws java.rmi.RemoteException{
        if(!this.conexionBD.validarUsuario(nombreUsuario, contrasenha)){
            return false;
        }

        if(!this.conexionBD.rechazarSolicitudAmistad(nombreUsuario, nombreUsuarioReceptor)){
            return false;
        }


        if(this.clientesConectados.containsKey(nombreUsuarioReceptor)){
            P2PClientInterface usuarioReceptor = this.clientesConectados.get(nombreUsuarioReceptor);
            usuarioReceptor.notifyActualizarListados(this.conexionBD.listarAmigos(nombreUsuarioReceptor),
                    this.conexionBD.listarSolicitudesEnviadas(nombreUsuarioReceptor),
                    this.conexionBD.listarSolicitudesRecibidas(nombreUsuarioReceptor),
                    this.contrasenhasClientes.get(nombreUsuarioReceptor));
        }

        return true;
    }

    /**
     * Este metodo remoto permite registrar a un usuario
     * @param nombreUsuario nombre de usuario del nuevo usuario
     * @param contrasenha contrasenha del nuevo usuario
     * @return true si se ha añadido correctamente, false en otro caso
     * @throws java.rmi.RemoteException
     */
    public synchronized boolean registrarUsuario(String nombreUsuario, String contrasenha)
            throws java.rmi.RemoteException{
        return this.conexionBD.registrarUsuario(nombreUsuario, contrasenha);
    }

    /**
     * Este metodo genera una secuencia de caracteres aleatoria (contrasenha o token)
     * @param longitud tamanho del token
     * @return string que representa el token
     */
    private String generarToken(int longitud) {
        // Definimos los caracteres que puede utilizar la contrasenha
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        // Generador seguro de numeros aleatorios
        SecureRandom random = new SecureRandom();

        StringBuilder token = new StringBuilder();
        for (int i = 0; i < longitud; i++) {
            int indiceCaracter = random.nextInt(caracteres.length());
            char caracterAleatorio = caracteres.charAt(indiceCaracter);
            token.append(caracterAleatorio);
        }

        return token.toString();
    }

    /**
     * Metodo local para cerrar la conexion con la base de datos antes de salir
     */
    public void cerrarConexionBD(){
        this.conexionBD.cerrarConexion();
    }



}
