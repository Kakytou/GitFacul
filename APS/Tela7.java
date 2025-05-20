package APS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.category.*;

public class Tela7 extends JFrame {

    private JComboBox<String> comboTipoDiabetes;
    private JComboBox<String> comboCidade1;
    private JComboBox<String> comboCidade2;
    private JComboBox<String> comboEstado;
    private JButton botaoGerarGrafico;
    private JPanel painelGrafico;

    private final String[] cidades = {"São Paulo", "Guarulhos", "Campinas", "São Bernardo do Campo", "Santo André"};

    public Tela7() {
        setTitle("Estatísticas - Central da Saúde");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        Color fundo = new Color(224, 255, 255);
        Font fonteTitulo = new Font("Times New Roman", Font.BOLD, 28);
        Font fontePadrao = new Font("Candara", Font.PLAIN, 16);

        
        JLabel titulo = new JLabel("Estatísticas - Comparação de Pacientes", SwingConstants.CENTER);
        titulo.setFont(fonteTitulo);
        titulo.setForeground(new Color(0, 102, 102));
        JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.setBackground(fundo);
        painelTopo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        painelTopo.add(titulo, BorderLayout.CENTER);
        add(painelTopo, BorderLayout.NORTH);

        
        JPanel painelFiltros = new JPanel();
        painelFiltros.setBackground(fundo);
        painelFiltros.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        
        gbc.gridx = 0; gbc.gridy = 0;
        painelFiltros.add(new JLabel("Tipo de Diabetes:"), gbc);
        comboTipoDiabetes = new JComboBox<>(new String[]{"Todos os Tipos", "Tipo 1", "Tipo 2"});
        comboTipoDiabetes.setFont(fontePadrao);
        gbc.gridx = 1;
        painelFiltros.add(comboTipoDiabetes, gbc);

        
        gbc.gridx = 0; gbc.gridy = 1;
        painelFiltros.add(new JLabel("Cidade 1:"), gbc);
        comboCidade1 = new JComboBox<>(cidades);
        comboCidade1.setFont(fontePadrao);
        gbc.gridx = 1;
        painelFiltros.add(comboCidade1, gbc);

        
        gbc.gridx = 0; gbc.gridy = 2;
        painelFiltros.add(new JLabel("Cidade 2 (comparar com):"), gbc);
        String[] cidadesComTodas = new String[cidades.length + 1];
        cidadesComTodas[0] = "Todas as Cidades";
        System.arraycopy(cidades, 0, cidadesComTodas, 1, cidades.length);
        comboCidade2 = new JComboBox<>(cidadesComTodas);
        comboCidade2.setFont(fontePadrao);
        gbc.gridx = 1;
        painelFiltros.add(comboCidade2, gbc);

        
        gbc.gridx = 0; gbc.gridy = 3;
        painelFiltros.add(new JLabel("Estado do Paciente:"), gbc);
        comboEstado = new JComboBox<>(new String[]{
            "Todos os Estados", "Sem tratamento", "Em tratamento", "Internado", "Óbito"
        });
        comboEstado.setFont(fontePadrao);
        gbc.gridx = 1;
        painelFiltros.add(comboEstado, gbc);

        
        botaoGerarGrafico = new JButton("Gerar Gráfico");
        botaoGerarGrafico.setFont(fontePadrao);
        botaoGerarGrafico.setBackground(new Color(173, 216, 230));
        botaoGerarGrafico.setFocusPainted(false);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        painelFiltros.add(botaoGerarGrafico, gbc);

        add(painelFiltros, BorderLayout.WEST);

        
        painelGrafico = new JPanel(new BorderLayout());
        painelGrafico.setBackground(Color.WHITE);
        painelGrafico.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(painelGrafico, BorderLayout.CENTER);

        
        botaoGerarGrafico.addActionListener(e -> gerarGrafico());

        setVisible(true);
    }

    private void gerarGrafico() {
        String tipoDiabetes = comboTipoDiabetes.getSelectedItem().toString();
        String cidade1 = comboCidade1.getSelectedItem().toString();
        String cidade2 = comboCidade2.getSelectedItem().toString();
        String estado = comboEstado.getSelectedItem().toString();

        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try {
            String url = "jdbc:mysql://localhost:3306/aps";
            String usuario = "root";
            String senha = "APS-2025";
            Connection conn = DriverManager.getConnection(url, usuario, senha);

            
            if (cidade2.equals("Todas as Cidades")) {
                
                String sql = "SELECT nome_cidade, COUNT(*) AS total FROM pacientes WHERE 1=1 ";
                if (!tipoDiabetes.equals("Todos os Tipos")) {
                    sql += "AND tipo_diabetes = ? ";
                }
                if (!estado.equals("Todos os Estados")) {
                    sql += "AND estado_tratamento = ? ";
                }
                sql += "GROUP BY nome_cidade";

                PreparedStatement ps = conn.prepareStatement(sql);

                int idx = 1;
                if (!tipoDiabetes.equals("Todos os Tipos")) {
                    ps.setString(idx++, tipoDiabetes);
                }
                if (!estado.equals("Todos os Estados")) {
                    ps.setString(idx++, estado);
                }

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String cidade = rs.getString("nome_cidade");
                    int total = rs.getInt("total");
                    dataset.addValue(total, "Pacientes", cidade);
                }
                rs.close();
                ps.close();

            } else {
                
                String sql = "SELECT nome_cidade, COUNT(*) AS total FROM pacientes WHERE nome_cidade IN (?, ?) ";
                if (!tipoDiabetes.equals("Todos os Tipos")) {
                    sql += "AND tipo_diabetes = ? ";
                }
                if (!estado.equals("Todos os Estados")) {
                    sql += "AND estado_tratamento = ? ";
                }
                sql += "GROUP BY nome_cidade";

                PreparedStatement ps = conn.prepareStatement(sql);

                int idx = 1;
                ps.setString(idx++, cidade1);
                ps.setString(idx++, cidade2);
                if (!tipoDiabetes.equals("Todos os Tipos")) {
                    ps.setString(idx++, tipoDiabetes);
                }
                if (!estado.equals("Todos os Estados")) {
                    ps.setString(idx++, estado);
                }

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String cidade = rs.getString("nome_cidade");
                    int total = rs.getInt("total");
                    dataset.addValue(total, "Pacientes", cidade);
                }
                rs.close();
                ps.close();
            }

            conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + e.getMessage());
            return;
        }

        
        JFreeChart grafico = ChartFactory.createBarChart(
                "Comparativo de Pacientes",
                "Cidade",
                "Quantidade",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );

        
        CategoryPlot plot = grafico.getCategoryPlot();
        plot.setBackgroundPaint(new Color(224, 255, 255));
        plot.setRangeGridlinePaint(Color.GRAY);

        
        painelGrafico.removeAll();
        ChartPanel chartPanel = new ChartPanel(grafico);
        painelGrafico.add(chartPanel, BorderLayout.CENTER);
        painelGrafico.validate();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Tela7());
    }
}
