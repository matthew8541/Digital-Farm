package main.Games;

import main.Items.Item;
import main.Items.Seed;

import java.util.Arrays;

public class Inventory {
    private Item[] list;
    private int size;
    private int selectedItem;
    private int capacity;

    /**
     * Basic inventory constructor sets, max inventory slots.
     * @param capacity maximum unique items in inventory.
     */
    public Inventory(int capacity) {
        list = new Item[capacity];
        this.capacity = capacity;
        size = 0;
    }

    /**
     * Adds an item to the inventory
     * @param item item to be added to inventory
     * @return true, if item was added, false if there was not enough space
     */
    public boolean add(Item item) {
        boolean firstNullFound = false;
        int firstNullIdx = 0;

        for (int i = 0; i < list.length; i++) {
            if (list[i] == null) {
                if (!firstNullFound) {
                    firstNullFound = true;
                    firstNullIdx = i;
                }
            } else if (list[i].getName().equals(item.getName())) {
                if (list[i].getQuantity() + item.getQuantity() >= item.getMaxQuantity()) {
                    return false;
                }
                list[i].setQuantity(list[i].getQuantity() + item.getQuantity());
                return true;
                // Item of same type is already in inventory quantities were added together.
            }
        }
        if (firstNullFound) {
            list[firstNullIdx] = item.copy();
            size++;
            return true;
            // Item was not already in inventory, added in new slot.
        }
        return false;
        // Item was not already in inventory, added in new slot.
    }

    /**
     * Removes an amount of one item from inventory.
     * @param itemName Name of item that needs to be removed.
     * @param quantity Amount of the item that needs to be removed.
     * @return true if successfully removed, false if item wasn't found, or if quantity to remove
     *         was more than the player had.
     */
    public boolean remove(String itemName, int quantity) {
        Item target = get(itemName);
        if (target != null) {
            if (target.getQuantity() >= quantity) {
                target.setQuantity(target.getQuantity() - quantity);
                if (target.getQuantity() == 0) {
                    list[getPos(target.getName())] = null;
                    size--;
                    // Item quantity was set to 0 and then removed from inventory
                }
                return true; // Successfully removes the quantity of items from player.
            } else {
                return false; // You asked to remove more items than player has.
            }
        }
        return false; // Item could not be found in the inventory;
    }

    /**
     * Removes using the selected item.
     * @param quantity amount to be removed
     * @return true if amount was removed properly, false if selectedItem is null,
     *         and false if amount to remove was greater than amount of selectedItem
     */
    public boolean remove(int quantity) {
        if (list[selectedItem] == null) {
            return false;
            // There was no item selected
        }
        if (list[selectedItem].getQuantity() >= quantity) {
            list[selectedItem].setQuantity(list[selectedItem].getQuantity() - quantity);
            if (list[selectedItem].getQuantity() == 0) {
                list[selectedItem] = null;
                size--;
                // Quantity is zero, and slot is emptied
            }
            return true;
            // Item and amount was successfully removed
        } else {
            return false;
            // Item was not removed, because quantity was larger than it had
        }
    }

    /**
     * Finds the Item object from inventory with equivalent name to parameter.
     * @param itemName Name of item that wants to be retrieved
     * @return If item is in the inventory, that Item object is returned.
     * If not found, returns null.
     */
    public Item get(String itemName) {
        int result = getPos(itemName);
        if (result != -1) {
            return list[result];
        }
        return null;
    }

    public int getPos(String itemName) {
        for (int i = 0; i < capacity; i++) {
            if (list[i] != null && list[i].getName().equals(itemName)) {
                return i;
            }
        }
        return -1;
    }

    public boolean checkSeeds() {
        for (int i = 0; i < capacity; i++) {
            if (list[i] != null && list[i] instanceof Seed) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets Item object based on position in hotbar
     * @param position index from 0 to capacity - 1
     * @return the item in that position, or null if index out of bounds.
     */
    public Item get(int position) {

        if (position < capacity) {
            return list[position];
        }
        return null;
    }

    public Item getSelectedItem() {
        return list[selectedItem];
    }

    public Item[] getInventory() {
        return list;
    }

    public int getSelectedItemPos() {
        return selectedItem;
    }

    public void setSelectedItem(int position) {
        selectedItem = position;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return Arrays.toString(list) + "\nsize: \t" + size + "\nselectedItem: \t"
                + selectedItem;
    }
}
