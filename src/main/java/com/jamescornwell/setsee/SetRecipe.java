package com.jamescornwell.setsee;

import java.util.Collections;
import java.util.List;

/** One recipe from the same cache enums used by the GE Sets interface. */
final class SetRecipe
{
	final int id;
	final int noteId;
	final String name;
	final int label;
	final boolean members;
	final List<Component> components;

	SetRecipe(int id, int noteId, String name, int label, boolean members, List<Component> components)
	{
		this.id = id;
		this.noteId = noteId;
		this.name = name;
		this.label = label;
		this.members = members;
		this.components = Collections.unmodifiableList(components);
	}

	static final class Component
	{
		final int id;
		final int noteId;
		final String name;
		final int quantity;
		final boolean stackable;

		Component(int id, int noteId, String name, int quantity, boolean stackable)
		{
			this.id = id;
			this.noteId = noteId;
			this.name = name;
			this.quantity = quantity;
			this.stackable = stackable;
		}

		int notedOrSelf()
		{
			return noteId > 0 ? noteId : id;
		}
	}
}
