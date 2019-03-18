package gameModel.Unity;

import gameModel.Unity.UnityObservable.Direction;

public class SentinelState extends UnityState{
	public SentinelState(UnityObservable u) {
		super(u);

	}

	@Override
	public void action() {
		unite.notifyPlayerBySentinelForAttack();
		if(unite.getDirection()==Direction.PosA) {
			if(unite.getPosition().equals(unite.getPositionA())) {
				unite.notifyPlayerBySentinel();
				unite.setDirection(Direction.PosB);

			}
		}else if(unite.getDirection()==Direction.PosB) {			
			if(unite.getPosition().equals(unite.getPositionB())) {
				unite.notifyPlayerBySentinel();
				unite.setDirection(Direction.PosA);
			}
		}
	}

	@Override
	State getState() {
		return State.SENTINEL;
	}
}
