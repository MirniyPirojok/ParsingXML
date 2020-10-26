package by.epam.learn.xml.parser;

import by.epam.learn.xml.entity.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Set;

import static by.epam.learn.xml.handler.StudentXmlTag.CITY;
import static by.epam.learn.xml.handler.StudentXmlTag.COUNTRY;
import static by.epam.learn.xml.handler.StudentXmlTag.FACULTY;
import static by.epam.learn.xml.handler.StudentXmlTag.LOGIN;
import static by.epam.learn.xml.handler.StudentXmlTag.NAME;
import static by.epam.learn.xml.handler.StudentXmlTag.STREET;
import static by.epam.learn.xml.handler.StudentXmlTag.STUDENT;
import static by.epam.learn.xml.handler.StudentXmlTag.TELEPHONE;

public class StudentsEventStaxBuilder extends AbstractStudentsBuilder {
    static Logger logger = LogManager.getLogger();

    public StudentsEventStaxBuilder() {
    }

    public StudentsEventStaxBuilder(Set<Student> students) {
        super(students);
    }

    @Override
    public void buildSetStudents(String fileName) {
        Student student = null;
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        try {
            XMLEventReader reader = inputFactory.createXMLEventReader(new FileInputStream(fileName));
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    if (startElement.getName().getLocalPart().equals(STUDENT.getValue())) {
                        student = new Student();
                        Attribute login = startElement.getAttributeByName(new QName(LOGIN.getValue()));
                        student.setLogin(login.getValue());
                        Attribute faculty = startElement.getAttributeByName(new QName(FACULTY.getValue()));
                        if (faculty != null) {
                            student.setFaculty(faculty.getValue());
                        }
                    } else if (startElement.getName().getLocalPart().equals(TELEPHONE.getValue())) {
                        event = reader.nextEvent();
                        student.setTelephone(Integer.parseInt(event.asCharacters().getData()));
                    } else if (startElement.getName().getLocalPart().equals(NAME.getValue())) {
                        event = reader.nextEvent();
                        student.setName(event.asCharacters().getData());
                    } else if (event.isStartElement()) {
                        StartElement startElementAddr = event.asStartElement();
                        if (startElement.getName().getLocalPart().equals(COUNTRY.getValue())) {
                            event = reader.nextEvent();
                            student.getAddress().setCountry((event.asCharacters().getData()));
                        } else if (startElement.getName().getLocalPart().equals(CITY.getValue())) {
                            event = reader.nextEvent();
                            student.getAddress().setCity(event.asCharacters().getData());
                        } else if (startElement.getName().getLocalPart().equals(STREET.getValue())) {
                            event = reader.nextEvent();
                            student.getAddress().setStreet(event.asCharacters().getData());
                        }
                    }
                }
                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart().equals(STUDENT.getValue())) {
                        students.add(student);
                    }
                }
            }
        } catch (FileNotFoundException | XMLStreamException e) {
            logger.error("File wasn't read.", e);
        }
    }
}
