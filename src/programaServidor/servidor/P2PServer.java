package programaServidor.servidor;

import java.rmi.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

/**
 * Esta clase representa el servidor de objetos para un objeto
 * distribuido de una aplicacion de mensajeria P2P
 * @author pablo garcia fuentes
 * @author adrian martinez balea
 */

public class P2PServer {

    public static void main(String args[]) {

        try {
            String portNum = "1099";
            int RMIPortNum = Integer.parseInt(portNum);

            // Inicia directamente el registro en el puerto especificado
            startRegistry(RMIPortNum);

            // Crea un objeto para invocar los metodos remotos y lo vincula al registro RMI
            P2PServerImpl exportedObj =
                    new P2PServerImpl();
            String registryURL =
                    "rmi://localhost:" + portNum + "/p2p";
            Naming.rebind(registryURL, exportedObj);
            System.out.println("P2P Server ready.");

            // Si el servidor termina abruptamente se cierra la conexion con la base de datos
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                exportedObj.cerrarConexionBD();
            }));
        }
        catch (Exception re) {
            System.out.println(
                    "Exception in P2PServer.main: " + re);
        }
    }

    /**
     * Este metodo inicia un registro RMI en localhost, si
     * no existe en el numero de puerto especificado
     * @param RMIPortNum numero del puerto
     * @throws RemoteException
     */
    private static void startRegistry(int RMIPortNum)
            throws RemoteException {
        try {
            Registry registry =
                    LocateRegistry.getRegistry(RMIPortNum);
            registry.list();
            // Esta llamada devuelve una excepcion si el registro no existe
        } catch (RemoteException e) {
            // No hay un registro valido en ese puerto
            Registry registry =
                    LocateRegistry.createRegistry(RMIPortNum);
        }
    }

}
