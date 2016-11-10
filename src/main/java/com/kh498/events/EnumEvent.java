/*
 *  This file is part of Enumsk
 *  
 *  Copyright (C) 2016, kh498
 * 
 *  Enumsk is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Enumsk is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Enumsk.  If not, see <http://www.gnu.org/licenses/>.
 */

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