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

package com.kh498.main;

import java.util.LinkedHashMap;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.kh498.events.EnumEvent;
import com.kh498.events.EvtEnum;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAPIException;
import ch.njol.skript.lang.Expression;

/**
 * @author kh498
 */
public class EnumManager
{
	private static LinkedHashMap<String, Object> skEnums;

	private static String lastExpr = null;

	EnumManager ()
	{
		skEnums = new LinkedHashMap<String, Object> ();
	}

	/**
	 * 
	 * @param newEnumName
	 *        The name of the new enum
	 * @param parentEnum
	 *        If null add the enum to main Map if not add to the parentEnum
	 * @return False if an enum with that name already exists
	 */
	@ SuppressWarnings ("unchecked")
	public static boolean addEnum (String newEnumName, @ Nullable String parentEnum)
	{
		if (parentEnum == null || parentEnum.isEmpty ())
		{
			skEnums.put (newEnumName, new LinkedHashMap<String, Object> ());
		} else if (skEnums.containsKey (parentEnum) || skEnums.containsKey (parentEnum))
		{
			/*put a new enum inside the parentEnum*/
			LinkedHashMap<String, Object> tempMap = (LinkedHashMap<String, Object>) skEnums.get (parentEnum);
			if (tempMap == null)
			{
				tempMap = new LinkedHashMap<String, Object> ();
			}
			tempMap.put (newEnumName, new LinkedHashMap<String, Object> ());
		} else
		{
			return false;
		}

		LinkedHashMap<String, Object> g = null;

		try
		{
			g = (LinkedHashMap<String, Object>) skEnums.get (parentEnum);
		} catch (NullPointerException e)
		{
			System.out.println ("Failed to get parent enum");
			return false;
		}

		if (g != null)
		{
			try
			{
				@ SuppressWarnings ("unused")
				Object h = g.get (newEnumName);
			} catch (NullPointerException e)
			{
				System.out.println ("Failed to get sub enum");
				return false;
			}
		}
//		System.out.println ("All systems good! Values: newEnumName=" + newEnumName + ", parentEnum=" + parentEnum);
		return true;
	}

	//TODO Maybe make the enum hold multible values (making each values a new class)
	/**
	 * @param enumName
	 *        Object, The enum to add the value to
	 * @param valueName
	 *        Object, The name of the enum value
	 * @param obj
	 *        Object, the object the enum is refering to
	 * @return false if the mother enum does exist
	 */
	@ SuppressWarnings ("unchecked")
	public static boolean addValue (@ Nullable String parentEnum, String currEnum, String valueName, Object obj)
	{
		LinkedHashMap<String, Object> enumValues = null;

		if (parentEnum == null || parentEnum.isEmpty ())
		{
			if (!skEnums.containsKey (currEnum))
			{
				Skript.error ("The enum " + currEnum + " does not exist.");
				return false;
			}
			enumValues = (LinkedHashMap<String, Object>) skEnums.get (currEnum);
		} else
		{
			if (!skEnums.containsKey (parentEnum))
			{
				Skript.error ("The enum " + parentEnum + " does not exist.");
				return false;
			} else if (!((LinkedHashMap<String, Object>) skEnums.get (parentEnum)).containsKey (currEnum))
			{
				Skript.error ("The sub enum " + currEnum + " does not exist.");
				return false;
			}

			enumValues = (LinkedHashMap<String, Object>) ((LinkedHashMap<String, Object>) skEnums.get (parentEnum)).get (currEnum);
		}

		if (enumValues == null)
		{
			enumValues = new LinkedHashMap<String, Object> ();
		}
		enumValues.put (valueName, obj);

		test (parentEnum, currEnum, valueName);
		return true;
//		return enumValues.put (valueName, obj) != null;
	}

	@ SuppressWarnings ("unchecked")
	public static void test (String parentEnum, String currEnum, String valueName)
	{

		if (parentEnum == null || parentEnum.isEmpty ()) //only test sub enums
			return;

		System.out.println ("P: " + parentEnum + " | N: " + currEnum + " | V: " + valueName);

		LinkedHashMap<String, Object> a = null;
		LinkedHashMap<String, Object> b = null;
		Object c = null;
		try
		{
			a = (LinkedHashMap<String, Object>) EnumManager.getEnums ().get (parentEnum);
			b = (LinkedHashMap<String, Object>) a.get (currEnum);
			c = b.get (valueName);
		} catch (NullPointerException e1)
		{
		}

		System.out.println ("parent: " + a);
		System.out.println ("Name: " + b);
		System.out.println ("Value: " + c);
		if (c == null)
			System.out.println ("Failed to get object");

	}

	/**
	 * @param errorMsg
	 *        Message to display if the event is not valid
	 * @return true if the current skript event is of the type {@link EvtEnum}
	 */
	public static boolean isValidEvent (String errMsg)
	{
		if (!ScriptLoader.isCurrentEvent (EnumEvent.class))
		{
			Skript.error (errMsg);
			return false;
		}
		return true;
	}

	public static LinkedHashMap<String, Object> getEnums ()
	{
		return skEnums;
	}

	public static void flushEnums ()
	{
		skEnums.clear ();
	}

	/**
	 * @param e
	 *        The current event
	 * @param expr
	 *        The expression gotten from init()
	 * @return Either expr from the event, if not valid then the string version of expr
	 */
	@ SuppressWarnings ("unchecked")
	public static Object getProperEnumName (Event e, Object expr)
	{
		try
		{
			return ((Expression<Object>) expr).getSingle (e);
		} catch (SkriptAPIException ex)
		{
			return "" + expr;
		}
	}

	/**
	 * @return the lastExpr
	 */
	public static String getLastExpr ()
	{
		return lastExpr;
	}

	/**
	 * @param lastExpr
	 *        the lastExpr to set
	 */
	public static void setLastExpr (String lastExpr)
	{
		EnumManager.lastExpr = lastExpr;
	}

	public static String getConKey (@ Nullable String key)
	{
		/* Get the parents expression (the key to the enum-map) */
		if (key == null || key.isEmpty ())
			return null;
		return key.replaceFirst ("(?i)Sub ", "").replaceFirst ("(?i)enum ", "");
	}
}
