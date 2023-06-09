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
//Model Import
import model.*;
//JavaCore
import java.io.IOException;

public class AddPartController {
    //Radio Buttons
    @FXML private RadioButton InHouseButton;
    @FXML private RadioButton OutsourcedButton;
    //Table Label
    @FXML private Label MachineIDorCompany;
    //TextField
    @FXML private TextField addPartName;
    @FXML private TextField addPartInv;
    @FXML private TextField addPartPrice;
    @FXML private TextField addPartMax;
    @FXML private TextField addPartMin;
    @FXML private TextField addPartMachID;

    /**
     Sets the label text to "Company Name" when the Outsourced button is pressed.
     @param event the ActionEvent object generated by the user action
     */
    @FXML
    void AddPartOutsourced(ActionEvent event) {
        MachineIDorCompany.setText("Company");
    }

    /**
     Sets the label text to "Machine ID" when the InHouse button is pressed.
     @param event the ActionEvent object generated by the user action
     */
    @FXML
    void AddPartInHouse(ActionEvent event) {
        MachineIDorCompany.setText("Machine ID");
    }

    /**
     Returns to the main screen when the cancel button is pressed.
     @param event the ActionEvent that triggered this method.
     @throws IOException if an error occurs while loading the MainScreen.fxml file.
     */
    @FXML
    public void addPartCancel(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../views/MainScreen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    /**
     * This method is called when the user clicks the "Save" button to add a new part to the inventory.
     * It retrieves the input values from the form, creates a new Part object, and adds it to the inventory.
     * If any input values are invalid, an error message is displayed to the user.
     * RUNTIME ERROR: ValidationException - Errors with entering maximum and minimum values before I added the createPartFromInput method that threw validationexceptions for those situations
     * @param event The event object representing the button click.
     */
    @FXML
    void addPartSave(ActionEvent event) throws IOException {
        try {
            Part part = createPartFromInput();

            Inventory.addPart(part);

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("../views/MainScreen.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        } catch (ValidationException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    private Part createPartFromInput() throws ValidationException {
        String name = addPartName.getText();
        int inStock = Integer.parseInt(addPartInv.getText());
        double price = Double.parseDouble(addPartPrice.getText());
        int min = Integer.parseInt(addPartMin.getText());
        int max = Integer.parseInt(addPartMax.getText());

        if (max < min) {
            throw new ValidationException("Maximum must be greater than minimum.");
        }

        if (inStock < min || max < inStock) {
            throw new ValidationException("Inventory must be within set minimum and maximum");
        }

        if (OutsourcedButton.isSelected()) {
            String companyName = addPartMachID.getText();
            return new Outsourced(generateUniqueID(), name, price, inStock, min, max, companyName);
        }

        if (InHouseButton.isSelected()) {
            int machineID = Integer.parseInt(addPartMachID.getText());
            return new InHouse(generateUniqueID(), name, price, inStock, min, max, machineID);
        }

        throw new ValidationException("Part type not selected.");
    }
    private int generateUniqueID() {
        return (int) (Math.random() * 100);
    }
    class ValidationException extends Exception {
        public ValidationException(String message) {
            super(message);
        }
    }

}