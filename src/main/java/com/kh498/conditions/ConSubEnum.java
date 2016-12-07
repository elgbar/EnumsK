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

package com.kh498.conditions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.kh498.main.EnumManager;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

/**
 * @author kh498
 */
public class ConSubEnum extends Condition
{
	private Object expr0;

	private String name;
	private String parent;

	@ Override
	public boolean init (final Expression<?>[] expr, final int matchedPattern, final Kleenean isDelayed, final ParseResult parseResult)
	{
		expr0 = expr[0];

		parent = EnumManager.getConKey (EnumManager.getLastExpr ()); //get the last "real" enum

		return EnumManager.isValidEvent ("A new enum cannot be declared outside of Enums event.");
	}

	@ Override
	public String toString (@ Nullable Event e, boolean debug)
	{
		return "Sub enum " + name + " from parent " + parent;
	}

	@ Override
	public boolean check (Event e)
	{
		name = EnumManager.getProperEnumName (e, expr0);

		if (name == null)
			return false;

		if (!EnumManager.addEnum (name, parent))
		{
			/*Bypass that we don't get the expression or line number from Skript*/
			Skript.error ("Could not add the sub enum " + name + " to the parent " + parent + " (expression: " + currFullExpr + ")");
			return false;
		}
		return true;
	}

}
