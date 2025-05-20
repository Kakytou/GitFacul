package APS;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Tela6 extends JFrame {

    private JTextArea areaResultados;
    private JTextField campoBusca;
    private JButton botaoBuscar;

    public Tela6() {
        setTitle("Consulta de Pacientes");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(new Color(224, 255, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        
        gbc.gridx = 0;
        gbc.gridy = 0;
        painel.add(new JLabel("Buscar por nome:"), gbc);

        gbc.gridx = 1;
        campoBusca = new JTextField(20);
        painel.add(campoBusca, gbc);

        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        botaoBuscar = new JButton("🔍 Consultar");
        botaoBuscar.setPreferredSize(new Dimension(200, 40));
        botaoBuscar.addActionListener(e -> consultarPacientes());
        painel.add(botaoBuscar, gbc);

        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        areaResultados = new JTextArea();
        areaResultados.setFont(new Font("Monospaced", Font.PLAIN, 14));
        areaResultados.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaResultados);
        painel.add(scroll, gbc);

        add(painel);
        setVisible(true);
    }

    private void consultarPacientes() {
        String nomeBusca = campoBusca.getText().trim();
        areaResultados.setText("");

        String url = "jdbc:mysql://localhost:3306/aps";
        String usuario = "root";
        String senha = "APS-2025";

        try (Connection conn = DriverManager.getConnection(url, usuario, senha)) {
            String sql = "SELECT id_paciente, nome_paciente, cpf, nome_cidade, tipo_diabetes, estado_tratamento " +
                         "FROM pacientes WHERE nome_paciente LIKE ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + nomeBusca + "%");

            ResultSet rs = stmt.executeQuery();
            boolean encontrou = false;
            while (rs.next()) {
                encontrou = true;
                areaResultados.append("ID: " + rs.getInt("id_paciente") + "\n");
                areaResultados.append("Nome: " + rs.getString("nome_paciente") + "\n");
                areaResultados.append("CPF: " + rs.getString("cpf") + "\n");
                areaResultados.append("Cidade: " + rs.getString("nome_cidade") + "\n");
                areaResultados.append("Tipo de Diabetes: " + rs.getString("tipo_diabetes") + "\n");
                areaResultados.append("Estado do Tratamento: " + rs.getString("estado_tratamento") + "\n");
                areaResultados.append("----------------------------------------------------\n");
            }

            if (!encontrou) {
                areaResultados.setText("Nenhum paciente encontrado com esse nome.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro na consulta: " + ex.getMessage());
        }
    }
}
