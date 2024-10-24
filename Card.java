import java.io.File;

public class Card {
    String value;
    String type;

    // Constructor to initialize a card with its value and type
    public Card(String value, String type) {
        this.value = value;
        this.type = type;
    }

    // Method to get the image path for the card's image file based on the "8s.png" naming convention
    public String getImagePath() {
        // Construct the file path using value and type without the hyphen
        String path = "./cards/" + value.toLowerCase() + type.toLowerCase() + ".png";
        
        // Debugging: Print the path to ensure it's correct
        System.out.println("Attempting to load image from: " + path);
        
        // Check if the file exists
        File file = new File(path);
        if (file.exists()) {
            return path;
        } else {
            System.out.println("Image file not found: " + path);
            return null;
        }
    }

    // Method to return the numerical value of the card in the context of Blackjack
    public int getValue() {
        if ("JQK".contains(value)) {
            return 10;
        } else if (value.equals("A")) {
            return 11;
        } else {
            return Integer.parseInt(value);
        }
    }
}
