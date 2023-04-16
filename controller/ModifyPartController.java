package controller;

//JavaFX

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;
import java.io.IOException;

public class ModifyPartController  {
    //RadioButtons
    @FXML private RadioButton ModifyPartInHouse;
    @FXML private RadioButton ModifyPartOutsourced;
    //Labels
    @FXML private Label MachineIDorCompany;
    //TextFields
    @FXML private TextField modifyPartID;
    @FXML private TextField modifyPartName;
    @FXML private TextField modifyPartInv;
    @FXML private TextField modifyPartPrice;
    @FXML private TextField modifyPartMax;
    @FXML private TextField modifyPartMin;
    @FXML private TextField addPartMachID;
    //Buttons
    @FXML private Button modifyPartCancelButton;

    private int currentIndex = 0;

    /**
     Returns to the main screen when the cancel button is clicked.
     @param event The event that triggered the action.
     @throws IOException If an error occurs while loading the main screen.
     */
    @FXML
    public void ModifyPartCancel(ActionEvent event) throws IOException {
// Load the main screen
        Parent root = FXMLLoader.load(getClass().getResource("../views/MainScreen.fxml"));
        Scene scene = new Scene(root);
// Set the main screen as the active scene
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     Receives information about a part from the main screen and populates the modify part form with it.
     @param selectedIndex The index of the selected part.
     @param part The part to populate the form with.
     */
    public void sendPartToMain(int selectedIndex, Part part) {
        currentIndex = selectedIndex;
// Determine if the part is InHouse or Outsourced and set the appropriate fields
        addPartMachID.setText(part instanceof InHouse ? Integer.toString(((InHouse) part).getMachineID()) : ((Outsourced) part).getCompanyName());
        ModifyPartInHouse.setSelected(part instanceof InHouse);
        ModifyPartOutsourced.setSelected(part instanceof Outsourced);
// Populate the remaining form fields with the part information
        modifyPartID.setText(Integer.toString(part.getId()));
        modifyPartName.setText(part.getName());
        modifyPartInv.setText(Integer.toString(part.getStock()));
        modifyPartPrice.setText(Double.toString(part.getPrice()));
        modifyPartMax.setText(Integer.toString(part.getMax()));
        modifyPartMin.setText(Integer.toString(part.getMin()));
    }
    /**
     Change the label for the "Machine ID or Company Name" field to "Machine ID" to indicate that the part is InHouse.
     @param event The event triggered by selecting the InHouse radio button.
     */
    @FXML
    public void addPartInHouse(ActionEvent event) {
        MachineIDorCompany.setText("Machine ID");
    }
    /**
     Change the label for the "Machine ID or Company Name" field to "Company Name" to indicate that the part is Outsourced.
     @param event The event triggered by selecting the Outsourced radio button.
     */
    @FXML
    public void addPartOutsourced(ActionEvent event) {
        MachineIDorCompany.setText("Company Name");
    }
    /**
     Handles the save button action for modifying a part in the inventory system.
     Validates user input and updates the selected part in the inventory with the modified values.
     Throws NumberFormatException or IllegalArgumentException for invalid input.
     @param event The action event triggered by clicking the "Save" button.
     @throws IOException If the FXML file for the main screen cannot be found.
     */
    @FXML
    void modifyPartSave(ActionEvent event) throws IOException {
        try {
            int partID = Integer.parseInt(modifyPartID.getText());
            String name = modifyPartName.getText();
            int inStock = Integer.parseInt(modifyPartInv.getText());
            double price = Double.parseDouble(modifyPartPrice.getText());
            int min = Integer.parseInt(modifyPartMin.getText());
            int max = Integer.parseInt(modifyPartMax.getText());
            int machineID = 0;
            String companyName = "";
            if (max < min || inStock < min || inStock > max) {
                throw new IllegalArgumentException("Invalid input values.");
            }
            if (ModifyPartInHouse.isSelected()) {
                machineID = Integer.parseInt(addPartMachID.getText());
                Inventory.updatePart(currentIndex, new InHouse(partID, name, price, inStock, min, max, machineID));
            } else if (ModifyPartOutsourced.isSelected()) {
                companyName = addPartMachID.getText();
                Inventory.updatePart(currentIndex, new Outsourced(partID, name, price, inStock, min, max, companyName));
            }
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../views/MainScreen.fxml"))));
            stage.show();
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.WARNING, "Invalid input.").showAndWait();
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }

}