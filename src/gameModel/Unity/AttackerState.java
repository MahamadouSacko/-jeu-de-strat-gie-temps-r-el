package gameModel.Unity;

import gameModel.Unity.UnityObservable.Direction;
import gameModel.Unity.UnityObservable.Order;

public class AttackerState extends UnityState{
	public AttackerState(UnityObservable u) {
		super(u);
	}
	@Override
	public void action() {
		if(unite.getPosition().equals(unite.getPositionGoToWent())) {
			if(unite.getDirection()!=Direction.Teleporteur)
			unite.setAction(Order.Find);
		}
		unite.notifyPlayerByAttacker();
	}

	@Override
	State getState() {
		return State.ATTACKER;
	}

	
}
