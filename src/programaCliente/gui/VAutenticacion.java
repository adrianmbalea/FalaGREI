package programaCliente.gui;

import programaCliente.cliente.P2PClientImpl;
import programaCliente.cliente.P2PClientInterface;
import programaServidor.servidor.P2PServerInterface;
import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.*;

/**
 * Esta clase representa la ventana de autenticacion del usuario
 * @author pablo garcia fuentes
 * @author adrian martinez balea
 */

public class VAutenticacion extends JFrame{

    /**
     * Constructor de la clase VAutenticacion
     * @param servidorP2P servidor remoto
     */
    public VAutenticacion(P2PServerInterface servidorP2P) {

        // Asignamos el servidor
        this.servidorP2P = servidorP2P;

        initComponents();

        // Mostrar el texto de los campos de contrasenha
        this.textFieldContrasenha.setEchoChar((char) 0); // Muestra los caracteres
        this.textFieldContrasenha2.setEchoChar((char) 0);
        this.textFieldContrasenha3.setEchoChar((char) 0);
    }

    /**
     * Listener que modifica el texto de los campos de usuario y contrasenha al hacer click en uno de ellos
     * @param e MouseEvent
     */
    private void textFieldUsuario2MousePressed(MouseEvent e) {
        // Si se hace click en el campo de nombre de usuario se elimina el texto preescrito
        if(textFieldUsuario2.getText().equals("Introduce tu nombre de usuario")){
            textFieldUsuario2.setText("");
            textFieldUsuario2.setForeground(Color.black);
        }
        if(textFieldContrasenha2.getText().equals("")){
            textFieldContrasenha2.setText("Introduce tu contraseña");
            textFieldContrasenha2.setForeground(new Color(197, 197,197));
            this.textFieldContrasenha2.setEchoChar((char) 0); // Muestra los caracteres
        }
        if(textFieldContrasenha3.getText().equals("")){
            textFieldContrasenha3.setText("Repite tu contraseña");
            textFieldContrasenha3.setForeground(new Color(197, 197,197));
            this.textFieldContrasenha3.setEchoChar((char) 0); // Muestra los caracteres
        }
    }

    /**
     * Listener que modifica el texto de los campos de usuario y contrasenha al hacer click en uno de ellos
     * @param e MouseEvent
     */
    private void textFieldContrasenha2MousePressed(MouseEvent e) {
        if(textFieldUsuario2.getText().equals("")){
            textFieldUsuario2.setText("Introduce tu nombre de usuario");
            textFieldUsuario2.setForeground(new Color(197, 197,197));
        }
        // Si se hace click en el campo de nombre de contrasenha se elimina el texto preescrito y se ocultan los caracteres
        if(textFieldContrasenha2.getText().equals("Introduce tu contraseña")){
            textFieldContrasenha2.setText("");
            textFieldContrasenha2.setForeground(Color.black);
            textFieldContrasenha2.setEchoChar('\u2022'); // Oculta los caracteres
        }
        if(textFieldContrasenha3.getText().equals("")){
            textFieldContrasenha3.setText("Repite tu contraseña");
            textFieldContrasenha3.setForeground(new Color(197, 197,197));
            this.textFieldContrasenha3.setEchoChar((char) 0); // Muestra los caracteres
        }
    }

    /**
     * Listener que modifica el texto de los campos de usuario y contrasenha al hacer click en uno de ellos
     * @param e MouseEvent
     */
    private void textFieldContrasenha3MousePressed(MouseEvent e) {
        if(textFieldUsuario2.getText().equals("")){
            textFieldUsuario2.setText("Introduce tu nombre de usuario");
            textFieldUsuario2.setForeground(new Color(197, 197,197));
        }
        if(textFieldContrasenha2.getText().equals("")){
            textFieldContrasenha2.setText("Introduce tu contraseña");
            textFieldContrasenha2.setForeground(new Color(197, 197,197));
            this.textFieldContrasenha2.setEchoChar((char) 0); // Muestra los caracteres
        }
        if(textFieldContrasenha3.getText().equals("Repite tu contraseña")){
            textFieldContrasenha3.setText("");
            textFieldContrasenha3.setForeground(Color.black);
            textFieldContrasenha3.setEchoChar('\u2022'); // Oculta los caracteres
        }
    }

