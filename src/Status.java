public enum Status {

    TODO("todo"),
    IN_PROGRESS("in-progress"),
    DONE("done");

    private String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
