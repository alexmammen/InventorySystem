package model;
//JavaFX Collections

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.util.stream.Collectors;

/**
 * The inventory class stores the parts and products in observable lists.
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    /**
     * Adds a part to the inventory.
     *
     * @param part the part to add
     */
    public static void addPart(Part part) {
        allParts.add(part);
    }
    /**
     * Adds a product to the inventory.
     *
     * @param product the product to add
     */
    public static void addProduct(Product product) {
        allProducts.add(product);
    }
    /**
     * Looks up a part in the inventory by ID.
     *
     * @param partID the ID of the part to look up
     * @return the part with the given ID, or null if no such part is found
     */
    public static Part lookupPart(int partID) {
        for (Part part : allParts) {
            if (part.getId() == partID) {
                return part;
            }
        }
        Alert alert = new Alert(Alert.AlertType.ERROR, "No item using this ID found. Please try again");
        alert.show();
        return null;
    }
    /**
     * Searches for a product in the allProducts list by ID.
     * @param productID The ID of the product to look for.
     * @return The product if found, or null if no product is found.
     */
    public static Product lookupProduct(int productID) {
        for (Product product : allProducts) {
            if (product.getId() == productID) {
                return product;
            }
        }
        return null;
    }
    /**
     * Returns an observable list of parts that match the given name, by filtering the allParts list using a stream.
     *
     * @param partName The name to search for.
     * @return An observable list of parts that match the given name.
     */
    public static ObservableList<Part> lookupPart(String partName) {
        // Use a stream to filter the allParts list based on the provided partName,
        // and then collect the filtered parts into an observableArrayList.
        return allParts.stream()
                .filter(part -> part.getName().contains(partName))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }
    /**
     * Returns an observable list of products that match the given name, by filtering the allProducts list using a stream.
     *
     * @param productName The name to search for.
     * @return An observable list of products that match the given name.
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        // Use a stream to filter the allProducts list based on the provided productName,
        // and then collect the filtered products into an observableArrayList.
        return allProducts.stream()
                .filter(product -> product.getName().contains(productName))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }
    /**
     * Replaces the part at the specified index in the allParts list with the given part.
     *
     * @param index The index of the part to replace.
     * @param selectedPart The part to replace the existing part with.
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }
    /**
     * Replaces the product at the specified index in the allProducts list with the given product.
     *
     * @param index The index of the product to replace.
     * @param selectedProduct The product to replace the existing product with.
     */
    public static void updateProduct(int index, Product selectedProduct) {
        // Use the replace method of the ObservableList interface to replace the product at the specified index.
        allProducts.set(index, selectedProduct);
    }
    /**
     * Removes the specified part from the allParts list.
     *
     * @param selectedPart The part to remove.
     * @return True if the part was found and removed; false otherwise.
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.removeIf(part -> part.equals(selectedPart));
    }
    /**
     * Removes the specified product from the allProducts list.
     *
     * @param selectedProduct The product to remove.
     * @return True if the product was found and removed; false otherwise.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.removeIf(product -> product.equals(selectedProduct));
    }
    /**
     * Returns an unmodifiable view of the allParts list.
     *
     * @return An unmodifiable view of the allParts list.
     */
    public static ObservableList<Part> getAllParts() {
        return FXCollections.unmodifiableObservableList(allParts);
    }
    /**
     * Returns an unmodifiable view of the allProducts list.
     *
     * @return An unmodifiable view of the allProducts list.
     */
    public static ObservableList<Product> getAllProducts() {
        return FXCollections.unmodifiableObservableList(allProducts);
    }
}