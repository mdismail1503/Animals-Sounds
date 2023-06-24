import jaco.mp3.player.MP3Player;

import javax.print.attribute.standard.MediaPrintableArea;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Filer extends JFrame implements ActionListener {
    String s,str;
    MP3Player mp3;
    static JTextField text;
    JButton cancel,add,search,sound,skip;
    JLabel label;
    JTextArea tf;
    Filer(){
        setLayout(null);


        cancel=new JButton("Browse");
        cancel.setBounds(100,430,200,30);
        cancel.setFont(new Font("Raleway",Font.BOLD,18));
        cancel.addActionListener(this);
        add(cancel);

        add=new JButton("Add");
        add.setBounds(400,430,200,30);
        add.setFont(new Font("Raleway",Font.BOLD,18));
        add.addActionListener(this);
        add(add);

       label=new JLabel();
       label.setBounds(10,50,600,350);
       add(label);


       search =new JButton("search");
       search.setBounds(810,430,130,30);
        search.setFont(new Font("Raleway",Font.BOLD,18));
       search.addActionListener(this);
       add(search);

        text=new JTextField();
        text.setBounds(300,15,200,30);
        text.setFont(new Font("System",Font.BOLD,15));
       //For keyboard ENTER

        Action action=new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(text.getText()+" is shown ");
                MP3Player m=new MP3Player(new File("C:/Users/Ismart Ismail/SOUNDS/starboy.mp3"));
                m.play();
            }
        };

        text.addActionListener(action);
        add(text);

        sound=new JButton("Sound");
        sound.setBounds(540,15,100,30);
        sound.setFont(new Font("Raleway",Font.BOLD,18));
        sound.addActionListener(this);
        add(sound);

        skip=new JButton("Skip");
        skip.setBounds(700,15,100,30);
        skip.setFont(new Font("Raleway",Font.BOLD,18));
        skip.addActionListener(this);
        add(skip);

        getContentPane().setBackground(Color.white);
        setSize(1000,500);
        setLocation(300,250);
        setVisible(true);
    }
    public ImageIcon ResizeImage(String ImagePath){
        ImageIcon MyImage=new ImageIcon(ImagePath);
        Image img=MyImage.getImage().getScaledInstance(label.getWidth(),label.getHeight(),Image.SCALE_DEFAULT);
        return new ImageIcon(img);
    }

    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==cancel) { //Browse
            JFileChooser file = new JFileChooser("C:/ANIMALS");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg", "png","jpeg");//Shows filtered files...
            file.addChoosableFileFilter(filter);//applies the filter..

            int result = file.showDialog(null,"Open"); //file.showDialog(null,"some name");
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = file.getSelectedFile();//From file chooser we select a file and store in a selected file..
                String path = selectedFile.getAbsolutePath();
                s=path;
                label.setIcon(ResizeImage(path));
//           Image i=(Image) label.getIcon();
//           JLabel l=new JLabel((Icon) i);
            } else if (result == JFileChooser.CANCEL_OPTION) {
                System.out.println("No File Selected");
            }
        }
         else if(ae.getSource()==add){
            try{
                Connection con= DriverManager.getConnection("jdbc:mysql://localhost/images","root","00000");
                PreparedStatement ps=con.prepareStatement("insert into pics values(?,?)");
                InputStream is=new FileInputStream(s); //used when reading data from a file..
                if(text.getText().equals("")){
                    JOptionPane.showMessageDialog(null,"Name Not specified ");
                }
                else{
                ps.setString(1,text.getText().toLowerCase());
                ps.setBlob(2,is);
                ps.executeUpdate();}
            }catch (Exception e){
                System.out.println(e);
            }
        }
         //Playing sounds:

         else if(ae.getSource()==sound){
File path=new File("C:/Users/Ismart Ismail/SOUNDS");
File files[]=path.listFiles();
            assert files != null;
            LinkedList<File> al=new LinkedList<>(Arrays.asList(files));
            System.out.println(al.size());

        }
//         else if(ae.getSource()==skip){
//             mp3.stop();
//             mp3.skipForward();
//             mp3.play();
//        }
        else{
            setVisible(false);
            new SearchName().setVisible(true);
        }

    }
    public static void main(String[] args) {
        new Filer();

    }

}
