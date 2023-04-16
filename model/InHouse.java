package model;
public class InHouse extends Part {
    private int machineID;

    /**
     * Constructor for InHouse parts.
     *
     * @param id        The ID of the part.
     * @param name      The name of the part.
     * @param price     The price of the part.
     * @param inStock   The current inventory level of the part.
     * @param min       The minimum inventory level of the part.
     * @param max       The maximum inventory level of the part.
     * @param machineID The ID of the machine used to manufacture the part.
     */
    public InHouse(int id, String name, double price, int inStock, int min, int max, int machineID) {
        super(id, name, price, inStock, min, max);
        this.machineID = machineID;
    }

    /**
     * Returns the ID of the machine used to manufacture the part.
     *
     * @return The ID of the machine.
     */
    public int getMachineID() {
        return machineID;
    }

    /**
     * Sets the ID of the machine used to manufacture the part.
     *
     * @param machineID The ID of the machine.
     */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
}