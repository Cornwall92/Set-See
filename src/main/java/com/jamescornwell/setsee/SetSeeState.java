package com.jamescornwell.setsee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Singleton;

@Singleton
final class SetSeeState
{
	static final class Snapshot
	{
		static final Snapshot EMPTY = new Snapshot(Collections.emptyList());
		final Map<Integer, SetMatcher.Status> boxes;
		final Map<Integer, List<SetMatcher.Status>> components;

		Snapshot(List<SetMatcher.Status> statuses)
		{
			Map<Integer, SetMatcher.Status> boxIndex = new LinkedHashMap<>();
			Map<Integer, List<SetMatcher.Status>> componentIndex = new LinkedHashMap<>();
			for (SetMatcher.Status status : statuses)
			{
				SetRecipe r = status.recipe;
				boxIndex.put(r.id, status);
				if (r.noteId > 0)
				{
					boxIndex.put(r.noteId, status);
				}
				for (SetRecipe.Component c : r.components)
				{
					componentIndex.computeIfAbsent(c.id, k -> new ArrayList<>()).add(status);
					if (c.noteId > 0)
					{
						componentIndex.computeIfAbsent(c.noteId, k -> new ArrayList<>()).add(status);
					}
				}
			}
			componentIndex.replaceAll((id, values) -> Collections.unmodifiableList(values));
			boxes = Collections.unmodifiableMap(boxIndex);
			components = Collections.unmodifiableMap(componentIndex);
		}
	}

	volatile Snapshot snapshot = Snapshot.EMPTY;
}
