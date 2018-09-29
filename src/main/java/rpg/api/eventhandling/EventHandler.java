package rpg.api.eventhandling;

import java.util.ArrayList;
import java.util.HashMap;

import rpg.api.eventhandling.events.Event;

/**
 * The Class EventHandler.
 */
public class EventHandler {
	private static HashMap<EventType, ArrayList<EventListener>>	listener			= new HashMap<>();
	private static HashMap<EventType, Boolean>					eventTypeRegistered	= initEventTypeRegistered();
	
	/**
	 * Inits the registered eventTypes as false.
	 *
	 * @return the HashMap created by this method.
	 */
	private static HashMap<EventType, Boolean> initEventTypeRegistered() {
		final HashMap<EventType, Boolean> eventTypeRegistered = new HashMap<>();
		
		for(final EventType et : EventType.values())
			eventTypeRegistered.put(et, false);
		
		return eventTypeRegistered;
	}
	
	/**
	 * Checks if the EventType is registered.
	 *
	 * @param eventType
	 *        the EventType to check
	 * @return true, if the EventType is registered.
	 */
	private boolean isEventTypeRegistered(final EventType eventType) {
		return eventTypeRegistered.get(eventType);
	}
	
	/**
	 * Gets the listeners for the EventType.
	 *
	 * @param eventType
	 *        the t
	 * @return the listeners for the EventType
	 */
	private ArrayList<EventListener> getListenersForEventType(final EventType eventType) {
		return listener.get(eventType);
	}
	
	/**
	 * Handles the Event.
	 *
	 * @param event
	 *        the Event to handle
	 */
	public void handle(final Event event) {
		final EventType t = event.getEventType();
		
		if(!isEventTypeRegistered(t)) return;
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				for(final EventListener l : getListenersForEventType(t))
					new Thread(new Runnable() {
						@Override
						public void run() {
							l.onEvent(event);
						}
					}, "Listener " + l + " handling " + t).start();
			}
		}, "Handling " + event).start();
	}
	
	/**
	 * Register the EventListener.
	 *
	 * @param eventListener
	 *        the Listener to register
	 */
	public void registerEventListener(final EventListener eventListener) {
		final EventType eT = eventListener.getEventType();
		
		if(!listener.containsKey(eT)) {
			listener.put(eT, new ArrayList<>());
			eventTypeRegistered.put(eT, true);
		}
		
		listener.get(eT).add(eventListener);
	}
}
