/**
 * This file is part of Enumsk
 * 
 * Copyright (C) 2016, kh498
 * 
 * Enumsk is free software: you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * Enumsk is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Enumsk. If not, see
 * <http://www.gnu.org/licenses/>.
 */

package com.kh498.main;

import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

import com.kh498.conditions.ConEnum;
import com.kh498.conditions.ConEnumValue;
import com.kh498.events.EnumEvent;
import com.kh498.events.EvtEnum;
import com.kh498.expressions.ExprIterateEnum;
import com.kh498.expressions.ExprSingularEnum;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;

/**
 * @author kh498
 */
public class EnumsK extends JavaPlugin
{
	private static EnumsK instance;
	private static EnumManager enumList;

	@ Override
	public void onEnable ()
	{
		instance = this;

		enumList = new EnumManager ();
		if (Skript.isAcceptRegistrations ())
		{
			Skript.registerAddon (this);

			/* The event */
			//Skript.registerEvent ("enums register", SimpleEvent.class, EvtEnum.class, "Enums");
			Skript.registerEvent ("enums register", EvtEnum.class, EnumEvent.class, "Enums");

			/* Conditions */
			Skript.registerCondition (ConEnum.class, "Enum %object%");
			Skript.registerCondition (ConEnumValue.class, "%object%[]:[]%object%");

			/* Expressions */
			Skript.registerExpression (ExprSingularEnum.class, Object.class, ExpressionType.PROPERTY, "\\|%object%.%object%\\|");
			Skript.registerExpression (ExprIterateEnum.class, Object.class, ExpressionType.PROPERTY, "\\|%object%.*\\|");

		} else
		{
			this.getLogger ().log (Level.SEVERE, "Could not enable EnumsK due too Skript not accepting registrations.");
			this.getServer ().getPluginManager ().disablePlugin (this);
			return;
		}
	}

	/**
	 * @return the instance of the plugin
	 */
	public static EnumsK getInstance ()
	{
		return instance;
	}

	/**
	 * @return the list of all enums
	 */
	public static EnumManager getEnumList ()
	{
		return enumList;
	}
}