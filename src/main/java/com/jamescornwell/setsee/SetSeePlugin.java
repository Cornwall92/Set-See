package com.jamescornwell.setsee;

import com.google.inject.Provides;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.EnumComposition;
import net.runelite.api.GameState;
import net.runelite.api.Item;
import net.runelite.api.ItemComposition;
import net.runelite.api.ItemContainer;
import net.runelite.api.WorldType;
import net.runelite.api.events.ClientTick;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.api.gameval.InterfaceID;
import net.runelite.api.gameval.InventoryID;
import net.runelite.api.widgets.Widget;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@PluginDescriptor(name = "Set See", description = "Match GE sets to inventory components and see missing pieces",
	tags = {"grand exchange", "sets", "pack", "unpack", "highlight"})
public class SetSeePlugin extends Plugin
{
	private static final Logger log = LoggerFactory.getLogger(SetSeePlugin.class);
	@Inject Client client;
	@Inject ClientThread clientThread;
	@Inject OverlayManager overlayManager;
	@Inject SetSeeOverlay overlay;
	@Inject SetSeeState state;
	private volatile Object session;
	private boolean dirty = true;
	private boolean wasOpen;
	private List<SetRecipe> catalogue;
	private boolean catalogueFailed;

	@Provides
	SetSeeConfig provideConfig(ConfigManager manager)
	{
		return manager.getConfig(SetSeeConfig.class);
	}

	@Override
	protected void startUp()
	{
		Object current = new Object();
		session = current;
		overlayManager.add(overlay);
		clientThread.invokeLater(() ->
		{
			if (session == current)
			{
				reset();
				refresh();
			}
		});
	}

	@Override
	protected void shutDown()
	{
		session = null;
		overlayManager.remove(overlay);
		state.snapshot = SetSeeState.Snapshot.EMPTY;
	}

	@Subscribe
	public void onClientTick(ClientTick event)
	{
		if (session != null)
		{
			refresh();
		}
	}

	@Subscribe
	public void onItemContainerChanged(ItemContainerChanged event)
	{
		if (event.getContainerId() == InventoryID.INV)
		{
			dirty = true;
		}
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged event)
	{
		if (event.getGameState() != GameState.LOGGED_IN)
		{
			reset();
		}
	}

	static boolean windowOpen(Client client)
	{
		if (client.getGameState() != GameState.LOGGED_IN)
		{
			return false;
		}
		Widget list = client.getWidget(InterfaceID.Itemsets.ITEMLIST);
		return list != null && !list.isHidden();
	}

	private void reset()
	{
		catalogue = null;
		catalogueFailed = false;
		wasOpen = false;
		dirty = true;
		state.snapshot = SetSeeState.Snapshot.EMPTY;
	}

	private void refresh()
	{
		Object current = session;
		if (current == null)
		{
			return;
		}
		boolean open = windowOpen(client);
		if (!open)
		{
			if (wasOpen)
			{
				state.snapshot = SetSeeState.Snapshot.EMPTY;
				catalogueFailed = false;
			}
			wasOpen = false;
			return;
		}
		if (wasOpen && !dirty)
		{
			return;
		}
		wasOpen = true;
		dirty = false;
		if (catalogueFailed)
		{
			return;
		}
		try
		{
			if (catalogue == null)
			{
				catalogue = CatalogueLoader.load(this::readEnum, id ->
				{
					ItemComposition item = client.getItemDefinition(id);
					if (item == null)
					{
						throw new IllegalStateException("Missing set item " + id);
					}
					return new CatalogueLoader.ItemDetails(id, item.getLinkedNoteId(), item.getName(),
						item.isStackable(), item.isMembers());
				});
			}
			ItemContainer inventory = client.getItemContainer(InventoryID.INV);
			if (inventory == null)
			{
				state.snapshot = SetSeeState.Snapshot.EMPTY;
				dirty = true;
				return;
			}
			Map<Integer, Long> counts = new HashMap<>();
			int occupied = 0;
			for (Item item : inventory.getItems())
			{
				if (item.getId() > 0 && item.getQuantity() > 0)
				{
					counts.merge(item.getId(), (long) item.getQuantity(), Long::sum);
					occupied++;
				}
			}
			Map<Integer, Long> snapshotCounts = Collections.unmodifiableMap(counts);
			List<SetMatcher.Status> statuses = new ArrayList<>();
			boolean members = client.getWorldType().contains(WorldType.MEMBERS);
			for (SetRecipe recipe : catalogue)
			{
				statuses.add(SetMatcher.match(recipe, snapshotCounts, Math.max(0, 28 - occupied), members));
			}
			if (session == current)
			{
				state.snapshot = new SetSeeState.Snapshot(statuses);
			}
		}
		catch (IllegalStateException ex)
		{
			state.snapshot = SetSeeState.Snapshot.EMPTY;
			catalogueFailed = true;
			log.debug("Set See could not validate the game's GE set catalogue", ex);
		}
	}

	private Map<Integer, Integer> readEnum(int id)
	{
		EnumComposition definition = client.getEnum(id);
		if (definition == null || definition.getKeys() == null || definition.getIntVals() == null)
		{
			throw new IllegalStateException("Missing set enum " + id);
		}
		int[] keys = definition.getKeys();
		int[] values = definition.getIntVals();
		if (keys.length != values.length)
		{
			throw new IllegalStateException("Invalid set enum " + id);
		}
		Map<Integer, Integer> result = new HashMap<>();
		for (int i = 0; i < keys.length; i++)
		{
			result.put(keys[i], values[i]);
		}
		return result;
	}
}
