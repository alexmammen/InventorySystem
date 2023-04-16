package controller;
//Java Core

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
//Model Imports
import model.*;

/**
 *  MainScreenController - provides logic for the main screen. The main UI and
 *  features to allow for interaction with the inventory.
 */

public class MainScreenController implements Initializable {

    // Buttons
    @FXML private Button addProdButton;
    @FXML private Button modifyProdButton;
    @FXML private Button deleteProdButton;
    @FXML private Button modifyPartButton;
    @FXML private Button addPartButton;
    @FXML private Button deletePartButton;
    @FXML private Button exitMain;

    // TextFields
    @FXML private TextField prodSearch;
    @FXML private TextField partSearch;

    // Product TableColumns
    @FXML private TableColumn<Product, Integer> prodIDCol;
    @FXML private TableColumn<Product, String> prodNameCol;
    @FXML private TableColumn<Product, Integer> prodInventoryCol;
    @FXML private TableColumn<Product, Double> prodPriceCol;

    // Part TableColumns
    @FXML private TableColumn<Part, Integer> partIDCol;
    @FXML private TableColumn<Part, String> partNameCol;
    @FXML private TableColumn<Part, Integer> partInventoryCol;
    @FXML private TableColumn<Part, Double> partPriceCol;

    // TableView for Products
    @FXML private TableView<Product> mainPageProductsTable;

    // TableView for Parts
    @FXML private TableView<Part> mainPagePartsTable;


    Stage stage;

