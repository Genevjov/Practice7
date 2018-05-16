package ua.nure.dlubovskyi.Practice7.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import ua.nure.dlubovskyi.Practice7.constants.XMLTariffConstants;
import ua.nure.dlubovskyi.Practice7.tariff.CallPrice;
import ua.nure.dlubovskyi.Practice7.tariff.Parameters;
import ua.nure.dlubovskyi.Practice7.tariff.Tariff;

public class Util {

	public static List<Object> tariffToList(Tariff tariff) {
		List<Object> resultList = new ArrayList<>();
		resultList.add(tariff.getOperatorName());
		resultList.add(tariff.getTariffName());
		resultList.add(tariff.getPayroll());
		resultList.add(tariff.getSmsPrice());
		CallPrice callPrice = tariff.getCallPrices();
		resultList.add(callPrice.getWithinNetworkCallPrice());
		resultList.add(callPrice.getOutOfNetworkCallPrice());
		resultList.add(callPrice.getLandLineNumCallPrice());
		Parameters parameters = tariff.getParameters();
		resultList.add(parameters.getLovelyNumberExitance());
		resultList.add(parameters.getTariffication());
		resultList.add(parameters.getConnectingPrice());
		return resultList;
	}

	public static void writeSaxStax(List<Tariff> tariffs, String path) throws IOException, XMLStreamException {

		// creating factory
		XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
		XMLStreamWriter xmlStreamWriter = null;
		// getting StreamWriter
		try (FileWriter fileWriter = new FileWriter(path)) {
			xmlStreamWriter = outputFactory.createXMLStreamWriter(fileWriter);
			xmlStreamWriter.writeStartDocument();
			// adding xml file root element
			xmlStreamWriter.writeStartElement(XMLTariffConstants.TARIFFS);
			xmlStreamWriter.writeAttribute("xmlns:tns", "http://www.example.org/output");
			xmlStreamWriter.writeAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			xmlStreamWriter.writeAttribute("xsi:schemaLocation", "http://www.example.org/input input.xsd");
			// filling xml object
			for (Tariff tariffObj : tariffs) {
				List<Object> listedTariff = Util.tariffToList(tariffObj);
				List<Object> listedCallPrice = listedTariff.subList(4, 7);
				List<Object> listedParameters = listedTariff.subList(7, listedTariff.size());
				xmlStreamWriter.writeStartElement(XMLTariffConstants.TARIFF);
				for (int i = 0; i < 4; i++) {
					xmlStreamWriter.writeStartElement(XMLTariffConstants.TARIFF_ARRAY[i]);
					xmlStreamWriter.writeCharacters(listedTariff.get(i).toString());
					xmlStreamWriter.writeEndElement();
				}
				writeCallPrice(xmlStreamWriter, listedCallPrice);
				writeParameters(xmlStreamWriter, listedParameters);

				xmlStreamWriter.writeEndElement();
			}
			xmlStreamWriter.writeEndElement();

		}
	}

	// Parameters xml writer
	private static void writeParameters(XMLStreamWriter writer, List<Object> listedParameters)
			throws XMLStreamException {
		writer.writeStartElement(XMLTariffConstants.PARAMETERS);
		for (int i = 0; i < listedParameters.size(); i++) {
			writer.writeStartElement(XMLTariffConstants.PARAMETERS_ARRAY[i]);
			writer.writeCharacters(listedParameters.get(i).toString());
			writer.writeEndElement();
		}
		writer.writeEndElement();
	}

	// CallPrice xml writer
	private static void writeCallPrice(XMLStreamWriter writer, List<Object> listedCallPrice) throws XMLStreamException {
		writer.writeStartElement(XMLTariffConstants.CALL_PRICE);
		for (int i = 0; i < listedCallPrice.size(); i++) {
			writer.writeStartElement(XMLTariffConstants.CALL_PRICE_ARRAY[i]);
			writer.writeCharacters(listedCallPrice.get(i).toString());
			writer.writeEndElement();
		}
		writer.writeEndElement();
	}
}
