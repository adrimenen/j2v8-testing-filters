package odatesting.nashorn;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Object;
import com.eclipsesource.v8.utils.V8ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FilterTemperatureV4 {
	private static final Logger LOGGER = LoggerFactory.getLogger(FilterTemperatureV4.class);

	private static final String TEMPERATURE_DEVICE_DATASTREAM_ID = "tempDevice";

	public static void main(String[] args) {
		Integer newValue = (int) ((Math.random() * 50) - 5);

		String key1 = "power";
		String key2 = TEMPERATURE_DEVICE_DATASTREAM_ID;
		boolean value1 = true;
		Integer value2 = 21;
		HashMap<String, Object> state = new HashMap<>();
		state.put(key1, value1);
		state.put(key2, value2);

		LOGGER.info(newValue.toString());

		state = filterTemperature(state, newValue);

		LOGGER.info(state.get(key2).toString());

	}

	private static HashMap<String, Object> filterTemperature(HashMap<String, Object> state, Object value) {
		V8 runtime = V8.createV8Runtime();
		runtime.executeVoidScript(readFile("filterTemperatureV4.js"));

		runtime.add("value", (Integer) value);
		V8Object map = V8ObjectUtils.toV8Object(runtime, state);
		runtime.add("state", map);
		map.release();
		map = runtime.executeObjectFunction("run", null);

		Map m = V8ObjectUtils.toMap(map);
		m.forEach((key, mapValue) -> state.put(key.toString(), mapValue));

		map.release();
		runtime.release();

		return state;
	}

	private static String readFile(String fileName) {
		try {
			Scanner script = new Scanner(new File("oda-statemanager/test/src/main/resources/" + fileName));
			StringBuilder scriptContent = new StringBuilder();

			while(script.hasNext()) {
				scriptContent.append(script.nextLine());
			}

			script.close();

			return scriptContent.toString();
		} catch (FileNotFoundException e) {
			LOGGER.error(e.getMessage());
		}
		return "";
	}
}