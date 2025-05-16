package APS;

import javax.swing.*;
import java.awt.*;

public class Tela2 extends JFrame {

    JButton botao1, botao2, botao3, botao4, botaoPesquisa;
    JPanel painelCentral, painelTopo, painelDireita;
    JLabel titulo;

    public Tela2() {
        // Configuração da janela principal
        setTitle("Área da Saúde - Tela Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 900);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Estilo visual
        Color fundo = new Color(224, 255, 255); // cor de fundo suave azulada
        Font fonteBotao = new Font("Candara", Font.PLAIN, 24);

        // Título
        titulo = new JLabel("Bem-vindo à Central da Saúde");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Candara", Font.BOLD, 36));
        titulo.setForeground(new Color(0, 102, 102));

        // Painel Superior (contendo o título)
        painelTopo = new JPanel(new BorderLayout());
        painelTopo.setBackground(fundo);
        painelTopo.setPreferredSize(new Dimension(100, 100));
        painelTopo.add(titulo, BorderLayout.CENTER);

    

        // Painel Central com botões em coluna
        painelCentral = new JPanel();
        painelCentral.setLayout(new GridLayout(4, 1, 20, 20)); // 4 linhas, 1 coluna
        painelCentral.setBorder(BorderFactory.createEmptyBorder(60, 200, 60, 200));
        painelCentral.setBackground(fundo);

        botao1 = new JButton("Cadastrar");
        // Ação do botão "Consulta"
        botao1.addActionListener(e -> {
        new Tela3(); });

        botao2 = new JButton("Alterar");
        botao3 = new JButton("Deletar");
        botao4 = new JButton("Consultar");

        for (JButton botao : new JButton[]{botao1, botao2, botao3, botao4}) {
            botao.setFont(fonteBotao);
            botao.setBackground(new Color(173, 216, 230));
            botao.setForeground(Color.BLACK);
            botao.setFocusPainted(false);
            painelCentral.add(botao);
        }

        // Adicionando os painéis ao frame
        add(painelTopo, BorderLayout.NORTH);
        add(painelCentral, BorderLayout.CENTER);

        setVisible(true);
    }
}
