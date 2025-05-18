package APS;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Tela5 extends JFrame {

    JTable tabela;
    DefaultTableModel modelo;
    JButton botaoExcluir;

    public Tela5() {
        setTitle("Deletar Pacientes");
        setSize(950, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(0xE1F5FE)); // fundo geral

        Font fonteTitulo = new Font("Times New Roman", Font.BOLD, 24);
        Font fontePadrao = new Font("Segoe UI", Font.PLAIN, 14);

        // Cabeçalho com fundo azul claro e fonte grande
        JLabel titulo = new JLabel("Central da Saúde - Alterar Pacientes", JLabel.CENTER);
        titulo.setFont(fonteTitulo);
        titulo.setForeground(new Color(0, 102, 102)); // azul escuro
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(titulo, BorderLayout.NORTH);

        // Tabela com modelo não editável
        modelo = new DefaultTableModel(new String[]{
            "ID", "Nome", "CPF", "Cidade", "Tipo Diabetes", "Estado Tratamento"
        }, 0) {
            public boolean isCellEditable(int row, int column) {
                return false; // impede edição direta
            }
        };

        tabela = new JTable(modelo);
        tabela.setFont(fontePadrao);
        tabela.setRowHeight(28);
        tabela.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabela.setSelectionBackground(new Color(0xBBDEFB)); // azul claro
        tabela.setBackground(Color.WHITE);
        tabela.setGridColor(Color.LIGHT_GRAY);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        add(scroll, BorderLayout.CENTER);

        // Botão Exluir
        botaoExcluir = new JButton("Excluir paciente");
        botaoExcluir.setFont(fontePadrao);
        botaoExcluir.setFocusPainted(false);
        botaoExcluir.setBackground(new Color(173, 216, 230));
        botaoExcluir.setForeground(Color.BLACK);
        botaoExcluir.setPreferredSize(new Dimension(200, 40));

        JPanel painelInferior = new JPanel();
        painelInferior.setLayout(new FlowLayout(FlowLayout.CENTER));
        painelInferior.setBackground(new Color(0xE1F5FE)); // igual fundo
        painelInferior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        painelInferior.add(botaoExcluir);
        add(painelInferior, BorderLayout.SOUTH);

        botaoExcluir.addActionListener(e -> editarPaciente());
        carregarDados();
        setVisible(true);
    }

    private void carregarDados() {
        try {
            String url = "jdbc:mysql://localhost:3306/aps";
            String usuario = "root";
            String senha = "APS-2025";

            Connection conn = DriverManager.getConnection(url, usuario, senha);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id_paciente, nome_paciente, cpf, nome_cidade, tipo_diabetes, estado_tratamento FROM pacientes");

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("id_paciente"),
                    rs.getString("nome_paciente"),
                    rs.getString("cpf"),
                    rs.getString("nome_cidade"),
                    rs.getString("tipo_diabetes"),
                    rs.getString("estado_tratamento")
                });
            }

            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + e.getMessage());
        }
    }

    private void editarPaciente() {
    int linha = tabela.getSelectedRow();
    if (linha == -1) {
        JOptionPane.showMessageDialog(this, "Selecione um paciente para excluir.");
        return;
    }

    int id = (int) modelo.getValueAt(linha, 0);
    String nome = (String) modelo.getValueAt(linha, 1);

    int confirmacao = JOptionPane.showConfirmDialog(
        this,
        "Tem certeza que deseja excluir o paciente \"" + nome + "\"?",
        "Confirmar Exclusão",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.WARNING_MESSAGE
    );

    if (confirmacao == JOptionPane.YES_OPTION) {
        try {
            String url = "jdbc:mysql://localhost:3306/aps";
            String usuario = "root";
            String senha = "APS-2025";

            Connection conn = DriverManager.getConnection(url, usuario, senha);
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM pacientes WHERE id_paciente=?");
            stmt.setInt(1, id);

            stmt.executeUpdate();
            conn.close();

            JOptionPane.showMessageDialog(this, "Paciente excluído com sucesso.");
            modelo.setRowCount(0); // limpa a tabela
            carregarDados(); // recarrega os dados
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir paciente: " + ex.getMessage());
        }
    }
}



}
