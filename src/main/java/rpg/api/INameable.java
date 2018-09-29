package rpg.api;

import rpg.api.localization.StringLocalizer;

/**
 * The Interface INameable.
 *
 * @author Neo Hornberger
 */
public interface INameable {
	
	/**
	 * Gets the unlocalized name.
	 *
	 * @return the unlocalized name
	 */
	public String getUnlocalizedName();
	
	/**
	 * Sets the display name.
	 *
	 * @param displayName
	 *            the new display name
	 */
	public void setDisplayName(String displayName);
	
	/**
	 * Gets the localized display name.
	 *
	 * @return the localized display name
	 */
	public default String getDisplayName() {
		return StringLocalizer.localize(getUnlocalizedName());
	}
}
