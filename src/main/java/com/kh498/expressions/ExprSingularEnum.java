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

	private Expression<String> e0;
	private Expression<String> e1;

	@ SuppressWarnings ("unchecked")
	@ Override
	public boolean init (Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult)
	{
		e0 = (Expression<String>) exprs[0];
		e1 = (Expression<String>) exprs[1];
		return true;
	}

	@ Override
	@ Nullable
	protected Object[] get (Event event)
	{
		final String enumName = e0.getSingle (event);
		final String enumValue = e1.getSingle (event);

		/* Get the object by first getting the value map (Map<String, Object>) then getting the value */
		@ SuppressWarnings ("unchecked")
		Object[] obj = { ((LinkedHashMap<String, Object>) EnumManager.getEnums ().get (enumName)).get (enumValue) };
		return obj;
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
