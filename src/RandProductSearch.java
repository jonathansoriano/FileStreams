import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.List;

public class RandProductSearch extends JFrame{

    //JPanel
    private JPanel searchPanel;
    private JPanel resultsPanel;

    //JTextField
    private JTextField searchTF;

    //JTextArea
    private JTextArea resultsTA;

    public RandProductSearch(){
        JPanel mainPanel = new JPanel(new BorderLayout());

        //Create Search Panel
        createSearchPanel();
        mainPanel.add(searchPanel, BorderLayout.NORTH);
        //Create Result Panel
        createResultsPanel();
        mainPanel.add(resultsPanel, BorderLayout.CENTER);



        this.add(mainPanel);
        this.setSize(400, 300);
        this.setTitle("Random Product Search Application");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void createResultsPanel() {
        resultsPanel = new JPanel();

        resultsTA = new JTextArea(12, 34);
        JScrollPane scrollPane = new JScrollPane(resultsTA);

        resultsPanel.add(scrollPane);
    }

    private void createSearchPanel() {
        searchPanel = new JPanel();

        searchTF = new JTextField(15);

        JButton searchBtn = new JButton("Search");
        searchBtn.setFocusable(false);
        searchBtn.addActionListener((ActionEvent ae) -> {
            String searchTerm = searchTF.getText().trim().toLowerCase();//Use the text from the searchTF
            List<Product> products = readProductsFromFile();// Reads the binary file and returns them back to text and put data into a new Product Object
            List<Product> matchingProducts = products.stream()// use Java Stream to filter through the products List and return back a List
                                                            // of matching products that have the searchTerm.
                    .filter(p -> p.getName().toLowerCase().contains(searchTerm))//make everything lowercase to make it easier to compare searchTerm and
                                                                            //products in products List.
                    .collect(Collectors.toList());//This puts the filtered items to a list (goes to matchingProducts List line 64)

            displayResults(matchingProducts);
        });

        searchPanel.add(searchTF);
        searchPanel.add(searchBtn);

    }

    private List<Product> readProductsFromFile() {
        List<Product> products = new ArrayList<>();
        File workingDirectory = new File(System.getProperty("user.dir"));
        Path path = Paths.get(workingDirectory.getPath() + "\\src\\data.bin");

        try (RandomAccessFile raf = new RandomAccessFile(path.toFile(), "r")) {
            while (raf.getFilePointer() < raf.length()) {
                String id = readFixedLengthString(raf, 6).trim();
                String name = readFixedLengthString(raf, 35).trim();
                String description = readFixedLengthString(raf, 75).trim();
                double cost = raf.readDouble();
                products.add(new Product(id, name, description, cost));
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading from file: " + e.getMessage());
        }
        return products;
    }

    private String readFixedLengthString(RandomAccessFile raf, int length) throws IOException {
        byte[] bytes = new byte[length];
        raf.readFully(bytes);
        return new String(bytes);
    }
    private void displayResults(List<Product> products) {
        resultsTA.setText(""); // Clear previous results
        if (products.isEmpty()) {
            resultsTA.append("No matching products found.");
        } else {
            for (Product p : products) {
                resultsTA.append(String.format("ID: %s\nName: %s\nDescription: %s\nCost: $%.2f\n\n",
                        p.getID(), p.getName(), p.getDescription(), p.getCost()));
            }
        }
    }



}
