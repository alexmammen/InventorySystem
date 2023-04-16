package model;
public class Outsourced extends Part {
    private String company;

    /**
     * Creates a new Outsourced part with the specified properties.
     *
     * @param id The part ID.
     * @param name The part name.
     * @param price The price of the part.
     * @param stock The current inventory level of the part.
     * @param min The minimum inventory level of the part.
     * @param max The maximum inventory level of the part.
     * @param company The name of the company that supplies the part.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String company) {
        super(id, name, price, stock, min, max);
        this.company = company;
    }

    /**
     * Returns the name of the company that supplies the part.
     *
     * @return The name of the company.
     */
    public String getCompanyName() {
        return company;
    }

    /**
     * Sets the name of the company that supplies the part.
     *
     * @param company The name of the company.
     */
    public void setCompanyName(String company) {
        this.company = company;
    }
}