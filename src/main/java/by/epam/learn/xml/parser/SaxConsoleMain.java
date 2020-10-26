package by.epam.learn.xml.parser;

import by.epam.learn.xml.handler.ConsoleStudentHandler;
import by.epam.learn.xml.handler.StudentErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

import static by.epam.learn.xml.demo.ParsingDemo.STUDENTS_XML;

public class SaxConsoleMain {
    public static void main(String[] args) {
        try {
            //SAX parser creating and configuring
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            XMLReader reader = parser.getXMLReader();
            reader.setContentHandler(new ConsoleStudentHandler());
            reader.setErrorHandler(new StudentErrorHandler());
            reader.parse(STUDENTS_XML);
        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
}
