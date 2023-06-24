import jaco.mp3.player.MP3Player;


import javax.imageio.stream.FileImageOutputStream;
import javax.swing.*;
import javax.swing.plaf.metal.MetalIconFactory;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.sql.*;
import java.util.Arrays;

public class SearchName extends JFrame implements ActionListener {

    JTextField search;
    JButton find,back,sound,stop;

    JLabel lab,jb;
    String str;
    MP3Player mp3;
    SearchName(){
        setLayout(null);

        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("icons/frame3.jpg"));
        Image i2=i1.getImage().getScaledInstance(700,700,Image.SCALE_AREA_AVERAGING);
        ImageIcon i3=new ImageIcon(i2);
        JLabel jl=new JLabel(i3);
        jl.setBounds(0,0,700,700);
        add(jl);

        lab=new JLabel("Enter the name to find the animal ");
        lab.setBounds(150,20,430,70);
        lab.setForeground(new Color(6, 42, 43));
        lab.setFont(new Font("Raleway",Font.BOLD,25));
        jl.add(lab);


        find =new JButton("Find");
        find.setBounds(400,100,100,30);
        find.setFont(new Font("System",Font.BOLD,16));
        find.addActionListener(this);
        jl.add(find);

        sound=new JButton("S");
        sound.setBounds(530,100,100,30);
        sound.setFont(new Font("System",Font.BOLD,16));
        sound.addActionListener(this);
        jl.add(sound);

        stop=new JButton("P");
        stop.setBounds(640,100,50,30);
        stop.setFont(new Font("System",Font.BOLD,16));
        stop.addActionListener(this);
        jl.add(stop);

        search=new JTextField();
        search.setBounds(160,100,210,30);
        search.setFont(new Font("System",Font.BOLD,19));
        jl.add(search);


//        back =new JButton("Back");
//        back.setBounds(440,100,100,30);
//        back.addActionListener(this);
//        add(back);


        jb = new JLabel();
        jb.setBounds(160, 230, 360, 310);
        jl.add(jb);


        setUndecorated(true);

        getContentPane().setBackground(Color.white);
        setSize(700,700);
        setLocation(364,90);
        setVisible(true);
    }
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==find) {
            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost/images", "root", "00000");
                Statement s = con.createStatement();
                String query = "select image from pics where name = '" + search.getText().toLowerCase()+ "'";

                ResultSet rs = s.executeQuery(query);
               File file=new File("C:/ANIMALS/"+search.getText().toLowerCase()+".jpg");

               if(rs.next()){
                   InputStream input=rs.getBinaryStream("image"); //Using inputstream we can read data as an ordered sequence of Bytes..
                   byte[] buffer=new byte[1024];
                   FileOutputStream fo=new FileOutputStream(file);//writing data to a destination..

                   while(input.read(buffer)>0){
                       fo.write(buffer);
                   }
                   String path=file.getAbsolutePath();
                   ImageIcon ii=new ImageIcon(path);
                   Image im=ii.getImage().getScaledInstance(jb.getWidth(),jb.getHeight(),Image.SCALE_DEFAULT);
                   ImageIcon x=new ImageIcon(im);
                   jb.setIcon(x);//setting icon
               }
               else{
                   JOptionPane.showMessageDialog(null," '"+search.getText().toUpperCase()+"' not found in the DataBase");
               }

                }catch (Exception e){
                System.out.println(e);
            }
        }
else if(ae.getSource()==sound){
          try{

           // System.out.println(search.getText()+"  this is it");
            str="C:/SOUNDS/"+search.getText().toLowerCase()+".mp3";
            mp3=new MP3Player(new File(str));
            mp3.play();

               throw new FileNotFoundException("SOUND NOT FOUND");
            } catch (FileNotFoundException e) {
                //JOptionPane.showMessageDialog(null,search.getText().toUpperCase()+" sound not found");

                //System.out.println(e);
            }
        }
if(ae.getSource()==stop)
            mp3.stop();

        //System.out.println(mp3.getPlayList());
    }

    public static void main(String[] args) {
        new SearchName();
    }
}
