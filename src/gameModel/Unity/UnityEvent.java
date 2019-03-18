package gameModel.Unity;
public class UnityEvent {
	public static enum EventType {
		Move,Come,Start,PosA,PosB,Teleporter,AttackAttacher,AttackSentinel,SentinelState,AttackerState,
		NormalState,HarvestState,Add,Muddy
	}
	private int  pos;
	private EventType Type;

	public UnityEvent (int pos, EventType changeType) {
		this.pos = pos;
		this.Type = changeType;
	}

	public EventType getType() {
		return Type;
	}

	public int getpos() {
		return pos;
	}

}
