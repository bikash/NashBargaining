package example.BargaingTheoryLAN;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author SydnaAgnehs
 */
public class Truck implements ActionListener {
    private JFrame frame;
    private JPanel panel;
    private JButton host=new JButton("host");
    private JButton player1=new JButton("join");
    
    public Truck() throws IOException {
        frame=new JFrame("Truck");
        panel=new JPanel();
        
        host.setVisible(true);
        host.setActionCommand("host");
        host.addActionListener(this);
        panel.add(host);
        
        player1.setVisible(true);
        player1.setActionCommand("player1");
        player1.addActionListener(this);
        panel.add(player1);

        
        
        panel.setVisible(true);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("host")) {
            try {
                panel.setVisible(false);
                Host h=new Host();
                panel=null;
                frame.add(h);
                frame.setSize(h.getSize());
            } catch (IOException ex) {
                Logger.getLogger(Truck.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if(e.getActionCommand().equals("player1")) {
            panel.setVisible(false);
                player1 p1;
            try {
                String ip=JOptionPane.showInputDialog("IP ADDRESS of HOST");
                p1 = new player1(ip);
                 panel=null;
                frame.add(p1);
                frame.setSize(p1.getSize());
            } catch (IOException ex) {
                Logger.getLogger(Truck.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Truck.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        new Truck();
    }
}