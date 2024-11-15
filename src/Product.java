import java.io.Serializable;
import java.util.Objects;

public class Product implements Serializable {
    private String ID = "";
    private String name = "";
    private String description = "";
    private double cost = 0.0;

    private static int IDSeed = 1;


    public Product (String ID, String name, String description, double cost){ //Updated Constructor.
        this.setID(ID);
        this.setName(name);
        this.setDescription(description);
        this.setCost(cost);
    }

    public Product (String name, String description, double cost){ //Updated Constructor.
        this.ID = String.format("%6s", generatorID()); // Won't let the user create an ID, rather it will be assigned. 6 bytes
        this.name = String.format("%35s", name);//35 bytes
        this.description = String.format("%75s",description);//75 bytes
        this.cost = cost; //8 bytes
        //124 bytes per record
    }

    //Getter and Setter Methods for "ID".

    /**
     * This method retrieves the ID of the Product object.
     * @return - Returns the ID of the Product object.
     */
    public String getID (){
        return ID;
    }

    /**
     * This method can change the ID value of the Product object.
     * @param ID - You need enter an ID type String to be able to change the ID.
     */
    public void setID(String ID){
        this.ID = ID;
    }


    /**
     * Computer generates a 6 char ID that increments after every use of this method.
     * @return - Returns a 6 char ID
     */
    private String generatorID(){
        String newID = "" + IDSeed; //Starts at 1.

        while (newID.length() < 6){
            newID = "0" + newID;
        }
        IDSeed++;

        return newID;
    }


    //Getter and Setter Methods for "name".

    /**
     * This method retrieves the name of the Product object.
     * @return - Returns a name of the product
     */
    public String getName(){
        return name;
    }

    /**
     * This method allows you to change the name of the Product object.
     * @param name - You need to enter a name type String to be able to change the name of the product.
     */
    public void setName(String name){
        this.name = name;
    }
    //Getter and Setter Methods for "description".

    /**
     * This method allows you to retrieve the Description of the Product object.
     * @return - returns the description of the product
     */
    public String getDescription(){
        return description;
    }

    /**
     * This method allows you to change the Description of the Product Object.
     * @param description - You need to enter a Description type String to change the description of the product
     */
    public void setDescription(String description){
        this.description = description;
    }
    //Getter and Setter Methods for "cost".

    /**
     * This method gets the cost of the Product object
     * @return - returns the cost of the product
     */
    public double getCost(){
        return cost;
    }

    /**
     * This method allows you to change the cost of the Product Object
     * @param cost - You need to enter a cost type double to be able to change the cost.
     */
    public void setCost(double cost){
        this.cost = cost;
    }
    //Additional Methods

    /**
     * This method prints out the name and description of the Product Object.
     * @return - Returns the name and description of the product.
     */
    public String fullDescription(){
        String info;
        info = getName() + " - " + getDescription();

        return info;
    }

    /**
     * This method converts the data fields of the Product object to CSV text file
     * @return - Returns all data fields of product, separated by commas.
     */
    public String toCSVDataRecord(){
        String productData = "";

        productData = getID() + "," + getName() + "," + getDescription() + "," + getCost();
        return productData;
    }

    /**
     * This method takes the data fields of the Product object and formats them in JSON format for a text file
     * @return - returns a JSON formatted text file of the Product's data fields.
     */
    public String toJSONRecord(){
        String productRecord = "";
        char DQ = '\u0022'; // This is the equivalent of double quotes but in character form.
        // Makes it easier to code and read the toJSONRecord method.

        productRecord = "{" + DQ + "IDNum" + DQ + ":" + DQ + getID() + DQ + "," + DQ + "Name" + DQ + ":" + DQ + getName() + DQ + ","
                + DQ + "Description" + DQ + ":" + DQ + getDescription() + DQ + "," + DQ + "Cost" + DQ + ":" + DQ + getCost() + DQ + "}";

        return productRecord;
    }

    /**
     * This method takes the data fields of the Product object and formats them in XML format for a text file
     * @return - returns an XML formatted text file of a Product's data fields.
     */
    public String toXMLRecord(){
        String productRecord = "";
        productRecord = "<Product>";
        productRecord += "<IDNum>" + getID() + "</IDNum>";
        productRecord += "<Name>" + getName() + "</Name>";
        productRecord += "<Description>" + getDescription() + "</Description>";
        productRecord += "<Cost>" + getCost() + "</Cost>";
        productRecord += "</Product>";

        return productRecord;
    }

    /**
     * This method allows us to print out our data fields of a Product object into an organized String format.
     * @return - print out our data fields of a Product object into an organized String format.
     */
    @Override
    public String toString() {
        return "Product{" +
                "ID='" + ID + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", cost=" + cost +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(cost, product.cost) == 0 && Objects.equals(ID, product.ID) && Objects.equals(name, product.name) && Objects.equals(description, product.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, name, description, cost);
    }
}
