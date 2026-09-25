package com.jamescornwell.setsee;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.*;
import static org.junit.Assume.assumeTrue;

@RunWith(Parameterized.class)
public class EveryRecipeTest
{
	@Parameterized.Parameters(name = "{0}")
	public static Collection<Object[]> data()
	{
		return new CacheFixture().recipes.stream().map(r -> new Object[]{r.name, r}).collect(Collectors.toList());
	}

	private final SetRecipe recipe;

	public EveryRecipeTest(String name, SetRecipe recipe)
	{
		this.recipe = recipe;
	}

	@Test
	public void unnotedComponentsPack()
	{
		assertEquals(3, SetMatcher.match(recipe, CacheFixture.components(recipe, false, 3), 0, true).packCount);
	}

	@Test
	public void notedComponentsPackWithoutDoubleCountingStackablePages()
	{
		assertEquals(7, SetMatcher.match(recipe, CacheFixture.components(recipe, true, 7), 0, true).packCount);
	}

	@Test
	public void eachMissingComponentPreventsPacking()
	{
		for (SetRecipe.Component c : recipe.components)
		{
			Map<Integer, Long> counts = CacheFixture.components(recipe, false, 1);
			counts.remove(c.id);
			SetMatcher.Status status = SetMatcher.match(recipe, counts, 20, true);
			assertEquals(c.name, 0, status.packCount);
			assertTrue(c.name, status.missing(false).containsKey(c.name));
		}
	}

	@Test
	public void mixedFormsDoNotPack()
	{
		assumeTrue(recipe.components.stream().filter(c -> c.noteId > 0).count() >= 2);
		Map<Integer, Long> counts = CacheFixture.components(recipe, false, 1);
		SetRecipe.Component c = recipe.components.get(0);
		counts.remove(c.id);
		counts.put(c.noteId, (long) c.quantity);
		SetMatcher.Status status = SetMatcher.match(recipe, counts, 20, true);
		assertEquals(0, status.packCount);
		assertTrue(status.incomplete());
		assertEquals(recipe.components.size(), status.ownedTypes);
	}

	@Test
	public void bothBoxFormsAreRecognised()
	{
		for (int id : new int[]{recipe.id, recipe.noteId})
		{
			SetMatcher.Status status = SetMatcher.match(recipe, Collections.singletonMap(id, 5L), 20, true);
			assertEquals(5, status.boxes);
			SetSeeState.Snapshot snapshot = new SetSeeState.Snapshot(Collections.singletonList(status));
			assertFalse(SetHighlights.forItem(snapshot, id, false, false).get(0).incomplete);
			assertFalse(SetHighlights.forItem(snapshot, recipe.id, true, false).get(0).incomplete);
		}
	}

	@Test
	public void emptyInventoryNeverHighlights()
	{
		SetMatcher.Status status = SetMatcher.match(recipe, Collections.emptyMap(), 28, true);
		SetSeeState.Snapshot snapshot = new SetSeeState.Snapshot(Collections.singletonList(status));
		assertEquals(0, status.packCount);
		assertFalse(status.incomplete());
		assertTrue(SetHighlights.forItem(snapshot, recipe.id, true, true).isEmpty());
	}

	@Test
	public void completeColoursNeverUseReservedRed()
	{
		float[] hsb = java.awt.Color.RGBtoHSB(SetHighlights.colour(recipe).getRed(),
			SetHighlights.colour(recipe).getGreen(), SetHighlights.colour(recipe).getBlue(), null);
		assertTrue(hsb[0] > 0.12 && hsb[0] < 0.8);
		assertNotEquals(SetHighlights.INCOMPLETE, SetHighlights.colour(recipe));
	}
}
