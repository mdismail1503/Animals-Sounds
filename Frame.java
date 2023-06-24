import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class Frame extends JFrame implements ActionListener {
 JButton start;
    Frame(){
        setLayout(null);

        start=new JButton("Get Started \uD83D\uDE00");
        start.setBounds(400,300,140,30);
        start.setFont(new Font("System",Font.BOLD,15));
        start.setBackground(new Color(157, 173, 233));
        start.setForeground(Color.black);
        start.addActionListener(this);
        add(start);

ImageIcon im=new ImageIcon(ClassLoader.getSystemResource("icons/frame4.jpg"));
Image i=im.getImage().getScaledInstance(700,900, Image.SCALE_SMOOTH);
ImageIcon ia=new ImageIcon(i);
JLabel j=new JLabel(ia);
j.setBounds(0,0,700,900);
add(j);

setUndecorated(true);
        setLocation(350,10);
        setSize(700,900);
        setVisible(true);

    }

    public void actionPerformed(ActionEvent ae){
        setVisible(false);
new SearchName();
    }
    public static void main(String[] args) {
        new Frame();
    }
}

