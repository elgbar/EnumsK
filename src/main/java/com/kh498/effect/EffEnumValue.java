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
	private Object expr1;

	private String currFullExpr;

	private String parentEnum;
	private String currEnum;
	private String enumNode;
	private Object value;

	@ Override
	public boolean init (final Expression<?>[] expr, final int matchedPattern, final Kleenean isDelayed, final ParseResult parseResult)
	{
		currFullExpr = parseResult.expr;
		if ('*' == (currFullExpr.charAt (0)))
		{
			Skript.error ("The name of the enum node cannot be '*'");
			return false;
		} else if (currFullExpr.contains ("enum"))
		{
			Skript.error ("Cannot use the world 'enum' in the name of an enum node");
			return false;
		}

		/*Two different syntaxes, two different ways of splitting the expression*/
		int splitLoc;
		if (currFullExpr.contains (":"))
		{
			splitLoc = currFullExpr.indexOf (":");
			enumNode = currFullExpr.substring (0, splitLoc);
		} else
		{
			splitLoc = currFullExpr.indexOf (" to ");
			//We have the string "set value " at the start of this expression
			enumNode = currFullExpr.substring (10, splitLoc);
		}

		expr1 = expr[1]; //will become 'value'

		return EnumManager.isValidEvent ("Enum values cannot be declared outside of Enums event.");
	}

	@ Override
	public String toString (@ Nullable Event e, boolean debug)
	{
		return "Enum object " + enumNode + " from enum " + currEnum + ((parentEnum == null) ? "" : " and it's parent enum " + parentEnum);
	}

	@ SuppressWarnings ("unchecked")
	@ Override
	protected void execute (Event e)
	{
		String parentFullExpr = EnumManager.getConKey (this.getParent ().toString ());
		if (parentFullExpr.contains ("parent"))
		{
			String[] value = parentFullExpr.split (" from parent ");
			currEnum = value[0];
			parentEnum = value[1];
		} else
		{
			currEnum = this.getParent ().toString ().replaceFirst ("(?i)enum ", "");
		}

		if (enumNode == null)
			return;

		try
		{
			value = ((Expression<Object>) expr1).getSingle (e); //the object needs to be valid
		} catch (SkriptAPIException ex)
		{

			/* Check if the object is a boolean, if not return error 
			 * The reason for the extra ' is that is how the toString() 
			 * function works here...
			 */
			if ("'true'".equalsIgnoreCase (expr1.toString ()))
			{
				value = true;
			} else if ("'false'".equalsIgnoreCase (expr1.toString ()))
			{
				value = false;
			} else
			{
				Skript.error ("The enum value " + expr1 + " is not a valid object and will NOT be loaded in! (expression: " + currFullExpr + ")");
				return;
			}
		}

		EnumManager.addValue (parentEnum, currEnum, enumNode, value);
	}
}