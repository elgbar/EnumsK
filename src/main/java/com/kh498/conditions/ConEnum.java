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
public class ConEnum extends Condition
{
	private Object expr0;
	private String name = "";

	@ Override
	public boolean init (final Expression<?>[] expr, final int matchedPattern, final Kleenean isDelayed, final ParseResult parseResult)
	{
		expr0 = expr[0];

		EnumManager.setLastExpr (parseResult.expr);
		return EnumManager.isValidEvent ("A new enum cannot be declared outside of Enums event.");
	}

	@ Override
	public String toString (@ Nullable Event e, boolean debug)
	{
		return "Enum " + name;
	}

	@ Override
	public boolean check (Event e)
	{
		name = EnumManager.getProperEnumName (e, expr0);

		if (name == null)
			return false;

		if (!EnumManager.addEnum (name, null))
		{
			Skript.error ("Could not add the enum " + name);
			return false;
		}
		return true;
	}

}
