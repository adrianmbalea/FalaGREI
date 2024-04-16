package programaCliente.gui;

import javax.swing.event.*;
import programaCliente.cliente.P2PClientImpl;
import programaCliente.cliente.P2PClientInterface;
import programaServidor.servidor.P2PServerInterface;
import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.util.Set;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.*;

/**
 * Esta clase representa la ventana de listado de amigos y gestion de solicitudes
 * @author pablo garcia fuentes
 * @author adrian martinez balea
 */

public class VAmigos extends JFrame{

    /**
     * Constructor
     * @param servidorP2P servidor remoto
     * @param nombreUsuario nombre de usuario del cliente
     * @param contrasenha contrasenha del cliente
     */
    public VAmigos(P2PServerInterface servidorP2P, VChats vChats, String nombreUsuario, String contrasenha) {

        // Asignamos servidor
        this.servidorP2P = servidorP2P;

        // Asignamos la ventana de chats
        this.vChats = vChats;

        // Asignamos nombre de usuario y contrasenha
        this.nombreUsuario = nombreUsuario;
        this.contrasenha = contrasenha;

        initComponents();

        // Creamos los modelos de las listas
        this.listModelAmigos = new DefaultListModel<>();
        this.listModelSolEnviadas = new DefaultListModel<>();
        this.listModelSolRecibidas = new DefaultListModel<>();

        // Asignamos los modelos de las listas
        this.listAmigos.setModel(this.listModelAmigos);
        this.listSolEnviadas.setModel(this.listModelSolEnviadas);
        this.listSolRecibidas.setModel(this.listModelSolRecibidas);

    }

