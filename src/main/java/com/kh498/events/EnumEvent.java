package com.kh498.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author kh498
 */
public class EnumEvent extends Event
{
	public EnumEvent ()
	{
	}

	private static HandlerList handlers = new HandlerList ();

	@ Override
	public HandlerList getHandlers ()
	{
		return handlers;
	}

	public static HandlerList getHandlerList ()
	{
		return handlers;
	}
}