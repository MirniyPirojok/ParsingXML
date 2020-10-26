package by.epam.learn.xml.parser;

import by.epam.learn.xml.entity.Student;
import by.epam.learn.xml.handler.StudentErrorHandler;
import by.epam.learn.xml.handler.StudentHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.Set;

public class StudentsSaxBuilder extends AbstractStudentsBuilder {
    static Logger logger = LogManager.getLogger();

    private final StudentHandler handler = new StudentHandler();
    private XMLReader reader;

    public StudentsSaxBuilder() {
        //reader configuration
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = factory.newSAXParser();
            reader = saxParser.getXMLReader();
        } catch (ParserConfigurationException | SAXException e) {
            logger.error("XML reader is not created.", e);
        }
        reader.setErrorHandler(new StudentErrorHandler());
        reader.setContentHandler(handler);
    }

    public StudentsSaxBuilder(Set<Student> students) {
        super(students);
    }

    @Override
    public void buildSetStudents(String filename) {
        try {
            reader.parse(filename);
        } catch (IOException | SAXException e) {
            logger.error("File is not parsed.", e);
        }
        students = handler.getStudents();
    }
}
