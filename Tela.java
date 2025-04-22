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

        // Cores mais suaves para o tema de saúde
        Color corFundo = new Color(240, 255, 240);  // Verde bem claro
        Color corBotao = new Color(72, 181, 98);  // Verde suave para o botão

        // Configurações do botão de login
        botaoLogin.setForeground(Color.white);
        botaoLogin.setBackground(corBotao);
        botaoLogin.setFocusable(false);
        botaoLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botaoLogin.setPreferredSize(new Dimension(200, 35));
        botaoLogin.addActionListener(this);

        // Configuração dos labels com fontes amigáveis e cores suaves
        label.setForeground(new Color(34, 139, 34));  // Verde escuro para destaque
        label.setText("Bem-vindo à Saúde");
        label.setFont(new Font("Segoe UI", Font.BOLD, 50));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);

        texto.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        texto.setText("Entre para acessar sua conta");

        labelUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        labelSenha.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        // Tamanho maior para os campos de texto
        campoUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        campoSenha.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        
        // Aumentando o tamanho dos campos para garantir que sejam grandes
        campoUsuario.setPreferredSize(new Dimension(300, 40));  // Tamanho fixo maior para o campo de usuário
        campoSenha.setPreferredSize(new Dimension(300, 40));  // Tamanho fixo maior para o campo de senha

        // Configuração do painel principal
        Cpanel3.setPreferredSize(new Dimension(600, 600));
        Cpanel3.setLayout(new GridBagLayout());
        Cpanel3.setBackground(corFundo);

        // Organizando os componentes no GridBagLayout
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(20, 0, 40, 0);  // Aumentei o espaçamento abaixo do título
        Cpanel3.add(label, c);

        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(10, 0, 5, 0);  // Menos espaçamento entre o label e campo do usuário
        Cpanel3.add(labelUsuario, c);

        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(5, 0, 20, 0);  // Menor espaçamento entre o label do usuário e o campo de usuário
        Cpanel3.add(campoUsuario, c);

        c.gridx = 0;
        c.gridy = 3;
        c.insets = new Insets(30, 0, 5, 0);  // Maior espaçamento entre o campo de usuário e o label de senha
        Cpanel3.add(labelSenha, c);

        c.gridx = 0;
        c.gridy = 4;
        c.insets = new Insets(5, 0, 20, 0);  // Menor espaçamento entre o label da senha e o campo de senha
        Cpanel3.add(campoSenha, c);

        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2;
        c.insets = new Insets(40, 0, 0, 0);  // Maior espaçamento acima do botão
        Cpanel3.add(botaoLogin, c);

        Mainpanel_Center.add(Cpanel3);
        Mainpanel_Center.setBackground(corFundo);

        // Finalizando configurações da janela
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

            // Aqui você pode adicionar validações para o login
            if (usuario.equals("admin") && String.valueOf(senha).equals("1234")) {
                JOptionPane.showMessageDialog(this, "Login bem-sucedido!");
                // Aqui você pode chamar a próxima tela
                this.dispose(); // Fecha a tela de login
                Tela2 Tela2 = new Tela2(); // Chama a próxima tela
            } else {
                JOptionPane.showMessageDialog(this, "Usuário ou senha incorretos.");
            }
        }
    }
}
