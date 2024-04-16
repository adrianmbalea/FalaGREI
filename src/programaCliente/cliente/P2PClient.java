package programaCliente.cliente;

import programaServidor.servidor.P2PServerInterface;
import java.rmi.*;

// Necesario para el programa con GUI
import programaCliente.gui.VAutenticacion;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;

// Necesarios para el programa por terminal
import programaCliente.gui.VChats;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Set;

/**
 * Esta clase representa el cliente p2p de un objeto distribuido P2PServerInterface
 * @author pablo garcia fuentes
 * @author adrian martinez balea
 */

public class P2PClient {
    public static void main(String args[]) {

        try {
            // Variables de host y puerto
            String hostName = "localhost";
            String portNum = "1099";

            // Creamos una URL para acceder al registro
            String registryURL =
                    "rmi://" + hostName + ":" + portNum + "/p2p";

            // Buscamos el objeto remoto y lo casteamos al tipo interfaz
            P2PServerInterface remoteObject =
                    (P2PServerInterface) Naming.lookup(registryURL);


            /* EJECUCION DEL PROGRAMA CON INTERFAZ GRAFICA */

            // Designamos FlatLaf como UI manager
            // Esto cambia el Look and Feel de las ventanas
            try {
                UIManager.setLookAndFeel(new FlatLightLaf());
            } catch (Exception ex) {
                System.err.println("Failed to initialize LaF");
            }

            // Crea la ventana de autenticacion y la inicia
            // Desde esta ventana se crea el objeto cliente con las credenciales introducidas
            VAutenticacion va = new VAutenticacion(remoteObject);
            va.startWindow();

            /* FIN DEL PROGRAMA CON INTERFAZ GRAFICA */



            /* EJECUCION DEL PROGRAMA POR TERMINAL */

            /*
            // Se registra con un nombre de usuario
            InputStreamReader is = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(is);
            System.out.print("Nombre de usuario: ");
            String username = br.readLine();
            System.out.print("Contrasena: ");
            String passwd = br.readLine();

            if(!remoteObject.validarUsuario(username, passwd)){
                System.out.println("Incorrect login");
                return;
            }

            // Crea una instancia de P2PClientInterface
            P2PClientImpl clienteImplementacion =
                    new P2PClientImpl(remoteObject, username, passwd, new VChats(remoteObject, username, passwd));

            P2PClientInterface clienteInterfaz = (P2PClientInterface) clienteImplementacion;

            // Registra el cliente en el servidor para recibir la lista de conectados
            remoteObject.conectarse(username, passwd, clienteInterfaz);

            boolean conectado = true;
            while(true){
                if(conectado){
                    System.out.println("-------------------------------------------------------------------");
                    System.out.println("1 - Enviar mensaje");
                    System.out.println("2 - Listar amigos");
                    System.out.println("3 - Listar amigos conectados");
                    System.out.println("4 - Enviar solicitud de amistad");
                    System.out.println("5 - Listar solicitudes de amistad enviadas");
                    System.out.println("6 - Cancelar solicitud de amistad enviada");
                    System.out.println("7 - Listar solicitudes de amistad recibidas");
                    System.out.println("8 - Aceptar solicitud de amistad");
                    System.out.println("9 - Rechazar solicitud de amistad");
                    System.out.println("10 - Eliminar amigo");
                    System.out.println("11 - Desconectarse");
                    System.out.println("12 - Salir");
                    System.out.print("Seleccione una opcion: ");
                    String inputText = br.readLine();
                    int input;
                    try{
                        input = Integer.parseInt(inputText);
                    }catch(NumberFormatException ex){
                        System.out.println(ex.toString());
                        continue;
                    }
                    switch (input){
                        case 1:
                            System.out.print("Nombre de usuario del receptor: ");
                            String usuarioReceptor = br.readLine();
                            System.out.print("Mensaje a enviar: ");
                            String mensaje = br.readLine();
                            if(clienteImplementacion.enviarMensaje(username, usuarioReceptor, mensaje)){
                                System.out.println("\nMensaje enviado correctamente a "+usuarioReceptor);
                            }else{
                                System.out.println("\nError al enviar el mensaje");
                            }
                            break;
                        case 2:
                            Set<String> amigos = remoteObject.listarAmigos(username, passwd);
                            System.out.print("Amigos: [ ");
                            for(String amigo: amigos){
                                System.out.print(amigo+" ");
                            }
                            System.out.println("]");
                            break;
                        case 3:
                            Set<String> amigosConectados = clienteImplementacion.getAmigosConectados();
                            System.out.print("Amigos conectados: [ ");
                            for(String amigo: amigosConectados){
                                System.out.print(amigo+" ");
                            }
                            System.out.println("]");
                            break;
                        case 4:
                            System.out.print("Nombre de usuario del receptor de la solicitud: ");
                            String usuarioReceptorSolicitud = br.readLine();
                            if(remoteObject.enviarSolicitudAmistad(username, passwd, usuarioReceptorSolicitud)){
                                System.out.println("\nSolicitud enviada correctamente a "+usuarioReceptorSolicitud);
                            }else{
                                System.out.println("\nError al enviar la solicitud");
                            }
                            break;
                        case 5:
                            Set<String> solicitudesEnviadas = remoteObject.listarSolicitudesEnviadas(username, passwd);
                            System.out.print("Solicitudes enviadas: [ ");
                            for(String solicitud: solicitudesEnviadas){
                                System.out.print(solicitud+" ");
                            }
                            System.out.println("]");
                            break;
                        case 6:
                            System.out.print("Nombre de usuario del receptor de la solicitud: ");
                            String usuarioReceptorSolicitudCancelada = br.readLine();
                            if(remoteObject.cancelarSolicitudAmistad(username, passwd, usuarioReceptorSolicitudCancelada)){
                                System.out.println("\nSolicitud de amistad a "+usuarioReceptorSolicitudCancelada+" cancelada correctamente");
                            }else{
                                System.out.println("\nError al cancelar la solicitud");
                            }
                            break;
                        case 7:
                            Set<String> solicitudesRecibidas = remoteObject.listarSolicitudesRecibidas(username, passwd);
                            System.out.print("Solicitudes recibidas: [ ");
                            for(String solicitud: solicitudesRecibidas){
                                System.out.print(solicitud+" ");
                            }
                            System.out.println("]");
                            break;
                        case 8:
                            System.out.print("Nombre de usuario del remitente de la solicitud: ");
                            String usuarioRemitenteSolicitudAceptada = br.readLine();
                            if(remoteObject.aceptarSolicitudAmistad(username, passwd, usuarioRemitenteSolicitudAceptada)){
                                System.out.println("\nSolicitud de amistad de "+usuarioRemitenteSolicitudAceptada+" aceptada");
                            }else{
                                System.out.println("\nError al aceptar la solicitud");
                            }
                            break;
                        case 9:
                            System.out.print("Nombre de usuario del remitente de la solicitud: ");
                            String usuarioRemitenteSolicitudRechazada = br.readLine();
                            if(remoteObject.rechazarSolicitudAmistad(username, passwd, usuarioRemitenteSolicitudRechazada)){
                                System.out.println("\nSolicitud de amistad de "+usuarioRemitenteSolicitudRechazada+" rechazada");
                            }else{
                                System.out.println("\nError al rechazar la solicitud");
                            }
                            break;
                        case 10:
                            System.out.print("Nombre de usuario del amigo a eliminar: ");
                            String amigoEliminar = br.readLine();
                            if(remoteObject.eliminarAmistad(username, passwd, amigoEliminar)){
                                System.out.println("\n"+amigoEliminar+" eliminado correctamente de tus amigos");
                            }else{
                                System.out.println("\nNo se ha podido eliminar a "+amigoEliminar+ " como amigo");
                            }
                            break;
                        case 11:
                            remoteObject.desconectarse(username, passwd, clienteInterfaz);
                            conectado = false;
                            break;
                        case 12:
                            remoteObject.desconectarse(username, passwd, clienteInterfaz);
                            System.exit(0);
                        default:
                            System.out.println("Opcion incorrecta");
                    }
                }else{
                    System.out.println("-------------------------------------------------------------------");
                    System.out.print("Pulse 1 para conectarse de nuevo: ");
                    String inputText = br.readLine();
                    int input;
                    try{
                        input = Integer.parseInt(inputText);
                    }catch(NumberFormatException ex){
                        System.out.println(ex.toString());
                        continue;
                    }
                    if(input==1){
                        remoteObject.conectarse(username, passwd, clienteInterfaz);
                        conectado=true;
                    }
                }
            }
            */
            /* FIN EJECUCION DEL PROGRAMA POR TERMINAL */

        }catch (Exception e) {
            System.out.println(
                    "Exception in P2PClient: " + e);
        }

    }



}