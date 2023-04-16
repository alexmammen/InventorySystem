package controller;
//JavafX

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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
public class ModifyProductController implements Initializable {
    private ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();

    //Modify Product Table
    @FXML private TableView<Part> modifyProdTable;
    @FXML private TableColumn<?, ?> modifyProdPartIDCol;
    @FXML private TableColumn<?, ?> modifyPartNameCol;
    @FXML private TableColumn<?, ?> modifyProdInventoryCol;
    @FXML private TableColumn<?, ?> modifyProdPriceCol;
    //Associated List ProductTable
    @FXML private TableView<Part> assocProdTable;
    @FXML private TableColumn<?, ?> assocProdIDCol;
    @FXML private TableColumn<?, ?> assocPartNameCol;
    @FXML private TableColumn<?, ?> assocInventoryCol;
    @FXML private TableColumn<?, ?> assocPriceCol;
    //Buttons
    @FXML private Button modifyProdCancelButton;
    @FXML private Button modifyProdSaveButton;
    @FXML private Button removeAssocPartButton;
    @FXML private Button modifyProdmodifyButton;
    //TextFields
    @FXML private TextField modifyProdSearchBox;
    @FXML private TextField modifyProdID;
    @FXML private TextField modifyProdName;
    @FXML private TextField modifyProdInv;
    @FXML private TextField modifyProdPrice;
    @FXML private TextField modifyProdMax;
    @FXML private TextField modifyProdMin;
    private int currentIndex = 0;
    /**

     Initializes the ModifyProductController by setting up the tables for the parts and associated parts lists.

     @param url The location used to resolve relative paths for the root object, or null if the location is not known.

     @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
// Set up table for parts list
        modifyProdTable.setItems(Inventory.getAllParts());
        modifyProdPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProdInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProdPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
// Set up table for associated parts list
        assocProdTable.setItems(associatedPartsList);
        assocProdIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    /**
     The controller method for the 'Modify Product' cancel button. Returns to the main screen.
     @param event The action event triggered by the user.
     @throws IOException if there is an error loading the MainScreen.fxml file.
     */
    @FXML
    void modifyProdCancel(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/MainScreen.fxml"))));
        stage.show();
    }
    /**
     Receive information from the main screen and update the Modify Product form.
     @param selectedIndex the index of the selected product in the table
     @param product the product to display in the Modify Product form
     */
    @FXML
    public void sendProductToMain(int selectedIndex, Product product) {
        currentIndex = selectedIndex;
        modifyProdID.setText(Integer.toString(product.getId()));
        modifyProdName.setText(product.getName());
        modifyProdInv.setText(Integer.toString(product.getStock()));
        modifyProdPrice.setText(Double.toString(product.getPrice()));
        modifyProdMax.setText(Integer.toString(product.getMax()));
        modifyProdMin.setText(Integer.toString(product.getMin()));
        associatedPartsList.addAll(product.getAllAssociatedParts());
    }
    /**
     * Adds the selected part to the associated parts table.
     * If no part is selected in the modifyProdTable, displays a warning alert and returns without adding the part.
     * If the selected part is already in the associated parts list, does not add it again.
     * @param event the action event that triggered this method.
     */
    @FXML
    void addPartProdModify(ActionEvent event) {
        Part selectedPart = modifyProdTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            new Alert(AlertType.WARNING, "Select part").showAndWait();
            return;
        }
        if (!associatedPartsList.contains(selectedPart)) {
            associatedPartsList.add(selectedPart);
            assocProdTable.setItems(associatedPartsList);
        }
    }
    /**
     * Removes the selected part from the associated parts table.
     * If no part is selected in the assocProdTable, displays a warning alert and returns without removing any part.
     * If the selected part is in the associated parts list, removes it and updates the table.
     * @param event the action event that triggered this method.
     */
    @FXML
    void removeAssocPartButton(ActionEvent event) {
        Part selectedPart = assocProdTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            new Alert(AlertType.WARNING, "Select part from list").showAndWait();
            return;
        }

        if (associatedPartsList.remove(selectedPart)) {
            Product.deleteAssociatedPart(selectedPart);
            assocProdTable.setItems(associatedPartsList);
        }
    }

    /**
     * Saves the updated product with the modified fields and associated parts, and returns to the main screen.
     * If any of the input fields are not valid integers or doubles, displays a warning alert and returns without saving.
     * If the stock is not within the min and max limits, displays an error alert and returns without saving.
     * If the minimum is greater than or equal to the maximum, displays an error alert and returns without saving.
     * Otherwise, creates a new Product with the updated fields and associated parts, and updates the inventory.
     * Finally, returns to the main screen.
     * @param event the action event that triggered this method.
     * @throws IOException if the MainScreen.fxml file cannot be loaded.
     */
    @FXML
    void prodSavePushed(ActionEvent event) throws IOException {
        int id, stock, max, min;
        double price;
        if (!isInteger(modifyProdID.getText())) {
            new Alert(AlertType.WARNING, "Please enter a valid number for ID").showAndWait();
            return;
        }
        id = Integer.parseInt(modifyProdID.getText());
        String name = modifyProdName.getText();
        if (!isDouble(modifyProdPrice.getText())) {
            new Alert(AlertType.WARNING, "Please enter a valid number for Price").showAndWait();
            return;
        }
        price = Double.parseDouble(modifyProdPrice.getText());
        if (!isInteger(modifyProdInv.getText())) {
            new Alert(AlertType.WARNING, "Please enter a valid number for Inventory").showAndWait();
            return;
        }
        stock = Integer.parseInt(modifyProdInv.getText());
        if (!isInteger(modifyProdMax.getText())) {
            new Alert(AlertType.WARNING, "Please enter a valid number for Max").showAndWait();
            return;
        }
        max = Integer.parseInt(modifyProdMax.getText());
        if (!isInteger(modifyProdMin.getText())) {
            new Alert(AlertType.WARNING, "Please enter a valid number for Min").showAndWait();
            return;
        }
        min = Integer.parseInt(modifyProdMin.getText());
        if (stock > max || stock < min) {
            new Alert(AlertType.ERROR, "Inventory must be within min and max").showAndWait();
            return;
        }
        if (min >= max) {
            new Alert(AlertType.ERROR, "Maximum must be greater than minimum").showAndWait();
            return;
        }
        Product updatedProduct = new Product(id, name, price, stock, min, max);
        for (Part part : associatedPartsList) {
            updatedProduct.addAssociatedPart(part);
        }
        Inventory.updateProduct(currentIndex, updatedProduct);
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent mainScreen = FXMLLoader.load(getClass().getResource("../views/MainScreen.fxml"));
        stage.setScene(new Scene(mainScreen));
        stage.show();
    }
    private boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private boolean isDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    /**
     * Searches for a part by name or ID and displays the results in the modify product table.
     * If the search term is an integer and no parts are found by name, the method attempts
     * to find a part by ID.
     * @param event The event that triggered the search.
     */
    @FXML
    void modifyProdPartSearch(ActionEvent event) {
        String searchText = modifyProdSearchBox.getText();
        ObservableList<Part> results = Inventory.lookupPart(searchText);
        if (results.isEmpty() && searchText.matches("\\d+")) {
            int partID = Integer.parseInt(searchText);
            Part result = Inventory.lookupPart(partID);
            if (result != null) {
                results.add(result);
            }
        }
        if (results.isEmpty()) {
            new Alert(AlertType.ERROR, "No match")
                    .showAndWait();
        }
        modifyProdTable.setItems(results);
    }
}