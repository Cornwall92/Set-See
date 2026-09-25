package com.jamescornwell.setsee;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

final class SetHighlights
{
	static final Color INCOMPLETE = new Color(255, 72, 72);
	// Deliberately no red/orange/pink: red means incomplete, never a complete group.
	private static final Color[] PALETTE = {
		new Color(255, 225, 50), new Color(62, 211, 244), new Color(120, 235, 109),
		new Color(183, 157, 255), new Color(255, 255, 160), new Color(96, 174, 255),
		new Color(67, 235, 182), new Color(197, 235, 75), new Color(207, 200, 255),
		new Color(124, 245, 239), new Color(208, 255, 188), new Color(210, 222, 245)
	};

	static Color colour(SetRecipe recipe)
	{
		return PALETTE[(recipe.label - 1) % PALETTE.length];
	}

	static final class Mark
	{
		final SetRecipe recipe;
		final boolean incomplete;

		Mark(SetRecipe recipe, boolean incomplete)
		{
			this.recipe = recipe;
			this.incomplete = incomplete;
		}

		Color colour()
		{
			return incomplete ? INCOMPLETE : SetHighlights.colour(recipe);
		}
	}

	static List<Mark> forItem(SetSeeState.Snapshot snapshot, int itemId, boolean setWindow, boolean showIncomplete)
	{
		List<Mark> marks = new ArrayList<>();
		SetMatcher.Status box = snapshot.boxes.get(itemId);
		if (box != null)
		{
			if (!box.membersBlocked && (setWindow ? box.packCount > 0 || box.boxes > 0 : box.count(itemId) > 0))
			{
				marks.add(new Mark(box.recipe, false));
			}
			else if (setWindow && showIncomplete && box.incomplete())
			{
				marks.add(new Mark(box.recipe, true));
			}
			return marks;
		}
		for (SetMatcher.Status status : snapshot.components.getOrDefault(itemId, java.util.Collections.emptyList()))
		{
			boolean ready = false;
			for (SetRecipe.Component c : status.recipe.components)
			{
				if ((c.id == itemId && (status.unnotedPacks > 0 || (c.noteId < 0 && status.packCount > 0)))
					|| (c.noteId == itemId && status.notedPacks > 0))
				{
					ready = true;
				}
			}
			if (ready && !status.membersBlocked)
			{
				marks.add(new Mark(status.recipe, false));
			}
			else if (!ready && showIncomplete && status.hasLooseItems())
			{
				marks.add(new Mark(status.recipe, true));
			}
		}
		marks.sort((a, b) -> Boolean.compare(a.incomplete, b.incomplete));
		return marks;
	}
}
