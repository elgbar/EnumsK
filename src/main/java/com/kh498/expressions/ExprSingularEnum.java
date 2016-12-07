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

package com.kh498.expressions;

import java.util.LinkedHashMap;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.kh498.main.EnumManager;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprSingularEnum extends SimpleExpression<Object>
{
	private String currFullExpr;

	private String[] enums1;
	private String topEnum;

	@ Override
	public boolean init (Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult)
	{
		/*The full text of this expresstion*/
		currFullExpr = parseResult.expr;
		String[] exprArray;
		if (currFullExpr.charAt (0) == '|')
		{
			/*Split the full expression into an array (test[]), then set test[0] to topEnum and add the other values to enums1[]*/
			exprArray = currFullExpr.replaceAll ("\\|", "").split ("\\.");

			topEnum = EnumManager.getProperEnumName (exprArray[0]);

			enums1 = new String[exprArray.length - 1];
			for (int i = 0; i < exprArray.length - 1; i++)
			{
				enums1[i] = exprArray[i + 1];
			}
		} else
		{
			exprArray = currFullExpr.replaceAll ("(values?|from|of|all| )", "").split ("enum");
			enums1 = new String[1];
			topEnum = EnumManager.getProperEnumName (exprArray[1]);
			enums1[0] = EnumManager.getProperEnumName (exprArray[0]);
		}
		return (this.getEnum () != null) ? true : false;
	}

	@ Override
	@ Nullable
	protected Object[] get (Event e)
	{
		return this.getEnum ();
	}

	@ Override
	public boolean isSingle ()
	{
		return true;
	}

	@ Override
	public Class<? extends Object> getReturnType ()
	{
		return Object.class;
	}

	@ Override
	public String toString (@ Nullable Event e, boolean debug)
	{
		return "single enum value";
	}

	@ SuppressWarnings ("unchecked")
	private Object[] getEnum ()
	{
		try
		{
			Object map = EnumManager.getEnums ().get (topEnum);

			if (enums1.length == 1) //there is no sub enum
			{
				map = ((LinkedHashMap<String, Object>) map).get (enums1[0]);
			} else
			{
				/*Loop through each layer of maps untill you get your value*/
				for (Object obj : enums1)
				{
					map = ((LinkedHashMap<String, Object>) map).get (obj);
				}
			}
			/*cast the object from the map to an Object array and return it*/
			if (map == null)
			{
				throw new NullPointerException ();
			}
			final Object[] returnObj = { map };
			return returnObj;
		} catch (NullPointerException ex)
		{
			return null;
		}
	}

}
