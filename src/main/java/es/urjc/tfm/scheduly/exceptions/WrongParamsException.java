package es.urjc.tfm.scheduly.exceptions;

public class WrongParamsException extends Exception {
	public WrongParamsException() {
        super("One or more params are wrong. This is an example of request: "
				+ "{\r\n"
				+ "    \"messageBody\":\"Something new\",\r\n"
				+ "    \"year\": 2023,\r\n"
				+ "    \"month\": 9,\r\n"
				+ "    \"day\": 10,\r\n"
				+ "    \"hour\": 23,\r\n"
				+ "    \"minute\": 23 \r\n"
				+ "}");
    }
}
