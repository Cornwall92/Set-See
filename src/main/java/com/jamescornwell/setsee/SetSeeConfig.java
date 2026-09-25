package com.jamescornwell.setsee;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("set-see")
public interface SetSeeConfig extends Config
{
	@ConfigItem(keyName = "showIncomplete", name = "Show incomplete sets",
		description = "Highlight owned components and incomplete set entries with red dashed borders.", position = 0)
	default boolean showIncomplete()
	{
		return false;
	}

	@ConfigItem(keyName = "showLabels", name = "Show matching labels",
		description = "Display matching set numbers so colours are not the only way to identify a set.", position = 1)
	default boolean showLabels()
	{
		return true;
	}

	@ConfigItem(keyName = "showTooltips", name = "Show component tooltips",
		description = "Show missing pieces, quantities, shared components and unpacking space estimates.", position = 2)
	default boolean showTooltips()
	{
		return true;
	}
}