    /**
     * Listener que modifica el texto de los campos de usuario y contrasenha al hacer click en uno de ellos
     * @param e MouseEvent
     */
    private void textFieldUsuarioMousePressed(MouseEvent e) {
        if(textFieldUsuario.getText().equals("Introduce tu nombre de usuario")){
            textFieldUsuario.setText("");
            textFieldUsuario.setForeground(Color.black);
        }
        if(textFieldContrasenha.getText().equals("")){
            textFieldContrasenha.setText("Introduce tu contraseña");
            textFieldContrasenha.setForeground(new Color(197, 197,197));
            this.textFieldContrasenha.setEchoChar((char) 0); // Muestra los caracteres
        }
    }

    /**
     * Listener que modifica el texto de los campos de usuario y contrasenha al hacer click en uno de ellos
     * @param e MouseEvent
     */
    private void textFieldContrasenhaMousePressed(MouseEvent e) {
        if(textFieldUsuario.getText().equals("")){
            textFieldUsuario.setText("Introduce tu nombre de usuario");
            textFieldUsuario.setForeground(new Color(197, 197,197));
        }
        if(textFieldContrasenha.getText().equals("Introduce tu contraseña")){
            textFieldContrasenha.setText("");
            textFieldContrasenha.setForeground(Color.black);
            textFieldContrasenha.setEchoChar('\u2022'); // Oculta los caracteres
        }
    }

    /**
     * Listener que abre la ventana de chats y se conecta al servidor si las credenciales son correctas
     * @param e ActionEvent
     * @throws RemoteException
     */
    private void iniciarSesion(ActionEvent e){


        if(this.textFieldUsuario.getText().equals("Introduce tu nombre de usuario") || this.textFieldUsuario.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Credenciales incorrectas", "Aviso", JOptionPane.ERROR_MESSAGE);
        }else if(this.textFieldContrasenha.getText().equals("Introduce tu contraseña") || this.textFieldContrasenha.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Credenciales incorrectas", "Aviso", JOptionPane.ERROR_MESSAGE);
        }else {

            String username = this.textFieldUsuario.getText();
            String passwd = this.textFieldContrasenha.getText();

            // Primero comprobamos las credenciales en el servidor
            try {
                if(!this.servidorP2P.validarUsuario(username, passwd)){
                    JOptionPane.showMessageDialog(null, "Credenciales incorrectas", "Aviso", JOptionPane.ERROR_MESSAGE);
                }else {
    
                    // Si las credenciales son correctas se crea la ventana de chats
                    VChats vc = new VChats(this.servidorP2P, username, passwd);
    
                    // Crea una instancia de P2PClientImpl
                    P2PClientImpl clienteP2P = new P2PClientImpl(servidorP2P, username, passwd, vc);
    
                    // Inicia la ventana de chats y cierra la de autenticacion
                    // Asigna el cliente a la ventana de chats para iniciar la conexion
                    vc.startWindow(clienteP2P);
    
                    this.dispose();
                }
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }

        }

    }

