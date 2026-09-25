# Complete GE set catalogue

Audited on 25 September 2026 against the Jagex Old School RuneScape live cache dated 23 September 2026 (build 240).

**129 unique sets · 423 distinct unnoted component IDs · 131 display entries.** The display list repeats Mixed hide armour set and Hueycoatl hide armour set. Each is counted once below.

“Component IDs” means the exact required items; noted variants are recorded separately in [set-catalogue.csv](set-catalogue.csv). That CSV includes every recipe, component, quantity, membership flag and normal/noted box ID. A negative noted ID means the item has no noted variant.

## Provenance

- [Jagex cache 2720 archived by OpenRS2](https://archive.openrs2.org/caches/runescape/2720). Metadata identifies the source as Jagex, language English, environment live.
- [Config index](https://archive.openrs2.org/caches/runescape/2720/archives/255/groups/2.dat), [enum group](https://archive.openrs2.org/caches/runescape/2720/archives/2/groups/8.dat), and [item group](https://archive.openrs2.org/caches/runescape/2720/archives/2/groups/10.dat). The archive serves JS5 containers, which were decoded to extract the enum and item definitions.
- [RuneLite decompiled game scripts, audited revision](https://github.com/runelite/cs2-scripts/tree/f65e33ec680120f5f957db18e7c24b8ee69e5b3d): `script8020`, `itemsets_draw` (8023), `itemsets_init` (822), and `itemsets_side_draw` (827).

Enum 1033 contains the displayed recipe IDs. Each recipe enum maps key -1 to the packed box and keys 0 onwards to the components. Enum 1034 independently maps packed box IDs back to recipe IDs. The runtime loader requires both indexes to agree exactly. Duplicate display entries are deduplicated; repeated component IDs in a recipe become quantities.

Names, note links, stackability and membership come from item definitions. The checked-in JSON fixture preserves the raw selected enums, all 131 display entries, all 129 decoded recipes and relevant item definitions including notes. It is used only by tests. Runtime matching uses the current client cache and performs no downloads.

Source container SHA-256 checksums:

| Source | SHA-256 |
| --- | --- |
| config-index.dat | `31daf6aa74c64972bc89ab6b95791c785d3fac56e4fa509924c83ae64a5ff3e5` |
| enums.dat | `ad4df0a51f07fbcc72a72e4c9c0dbbadf7ddc48f354b88da1ac30373bcba3b98` |
| items.dat | `32ab34b4b4a547a769fbea087076519bd90fab10d85f4d5b8850fe0dfe0c55d9` |

## Sets

| # | Set | Box ID | Members | Required components |
| --- | --- | --- | --- | --- |
| 1 | Dwarf cannon set | 12863 | Yes | Cannon base ×1; Cannon stand ×1; Cannon barrels ×1; Cannon furnace ×1 |
| 2 | Green dragonhide set | 12865 | No | Green d'hide body ×1; Green d'hide chaps ×1; Green d'hide vambraces ×1 |
| 3 | Blue dragonhide set | 12867 | Yes | Blue d'hide body ×1; Blue d'hide chaps ×1; Blue d'hide vambraces ×1 |
| 4 | Red dragonhide set | 12869 | Yes | Red d'hide body ×1; Red d'hide chaps ×1; Red d'hide vambraces ×1 |
| 5 | Black dragonhide set | 12871 | Yes | Black d'hide body ×1; Black d'hide chaps ×1; Black d'hide vambraces ×1 |
| 6 | Ahrim's armour set | 12881 | Yes | Ahrim's hood ×1; Ahrim's robetop ×1; Ahrim's robeskirt ×1; Ahrim's staff ×1 |
| 7 | Dharok's armour set | 12877 | Yes | Dharok's helm ×1; Dharok's platebody ×1; Dharok's platelegs ×1; Dharok's greataxe ×1 |
| 8 | Guthan's armour set | 12873 | Yes | Guthan's helm ×1; Guthan's platebody ×1; Guthan's chainskirt ×1; Guthan's warspear ×1 |
| 9 | Karil's armour set | 12883 | Yes | Karil's coif ×1; Karil's leathertop ×1; Karil's leatherskirt ×1; Karil's crossbow ×1 |
| 10 | Torag's armour set | 12879 | Yes | Torag's helm ×1; Torag's platebody ×1; Torag's platelegs ×1; Torag's hammers ×1 |
| 11 | Verac's armour set | 12875 | Yes | Verac's helm ×1; Verac's brassard ×1; Verac's plateskirt ×1; Verac's flail ×1 |
| 12 | Combat potion set | 13064 | Yes | Attack potion(4) ×1; Defence potion(4) ×1; Strength potion(4) ×1 |
| 13 | Super potion set | 13066 | Yes | Super attack(4) ×1; Super defence(4) ×1; Super strength(4) ×1 |
| 14 | Bronze set (lg) | 12960 | No | Bronze full helm ×1; Bronze platebody ×1; Bronze platelegs ×1; Bronze kiteshield ×1 |
| 15 | Bronze set (sk) | 12962 | No | Bronze full helm ×1; Bronze platebody ×1; Bronze plateskirt ×1; Bronze kiteshield ×1 |
| 16 | Iron set (lg) | 12972 | No | Iron full helm ×1; Iron platebody ×1; Iron platelegs ×1; Iron kiteshield ×1 |
| 17 | Iron set (sk) | 12974 | No | Iron full helm ×1; Iron platebody ×1; Iron plateskirt ×1; Iron kiteshield ×1 |
| 18 | Steel set (lg) | 12984 | No | Steel full helm ×1; Steel platebody ×1; Steel platelegs ×1; Steel kiteshield ×1 |
| 19 | Steel set (sk) | 12986 | No | Steel full helm ×1; Steel platebody ×1; Steel plateskirt ×1; Steel kiteshield ×1 |
| 20 | Black set (lg) | 12988 | No | Black full helm ×1; Black platebody ×1; Black platelegs ×1; Black kiteshield ×1 |
| 21 | Black set (sk) | 12990 | No | Black full helm ×1; Black platebody ×1; Black plateskirt ×1; Black kiteshield ×1 |
| 22 | Mithril set (lg) | 13000 | No | Mithril full helm ×1; Mithril platebody ×1; Mithril platelegs ×1; Mithril kiteshield ×1 |
| 23 | Mithril set (sk) | 13002 | No | Mithril full helm ×1; Mithril platebody ×1; Mithril plateskirt ×1; Mithril kiteshield ×1 |
| 24 | Adamant set (lg) | 13012 | No | Adamant full helm ×1; Adamant platebody ×1; Adamant platelegs ×1; Adamant kiteshield ×1 |
| 25 | Adamant set (sk) | 13014 | No | Adamant full helm ×1; Adamant platebody ×1; Adamant plateskirt ×1; Adamant kiteshield ×1 |
| 26 | Rune armour set (lg) | 13024 | No | Rune full helm ×1; Rune platebody ×1; Rune platelegs ×1; Rune kiteshield ×1 |
| 27 | Rune armour set (sk) | 13026 | No | Rune full helm ×1; Rune platebody ×1; Rune plateskirt ×1; Rune kiteshield ×1 |
| 28 | Dragon armour set (lg) | 21882 | Yes | Dragon full helm ×1; Dragon platebody ×1; Dragon platelegs ×1; Dragon kiteshield ×1 |
| 29 | Dragon armour set (sk) | 21885 | Yes | Dragon full helm ×1; Dragon platebody ×1; Dragon plateskirt ×1; Dragon kiteshield ×1 |
| 30 | Bronze trimmed set (lg) | 12964 | No | Bronze full helm (t) ×1; Bronze platebody (t) ×1; Bronze platelegs (t) ×1; Bronze kiteshield (t) ×1 |
| 31 | Bronze trimmed set (sk) | 12966 | No | Bronze full helm (t) ×1; Bronze platebody (t) ×1; Bronze plateskirt (t) ×1; Bronze kiteshield (t) ×1 |
| 32 | Bronze gold-trimmed set (lg) | 12968 | No | Bronze full helm (g) ×1; Bronze platebody (g) ×1; Bronze platelegs (g) ×1; Bronze kiteshield (g) ×1 |
| 33 | Bronze gold-trimmed set (sk) | 12970 | No | Bronze full helm (g) ×1; Bronze platebody (g) ×1; Bronze plateskirt (g) ×1; Bronze kiteshield (g) ×1 |
| 34 | Iron trimmed set (lg) | 12976 | No | Iron full helm (t) ×1; Iron platebody (t) ×1; Iron platelegs (t) ×1; Iron kiteshield (t) ×1 |
| 35 | Iron trimmed set (sk) | 12978 | No | Iron full helm (t) ×1; Iron platebody (t) ×1; Iron plateskirt (t) ×1; Iron kiteshield (t) ×1 |
| 36 | Iron gold-trimmed set (lg) | 12980 | No | Iron full helm (g) ×1; Iron platebody (g) ×1; Iron platelegs (g) ×1; Iron kiteshield (g) ×1 |
| 37 | Iron gold-trimmed set (sk) | 12982 | No | Iron full helm (g) ×1; Iron platebody (g) ×1; Iron plateskirt (g) ×1; Iron kiteshield (g) ×1 |
| 38 | Steel trimmed set (lg) | 20376 | No | Steel full helm (t) ×1; Steel platebody (t) ×1; Steel platelegs (t) ×1; Steel kiteshield (t) ×1 |
| 39 | Steel trimmed set (sk) | 20379 | No | Steel full helm (t) ×1; Steel platebody (t) ×1; Steel plateskirt (t) ×1; Steel kiteshield (t) ×1 |
| 40 | Steel gold-trimmed set (lg) | 20382 | No | Steel full helm (g) ×1; Steel platebody (g) ×1; Steel platelegs (g) ×1; Steel kiteshield (g) ×1 |
| 41 | Steel gold-trimmed set (sk) | 20385 | No | Steel full helm (g) ×1; Steel platebody (g) ×1; Steel plateskirt (g) ×1; Steel kiteshield (g) ×1 |
| 42 | Black trimmed set (lg) | 12992 | No | Black full helm (t) ×1; Black platebody (t) ×1; Black platelegs (t) ×1; Black kiteshield (t) ×1 |
| 43 | Black trimmed set (sk) | 12994 | No | Black full helm (t) ×1; Black platebody (t) ×1; Black plateskirt (t) ×1; Black kiteshield (t) ×1 |
| 44 | Black gold-trimmed set (lg) | 12996 | No | Black full helm (g) ×1; Black platebody (g) ×1; Black platelegs (g) ×1; Black kiteshield (g) ×1 |
| 45 | Black gold-trimmed set (sk) | 12998 | No | Black full helm (g) ×1; Black platebody (g) ×1; Black plateskirt (g) ×1; Black kiteshield (g) ×1 |
| 46 | Mithril trimmed set (lg) | 13004 | No | Mithril full helm (t) ×1; Mithril platebody (t) ×1; Mithril platelegs (t) ×1; Mithril kiteshield (t) ×1 |
| 47 | Mithril trimmed set (sk) | 13006 | No | Mithril full helm (t) ×1; Mithril platebody (t) ×1; Mithril plateskirt (t) ×1; Mithril kiteshield (t) ×1 |
| 48 | Mithril gold-trimmed set (lg) | 13008 | No | Mithril full helm (g) ×1; Mithril platebody (g) ×1; Mithril platelegs (g) ×1; Mithril kiteshield (g) ×1 |
| 49 | Mithril gold-trimmed set (sk) | 13010 | No | Mithril full helm (g) ×1; Mithril platebody (g) ×1; Mithril plateskirt (g) ×1; Mithril kiteshield (g) ×1 |
| 50 | Adamant trimmed set (lg) | 13016 | No | Adamant full helm (t) ×1; Adamant platebody (t) ×1; Adamant platelegs (t) ×1; Adamant kiteshield (t) ×1 |
| 51 | Adamant trimmed set (sk) | 13018 | No | Adamant full helm (t) ×1; Adamant platebody (t) ×1; Adamant plateskirt (t) ×1; Adamant kiteshield (t) ×1 |
| 52 | Adamant gold-trimmed set (lg) | 13020 | No | Adamant full helm (g) ×1; Adamant platebody (g) ×1; Adamant platelegs (g) ×1; Adamant kiteshield (g) ×1 |
| 53 | Adamant gold-trimmed set (sk) | 13022 | No | Adamant full helm (g) ×1; Adamant platebody (g) ×1; Adamant plateskirt (g) ×1; Adamant kiteshield (g) ×1 |
| 54 | Rune trimmed set (lg) | 13028 | No | Rune full helm (t) ×1; Rune platebody (t) ×1; Rune platelegs (t) ×1; Rune kiteshield (t) ×1 |
| 55 | Rune trimmed set (sk) | 13030 | No | Rune full helm (t) ×1; Rune platebody (t) ×1; Rune plateskirt (t) ×1; Rune kiteshield (t) ×1 |
| 56 | Rune gold-trimmed set (lg) | 13032 | No | Rune full helm (g) ×1; Rune platebody (g) ×1; Rune platelegs (g) ×1; Rune kiteshield (g) ×1 |
| 57 | Rune gold-trimmed set (sk) | 13034 | No | Rune full helm (g) ×1; Rune platebody (g) ×1; Rune plateskirt (g) ×1; Rune kiteshield (g) ×1 |
| 58 | Saradomin armour set (lg) | 13040 | No | Saradomin full helm ×1; Saradomin platebody ×1; Saradomin platelegs ×1; Saradomin kiteshield ×1 |
| 59 | Saradomin armour set (sk) | 13042 | No | Saradomin full helm ×1; Saradomin platebody ×1; Saradomin plateskirt ×1; Saradomin kiteshield ×1 |
| 60 | Zamorak armour set (lg) | 13044 | No | Zamorak full helm ×1; Zamorak platebody ×1; Zamorak platelegs ×1; Zamorak kiteshield ×1 |
| 61 | Zamorak armour set (sk) | 13046 | No | Zamorak full helm ×1; Zamorak platebody ×1; Zamorak plateskirt ×1; Zamorak kiteshield ×1 |
| 62 | Guthix armour set (lg) | 13048 | No | Guthix full helm ×1; Guthix platebody ×1; Guthix platelegs ×1; Guthix kiteshield ×1 |
| 63 | Guthix armour set (sk) | 13050 | No | Guthix full helm ×1; Guthix platebody ×1; Guthix plateskirt ×1; Guthix kiteshield ×1 |
| 64 | Armadyl rune armour set (lg) | 13052 | No | Armadyl full helm ×1; Armadyl platebody ×1; Armadyl platelegs ×1; Armadyl kiteshield ×1 |
| 65 | Armadyl rune armour set (sk) | 13054 | No | Armadyl full helm ×1; Armadyl platebody ×1; Armadyl plateskirt ×1; Armadyl kiteshield ×1 |
| 66 | Bandos rune armour set (lg) | 13056 | No | Bandos full helm ×1; Bandos platebody ×1; Bandos platelegs ×1; Bandos kiteshield ×1 |
| 67 | Bandos rune armour set (sk) | 13058 | No | Bandos full helm ×1; Bandos platebody ×1; Bandos plateskirt ×1; Bandos kiteshield ×1 |
| 68 | Ancient rune armour set (lg) | 13060 | No | Ancient full helm ×1; Ancient platebody ×1; Ancient platelegs ×1; Ancient kiteshield ×1 |
| 69 | Ancient rune armour set (sk) | 13062 | No | Ancient full helm ×1; Ancient platebody ×1; Ancient plateskirt ×1; Ancient kiteshield ×1 |
| 70 | Gilded armour set (lg) | 13036 | No | Gilded full helm ×1; Gilded platebody ×1; Gilded platelegs ×1; Gilded kiteshield ×1 |
| 71 | Gilded armour set (sk) | 13038 | No | Gilded full helm ×1; Gilded platebody ×1; Gilded plateskirt ×1; Gilded kiteshield ×1 |
| 72 | Holy book page set | 13149 | Yes | Saradomin page 1 ×1; Saradomin page 2 ×1; Saradomin page 3 ×1; Saradomin page 4 ×1 |
| 73 | Unholy book page set | 13151 | Yes | Zamorak page 1 ×1; Zamorak page 2 ×1; Zamorak page 3 ×1; Zamorak page 4 ×1 |
| 74 | Book of Balance page set | 13153 | Yes | Guthix page 1 ×1; Guthix page 2 ×1; Guthix page 3 ×1; Guthix page 4 ×1 |
| 75 | Book of War page set | 13155 | Yes | Bandos page 1 ×1; Bandos page 2 ×1; Bandos page 3 ×1; Bandos page 4 ×1 |
| 76 | Book of Law page set | 13157 | Yes | Armadyl page 1 ×1; Armadyl page 2 ×1; Armadyl page 3 ×1; Armadyl page 4 ×1 |
| 77 | Book of Darkness page set | 13159 | Yes | Ancient page 1 ×1; Ancient page 2 ×1; Ancient page 3 ×1; Ancient page 4 ×1 |
| 78 | Mixed hide armour set | 31166 | Yes | Mixed hide top ×1; Mixed hide legs ×1; Mixed hide boots ×1; Mixed hide cape ×1 |
| 79 | Gilded dragonhide set | 23124 | No | Gilded d'hide body ×1; Gilded d'hide chaps ×1; Gilded d'hide vambraces ×1 |
| 80 | Saradomin dragonhide set | 13163 | Yes | Saradomin coif ×1; Saradomin d'hide body ×1; Saradomin chaps ×1; Saradomin bracers ×1 |
| 81 | Zamorak dragonhide set | 13161 | Yes | Zamorak coif ×1; Zamorak d'hide body ×1; Zamorak chaps ×1; Zamorak bracers ×1 |
| 82 | Guthix dragonhide set | 13165 | Yes | Guthix coif ×1; Guthix d'hide body ×1; Guthix chaps ×1; Guthix bracers ×1 |
| 83 | Bandos dragonhide set | 13167 | Yes | Bandos coif ×1; Bandos d'hide body ×1; Bandos chaps ×1; Bandos bracers ×1 |
| 84 | Armadyl dragonhide set | 13169 | Yes | Armadyl coif ×1; Armadyl d'hide body ×1; Armadyl chaps ×1; Armadyl bracers ×1 |
| 85 | Ancient dragonhide set | 13171 | Yes | Ancient coif ×1; Ancient d'hide body ×1; Ancient chaps ×1; Ancient bracers ×1 |
| 86 | Hueycoatl hide armour set | 31169 | Yes | Hueycoatl hide coif ×1; Hueycoatl hide body ×1; Hueycoatl hide chaps ×1; Hueycoatl hide vambraces ×1 |
| 87 | Partyhat set | 13173 | No | Red partyhat ×1; Yellow partyhat ×1; Blue partyhat ×1; Green partyhat ×1; Purple partyhat ×1; White partyhat ×1 |
| 88 | Halloween mask set | 13175 | No | Green halloween mask ×1; Blue halloween mask ×1; Red halloween mask ×1 |
| 89 | Ancestral robes set | 21049 | Yes | Ancestral hat ×1; Ancestral robe top ×1; Ancestral robe bottom ×1 |
| 90 | Obsidian armour set | 21279 | Yes | Obsidian helmet ×1; Obsidian platebody ×1; Obsidian platelegs ×1 |
| 91 | Justiciar armour set | 22438 | Yes | Justiciar faceguard ×1; Justiciar chestguard ×1; Justiciar legguards ×1 |
| 92 | Mystic set (light) | 23110 | Yes | Mystic hat (light) ×1; Mystic robe top (light) ×1; Mystic robe bottom (light) ×1; Mystic gloves (light) ×1; Mystic boots (light) ×1 |
| 93 | Mystic set (blue) | 23113 | Yes | Mystic hat ×1; Mystic robe top ×1; Mystic robe bottom ×1; Mystic gloves ×1; Mystic boots ×1 |
| 94 | Mystic set (dark) | 23116 | Yes | Mystic hat (dark) ×1; Mystic robe top (dark) ×1; Mystic robe bottom (dark) ×1; Mystic gloves (dark) ×1; Mystic boots (dark) ×1 |
| 95 | Mystic set (dusk) | 23119 | Yes | Mystic hat (dusk) ×1; Mystic robe top (dusk) ×1; Mystic robe bottom (dusk) ×1; Mystic gloves (dusk) ×1; Mystic boots (dusk) ×1 |
| 96 | Dragonstone armour set | 23667 | Yes | Dragonstone full helm ×1; Dragonstone platebody ×1; Dragonstone platelegs ×1; Dragonstone boots ×1; Dragonstone gauntlets ×1 |
| 97 | Dagon'hai robes set | 24333 | Yes | Dagon'hai hat ×1; Dagon'hai robe top ×1; Dagon'hai robe bottom ×1 |
| 98 | Inquisitor's armour set | 24488 | Yes | Inquisitor's great helm ×1; Inquisitor's hauberk ×1; Inquisitor's plateskirt ×1 |
| 99 | Masori armour set (f) | 27355 | Yes | Masori body (f) ×1; Masori chaps (f) ×1; Masori mask (f) ×1 |
| 100 | Twisted relic hunter (t1) armour set | 24469 | Yes | Twisted boots (t1) ×1; Twisted hat (t1) ×1; Twisted trousers (t1) ×1; Twisted coat (t1) ×1 |
| 101 | Twisted relic hunter (t2) armour set | 24472 | Yes | Twisted boots (t2) ×1; Twisted hat (t2) ×1; Twisted trousers (t2) ×1; Twisted coat (t2) ×1 |
| 102 | Twisted relic hunter (t3) armour set | 24475 | Yes | Twisted boots (t3) ×1; Twisted hat (t3) ×1; Twisted trousers (t3) ×1; Twisted coat (t3) ×1 |
| 103 | Trailblazer relic hunter (t1) armour set | 25380 | Yes | Trailblazer boots (t1) ×1; Trailblazer hood (t1) ×1; Trailblazer trousers (t1) ×1; Trailblazer top (t1) ×1 |
| 104 | Trailblazer relic hunter (t2) armour set | 25383 | Yes | Trailblazer boots (t2) ×1; Trailblazer hood (t2) ×1; Trailblazer trousers (t2) ×1; Trailblazer top (t2) ×1 |
| 105 | Trailblazer relic hunter (t3) armour set | 25386 | Yes | Trailblazer boots (t3) ×1; Trailblazer hood (t3) ×1; Trailblazer trousers (t3) ×1; Trailblazer top (t3) ×1 |
| 106 | Shattered relic hunter (t1) armour set | 26554 | Yes | Shattered boots (t1) ×1; Shattered hood (t1) ×1; Shattered trousers (t1) ×1; Shattered top (t1) ×1 |
| 107 | Shattered relic hunter (t2) armour set | 26557 | Yes | Shattered boots (t2) ×1; Shattered hood (t2) ×1; Shattered trousers (t2) ×1; Shattered top (t2) ×1 |
| 108 | Shattered relic hunter (t3) armour set | 26560 | Yes | Shattered boots (t3) ×1; Shattered hood (t3) ×1; Shattered trousers (t3) ×1; Shattered top (t3) ×1 |
| 109 | Trailblazer reloaded relic hunter (t1) armour set | 28777 | Yes | Trailblazer reloaded boots (t1) ×1; Trailblazer reloaded headband (t1) ×1; Trailblazer reloaded trousers (t1) ×1; Trailblazer reloaded top (t1) ×1 |
| 110 | Trailblazer reloaded relic hunter (t2) armour set | 28780 | Yes | Trailblazer reloaded boots (t2) ×1; Trailblazer reloaded headband (t2) ×1; Trailblazer reloaded trousers (t2) ×1; Trailblazer reloaded top (t2) ×1 |
| 111 | Trailblazer reloaded relic hunter (t3) armour set | 28783 | Yes | Trailblazer reloaded boots (t3) ×1; Trailblazer reloaded headband (t3) ×1; Trailblazer reloaded trousers (t3) ×1; Trailblazer reloaded top (t3) ×1 |
| 112 | Raging echoes relic hunter (t1) armour set | 30331 | Yes | Raging echoes boots (t1) ×1; Raging echoes hat (t1) ×1; Raging echoes robeskirt (t1) ×1; Raging echoes top (t1) ×1 |
| 113 | Raging echoes relic hunter (t2) armour set | 30334 | Yes | Raging echoes boots (t2) ×1; Raging echoes hat (t2) ×1; Raging echoes robeskirt (t2) ×1; Raging echoes top (t2) ×1 |
| 114 | Raging echoes relic hunter (t3) armour set | 30337 | Yes | Raging echoes boots (t3) ×1; Raging echoes hat (t3) ×1; Raging echoes robeskirt (t3) ×1; Raging echoes top (t3) ×1 |
| 115 | Demonic pacts relic hunter (t1) armour set | 33451 | Yes | Demonic boots (t1) ×1; Demonic hood (t1) ×1; Demonic robe bottom (t1) ×1; Demonic robe top (t1) ×1 |
| 116 | Demonic pacts relic hunter (t2) armour set | 33454 | Yes | Demonic boots (t2) ×1; Demonic hood (t2) ×1; Demonic robe bottom (t2) ×1; Demonic robe top (t2) ×1 |
| 117 | Demonic pacts relic hunter (t3) armour set | 33457 | Yes | Demonic boots (t3) ×1; Demonic hood (t3) ×1; Demonic robe bottom (t3) ×1; Demonic robe top (t3) ×1 |
| 118 | Sunfire fanatic armour set | 29424 | Yes | Sunfire fanatic helm ×1; Sunfire fanatic cuirass ×1; Sunfire fanatic chausses ×1 |
| 119 | Oathplate armour set | 30744 | Yes | Oathplate helm ×1; Oathplate chest ×1; Oathplate legs ×1 |
| 120 | Blood Moon armour set | 31136 | Yes | Blood Moon chestplate ×1; Blood Moon helm ×1; Blood Moon tassets ×1; Dual macuahuitl ×1 |
| 121 | Blue Moon armour set | 31139 | Yes | Blue Moon chestplate ×1; Blue Moon tassets ×1; Blue Moon helm ×1; Blue Moon spear ×1 |
| 122 | Eclipse Moon armour set | 31142 | Yes | Eclipse Moon helm ×1; Eclipse Moon tassets ×1; Eclipse Moon chestplate ×1; Eclipse atlatl ×1 |
| 123 | Torva armour set | 31145 | Yes | Torva full helm ×1; Torva platebody ×1; Torva platelegs ×1 |
| 124 | Virtus armour set | 31148 | Yes | Virtus mask ×1; Virtus robe top ×1; Virtus robe bottom ×1 |
| 125 | Rock-shell armour set | 31151 | Yes | Rock-shell helm ×1; Rock-shell plate ×1; Rock-shell legs ×1; Rock-shell boots ×1; Rock-shell gloves ×1 |
| 126 | Skeletal armour set | 31154 | Yes | Skeletal helm ×1; Skeletal top ×1; Skeletal bottoms ×1; Skeletal boots ×1; Skeletal gloves ×1 |
| 127 | Spined armour set | 31157 | Yes | Spined helm ×1; Spined body ×1; Spined chaps ×1; Spined boots ×1; Spined gloves ×1 |
| 128 | Swampbark armour set | 31160 | Yes | Swampbark helm ×1; Swampbark body ×1; Swampbark legs ×1; Swampbark boots ×1; Swampbark gauntlets ×1 |
| 129 | Bloodbark armour set | 31163 | Yes | Bloodbark helm ×1; Bloodbark body ×1; Bloodbark legs ×1; Bloodbark boots ×1; Bloodbark gauntlets ×1 |
