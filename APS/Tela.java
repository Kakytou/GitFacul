package APS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Tela extends JFrame implements ActionListener {

    JPanel Mainpanel_North, Mainpanel_Center, Cpanel3;
    JLabel label, texto, labelUsuario, labelSenha;
    JTextField campoUsuario;
    JPasswordField campoSenha;
    JButton botaoLogin;
    GridBagConstraints c;

    Tela() {
        Mainpanel_North = new JPanel();
        Mainpanel_Center = new JPanel();
        Cpanel3 = new JPanel();
        c = new GridBagConstraints();

        botaoLogin = new JButton("Login");
        label = new JLabel();
        texto = new JLabel();
        labelUsuario = new JLabel("Usuário:");
        labelSenha = new JLabel("Senha:");
        campoUsuario = new JTextField(20);
        campoSenha = new JPasswordField(20);

        
        Color corFundo = new Color(240, 255, 240);
        Color corBotao = new Color(72, 181, 98);

       
        botaoLogin.setForeground(Color.white);
        botaoLogin.setBackground(corBotao);
        botaoLogin.setFocusable(false);
        botaoLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botaoLogin.setPreferredSize(new Dimension(200, 35));
        botaoLogin.addActionListener(this);

        
        label.setForeground(new Color(34, 139, 34));
        label.setText("Bem-vindo!");
        label.setFont(new Font("Segoe UI", Font.BOLD, 50));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);

        texto.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        texto.setText("Entre para acessar sua conta");

        labelUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        labelSenha.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        
        campoUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        campoSenha.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        
        
        campoUsuario.setPreferredSize(new Dimension(300, 40));
        campoSenha.setPreferredSize(new Dimension(300, 40));

        
        Cpanel3.setPreferredSize(new Dimension(600, 600));
        Cpanel3.setLayout(new GridBagLayout());
        Cpanel3.setBackground(corFundo);

        
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(20, 0, 40, 0);
        Cpanel3.add(label, c);

        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(10, 0, 5, 0);
        Cpanel3.add(labelUsuario, c);

        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(5, 0, 20, 0);
        Cpanel3.add(campoUsuario, c);

        c.gridx = 0;
        c.gridy = 3;
        c.insets = new Insets(30, 0, 5, 0);
        Cpanel3.add(labelSenha, c);

        c.gridx = 0;
        c.gridy = 4;
        c.insets = new Insets(5, 0, 20, 0);
        Cpanel3.add(campoSenha, c);

        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2;
        c.insets = new Insets(40, 0, 0, 0);
        Cpanel3.add(botaoLogin, c);

        Mainpanel_Center.add(Cpanel3);
        Mainpanel_Center.setBackground(corFundo);

        
        this.setLayout(new BorderLayout());
        this.add(Mainpanel_Center, BorderLayout.CENTER);
        this.setTitle("Tela de Login - Saúde");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 900);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botaoLogin) {
            String usuario = campoUsuario.getText();
            char[] senha = campoSenha.getPassword();

            
            if (usuario.equals("admin") && String.valueOf(senha).equals("1234")) {
                JOptionPane.showMessageDialog(this, "Login bem-sucedido!");
                
                this.dispose();
                Tela2 Tela2 = new Tela2();
            } else {
                JOptionPane.showMessageDialog(this, "Usuário ou senha incorretos.");
            }
        }
    }
}
