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

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.kh498.main.EnumManager;

import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SelfRegisteringSkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.Trigger;

/**
 * @author kh498
 */
public class EvtEnum extends SelfRegisteringSkriptEvent
{
	@ Override
	public boolean init (final Literal<?>[] args, final int matchedPattern, final ParseResult parser)
	{
		return true;
	}

	@ Override
	public void register (final Trigger t)
	{
		t.execute (new EnumEvent ());
	}

	@ Override
	public void unregister (final Trigger t)
	{
	}

	@ Override
	public void unregisterAll ()
	{
		EnumManager.flushEnums ();
	}

	@ Override
	public String toString (final @ Nullable Event e, final boolean debug)
	{
		return "Enum event";
	}

}
