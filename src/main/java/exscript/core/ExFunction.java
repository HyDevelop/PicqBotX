package exscript.core;

public class ExFunction {

	protected final ExEngine engine;
	protected final String functionName;

	public ExFunction(ExEngine engine, String functionName) {
		this.engine = engine;
		this.functionName = functionName;
	}

	public Object execute(Object...args) {
		return engine.invokeFunctionCatched(functionName, args);
	}

}
