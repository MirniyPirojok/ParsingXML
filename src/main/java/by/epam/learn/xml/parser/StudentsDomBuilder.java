package by.epam.learn.xml.parser;

import by.epam.learn.xml.entity.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Set;

import static by.epam.learn.xml.handler.StudentXmlTag.ADDRESS;
import static by.epam.learn.xml.handler.StudentXmlTag.CITY;
import static by.epam.learn.xml.handler.StudentXmlTag.COUNTRY;
import static by.epam.learn.xml.handler.StudentXmlTag.FACULTY;
import static by.epam.learn.xml.handler.StudentXmlTag.LOGIN;
import static by.epam.learn.xml.handler.StudentXmlTag.NAME;
import static by.epam.learn.xml.handler.StudentXmlTag.STREET;
import static by.epam.learn.xml.handler.StudentXmlTag.STUDENT;
import static by.epam.learn.xml.handler.StudentXmlTag.TELEPHONE;

public class StudentsDomBuilder extends AbstractStudentsBuilder {
    static Logger logger = LogManager.getLogger();
    private DocumentBuilder docBuilder;

    public StudentsDomBuilder() {
        //configuration
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            logger.error("Doc builder is not created.");
        }
    }

    public StudentsDomBuilder(Set<Student> students) {
        super(students);
    }

    @Override
    public void buildSetStudents(String filename) {
        Document doc;
        try {
            doc = docBuilder.parse(filename);
            Element root = doc.getDocumentElement();
            //getting a list of <student> child elements
            NodeList studentsList = root.getElementsByTagName(STUDENT.getValue());
            for (int i = 0; i < studentsList.getLength(); i++) {
                Element studentElement = (Element) studentsList.item(i);
                Student student = buildStudent(studentElement);
                students.add(student);
            }
        } catch (IOException | SAXException e) {
            logger.error("Students set is not built.");
        }
    }

    private Student buildStudent(Element studentElement) {
        Student student = new Student();
        if (studentElement.hasAttribute(FACULTY.getValue())) {
            student.setFaculty(studentElement.getAttribute(FACULTY.getValue()));
        }

        student.setName(getElementTextContent(studentElement, NAME.getValue()));
        int tel = Integer.parseInt(getElementTextContent(studentElement, TELEPHONE.getValue()));
        student.setTelephone(tel);
        Student.Address address = student.getAddress();
        //init an address object
        Element addressElement = (Element) studentElement.getElementsByTagName(
                ADDRESS.getValue()).item(0);
        address.setCountry(getElementTextContent(addressElement, COUNTRY.getValue()));
        address.setCity(getElementTextContent(addressElement, CITY.getValue()));
        address.setStreet(getElementTextContent(addressElement, STREET.getValue()));
        student.setLogin(studentElement.getAttribute(LOGIN.getValue()));
        return student;
    }

    //get the text content of the tag
    private String getElementTextContent(Element element, String elementName) {
        NodeList nList = element.getElementsByTagName(elementName);
        Node node = nList.item(0);
        return node.getTextContent();
    }
}
