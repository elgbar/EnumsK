package com.kh498.expressions;

import java.util.LinkedHashMap;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.kh498.main.EnumManager;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprSingularEnum extends SimpleExpression<Object>
{

	private Expression<Object> e0;
	private Expression<Object> e1;

	@ SuppressWarnings ("unchecked")
	@ Override
	public boolean init (Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult)
	{
		e0 = (Expression<Object>) exprs[0];
		e1 = (Expression<Object>) exprs[1];
		return true;
	}

	@ Override
	@ Nullable
	protected Object[] get (Event event)
	{
		final Object enumName = e0.getSingle (event);
		final Object enumValue = e1.getSingle (event);

		/* Get the object by first getting the value map (Map<Object, Object>) then getting the value */

		try
		{
			@ SuppressWarnings ("unchecked")
			Object[] obj2 = { ((LinkedHashMap<Object, Object>) EnumManager.getEnums ().get (enumName)).get (enumValue) };
			return obj2;
		} catch (NullPointerException ex)
		{
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
		return "single enum value";
	}

}
