package ihm.smartPhone.tools;

/**
 * Wrapper permettant de fournir un morceau de code a executer ultérieurement avec trois paramètres typés.
 * 
 * @author "iGo"
 * 
 */
public abstract class CodeExecutor3P<T, U,V> implements CodeExecutor {
	protected T origineA;
	protected U origineB;
	protected V origineC;

	public CodeExecutor3P(T origineA, U origineB,V origineC) {
		super();
		this.origineA = origineA;
		this.origineB = origineB;
		this.origineC = origineC;
	}

	@Override
	public abstract void execute();
}