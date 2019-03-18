package gameModel.Unity;



public class NormalState extends UnityState{
	public NormalState(UnityObservable unite) {
		super(unite);
	}
	@Override
	public void action() {
		
	}
	@Override
	State getState() {
		return State.NORMAL;
	}
	

}
