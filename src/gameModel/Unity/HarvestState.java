package gameModel.Unity;

import gameModel.Unity.UnityObservable.Direction;

public class HarvestState extends UnityState {
	public HarvestState(UnityObservable u) {
		super(u);
	}
	@Override
	public void action() {
		if(unite.getDirection()==Direction.Home) {
			if(unite.getPosition().equals(unite.getHomePos())) {
				unite.notifyPlayerByHarvest();
				unite.setDirection(Direction.Gold);

			}
		}else if(unite.getDirection()==Direction.Gold) {			
			if(unite.getPosition().equals(unite.getMinePosition())) {
				unite.notifyPlayerByHarvest();
			
			}
		}else if(unite.getDirection()==Direction.StayHome) {

		}
	}

	@Override
	State getState() {
		return State.HARVEST;
	}
}
