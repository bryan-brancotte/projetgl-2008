package ihm.smartPhone.tools;

/**
 * Wrapper permettant de fournir un morceau de vode a executer ultérieurement avec deux paramètres typés.
 * 
 * @author "iGo"
 * 
 */
public abstract class CodeExecutor2P<T, U> implements CodeExecutor {
	protected T origineA;
	protected U origineB;

	public CodeExecutor2P(T origineA, U origineB) {
		super();
		this.origineA = origineA;
		this.origineB = origineB;
	}

	@Override
	public abstract void execute();
}