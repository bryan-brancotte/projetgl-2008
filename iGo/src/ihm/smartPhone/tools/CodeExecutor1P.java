package ihm.smartPhone.tools;

/**
 * Wrapper permettant de fournir un morceau de vode a executer ultérieurement avec un paramètre typé.
 * 
 * @author "iGo"
 * 
 */
public abstract class CodeExecutor1P<T> implements CodeExecutor {
	protected T origine;

	public CodeExecutor1P(T origine) {
		super();
		this.origine = origine;
	}

	@Override
	public abstract void execute();
}