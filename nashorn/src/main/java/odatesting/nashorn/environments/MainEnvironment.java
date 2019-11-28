package odatesting.nashorn.environments;

import odatesting.nashorn.NashornRuleEngine;

import javax.script.ScriptException;
import java.io.IOException;
import java.util.HashMap;

public class MainEnvironment {

	private static final String POWER_DATASTREAM_ID = "power";
	private static final String TEMPERATURE_DEVICE_DATASTREAM_ID = "tempDevice";
	private static final String STATE_OF_FIELDS = "fieldsState";

	public static void main(String[] args) throws ScriptException, NoSuchMethodException, IOException {
		Integer newValueTemp = (int) ((Math.random() * 50) - 5);
		String newValueState = newValueTemp > 10 ? "HARVESTING" : "RE";

		String key1 = POWER_DATASTREAM_ID;
		String key2 = TEMPERATURE_DEVICE_DATASTREAM_ID;
		String key3 = STATE_OF_FIELDS;
		boolean value1 = true;
		Integer value2 = 21;
		String value3 = "TO HARVEST";
		HashMap<String, Object> state = new HashMap<>();
		state.put(key1, value1);
		state.put(key2, value2);
		state.put(key3, value3);

		System.out.println("TO ASSIGN TEMP -> " + newValueTemp.toString());
		state = NashornRuleEngine.engineThisEngineer(state, newValueTemp);
		System.out.println("ASSIGNED TEMP -> " + state.get(key2).toString());

		System.out.println("TO ASSIGN STATE -> " + newValueState);
		state = NashornRuleEngine.engineThisEngineer(state, newValueState);
		System.out.println("ASSIGNED STATE -> " + state.get(key3).toString());

	}
}
