package example.BargaingTheoryLAN;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Bikash
 */
public class player1 extends JPanel implements ActionListener {
    private Image map;
    private Image p1;
    private Socket soc=null;
    private final int PORT= 23435;
    PrintWriter out= null;
    BufferedReader in=null;
    JButton long_o=new JButton("Long- Open Gate");
    JButton long_c=new JButton("Long- Close Gate");
    JButton sh=new JButton("Short");
    String res=null;
    boolean showRes=false;
    private Timer time;
    
    public player1(String ip) throws IOException, InterruptedException {
        this.setSize(new Dimension(600, 700));
        this.setVisible(true);
        this.initClient(ip);
        //System.out.println(in.readLine());
        long_o.setVisible(true);
        long_c.setVisible(true);
        sh.setVisible(true);
        long_o.setActionCommand("Long- Open Gate");
        long_c.setActionCommand("Long- Close Gate");
        sh.setActionCommand("Short");
        long_o.addActionListener(this);
        long_c.addActionListener(this);
        sh.addActionListener(this);

        ImageIcon a = new ImageIcon(this.getClass().getResource("map.png"));
        map = a.getImage();
        a = new ImageIcon(this.getClass().getResource("food.png"));
        p1 = a.getImage();
        
        this.setLayout(null);
        long_o.setBounds(130, 550, 120, 50);
        sh.setBounds(250, 550, 100, 50);
        long_c.setBounds(350, 550, 150, 50);

        this.add(long_o);
        this.add(sh);
        this.add(long_c);
        
        time=new Timer(1000, this);
        time.setActionCommand("time");
        time.start();
        res="waiting";
        
    }
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(map, 0, 0, this);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Consolas", Font.BOLD, 22));
        g.drawString(res, 50, 500);

    }
    private void initWriterReader() {
        try {
            out =new PrintWriter(soc.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
        } catch (IOException ex) {
        }
    }
    public void initClient(String ip) throws IOException, InterruptedException {
        try {
            soc=new Socket(ip, PORT);
        } catch (UnknownHostException ex) {
        }
        this.initWriterReader();
        System.out.println(in.readLine());
    }
    public void windowClosing(WindowEvent e) throws IOException {
         soc.close();
         out.close();
         in.close();
    };
    public void waiting() throws IOException {
        res=in.readLine();
        
        switch(Integer.parseInt(res)) {
            case 0:
                res="You were blocked from the small route by their gate +0";
                repaint();
                break;
            case 1:
                res="Long route- sucessful +1";
                repaint();
                break;
            case 2:
                res="You were able to get through the small route +2";
                repaint();
                break;
            case 4:
                res="You both tried to get through the small route +0";
                repaint();
                break;
        }
        
        System.out.println(res);
        //System.out.println(in.readLine());
        long_o.setEnabled(true);
        long_c.setEnabled(true);
        sh.setEnabled(true);
    }
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("time")) {
            repaint();
            return;
        }
        if(e.getActionCommand().equals("Long- Open Gate")) {
            out.println("open");
            System.out.println("open");

        } else if(e.getActionCommand().equals("Long- Close Gate")) {
            out.println("close");
            System.out.println("close");
        }
        if(e.getActionCommand().equals("Short")) {
            out.println("short");
            System.out.println("short");
        }
        //long_o.setEnabled(false);
        //long_c.setEnabled(false);
        //sh.setEnabled(false);
        try {
            this.waiting();
        } catch (IOException ex) {
            Logger.getLogger(player1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}