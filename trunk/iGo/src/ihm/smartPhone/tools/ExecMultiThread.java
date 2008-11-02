package ihm.smartPhone.tools;

public abstract class ExecMultiThread<E> extends Thread {

	protected E origine;

	public ExecMultiThread(E _origine) {
		origine = _origine;
	}

//	(new ExecMultiThread<EvoluFace>(null) {
//
//		@Override
//		public void run() {
//		}
//	}).start();
	
	public abstract void run();

}
