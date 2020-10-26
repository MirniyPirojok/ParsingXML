package by.epam.learn.xml.handler;

import by.epam.learn.xml.entity.Student;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import static by.epam.learn.xml.handler.StudentXmlTag.FACULTY;
import static by.epam.learn.xml.handler.StudentXmlTag.LOGIN;
import static by.epam.learn.xml.handler.StudentXmlTag.STUDENT;

public class StudentHandler extends DefaultHandler {
    private final Set<Student> students;
    private Student current;
    private StudentXmlTag currentXmlTag;
    private final EnumSet<StudentXmlTag> withText;

    public StudentHandler() {
        students = new HashSet<>();
        withText = EnumSet.range(StudentXmlTag.NAME, StudentXmlTag.STREET);
    }

    public Set<Student> getStudents() {
        return students;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        if (STUDENT.getValue().equals(qName)) {
            current = new Student();

            for (int i = 0; i < attrs.getLength(); i++) {
                if (attrs.getQName(i).equals(LOGIN.getValue())) {
                    current.setLogin(attrs.getValue(i));
                } else if (attrs.getQName(i).equals(FACULTY.getValue())) {
                    current.setFaculty(attrs.getValue(i));
                }
            }

        } else {
            StudentXmlTag temp = StudentXmlTag.valueOf(qName.toUpperCase());
            if (withText.contains(temp)) {
                currentXmlTag = temp;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (STUDENT.getValue().equals(qName)) {
            students.add(current);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String data = new String(ch, start, length).strip();
        if (currentXmlTag != null) {
            switch (currentXmlTag) {
                case NAME -> current.setName(data);
                case TELEPHONE -> current.setTelephone(Integer.parseInt(data));
                case STREET -> current.getAddress().setStreet(data);
                case CITY -> current.getAddress().setCity(data);
                case COUNTRY -> current.getAddress().setCountry(data);
                default -> throw new EnumConstantNotPresentException(
                        currentXmlTag.getDeclaringClass(), currentXmlTag.name());
            }
        }
        currentXmlTag = null;
    }
}