package com.jamescornwell.setsee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.IntFunction;

final class CatalogueLoader
{
	// No named RuneLite enum constants exist for these. Verified in Jagex's
	// itemsets_init / script8020 and itemsets_side_draw client scripts.
	static final int SET_LIST = 1033;
	static final int SET_LOOKUP = 1034;

	static final class ItemDetails
	{
		final int id;
		final int noteId;
		final String name;
		final boolean stackable;
		final boolean members;

		ItemDetails(int id, int noteId, String name, boolean stackable, boolean members)
		{
			this.id = id;
			this.noteId = noteId;
			this.name = name;
			this.stackable = stackable;
			this.members = members;
		}
	}

	static List<SetRecipe> load(IntFunction<Map<Integer, Integer>> enums, IntFunction<ItemDetails> items)
	{
		Map<Integer, Integer> index = enums.apply(SET_LIST);
		Map<Integer, Integer> reverse = enums.apply(SET_LOOKUP);
		if (index == null || reverse == null || index.isEmpty() || reverse.isEmpty())
		{
			throw new IllegalStateException("GE set definitions are unavailable");
		}
		index = new TreeMap<>(index);
		Map<Integer, SetRecipe> recipes = new LinkedHashMap<>();
		Map<Integer, Integer> seenEnums = new LinkedHashMap<>();
		for (Map.Entry<Integer, Integer> entry : index.entrySet())
		{
			if (entry.getKey() < 0 || entry.getValue() <= 0)
			{
				throw new IllegalStateException("Unexpected GE set index");
			}
			Map<Integer, Integer> definition = enums.apply(entry.getValue());
			if (definition == null)
			{
				throw new IllegalStateException("Missing GE set recipe " + entry.getValue());
			}
			definition = new TreeMap<>(definition);
			Integer boxId = definition.get(-1);
			if (boxId == null || boxId <= 0 || !entry.getValue().equals(reverse.get(boxId)))
			{
				throw new IllegalStateException("GE set index and reverse lookup disagree");
			}
			if (recipes.containsKey(boxId))
			{
				if (!entry.getValue().equals(seenEnums.get(boxId)))
				{
					throw new IllegalStateException("Conflicting GE set recipes");
				}
				continue; // The current game list contains duplicate display entries.
			}
			Map<Integer, Integer> amounts = new LinkedHashMap<>();
			int expectedKey = 0;
			for (Map.Entry<Integer, Integer> component : definition.entrySet())
			{
				if (component.getKey() == -1)
				{
					continue;
				}
				if (component.getKey() != expectedKey++ || component.getValue() <= 0)
				{
					throw new IllegalStateException("Unexpected GE component definition");
				}
				amounts.merge(component.getValue(), 1, Integer::sum);
			}
			if (amounts.isEmpty())
			{
				throw new IllegalStateException("GE set has no components");
			}
			List<SetRecipe.Component> components = new ArrayList<>();
			for (Map.Entry<Integer, Integer> amount : amounts.entrySet())
			{
				ItemDetails item = readItem(items, amount.getKey());
				components.add(new SetRecipe.Component(item.id, item.noteId, item.name, amount.getValue(), item.stackable));
			}
			ItemDetails box = readItem(items, boxId);
			recipes.put(boxId, new SetRecipe(box.id, box.noteId, box.name, recipes.size() + 1, box.members, components));
			seenEnums.put(boxId, entry.getValue());
		}
		if (!recipes.keySet().equals(reverse.keySet()))
		{
			throw new IllegalStateException("GE set catalogue is incomplete");
		}
		return Collections.unmodifiableList(new ArrayList<>(recipes.values()));
	}

	private static ItemDetails readItem(IntFunction<ItemDetails> items, int id)
	{
		ItemDetails item = items.apply(id);
		if (item == null || item.id != id || item.name == null)
		{
			throw new IllegalStateException("Missing GE set item " + id);
		}
		return item;
	}
}
