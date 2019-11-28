package odatesting.nashorn;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NashornRuleEngine {
	private static final String ENGINE_NAME = "nashorn";

	private NashornRuleEngine() {}


	@SuppressWarnings("all")
	public static Map<String, Object> engineThisEngineer(Map<String, Object> state, Object value) throws ScriptException, NoSuchMethodException, IOException {
		Stream<Path> path = Files.walk(Paths.get("nashorn/src/main/resources/rules"));
		List<String> files = path
				.map(Path::toString)
				.filter(file -> file.endsWith(".js"))
				.collect(Collectors.toList());

		for (String file : files) {
			state = rollTheRule(file, state, value);
		}

		return state;
	}

	private static Map<String, Object> rollTheRule(String file, Map<String, Object> state, Object value) throws ScriptException, NoSuchMethodException {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName(ENGINE_NAME);
		engine.eval(readFile(file));

		engine.put("value", value);
		engine.put("state", state);

		Invocable rule = (Invocable) engine;
		Object map = rule.invokeFunction("run");

		((Map) map).forEach((key, mapValue) -> state.put(key.toString(), mapValue));

		return state;
	}

	private static String readFile(String file) {
		try {
			Scanner script = new Scanner(new File(file));
			StringBuilder scriptContent = new StringBuilder();

			while(script.hasNext()) {
				scriptContent.append(script.nextLine());
			}

			script.close();

			return scriptContent.toString();
		} catch (FileNotFoundException ignored) {
		}
		return "";
	}
}
