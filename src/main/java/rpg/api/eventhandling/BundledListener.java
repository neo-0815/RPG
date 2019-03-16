package rpg.api.eventhandling;

import java.util.function.Function;

import rpg.api.eventhandling.events.Event;
/**
 * A BundledListener is a bundle of listeners.
 * In order to have current results, it has to be reset.
 * @author Erik Diers, Jan Unterhuber, Alexander Schallenberg
 *
 */
public abstract class BundledListener {
	private QuestEventListener[] listener;
	
	public BundledListener(EventCondition... listener) {
		
		this.listener = new QuestEventListener[listener.length];
		int i = 0;
		for (EventCondition cond : listener) {
			this.listener[i] = new QuestEventListener(cond);
			rpg.api.eventhandling.EventHandler.registerEventListener(this.listener[i++]);
		}
		
	}
	
	/**
	 * Returns whether all listernes are triggered
	 * @return {@code true} if all Listeners are triggered
	 */
	public boolean isTriggered() {
		for (QuestEventListener listener : this.listener) 
			if (!listener.triggered)return false;
		
		return true;
	}
	
	/**
	 * Resets the listeners
	 */
	public void reset() {
		for (QuestEventListener eventListener : listener) 
			eventListener.reset();
	}
	
	private class QuestEventListener implements EventListener {
		private EventCondition condition;
		private boolean triggered;
		
		public QuestEventListener(EventCondition condition) {
			this.condition = condition;
		}
		
		@Override
		public void onEvent(Event event) {
			if (condition.apply(event))triggered = true;
		}
		
		public void reset() {
			triggered = false;
		}
		
	}
	
	/**
	 * Overwrite the method apply in order to set the method condition.
	 *
	 */
	public static interface EventCondition extends Function<Event, Boolean> {
	}
}