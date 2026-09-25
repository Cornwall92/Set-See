package com.jamescornwell.setsee;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.List;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.Point;
import net.runelite.api.gameval.InterfaceID;
import net.runelite.api.widgets.Widget;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.tooltip.Tooltip;
import net.runelite.client.ui.overlay.tooltip.TooltipManager;

final class SetSeeOverlay extends Overlay
{
	private static final BasicStroke SOLID = new BasicStroke(2);
	private static final BasicStroke DASHED = new BasicStroke(2, BasicStroke.CAP_BUTT,
		BasicStroke.JOIN_MITER, 10, new float[]{3, 3}, 0);
	private static final Font LABEL_FONT = new Font(Font.MONOSPACED, Font.BOLD, 9);
	private final Client client;
	private final SetSeeConfig config;
	private final SetSeeState state;
	private final TooltipManager tooltips;

	@Inject
	SetSeeOverlay(Client client, SetSeeConfig config, SetSeeState state, TooltipManager tooltips)
	{
		this.client = client;
		this.config = config;
		this.state = state;
		this.tooltips = tooltips;
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_WIDGETS);
		setPriority(PRIORITY_HIGH);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		if (!SetSeePlugin.windowOpen(client))
		{
			return null;
		}
		SetSeeState.Snapshot snapshot = state.snapshot;
		if (snapshot.boxes.isEmpty())
		{
			return null;
		}
		// Use live widget bounds rather than fixed positions. This follows scrolling,
		// the native craftable filter, stretched mode and movable-interface plugins.
		renderContainer(graphics, client.getWidget(InterfaceID.Itemsets.ITEMLIST), true, snapshot);
		renderContainer(graphics, client.getWidget(InterfaceID.ItemsetsSide.ITEMS), false, snapshot);
		return null;
	}

	private void renderContainer(Graphics2D graphics, Widget container, boolean setWindow, SetSeeState.Snapshot snapshot)
	{
		if (container == null || container.isHidden())
		{
			return;
		}
		Widget[] children = container.getDynamicChildren();
		if (children == null)
		{
			return;
		}
		Rectangle viewport = container.getBounds();
		Point mouse = client.getMouseCanvasPosition();
		for (Widget child : children)
		{
			if (child == null || child.isHidden() || child.getItemId() <= 0)
			{
				continue;
			}
			Rectangle bounds = child.getBounds();
			Rectangle visible = bounds.intersection(viewport);
			if (visible.isEmpty())
			{
				continue;
			}
			int id = child.getItemId();
			List<SetHighlights.Mark> marks = SetHighlights.forItem(snapshot, id, setWindow, config.showIncomplete());
			if (!marks.isEmpty())
			{
				Graphics2D g = (Graphics2D) graphics.create();
				try
				{
					g.clip(viewport);
					drawMarks(g, bounds, marks, config.showLabels());
				}
				finally
				{
					g.dispose();
				}
			}
			if (config.showTooltips() && !client.isMenuOpen() && visible.contains(mouse.getX(), mouse.getY()))
			{
				String text = SetTooltip.forItem(snapshot, id);
				if (!text.isEmpty())
				{
					tooltips.add(new Tooltip(text));
				}
			}
		}
	}

	static void drawMarks(Graphics2D g, Rectangle bounds, List<SetHighlights.Mark> marks, boolean labels)
	{
		if (marks.size() == 1)
		{
			SetHighlights.Mark mark = marks.get(0);
			Color colour = mark.colour();
			g.setColor(new Color(colour.getRed(), colour.getGreen(), colour.getBlue(), mark.incomplete ? 25 : 35));
			g.fillRect(bounds.x + 1, bounds.y + 1, bounds.width - 2, bounds.height - 2);
			g.setColor(colour);
			g.setStroke(mark.incomplete ? DASHED : SOLID);
			g.drawRect(bounds.x + 1, bounds.y + 1, bounds.width - 3, bounds.height - 3);
		}
		// Shared components get separate coloured chips instead of overwriting one border.
		int shown = Math.min(marks.size(), 2);
		int chipWidth = Math.min(22, (bounds.width - 2) / shown);
		g.setFont(LABEL_FONT);
		for (int i = 0; i < shown; i++)
		{
			SetHighlights.Mark mark = marks.get(i);
			int x = bounds.x + 1 + i * chipWidth;
			int y = bounds.y + bounds.height - (labels ? 11 : 4);
			g.setColor(mark.colour());
			g.fillRect(x, y, chipWidth - 1, labels ? 10 : 3);
			if (labels)
			{
				String label = i == shown - 1 && marks.size() > shown ? "+" + (marks.size() - shown + 1)
					: String.valueOf(mark.recipe.label);
				g.setColor(Color.BLACK);
				g.drawString(label, x + 1, y + 8);
			}
		}
	}
}
