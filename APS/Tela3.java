package APS;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Tela3 extends JFrame implements ActionListener {

    private JTextField campoNome;
    private JFormattedTextField campoCpf;
    private JComboBox<String> comboCidade, comboTipo, comboEstado;
    private JButton botaoSalvar;

    public Tela3() {
        setTitle("Registro de Paciente");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel com GridBagLayout para maior controle
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(new Color(224, 255, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nome do Paciente
        gbc.gridx = 0;
        gbc.gridy = 0;
        painel.add(new JLabel("Nome do Paciente:"), gbc);
        gbc.gridx = 1;
        campoNome = new JTextField(20);
        painel.add(campoNome, gbc);

        // CPF com formatação
        gbc.gridx = 0;
        gbc.gridy++;
        painel.add(new JLabel("CPF:"), gbc);
        try {
            MaskFormatter mask = new MaskFormatter("###.###.###-##");
            mask.setPlaceholderCharacter('_');
            campoCpf = new JFormattedTextField(mask);
            campoCpf.setColumns(20);
        } catch (Exception e) {
            campoCpf = new JFormattedTextField();
        }
        gbc.gridx = 1;
        painel.add(campoCpf, gbc);

        // Cidade
        gbc.gridx = 0;
        gbc.gridy++;
        painel.add(new JLabel("Cidade:"), gbc);
        gbc.gridx = 1;
        comboCidade = new JComboBox<>(new String[]{
                "São Paulo", "Guarulhos", "Campinas", 
                "São Bernardo do Campo", "Santo André"
        });
        painel.add(comboCidade, gbc);

        // Tipo de Diabetes
        gbc.gridx = 0;
        gbc.gridy++;
        painel.add(new JLabel("Tipo de Diabetes:"), gbc);
        gbc.gridx = 1;
        comboTipo = new JComboBox<>(new String[]{"Tipo 1", "Tipo 2"});
        painel.add(comboTipo, gbc);

        // Estado do Tratamento
        gbc.gridx = 0;
        gbc.gridy++;
        painel.add(new JLabel("Estado do Tratamento:"), gbc);
        gbc.gridx = 1;
        comboEstado = new JComboBox<>(new String[]{
                "Sem tratamento", "Em tratamento", "Internado", "Óbito"
        });
        painel.add(comboEstado, gbc);

        // Botão Salvar
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        botaoSalvar = new JButton("Salvar Registro");
        botaoSalvar.setPreferredSize(new Dimension(200, 40));
        botaoSalvar.addActionListener(this);
        painel.add(botaoSalvar, gbc);

        add(painel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String nome = campoNome.getText();
        String cpf = campoCpf.getText();
        String cidade = comboCidade.getSelectedItem().toString();
        int id_cidade = comboCidade.getSelectedIndex()+ 1;
        String tipo = comboTipo.getSelectedItem().toString();
        String estado = comboEstado.getSelectedItem().toString();

        try {
            // Altere os valores abaixo conforme o seu ambiente:
            String url = "jdbc:mysql://localhost:3306/aps";  // Substituir
            String usuario = "root";
            String senha = "Ka1234568!";

            Connection conn = DriverManager.getConnection(url, usuario, senha);
            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO pacientes (nome_paciente, cpf, id_cidade, nome_cidade, tipo_diabetes, estado_tratamento) " +
                "VALUES (?, ?, ?, ?, ?, ?)"
            );

            stmt.setString(1, nome);
            stmt.setString(2, cpf);
            stmt.setInt(3, id_cidade);
            stmt.setString(4, cidade);
            stmt.setString(5, tipo);
            stmt.setString(6, estado);

            stmt.executeUpdate();
            conn.close();

            JOptionPane.showMessageDialog(this, "Registro salvo com sucesso!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
        }
    }
}
