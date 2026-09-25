# Set See

![Set See rune platebody icon](icon.png)

**Author: James C · Version: 1.0.0**

Set See connects the Grand Exchange Sets list to the items in your inventory. Open the GE **Sets** window to see matching colours and numbered labels around packable sets, their components, and packed boxes you own.

## Behaviour

- A complete loose set and its entry in the Sets window share a colour and number. Packed boxes in your inventory use the same group identity.
- Components shared by alternative sets show separate coloured number chips. Each recipe is evaluated independently; packing one immediately updates the others.
- Hover over a set or component for missing pieces, required quantities, the number of sets you can pack, packed-box quantities, and shared-component information.
- Optional incomplete highlights use **red only**, with dashed borders. They are off by default. Missing-piece tooltips remain available while they are off.
- Owning a packed box and some incomplete loose pieces produces separate statuses: the box stays in its group colour, and the loose pieces can be red.
- Noted items are supported. The game's current packing check requires a complete unnoted recipe or a complete noted recipe; it does not combine forms into one packable set. Stackable, non-noteable book pages are handled once.
- Exact item IDs matter. Degraded, charged, trimmed, and ornamented variants are not silently substituted for a recipe's required item.
- Members-only recipes are not reported as packable on free worlds.
- Only inventory items count. Closing the Sets window, logging out, or disabling the plugin clears the overlay.
- The game's **Show craftable sets** filter is respected. Select **Show all sets** to inspect incomplete entries hidden by that filter.

Colours come from a non-red palette and repeat across the catalogue; the matching number distinguishes groups with the same colour. Shared items display up to two chips, with an overflow count if future recipes add more matches. Hover for the full list.

Unpacking tooltips show available slots and space **estimates** for unnoted and noted contents, accounting for the consumed box and existing stacks. These are estimates, not a guarantee that the server will accept an unpack action. The plugin does not choose output forms or perform exchanges.

## Settings

| Setting | Default | Purpose |
| --- | --- | --- |
| Show incomplete sets | Off | Red highlights for partial loose sets |
| Show matching labels | On | Numbered group labels alongside colours |
| Show component tooltips | On | Missing pieces, quantities, alternatives and space estimates |

## Complete set catalogue

The audited game cache contains **129 unique GE sets and 423 distinct component item IDs**. Its display list has 131 entries because two recipes appear twice. See the [complete catalogue](docs/CATALOGUE.md) or [item-level CSV](docs/set-catalogue.csv), including noted IDs and required quantities.

At runtime, Set See reads the same cache enums as the game's GE Sets interface. It cross-checks the entire display list against the reverse box-to-recipe lookup before enabling highlights. New sets are discovered automatically while that schema remains compatible. Invalid or unavailable definitions disable highlights until the window is reopened; they are never treated as a successful partial catalogue.

The included test fixture is from the Jagex cache dated **23 September 2026**, build 240. This snapshot is test data only and is not bundled into the plugin JAR. See [catalogue provenance](docs/CATALOGUE.md#provenance).

## Build and try it

Use a full **JDK 17** to run Gradle. Plugin source targets Java 11, as required by RuneLite.

```sh
./gradlew clean build
./gradlew run
```

On Windows, use `gradlew.bat clean build` and `gradlew.bat run`. The run task launches the RuneLite development client with Set See loaded. Enable **Set See** in its plugin settings if necessary.

For a Jagex account, follow RuneLite's [Using Jagex Accounts](https://github.com/runelite/runelite/wiki/Using-Jagex-Accounts) instructions for logging into a development client.

This source release is **not yet published to Plugin Hub**. A successful build does not install it into your normal RuneLite client. Hub publication requires a public source repository, a manifest pointing to its reviewed commit, and acceptance by the RuneLite maintainers.

## Validation

See [TESTING.md](docs/TESTING.md) for the automated results and the short manual in-game checklist. In-game rendering and server exchange behaviour still require a human check before release.

The plugin draws overlays and reads game state. It does not inject input, exchange items automatically, alter click targets, perform runtime network requests, or store player data.

## Credits and licensing

Plugin code: James C, BSD 2-Clause license. Gradle wrapper and project structure originate from [runelite/example-plugin](https://github.com/runelite/example-plugin); the wrapper is distributed under its upstream terms. RuneLite and Old School RuneScape are their respective owners' projects. The generated rune-style icon is an original illustration; see [artwork notes](artwork/README.md).
