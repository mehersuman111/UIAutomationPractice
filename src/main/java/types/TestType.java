package types;

public enum TestType {

    SMOKE("Smoke"),
    SANITY("Sanity"),
    REGRESSION("Regression");

    private final String value;

    TestType(String value){
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
