package programaCliente.cliente;

import programaCliente.gui.VChats;
import programaServidor.servidor.P2PServerInterface;
import java.rmi.*;
import java.rmi.server.*;
import java.util.HashMap;
import java.util.Set;

/**
 * Esta clase implementa la interfaz remota P2PClientInterface
 * @author pablo garcia fuentes
 * @author adrian martinez balea
 */

public class P2PClientImpl extends UnicastRemoteObject
        implements P2PClientInterface {

    // Referencia al objeto servidor
    private P2PServerInterface objetoServidor;
    // Nombre de usuario
    private String nombreUsuario;
    // Contrasenha
    private String contrasenha;
    // HashMap con los amigos de este cliente que estan conectados (nombre de usuario - referencia del objeto)
    private HashMap<String, P2PClientInterface> amigosConectados;
    // HashMap con los tokens para cada amigo conectado (nombre de usuario - token)
    private HashMap<String, String> tokensAmigos;
    // Ventana de chats necesaria para actualizar los chats con los metodos notify
    private VChats vChats;

    /**
     * Constructor de la clase P2PClientImpl
     * @param objetoServidor Servidor al que se conecta el cliente
     * @param nombreUsuario Nombre de usuario del cliente
     * @param contrasenha Contrasenha del cliente
     * @param vChats Referencia a la ventana de chats del cliente
     * @throws RemoteException
     */
    public P2PClientImpl(P2PServerInterface objetoServidor, String nombreUsuario, String contrasenha, VChats vChats) throws RemoteException {
        super();

        this.objetoServidor = objetoServidor;
        this.nombreUsuario = nombreUsuario;
        this.contrasenha = contrasenha;
        this.vChats = vChats;
        this.amigosConectados = new HashMap<>();
        this.tokensAmigos = new HashMap<>();
    }

    /**
     * Metodo que utiliza el servidor para notificar al cliente
     * de que se ha conectado uno de sus amigos
     * @param nombreUsuario nombre de usuario del amigo que se ha conectado
     * @param objetoCliente interfaz cliente del amigo que se ha conectado
     * @param miContrasenha contrasenha del usuario que recibe la notificacion para verificar que proviene del servidor
     * @param token token entre el cliente y el amigo conectado asignado por el servidor
     * @throws RemoteException
     */
    public void notifyConexion(String nombreUsuario, P2PClientInterface objetoCliente, String miContrasenha, String token)
            throws RemoteException{
        // Comprueba que es el servidor
        if(!miContrasenha.equals(this.contrasenha)){
            return;
        }

        this.amigosConectados.put(nombreUsuario, objetoCliente);
        this.tokensAmigos.put(nombreUsuario, token);

        // Mostramos el amigo en la ventana
        this.vChats.mostrarUsuarioConectado(nombreUsuario);
    }

    /**
     * Metodo que utiliza el servidor para notificar al cliente
     * de que se ha desconectado uno de sus amigos
     * @param nombreUsuario nombre de usuario del amigo que se ha desconectado
     * @param miContrasenha contrasenha del usuario que recibe la notificacion para verificar que proviene del servidor
     * @throws RemoteException
     */
    public void notifyDesconexion(String nombreUsuario, String miContrasenha) throws RemoteException{
        // Comprueba que es el servidor
        if(!miContrasenha.equals(this.contrasenha)){
            return;
        }

        this.amigosConectados.remove(nombreUsuario);
        this.tokensAmigos.remove(nombreUsuario);

        // Ocultamos el amigo en la ventana
        this.vChats.ocultarUsuarioDesconectado(nombreUsuario);
    }

    /**
     * Metodo que actualiza la lista de amigos y solicitudes
     * de un cliente, sin necesidad de abrir y cerrar la ventana
     * @param amigos lista de amigos del cliente
     * @param solEnviadas lista de solicitudes enviadas del cliente
     * @param solRecibidas lista de solicitudes recibidas del cliente
     * @param miContrasenha contrasenha del usuario que recibe la notificacion para verificar que proviene del servidor
     * @throws RemoteException
     */
    public void notifyActualizarListados(Set<String> amigos, Set<String> solEnviadas, Set<String> solRecibidas,
                                         String miContrasenha) throws RemoteException{
        // Comprueba que es el servidor
        if(!miContrasenha.equals(this.contrasenha)){
            return;
        }

        this.vChats.notifyActualizarListados(amigos, solEnviadas, solRecibidas);
    }

    /**
     * Metodo que utiliza un cliente para recibir un mensaje de otro cliente
     * @param nombreUsuario nombre del usuario que envio el mensaje
     * @param mensaje mensaje enviado por el usuario
     * @param token token del amigo que envia el mensaje para verificar su identidad
     * @throws RemoteException
     */
    public void recibirMensaje(String nombreUsuario, String mensaje, String token) throws RemoteException{

        // Se verifica la identidad del cliente
        if(this.tokensAmigos.containsKey(nombreUsuario) && this.tokensAmigos.get(nombreUsuario).equals(token)) {
            System.out.println("Mensaje recibido del usuario " + nombreUsuario + ": ");
            System.out.println(mensaje);

            // Mostramos el mensaje en la ventana
            this.vChats.mostrarMensajeUsuario(nombreUsuario, mensaje);
        }

    }

    /**
     * Metodo local que utiliza un cliente para enviar un mensaje a otro cliente
     * @param nombreUsuarioRemitente nombre del usuario que envia el mensaje
     * @param nombreUsuarioReceptor nombre del usuario del destinatario del mensaje
     * @param mensaje mensaje que envia el usuario
     * @throws RemoteException
     */
    public boolean enviarMensaje(String nombreUsuarioRemitente, String nombreUsuarioReceptor, String mensaje)
            throws RemoteException{
        if(!this.amigosConectados.containsKey(nombreUsuarioReceptor) || !this.tokensAmigos.containsKey(nombreUsuarioReceptor)){
            return false;
        }
        try{
            P2PClientInterface usuarioReceptor = this.amigosConectados.get(nombreUsuarioReceptor);
            usuarioReceptor.recibirMensaje(nombreUsuarioRemitente, mensaje, this.tokensAmigos.get(nombreUsuarioReceptor));
        }catch (RemoteException ex){
            return false;
        }

        return true;
    }

    /**
     * Metodo local que delvuelve los amigos conectados del usuario
     * Se emplea solo para el programa por terminal, ya que
     * no hay una ventana vChats que lleve seguimiento de esto
     * @return nombres de usuario de los amigos conectados
     */
    public Set<String> getAmigosConectados(){
        return this.amigosConectados.keySet();
    }



}