    /**
     * Listener que registra al usuario si las credenciales son correctas
     * @param e ActionEvent
     */
    private void registrarse2(ActionEvent e){

        if(this.textFieldUsuario2.getText().equals("Introduce tu nombre de usuario") || this.textFieldUsuario2.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Credenciales incorrectas", "Aviso", JOptionPane.ERROR_MESSAGE);
        }else if(this.textFieldContrasenha2.getText().equals("Introduce tu contraseña") || this.textFieldContrasenha2.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Credenciales incorrectas", "Aviso", JOptionPane.ERROR_MESSAGE);
        }else if(this.textFieldContrasenha3.getText().equals("Repite tu contraseña") || this.textFieldContrasenha3.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Credenciales incorrectas", "Aviso", JOptionPane.ERROR_MESSAGE);
        }else if(!this.textFieldContrasenha2.getText().equals(this.textFieldContrasenha3.getText())){
            JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden", "Aviso", JOptionPane.ERROR_MESSAGE);
        }else {
            try {
                if(this.servidorP2P.registrarUsuario(this.textFieldUsuario2.getText(), this.textFieldContrasenha2.getText())){
                    JOptionPane.showMessageDialog(null, "Usuario registrado correctamente.\nYa puedes iniciar sesión", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                    textFieldUsuario2.setForeground(new Color(197, 197,197));
                    textFieldUsuario2.setText("Introduce tu nombre de usuario");
                    textFieldContrasenha2.setForeground(new Color(197, 197,197));
                    textFieldContrasenha2.setEchoChar((char) 0);
                    textFieldContrasenha2.setText("Introduce tu contrase\u00f1a");
                    textFieldContrasenha3.setForeground(new Color(197, 197,197));
                    textFieldContrasenha3.setEchoChar((char) 0);
                    textFieldContrasenha3.setText("Repite tu contrase\u00f1a");
                }else{
                    JOptionPane.showMessageDialog(null, "Error al registrar el usuario", "Aviso", JOptionPane.ERROR_MESSAGE);
                }
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
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
        panelIzquierdo = new JPanel();
        labelBienvenido = new JLabel();
        label1 = new JLabel();
        tabbedPane = new JTabbedPane();
        panelInicioSesion = new JPanel();
        labelInicioDeSesion = new JLabel();
        textFieldContrasenha = new JPasswordField();
        textFieldUsuario = new JTextField();
        buttonIniciarSesion = new JButton();
        panelRegistro = new JPanel();
        labelRegistro = new JLabel();
        textFieldContrasenha2 = new JPasswordField();
        textFieldUsuario2 = new JTextField();
        textFieldContrasenha3 = new JPasswordField();
        buttonRegistrarse2 = new JButton();

        //======== mainFrame ========
        {
            mainFrame.setMaximumSize(new Dimension(700, 500));
            mainFrame.setMinimumSize(new Dimension(700, 500));
            mainFrame.setPreferredSize(new Dimension(700, 500));
            mainFrame.setResizable(false);
            var mainFrameContentPane = mainFrame.getContentPane();

            //======== mainPanel ========
            {
                mainPanel.setBackground(Color.white);
                mainPanel.setMaximumSize(new Dimension(700, 500));
                mainPanel.setMinimumSize(new Dimension(700, 500));
                mainPanel.setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax. swing. border.
                EmptyBorder( 0, 0, 0, 0) , "", javax. swing. border. TitledBorder. CENTER, javax. swing
                . border. TitledBorder. BOTTOM, new java .awt .Font ("Dia\u006cog" ,java .awt .Font .BOLD ,12 ),
                java. awt. Color. red) ,mainPanel. getBorder( )) ); mainPanel. addPropertyChangeListener (new java. beans. PropertyChangeListener( )
                { @Override public void propertyChange (java .beans .PropertyChangeEvent e) {if ("\u0062ord\u0065r" .equals (e .getPropertyName () ))
                throw new RuntimeException( ); }} );

                //======== panelIzquierdo ========
                {
                    panelIzquierdo.setBackground(new Color(0x23a661));

                    //---- labelBienvenido ----
                    labelBienvenido.setText("Bienvenido de nuevo!");
                    labelBienvenido.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
                    labelBienvenido.setForeground(Color.white);
                    labelBienvenido.setBackground(Color.white);
                    labelBienvenido.setHorizontalAlignment(SwingConstants.CENTER);

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
                                        .addComponent(labelBienvenido, GroupLayout.PREFERRED_SIZE, 288, GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelIzquierdoLayout.createSequentialGroup()
                                        .addGap(29, 29, 29)
                                        .addComponent(label1)))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    );
                    panelIzquierdoLayout.setVerticalGroup(
                        panelIzquierdoLayout.createParallelGroup()
                            .addGroup(panelIzquierdoLayout.createSequentialGroup()
                                .addGap(48, 48, 48)
                                .addComponent(labelBienvenido, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(label1, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                    );
                }

                //======== tabbedPane ========
                {
                    tabbedPane.setBackground(Color.white);
                    tabbedPane.setForeground(new Color(0x07a479));
                    tabbedPane.setBorder(null);
                    tabbedPane.setFont(new Font("sansserif", Font.PLAIN, 18));
                    tabbedPane.setTabPlacement(SwingConstants.BOTTOM);

                    //======== panelInicioSesion ========
                    {
                        panelInicioSesion.setBackground(Color.white);
                        panelInicioSesion.setBorder(null);

                        //---- labelInicioDeSesion ----
                        labelInicioDeSesion.setText("Inicio de sesi\u00f3n");
                        labelInicioDeSesion.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
                        labelInicioDeSesion.setForeground(new Color(0x07a479));
                        labelInicioDeSesion.setBackground(Color.black);
                        labelInicioDeSesion.setHorizontalAlignment(SwingConstants.CENTER);

                        //---- textFieldContrasenha ----
                        textFieldContrasenha.setBackground(Color.white);
                        textFieldContrasenha.setBorder(new EtchedBorder());
                        textFieldContrasenha.setCaretColor(Color.black);
                        textFieldContrasenha.setForeground(Color.lightGray);
                        textFieldContrasenha.setText("Introduce tu contrase\u00f1a");
                        textFieldContrasenha.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mousePressed(MouseEvent e) {
                                textFieldContrasenhaMousePressed(e);
                            }
                        });

                        //---- textFieldUsuario ----
                        textFieldUsuario.setBackground(Color.white);
                        textFieldUsuario.setBorder(new EtchedBorder());
                        textFieldUsuario.setCaretColor(Color.black);
                        textFieldUsuario.setForeground(Color.lightGray);
                        textFieldUsuario.setText("Introduce tu nombre de usuario");
                        textFieldUsuario.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mousePressed(MouseEvent e) {
                                textFieldUsuarioMousePressed(e);
                            }
                        });

                        //---- buttonIniciarSesion ----
                        buttonIniciarSesion.setText("Iniciar sesi\u00f3n");
                        buttonIniciarSesion.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 17));
                        buttonIniciarSesion.setBackground(Color.white);
                        buttonIniciarSesion.setForeground(new Color(0x07a479));
                        buttonIniciarSesion.setContentAreaFilled(false);
                        buttonIniciarSesion.setBorder(new EtchedBorder());
                        buttonIniciarSesion.addActionListener(e -> iniciarSesion(e));

