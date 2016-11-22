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

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprSingularSubEnum extends SimpleExpression<Object>
{

	private Expression<Object> expr0;
	private Expression<Object> expr1;
	private Expression<Object> expr2;
	private String fullExpr;

	@ SuppressWarnings ("unchecked")
	@ Override
	public boolean init (Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult)
	{
		expr0 = (Expression<Object>) exprs[0];
		expr1 = (Expression<Object>) exprs[1];
		expr2 = (Expression<Object>) exprs[2];
		fullExpr = parseResult.expr;
		return true;
	}

	@ Override
	@ Nullable
	@ SuppressWarnings ("unchecked")
	protected Object[] get (Event e)
	{
		String enumParent = EnumManager.getProperEnumName (e, expr0).toString ().replaceAll ("'", "");
		String enumName = EnumManager.getProperEnumName (e, expr1).toString ().replaceAll ("'", "");
		String enumValue = EnumManager.getProperEnumName (e, expr2).toString ().replaceAll ("'", "");

		System.out.println (enumValue);
		EnumManager.test (enumParent, enumName, enumValue);

		/* Get the object by first getting the value map (Map<Object, Object>) then getting the value */
		if (fullExpr.charAt (0) == '|')
		{
			try
			{
				Object[] obj = {
						((LinkedHashMap<String, Object>) ((LinkedHashMap<String, Object>) EnumManager.getEnums ().get (enumParent)).get (enumName))
								.get (enumValue) };
				return obj;
			} catch (NullPointerException e1)
			{
			}
		} else
		{
			try
			{
				Object[] obj = {
						((LinkedHashMap<String, Object>) ((LinkedHashMap<String, Object>) EnumManager.getEnums ().get (enumValue)).get (enumName))
								.get (enumParent) };
				return obj;
			} catch (NullPointerException e2)
			{
			}
		}
		return null;
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
		return "single sub enum value '" + fullExpr + "'";
	}

}
