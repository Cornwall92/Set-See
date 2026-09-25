package com.jamescornwell.setsee;

import java.util.ArrayDeque;
import java.util.EnumSet;
import java.util.Map;
import java.util.Queue;
import javax.swing.SwingUtilities;
import net.runelite.api.Client;
import net.runelite.api.EnumComposition;
import net.runelite.api.GameState;
import net.runelite.api.Item;
import net.runelite.api.ItemComposition;
import net.runelite.api.ItemContainer;
import net.runelite.api.WorldType;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.api.gameval.InterfaceID;
import net.runelite.api.gameval.InventoryID;
import net.runelite.api.widgets.Widget;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.ui.overlay.tooltip.TooltipManager;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class LifecycleTest
{
	private final CacheFixture f = new CacheFixture();
	private final Queue<Runnable> queued = new ArrayDeque<>();
	private final Client client = mock(Client.class);
	private final Widget list = mock(Widget.class);
	private final ItemContainer inventory = mock(ItemContainer.class);
	private SetSeePlugin plugin;
	private boolean onClientThread;

	@Before
	public void setup()
	{
		plugin = new SetSeePlugin();
		plugin.client = client;
		plugin.clientThread = mock(ClientThread.class);
		plugin.overlayManager = mock(OverlayManager.class);
		plugin.state = new SetSeeState();
		plugin.overlay = new SetSeeOverlay(client, mock(SetSeeConfig.class),
			plugin.state, mock(TooltipManager.class));
		doAnswer(invocation -> { queued.add(invocation.getArgument(0)); return null; })
			.when(plugin.clientThread).invokeLater(any(Runnable.class));
		when(client.getGameState()).thenReturn(GameState.LOGGED_IN);
		when(client.getWidget(InterfaceID.Itemsets.ITEMLIST)).thenReturn(list);
		when(client.getWorldType()).thenReturn(EnumSet.of(WorldType.MEMBERS));
		when(client.getItemContainer(InventoryID.INV)).thenReturn(inventory);
		when(inventory.getItems()).thenReturn(new Item[]{new Item(13024, 1)});
		when(client.getEnum(anyInt())).thenAnswer(invocation ->
		{
			assertTrue("Game data was accessed off the client thread", onClientThread);
			Map<Integer, Integer> data = f.enums.get(invocation.<Integer>getArgument(0));
			EnumComposition definition = mock(EnumComposition.class);
			int[] keys = data.keySet().stream().mapToInt(Integer::intValue).toArray();
			when(definition.getKeys()).thenReturn(keys);
			when(definition.getIntVals()).thenReturn(java.util.Arrays.stream(keys).map(data::get).toArray());
			return definition;
		});
		when(client.getItemDefinition(anyInt())).thenAnswer(invocation ->
		{
			CatalogueLoader.ItemDetails item = f.items.get(invocation.<Integer>getArgument(0));
			ItemComposition definition = mock(ItemComposition.class);
			when(definition.getName()).thenReturn(item.name);
			when(definition.getLinkedNoteId()).thenReturn(item.noteId);
			when(definition.isMembers()).thenReturn(item.members);
			when(definition.isStackable()).thenReturn(item.stackable);
			return definition;
		});
	}

	private void drain()
	{
		onClientThread = true;
		while (!queued.isEmpty())
		{
			queued.remove().run();
		}
	}

	@Test
	public void enablingFromSwingWaitsForClientThread() throws Exception
	{
		SwingUtilities.invokeAndWait(() -> plugin.startUp());
		verify(client, never()).getEnum(anyInt());
		drain();
		assertEquals(1, plugin.state.snapshot.boxes.get(13024).boxes);
	}

	@Test
	public void queuedStartupCannotRestoreStateAfterDisable() throws Exception
	{
		SwingUtilities.invokeAndWait(() -> plugin.startUp());
		SwingUtilities.invokeAndWait(() -> plugin.shutDown());
		drain();
		assertTrue(plugin.state.snapshot.boxes.isEmpty());
		verify(client, never()).getEnum(anyInt());
	}

	@Test
	public void quickDisableReenableIgnoresFirstSession() throws Exception
	{
		SwingUtilities.invokeAndWait(() -> plugin.startUp());
		SwingUtilities.invokeAndWait(() -> plugin.shutDown());
		SwingUtilities.invokeAndWait(() -> plugin.startUp());
		drain();
		assertEquals(1, plugin.state.snapshot.boxes.get(13024).boxes);
		verify(client, times(1)).getEnum(CatalogueLoader.SET_LIST);
	}

	@Test
	public void closingTheWindowImmediatelyClearsState()
	{
		plugin.startUp();
		drain();
		when(list.isHidden()).thenReturn(true);
		plugin.onClientTick(null);
		assertTrue(plugin.state.snapshot.boxes.isEmpty());
	}

	@Test
	public void inventoryChangeUpdatesWithoutWaitingForGameTick()
	{
		plugin.startUp();
		drain();
		when(inventory.getItems()).thenReturn(new Item[]{new Item(1127, 1)});
		ItemContainerChanged event = new ItemContainerChanged(InventoryID.INV, inventory);
		plugin.onItemContainerChanged(event);
		plugin.onClientTick(null);
		assertEquals(0, plugin.state.snapshot.boxes.get(13024).boxes);
		assertTrue(plugin.state.snapshot.boxes.get(13024).incomplete());
	}

	@Test
	public void missingInventoryIsRetriedWithoutCrash()
	{
		when(client.getItemContainer(InventoryID.INV)).thenReturn(null);
		plugin.startUp();
		drain();
		assertTrue(plugin.state.snapshot.boxes.isEmpty());
		when(client.getItemContainer(InventoryID.INV)).thenReturn(inventory);
		plugin.onClientTick(null);
		assertEquals(1, plugin.state.snapshot.boxes.get(13024).boxes);
	}

	@Test
	public void loadingClearsStateAndLoginCanRefreshAgain()
	{
		plugin.startUp();
		drain();
		GameStateChanged event = mock(GameStateChanged.class);
		when(event.getGameState()).thenReturn(GameState.LOADING);
		when(client.getGameState()).thenReturn(GameState.LOADING);
		plugin.onGameStateChanged(event);
		assertTrue(plugin.state.snapshot.boxes.isEmpty());
		when(client.getGameState()).thenReturn(GameState.LOGGED_IN);
		plugin.onClientTick(null);
		assertEquals(1, plugin.state.snapshot.boxes.get(13024).boxes);
	}

	@Test
	public void corruptCatalogueFailsClosedInsteadOfPartialHighlights()
	{
		f.enums.get(CatalogueLoader.SET_LOOKUP).remove(13024);
		plugin.startUp();
		drain();
		assertTrue(plugin.state.snapshot.boxes.isEmpty());
		plugin.onClientTick(null);
		assertTrue(plugin.state.snapshot.boxes.isEmpty());
	}
}
