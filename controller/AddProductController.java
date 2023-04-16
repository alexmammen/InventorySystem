package controller;
//JavaFX

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddProductController implements Initializable {
    private ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();
    //TableViews
    @FXML private TableView<Part> associatedProdTable;
    @FXML private TableView<Part> addProdTable;
    //Product Table Columns
    @FXML private TableColumn<?, ?> addProdPartIDCol;
    @FXML private TableColumn<?, ?> addPartNameCol;
    @FXML private TableColumn<?, ?> addProdInventoryCol;
    @FXML private TableColumn<?, ?> addProdPriceCol;
    @FXML private TableColumn<?, ?> associatedProdIDCol;
    @FXML private TableColumn<?, ?> associatedPartNameCol;
    @FXML private TableColumn<?, ?> associatedInventoryCol;
    @FXML private TableColumn<?, ?> associatedPriceCol;
    //Buttons
    @FXML private Button addProdCancelButton;
    @FXML private Button addProdSaveButton;
    @FXML private Button removeAssociatedPartButton;
    @FXML private Button addProdAddButton;
    //TextFields
    @FXML private TextField addProdName;
    @FXML private TextField addProdInv;
    @FXML private TextField addProdPrice;
    @FXML private TextField addProdMax;
    @FXML private TextField addProdMin;
    @FXML private TextField addProdID;
    @FXML private TextField addProdSearchBox;
    /**
     Initializes the controller after its root element has been completely processed.
     @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
// Populate the add parts table with all available parts
        addProdTable.setItems(Inventory.getAllParts());
// Set up the columns to display part ID, part name, part inventory, and part price.
        addProdPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProdInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProdPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
// Populate the associated parts table with the associated parts list
        associatedProdTable.setItems(associatedPartsList);
// Set up the columns to display part ID, part name, part inventory, and part price.
        associatedProdIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    /**
     * Exits to homescreen
     * @param event the event that triggered this action
    * @throws IOException if there is an error loading the FXML file
    */
    @FXML
    public void addProdCancel(ActionEvent event) throws IOException {
        showScene("../views/MainScreen.fxml", event);
    }
    private void showScene(String fxmlPath, ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    /**
     This method is called when the user clicks the "Save" button in the "Add Product" view.
     RUNTIME ERROR:Initial Logical Error: Inventory Error - I was originally getting any input data to go through without warning. For this, I added Alerts for negative numbers and other numbers that did not match maximum and minimum criteria.
     @param event The ActionEvent triggered by the "Save" button being clicked.
     @throws IOException if an I/O error occurs.
     */
    @FXML
    void prodSavePushed(ActionEvent event) throws IOException {
        int uniqueID = (int) (Math.random() * 10000);
        String name = addProdName.getText();
        int stock = 0;
        double price = 0.0;
        int max = 0;
        int min = 0;
        // Validate input fields
        if (addProdInv.getText().matches("\\d+")) {
            stock = Integer.parseInt(addProdInv.getText());
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Invalid input: Stock must be a positive integer");
            alert.showAndWait();
            return;
        }
        if (addProdPrice.getText().matches("\\d+(\\.\\d+)?")) {
            price = Double.parseDouble(addProdPrice.getText());
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Invalid input: Price must be a positive number");
            alert.showAndWait();
            return;
        }
        if (addProdMax.getText().matches("\\d+")) {
            max = Integer.parseInt(addProdMax.getText());
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Invalid input: Maximum must be a positive integer and greater than minimum");
            alert.showAndWait();
            return;
        }
        if (addProdMin.getText().matches("\\d+")) {
            min = Integer.parseInt(addProdMin.getText());
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Invalid input: Minimum must be a positive integer");
            alert.showAndWait();
            return;
        }
        // Validate inventory requirements
        if (min > stock || stock > max) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Inventory requirements: Inventory must be within min and max.");
            alert.showAndWait();
            return;
        }
        if (min >= max) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Inventory requirements: maximum must be greater than minimum");
            alert.showAndWait();
            return;
        }
        // Create product and add it to inventory
        Product product = new Product(uniqueID, name, price, stock, min, max);
        for (Part part : associatedPartsList) {
            product.addAssociatedPart(part);
        }
        Inventory.addProduct(product);
        // Load the main screen
        Parent root = FXMLLoader.load(getClass().getResource("../views/MainScreen.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    /**
     Adds a selected part to the associated parts list of a product in the Add Product form.
     @param event the ActionEvent triggered by clicking the "Add" button
     */
    @FXML
    void addProductAddClick(ActionEvent event) {
        Part selectedPart = addProdTable.getSelectionModel().getSelectedItem();

        if (selectedPart != null && !associatedPartsList.contains(selectedPart)) {
            associatedPartsList.add(selectedPart);
            associatedProdTable.setItems(associatedPartsList);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Select available part");
            alert.showAndWait();
        }
    }
    /**
     * Removes the selected part from the associated parts list.
     *
     * @param event the action event
     */
    @FXML
    void removeAssociatedPart(ActionEvent event) {
        Part selectedPart = associatedProdTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null && associatedPartsList.remove(selectedPart)) {
            associatedProdTable.setItems(associatedPartsList);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setContentText("Select an available part");
            alert.showAndWait();
        }
    }
    /**
     Filters the add product table to display only the parts that match the search text.
     @param event The event that triggered the search.
     */
    @FXML
    void addProductAssociatedPartSearch(ActionEvent event) {
        String searchText = addProdSearchBox.getText();
        ObservableList<Part> results;
// Search for parts that match the search text
        if (searchText.matches("\\d+")) { // Check if search text is a number
            int partID = Integer.parseInt(searchText);
            Part part = Inventory.lookupPart(partID);
            results = part == null ? FXCollections.observableArrayList() : FXCollections.observableArrayList(part);
        } else {
            results = Inventory.lookupPart(searchText);
        }
// Display an error message if no matching parts are found
        if (results.isEmpty()) {
            Alert noParts = new Alert(Alert.AlertType.ERROR);
            noParts.setTitle("Error Message");
            noParts.setContentText("No parts found");
            noParts.showAndWait();
        }
// Display the matching parts in the add product table
        addProdTable.setItems(results);
    }
}