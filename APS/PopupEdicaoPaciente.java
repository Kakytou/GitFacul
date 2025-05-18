package APS;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class PopupEdicaoPaciente extends JDialog {

    private JTextField campoNome;
    private JComboBox<String> comboCidade, comboTipo, comboEstado;
    private String cpfOriginal;
    private Tela4 telaPai;

    public PopupEdicaoPaciente(Tela4 telaPai, String cpf) {
        super(telaPai, "Editar Paciente", true); // Janela modal
        this.cpfOriginal = cpf;
        this.telaPai = telaPai;

        setSize(500, 400);
        setLocationRelativeTo(telaPai);
        setLayout(new GridLayout(6, 2, 10, 10));
        getContentPane().setBackground(new Color(224, 255, 255));

        campoNome = new JTextField();
        comboCidade = new JComboBox<>(new String[]{
                "São Paulo", "Guarulhos", "Campinas", 
                "São Bernardo do Campo", "Santo André"
        });
        comboTipo = new JComboBox<>(new String[]{"Tipo 1", "Tipo 2"});
        comboEstado = new JComboBox<>(new String[]{
                "Sem tratamento", "Em tratamento", "Internado", "Óbito"
        });

        add(new JLabel("Nome:"));
        add(campoNome);
        add(new JLabel("Cidade:"));
        add(comboCidade);
        add(new JLabel("Tipo de Diabetes:"));
        add(comboTipo);
        add(new JLabel("Estado do Tratamento:"));
        add(comboEstado);

        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");

        btnSalvar.addActionListener(e -> salvarAlteracoes());
        btnCancelar.addActionListener(e -> dispose());

        add(btnSalvar);
        add(btnCancelar);

        carregarDadosDoBanco();
        setVisible(true); // ESSENCIAL!
    }

    private void carregarDadosDoBanco() {
        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/aps", "root", "APS-2025");
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM pacientes WHERE cpf = ?");
            stmt.setString(1, cpfOriginal);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                campoNome.setText(rs.getString("nome_paciente"));
                comboCidade.setSelectedItem(rs.getString("nome_cidade"));
                comboTipo.setSelectedItem(rs.getString("tipo_diabetes"));
                comboEstado.setSelectedItem(rs.getString("estado_tratamento"));
            }

            conn.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + ex.getMessage());
        }
    }

    private void salvarAlteracoes() {
        String nome = campoNome.getText();
        String cidade = comboCidade.getSelectedItem().toString();
        int idCidade = comboCidade.getSelectedIndex() + 1;
        String tipo = comboTipo.getSelectedItem().toString();
        String estado = comboEstado.getSelectedItem().toString();

        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/aps", "root", "APS-2025");
            PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE pacientes SET nome_paciente = ?, nome_cidade = ?, id_cidade = ?, tipo_diabetes = ?, estado_tratamento = ? WHERE cpf = ?");

            stmt.setString(1, nome);
            stmt.setString(2, cidade);
            stmt.setInt(3, idCidade);
            stmt.setString(4, tipo);
            stmt.setString(5, estado);
            stmt.setString(6, cpfOriginal);

            stmt.executeUpdate();
            conn.close();

            JOptionPane.showMessageDialog(this, "Alterações salvas com sucesso!");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar alterações: " + ex.getMessage());
        }
    }
}
