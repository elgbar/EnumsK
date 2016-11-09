/**
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

package com.kh498.conditions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.kh498.main.EnumManager;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

/**
 * @author kh498
 */
public class ConEnumValue extends Condition
{
	private Object expr0;
	private Object expr1;

	private Object value;
	private Object obj;

	@ Override
	public boolean init (Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, ParseResult parseResult)
	{
		expr0 = expr[0];
		expr1 = expr[1];

		return EnumManager.isValidEvent ("Enum values cannot be declared outside of Enums event.");
	}

	@ Override
	public String toString (@ Nullable Event e, boolean debug)
	{
		return "Enum value";
	}

	@ SuppressWarnings ("unchecked")
	@ Override
	public boolean check (Event e)
	{
		/* Get the parents expression (the key to the enum-map) */
		Object key = this.getParent ().toString ().replace ("Enum ", "");

		value = ((Expression<Object>) expr0).getSingle (e);
		obj = ((Expression<Object>) expr1).getSingle (e);

		return EnumManager.addValue (key, value, obj);
	}

}