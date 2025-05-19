package APS;

import javax.swing.*;
import java.awt.*;

public class Tela2 extends JFrame {

    JButton botao1, botao2, botao3, botao4, botao5;
    JPanel painelCentral, painelTopo, painelEstatisticas;
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
        titulo.setFont(new Font("Times New Roman", Font.BOLD, 36));
        titulo.setForeground(new Color(0, 102, 102));

        // Painel Superior (contendo o título)
        painelTopo = new JPanel(new BorderLayout());
        painelTopo.setBackground(fundo);
        painelTopo.setPreferredSize(new Dimension(100, 100));
        painelTopo.add(titulo, BorderLayout.CENTER);

        // Criando os botões
        botao1 = new JButton("Cadastrar");
        botao1.addActionListener(e -> new Tela3());

        botao2 = new JButton("Alterar");
        botao2.addActionListener(e -> new Tela4());

        botao3 = new JButton("Deletar");
        botao3.addActionListener(e -> new Tela5());

        botao4 = new JButton("Consultar");
        botao4.addActionListener(e -> new Tela6());

        botao5 = new JButton("Estatísticas");
        botao5.addActionListener(e -> new Tela7());

        // Painel Central com os 4 primeiros botões em coluna
        painelCentral = new JPanel(new GridLayout(4, 1, 20, 20));
        painelCentral.setBorder(BorderFactory.createEmptyBorder(60, 200, 20, 200));
        painelCentral.setBackground(fundo);

        for (JButton botao : new JButton[]{botao1, botao2, botao3, botao4}) {
            botao.setFont(fonteBotao);
            botao.setBackground(new Color(173, 216, 230));
            botao.setForeground(Color.BLACK);
            botao.setFocusPainted(false);
            painelCentral.add(botao);
        }

        // Painel separado para o botão "Estatísticas" centralizado
        painelEstatisticas = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelEstatisticas.setBackground(fundo);
        painelEstatisticas.setBorder(BorderFactory.createEmptyBorder(20, 200, 60, 200));

        botao5.setFont(fonteBotao);
        botao5.setBackground(new Color(173, 216, 230));
        botao5.setForeground(Color.BLACK);
        botao5.setFocusPainted(false);
        painelEstatisticas.add(botao5);

        // Adicionando os painéis ao frame
        add(painelTopo, BorderLayout.NORTH);
        add(painelCentral, BorderLayout.CENTER);
        add(painelEstatisticas, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Tela2());
    }
}
