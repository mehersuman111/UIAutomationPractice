package types;

public enum ElementType {
    TAB("tab"),
    MENU("menu"),
    ICON("icon"),
    LABEL("label"),
    TABLE("table"),
    ALERT("alert"),
    MODAL("modal"),
    BUTTON("button"),
    TOGGLE("toggle"),
    CHECKBOX("checkbox"),
    TEXT_LINK("text link"),
    SCROLLBAR("scrollbar"),
    TEXT_FIELD("text field"),
    IMAGE_LINK("image link"),
    PAGINATION("pagination"),
    BREADCRUMB("breadcrumb"),
    DATE_PICKER("date picker"),
    FILE_UPLOAD("file upload"),
    RADIO_BUTTON("radio button"),
    MULTI_SELECT_DROPDOWN("multi select dropdown"),
    SINGLE_SELECT_DROPDOWN("single select dropdown");

    private final String value;

    ElementType(String value){
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
