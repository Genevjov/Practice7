package ua.nure.dlubovskyi.Practice7.constants;

public class XMLTariffConstants {

	// tags
	public static final String TARIFFS = "tns:Tariffs";
	public static final String TARIFF = "tns:Tariff";
	public static final String OPERATOR_NAME = "tns:OperatorName";
	public static final String TARIFF_NAME = "tns:TariffName";
	public static final String PAYROLL = "tns:PayRoll";
	public static final String CALL_PRICE = "tns:CallPrice";
	public static final String WITHIN_NETWORK_CALL_PRICE = "tns:WithinNetworkCallPrice";
	public static final String OUT_OF_NETWORK_CALL = "tns:OutOfNetworkCallPrice";
	public static final String LAND_LINE_NUM_CALL_PRICE = "tns:LandLineNumCallPrice";
	public static final String SMS_PRICE = "tns:SmsPrice";
	public static final String PARAMETERS = "tns:Parameters";
	public static final String LOVELY_NUMBER_EXISTANCE = "tns:LovelyNumberRresence";
	public static final String TARIFFICATION = "tns:Tariffication";
	public static final String CONNECTION_PRICE = "tns:ConnectionPrice";

	// arrays for xml writers
	public static final String[] TARIFF_ARRAY = { OPERATOR_NAME, TARIFF_NAME, PAYROLL, SMS_PRICE, CALL_PRICE,
			PARAMETERS };
	public static final String[] PARAMETERS_ARRAY = { LOVELY_NUMBER_EXISTANCE, TARIFFICATION, CONNECTION_PRICE };
	public static final String[] CALL_PRICE_ARRAY = { WITHIN_NETWORK_CALL_PRICE, OUT_OF_NETWORK_CALL,
			LAND_LINE_NUM_CALL_PRICE };

}
