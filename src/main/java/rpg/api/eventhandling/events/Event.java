package rpg.api.eventhandling.events;

import rpg.api.eventhandling.EventType;

/**
 * The Interface Event.
 */
public interface Event {
	
	/**
	 * Gets the EventType of this Event.
	 *
	 * @return the EventType of this Event
	 */
	public default EventType getEventType() {
		return EventType.GENERAL_EVENT;
	}
}
