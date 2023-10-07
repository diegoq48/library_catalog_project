package main;

import interfaces.List;

public class User {
    private int id;
    private String name;
    private List<Integer> checkedOutList;

    /**
     * Get the unique identifier (ID) of the user.
     *
     * @return The user's ID.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Constructor to initialize a User object.
     *
     * @param id             The unique identifier (ID) of the user.
     * @param name           The name of the user.
     * @param checkedOutList The list of book IDs that the user has checked out.
     */
    public User(int id, String name, List<Integer> checkedOutList) {
        this.setId(id);
        this.setName(name);
        this.setCheckedOutList(checkedOutList);
    }

    /**
     * Set the unique identifier (ID) of the user.
     *
     * @param id The user's ID.
     * @throws IllegalArgumentException if the ID is negative or too large for a 32-bit integer.
     */
    public void setId(int id) {
        // Check that the ID is a positive number.
        if (id < 0) {
            throw new IllegalArgumentException("Id must be a positive number");
        }
        // TODO: Check that the ID is not already set (uncomment if necessary).
        /*if (id > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Id is too large for a 32-bit integer");
        }*/

        this.id = id;
    }

    /**
     * Get the name of the user.
     *
     * @return The user's name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the name of the user.
     *
     * @param name The user's name.
     * @throws IllegalArgumentException if the name is null or empty.
     */
    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name.trim(); // Remove leading and trailing whitespace.
    }

    /**
     * Get the list of book IDs that the user has checked out.
     *
     * @return The list of checked-out book IDs.
     */
    public List<Integer> getCheckedOutList() {
        return this.checkedOutList;
    }

    /**
     * Set the list of book IDs that the user has checked out.
     *
     * @param checkedOutList The list of checked-out book IDs.
     */
    public void setCheckedOutList(List<Integer> checkedOutList) {
        this.checkedOutList = checkedOutList;
    }
}
