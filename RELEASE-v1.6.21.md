# TASMpro v1.6.21 RC1 — Trade report controls

- Restores the project checkbox grid in trade report options (previously forcibly hidden by CSS).
- Makes the trade report summary open a scrollable trade snag register across selected plots.
- The register supports editing, completing/reopening and deleting original snag records, with confirmation on deletion. Changes are reflected in plot reports.
- Renames the action to Purge Trade List; purging hides the trade list but does not delete plot records. It can be restored using Restore hidden trade lists.

## Important

Trade-list purging currently hides the entire trade category until restored, including newly added snags. It does not individually mark snag IDs as purged.

## Validation

Source syntax and archive checks only; Android APK and device behaviour have not been verified. Back up data before testing.
