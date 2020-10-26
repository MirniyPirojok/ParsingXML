package by.epam.learn.xml.demo;

import by.epam.learn.xml.parser.AbstractStudentsBuilder;
import by.epam.learn.xml.parser.StudentBuilderFactory;

public class ParsingDemo {
    public static final String STUDENTS_XML = "data_xml/students.xml";
    public static final String SAX = "sax";
    public static final String DOM = "dom";
    public static final String STREAM_STAX = "stream_stax";
    public static final String EVENT_STAX = "event_stax";


    public static void main(String[] args) {
        AbstractStudentsBuilder saxBuilder = StudentBuilderFactory.createStudentBuilder(SAX);
        saxBuilder.buildSetStudents(STUDENTS_XML);
        System.out.println(saxBuilder.getStudents());

        AbstractStudentsBuilder domBuilder = StudentBuilderFactory.createStudentBuilder(DOM);
        domBuilder.buildSetStudents(STUDENTS_XML);
        System.out.println(domBuilder.getStudents());

        AbstractStudentsBuilder streamStaxBuilder = StudentBuilderFactory.createStudentBuilder(STREAM_STAX);
        streamStaxBuilder.buildSetStudents(STUDENTS_XML);
        System.out.println(streamStaxBuilder.getStudents());

        AbstractStudentsBuilder eventStaxBuilder = StudentBuilderFactory.createStudentBuilder(EVENT_STAX);
        eventStaxBuilder.buildSetStudents(STUDENTS_XML);
        System.out.println(eventStaxBuilder.getStudents());
    }
}
