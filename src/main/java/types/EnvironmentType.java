package types;

public enum EnvironmentType {

    DEV("Development"),
    TEST("Testing"),
    SIT("System Integration Testing"),
    UAT("User Acceptance Testing"),
    PREPROD("Pre-production"),
    PROD("Production");

    private final String value;

    EnvironmentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
