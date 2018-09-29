package rpg.api.eventhandling;

import rpg.api.eventhandling.events.Event;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving events.
 * 
 * @see Event
 */
public interface EventListener {
	
	/**
	 * Gets the EventType this EventListener is supposed to receive.
	 *
	 * @return the EventType
	 */
	public default EventType getEventType() {
		return EventType.GENERAL_EVENT;
	}
	
	/**
	 * The method to execute on receiving an Event.
	 *
	 * @param event
	 *        the Event received
	 */
	public abstract void onEvent(final Event event);
}
