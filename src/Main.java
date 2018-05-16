import ua.nure.dlubovskyi.Practice7.controller.DOMController;
import ua.nure.dlubovskyi.Practice7.controller.SAXController;
import ua.nure.dlubovskyi.Practice7.controller.StAXController;

public class Main {
	public static void main(String[] args) throws Exception {
		// DOM parser
		DOMController domController = new DOMController(args[0]);
		domController.parseXML(true);
		domController.saveToXML();

		// StAX parser
		StAXController stAXController = new StAXController(args[0]);
		stAXController.parse();
		stAXController.writeXML();

		// Sax parser
		SAXController saxController = new SAXController(args[0]);
		saxController.parse(true);
		saxController.writeXML();
	}
}
