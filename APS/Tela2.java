package APS;

import javax.swing.*;
import java.awt.*;

public class Tela2 extends JFrame {

    JPanel panel,panel2;

    Tela2(){

        panel = new JPanel();
        panel2 = new JPanel();


        //JPanel

        panel.setBackground(Color.red);
        panel.setPreferredSize(new Dimension(0,100));
        panel2.setBackground(Color.blue);


        //Fim JPanel

        this.setLayout(new BorderLayout());
        this.add(panel,BorderLayout.NORTH);
        this.add(panel2,BorderLayout.CENTER);
        this.setTitle("Treinin Segunda Tela");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900,900);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

}

