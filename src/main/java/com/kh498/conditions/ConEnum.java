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
public class ConEnum extends Condition
{
	private Object expr0;
	public static String value;

	@ Override
	public boolean init (Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3)
	{
		expr0 = expr[0];

		return EnumManager.isValidEvent ("A new enum cannot be declared outside of Enums event.");
	}

	public static String getvalue ()
	{
		return value;
	}

	@ Override
	public String toString (@ Nullable Event arg0, boolean arg1)
	{
		return "Enum " + value;
	}

	@ SuppressWarnings ("unchecked")
	@ Override
	public boolean check (Event e)
	{
		value = ((Expression<String>) expr0).getSingle (e);

		return EnumManager.addEnum (value);
	}

}
