package by.epam.learn.xml.handler;

public enum StudentXmlTag {
    STUDENTS("students"),
    LOGIN("login"),
    FACULTY("faculty"),
    STUDENT("student"),
    NAME("name"),
    TELEPHONE("telephone"),
    COUNTRY("country"),
    CITY("city"),
    STREET("street"),
    ADDRESS("address");

    private final String value;

    StudentXmlTag(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
