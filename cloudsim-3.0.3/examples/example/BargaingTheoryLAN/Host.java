package example.BargaingTheoryLAN;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 *
 * @author Bikash
 */
public class Host extends JPanel implements ActionListener {
    private Image map;
    private ServerSocket srvr=null;
    private final int PORT= 23435;
    private Socket soc1=null;
    private Socket soc2=null;
    PrintWriter out1= null;
    BufferedReader in1=null;
    PrintWriter out2= null;
    BufferedReader in2=null;
    String input1=null;
    String output1=null;
    String input2=null;
    String output2=null;
    private boolean game=false;
    private Point p1Start=new Point(80, 80);
    private Point p2Start=new Point(620, 385);
    private Point p1_cord=new Point(80, 80);
    private Point p2_cord=new Point(650, 650);
    private Map<Integer, Point> routeTop=new HashMap<Integer, Point>();
    private Map<Integer, Point> routeBot=new HashMap<Integer, Point>();
    private JButton next=new JButton("Next");
    private boolean first=true;
    private JTextField score1=new JTextField("0");
    private JTextField score2=new JTextField("0");
    private Timer time;
    public Host() throws IOException {
        this.setSize(new Dimension(600, 700));
        ImageIcon a = new ImageIcon(this.getClass().getResource("map.png"));
        map = a.getImage();
        this.initServer();
        this.setLayout(null);
        next.setVisible(true);
        next.setActionCommand("next");
        next.addActionListener(this);
        next.setBounds(270, 500, 100, 50);
        this.add(next);
        time=new Timer(1000, this);
        time.setActionCommand("time");
        time.start();

        score1.setVisible(true);
        score2.setVisible(true);
        score1.setBounds(100, 550, 80, 80);
        score1.setActionCommand("add1");
        score1.addActionListener(this);
        score2.setActionCommand("add2");
        score2.addActionListener(this);
        
        score2.setBounds(450, 550, 80, 80);
        this.add(score1);
        this.add(score2);
        
        routeTop.put(0, new Point(620, 100));
        routeTop.put(1, new Point(520, 160));
        routeTop.put(2, new Point(400, 240));
        routeTop.put(3, new Point(300, 240));
        routeTop.put(4, new Point(170, 140));
        routeTop.put(5, p1Start);
        routeTop.put(6, new Point(150, 50));
        routeTop.put(7, new Point(230, 40));
        routeTop.put(8, new Point(310, 50));
        routeTop.put(9, new Point(370, 50));
        routeTop.put(10, new Point(430, 40));
        routeTop.put(11, new Point(495, 50));

        
        routeBot.put(0, new Point(80, 385));
        routeBot.put(1, new Point(170, 300));
        routeBot.put(2, new Point(300, 240));
        routeBot.put(3, new Point(400, 240));
        routeBot.put(4, new Point(520, 310));
        routeBot.put(5, p2Start);
        routeBot.put(6, new Point(540, 430));
        routeBot.put(7, new Point(470, 420));
        routeBot.put(8, new Point(400, 430));
        routeBot.put(9, new Point(330, 420));
        routeBot.put(10, new Point(250, 420));
        routeBot.put(11, new Point(180, 427));
        
        game=true;
        //this.newGame();
        
    }
    public void newGame() throws IOException {
        //p1_cord=p1Start;
        input1=in1.readLine();
        System.out.println(input1);
        input2=in2.readLine();
        System.out.println(input2);
        //time.start();
    }
    public void initServer() throws IOException {
        try {
          srvr= new ServerSocket(PORT);
        } catch (IOException ex) {
        }
        try {
            soc1=srvr.accept();
            this.initWriterReader1();
            //out1.println("Ready");
            System.out.println("Ser one");
        } catch (IOException ex) {
        }
        
        
        
        try {
            soc2=srvr.accept();
            this.initWriterReader2();
            //out2.println("Ready");
            System.out.println("Ser two");
        } catch (IOException ex) {
        }
        
    }
    
    private void initWriterReader1() {
        try {
            out1 =new PrintWriter(soc1.getOutputStream(), true);
            in1 = new BufferedReader(new InputStreamReader(soc1.getInputStream()));
        } catch (IOException ex) {
        }
    }
    private void initWriterReader2() {
        try {
            out2 =new PrintWriter(soc2.getOutputStream(), true);
            in2 = new BufferedReader(new InputStreamReader(soc2.getInputStream()));
        } catch (IOException ex) {
        }
    }
    
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(map, 0, 0, this);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Consolas", Font.BOLD, 22));
        if(game){
                g.drawString("waiting for response", 200, 480);
        } else {
                 g.drawString("Player 1: "+input1+"\nPlayer 2: "+input2, 180, 480);

        }
    }
    public void windowClosing(WindowEvent e) throws IOException {
         soc1.close();
         soc2.close();
         out2.close();
         in2.close();
         out1.close();
         in1.close();
         srvr.close();
    };
    public void response() {
        if(input1.equals("open")&&input2.equals("open")) {
            out1.println("1");
            out2.println("1");
            score1.setText(Double.parseDouble(score1.getText())+1+"");
            score2.setText(Double.parseDouble(score2.getText())+1+"");
            game=false;
        } else if(input1.equals("close")&&input2.equals("close")) {
            out1.println("1");
            out2.println("1");
            score1.setText(Double.parseDouble(score1.getText())+1+"");
            score2.setText(Double.parseDouble(score2.getText())+1+"");
        } else if(input1.equals("close")&&input2.equals("open")) {
            out1.println("1");
            out2.println("1");
            score1.setText(Double.parseDouble(score1.getText())+1+"");
            score2.setText(Double.parseDouble(score2.getText())+1+"");
        } else if(input1.equals("open")&&input2.equals("close")) {
            out1.println("1");
            out2.println("1");
            score1.setText(Double.parseDouble(score1.getText())+1+"");
            score2.setText(Double.parseDouble(score2.getText())+1+"");
        } else if(input1.equals("short")&&input2.equals("open")) {
            out1.println("2");
            out2.println("1");
            score1.setText(Double.parseDouble(score1.getText())+2+"");
            score2.setText(Double.parseDouble(score2.getText())+1+"");
        } else if(input1.equals("short")&&input2.equals("close")) {
            out1.println("0");
            out2.println("1");
            score2.setText(Double.parseDouble(score2.getText())+1+"");
        } else if(input1.equals("close")&&input2.equals("short")) {
            out1.println("1");
            out2.println("0");
            score1.setText(Double.parseDouble(score1.getText())+1+"");
        } else if(input1.equals("short")&&input2.equals("short")) {
            out1.println("4");
            out2.println("4");
        } else if(input1.equals("open")&&input2.equals("Short")) {
            out1.println("1");
            out2.println("2");
            score1.setText(Double.parseDouble(score1.getText())+1+"");
            score2.setText(Double.parseDouble(score2.getText())+2+"");
        }
        game=false;
    }

    
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("time")) {
            repaint();
            return;
        }
        if(e.getActionCommand().equals("next")) {
            
            if(first) {
                out1.println("next");
                out2.println("next");
                System.out.println("next");
            }
            first=false;
            game=true;
            repaint();

        }
        try {
            input1=in1.readLine();
            System.out.println(input1);
            input2=in2.readLine();
            System.out.println(input1);
            if(input1!=null)
            this.response();
            repaint();
            
        } catch (IOException ex) {
            Logger.getLogger(Host.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}