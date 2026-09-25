package com.jamescornwell.setsee;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;

public class CatalogueTest
{
	@Test
	public void coversEveryCurrentGeSetAndComponent()
	{
		CacheFixture fixture = new CacheFixture();
		assertEquals(131, fixture.enums.get(CatalogueLoader.SET_LIST).size());
		assertEquals(129, fixture.recipes.size());
		Set<Integer> boxes = new HashSet<>();
		Set<Integer> components = new HashSet<>();
		for (SetRecipe recipe : fixture.recipes)
		{
			assertTrue(boxes.add(recipe.id));
			assertNotEquals("null", recipe.name);
			assertTrue(recipe.noteId > 0);
			for (SetRecipe.Component c : recipe.components)
			{
				components.add(c.id);
				assertNotEquals("null", c.name);
				assertTrue(c.quantity > 0);
			}
		}
		assertEquals(fixture.enums.get(CatalogueLoader.SET_LOOKUP).keySet(), boxes);
		assertEquals(423, components.size());
	}

	@Test
	public void duplicateDisplayEntriesHaveOneIdentity()
	{
		CacheFixture f = new CacheFixture();
		assertEquals(1, f.recipes.stream().filter(r -> r.id == 31166).count());
		assertEquals(1, f.recipes.stream().filter(r -> r.id == 31169).count());
	}

	@Test(expected = IllegalStateException.class)
	public void rejectsMissingRecipeRatherThanSilentlyClaimingCompleteness()
	{
		CacheFixture f = new CacheFixture();
		f.enums.get(CatalogueLoader.SET_LIST).remove(0);
		CatalogueLoader.load(f.enums::get, f.items::get);
	}

	@Test(expected = IllegalStateException.class)
	public void rejectsUnavailableRecipeData()
	{
		CacheFixture f = new CacheFixture();
		f.enums.remove(1004);
		CatalogueLoader.load(f.enums::get, f.items::get);
	}

	@Test(expected = IllegalStateException.class)
	public void rejectsUnavailableComponentData()
	{
		CacheFixture f = new CacheFixture();
		f.items.remove(6);
		CatalogueLoader.load(f.enums::get, f.items::get);
	}

	@Test(expected = IllegalStateException.class)
	public void rejectsChangedRecipeSchema()
	{
		CacheFixture f = new CacheFixture();
		f.enums.get(1004).put(-2, 123);
		CatalogueLoader.load(f.enums::get, f.items::get);
	}

	@Test
	public void repeatedComponentIdsBecomeRequiredQuantities()
	{
		CacheFixture f = new CacheFixture();
		Map<Integer, Integer> recipe = new HashMap<>(f.enums.get(1004));
		recipe.put(4, recipe.get(0));
		f.enums.put(1004, recipe);
		SetRecipe cannon = CatalogueLoader.load(f.enums::get, f.items::get).get(0);
		assertEquals(2, cannon.components.get(0).quantity);
	}

	@Test
	public void newValidGameRecipeIsDiscoveredWithoutCodeUpdate()
	{
		CacheFixture f = new CacheFixture();
		f.enums.get(CatalogueLoader.SET_LIST).put(131, 999999);
		f.enums.get(CatalogueLoader.SET_LOOKUP).put(999998, 999999);
		Map<Integer, Integer> values = new HashMap<>();
		values.put(-1, 999998);
		values.put(0, 1127);
		f.enums.put(999999, values);
		f.items.put(999998, new CatalogueLoader.ItemDetails(999998, -1, "Test-only future set", false, false));
		assertEquals(130, CatalogueLoader.load(f.enums::get, f.items::get).size());
	}
}
