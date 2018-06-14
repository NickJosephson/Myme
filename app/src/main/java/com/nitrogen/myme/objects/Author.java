package com.nitrogen.myme.objects;

/* Author
 *
 * purpose: This class represents an author for use in describing who created a Meme.
 */
public class Author {
    private static final String DEFAULT_NAME = "";

    private String fullName;
    private String firstName;
    private String lastName;

    //**************************************************
    // Constructors
    //**************************************************

    public Author() {
        this.fullName = DEFAULT_NAME;
        this.firstName = null;
        this.lastName = null;
    }

    public Author(String fullName) {
        this.fullName = fullName;
        this.firstName = null;
        this.lastName = null;
    }

    public Author(String firstName, String lastName) {
        this.fullName = null;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    //**************************************************
    // Helper Methods
    //**************************************************

    @Override
    public String toString() {
        return "Author{" +
                "fullName='" + fullName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    //**************************************************
    // Accessor Methods
    //**************************************************

    public String getName() {
        if (fullName != null) {
            return fullName;
        } else if (firstName != null && lastName != null) {
            return firstName + " " + lastName;
        }

        return DEFAULT_NAME;
    }

}
