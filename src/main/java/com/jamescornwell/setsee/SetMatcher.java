package com.jamescornwell.setsee;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/** Pure inventory arithmetic. Item IDs remain exact; degraded/ornamented items are not substituted. */
final class SetMatcher
{
	static final class Status
	{
		final SetRecipe recipe;
		final long packCount;
		final long unnotedPacks;
		final long notedPacks;
		final long boxes;
		final int ownedTypes;
		final boolean membersBlocked;
		final Map<Integer, Long> inventory;
		final int freeSlots;

		Status(SetRecipe recipe, Map<Integer, Long> inventory, int freeSlots, boolean membersWorld)
		{
			this.recipe = recipe;
			this.inventory = inventory;
			this.freeSlots = freeSlots;
			membersBlocked = recipe.members && !membersWorld;
			long normal = Long.MAX_VALUE;
			long noted = Long.MAX_VALUE;
			long shared = Long.MAX_VALUE;
			boolean hasNoteVariant = false;
			int owned = 0;
			for (SetRecipe.Component c : recipe.components)
			{
				normal = Math.min(normal, count(c.id) / c.quantity);
				noted = Math.min(noted, count(c.notedOrSelf()) / c.quantity);
				if (c.noteId > 0)
				{
					hasNoteVariant = true;
				}
				else
				{
					shared = Math.min(shared, count(c.id) / c.quantity);
				}
				if (count(c.id) + (c.noteId > 0 ? count(c.noteId) : 0) >= c.quantity)
				{
					owned++;
				}
			}
			unnotedPacks = normal;
			notedPacks = hasNoteVariant ? noted : 0;
			packCount = membersBlocked ? 0 : Math.min(normal + notedPacks, shared);
			boxes = count(recipe.id) + count(recipe.noteId);
			ownedTypes = owned;
		}

		long count(int id)
		{
			return inventory.getOrDefault(id, 0L);
		}

		boolean hasLooseItems()
		{
			for (SetRecipe.Component c : recipe.components)
			{
				if (count(c.id) > 0 || count(c.noteId) > 0)
				{
					return true;
				}
			}
			return false;
		}

		boolean incomplete()
		{
			return unnotedPacks == 0 && notedPacks == 0 && hasLooseItems();
		}

		Map<String, Long> missing(boolean noted)
		{
			Map<String, Long> missing = new LinkedHashMap<>();
			for (SetRecipe.Component c : recipe.components)
			{
				long need = c.quantity - count(noted ? c.notedOrSelf() : c.id);
				if (need > 0)
				{
					missing.put(c.name, need);
				}
			}
			return Collections.unmodifiableMap(missing);
		}

		/** Space estimate for ONE box, for either possible output form. */
		int extraSlotsForOneBox(int consumedBoxId, boolean notedOutput)
		{
			int slots = 0;
			for (SetRecipe.Component c : recipe.components)
			{
				int output = notedOutput ? c.notedOrSelf() : c.id;
				boolean stackable = c.stackable || output != c.id;
				slots += stackable ? (count(output) == 0 ? 1 : 0) : c.quantity;
			}
			boolean consumedSlotFreed = consumedBoxId == recipe.id || count(consumedBoxId) == 1;
			return Math.max(0, slots - (consumedSlotFreed ? 1 : 0) - freeSlots);
		}
	}

	static Status match(SetRecipe recipe, Map<Integer, Long> inventory, int freeSlots, boolean membersWorld)
	{
		return new Status(recipe, inventory, freeSlots, membersWorld);
	}
}
