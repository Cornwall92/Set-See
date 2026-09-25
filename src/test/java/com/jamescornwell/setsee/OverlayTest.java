package com.jamescornwell.setsee;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Collections;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.Point;
import net.runelite.api.gameval.InterfaceID;
import net.runelite.api.widgets.Widget;
import net.runelite.client.ui.overlay.tooltip.Tooltip;
import net.runelite.client.ui.overlay.tooltip.TooltipManager;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class OverlayTest
{
	private final Client client = mock(Client.class);
	private final Widget container = mock(Widget.class);
	private final Widget child = mock(Widget.class);
	private final SetSeeConfig config = mock(SetSeeConfig.class);
	private final TooltipManager tooltips = mock(TooltipManager.class);
	private final SetSeeState state = new SetSeeState();
	private SetSeeOverlay overlay;

	@Before
	public void setup()
	{
		SetRecipe recipe = new CacheFixture().recipe(13024);
		state.snapshot = new SetSeeState.Snapshot(Collections.singletonList(SetMatcher.match(recipe,
			Collections.singletonMap(recipe.id, 1L), 27, true)));
		when(client.getGameState()).thenReturn(GameState.LOGGED_IN);
		when(client.getWidget(InterfaceID.Itemsets.ITEMLIST)).thenReturn(container);
		when(client.getMouseCanvasPosition()).thenReturn(new Point(-1, -1));
		when(container.getBounds()).thenReturn(new Rectangle(20, 20, 80, 50));
		when(container.getDynamicChildren()).thenReturn(new Widget[]{child});
		when(child.getItemId()).thenReturn(recipe.id);
		when(child.getBounds()).thenReturn(new Rectangle(25, 10, 36, 32));
		when(config.showLabels()).thenReturn(true);
		when(config.showTooltips()).thenReturn(true);
		overlay = new SetSeeOverlay(client, config, state, tooltips);
	}

	private BufferedImage render()
	{
		BufferedImage image = new BufferedImage(150, 100, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		try
		{
			overlay.render(g);
		}
		finally
		{
			g.dispose();
		}
		return image;
	}

	@Test
	public void partiallyScrolledItemIsClippedToViewport()
	{
		BufferedImage image = render();
		assertEquals(0, image.getRGB(26, 11));
		assertNotEquals(0, image.getRGB(26, 25));
	}

	@Test
	public void movedWidgetUsesItsNewBounds()
	{
		when(child.getBounds()).thenReturn(new Rectangle(50, 35, 36, 32));
		BufferedImage image = render();
		assertEquals(0, image.getRGB(26, 25));
		assertNotEquals(0, image.getRGB(51, 40));
	}

	@Test
	public void nativeFilterHiddenItemsAreNotDrawn()
	{
		when(child.isHidden()).thenReturn(true);
		assertEquals(0, render().getRGB(26, 25));
	}

	@Test
	public void closedWindowDoesNotDrawEvenIfSnapshotStillExists()
	{
		when(container.isHidden()).thenReturn(true);
		assertEquals(0, render().getRGB(26, 25));
	}

	@Test
	public void tooltipOnlyTriggersInsideVisiblePartOfItem()
	{
		when(client.getMouseCanvasPosition()).thenReturn(new Point(30, 12));
		render();
		verify(tooltips, never()).add(any(Tooltip.class));
		when(client.getMouseCanvasPosition()).thenReturn(new Point(30, 25));
		render();
		verify(tooltips).add(any(Tooltip.class));
	}

	@Test
	public void contextMenuSuppressesTooltips()
	{
		when(client.getMouseCanvasPosition()).thenReturn(new Point(30, 25));
		when(client.isMenuOpen()).thenReturn(true);
		render();
		verify(tooltips, never()).add(any(Tooltip.class));
	}
}
