# Set See validation

## Automated result

Verified on **25 September 2026** with RuneLite **1.12.39**, Gradle **8.10**, and Temurin **17.0.20.1**. Plugin bytecode targets Java 11. The Gradle `clean build` task completed successfully with assertions enabled and headless Java2D rendering.

**934 test cases: 928 passed, 6 skipped, 0 failures, 0 errors.**

| Suite | Cases | Result |
| --- | ---: | --- |
| Catalogue | 8 | Passed |
| Every recipe | 903 | 897 passed; 6 not applicable |
| Lifecycle and events | 8 | Passed |
| Matching edge cases | 9 | Passed |
| Overlay rendering | 6 | Passed |

The six skipped cases are mixed noted/unnoted tests for the six god-book page sets: those page components have no noted variant, so that scenario does not exist. Their ordinary matching, quantity, missing-piece and box tests still run.

Every current recipe is exercised against the archived game definitions for complete unnoted components, complete noted components where available, multiple copies, each missing component, packed and noted boxes, and empty inventories. Catalogue validation also covers duplicate entries, newly added valid recipes, required quantities, unavailable definitions, and schema/index disagreement.

Regression checks cover shared pieces, packed boxes plus partial loose items, red reserved for incomplete groups, mixed forms, exact Barrows item variants, members worlds, integer overflow, slot estimates, client-thread startup, rapid enable/disable, inventory events, loading states, missing inventories, viewport clipping, moved widgets, hidden entries and tooltip hit areas.

The suite uses real catalogue data, pure matching logic, mocked RuneLite interfaces and Java2D images. It does **not** simulate game input or exercise the live server. CI is configured to repeat the build on pushes and pull requests; hosted CI has not run until the repository is pushed.

## Human in-game check — pending

Run `./gradlew run` from the project directory (Windows: `gradlew.bat run`). Enable Set See in the development client. For a Jagex account, use the official [development-client login instructions](https://github.com/runelite/runelite/wiki/Using-Jagex-Accounts).

1. Take a rune full helm, rune platebody, rune platelegs and rune kiteshield to the GE. Open **Sets**, with the native filter set to **Show all sets**. Check that the four items and **Rune armour set (lg)** share a colour and label.
2. Add a rune plateskirt. Check both alternative set entries and the shared-item chips. Pack one using the game's normal menu, then check that the other recipe updates immediately.
3. Unpack the box using the normal inventory menu. Check the packed box highlights before unpacking, then the loose components afterwards. Keep a box plus one loose component and confirm their statuses remain separate.
4. Remove a component and hover its set. Confirm the exact missing piece is listed even with incomplete highlighting off. Turn incomplete highlights on and check the red dashed borders.
5. Try all-noted components, several noted copies, and mixed noted/unnoted pieces. Compare the tooltip and highlights with the native packing option.
6. Try a nearly full inventory, including a noted box stack and existing component-note stacks. Check actual unpack output form and space requirements against the explicitly labelled estimates. Server output-form behaviour remains unverified by automated tests.
7. Scroll through the Sets list; switch the native craftable filter; try fixed, resizable and stretched modes. Check highlights stay aligned and clipped, and tooltips only appear over visible items.
8. Close/reopen Sets, disable/re-enable the plugin, and log out/in. Confirm stale highlights disappear and the client log has no Set See exceptions. If practical, also check a members-only recipe on a free world.

The repository's `AGENTS.md` requires human in-game confirmation before declaring a RuneLite feature complete and prohibits automated game input. That check is still pending for this build.
