package com.jamescornwell.setsee;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class CacheFixture
{
	final Map<Integer, Map<Integer, Integer>> enums = new HashMap<>();
	final Map<Integer, CatalogueLoader.ItemDetails> items = new HashMap<>();
	final List<SetRecipe> recipes;

	CacheFixture()
	{
		try (InputStreamReader reader = new InputStreamReader(
			CacheFixture.class.getResourceAsStream("/catalogue-snapshot.json"), StandardCharsets.UTF_8))
		{
			JsonObject data = new Gson().fromJson(reader, JsonObject.class);
			for (Map.Entry<String, JsonElement> entry : data.getAsJsonObject("enums").entrySet())
			{
				Map<Integer, Integer> values = new HashMap<>();
				for (Map.Entry<String, JsonElement> value : entry.getValue().getAsJsonObject().entrySet())
				{
					values.put(Integer.parseInt(value.getKey()), value.getValue().getAsInt());
				}
				enums.put(Integer.parseInt(entry.getKey()), values);
			}
			for (JsonElement element : data.getAsJsonArray("items"))
			{
				JsonObject item = element.getAsJsonObject();
				int id = item.get("id").getAsInt();
				items.put(id, new CatalogueLoader.ItemDetails(id, item.get("note").getAsInt(),
					item.get("name").getAsString(), item.get("stackable").getAsBoolean(), item.get("members").getAsBoolean()));
			}
		}
		catch (Exception ex)
		{
			throw new AssertionError("Cannot load the audited cache fixture", ex);
		}
		recipes = CatalogueLoader.load(enums::get, items::get);
	}

	SetRecipe recipe(int id)
	{
		return recipes.stream().filter(r -> r.id == id).findFirst().orElseThrow(AssertionError::new);
	}

	static Map<Integer, Long> components(SetRecipe recipe, boolean noted, long copies)
	{
		Map<Integer, Long> values = new HashMap<>();
		for (SetRecipe.Component c : recipe.components)
		{
			values.put(noted ? c.notedOrSelf() : c.id, copies * c.quantity);
		}
		return values;
	}
}
