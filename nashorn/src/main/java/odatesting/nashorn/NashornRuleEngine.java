package odatesting.nashorn;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class NashornRuleEngine {

	private static final String TEMPERATURE_DEVICE_DATASTREAM_ID = "tempDevice";
	private static final String ENGINE_NAME = "nashorn";

	public static void main(String[] args) throws ScriptException, NoSuchMethodException {
		Integer newValue = (int) ((Math.random() * 50) - 5);

		String key1 = "power";
		String key2 = TEMPERATURE_DEVICE_DATASTREAM_ID;
		boolean value1 = true;
		Integer value2 = 21;
		HashMap<String, Object> state = new HashMap<>();
		state.put(key1, value1);
		state.put(key2, value2);

		System.out.println(newValue.toString());

		state = filterTemperature(state, newValue);

		System.out.println(state.get(key2).toString());

	}

	private static HashMap<String, Object> filterTemperature(HashMap<String, Object> state, Object value) throws ScriptException, NoSuchMethodException {
//		V8 runtime = V8.createV8Runtime();
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName(ENGINE_NAME);
//		runtime.executeVoidScript(readFile("filterTemperatureV4.js"));
		engine.eval(readFile("filterTemperatureV4.js"));

//		runtime.add("value", (Integer) value);
		engine.put("value", value);
//		V8Object map = V8ObjectUtils.toV8Object(runtime, state);
//		runtime.add("state", map);
		engine.put("state", state);
//		map.release();
//		map = runtime.executeObjectFunction("run", null);
		Invocable rule = (Invocable) engine;
		Object map = rule.invokeFunction("run", null);

//		Map m = V8ObjectUtils.toMap(map);
		((Map) map).forEach((key, mapValue) -> state.put(key.toString(), mapValue));

//		map.release();
//		runtime.release();

		return state;
	}

	private static String readFile(String fileName) {
		try {
			Scanner script = new Scanner(new File("nashorn/src/main/resources/" + fileName));
			StringBuilder scriptContent = new StringBuilder();

			while(script.hasNext()) {
				scriptContent.append(script.nextLine());
			}

			script.close();

			return scriptContent.toString();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		return "";
	}
}
