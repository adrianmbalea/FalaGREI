package programaCliente.gui.chat.swing;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.rmi.RemoteException;

public interface ChatEvent {

    public void mousePressedSendButton(ActionEvent evt) throws RemoteException;

    public void mousePressedFileButton(ActionEvent evt);

    public void keyTyped(KeyEvent evt);
}
