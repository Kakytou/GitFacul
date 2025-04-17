package APS;

import APS.Mouse.mouseListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Tela extends JFrame implements ActionListener {


    JPanel Mainpanel_North,Mainpanel_Center, Cpanel3;
    JLabel label,texto;
    JButton botao;
    GridBagConstraints c;

    Tela(){
        Mainpanel_North = new JPanel();
        Mainpanel_Center = new JPanel();
        Cpanel3 = new JPanel();
        c = new GridBagConstraints();
        botao = new JButton("Comece aqui");
        label = new JLabel();
        texto = new JLabel();





        //JButton

        botao.setForeground(Color.black);
        botao.setFocusable(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setPreferredSize(new Dimension(200,35));
        botao.addActionListener(this);
        botao.addMouseListener(new mouseListener());

        //Fim JButton


        //JLabel

        texto.setFont(new Font("Candara",Font.PLAIN,24));
        texto.setText("Saiba o que é");


        label.setForeground(Color.black);
        label.setText("Asma");
        label.setFont(new Font("Candara", Font.BOLD,50));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        //FimJLabel

        //JPanel

        Cpanel3.setPreferredSize(new Dimension(600,600));
        Cpanel3.setLayout(new GridBagLayout());
        Cpanel3.setBackground(new Color(224,255,255));

        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(20,0,10,0);
        Cpanel3.add(label,c);

        c.gridx = 0;
        c.gridy = 2;
        Cpanel3.add(texto,c);

        c.gridx = 0;
        c.gridy = 3;
        Cpanel3.add(botao,c);


        Mainpanel_Center.add(Cpanel3);
        Mainpanel_Center.setBackground(new Color(224,255,255));


        //Fim JPanel

        this.setLayout(new BorderLayout());
        this.add(Mainpanel_Center,BorderLayout.CENTER);
        this.setTitle("Treinin Tela Principal");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900,900);
        this.setLocationRelativeTo(null);
        this.setVisible(true);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==botao){
            this.dispose();
            Tela2 tela2 = new Tela2();
        }
    }
}


