package exscript.core;

import cn.hutool.script.JavaScriptEngine;
import exscript.ExScript;

import javax.script.ScriptContext;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

public class ExEngine extends JavaScriptEngine {

	public ExEngine(ExScript es) {
		super();

		final SimpleBindings bindings = new SimpleBindings();
		bindings.put("es", es);
		bindings.put("ExScript", es);
		bindings.put("version", "v1.0.1");
		this.setBindings(bindings, ScriptContext.GLOBAL_SCOPE);
	}

	public Object invokeFunctionCatched(String function, Object...args) {
		try {
			return invokeFunction(function, args);
		} catch (ScriptException | NoSuchMethodException ex) {
			return ex;
		}
	}

}
