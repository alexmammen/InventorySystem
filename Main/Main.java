package Main;

/**
 * FUTURE ENHANCEMENT: One possible future enhancement for this project could be to add a feature for tracking inventory levels and generating alerts when stock
 * levels fall below a certain threshold. This would also include generating alerts for inventory nearing it's maximum and minimum thresholds. This could help users to stay
 * on top of their inventory and avoid stockouts, by giving them early warning when it's time to reorder
 * products. Another potential enhancement could be to add the ability to track sales and inventory data over time, which could help users to identify trends and make data-driven decisions
 * about how to optimize their inventory and pricing strategies. Additionally, it may be useful to add a feature for generating reports or analytics based on the inventory and
 * sales data, which could help users to gain deeper insights into their business performance and identify opportunities for improvement.
 */

// Java Core Libraries

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Product;

import java.io.IOException;

public class Main extends Application {

    /**
     * Initializes the JavaFX user interface by loading the fxml file "MainScreen.fxml" and creating a new scene with the loaded view.
     *
     * @param stage the primary stage of the application, which will be used to display the scene
     * @throws IOException if an I/O error occurs while loading the fxml file
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/MainScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 350);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * JAVADOC FOLDER IS LOCATED IN INVENTORY SYSTEM FOLDER. IT IS CALLED "Javadoc_Comments"
     * Main method uses test data to launch application
     * @param args which inclue sample data
     */
    public static void main(String[] args) {
        // Add parts to inventory
        Inventory.addPart(new InHouse(1, "Tire", 10.00, 20, 5, 50, 2));
        Inventory.addPart(new InHouse(2, "Brake Pad", 7.50, 50, 10, 100, 3));
        Inventory.addPart(new Outsourced(3, "Chain", 15.00, 15, 0, 75, "Bike Supplies Inc."));
        Inventory.addPart(new InHouse(4, "Pedal", 8.00, 30, 5, 40, 2));
        Inventory.addPart(new Outsourced(5, "Handlebar", 12.50, 25, 0, 100, "Bike Parts Co."));

        // Add products to inventory
        Product bike = new Product(1000, "Mountain Bike", 299.99, 5, 1, 50);
        Inventory.addProduct(bike);
        Product helmet = new Product(1001, "Bike Helmet", 49.99, 10, 5, 30);
        Inventory.addProduct(helmet);
        Product lock = new Product(1002, "Bike Lock", 19.99, 25, 10, 50);
        Inventory.addProduct(lock);
        Product bottle = new Product(1003, "Bike Water Bottle", 24.99, 100, 50, 150);
        Inventory.addProduct(bottle);

        // Launch application
        launch();
    }

}