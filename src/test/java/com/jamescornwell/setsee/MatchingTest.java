package com.jamescornwell.setsee;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

public class MatchingTest
{
	private final CacheFixture f = new CacheFixture();
	private final SetRecipe rune = f.recipe(13024);

	@Test
	public void partialLoosePiecesDoNotMarkOwnedBoxRed()
	{
		Map<Integer, Long> items = new HashMap<>();
		items.put(rune.id, 1L);
		items.put(1127, 1L);
		SetSeeState.Snapshot state = snapshot(rune, items);
		assertFalse(SetHighlights.forItem(state, rune.id, true, true).get(0).incomplete);
		assertFalse(SetHighlights.forItem(state, rune.id, false, true).get(0).incomplete);
		assertTrue(SetHighlights.forItem(state, 1127, false, true).get(0).incomplete);
	}

	@Test
	public void tooltipStillExplainsMissingPiecesWhenIncompleteHighlightsOff()
	{
		SetSeeState.Snapshot state = snapshot(rune, Collections.singletonMap(1127, 1L));
		assertTrue(SetHighlights.forItem(state, rune.id, true, false).isEmpty());
		String tip = SetTooltip.forItem(state, rune.id);
		assertTrue(tip.contains("Missing"));
		assertTrue(tip.contains("Rune full helm x1"));
		assertTrue(tip.contains("1/4 types owned"));
	}

	@Test
	public void sharedComponentsKeepBothMatchesAndUpdateAfterPacking()
	{
		SetRecipe skirt = f.recipe(13026);
		Map<Integer, Long> items = CacheFixture.components(rune, false, 1);
		items.put(1093, 1L);
		SetSeeState.Snapshot state = new SetSeeState.Snapshot(Arrays.asList(
			SetMatcher.match(rune, items, 23, true), SetMatcher.match(skirt, items, 23, true)));
		assertEquals(2, SetHighlights.forItem(state, 1127, false, false).size());
		assertTrue(SetTooltip.forItem(state, rune.id).contains("Shares components"));
		for (SetRecipe.Component c : rune.components)
		{
			items.remove(c.id);
		}
		assertEquals(0, SetMatcher.match(skirt, items, 26, true).packCount);
	}

	@Test
	public void mixedFormTooltipExplainsWhyFourOwnedTypesDoNotPack()
	{
		Map<Integer, Long> items = CacheFixture.components(rune, false, 1);
		items.remove(1127);
		items.put(1128, 1L);
		assertTrue(SetTooltip.forItem(snapshot(rune, items), rune.id).contains("mixed forms cannot be packed"));
	}

	@Test
	public void fullInventorySpaceAccountsForConsumedBoxAndExistingStacks()
	{
		Map<Integer, Long> items = new HashMap<>();
		items.put(rune.id, 1L);
		SetMatcher.Status status = SetMatcher.match(rune, items, 0, true);
		assertEquals(3, status.extraSlotsForOneBox(rune.id, false));
		items.put(rune.noteId, 5L);
		status = SetMatcher.match(rune, items, 0, true);
		assertEquals(4, status.extraSlotsForOneBox(rune.noteId, true));
		items.putAll(CacheFixture.components(rune, true, 2));
		assertEquals(0, SetMatcher.match(rune, items, 0, true).extraSlotsForOneBox(rune.noteId, true));
	}

	@Test
	public void separateFormQuantitiesAreAddedWithoutIntegerOverflow()
	{
		Map<Integer, Long> items = CacheFixture.components(rune, false, Integer.MAX_VALUE);
		items.putAll(CacheFixture.components(rune, true, Integer.MAX_VALUE));
		assertEquals(4294967294L, SetMatcher.match(rune, items, 0, true).packCount);
	}

	@Test
	public void sharedStackableIngredientIsNotCountedTwiceAcrossForms()
	{
		SetRecipe artificial = new SetRecipe(100, 101, "Quantity test", 1, false, Arrays.asList(
			new SetRecipe.Component(1, 2, "Noteable", 1, false),
			new SetRecipe.Component(3, -1, "Stackable", 2, true)));
		Map<Integer, Long> items = new HashMap<>();
		items.put(1, 5L);
		items.put(2, 5L);
		items.put(3, 6L);
		assertEquals(3, SetMatcher.match(artificial, items, 0, true).packCount);
	}

	@Test
	public void degradedBarrowsDoesNotSubstituteForNewBarrows()
	{
		SetRecipe ahrim = f.recipe(12881);
		Map<Integer, Long> items = CacheFixture.components(ahrim, false, 1);
		items.remove(4708);
		items.put(4856, 1L); // Ahrim's hood 100, a distinct item ID.
		assertEquals(0, SetMatcher.match(ahrim, items, 20, true).packCount);
	}

	@Test
	public void membersSetsCannotBeReportedReadyOnFreeWorlds()
	{
		SetRecipe ahrim = f.recipe(12881);
		SetMatcher.Status status = SetMatcher.match(ahrim, CacheFixture.components(ahrim, false, 1), 20, false);
		assertTrue(status.membersBlocked);
		assertEquals(0, status.packCount);
		assertFalse(status.incomplete());
	}

	private SetSeeState.Snapshot snapshot(SetRecipe recipe, Map<Integer, Long> items)
	{
		return new SetSeeState.Snapshot(Collections.singletonList(SetMatcher.match(recipe, items, 20, true)));
	}
}
