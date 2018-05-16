package ua.nure.dlubovskyi.Practice7.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.stream.StreamSource;

import ua.nure.dlubovskyi.Practice7.constants.XMLConstants;
import ua.nure.dlubovskyi.Practice7.constants.XMLTariffConstants;
import ua.nure.dlubovskyi.Practice7.tariff.CallPrice;
import ua.nure.dlubovskyi.Practice7.tariff.Parameters;
import ua.nure.dlubovskyi.Practice7.tariff.Tariff;
import ua.nure.dlubovskyi.Practice7.utils.Util;

public class StAXController {
	// xml file path
	private String xmlFilePath;
	// generated tariff
	Tariff tariff;
	// generated CallPrice for tariff
	CallPrice callPrice;
	// generated Parameters for tariff
	Parameters parameters;
	List<Tariff> tariffs;

	/**
	 * Default constructor
	 * 
	 * @param xmlFilePath
	 */
	public StAXController(String xmlFilePath) {
		this.xmlFilePath = xmlFilePath;
		tariffs = new ArrayList<>();
	}

	/**
	 * Method for XML parsing using {@link XMLEventReader}
	 */
	public void parse() {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		try {
			// creating event reader
			XMLEventReader eventReader = factory.createXMLEventReader(new StreamSource(xmlFilePath));
			// checking value and filling Tariff object
			while (eventReader.hasNext()) {
				XMLEvent xmlEvent = eventReader.nextEvent();
				if (xmlEvent.isStartElement()) {
					StartElement startElement = xmlEvent.asStartElement();
					if (startElement.getName().getLocalPart().equals("Tariff")) {
						tariff = new Tariff();
						continue;
					} else if (startElement.getName().getLocalPart().equals("TariffName")) {
						xmlEvent = eventReader.nextEvent();
						tariff.setTariffName(xmlEvent.asCharacters().getData());
						continue;
					} else if (startElement.getName().getLocalPart().equals("OperatorName")) {
						xmlEvent = eventReader.nextEvent();
						tariff.setOperatorName(xmlEvent.asCharacters().getData());
						continue;
					} else if (startElement.getName().getLocalPart().equals("PayRoll")) {
						xmlEvent = eventReader.nextEvent();
						tariff.setPayroll(Double.parseDouble(xmlEvent.asCharacters().getData()));
						continue;
					} else if (startElement.getName().getLocalPart().equals("CallPrice")) {
						callPrice = new CallPrice();
						setCallPrice(eventReader);
						continue;

					} else if (startElement.getName().getLocalPart().equals("SmsPrice")) {
						xmlEvent = eventReader.nextEvent();
						tariff.setSmsPrice(Double.parseDouble(xmlEvent.asCharacters().getData()));

					} else if (startElement.getName().getLocalPart().equals("Parameters")) {
						parameters = new Parameters();
						setParameters(eventReader);
						continue;
					}
					// checking for XML object end
				} else if (xmlEvent.isEndElement()) {
					EndElement endElement = xmlEvent.asEndElement();
					if (endElement.getName().getLocalPart().equals("Tariff")) {
						tariffs.add(tariff);
					} else if (endElement.getName().getLocalPart().equals("CallPrice")) {
						tariff.setCallPrices(callPrice);
					} else if (endElement.getName().getLocalPart().equals("Parameters")) {
						tariff.setParameters(parameters);
					}
				}
			}
		} catch (XMLStreamException e) {
			System.err.println(e.getLocalizedMessage());
		}
	}

	// generator of CallPrice
	private void setCallPrice(XMLEventReader reader) {
		XMLEvent event;
		StartElement element;
		Characters character;
		for (int i = 0; i <= 7; i++) {
			try {
				event = reader.nextEvent();
				if (event.isStartElement()) {
					element = event.asStartElement();
					if (element.getName().getLocalPart().equals("WithinNetworkCallPrice")) {
						event = reader.nextEvent();
						character = event.asCharacters();
						callPrice.setWithinNetworkCallPrice(Double.parseDouble(character.getData()));
					} else if (element.getName().getLocalPart().equals("OutOfNetworkCallPrice")) {
						event = reader.nextEvent();
						character = event.asCharacters();
						callPrice.setOutOfNetworkCallPrice(Double.parseDouble(character.getData()));
					} else if (element.getName().getLocalPart().equals("LandLineNumCallPrice")) {
						event = reader.nextEvent();
						character = event.asCharacters();
						callPrice.setLandLineNumCallPrice(Double.parseDouble(character.getData()));
					}
				}
			} catch (XMLStreamException e) {
				System.err.println(e.getLocalizedMessage());
			}
		}
	}

	// generator of Parameters
	private void setParameters(XMLEventReader reader) {
		XMLEvent event;
		StartElement element;
		Characters character;
		for (int i = 0; i <= 7; i++) {
			try {
				event = reader.nextEvent();
				if (event.isStartElement()) {
					element = event.asStartElement();
					if (element.getName().getLocalPart().equals("LovelyNumberRresence")) {
						event = reader.nextEvent();
						character = event.asCharacters();
						parameters.setLovelyNumberExitance(Integer.parseInt(character.getData()));
					} else if (element.getName().getLocalPart().equals("Tariffication")) {
						event = reader.nextEvent();
						character = event.asCharacters();
						parameters.setTariffication(Integer.parseInt(character.getData()));
					} else if (element.getName().getLocalPart().equals("ConnectionPrice")) {
						event = reader.nextEvent();
						character = event.asCharacters();
						parameters.setConnectingPrice(Double.parseDouble(character.getData()));
					}
				}
			} catch (XMLStreamException e) {
				System.err.println(e.getLocalizedMessage());
			}
		}
	}

	/**
	 * XML file writer
	 * 
	 * @throws XMLStreamException
	 * @throws JAXBException
	 * @throws IOException
	 */
	public void writeXML() {
		tariffs.sort(new Comparator<Tariff>() {
			@Override
			public int compare(Tariff o1, Tariff o2) {
				return o2.compareTo(o1);
			}
		});
		try {
			Util.writeSaxStax(tariffs, XMLConstants.STAX_RESULT);
		} catch (IOException | XMLStreamException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws XMLStreamException, JAXBException, IOException {
		StAXController parser = new StAXController("src/input.xml");
		parser.parse();
		parser.writeXML();
	}

}
