import java.io.File;

public class Card {
    String value;
    String type;

    public Card(String value, String type) {
        this.value = value;
        this.type = type;
    }

    public String getImagePath() {
        String path = "./Cards/" + value.toLowerCase() + type.toLowerCase().charAt(0) + ".png";
        File file = new File(path);
        if (file.exists()) {
            return path;
        } else {
            return "./Cards/placeholder.png";  // Placeholder if card image is missing
        }
    }

    public int getValue() {
        return "a".equals(value) ? 11 : Integer.parseInt(value);
    }
}
