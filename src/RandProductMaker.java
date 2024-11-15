import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.nio.file.Paths;


public class RandProductMaker extends JFrame {
    //Panels
    JPanel itemCounterPanel;
    JPanel productFormPanel;
    JPanel buttonPanel;

    //JTextFields
    JTextField itemCounterTF;
    JTextField nameTF;
    JTextField descriptionTF;
    JTextField costTF;

    //Item Counter
    int itemCounter = 0;




    public RandProductMaker(){
        JPanel mainPanel = new JPanel(new BorderLayout());
        this.add(mainPanel);


        //ProductFormPanel (Center location)
        createProductFormPanel();
        mainPanel.add(productFormPanel,BorderLayout.NORTH);

        //ButtonPanel (South Location)
        createButtonPanel();
        mainPanel.add(buttonPanel,BorderLayout.SOUTH);



        this.setSize(600, 200);
        this.setTitle("Random Product Maker Application");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void createButtonPanel() {
        buttonPanel = new JPanel();

        JButton addBtn = new JButton("Add");
        addBtn.setFocusable(false);
        addBtn.addActionListener((ActionEvent ae) -> {
                    //Create an if statement that checks if the one of the required TF are empty,
                    //if they are display a JOptionPane dialog box saying they need fill in all the boxes.
                    //Check to make sure the data types are correct for the cost (Try catch block) for the TFs.

            try{
                //Check to see if any of the Textfields are empty, if they are tell the user to enter values for all fields
                if (nameTF.getText().isEmpty() || descriptionTF.getText().isEmpty() || costTF.getText().isEmpty()){
                    JOptionPane.showMessageDialog(this, "Please enter values for all the text fields");
                }else {
                    //If the user has filled in the boxes with the correct data type, then...
                    String name = nameTF.getText().trim();
                    String description = descriptionTF.getText().trim();
                    double cost = Double.parseDouble(costTF.getText());

                    createProduct(name, description, cost);
                }
            }catch (NumberFormatException e){
                //If they enter the wrong data values for the "Cost" then tell user to put in a valid value.
                JOptionPane.showMessageDialog(this, "Please enter valid text into the fields!");
            }catch (Exception e){
                JOptionPane.showMessageDialog(this, "Something went wrong...");
            }



                });// using TextField input (trim() first) then use that to create a Product Object

        JButton quitBtn = new JButton("Quit");
        quitBtn.setFocusable(false);
        quitBtn.addActionListener((ActionEvent ae) -> System.exit(0));

        buttonPanel.add(addBtn);
        buttonPanel.add(quitBtn);

    }

    private void createProductFormPanel() {
        productFormPanel = new JPanel(new GridLayout(4, 2));

        JLabel itemCounterLabel = new JLabel("Items entered");
        JLabel nameLabel = new JLabel("Please enter enter the name of your product:");
        JLabel descriptionLabel = new JLabel("Please enter a description of your product");
        JLabel costLabel = new JLabel("Please enter the cost of your product:");

        itemCounterTF = new JTextField(10);
        itemCounterTF.setEditable(false);
        itemCounterTF.setText("0");
        nameTF = new JTextField(15);
        descriptionTF = new JTextField(15);
        costTF = new JTextField(15);

        productFormPanel.add(itemCounterLabel);
        productFormPanel.add(itemCounterTF);
        productFormPanel.add(nameLabel);
        productFormPanel.add(nameTF);
        productFormPanel.add(descriptionLabel);
        productFormPanel.add(descriptionTF);
        productFormPanel.add(costLabel);
        productFormPanel.add(costTF);

    }
    private void createProduct(String name, String description, double cost) {
        Product product = new Product( name, description, cost);

        File workingDirectory = new File(System.getProperty("user.dir"));
        Path path = Paths.get(workingDirectory.getPath() + "\\src\\data.bin");

        try(RandomAccessFile raf = new RandomAccessFile(path.toFile(), "rw")){
            long fileLength = raf.length();
            raf.seek(fileLength); //Positioned at the end of the file so we can add to file.
            raf.writeBytes(product.getID());//Writes 6 bytes
            raf.writeBytes(product.getName());//Writes 35 bytes
            raf.writeBytes(product.getDescription());//Writes 75 bytes
            raf.writeDouble(product.getCost());//Writes 8 bytes
            //Reasons why we didn't use the writeUTF ():

            //writeUTF prepends a 2-byte length indicator before the actual string data.
            // This would disrupt our fixed-length record structure.
            clearTexfields();
            updateItemCounter();


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void updateItemCounter() {
        itemCounter++;
        itemCounterTF.setText(String.valueOf(itemCounter));
    }

    private void clearTexfields() {
        nameTF.setText("");
        descriptionTF.setText("");
        costTF.setText("");
    }


}
