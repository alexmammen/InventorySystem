package model;

public abstract class Part {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Part constructor.
     * @param id The ID of the part.
     * @param name The name of the part.
     * @param price The price of the part.
     * @param stock The current inventory level of the part.
     * @param min The minimum inventory level of the part.
     * @param max The maximum inventory level of the part.
     */
    public Part(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**

     Gets the ID of the part.
     @return The ID of the part.
     */
    public int getId() {
        return id;
    }
    /**

     Sets the ID of the part.
     @param id The ID of the part.
     */
    public void setId(int id) {
        this.id = id;
    }
    /**

     Gets the name of the part.
     @return The name of the part.
     */
    public String getName() {
        return name;
    }
    /**

     Sets the name of the part.
     @param name The name of the part.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**

     Gets the price of the part.
     @return The price of the part.
     */
    public double getPrice() {
        return price;
    }
    /**

     Sets the price of the part.
     @param price The price of the part.
     */
    public void setPrice(double price) {
        this.price = price;
    }
    /**

     Gets the current inventory level of the part.
     @return The current inventory level of the part.
     */
    public int getStock() {
        return stock;
    }
    /**

     Sets the current inventory level of the part.
     @param stock The current inventory level of the part.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }
    /**

     Gets the minimum inventory level of the part.
     @return The minimum inventory level of the part.
     */
    public int getMin() {
        return min;
    }
    /**

     Sets the minimum inventory level of the part.
     @param min The minimum inventory level of the part.
     */
    public void setMin(int min) {
        this.min = min;
    }
    /**

     Gets the maximum inventory level of the part.
     @return The maximum inventory level of the part.
     */
    public int getMax() {
        return max;
    }
    /**

     Sets the maximum inventory level of the part.
     @param max The maximum inventory level of the part.
     */
    public void setMax(int max) {
        this.max = max;
    }

}