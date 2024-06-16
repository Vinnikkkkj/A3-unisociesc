package front;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JProgressBar;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.filechooser.FileNameExtensionFilter;

import ordenacoes.EscolhaMetodo;
import database.DatabaseManager;

public class front {

    private static JComboBox<String> searchDropdown; // Dropdown para pesquisa
    private static List<String> sortedDataList; // Lista de dados ordenados

    public static void main(String[] args) {
        JFrame frame = new JFrame("A3-unisociesc");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        JPanel filePanel = new JPanel();
        filePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel fileLabel = new JLabel("Selecione um arquivo:");
        filePanel.add(fileLabel);

        JTextField textField = new JTextField(20);
        textField.setEditable(false);
        filePanel.add(textField);

        JButton fileButton = new JButton("Selecionar Arquivo");
        filePanel.add(fileButton);

        JLabel fileSizeLabel = new JLabel("Tamanho do arquivo: ");
        filePanel.add(fileSizeLabel);

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Arquivos de Texto", "txt"));

        fileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    textField.setText(selectedFile.getAbsolutePath());
                    long fileSize = selectedFile.length();
                    fileSizeLabel.setText("Tamanho do arquivo: " + fileSize + " bytes");
                }
            }
        });

        JPanel sortPanel = new JPanel();
        sortPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel sortLabel = new JLabel("Selecione o tipo de ordenação:");
        sortPanel.add(sortLabel);

        String[] sortTypes = { "Escolha o método de ordenação", "Insertion Sort", "Quick Sort", "Bubble Sort" };
        JComboBox<String> sortComboBox = new JComboBox<>(sortTypes);
        sortPanel.add(sortComboBox);

        JButton sortButton = new JButton("Ordenar");
        sortButton.setVisible(false);
        sortPanel.add(sortButton);

        JLabel progressLabel = new JLabel("Linhas restantes: 0");
        progressLabel.setVisible(false);
        sortPanel.add(progressLabel);

        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setVisible(false);
        sortPanel.add(progressBar);

        JLabel timeLabel = new JLabel("Tempo total da ordenação: ");
        timeLabel.setVisible(false);
        sortPanel.add(timeLabel);
        
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        JLabel searchLabel = new JLabel("Selecione um número:");
        searchLabel.setVisible(false);
        searchPanel.add(searchLabel);

        searchDropdown = new JComboBox<>();
        searchDropdown.setVisible(false);
        searchPanel.add(searchDropdown);
        
        String[] searchTypes = { "Escolha o método de pesquisa", "Binaria", "Linear"};
        JComboBox<String> searchComboBox = new JComboBox<>(searchTypes);
        searchComboBox.setVisible(false);
        searchPanel.add(searchComboBox);
        
        JButton searchButton = new JButton("Pesquisar");
        searchButton.setVisible(false);
        searchPanel.add(searchButton);
        
        JLabel searchResultLabel = new JLabel("adasda");
        searchResultLabel.setVisible(false);
        searchPanel.add(searchResultLabel);

        JButton loadButton = new JButton("Carregar no Banco de Dados");
        loadButton.setVisible(false);
        sortPanel.add(loadButton);

        sortComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED && sortComboBox.getSelectedIndex() != 0) {
                    sortButton.setVisible(true);
                } else {
                    sortButton.setVisible(false);
                }
            }
        });
        
        searchComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED && searchComboBox.getSelectedIndex() != 0) {
                	searchButton.setVisible(true);
                } else {
                	searchButton.setVisible(false);
                }
            }
        });
        
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filePath = textField.getText();
                String sortType = (String) sortComboBox.getSelectedItem();
                if (!filePath.isEmpty()) {
                    try {
                        long startTime = System.currentTimeMillis();

                        List<String> data = readStringsFromFile(filePath);
                        sortedDataList = new ArrayList<>(data);
                        String[] array = sortedDataList.toArray(new String[0]);

                        progressBar.setMaximum(array.length);
                        progressBar.setValue(0);
                        progressBar.setVisible(true);
                        progressLabel.setText("Linhas restantes: " + array.length);
                        progressLabel.setVisible(true);

                        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
                            @Override
                            protected Void doInBackground() throws Exception {
                                EscolhaMetodo escolhaMetodo = new EscolhaMetodo();
                                escolhaMetodo.ordenar(array, sortType, new EscolhaMetodo.ProgressCallback() {
                                    @Override
                                    public void onProgressUpdate(int increment) {
                                        publish(increment);
                                    }
                                });
                                return null;
                            }

                            @Override
                            protected void process(List<Integer> chunks) {
                                for (int increment : chunks) {
                                    progressBar.setValue(increment);
                                    progressLabel.setText("Linhas restantes: " + (array.length - progressBar.getValue()));
                                }
                            }

                            @Override
                            protected void done() {
                                long endTime = System.currentTimeMillis();
                                long totalTime = endTime - startTime;
                                double seconds = totalTime / 1000.0;
                                timeLabel.setText("Tempo total: " + seconds + " segundos");
                                
                                progressBar.setVisible(false);
                                progressLabel.setVisible(false);
                                timeLabel.setVisible(true);
                                
                                searchDropdown.removeAllItems();
                                for (String value : sortedDataList) {
                                    searchDropdown.addItem(value);
                                }
                                searchDropdown.setVisible(true);
                                searchComboBox.setVisible(true);
                                searchLabel.setVisible(true);
                                loadButton.setVisible(true);

                                searchButton.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                    	String searchType = (String) searchComboBox.getSelectedItem();
                                    	String selectedValue = (String) searchDropdown.getSelectedItem();
                                    	try {
                                    		 EscolhaMetodo escolhaMetodo = new EscolhaMetodo();
                                    		 String indexNumberSearch = escolhaMetodo.pesquisar(array, selectedValue, searchType);
                                    		 
                                    		 if (selectedValue != null) {
                                                 String searchResult = indexNumberSearch;
                                                 searchResultLabel.setText(searchResult);
                                                 searchResultLabel.setVisible(true);
                                             }
                                    		 
                        				} catch (Exception err) {
                        					JOptionPane.showMessageDialog(frame, "Erro durante a pesquisa: " + err.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                        				}
                                    }    
                                });
                            }
                        };

                        worker.execute();

                    } catch (IOException err) {
                        JOptionPane.showMessageDialog(frame, "Falha ao ler o arquivo: " + err.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Nenhum arquivo selecionado.", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = "jdbc:mysql://localhost:3306/projeto-a3";
                String user = "root";
                String password = "root";
                DatabaseManager dbManager = new DatabaseManager(url, user, password);
                try {
                    dbManager.insertData(sortedDataList);
                    JOptionPane.showMessageDialog(frame, "Dados carregados com sucesso no banco de dados.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException err) {
                    JOptionPane.showMessageDialog(frame, "Erro ao carregar os dados no banco de dados: " + err.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.add(filePanel);
        frame.add(sortPanel);
        frame.add(searchPanel);
        frame.setSize(600, 600);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private static List<String> readStringsFromFile(String path) throws IOException {
        List<String> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    data.add(line.trim());
                }
            }
        }
        return data;
    }
}
