package gameModel.Unity;

public abstract class UnityState {
	public static enum State {NORMAL, ATTACKER,SENTINEL,HARVEST};
	public UnityObservable unite;
	public UnityState(UnityObservable u) {
		this.unite=u;
	}
	public abstract void action();
	abstract State getState();
}
