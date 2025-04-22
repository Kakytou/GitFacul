package APS;

import javax.swing.*;
import java.awt.*;

public class Tela3 extends JFrame {

    public Tela3() {
        setTitle("Registro de Consulta");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Estilo
        Color fundo = new Color(224, 255, 255);
        Font fonteLabel = new Font("Candara", Font.PLAIN, 20);

        // Título
        JLabel titulo = new JLabel("Registro de Consulta");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Candara", Font.BOLD, 28));
        titulo.setForeground(new Color(0, 102, 102));

        // Painel principal
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(fundo);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.fill = GridBagConstraints.HORIZONTAL;

        // Campo: Nome do paciente
        JLabel nomeLabel = new JLabel("Nome do Paciente:");
        nomeLabel.setFont(fonteLabel);
        JTextField campoNome = new JTextField(20);
        campoNome.setFont(fonteLabel);

        c.gridx = 0;
        c.gridy = 0;
        painel.add(nomeLabel, c);
        c.gridx = 1;
        painel.add(campoNome, c);

        // Campo: Cidade (ComboBox)
        JLabel cidadeLabel = new JLabel("Cidade:");
        cidadeLabel.setFont(fonteLabel);
        String[] cidades = {"São Paulo", "Guarulhos", "Campinas", "São Bernardo do Campo", "Santo André"};
        JComboBox<String> comboCidade = new JComboBox<>(cidades);
        comboCidade.setFont(fonteLabel);

        c.gridx = 0;
        c.gridy = 1;
        painel.add(cidadeLabel, c);
        c.gridx = 1;
        painel.add(comboCidade, c);

        // Botão confirmar
        JButton confirmar = new JButton("Confirmar Registro");
        confirmar.setFont(fonteLabel);
        confirmar.setBackground(new Color(173, 216, 230));
        confirmar.setFocusPainted(false);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        painel.add(confirmar, c);

        // Adiciona ao frame
        add(titulo, BorderLayout.NORTH);
        add(painel, BorderLayout.CENTER);

        setVisible(true);
    }
}
