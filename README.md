# Set See

![Set See rune platebody icon](icon.png)

**Author: Corndwell**

Set See helps you pack and unpack Grand Exchange item sets. While the game's **Sets** window is open, matching colours and numbered labels connect each set to its loose components and packed boxes in your inventory. Hover over an item to see what you have, what is missing and how many sets you can pack.

## Contents

- [Getting started](#getting-started)
- [Reading the highlights](#reading-the-highlights)
- [Packing a set](#packing-a-set)
- [Unpacking a set](#unpacking-a-set)
- [Hover information](#hover-information)
- [Settings](#settings)
- [Supported sets](#supported-sets)
- [Troubleshooting](#troubleshooting)
- [Help and credits](#help-and-credits)

## Getting started

1. Bring set components or packed set boxes in your **inventory** to a Grand Exchange clerk.
2. Open the clerk's **Sets** interface. Use **Show all sets** if you want to inspect recipes that you cannot currently pack.
3. Match the coloured number on an inventory item to the same number in the set list, or hover over either item for details.

Set See only displays its highlights and tooltips while the Sets interface is open. Banked and equipped items do not count towards a set.

## Reading the highlights

| Display | Meaning |
| --- | --- |
| Matching colour and number | The set entry, its usable loose components and any owned packed boxes belong to the same recipe. |
| Solid coloured border | You can pack the highlighted loose components, or you own the highlighted packed box. Hover to distinguish the two. |
| Red dashed border | You own part of an incomplete loose set. Requires **Show incomplete sets**. |
| Two coloured number chips on an item | That item is shared by more than one recipe, such as the platelegs and plateskirt versions of rune armour. |
| A `+` count | More matching recipes exist than the item has room to display. Hover for the complete list. |

Colours repeat across the catalogue, so leave **Show matching labels** enabled to distinguish sets that share a colour. Red is reserved for incomplete sets.

A packed box and leftover loose pieces have separate statuses. The box can remain highlighted in its set colour while incomplete loose pieces appear red.

## Packing a set

1. Put every required component in your inventory.
2. Open **Sets** and hover over the set you want. **Can pack** shows how many complete copies you have.
3. Use the game's normal set exchange option to pack it.
4. The highlights and quantities update when your inventory changes.

For example, a rune full helm, rune platebody, rune platelegs and rune kiteshield make **Rune armour set (lg)**. Add a rune plateskirt and both armour recipes become available. They share the helm, body and shield: packing one uses those pieces and updates the other recipe's status.

### Noted items and exact variants

A packable recipe needs all its noteable components in the same form: **all unnoted or all noted**. A mixture does not make one packable set. Non-noteable stackable components, such as god-book pages, are counted normally.

Use the exact items listed by the recipe. Degraded, charged, trimmed or ornamented alternatives are not automatically interchangeable. A members-only recipe is not shown as packable on a free world.

## Unpacking a set

1. Put the packed set box in your inventory and open **Sets**.
2. Hover over the box to see the number of boxes you own and your free inventory slots.
3. If more space may be needed, the tooltip estimates the extra slots required for unnoted or noted contents.
4. Use the game's normal inventory exchange option to unpack the box.

Space figures are estimates for **one box**. They account for the space freed by consuming that box and existing stacks. The game determines whether the exchange is accepted and which output forms are available; Set See does not choose a form or perform the exchange for you.

## Hover information

With **Show component tooltips** enabled, hover over a visible set entry, component or box to see:

- The recipe name and matching number.
- The number of complete sets you can pack, including noted copies.
- How many different component types you own.
- Missing items and quantities for the closest noted or unnoted recipe.
- Packed-box quantities and, when needed, unpacking space estimates.
- Other recipes that share its components.
- A members-world requirement where applicable.

Missing-piece tooltips work even when incomplete highlights are disabled. Close any right-click menu before hovering to read a tooltip.

## Settings

Open the settings for **Set See** in RuneLite's plugin list.

| Setting | Default | What it does |
| --- | --- | --- |
| **Show incomplete sets** | Off | Adds red dashed highlights to partial loose sets and their owned components. |
| **Show matching labels** | On | Adds matching recipe numbers so you can identify sets without relying on colour alone. |
| **Show component tooltips** | On | Shows missing pieces, quantities, shared recipes and unpacking space estimates when you hover. |

## Supported sets

Set See reads the set catalogue supplied by the game, including armour, god-book page, potion and other GE-exchangeable sets. New recipes are picked up automatically when the game's catalogue format remains compatible; there is no manually maintained list of supported item IDs in the plugin.

The latest catalogue review on **21 November 2026** confirmed **129 unique GE sets**, with no missing or newly added recipes. The [set catalogue](docs/CATALOGUE.md) lists the audited recipes and source details. The [item reference](docs/set-catalogue.csv) includes their exact components and quantities. These references describe that audit; the live game supplies the recipes used while you play.

## Troubleshooting

| Problem | What to check |
| --- | --- |
| No highlights appear | Enable Set See, open the GE **Sets** interface and put matching components or boxes in your inventory. With the defaults, incomplete loose sets are not highlighted. |
| An incomplete set is missing from the list | Switch the game's filter from **Show craftable sets** to **Show all sets**. |
| I own every piece but cannot pack the set | Check the exact item variants, required quantities, noted/unnoted forms and whether the recipe needs a members world. Hover for the missing-piece explanation. |
| An item belongs to two sets | Read its separate number chips or hover for the shared recipes. Each recipe is evaluated separately; shared pieces cannot be spent twice. |
| My box is highlighted, but the loose pieces are not | Owning a box does not complete your leftover loose recipe. Enable incomplete highlights to see partial loose sets. |
| Tooltips do not appear | Enable **Show component tooltips**, close the right-click menu and hover over a visible item in the Sets interface or its inventory panel. |
| Highlights stop after a game update | Update/restart RuneLite and reopen Sets. If the game's catalogue cannot be validated, Set See hides its highlights instead of showing a partial result. Report the problem if it persists. |
| Highlights disappear when I leave the interface | This is expected. Set See clears its display when you close Sets, log out or disable the plugin. |

## Help and credits

Report problems or suggest improvements on the [Set See issue tracker](https://github.com/Cornwall92/Set-See/issues). For a missing or incorrect set, include its name, the exact components and note forms in your inventory, your world type and a screenshot of the Sets interface if possible.

Set See is maintained by **Corndwell** and distributed under the [BSD 2-Clause license](LICENSE). Project structure and the Gradle wrapper originate from [RuneLite's example plugin](https://github.com/runelite/example-plugin); upstream notices are retained. RuneLite and Old School RuneScape belong to their respective owners. The rune platebody icon is an original illustration; see the [artwork notes](artwork/README.md).
