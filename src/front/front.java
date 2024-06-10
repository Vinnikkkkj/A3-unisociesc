package front;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class front extends JFrame {

    private JComboBox<String> sortingMethodComboBox;
    private JComboBox<String> searchMethodComboBox;
    private JProgressBar progressBar;
    private Timer timer;
    private int progressValue;

    public front() {
        setTitle("File Sorter");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel topPanel = new JPanel();
        JLabel fileLabel = new JLabel("File Path:");
        JTextField filePathTextField = new JTextField(20);
        JButton selectFileButton = new JButton("Select File");
        selectFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    String selectedFilePath = fileChooser.getSelectedFile().getAbsolutePath();
                    filePathTextField.setText(selectedFilePath);
                }
            }
        });

        topPanel.add(fileLabel);
        topPanel.add(filePathTextField);
        topPanel.add(selectFileButton);

        JPanel middlePanel = new JPanel();
        JLabel sortingMethodLabel = new JLabel("Sorting Method:");
        String[] sortingMethods = {"Insertion Sort", "Quick Sort", "Bubble Sort"};
        sortingMethodComboBox = new JComboBox<>(sortingMethods);
        middlePanel.add(sortingMethodLabel);
        middlePanel.add(sortingMethodComboBox);

        JPanel bottomPanel = new JPanel();
        JButton sortButton = new JButton("Sort");
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                progressBar.setValue(0); // Reset progress bar value
                progressValue = 0; // Reset progress value
                progressBar.setIndeterminate(false); // Disable indeterminate mode
                timer.start(); // Start the timer
                JOptionPane.showMessageDialog(null, "Sorting started. Please wait...");
            }
        });

        progressBar = new JProgressBar(0, 100); // Set the minimum and maximum values for progress bar
        progressBar.setStringPainted(true); // Show progress text
        bottomPanel.add(progressBar);
        bottomPanel.add(sortButton);

        timer = new Timer(100, new ActionListener() { // Timer to increment progress value
            @Override
            public void actionPerformed(ActionEvent e) {
                if (progressValue < 100) {
                    progressValue += 1;
                    progressBar.setValue(progressValue);
                } else {
                    timer.stop();
                    progressBar.setVisible(false); // Hide progress bar
                    showSearchMethodDropdown();
                    JOptionPane.showMessageDialog(null, "Sorting complete!");
                }
            }
        });

        setLayout(new GridLayout(3, 3));
        add(topPanel);
        add(middlePanel);
        add(bottomPanel);

        setVisible(true);
    }

    private void showSearchMethodDropdown() {
        JPanel bottomPanel = (JPanel) getContentPane().getComponent(2);
        JLabel searchMethodLabel = new JLabel("Search Method:");
        String[] searchMethods = {"Binary Search", "Linear Search"};
        searchMethodComboBox = new JComboBox<>(searchMethods);
        bottomPanel.add(searchMethodLabel);
        bottomPanel.add(searchMethodComboBox);
        bottomPanel.revalidate();
        bottomPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new front();
        });
    }
}
