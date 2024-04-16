package programaCliente.gui;

import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.*;
import programaCliente.cliente.P2PClientImpl;
import programaCliente.cliente.P2PClientInterface;
import programaCliente.gui.chat.component.*;
import programaCliente.gui.chat.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import programaCliente.gui.chat.component.ChatBox;
import programaCliente.gui.chat.model.ModelMessage;
import programaServidor.servidor.P2PServerInterface;

/**
 * Esta clase representa la ventana de chats del usuario
 * @author pablo garcia fuentes
 * @author adrian martinez balea
 */

public class VChats extends JFrame {

    /**
     * Constructor de la clase VChats
     * @param servidorP2P   servidor remoto
     * @param nombreUsuario nombre de usuario del cliente
     * @param contrasenha   contrasenha del cliente
     */
    public VChats(P2PServerInterface servidorP2P, String nombreUsuario, String contrasenha) {

        // Asignamos servidor
        this.servidorP2P = servidorP2P;
        // Asignamos nombre de usuario y contrasenha
        this.nombreUsuario = nombreUsuario;
        this.contrasenha = contrasenha;

        // Creamos la ventana de amigos
        ventanaAmigos = new VAmigos(servidorP2P, this,  this.nombreUsuario, this.contrasenha);

        // Inicializamos los hashmaps
        panelesAmigos = new HashMap<>();
        chatsAmigos = new HashMap<>();
        notificacionesAmigos = new HashMap<>();

        initComponents();
    }

    /**
     * Listener que abre la ventana de gestion de amigos
     * @param e ActionEvent
     */
    private void agregarAmigo(ActionEvent e) {
        ventanaAmigos.showWindow();
    }

