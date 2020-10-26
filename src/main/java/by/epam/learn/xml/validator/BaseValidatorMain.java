package by.epam.learn.xml.validator;

import by.epam.learn.xml.handler.StudentErrorHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class BaseValidatorMain {
    static Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
        String fileName = "data_xml/students.xml";
        String schemaName = "data_xml/students.xsd";
        SchemaFactory factory = SchemaFactory.newInstance(language);
        File schemaLocation = new File(schemaName);

        try {
            //schema creation
            Schema schema = factory.newSchema(schemaLocation);
            //creating a schema-based validator
            Validator validator = schema.newValidator();
            Source source = new StreamSource(fileName);
            //document check
            validator.setErrorHandler(new StudentErrorHandler());
            validator.validate(source);
            logger.info(String.format("%s is valid", fileName));
        } catch (SAXException | IOException e) {
            logger.error(String.format("%s is not correct or valid", fileName));
        }
    }
}
