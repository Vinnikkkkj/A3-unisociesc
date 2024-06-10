package front;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;

public class front {

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

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Arquivos de Texto", "txt"));

        fileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    textField.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        JPanel sortPanel = new JPanel();
        sortPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel sortLabel = new JLabel("Selecione o tipo de ordenação:");
        sortPanel.add(sortLabel);

        String[] sortTypes = { "Insertion Sort", "Quick Sort", "Bubble Sort" };
        JComboBox<String> sortComboBox = new JComboBox<>(sortTypes);
        sortPanel.add(sortComboBox);

        JButton sortButton = new JButton("Ordenar");
        sortButton.setVisible(false);
        sortPanel.add(sortButton);

        sortComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    sortButton.setVisible(true);
                }
            }
        });

        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filePath = textField.getText();
                String sortType = (String) sortComboBox.getSelectedItem();
                if (!filePath.isEmpty()) {
                    System.out.println("Ordenando o arquivo: " + filePath + " usando " + sortType);
                } else {
                    System.out.println("Nenhum arquivo selecionado.");
                }
            }
        });

        frame.add(filePanel);
        frame.add(sortPanel);
        frame.setSize(600, 600);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}