package odatesting.j2v8;

import com.eclipsesource.v8.V8;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class FilterTemperature {
	private static final String TEMPERATURE_DEVICE_DATASTREAM_ID = "tempDevice";

	public static void main(String[] args) throws FileNotFoundException {
		Integer newValue = (int) ((Math.random() * 35) - 5);

		String key1 = "power";
		String key2 = TEMPERATURE_DEVICE_DATASTREAM_ID;
		boolean value1 = true;
		Integer value2 = 21;
		HashMap<String, Object> state = new HashMap<>();
		state.put(key1, value1);
		state.put(key2, value2);

		System.out.println(newValue);

		state = filterTemperature(state, newValue);

		System.out.println(state.get(key2));
	}

	private static HashMap<String, Object> filterTemperature(HashMap<String, Object> state, Object value) throws FileNotFoundException {
		V8 runtime = V8.createV8Runtime();
		runtime.executeVoidScript(readFile("filterTemperature.js"));

		runtime.add("min", -5);
		runtime.add("max", 21);
		runtime.add("oldValue", (Integer) state.get(TEMPERATURE_DEVICE_DATASTREAM_ID));
		runtime.add("newValue", (Integer) value);

		state.put(TEMPERATURE_DEVICE_DATASTREAM_ID, runtime.executeIntegerFunction("run", null));

		return state;
	}

	private static String readFile(String fileName) throws FileNotFoundException {
		Scanner script = new Scanner(new File("oda-statemanager/test/src/main/resources/" + fileName));
		StringBuilder scriptContent = new StringBuilder();

		while(script.hasNext()) {
			scriptContent.append(script.nextLine());
		}

		script.close();

		return scriptContent.toString();
	}
}