    /**
     * Este listener elimina un amigo si se ha seleccionado algun elemento de la lista
     * @param e ActionEvent
     */
    private void eliminarAmigo(ActionEvent e) {
        // Primero comprobamos que haya algun elemento seleccionado
        int indiceSeleccionado = this.listAmigos.getSelectedIndex();
        if (indiceSeleccionado != -1) {
            // Si se ha seleccionado un usuario se realiza la accion correspondiente
            String usuarioSeleccionado = (String) this.listAmigos.getSelectedValue();

            // Antes de eliminar el usuario seleccionado se muestra un dialogo de confirmacion
            int opcion = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres eliminar a " +
                    usuarioSeleccionado + " de tu lista de amigos?", "Confirmación", JOptionPane.YES_NO_OPTION);

            // Se comprueba la opcion seleccionada
            if (opcion == JOptionPane.YES_OPTION) {

                try {
                    if(servidorP2P.eliminarAmistad(this.nombreUsuario, this.contrasenha, usuarioSeleccionado)){
                        JOptionPane.showMessageDialog(null, usuarioSeleccionado + " ya no está en tu lista de amigos",
                                "Aviso", JOptionPane.INFORMATION_MESSAGE);

                        // Actualizamos los listados
                        actualizarListados();
                        // Actualizamos los chats
                        vChats.actualizarChats();
                    }else{
                        JOptionPane.showMessageDialog(null, "No se ha podido eliminar a " + usuarioSeleccionado + " de tu lista de amigos",
                                "Aviso", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }

            }

        } else {
            JOptionPane.showMessageDialog(null, "No has seleccionado ningún usuario", "Aviso", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Este listener elimina una solicitud enviada si se ha seleccionado algun elemento de la lista
     * @param e ActionEvent
     */
    private void eliminarSolEnviada(ActionEvent e) {
        // Primero comprobamos que haya algun elemento seleccionado
        int indiceSeleccionado = this.listSolEnviadas.getSelectedIndex();
        if (indiceSeleccionado != -1) {
            // Si se ha seleccionado una solicitud se realiza la accion correspondiente
            String solicitudSeleccionada = (String) this.listSolEnviadas.getSelectedValue();

            // Antes de cancelar la solicitud se muestra un dialogo de confirmacion
            int opcion = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres cancelar " +
                    "la solicitud de amistad a " + solicitudSeleccionada + "?", "Confirmación", JOptionPane.YES_NO_OPTION);

            // Se comprueba la opcion seleccionada
            if (opcion == JOptionPane.YES_OPTION) {

                try {
                    if(servidorP2P.cancelarSolicitudAmistad(this.nombreUsuario, this.contrasenha, solicitudSeleccionada)){
                        JOptionPane.showMessageDialog(null, "La solicitud a " + solicitudSeleccionada + " se ha cancelado",
                                "Aviso", JOptionPane.INFORMATION_MESSAGE);

                        // Actualizamos los listados
                        actualizarListados();
                    }else{
                        JOptionPane.showMessageDialog(null, "No se ha podido cancelar la solicitud enviada a " + solicitudSeleccionada,
                                "Aviso", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }

            }

        } else {
            JOptionPane.showMessageDialog(null, "No has seleccionado ninguna solicitud", "Aviso", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Este listener acepta una solicitud recibida si se ha seleccionado algun elemento de la lista
     * @param e ActionEvent
     */
    private void aceptarSolicitud(ActionEvent e) {
        // Primero comprobamos que haya algun elemento seleccionado
        int indiceSeleccionado = this.listSolRecibidas.getSelectedIndex();
        if (indiceSeleccionado != -1) {
            // Si se ha seleccionado una solicitud se realiza la accion correspondiente
            String solicitudSeleccionada = (String) this.listSolRecibidas.getSelectedValue();

            // Antes de aceptar la solicitud se muestra un dialogo de confirmacion
            int opcion = JOptionPane.showConfirmDialog(null, "¿Quieres aceptar " +
                    "la solicitud de amistad de " + solicitudSeleccionada + "?" , "Confirmación", JOptionPane.YES_NO_OPTION);

            // Se comprueba la opcion seleccionada
            if (opcion == JOptionPane.YES_OPTION) {

                try {
                    if(servidorP2P.aceptarSolicitudAmistad(this.nombreUsuario, this.contrasenha, solicitudSeleccionada)){
                        JOptionPane.showMessageDialog(null, "La solicitud de " + solicitudSeleccionada + " ha sido aceptada",
                                "Aviso", JOptionPane.INFORMATION_MESSAGE);

                        // Actualizamos los listados
                        actualizarListados();
                        // Actualizamos los chats
                        vChats.actualizarChats();
                    }else{
                        JOptionPane.showMessageDialog(null, "No se ha podido aceptar la solicitud de " + solicitudSeleccionada,
                                "Aviso", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }

            }

        } else {
            JOptionPane.showMessageDialog(null, "No has seleccionado ninguna solicitud", "Aviso", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Este listener rechaza una solicitud recibida si se ha seleccionado algun elemento de la lista
     * @param e ActionEvent
     */
    private void rechazarSolicitud(ActionEvent e) {
        // Primero comprobamos que haya algun elemento seleccionado
        int indiceSeleccionado = this.listSolRecibidas.getSelectedIndex();
        if (indiceSeleccionado != -1) {
            // Si se ha seleccionado una solicitud se realiza la accion correspondiente
            String solicitudSeleccionada = (String) this.listSolRecibidas.getSelectedValue();

            // Antes de rechazar la solicitud se muestra un dialogo de confirmacion
            int opcion = JOptionPane.showConfirmDialog(null, "¿Quieres rechazar " +
                    "la solicitud de amistad de " + solicitudSeleccionada + "?" , "Confirmación", JOptionPane.YES_NO_OPTION);

            // Se comprueba la opcion seleccionada
            if (opcion == JOptionPane.YES_OPTION) {

                try {
                    if(servidorP2P.rechazarSolicitudAmistad(this.nombreUsuario, this.contrasenha, solicitudSeleccionada)){
                        JOptionPane.showMessageDialog(null, "La solicitud de " + solicitudSeleccionada + " ha sido rechazada",
                                "Aviso", JOptionPane.INFORMATION_MESSAGE);

                        // Actualizamos los listados
                        actualizarListados();
                    }else{
                        JOptionPane.showMessageDialog(null, "No se ha podido rechazar la solicitud de " + solicitudSeleccionada,
                                "Aviso", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }

            }

        } else {
            JOptionPane.showMessageDialog(null, "No has seleccionado ninguna solicitud", "Aviso", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Este listener envia una solicitud mostrando un cuadro de dialogo
     * @param e ActionEvent
     */
    private void nuevaSolicitud(ActionEvent e) {

        // Campo en el que el usuario introduce el nombre
        JTextField textoSolicitud = new JTextField();

        // Mensaje del cuadro de dialogo
        Object[] message = {
                "Ingrese el nombre del usuario:", textoSolicitud
        };

        // Mostramos el cuadro de dialogo
        int opcion = JOptionPane.showConfirmDialog(null, message, "Solicitud de amistad",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        // Se comprueba la opcion seleccionada
        if (opcion == JOptionPane.OK_OPTION) {

            String nombreSolicitud = textoSolicitud.getText();
            if(nombreSolicitud.isEmpty() || nombreSolicitud.isBlank()){
                JOptionPane.showMessageDialog(null, "El nombre de la solicitud no es válido", "Aviso", JOptionPane.ERROR_MESSAGE);
            }else{

                try {
                    if(servidorP2P.enviarSolicitudAmistad(this.nombreUsuario, this.contrasenha, nombreSolicitud)){
                        JOptionPane.showMessageDialog(null, "La solicitud a " + nombreSolicitud + " ha sido enviada",
                                "Aviso", JOptionPane.INFORMATION_MESSAGE);

                        // Actualizamos los listados
                        actualizarListados();
                    }else{
                        JOptionPane.showMessageDialog(null, "No se ha podido enviar la solicitud a " + nombreSolicitud,
                                "Aviso", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }

            }

        }

    }

    /**
     * Funcion autogenerada por JFormDesigner que inicia los componentes de la ventana
     */
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Pablo
        mainFrame = new JFrame();
        mainPanel = new JPanel();
        tabbedPaneListaAmigos = new JTabbedPane();
        panelAmigos = new JPanel();
        scrollPaneAmigos = new JScrollPane();
        listAmigos = new JList();
        buttonEliminarAmigo = new JButton();
        panelSolEnviadas = new JPanel();
        scrollPaneSolEnviadas = new JScrollPane();
        listSolEnviadas = new JList();
        buttonEliminarSolEnviada = new JButton();
        buttonNuevaSolicitud = new JButton();
        panelSolRecibidas = new JPanel();
        scrollPaneSolRecibidas = new JScrollPane();
        listSolRecibidas = new JList();
        buttonAceptarSolicitud = new JButton();
        buttonRechazarSolicitud = new JButton();
        panelIzquierdo = new JPanel();
        labelGestionAmigos = new JLabel();
        label1 = new JLabel();

        //======== mainFrame ========
        {
            mainFrame.setResizable(false);
            var mainFrameContentPane = mainFrame.getContentPane();

            //======== mainPanel ========
            {
                mainPanel.setBackground(Color.white);
                mainPanel.setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax. swing. border.
                EmptyBorder( 0, 0, 0, 0) , "", javax. swing. border. TitledBorder. CENTER, javax. swing
                . border. TitledBorder. BOTTOM, new java .awt .Font ("D\u0069al\u006fg" ,java .awt .Font .BOLD ,12 ),
                java. awt. Color. red) ,mainPanel. getBorder( )) ); mainPanel. addPropertyChangeListener (new java. beans. PropertyChangeListener( )
                { @Override public void propertyChange (java .beans .PropertyChangeEvent e) {if ("\u0062or\u0064er" .equals (e .getPropertyName () ))
                throw new RuntimeException( ); }} );

                //======== tabbedPaneListaAmigos ========
                {
                    tabbedPaneListaAmigos.setFont(new Font("sansserif", Font.PLAIN, 20));
                    tabbedPaneListaAmigos.setForeground(new Color(0x23a661));
                    tabbedPaneListaAmigos.setBorder(null);
                    tabbedPaneListaAmigos.setBackground(Color.white);

                    //======== panelAmigos ========
                    {
                        panelAmigos.setBackground(Color.white);

                        //======== scrollPaneAmigos ========
                        {
                            scrollPaneAmigos.setFont(new Font("sansserif", Font.PLAIN, 20));

                            //---- listAmigos ----
                            listAmigos.setFont(new Font("sansserif", Font.PLAIN, 20));
                            listAmigos.setBackground(Color.white);
                            scrollPaneAmigos.setViewportView(listAmigos);
                        }

                        //---- buttonEliminarAmigo ----
                        buttonEliminarAmigo.setText("Eliminar amigo");
                        buttonEliminarAmigo.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 17));
                        buttonEliminarAmigo.setBackground(Color.white);
                        buttonEliminarAmigo.setForeground(new Color(0x07a479));
                        buttonEliminarAmigo.setContentAreaFilled(false);
                        buttonEliminarAmigo.setBorder(new EtchedBorder());
                        buttonEliminarAmigo.addActionListener(e -> eliminarAmigo(e));

                        GroupLayout panelAmigosLayout = new GroupLayout(panelAmigos);
                        panelAmigos.setLayout(panelAmigosLayout);
                        panelAmigosLayout.setHorizontalGroup(
                            panelAmigosLayout.createParallelGroup()
                                .addComponent(scrollPaneAmigos, GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE)
                                .addGroup(panelAmigosLayout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(buttonEliminarAmigo, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                                    .addContainerGap(206, Short.MAX_VALUE))
                        );
                        panelAmigosLayout.setVerticalGroup(
                            panelAmigosLayout.createParallelGroup()
                                .addGroup(panelAmigosLayout.createSequentialGroup()
                                    .addComponent(scrollPaneAmigos, GroupLayout.PREFERRED_SIZE, 260, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(buttonEliminarAmigo, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 28, Short.MAX_VALUE))
                        );
                    }
                    tabbedPaneListaAmigos.addTab("Amigos", panelAmigos);

                    //======== panelSolEnviadas ========
                    {
                        panelSolEnviadas.setBackground(Color.white);

                        //======== scrollPaneSolEnviadas ========
                        {
                            scrollPaneSolEnviadas.setFont(new Font("sansserif", Font.PLAIN, 20));

                            //---- listSolEnviadas ----
                            listSolEnviadas.setFont(new Font("sansserif", Font.PLAIN, 20));
                            listSolEnviadas.setBackground(Color.white);
                            scrollPaneSolEnviadas.setViewportView(listSolEnviadas);
                        }

                        //---- buttonEliminarSolEnviada ----
                        buttonEliminarSolEnviada.setText("Cancelar solicitud");
                        buttonEliminarSolEnviada.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 17));
                        buttonEliminarSolEnviada.setBackground(Color.white);
                        buttonEliminarSolEnviada.setForeground(new Color(0x07a479));
                        buttonEliminarSolEnviada.setContentAreaFilled(false);
                        buttonEliminarSolEnviada.setBorder(new EtchedBorder());
                        buttonEliminarSolEnviada.addActionListener(e -> eliminarSolEnviada(e));

                        //---- buttonNuevaSolicitud ----
                        buttonNuevaSolicitud.setText("Nueva solicitud");
                        buttonNuevaSolicitud.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 17));
                        buttonNuevaSolicitud.setBackground(Color.white);
                        buttonNuevaSolicitud.setForeground(new Color(0x07a479));
                        buttonNuevaSolicitud.setContentAreaFilled(false);
                        buttonNuevaSolicitud.setBorder(new EtchedBorder());
                        buttonNuevaSolicitud.addActionListener(e -> nuevaSolicitud(e));

                        GroupLayout panelSolEnviadasLayout = new GroupLayout(panelSolEnviadas);
                        panelSolEnviadas.setLayout(panelSolEnviadasLayout);
                        panelSolEnviadasLayout.setHorizontalGroup(
                            panelSolEnviadasLayout.createParallelGroup()
                                .addComponent(scrollPaneSolEnviadas, GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE)
                                .addGroup(panelSolEnviadasLayout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(buttonEliminarSolEnviada, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(buttonNuevaSolicitud, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                                    .addContainerGap(25, Short.MAX_VALUE))
                        );
                        panelSolEnviadasLayout.setVerticalGroup(
                            panelSolEnviadasLayout.createParallelGroup()
                                .addGroup(panelSolEnviadasLayout.createSequentialGroup()
                                    .addComponent(scrollPaneSolEnviadas, GroupLayout.PREFERRED_SIZE, 260, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(panelSolEnviadasLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(buttonEliminarSolEnviada, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(buttonNuevaSolicitud, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE))
                                    .addGap(0, 28, Short.MAX_VALUE))
                        );
                    }
                    tabbedPaneListaAmigos.addTab("Solicitudes enviadas", panelSolEnviadas);

                    //======== panelSolRecibidas ========
                    {
                        panelSolRecibidas.setBackground(Color.white);

                        //======== scrollPaneSolRecibidas ========
                        {
                            scrollPaneSolRecibidas.setFont(new Font("sansserif", Font.PLAIN, 20));

                            //---- listSolRecibidas ----
                            listSolRecibidas.setFont(new Font("sansserif", Font.PLAIN, 20));
                            listSolRecibidas.setBackground(Color.white);
                            scrollPaneSolRecibidas.setViewportView(listSolRecibidas);
                        }

                        //---- buttonAceptarSolicitud ----
                        buttonAceptarSolicitud.setText("Aceptar solicitud");
                        buttonAceptarSolicitud.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 17));
                        buttonAceptarSolicitud.setBackground(Color.white);
                        buttonAceptarSolicitud.setForeground(new Color(0x07a479));
                        buttonAceptarSolicitud.setContentAreaFilled(false);
                        buttonAceptarSolicitud.setBorder(new EtchedBorder());
                        buttonAceptarSolicitud.addActionListener(e -> aceptarSolicitud(e));

                        //---- buttonRechazarSolicitud ----
                        buttonRechazarSolicitud.setText("Rechazar solicitud");
                        buttonRechazarSolicitud.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 17));
                        buttonRechazarSolicitud.setBackground(Color.white);
                        buttonRechazarSolicitud.setForeground(new Color(0x07a479));
                        buttonRechazarSolicitud.setContentAreaFilled(false);
                        buttonRechazarSolicitud.setBorder(new EtchedBorder());
                        buttonRechazarSolicitud.addActionListener(e -> rechazarSolicitud(e));

                        GroupLayout panelSolRecibidasLayout = new GroupLayout(panelSolRecibidas);
                        panelSolRecibidas.setLayout(panelSolRecibidasLayout);
                        panelSolRecibidasLayout.setHorizontalGroup(
                            panelSolRecibidasLayout.createParallelGroup()
                                .addComponent(scrollPaneSolRecibidas)
                                .addGroup(panelSolRecibidasLayout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(buttonAceptarSolicitud, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(buttonRechazarSolicitud, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
                                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        );
                        panelSolRecibidasLayout.setVerticalGroup(
                            panelSolRecibidasLayout.createParallelGroup()
                                .addGroup(panelSolRecibidasLayout.createSequentialGroup()
                                    .addComponent(scrollPaneSolRecibidas, GroupLayout.PREFERRED_SIZE, 260, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(panelSolRecibidasLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(buttonAceptarSolicitud, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(buttonRechazarSolicitud, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE))
                                    .addGap(0, 28, Short.MAX_VALUE))
                        );
                    }
                    tabbedPaneListaAmigos.addTab("Solicitudes recibidas", panelSolRecibidas);
                }

                //======== panelIzquierdo ========
                {
                    panelIzquierdo.setBackground(new Color(0x23a661));

                    //---- labelGestionAmigos ----
                    labelGestionAmigos.setText("Gesti\u00f3n de amigos");
                    labelGestionAmigos.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
                    labelGestionAmigos.setForeground(Color.white);
                    labelGestionAmigos.setBackground(Color.white);
                    labelGestionAmigos.setHorizontalAlignment(SwingConstants.CENTER);

                    //---- label1 ----
                    label1.setIcon(new ImageIcon(getClass().getResource("/programaCliente/gui/logo.png")));

                    GroupLayout panelIzquierdoLayout = new GroupLayout(panelIzquierdo);
                    panelIzquierdo.setLayout(panelIzquierdoLayout);
                    panelIzquierdoLayout.setHorizontalGroup(
                        panelIzquierdoLayout.createParallelGroup()
                            .addGroup(panelIzquierdoLayout.createSequentialGroup()
                                .addGroup(panelIzquierdoLayout.createParallelGroup()
                                    .addGroup(panelIzquierdoLayout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(labelGestionAmigos, GroupLayout.PREFERRED_SIZE, 287, GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelIzquierdoLayout.createSequentialGroup()
                                        .addGap(28, 28, 28)
                                        .addComponent(label1)))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    );
                    panelIzquierdoLayout.setVerticalGroup(
                        panelIzquierdoLayout.createParallelGroup()
                            .addGroup(panelIzquierdoLayout.createSequentialGroup()
                                .addGap(55, 55, 55)
                                .addComponent(labelGestionAmigos, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(label1, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    );
                }

                GroupLayout mainPanelLayout = new GroupLayout(mainPanel);
                mainPanel.setLayout(mainPanelLayout);
                mainPanelLayout.setHorizontalGroup(
                    mainPanelLayout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                            .addComponent(panelIzquierdo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(tabbedPaneListaAmigos)
                            .addContainerGap())
                );
                mainPanelLayout.setVerticalGroup(
                    mainPanelLayout.createParallelGroup()
                        .addGroup(mainPanelLayout.createSequentialGroup()
                            .addComponent(tabbedPaneListaAmigos, GroupLayout.PREFERRED_SIZE, 430, GroupLayout.PREFERRED_SIZE)
                            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(panelIzquierdo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );
            }

            GroupLayout mainFrameContentPaneLayout = new GroupLayout(mainFrameContentPane);
            mainFrameContentPane.setLayout(mainFrameContentPaneLayout);
            mainFrameContentPaneLayout.setHorizontalGroup(
                mainFrameContentPaneLayout.createParallelGroup()
                    .addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );
            mainFrameContentPaneLayout.setVerticalGroup(
                mainFrameContentPaneLayout.createParallelGroup()
                    .addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );
            mainFrame.pack();
            mainFrame.setLocationRelativeTo(mainFrame.getOwner());
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Pablo
    private JFrame mainFrame;
    private JPanel mainPanel;
    private JTabbedPane tabbedPaneListaAmigos;
    private JPanel panelAmigos;
    private JScrollPane scrollPaneAmigos;
    private JList listAmigos;
    private JButton buttonEliminarAmigo;
    private JPanel panelSolEnviadas;
    private JScrollPane scrollPaneSolEnviadas;
    private JList listSolEnviadas;
    private JButton buttonEliminarSolEnviada;
    private JButton buttonNuevaSolicitud;
    private JPanel panelSolRecibidas;
    private JScrollPane scrollPaneSolRecibidas;
    private JList listSolRecibidas;
    private JButton buttonAceptarSolicitud;
    private JButton buttonRechazarSolicitud;
    private JPanel panelIzquierdo;
    private JLabel labelGestionAmigos;
    private JLabel label1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    // Cliente P2P
    private P2PClientImpl clienteP2P;
    // Servidor P2P
    private P2PServerInterface servidorP2P;
    // Nombre de usuario del cliente
    private String nombreUsuario;
    // Contrasenha del cliente
    private String contrasenha;
    // Modelos de las listas
    private DefaultListModel<String> listModelAmigos;
    private DefaultListModel<String> listModelSolEnviadas;
    private DefaultListModel<String> listModelSolRecibidas;
    // Ventana de chats correspondiente
    private VChats vChats;

    /**
     * Este metodo inicia la ventana
     * Esta ventana no se muestra hasta que no se indique en VChats
     * @param clienteP2P implementacion del cliente que utiliza la ventana
     */
    public void startWindow(P2PClientImpl clienteP2P) {

        // Asignamos el cliente
        this.clienteP2P = clienteP2P;

        // Anhadimos el panel principal a la ventana
        this.setContentPane(mainPanel);
        this.setSize(this.mainFrame.getSize());
        // Nombramos la ventana
        this.setTitle("Amigos");

        // Anhadimos un WindowAdapter para personalizar la accion de cierre
        // y no salir del programa al cerrar la ventana
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                // Cerramos la ventana sin salir del programa
                VAmigos.this.dispose();

            }
        });

        // Se abre en el centro de la pantalla
        this.setLocationRelativeTo(null);
        // No puede variar su tamanho
        this.setResizable(false);

        // Se actualizan los listados
        actualizarListados();

    }

    /**
     * Este metodo muestra la ventana
     */
    public void showWindow() {

        // Antes de mostrar la ventana se actualizan los listados
        actualizarListados();

        this.setVisible(true);
    }

    /**
     * Este metodo actualiza de forma local:
     * Los amigos disponibles en la lista de amigos
     * Las solicitudes enviadas
     * Las solicitudes recibidas
     */
    private void actualizarListados() {

        try {
            // Se eliminan todos los elementos y se anhaden los nombres de usuario correspondientes
            listModelAmigos.removeAllElements();
            Set<String> amigos = servidorP2P.listarAmigos(this.nombreUsuario, this.contrasenha);
            for (String amigo : amigos) {
                if (!listModelAmigos.contains(amigo)) {
                    listModelAmigos.addElement(amigo);
                }
            }

            listModelSolEnviadas.removeAllElements();
            Set<String> solEnviadas = servidorP2P.listarSolicitudesEnviadas(this.nombreUsuario, this.contrasenha);
            for (String solicitud : solEnviadas) {
                if (!listModelSolEnviadas.contains(solicitud)) {
                    listModelSolEnviadas.addElement(solicitud);
                }
            }

            listModelSolRecibidas.removeAllElements();
            Set<String> solRecibidas = servidorP2P.listarSolicitudesRecibidas(this.nombreUsuario, this.contrasenha);
            for (String solicitud : solRecibidas) {
                if (!listModelSolRecibidas.contains(solicitud)) {
                    listModelSolRecibidas.addElement(solicitud);
                }
            }

            // Actualizamos el numero de notificaciones en el titulo de la pestanha
            int numSolRecibidas = this.getSolicitudesRecibidas();
            if(numSolRecibidas > 0){
                this.tabbedPaneListaAmigos.setTitleAt(this.tabbedPaneListaAmigos.indexOfComponent(this.panelSolRecibidas),
                        "Solicitudes recibidas [" + numSolRecibidas + "]");
            }else{
                this.tabbedPaneListaAmigos.setTitleAt(this.tabbedPaneListaAmigos.indexOfComponent(this.panelSolRecibidas),
                        "Solicitudes recibidas");
            }

        }catch (RemoteException e) {
            throw new RuntimeException("Exception in actualizarListados: " + e);
        }

        // Actualizamos las notificaciones de la ventana de chats
        this.vChats.actualizarNotificacionSolicitudes();

    }

    /**
     * Este metodo actualiza a partir de un notify los siguientes listados:
     * Los amigos disponibles en la lista de amigos
     * Las solicitudes enviadas
     * Las solicitudes recibidas
     * @param amigos lista de amigos del cliente
     * @param solEnviadas lista de solicitudes enviadas del cliente
     * @param solRecibidas lista de solicitudes recibidas del cliente
     */
    public void notifyActualizarListados(Set<String> amigos, Set<String> solEnviadas, Set<String> solRecibidas) {

        // Se eliminan todos los elementos y se anhaden los nombres de usuario correspondientes
        listModelAmigos.removeAllElements();
        for(String amigo: amigos){
            if(!listModelAmigos.contains(amigo)){
                listModelAmigos.addElement(amigo);
            }
        }

        listModelSolEnviadas.removeAllElements();
        for(String solicitud : solEnviadas){
            if(!listModelSolEnviadas.contains(solicitud)){
                listModelSolEnviadas.addElement(solicitud);
            }
        }

        listModelSolRecibidas.removeAllElements();
        for(String solicitud : solRecibidas){
            if(!listModelSolRecibidas.contains(solicitud)){
                listModelSolRecibidas.addElement(solicitud);
            }
        }

        // Actualizamos el numero de notificaciones en el titulo de la pestanha
        int numSolRecibidas = this.getSolicitudesRecibidas();
        if(numSolRecibidas > 0){
            this.tabbedPaneListaAmigos.setTitleAt(this.tabbedPaneListaAmigos.indexOfComponent(this.panelSolRecibidas),
                    "Solicitudes recibidas [" + numSolRecibidas + "]");
        }else{
            this.tabbedPaneListaAmigos.setTitleAt(this.tabbedPaneListaAmigos.indexOfComponent(this.panelSolRecibidas),
                    "Solicitudes recibidas");
        }

        // Actualizamos las notificaciones de la ventana de chats
        this.vChats.actualizarNotificacionSolicitudes();

    }

    /**
     * Getter del numero de solicitudes recibidas
     * @return numero de solicitudes recibidas
     */
    public int getSolicitudesRecibidas(){
        return(this.listModelSolRecibidas.getSize());
    }



}
