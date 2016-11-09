package com.kh498.main;

import java.util.LinkedHashMap;
import java.util.Map;

import com.kh498.events.EnumEvent;
import com.kh498.events.EvtEnum;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;

/**
 * @author kh498
 */
public class EnumManager
{
	private static LinkedHashMap<Object, Object> skEnums;

	EnumManager ()
	{
		skEnums = new LinkedHashMap<Object, Object> ();
	}

	/**
	 * @param newEnumName
	 *            Object
	 * @return False if an enum with that name already exists
	 */
	public static boolean addEnum (Object newEnumName)
	{
		if (isValidEnum (newEnumName))
		{
			Skript.error ("An enum with the name '" + newEnumName + "' already exists.");
			return false;
		}
		skEnums.put (newEnumName, new LinkedHashMap<Object, Object> ());
		return true;
	}

	//TODO Maybe make the enum hold multible values (making each values a new class)
	/**
	 * @param enumName
	 *            Object, The enum to add the value to
	 * @param valueName
	 *            Object, The name of the enum value
	 * @param obj
	 *            Object, the object the enum is refering to
	 * @return false if the mother enum does exist
	 */
	public static boolean addValue (Object enumName, Object valueName, Object obj)
	{
		if (!isValidEnum (enumName))
		{
			Skript.error ("The enum '" + enumName + "' does not exist.");
			return false;
		}

		@ SuppressWarnings ("unchecked")
		Map<Object, Object> enumValues = (Map<Object, Object>) skEnums.get (enumName);

		if (enumValues == null)
		{
			enumValues = new LinkedHashMap<Object, Object> ();
		}

		enumValues.put (valueName, obj);

		return true;
	}

	/**
	 * @param errorMsg
	 *            Message to display if the event is not valid
	 * @return true if the current skript event is of the type {@link EvtEnum}
	 */
	public static boolean isValidEvent (String errorMsg)
	{
		if (!ScriptLoader.isCurrentEvent (EnumEvent.class))
		{
			Skript.error (errorMsg);
			return false;
		}
		return true;
	}

	/**
	 * @param enumName
	 *            Object
	 * @return boolean
	 */
	private static boolean isValidEnum (Object enumName)
	{
		return skEnums.containsKey (enumName);
	}

	public static LinkedHashMap<Object, Object> getEnums ()
	{
		return skEnums;
	}

	public static void flushEnums ()
	{
		skEnums.clear ();
	}
}
