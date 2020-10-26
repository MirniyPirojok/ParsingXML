package by.epam.learn.xml.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class ConsoleStudentHandler extends DefaultHandler {
    static Logger logger = LogManager.getLogger();

    @Override
    public void startDocument() {
        logger.debug("Parsing started.");
        System.out.println("Parsing started.");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        StringBuilder tagData = new StringBuilder(qName + " ");
        for (int i = 0; i < attrs.getLength(); i++) {
            tagData.append(" ")
                    .append(attrs.getQName(i))
                    .append("=")
                    .append(attrs.getValue(i));
        }

        logger.debug(tagData.toString());
        System.out.print(tagData);
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        logger.debug(new String(ch, start, length));
        System.out.print(new String(ch, start, length));
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        System.out.print(" " + qName);
        logger.debug(String.format(" %s", qName));
    }

    @Override
    public void endDocument() {
        logger.debug("\nParsing ended.");
        System.out.println("\nParsing ended.");
    }
}
