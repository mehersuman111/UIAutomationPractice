package types;

public enum Scope {
    API("API"),
    WEB("Web Application"),
    APK("Android Application"),
    IPA("IOS Application"),
    DB("Database");

    private final String value;

    Scope(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
