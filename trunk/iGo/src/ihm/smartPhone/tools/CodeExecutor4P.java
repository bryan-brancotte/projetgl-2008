package ihm.smartPhone.tools;

/**
 * Wrapper permettant de fournir un morceau de code a executer ultérieurement avec quatre paramètres typés.
 * 
 * @author "iGo"
 * 
 */
public abstract class CodeExecutor4P<T, U, V, W> implements CodeExecutor {
	protected T origineA;
	protected U origineB;
	protected V origineC;
	protected W origineD;

	public CodeExecutor4P(T origineA, U origineB, V origineC, W origineD) {
		super();
		this.origineA = origineA;
		this.origineB = origineB;
		this.origineC = origineC;
		this.origineD = origineD;
	}

	@Override
	public abstract void execute();
}