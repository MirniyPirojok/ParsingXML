package by.epam.learn.xml.parser;

public class StudentBuilderFactory {
    private enum TypeParser {
        SAX, STREAM_STAX, EVENT_STAX, DOM
    }

    private StudentBuilderFactory() {
    }

    public static AbstractStudentsBuilder createStudentBuilder(String typeParser) {
        TypeParser type = TypeParser.valueOf(typeParser.toUpperCase());
        switch (type) {
            case DOM -> {
                return new StudentsDomBuilder();
            }
            case STREAM_STAX -> {
                return new StudentsStreamStaxBuilder();
            }
            case EVENT_STAX -> {
                return new StudentsEventStaxBuilder();
            }
            case SAX -> {
                return new StudentsSaxBuilder();
            }
            default -> throw new EnumConstantNotPresentException(type.getDeclaringClass(), type.name());
        }
    }
}
