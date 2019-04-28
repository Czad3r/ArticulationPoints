import Domain.Graph;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;

public class GraphFrame extends JFrame {
    private int screenWidth;
    private int screenHeight;

    private Graph graph;

    private JPanel panel1;
    private JLabel labelWithImage;
    private JTextField graphSizeField;
    private JButton zainicjujGrafButton;
    private JTable verticesTable;
    private JButton szukajPunktówArtykulacjiButton;
    private JLabel apResult;

    public GraphFrame() {
        setScreenResolution();
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(screenWidth, screenHeight);
        setLocationByPlatform(true);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("Punkty artykulacji");
        zainicjujGrafButton.addActionListener(e -> {
            graph = new Graph(getGraphSize());
            initTable();
        });
        szukajPunktówArtykulacjiButton.addActionListener(e -> {
            findAP();
        });
    }

    public void setScreenResolution() {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        screenWidth = screenSize.width / 2;
        screenHeight = screenSize.height / 2;
    }

    private int getGraphSize() {
        int V = Integer.parseInt(graphSizeField.getText());
        return V;
    }

    private void initTable() {
        TableModel dataModel = new DefaultTableModel() {
            String[] columns = {"Numer wierzchołka", "Nazwa wierzchołka", "Sąsiedzi(numery po przecinkach)"};

            @Override
            public int getColumnCount() {
                return columns.length;
            }

            @Override
            public String getColumnName(int index) {
                return columns[index];
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return columnIndex == 1 || columnIndex == 2; //Or whatever column index you want to be editable
            }
        };
        for (int i = 0; i < getGraphSize(); i++)
            ((DefaultTableModel) dataModel).addRow(new Object[]{i, "", ""});

        verticesTable.setModel(dataModel);
    }

    private void findAP() {
        boolean flaga = true;
        if (verticesTable.getCellEditor() != null) {
            verticesTable.getCellEditor().stopCellEditing();
        }

        int V = getGraphSize();
        graph = new Graph(V);
        for (int i = 0; i < V; i++) {
            String[] adjacent = verticesTable.getModel().getValueAt(i, 2).toString().split("[\\s]?,");
            String name = verticesTable.getModel().getValueAt(i, 1).toString();

            for (String adj : adjacent) {
                int adjacentNode = -1;
                try {
                    if (!adj.equals(""))
                        adjacentNode = Integer.parseInt(adj);
                } catch (Exception e) {
                    flaga = false;
                    JOptionPane.showMessageDialog(null, "Błąd przy odczytywaniu sąsiadów wierzchołka: " + i + "(" + adj);
                }

                if (adjacentNode != -1) {
                    try {
                        graph.addEdge(i, adjacentNode);
                    } catch (Exception e) {
                        flaga = false;
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }
                }
            }

            try {
                graph.addNameForVertice(i, name);
            } catch (Exception e) {
                flaga = false;
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        if (flaga) {
            graph.findAP();
            drawGraph();
        }
    }

    private void drawGraph() {
        GraphAdapter graphAdapter = new GraphAdapter(graph);
        ImageIcon icon = new ImageIcon(GraphAdapter.getImage());
        labelWithImage.setIcon(icon);
        apResult.setText("Punkty artykulacji: " + graph.printAP());
    }


    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            new GraphFrame();
        });
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(20, 0, 0, 0), -1, -1));
        panel1.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        apResult = new JLabel();
        apResult.setText("");
        panel2.add(apResult, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel2.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        labelWithImage = new JLabel();
        labelWithImage.setText("");
        scrollPane1.setViewportView(labelWithImage);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 2, new Insets(20, 20, 20, 0), -1, -1));
        panel1.add(panel3, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Liczba wierzchołków:");
        panel3.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        graphSizeField = new JTextField();
        panel3.add(graphSizeField, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        zainicjujGrafButton = new JButton();
        zainicjujGrafButton.setText("Zainicjuj graf");
        panel3.add(zainicjujGrafButton, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane2 = new JScrollPane();
        panel3.add(scrollPane2, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        verticesTable = new JTable();
        scrollPane2.setViewportView(verticesTable);
        szukajPunktówArtykulacjiButton = new JButton();
        szukajPunktówArtykulacjiButton.setText("Szukaj punktów artykulacji");
        panel3.add(szukajPunktówArtykulacjiButton, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }
}