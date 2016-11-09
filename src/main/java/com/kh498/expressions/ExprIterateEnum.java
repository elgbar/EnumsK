package com.kh498.expressions;

import java.util.Collection;
import java.util.LinkedHashMap;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.kh498.main.EnumManager;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprIterateEnum extends SimpleExpression<Object>
{

	private Expression<Object> e0;

	@ SuppressWarnings ("unchecked")
	@ Override
	public boolean init (Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult)
	{
		e0 = (Expression<Object>) exprs[0];
		return true;
	}

	@ SuppressWarnings ("unchecked")
	@ Override
	@ Nullable
	protected Object[] get (Event event)
	{
		final Object enumName = e0.getSingle (event);

		try
		{
			Collection<Object> objectMap = ((LinkedHashMap<Object, Object>) EnumManager.getEnums ().get (enumName)).values ();
			return objectMap.toArray (new Object[objectMap.size ()]);
		} catch (NullPointerException ex)
		{
		}

		return null;
	}

	@ Override
	public boolean isSingle ()
	{
		return false;
	}

	@ Override
	public Class<? extends Object> getReturnType ()
	{
		return Object.class;
	}

	@ Override
	public String toString (@ Nullable Event e, boolean debug)
	{
		return "all values of an enum";
	}

}
