package APS;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Tela4 extends JFrame {

    JTable tabela;
    DefaultTableModel modelo;
    JButton botaoEditar;
    JTextField campoBusca;

    public Tela4() {
        setTitle("Alterar Pacientes");
        setSize(950, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(0xE1F5FE)); // fundo geral

        Font fonteTitulo = new Font("Times New Roman", Font.BOLD, 24);
        Font fontePadrao = new Font("Segoe UI", Font.PLAIN, 14);

        // Cabeçalho
        JLabel titulo = new JLabel("Central da Saúde - Alterar Pacientes", JLabel.CENTER);
        titulo.setFont(fonteTitulo);
        titulo.setForeground(new Color(0, 102, 102));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(titulo, BorderLayout.NORTH);

        // Tabela
        modelo = new DefaultTableModel(new String[]{
            "ID", "Nome", "CPF", "Cidade", "Tipo Diabetes", "Estado Tratamento"
        }, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
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

        // Painel inferior com campo de busca e botões
        JPanel painelInferior = new JPanel();
        painelInferior.setLayout(new FlowLayout(FlowLayout.CENTER));
        painelInferior.setBackground(new Color(0xE1F5FE));
        painelInferior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        campoBusca = new JTextField(20);
        campoBusca.setFont(fontePadrao);

        JButton botaoBuscar = new JButton("Buscar");
        botaoBuscar.setFont(fontePadrao);
        botaoBuscar.setBackground(new Color(173, 216, 230));
        botaoBuscar.setFocusPainted(false);
        botaoBuscar.setToolTipText("Buscar por nome, CPF ou cidade");

        botaoBuscar.addActionListener(e -> {
            String textoBusca = campoBusca.getText().trim();
            buscarPacientes(textoBusca);
        });

        campoBusca.addActionListener(e -> botaoBuscar.doClick());

        botaoEditar = new JButton("Editar Selecionado");
        botaoEditar.setFont(fontePadrao);
        botaoEditar.setFocusPainted(false);
        botaoEditar.setBackground(new Color(173, 216, 230));
        botaoEditar.setForeground(Color.BLACK);
        botaoEditar.setPreferredSize(new Dimension(200, 40));
        botaoEditar.addActionListener(e -> editarPaciente());

        painelInferior.add(new JLabel("Pesquisar:"));
        painelInferior.add(campoBusca);
        painelInferior.add(botaoBuscar);
        painelInferior.add(botaoEditar);

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
            JOptionPane.showMessageDialog(this, "Erro ao buscar: " + e.getMessage());
        }
    }

    private void editarPaciente() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um paciente para editar.");
            return;
        }

        UIManager.put("OptionPane.background", new ColorUIResource(0xE1F5FE));
        UIManager.put("Panel.background", new ColorUIResource(0xE1F5FE));
        UIManager.put("OptionPane.messageForeground", new ColorUIResource(new Color(0, 102, 102)));

        int id = (int) modelo.getValueAt(linha, 0);
        String nome = (String) modelo.getValueAt(linha, 1);
        String cpf = (String) modelo.getValueAt(linha, 2);
        String cidade = (String) modelo.getValueAt(linha, 3);
        String tipo = (String) modelo.getValueAt(linha, 4);
        String estado = (String) modelo.getValueAt(linha, 5);

        JTextField campoNome = new JTextField(nome);
        JTextField campoCpf = new JTextField(cpf);
        JComboBox<String> campoCidade = new JComboBox<>(new String[]{
            "São Paulo", "Guarulhos", "Campinas", "São Bernardo do Campo", "Santo André"
        });
        campoCidade.setSelectedItem(cidade);

        JComboBox<String> comboTipo = new JComboBox<>(new String[]{"Tipo 1", "Tipo 2"});
        comboTipo.setSelectedItem(tipo);

        JComboBox<String> comboEstado = new JComboBox<>(new String[]{
            "Sem tratamento", "Em tratamento", "Internado", "Óbito"
        });
        comboEstado.setSelectedItem(estado);

        Font fontePadrao = new Font("Segoe UI", Font.PLAIN, 14);
        campoNome.setFont(fontePadrao);
        campoCpf.setFont(fontePadrao);
        campoCidade.setFont(fontePadrao);
        comboTipo.setFont(fontePadrao);
        comboEstado.setFont(fontePadrao);

        JPanel painel = new JPanel(new GridLayout(0, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        painel.setBackground(new Color(0xE1F5FE));
        painel.add(new JLabel("Nome:")); painel.add(campoNome);
        painel.add(new JLabel("CPF:")); painel.add(campoCpf);
        painel.add(new JLabel("Cidade:")); painel.add(campoCidade);
        painel.add(new JLabel("Tipo de Diabetes:")); painel.add(comboTipo);
        painel.add(new JLabel("Estado do Tratamento:")); painel.add(comboEstado);

        Object[] opcoes = {"Salvar", "Cancelar"};
        int opcao = JOptionPane.showOptionDialog(
            this,
            painel,
            "Editar Paciente",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null,
            opcoes,
            opcoes[0]
        );

        if (opcao == JOptionPane.YES_OPTION) {
            try {
                String url = "jdbc:mysql://localhost:3306/aps";
                String usuario = "root";
                String senha = "APS-2025";

                Connection conn = DriverManager.getConnection(url, usuario, senha);
                PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE pacientes SET nome_paciente=?, cpf=?, nome_cidade=?, tipo_diabetes=?, estado_tratamento=? WHERE id_paciente=?"
                );

                stmt.setString(1, campoNome.getText());
                stmt.setString(2, campoCpf.getText());
                stmt.setString(3, campoCidade.getSelectedItem().toString());
                stmt.setString(4, comboTipo.getSelectedItem().toString());
                stmt.setString(5, comboEstado.getSelectedItem().toString());
                stmt.setInt(6, id);

                stmt.executeUpdate();
                conn.close();

                JOptionPane.showMessageDialog(this, "Paciente atualizado com sucesso.");
                carregarDados();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar: " + ex.getMessage());
            }
        }

        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        UIManager.put("OptionPane.messageForeground", null);
    }
}
