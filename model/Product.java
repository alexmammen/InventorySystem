package model;

//JavaFX Collections
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {
    /**
     * The list of associated parts for the product.
     */
    public static ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Creates a new Product instance with the specified ID, name, price, stock, minimum quantity, and maximum quantity.
     *
     * @param id the ID of the product
     * @param name the name of the product
     * @param price the price of the product
     * @param stock the current stock quantity of the product
     * @param min the minimum stock quantity of the product
     * @param max the maximum stock quantity of the product
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }
    //Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public void setMin(int min) {
        this.min = min;
    }
    public void setMax(int max) {
        this.max = max;
    }
    //Getters
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public int getStock() {
        return stock;
    }
    public int getMin() {
        return min;
    }
    public int getMax() {
        return max;
    }

    /**

     Adds a given part to the list of associated parts for this product.
     @param part the part to be added
     @return true if the part was successfully added, false otherwise
     */
    public boolean addAssociatedPart(Part part) {
        return associatedParts.add(part);
    }
    /**

     Returns an unmodifiable view of the list of all associated parts for this product.
     @return an unmodifiable view of the list of all associated parts for this product
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return FXCollections.unmodifiableObservableList(associatedParts);
    }

    /**

     Removes the specified Part from the associated parts list of the Product.
     @param selectedAssociatedPart the Part to remove from the associated parts list.
     @return true if the specified Part was successfully removed from the associated parts list, false otherwise.
     */
    public static boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        return associatedParts.remove(selectedAssociatedPart);
    }

}