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
public class ConEnumValue extends Condition
{
	private Object expr0;
	private Object expr1;

	private String value;
	private Object obj;

	@ Override
	public boolean init (Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, ParseResult parseResult)
	{
		expr0 = expr[0];
		expr1 = expr[1];

		return EnumManager.isValidEvent ("Enum values cannot be declared outside of Enums event.");
	}

	@ Override
	public String toString (@ Nullable Event e, boolean debug)
	{
		return "Enum value";
	}

	@ SuppressWarnings ("unchecked")
	@ Override
	public boolean check (Event e)
	{
		/* Get the parents expression (the key to the enum-map) */
		String key = this.getParent ().toString ().replace ("Enum ", "");

		value = ((Expression<String>) expr0).getSingle (e);
		obj = ((Expression<Object>) expr1).getSingle (e);

		return EnumManager.addValue (key, value, obj);
	}

}