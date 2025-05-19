package APS;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Tela5 extends JFrame {

    JTable tabela;
    JTextField campoBusca;
    JButton botaoBuscar;
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
        JLabel titulo = new JLabel("Central da Saúde - Deletar Pacientes", JLabel.CENTER);
        titulo.setFont(fonteTitulo);
        titulo.setForeground(new Color(0, 102, 102));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(titulo, BorderLayout.NORTH);

        // Campo de busca
        campoBusca = new JTextField(20);
        campoBusca.setFont(fontePadrao);

        botaoBuscar = new JButton("Buscar");
        botaoBuscar.setFont(fontePadrao);
        botaoBuscar.setBackground(new Color(173, 216, 230));
        botaoBuscar.setFocusPainted(false);
        botaoBuscar.setToolTipText("Buscar por nome, CPF ou cidade");

        botaoBuscar.addActionListener(e -> buscarPacientes(campoBusca.getText().trim()));
        campoBusca.addActionListener(e -> botaoBuscar.doClick());

        

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
        tabela.setSelectionBackground(new Color(0xBBDEFB));
        tabela.setBackground(Color.WHITE);
        tabela.setGridColor(Color.LIGHT_GRAY);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        add(scroll, BorderLayout.CENTER);

        // Botão Excluir
        botaoExcluir = new JButton("Excluir paciente");
        botaoExcluir.setFont(fontePadrao);
        botaoExcluir.setFocusPainted(false);
        botaoExcluir.setBackground(new Color(173, 216, 230));
        botaoExcluir.setForeground(Color.BLACK);
        botaoExcluir.setPreferredSize(new Dimension(200, 40));
        botaoExcluir.addActionListener(e -> excluirPaciente());

        JPanel painelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelInferior.setBackground(new Color(0xE1F5FE));
        painelInferior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        painelInferior.setBackground(new Color(0xE1F5FE));
        add(painelInferior, BorderLayout.BEFORE_FIRST_LINE);
        painelInferior.add(new JLabel("Pesquisar:"));
        painelInferior.add(campoBusca);
        painelInferior.add(botaoBuscar);
        painelInferior.add(botaoExcluir);
        add(painelInferior, BorderLayout.SOUTH);

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

            modelo.setRowCount(0);
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

    private void buscarPacientes(String textoBusca) {
        try {
            String url = "jdbc:mysql://localhost:3306/aps";
            String usuario = "root";
            String senha = "APS-2025";

            Connection conn = DriverManager.getConnection(url, usuario, senha);

            String sql = "SELECT id_paciente, nome_paciente, cpf, nome_cidade, tipo_diabetes, estado_tratamento " +
                         "FROM pacientes WHERE nome_paciente LIKE ? OR cpf LIKE ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            String termo = "%" + textoBusca + "%";
            stmt.setString(1, termo);
            stmt.setString(2, termo);
            

            ResultSet rs = stmt.executeQuery();

            modelo.setRowCount(0);
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
            JOptionPane.showMessageDialog(this, "Erro na busca: " + e.getMessage());
        }
    }

    private void excluirPaciente() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um paciente para excluir.");
            return;
        }

        UIManager.put("OptionPane.background", new ColorUIResource(0xE1F5FE));
        UIManager.put("Panel.background", new ColorUIResource(0xE1F5FE));
        UIManager.put("OptionPane.messageForeground", new ColorUIResource(new Color(0, 102, 102)));

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
                modelo.setRowCount(0);
                carregarDados();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir paciente: " + ex.getMessage());
            }
        }

        // Reset cores do JOptionPane
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        UIManager.put("OptionPane.messageForeground", null);
    }
}
