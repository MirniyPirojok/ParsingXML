package by.epam.learn.xml.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

public class StudentErrorHandler implements ErrorHandler {
    static Logger logger = LogManager.getLogger();

    @Override
    public void warning(SAXParseException exception) {
        logger.warn(String.format("%s-%s", getLineColumnNumber(exception), exception.getMessage()));
    }

    @Override
    public void error(SAXParseException exception) {
        logger.error(String.format("%s-%s", getLineColumnNumber(exception), exception.getMessage()));
    }

    @Override
    public void fatalError(SAXParseException exception) {
        logger.fatal(String.format("%s-%s", getLineColumnNumber(exception), exception.getMessage()));
    }

    private String getLineColumnNumber(SAXParseException exception) {
        //determine line and position of error
        return exception.getLineNumber() + ":" + exception.getColumnNumber();
    }
}
