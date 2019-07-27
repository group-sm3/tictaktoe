/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.view;
import server.cont.ServerController;
import server.model.ServerModel;
import server.model.ModelEvent;
import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*;

/**
 *
 * @author anne
 */
public class ServerView extends JFrameView{
    public static final String LISTEN = "Listen";
    private JTextField textField = new JTextField();
    
    public ServerView(ServerModel model, ServerController cont){
        super(model, cont);
        textField.setText("port #");
        this.getContentPane().add(textField, BorderLayout.NORTH);
        JPanel buttonPanel = new JPanel();
        Handler handler = new Handler();
        JButton buttonListen = new JButton(LISTEN);
        buttonListen.addActionListener(handler);
        // buttonPanel.setLayout(new GridLayout(4, 4, 5, 5));
        this.getContentPane().add(buttonPanel, BorderLayout.CENTER);
        buttonPanel.add(buttonListen, null);
        pack(); 
    }
    
    // implement event-handling code
    // i believe this is how the model alerts the view of a change.
    public void modelChanged(ModelEvent event){
        String message = event.getMessage();
        textField.setText(message);
//        String msg = event.getAmount() + "";
//        textField.setText(msg);
    }

    class Handler implements ActionListener{
        // event handled locally
        public void actionPerformed(ActionEvent e){
            ((ServerController)getCont()).operation(e.getActionCommand());
        }
    }
    public static void main(String [] args) { new ServerController(); }
}