    /**
     Initializes and populates the parts and products tables.
     @param url the URL of the FXML file
     @param resourceBundle the resource bundle used by this controller
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize and populate the parts table
        mainPagePartsTable.setItems(Inventory.getAllParts());
        partIDCol.setCellValueFactory(part -> new SimpleIntegerProperty(part.getValue().getId()).asObject());
        partNameCol.setCellValueFactory(part -> new SimpleStringProperty(part.getValue().getName()));
        partInventoryCol.setCellValueFactory(part -> new SimpleIntegerProperty(part.getValue().getStock()).asObject());
        partPriceCol.setCellValueFactory(part -> new SimpleDoubleProperty(part.getValue().getPrice()).asObject());

        // Initialize and populate the products table
        mainPageProductsTable.setItems(Inventory.getAllProducts());
        prodIDCol.setCellValueFactory(prod -> new SimpleIntegerProperty(prod.getValue().getId()).asObject());
        prodNameCol.setCellValueFactory(prod -> new SimpleStringProperty(prod.getValue().getName()));
        prodInventoryCol.setCellValueFactory(prod -> new SimpleIntegerProperty(prod.getValue().getStock()).asObject());
        prodPriceCol.setCellValueFactory(prod -> new SimpleDoubleProperty(prod.getValue().getPrice()).asObject());
    }
    /**
     This method handles the click event of the "Add Product" button on the main page by loading the AddProduct.fxml file and displaying it in a new window.
     @param event the event triggered by clicking the "Add Product" button on the main page
     @throws IOException if there is an error loading the AddProduct.fxml file
     */
    @FXML
    void MainPageAddProductsButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/AddProduct.fxml"));
        Parent root = loader.load();
        AddProductController controller = loader.getController();
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    /**
     Handles the event when the "Modify Product" button is clicked in the main page.
     @param event the event triggered by clicking the "Modify Product" button
     @throws IOException if an I/O error occurs while loading the ModifyProduct.fxml view
     */
    @FXML
    void MainPageModifyProductsButton(ActionEvent event) throws IOException {
        Product selectedProduct = mainPageProductsTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Select a product to modify");
            alert.show();
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/ModifyProduct.fxml"));
        Parent root = loader.load();
        ModifyProductController modifyProductControllerController = loader.getController();
        modifyProductControllerController.sendProductToMain(mainPageProductsTable.getSelectionModel().getSelectedIndex(), selectedProduct);
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    /**
     * Handles the event when the "Add Parts" button is clicked in the main page.
     * Loads the AddPart.fxml user interface for adding a new part to the inventory.
     * @param event The ActionEvent triggered by clicking the button.
     * @throws IOException if there is an error loading the AddPart.fxml file.
     */
    @FXML
    void MainPageAddPartsButton(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/AddPart.fxml"));
        Parent addPartParent = loader.load();
        Scene addPartScene = new Scene(addPartParent);

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(addPartScene);
        currentStage.show();
    }
    /**

     Loads the ModifyPart controller with the selected part from the mainPagePartsTable.

     @param event the action event

     @throws IOException if an I/O error occurs
     */
    @FXML
    void MainPageModifyPartsButton(ActionEvent event) throws IOException {
        Part selectedPart = mainPagePartsTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please Select a Part First");
            alert.show();
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/ModifyPart.fxml"));
        Parent root = loader.load();

        ModifyPartController MPController = loader.getController();
        MPController.sendPartToMain(mainPagePartsTable.getSelectionModel().getSelectedIndex(), selectedPart);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /**

     Closes the main page window.
     @param ExitButton The action event triggered by clicking the Exit button.
     */
    public void mainPageExitButton(ActionEvent ExitButton) {
        Stage stage = (Stage) ((Node) ExitButton.getSource()).getScene().getWindow();
        stage.close();
    }
    /**
     Deletes the selected part from the parts table.
     @param event the event triggered by clicking the delete button
     */
    @FXML
    void MainPageDeletePartButton(ActionEvent event) {
// Get the selected part from the parts table
        Part selectedPart = mainPagePartsTable.getSelectionModel().getSelectedItem();

// Prompt the user for confirmation before deleting the part
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText("Are you sure you want to delete?");
        alert.setContentText("Do you want to delete this part?");
        Optional<ButtonType> result = alert.showAndWait();

// Delete the part if the user confirms the action
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Inventory.deletePart(selectedPart);
        }
    }
    /**
     * Deletes the selected product while checking it for associated parts.
     *
     * @param event the event triggered by clicking the delete button
     */
    @FXML
    void mainScreenDeleteProductButton(ActionEvent event) {
        Product selectedProduct = mainPageProductsTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            return;
        }
        if (!selectedProduct.getAllAssociatedParts().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please remove associated parts before deleting the product.").showAndWait();
            return;
        }
        if (new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this product?", ButtonType.OK, ButtonType.CANCEL).showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }
        Inventory.deleteProduct(selectedProduct);
    }
    /**
     Updates the parts table with search results based on the search text in the parts search field.
     @param event the event triggered by pressing enter in the parts search field
     */
    @FXML
    void MainPagePartSearchField(ActionEvent event) {
        String searchText = partSearch.getText();
        ObservableList<Part> results;
        try {
            int partID = Integer.parseInt(searchText);
            Part part = Inventory.lookupPart(partID);
            if (part != null) {
                results = FXCollections.observableArrayList(part);
            } else {
                results = FXCollections.emptyObservableList();
            }
        } catch (NumberFormatException e) {
            results = Inventory.lookupPart(searchText);
        }
        mainPagePartsTable.setItems(results);
        if (results.isEmpty()) {
            Alert noParts = new Alert(Alert.AlertType.ERROR);
            noParts.setTitle("Error Message");
            noParts.setContentText("Not found");
            noParts.showAndWait();
        }
    }
    /**
     Handles the search event for the product search field in the main page.
     Searches for a product using either the product ID or product name as the search criteria.
     If a product is found, displays it in the products table.
     If no product is found, displays an error message.
     @param event the action event triggered by the search field
     */
    @FXML
    void MainPageProductSearchField(ActionEvent event) {
        String searchText = prodSearch.getText();
        ObservableList<Product> results;
        try {
            int productID = Integer.parseInt(searchText);
            Product product = Inventory.lookupProduct(productID);
            if (product != null) {
                results = FXCollections.observableArrayList(product);
            } else {
                results = FXCollections.emptyObservableList();
            }
        } catch (NumberFormatException e) {
            results = Inventory.lookupProduct(searchText);
        }
        mainPageProductsTable.setItems(results);
        if (results.isEmpty()) {
            Alert noProducts = new Alert(Alert.AlertType.ERROR);
            noProducts.setTitle("Error Message");
            noProducts.setContentText("Not found");
            noProducts.showAndWait();
        }
    }
}