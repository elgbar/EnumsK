
package com.kh498.events;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.kh498.main.EnumManager;

import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SelfRegisteringSkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.Trigger;

/**
 * @author kh498
 */
public class EvtEnum extends SelfRegisteringSkriptEvent
{
	@ Override
	public boolean init (final Literal<?>[] args, final int matchedPattern, final ParseResult parser)
	{
		return true;
	}

	@ Nullable
	private Trigger t;

	@ Override
	public void register (final Trigger t)
	{
		this.t = t;
		EnumManager.flushEnums ();
		t.execute (new EnumEvent ());
	}

	@ Override
	public void unregister (final Trigger t)
	{
	}

	@ Override
	public void unregisterAll ()
	{
	}

	@ Override
	public String toString (final @ Nullable Event e, final boolean debug)
	{
		return "Reload enums";
	}

}
