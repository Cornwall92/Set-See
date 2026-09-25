package com.jamescornwell.setsee;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public final class SetSeeLauncher
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(SetSeePlugin.class);
		RuneLite.main(args);
	}
}
