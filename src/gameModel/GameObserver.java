package gameModel;

import java.util.List;

import gameModel.Unity.UnityEvent;


public interface GameObserver {
	public void notify(List<UnityEvent> events);
}
