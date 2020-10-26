package by.epam.learn.xml.parser;

import by.epam.learn.xml.entity.Student;
import by.epam.learn.xml.handler.StudentXmlTag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Set;

public class StudentsStreamStaxBuilder extends AbstractStudentsBuilder {
    static Logger logger = LogManager.getLogger();
    private XMLInputFactory inputFactory;

    public StudentsStreamStaxBuilder() {
        inputFactory = XMLInputFactory.newInstance();
    }

    public StudentsStreamStaxBuilder(Set<Student> students) {
        super(students);
    }

    @Override
    public void buildSetStudents(String filename) {
        XMLStreamReader reader;
        String name;
        try (FileInputStream inputStream = new FileInputStream(new File(filename))) {
            reader = inputFactory.createXMLStreamReader(inputStream);
            //StAX parsing
            while (reader.hasNext()) {
                int type = reader.next();
                if (type == XMLStreamConstants.START_ELEMENT) {
                    name = reader.getLocalName();
                    if (name.equals(StudentXmlTag.STUDENT.getValue())) {
                        Student student = buildStudent(reader);
                        students.add(student);
                    }
                }
            }
        } catch (XMLStreamException | IOException e) {
            logger.error("File wasn't read.", e);
        }
    }

    private Student buildStudent(XMLStreamReader reader) throws XMLStreamException {
        Student student = new Student();
        student.setLogin(reader.getAttributeValue(null, StudentXmlTag.LOGIN.getValue()));
        String faculty = reader.getAttributeValue(null, StudentXmlTag.FACULTY.getValue());
        if (faculty != null) {
            student.setFaculty(faculty);
        }
        String name;
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT -> {
                    name = reader.getLocalName();
                    switch (StudentXmlTag.valueOf(name.toUpperCase())) {
                        case NAME -> student.setName(getXMLText(reader));
                        case TELEPHONE -> student.setTelephone(Integer.parseInt(getXMLText(reader)));
                        case ADDRESS -> student.setAddress(getXMLAddress(reader));
                    }
                }
                case XMLStreamConstants.END_ELEMENT -> {
                    name = reader.getLocalName();
                    if (StudentXmlTag.valueOf(name.toUpperCase()) == StudentXmlTag.STUDENT) {
                        return student;
                    }
                }
            }
        }
        throw new XMLStreamException("Unknown element in tag <student>");
    }

    private Student.Address getXMLAddress(XMLStreamReader reader) throws XMLStreamException {
        Student.Address address = new Student().new Address();
        int type;
        String name;
        while (reader.hasNext()) {
            type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT -> {
                    name = reader.getLocalName();
                    switch (StudentXmlTag.valueOf(name.toUpperCase())) {
                        case COUNTRY -> address.setCountry(getXMLText(reader));
                        case CITY -> address.setCity(getXMLText(reader));
                        case STREET -> address.setStreet(getXMLText(reader));
                    }
                }
                case XMLStreamConstants.END_ELEMENT -> {
                    name = reader.getLocalName();
                    if (StudentXmlTag.valueOf(name.toUpperCase()) == StudentXmlTag.ADDRESS) {
                        return address;
                    }
                }
            }
        }
        throw new XMLStreamException("Unknown element in tag <address>");
    }

    private String getXMLText(XMLStreamReader reader) throws XMLStreamException {
        String text = null;
        if (reader.hasNext()) {
            reader.next();
            text = reader.getText();
        }
        return text;
    }
}