                        GroupLayout panelInicioSesionLayout = new GroupLayout(panelInicioSesion);
                        panelInicioSesion.setLayout(panelInicioSesionLayout);
                        panelInicioSesionLayout.setHorizontalGroup(
                            panelInicioSesionLayout.createParallelGroup()
                                .addGroup(GroupLayout.Alignment.TRAILING, panelInicioSesionLayout.createSequentialGroup()
                                    .addGroup(panelInicioSesionLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addGroup(GroupLayout.Alignment.LEADING, panelInicioSesionLayout.createSequentialGroup()
                                            .addGroup(panelInicioSesionLayout.createParallelGroup()
                                                .addGroup(panelInicioSesionLayout.createSequentialGroup()
                                                    .addGap(39, 39, 39)
                                                    .addComponent(labelInicioDeSesion, GroupLayout.PREFERRED_SIZE, 287, GroupLayout.PREFERRED_SIZE))
                                                .addGroup(panelInicioSesionLayout.createSequentialGroup()
                                                    .addContainerGap()
                                                    .addComponent(buttonIniciarSesion, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)))
                                            .addGap(0, 58, Short.MAX_VALUE))
                                        .addGroup(GroupLayout.Alignment.LEADING, panelInicioSesionLayout.createSequentialGroup()
                                            .addContainerGap()
                                            .addGroup(panelInicioSesionLayout.createParallelGroup()
                                                .addComponent(textFieldUsuario, GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                                                .addComponent(textFieldContrasenha, GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE))))
                                    .addContainerGap())
                        );
                        panelInicioSesionLayout.setVerticalGroup(
                            panelInicioSesionLayout.createParallelGroup()
                                .addGroup(panelInicioSesionLayout.createSequentialGroup()
                                    .addGap(99, 99, 99)
                                    .addComponent(labelInicioDeSesion, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(textFieldUsuario, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(textFieldContrasenha, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(buttonIniciarSesion, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
                                    .addContainerGap(69, Short.MAX_VALUE))
                        );
                    }
                    tabbedPane.addTab("Iniciar Sesi\u00f3n", panelInicioSesion);

                    //======== panelRegistro ========
                    {
                        panelRegistro.setBackground(Color.white);

                        //---- labelRegistro ----
                        labelRegistro.setText("Crear un nuevo usuario");
                        labelRegistro.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
                        labelRegistro.setForeground(new Color(0x07a479));
                        labelRegistro.setBackground(Color.black);
                        labelRegistro.setHorizontalAlignment(SwingConstants.CENTER);

                        //---- textFieldContrasenha2 ----
                        textFieldContrasenha2.setBackground(Color.white);
                        textFieldContrasenha2.setBorder(new EtchedBorder());
                        textFieldContrasenha2.setCaretColor(Color.black);
                        textFieldContrasenha2.setForeground(Color.lightGray);
                        textFieldContrasenha2.setText("Introduce tu contrase\u00f1a");
                        textFieldContrasenha2.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mousePressed(MouseEvent e) {
                                textFieldContrasenha2MousePressed(e);
                            }
                        });

                        //---- textFieldUsuario2 ----
                        textFieldUsuario2.setBackground(Color.white);
                        textFieldUsuario2.setBorder(new EtchedBorder());
                        textFieldUsuario2.setCaretColor(Color.black);
                        textFieldUsuario2.setForeground(Color.lightGray);
                        textFieldUsuario2.setText("Introduce tu nombre de usuario");
                        textFieldUsuario2.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mousePressed(MouseEvent e) {
                                textFieldUsuario2MousePressed(e);
                            }
                        });

                        //---- textFieldContrasenha3 ----
                        textFieldContrasenha3.setBackground(Color.white);
                        textFieldContrasenha3.setBorder(new EtchedBorder());
                        textFieldContrasenha3.setCaretColor(Color.black);
                        textFieldContrasenha3.setForeground(Color.lightGray);
                        textFieldContrasenha3.setText("Repite tu contrase\u00f1a");
                        textFieldContrasenha3.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mousePressed(MouseEvent e) {
                                textFieldContrasenha3MousePressed(e);
                            }
                        });

                        //---- buttonRegistrarse2 ----
                        buttonRegistrarse2.setText("Registrarse");
                        buttonRegistrarse2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 17));
                        buttonRegistrarse2.setBackground(Color.white);
                        buttonRegistrarse2.setForeground(new Color(0x07a479));
                        buttonRegistrarse2.setContentAreaFilled(false);
                        buttonRegistrarse2.setBorder(new EtchedBorder());
                        buttonRegistrarse2.addActionListener(e -> registrarse2(e));

                        GroupLayout panelRegistroLayout = new GroupLayout(panelRegistro);
                        panelRegistro.setLayout(panelRegistroLayout);
                        panelRegistroLayout.setHorizontalGroup(
                            panelRegistroLayout.createParallelGroup()
                                .addGroup(panelRegistroLayout.createSequentialGroup()
                                    .addGap(35, 35, 35)
                                    .addComponent(labelRegistro, GroupLayout.PREFERRED_SIZE, 287, GroupLayout.PREFERRED_SIZE)
                                    .addContainerGap(68, Short.MAX_VALUE))
                                .addGroup(panelRegistroLayout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(panelRegistroLayout.createParallelGroup()
                                        .addGroup(panelRegistroLayout.createSequentialGroup()
                                            .addComponent(buttonRegistrarse2, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
                                            .addGap(0, 0, Short.MAX_VALUE))
                                        .addComponent(textFieldUsuario2)
                                        .addComponent(textFieldContrasenha2, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                                        .addComponent(textFieldContrasenha3, GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE))
                                    .addContainerGap())
                        );
                        panelRegistroLayout.setVerticalGroup(
                            panelRegistroLayout.createParallelGroup()
                                .addGroup(panelRegistroLayout.createSequentialGroup()
                                    .addGap(75, 75, 75)
                                    .addComponent(labelRegistro, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(textFieldUsuario2, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
                                    .addGap(12, 12, 12)
                                    .addComponent(textFieldContrasenha2, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(textFieldContrasenha3, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
                                    .addGap(12, 12, 12)
                                    .addComponent(buttonRegistrarse2, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
                                    .addContainerGap(34, Short.MAX_VALUE))
                        );
                    }
                    tabbedPane.addTab("Registrarse", panelRegistro);

                    tabbedPane.setSelectedIndex(0);
                }

                GroupLayout mainPanelLayout = new GroupLayout(mainPanel);
                mainPanel.setLayout(mainPanelLayout);
                mainPanelLayout.setHorizontalGroup(
                    mainPanelLayout.createParallelGroup()
                        .addGroup(mainPanelLayout.createSequentialGroup()
                            .addComponent(panelIzquierdo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 390, GroupLayout.PREFERRED_SIZE)
                            .addGap(100, 100, 100))
                );
                mainPanelLayout.setVerticalGroup(
                    mainPanelLayout.createParallelGroup()
                        .addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(panelIzquierdo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );
            }

            GroupLayout mainFrameContentPaneLayout = new GroupLayout(mainFrameContentPane);
            mainFrameContentPane.setLayout(mainFrameContentPaneLayout);
            mainFrameContentPaneLayout.setHorizontalGroup(
                mainFrameContentPaneLayout.createParallelGroup()
                    .addGroup(mainFrameContentPaneLayout.createSequentialGroup()
                        .addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            mainFrameContentPaneLayout.setVerticalGroup(
                mainFrameContentPaneLayout.createParallelGroup()
                    .addGroup(mainFrameContentPaneLayout.createSequentialGroup()
                        .addComponent(mainPanel, GroupLayout.PREFERRED_SIZE, 441, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
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
    private JPanel panelIzquierdo;
    private JLabel labelBienvenido;
    private JLabel label1;
    private JTabbedPane tabbedPane;
    private JPanel panelInicioSesion;
    private JLabel labelInicioDeSesion;
    private JPasswordField textFieldContrasenha;
    private JTextField textFieldUsuario;
    private JButton buttonIniciarSesion;
    private JPanel panelRegistro;
    private JLabel labelRegistro;
    private JPasswordField textFieldContrasenha2;
    private JTextField textFieldUsuario2;
    private JPasswordField textFieldContrasenha3;
    private JButton buttonRegistrarse2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    // Servidor P2P
    private P2PServerInterface servidorP2P;


    /**
     * Este metodo inicia y muestra la ventana
     */
    public void startWindow() {

        // Anhadimos el panel principal a la ventana
        this.setContentPane(mainPanel);
        this.setSize(this.mainFrame.getSize());

        // Nombramos la ventana
        this.setTitle("Autenticación");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Se abre en el centro de la pantalla
        this.setLocationRelativeTo(null);
        // No puede variar su tamanho
        this.setResizable(false);

        // Se muestra la ventana
        this.setVisible(true);

    }

}