    /**
     * Listener que establece a cero las notificaciones de la pestanha seleccionada
     * @param e ChangeEvent
     */
    private void tabbedPaneChatsStateChanged(ChangeEvent e) {
        
        // Obtenemos el nombre de usuario del chat seleccionado
        String nombreChatSeleccionado = this.getChatSeleccionado();
        if(nombreChatSeleccionado != null && this.notificacionesAmigos.containsKey(nombreChatSeleccionado)) {
            // Cambiamos las notificaciones a cero en el hashmap
            notificacionesAmigos.put(nombreChatSeleccionado, "0");
            // Actualizamos el titulo
            this.actualizarTituloPestana(nombreChatSeleccionado);
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
        tabbedPaneChats = new JTabbedPane();
        panelNoAmigosConectados = new JPanel();
        labelImagenDesierto = new JLabel();
        labelTodoTranquilo = new JLabel();
        labelNoAmigosConectados = new JLabel();
        panelSuperior = new JPanel();
        labelBienvenido = new JLabel();
        buttonAgregarAmigo = new BadgeButton();
        panelInferior = new JPanel();

        //======== mainFrame ========
        {
            mainFrame.setMaximumSize(new Dimension(1000, 700));
            mainFrame.setMinimumSize(new Dimension(1000, 700));
            mainFrame.setPreferredSize(new Dimension(1000, 700));
            mainFrame.setResizable(false);
            var mainFrameContentPane = mainFrame.getContentPane();

            //======== mainPanel ========
            {
                mainPanel.setBackground(Color.white);
                mainPanel.setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder (
                new javax . swing. border .EmptyBorder ( 0, 0 ,0 , 0) ,  ""
                , javax. swing .border . TitledBorder. CENTER ,javax . swing. border .TitledBorder . BOTTOM
                , new java. awt .Font ( "D\u0069al\u006fg", java .awt . Font. BOLD ,12 )
                ,java . awt. Color .red ) ,mainPanel. getBorder () ) ); mainPanel. addPropertyChangeListener(
                new java. beans .PropertyChangeListener ( ){ @Override public void propertyChange (java . beans. PropertyChangeEvent e
                ) { if( "\u0062or\u0064er" .equals ( e. getPropertyName () ) )throw new RuntimeException( )
                ;} } );

                //======== tabbedPaneChats ========
                {
                    tabbedPaneChats.setTabPlacement(SwingConstants.LEFT);
                    tabbedPaneChats.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
                    tabbedPaneChats.setFont(new Font("sansserif", Font.PLAIN, 20));
                    tabbedPaneChats.setForeground(Color.white);
                    tabbedPaneChats.setBorder(null);
                    tabbedPaneChats.addChangeListener(e -> tabbedPaneChatsStateChanged(e));

                    //======== panelNoAmigosConectados ========
                    {
                        panelNoAmigosConectados.setBackground(new Color(0x55d893));
                        panelNoAmigosConectados.setFont(new Font("sansserif", Font.PLAIN, 20));

                        //---- labelImagenDesierto ----
                        labelImagenDesierto.setIcon(new ImageIcon(getClass().getResource("/programaCliente/gui/desierto.png")));

                        //---- labelTodoTranquilo ----
                        labelTodoTranquilo.setText("Todo est\u00e1 tranquilo...");
                        labelTodoTranquilo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
                        labelTodoTranquilo.setForeground(Color.white);
                        labelTodoTranquilo.setBackground(Color.white);
                        labelTodoTranquilo.setHorizontalAlignment(SwingConstants.CENTER);

                        //---- labelNoAmigosConectados ----
                        labelNoAmigosConectados.setText("No hay amigos conectados");
                        labelNoAmigosConectados.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
                        labelNoAmigosConectados.setForeground(Color.white);
                        labelNoAmigosConectados.setBackground(Color.white);
                        labelNoAmigosConectados.setHorizontalAlignment(SwingConstants.CENTER);

                        GroupLayout panelNoAmigosConectadosLayout = new GroupLayout(panelNoAmigosConectados);
                        panelNoAmigosConectados.setLayout(panelNoAmigosConectadosLayout);
                        panelNoAmigosConectadosLayout.setHorizontalGroup(
                            panelNoAmigosConectadosLayout.createParallelGroup()
                                .addGroup(GroupLayout.Alignment.TRAILING, panelNoAmigosConectadosLayout.createSequentialGroup()
                                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(panelNoAmigosConectadosLayout.createParallelGroup()
                                        .addGroup(GroupLayout.Alignment.TRAILING, panelNoAmigosConectadosLayout.createSequentialGroup()
                                            .addComponent(labelImagenDesierto, GroupLayout.PREFERRED_SIZE, 202, GroupLayout.PREFERRED_SIZE)
                                            .addGap(183, 183, 183))
                                        .addGroup(GroupLayout.Alignment.TRAILING, panelNoAmigosConectadosLayout.createSequentialGroup()
                                            .addGroup(panelNoAmigosConectadosLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                .addComponent(labelNoAmigosConectados, GroupLayout.PREFERRED_SIZE, 325, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(labelTodoTranquilo, GroupLayout.PREFERRED_SIZE, 325, GroupLayout.PREFERRED_SIZE))
                                            .addGap(119, 119, 119))))
                        );
                        panelNoAmigosConectadosLayout.setVerticalGroup(
                            panelNoAmigosConectadosLayout.createParallelGroup()
                                .addGroup(panelNoAmigosConectadosLayout.createSequentialGroup()
                                    .addGap(102, 102, 102)
                                    .addComponent(labelImagenDesierto, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(labelTodoTranquilo, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(labelNoAmigosConectados)
                                    .addContainerGap(122, Short.MAX_VALUE))
                        );
                    }
                    tabbedPaneChats.addTab("                                                                              ", panelNoAmigosConectados);
                    tabbedPaneChats.setBackgroundAt(0, new Color(0x23a661));
                }

                //======== panelSuperior ========
                {
                    panelSuperior.setBackground(new Color(0x23a661));
                    panelSuperior.setBorder(null);

                    //---- labelBienvenido ----
                    labelBienvenido.setText("Amigos en l\u00ednea");
                    labelBienvenido.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
                    labelBienvenido.setForeground(Color.white);
                    labelBienvenido.setBackground(Color.white);
                    labelBienvenido.setHorizontalAlignment(SwingConstants.CENTER);

                    //---- buttonAgregarAmigo ----
                    buttonAgregarAmigo.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 17));
                    buttonAgregarAmigo.setBackground(Color.white);
                    buttonAgregarAmigo.setContentAreaFilled(false);
                    buttonAgregarAmigo.setBorder(null);
                    buttonAgregarAmigo.setIcon(new ImageIcon(getClass().getResource("/programaCliente/gui/iconoAgregarUsuario.png")));
                    buttonAgregarAmigo.setBadgeColor(new Color(0x0085ed));
                    buttonAgregarAmigo.setText("0");
                    buttonAgregarAmigo.addActionListener(e -> agregarAmigo(e));

                    GroupLayout panelSuperiorLayout = new GroupLayout(panelSuperior);
                    panelSuperior.setLayout(panelSuperiorLayout);
                    panelSuperiorLayout.setHorizontalGroup(
                        panelSuperiorLayout.createParallelGroup()
                            .addGroup(panelSuperiorLayout.createSequentialGroup()
                                .addGap(59, 59, 59)
                                .addComponent(labelBienvenido, GroupLayout.PREFERRED_SIZE, 287, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 348, Short.MAX_VALUE)
                                .addComponent(buttonAgregarAmigo, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                    );
                    panelSuperiorLayout.setVerticalGroup(
                        panelSuperiorLayout.createParallelGroup()
                            .addGroup(panelSuperiorLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(panelSuperiorLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(labelBienvenido, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(buttonAgregarAmigo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
                    );
                }

                //======== panelInferior ========
                {
                    panelInferior.setBackground(new Color(0x23a661));
                    panelInferior.setBorder(null);

                    GroupLayout panelInferiorLayout = new GroupLayout(panelInferior);
                    panelInferior.setLayout(panelInferiorLayout);
                    panelInferiorLayout.setHorizontalGroup(
                        panelInferiorLayout.createParallelGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                    );
                    panelInferiorLayout.setVerticalGroup(
                        panelInferiorLayout.createParallelGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                    );
                }

                GroupLayout mainPanelLayout = new GroupLayout(mainPanel);
                mainPanel.setLayout(mainPanelLayout);
                mainPanelLayout.setHorizontalGroup(
                    mainPanelLayout.createParallelGroup()
                        .addComponent(tabbedPaneChats, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(panelInferior, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelSuperior, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );
                mainPanelLayout.setVerticalGroup(
                    mainPanelLayout.createParallelGroup()
                        .addGroup(mainPanelLayout.createSequentialGroup()
                            .addComponent(panelSuperior, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(tabbedPaneChats, GroupLayout.PREFERRED_SIZE, 519, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(panelInferior, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
    private JTabbedPane tabbedPaneChats;
    private JPanel panelNoAmigosConectados;
    private JLabel labelImagenDesierto;
    private JLabel labelTodoTranquilo;
    private JLabel labelNoAmigosConectados;
    private JPanel panelSuperior;
    private JLabel labelBienvenido;
    private BadgeButton buttonAgregarAmigo;
    private JPanel panelInferior;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    // Cliente P2P
    private P2PClientImpl clienteP2P;
    // Servidor P2P
    private P2PServerInterface servidorP2P;
    // Nombre de usuario del cliente
    private String nombreUsuario;
    // Contrasenha del cliente
    private String contrasenha;
    // Ventana de gestion de amigos
    private VAmigos ventanaAmigos;
    // Paneles que funcionan de fondo para los chats de los amigos,
    // la clave es el nombre de usuario de cada amigo
    private HashMap<String, JPanel> panelesAmigos;
    // Chats de los amigos, la clave es el nombre de usuario de cada amigo
    private HashMap<String, ChatArea> chatsAmigos;
    // Contador de notificaciones de cada amigo (nombre de usuario - notificaciones)
    private HashMap<String, String> notificacionesAmigos;


    /**
     * Este metodo inicia y muestra la ventana
     * @param clienteP2P implementacion del cliente que utiliza la ventana
     */
    public void startWindow(P2PClientImpl clienteP2P) throws RemoteException {

        // Iniciamos la ventana asociando el objeto cliente a la ventana
        this.clienteP2P = clienteP2P;

        // Anhadimos el panel principal a la ventana
        this.setContentPane(mainPanel);
        this.setSize(this.mainFrame.getSize());
        // Nombramos la ventana
        this.setTitle("Chats");

        // Anhadimos un WindowAdapter para personalizar la accion de cierre
        // Preguntamos antes de salir y desconectamos al cliente
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                // Antes de eliminar el usuario seleccionado se muestra un dialogo de confirmacion
                int opcion = JOptionPane.showConfirmDialog(null, "Seguro que quiere " +
                        "salir del programa?", "Confirmación", JOptionPane.YES_NO_OPTION);

                // Se comprueba la opcion seleccionada
                if (opcion == JOptionPane.YES_OPTION) {

                    // Desconectamos al cliente antes de salir
                    try {
                        VChats.this.servidorP2P.desconectarse(VChats.this.nombreUsuario, VChats.this.contrasenha);
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }

                    // Cerramos la ventana y salimos del programa
                    VChats.this.dispose();
                    System.exit(0);

                } else {
                    // Si el usuario elige "no", se cancela el evento de cierre
                    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }

            }
        });

        // Anhadimos un chat por cada amigo del cliente
        // Solo se muestran en la JTabbedPane los conectados
        Set<String> amigos = servidorP2P.listarAmigos(this.nombreUsuario, this.contrasenha);
        for (String amigo : amigos) {
            anhadirChatUsuario(amigo);
        }

        // Nos conectamos al servidor
        // Esto producira los notifyConexion de los amigos conectados
        // y anhadira las pestanhas creadas correspondientes a estos usuarios conectados al JTabbedPane
        P2PClientInterface clienteInterfaz = (P2PClientInterface) this.clienteP2P;
        this.servidorP2P.conectarse(this.nombreUsuario, this.contrasenha, clienteInterfaz);

        // Manejamos la senal SIGINT para que si un cliente abandona abruptamente no se mantenga conectado
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {

            try {
                VChats.this.servidorP2P.desconectarse(VChats.this.nombreUsuario, VChats.this.contrasenha);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }

        }));

        // Iniciamos la ventana de amigos pero sin mostrarla
        ventanaAmigos.startWindow(this.clienteP2P);
        // Actualizamos el pop up de notificaciones recibidas
        this.actualizarNotificacionSolicitudes();

        // Se abre en el centro de la pantalla
        this.setLocationRelativeTo(null);
        // No puede variar su tamanho
        this.setResizable(false);

        // Por ultimo mostramos la ventana
        this.setVisible(true);

    }

    /**
     * Este metodo anhade una nueva pestaña a los hashmaps de pestañas con el chat correspondiente a un usuario
     * Esta pestanha solo se mostrara si el amigo correspondiente esta conectado
     * @param nombreUsuario nombre de usuario del remitente
     */
    private void anhadirChatUsuario(String nombreUsuario) {

        JPanel panelChatNuevo = new JPanel();
        ChatArea panelChatAreaNuevo = new ChatArea();

        //======== panelChatNuevo ========

        panelChatNuevo.setBackground(new Color(0x55d893));
        panelChatNuevo.setFont(new Font("sansserif", Font.PLAIN, 20));

        //---- panelChatAreaNuevo ----
        panelChatAreaNuevo.setFont(new Font("sansserif", Font.PLAIN, 18));

        GroupLayout panelChatLayout = new GroupLayout(panelChatNuevo);
        panelChatNuevo.setLayout(panelChatLayout);
        panelChatLayout.setHorizontalGroup(
                panelChatLayout.createParallelGroup()
                        .addComponent(panelChatAreaNuevo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelChatLayout.setVerticalGroup(
                panelChatLayout.createParallelGroup()
                        .addComponent(panelChatAreaNuevo, GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
        );

        // Anhadimos el listener del chat para el envio de mensajes
        // La chatbox de recepcion se crea cuando se recibe un mensaje en el metodo mostrarMensajeUsuario
        panelChatAreaNuevo.setTitle(nombreUsuario);
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy, hh:mmaa");
        panelChatAreaNuevo.addChatEvent(new ChatEvent() {
            @Override
            public void mousePressedSendButton(ActionEvent evt) throws RemoteException {

                if (!panelChatAreaNuevo.getText().isEmpty() && !panelChatAreaNuevo.getText().isBlank()) {
                    String message = panelChatAreaNuevo.getText().trim();

                    // Intentamos enviar el mensaje al otro objeto y lo mostramos en la chatbox
                    if (VChats.this.clienteP2P.enviarMensaje(VChats.this.nombreUsuario, nombreUsuario, message)) {
                        Icon icon = new ImageIcon(getClass().getResource("iconoPropio.png"));
                        String name = "Yo";
                        String date = df.format(new Date());
                        panelChatAreaNuevo.addChatBox(new ModelMessage(icon, name, date, message), ChatBox.BoxType.RIGHT);
                        panelChatAreaNuevo.clearTextAndGrabFocus();
                    }
                }

            }

            @Override
            public void mousePressedFileButton(ActionEvent evt) {

            }

            @Override
            public void keyTyped(KeyEvent evt) {

            }
        });

        // Anhadimos ambos paneles a los hashmaps correspondientes
        // Solo se anhaden los paneles a la JTabbedPane cuando cada usuario este conectado
        if (!this.panelesAmigos.containsKey(nombreUsuario)) {
            this.panelesAmigos.put(nombreUsuario, panelChatNuevo);
        }
        if(!this.chatsAmigos.containsKey(nombreUsuario)){
            this.chatsAmigos.put(nombreUsuario, panelChatAreaNuevo);
        }
        if(!this.notificacionesAmigos.containsKey(nombreUsuario)){
            this.notificacionesAmigos.put(nombreUsuario, "0");
        }
    }

    /**
     * Metodo que muestra la chatBox correspondiente al mensaje recibido de un usuario
     * Este metodo es invocado por el metodo remoto recibirMensaje del objeto cliente
     * @param nombreUsuario usuario remitente del mensaje
     * @param mensaje mensaje recibido
     */
    public void mostrarMensajeUsuario(String nombreUsuario, String mensaje){

        if(this.chatsAmigos.containsKey(nombreUsuario)){

            ChatArea chatAreaUsuario = this.chatsAmigos.get(nombreUsuario);

            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy, hh:mmaa");
            Icon icon = new ImageIcon(getClass().getResource("iconoAmigo.png"));
            String name = nombreUsuario;
            String date = df.format(new Date());
            chatAreaUsuario.addChatBox(new ModelMessage(icon, name, date, mensaje), ChatBox.BoxType.LEFT);

            if(!getChatSeleccionado().equals(nombreUsuario)){
                aumentarNotificaciones(nombreUsuario);
            }
        }
    }

    /**
     * Este metodo muestra la pestanha correspondiente a un usuario que se ha conecado
     * Este metodo es invocado por el metodo remoto notifyConexion del objeto cliente
     * @param nombreUsuario usuario que se ha conectado
     */
    public void mostrarUsuarioConectado(String nombreUsuario){

        if(!this.panelesAmigos.containsKey(nombreUsuario) || !this.chatsAmigos.containsKey(nombreUsuario)){
            this.anhadirChatUsuario(nombreUsuario);
        }

        JPanel panelChatUsuario = this.panelesAmigos.get(nombreUsuario);

        // Al titulo de la pestanha se le suman x espacios para que tenga siempre la misma longitud
        StringBuilder nombreAlineado = new StringBuilder(nombreUsuario);
        // Anhadimos espacios hasta alcanzar la longitud deseada
        while (nombreAlineado.length() < 58) {
            nombreAlineado.append(" ");
        }

        // Establecemos la pestanha y su nombre
        tabbedPaneChats.addTab(nombreAlineado.toString(), panelChatUsuario);

        // Establecemos el color de fondo para todas las pestañas
        for (int i = 0; i < tabbedPaneChats.getTabCount(); i++) {
            tabbedPaneChats.setBackgroundAt(i, new Color(0x23a661));
        }

        // Ocultamos la pestanha de cero usuarios conectados si esta en la JTabbedPane
        if(this.tabbedPaneChats.indexOfComponent(this.panelNoAmigosConectados) != -1){
            this.tabbedPaneChats.remove(this.panelNoAmigosConectados);
            }
    }

    /**
     * Este metodo oculta la pestanha correspondiente a un usuario que se ha desconectado
     * Este metodo es invocado por el metodo remoto notifyDesconexion del objeto cliente
     * @param nombreUsuario usuario que se ha desconectado
     */
    public void ocultarUsuarioDesconectado(String nombreUsuario){

        if(this.panelesAmigos.containsKey(nombreUsuario)) {

            JPanel panelChatUsuario = this.panelesAmigos.get(nombreUsuario);

            // Eliminamos la pestanha del JTabbedPane
            this.tabbedPaneChats.remove(panelChatUsuario);

            // Si no quedan amigos conectados se muestra la pestanha de cero usuarios conectados
            if(this.tabbedPaneChats.getTabCount() == 0){
                tabbedPaneChats.addTab("                                                                              ", panelNoAmigosConectados);
                tabbedPaneChats.setBackgroundAt(0, new Color(0x23a661));
            }
        }

    }

    /**
     * Este metodo actualiza los hashmaps de chats comparando los chats actuales con la lista de amigos del cliente
     */
    public void actualizarChats(){
        // Anhadimos un chat por cada amigo del cliente
        // Solo se muestran en la JTabbedPane los conectados
        Set<String> amigos;
        try {
            amigos = servidorP2P.listarAmigos(this.nombreUsuario, this.contrasenha);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        // Anhadimos los amigos que no estaban en la lista previamente
        for (String amigo : amigos) {
            if(!this.panelesAmigos.containsKey(nombreUsuario) && !this.chatsAmigos.containsKey(nombreUsuario)) {
                anhadirChatUsuario(amigo);
            }
        }
    }


    /**
     * Este metodo actualiza las listas de amigos y solicitudes en la ventana de amigos
     * @param amigos lista de amigos del cliente
     * @param solEnviadas lista de solicitudes enviadas del cliente
     * @param solRecibidas lista de solicitudes recibidas del cliente
     */
    public void notifyActualizarListados(Set<String> amigos, Set<String> solEnviadas, Set<String> solRecibidas) {
        this.ventanaAmigos.notifyActualizarListados(amigos, solEnviadas, solRecibidas);
    }

    /**
     * Metodo para extraer el nombre de usuario del titulo de una pestana de tabbedpanechats
     * @param tabTitle titulo de una de las pestanas de tabbedpanechats
     * @return nombre de usuario correspondiente a esa pestana
     */
    private String extractUsernameFromTabTitle(String tabTitle) {
        // Analizar el título para extraer el nombre de usuario
        int index = tabTitle.indexOf('[');
        if (index != -1) {
            return tabTitle.substring(0, index).trim();
        }
        return tabTitle.trim();
    }

    /**
     * Metodo que actualiza el titulo de uan pestana de tabbedpanechats
     * @param nombreUsuario nombre de usuario correspondiente a la pestana que se va a actualizar
     */
    private void actualizarTituloPestana(String nombreUsuario) {
        int index = encontrarIndicePestana(nombreUsuario);
        if (index != -1) {
            // Establecer el título de la pestaña con el nuevo valor del contador
            tabbedPaneChats.setTitleAt(index, generarTituloPestana(nombreUsuario));
        }
    }

    /**
     * Metodo para encontrar el indice de la pestana segun el nombre de usuario
     * @param nombreUsuario nombre de usuario que se usa como criterio de busqueda
     * @return indice de la pestana, -1 en caso de no existir
     */
    private int encontrarIndicePestana(String nombreUsuario) {
        for (int i = 0; i < tabbedPaneChats.getTabCount(); i++) {
            String title = tabbedPaneChats.getTitleAt(i);
            if (extractUsernameFromTabTitle(title).equals(nombreUsuario)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Metodo para construir el titulo de la pestana de tabbedPaneChats de un usuario
     * @param nombreUsuario nombre del usuario correspondiente a la pestana
     * @return titulo de la pestana
     */
    private String generarTituloPestana(String nombreUsuario) {
        StringBuilder titulo;
        try{
            // Eliminamos el signo '+' en caso de haber mas de 9 notificaciones
            String numNotificacionesString = this.notificacionesAmigos.get(nombreUsuario);
            numNotificacionesString = numNotificacionesString.replaceAll("[^0-9]", "");
            int numNotificaciones = Integer.parseInt(numNotificacionesString);
            if(numNotificaciones>0){
                titulo = new StringBuilder(nombreUsuario + " [" + notificacionesAmigos.get(nombreUsuario) + "]");
            }else{
                titulo = new StringBuilder(nombreUsuario);
            }
        }catch (NumberFormatException ex){
            titulo = new StringBuilder(nombreUsuario);
        }

        while (titulo.length() < 58) {
            titulo.append(" ");
        }
        return titulo.toString();
    }

    /**
     * Metodo para obtener el chat seleccionado actualmente por el usuario
     * Se utilizara para comprobar si se aumentan o no las notificaciones,
     * ya que si ya estamos en el chat seleccionado no se aumentan
     * @return nombre de usuario del chat seleccionado
     */
    private String getChatSeleccionado() {
        int indiceSeleccionado = tabbedPaneChats.getSelectedIndex();
        if (indiceSeleccionado != -1) {
            String tituloSeleccionado = tabbedPaneChats.getTitleAt(indiceSeleccionado);
            return extractUsernameFromTabTitle(tituloSeleccionado);
        }
        return null; // No hay ningún item seleccionado
    }

    /**
     * Metodo para aumentar en 1 las notificaciones del chat de un usuario
     * @param nombreUsuario nombre de usuario del chat del amigo que se aumentan las notificaciones
     */
    public void aumentarNotificaciones(String nombreUsuario) {
        if(notificacionesAmigos.get(nombreUsuario).equals("9+")){
            return;
        }

        // Obtener el valor actual de notificaciones
        int notificacionesActuales = Integer.parseInt(notificacionesAmigos.get(nombreUsuario));

        // Aumentar el contador de notificaciones
        notificacionesActuales++;

        // Actualizar el valor en el HashMap
        if(notificacionesActuales>9){
            notificacionesAmigos.put(nombreUsuario, "9+");
        }else{
            notificacionesAmigos.put(nombreUsuario, String.valueOf(notificacionesActuales));
        }

        // Actualizar el título de la pestaña
        actualizarTituloPestana(nombreUsuario);
    }

    /**
     * Metodo que actualiza el pop up de las solicitudes recibidas
     */
    public void actualizarNotificacionSolicitudes(){
        // Obtenemos el numero de solicitudes de amistad recibidas
        int solicitudesRecibidas = ventanaAmigos.getSolicitudesRecibidas();
        // Anhadimos ese numero a las notificaciones del boton de vamigos antes de mostrarlas
        if(solicitudesRecibidas > 0){
            this.buttonAgregarAmigo.setText(String.valueOf(solicitudesRecibidas));
        }else{
            this.buttonAgregarAmigo.setText("");
        }
    }


    
}
