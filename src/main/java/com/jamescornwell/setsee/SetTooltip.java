package com.jamescornwell.setsee;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class SetTooltip
{
	static String forItem(SetSeeState.Snapshot snapshot, int itemId)
	{
		SetMatcher.Status box = snapshot.boxes.get(itemId);
		List<SetMatcher.Status> statuses = box == null
			? snapshot.components.getOrDefault(itemId, Collections.emptyList()) : Collections.singletonList(box);
		StringBuilder text = new StringBuilder();
		for (SetMatcher.Status status : statuses)
		{
			if (text.length() > 0)
			{
				text.append("<br><br>");
			}
			SetRecipe r = status.recipe;
			text.append("<col=ffffff>#").append(r.label).append(" ").append(safe(r.name)).append("</col>");
			if (status.membersBlocked)
			{
				text.append("<br>Requires a members world");
			}
			if (status.packCount > 0)
			{
				text.append("<br>Can pack: ").append(status.packCount);
				if (status.notedPacks > 0)
				{
					text.append(" (noted: ").append(status.notedPacks).append(")");
				}
			}
			text.append("<br>Loose components: ").append(status.ownedTypes).append("/").append(r.components.size()).append(" types owned");
			if (status.unnotedPacks == 0 && status.notedPacks == 0)
			{
				Map<String, Long> normal = status.missing(false);
				Map<String, Long> noted = status.missing(true);
				boolean useNotes = total(noted) < total(normal);
				Map<String, Long> missing = useNotes ? noted : normal;
				if (status.ownedTypes == r.components.size())
				{
					text.append("<br>Use all noted or all unnoted components; mixed forms cannot be packed.");
				}
				text.append("<br>Missing for ").append(useNotes ? "noted" : "unnoted").append(" packing:");
				for (Map.Entry<String, Long> entry : missing.entrySet())
				{
					text.append("<br> - ").append(safe(entry.getKey())).append(" x").append(entry.getValue());
				}
			}
			if (status.boxes > 0)
			{
				text.append("<br>Boxed sets: ").append(status.boxes).append(" (unpack from inventory)");
				int consumed = status.count(itemId) > 0 && (itemId == r.id || itemId == r.noteId)
					? itemId : status.count(r.id) > 0 ? r.id : r.noteId;
				int normalExtra = status.extraSlotsForOneBox(consumed, false);
				int notedExtra = status.extraSlotsForOneBox(consumed, true);
				text.append("<br>Free inventory slots: ").append(status.freeSlots);
				if (normalExtra > 0 || notedExtra > 0)
				{
					text.append("<br>Space estimate for one box: free ").append(normalExtra)
						.append(" more for unnoted contents / ").append(notedExtra).append(" for noted contents.");
				}
			}
			Set<Integer> shared = new LinkedHashSet<>();
			for (SetRecipe.Component c : r.components)
			{
				for (SetMatcher.Status other : snapshot.components.getOrDefault(c.id, Collections.emptyList()))
				{
					if (other.recipe.id != r.id)
					{
						shared.add(other.recipe.label);
					}
				}
			}
			if (!shared.isEmpty())
			{
				text.append("<br>Shares components with ");
				boolean first = true;
				for (int label : shared)
				{
					if (!first)
					{
						text.append(", ");
					}
					text.append('#').append(label);
					first = false;
				}
				text.append(". Packing one updates the others.");
			}
		}
		return text.toString();
	}

	private static long total(Map<String, Long> values)
	{
		return values.values().stream().mapToLong(Long::longValue).sum();
	}

	private static String safe(String text)
	{
		return text.replace("<", "").replace(">", "");
	}
}
