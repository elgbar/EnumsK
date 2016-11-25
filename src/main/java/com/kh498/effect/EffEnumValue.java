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

package com.kh498.effect;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.kh498.main.EnumManager;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAPIException;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

/**
 * @author kh498
 */
public class EffEnumValue extends Effect
{
	private Object expr0;
	private Object expr1;

	private String parentEnum;
	private String currEnum;
	private String value;
	private Object obj;

	@ Override
	public boolean init (final Expression<?>[] expr, final int matchedPattern, final Kleenean isDelayed, final ParseResult parseResult)
	{
		expr0 = expr[0];
		expr1 = expr[1];
		if ('*' == (parseResult.expr.charAt (0)))
		{
			Skript.error ("The name of the enum cannot be '*'");
			return false;
		}

		return EnumManager.isValidEvent ("Enum values cannot be declared outside of Enums event.");
	}

	@ Override
	public String toString (@ Nullable Event e, boolean debug)
	{
		return "Enum object " + value + " from enum " + currEnum + ((parentEnum == null) ? "" : " and it's parent enum " + parentEnum);
	}

	@ SuppressWarnings ("unchecked")
	@ Override
	protected void execute (Event e)
	{
		String expr = EnumManager.getConKey (this.getParent ().toString ()).toString ();
		value = EnumManager.getProperEnumName (e, expr0);

		if (expr.contains ("parent"))
		{
			String[] value = expr.split (" from parent ");
			currEnum = value[0];
			parentEnum = value[1];
		} else
		{
			currEnum = this.getParent ().toString ().replaceFirst ("(?i)enum ", "");
		}
		try
		{
			obj = ((Expression<Object>) expr1).getSingle (e); //the object needs to be valid
		} catch (SkriptAPIException ex)
		{
			Skript.error ("The enum value " + expr1 + " is not a valid object and will NOT be loaded in!");
			return;
		}

		EnumManager.addValue (parentEnum, currEnum, value, obj);
	}

}