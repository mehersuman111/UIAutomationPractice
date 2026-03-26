package types;

public enum Polarity {

    POSITIVE("Positive"),
    NEGATIVE("Negative");

    private final String value;

    Polarity(String value){
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
