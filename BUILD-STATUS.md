# TASMpro v1.6.12 — development build (NOT final release)

Implemented in this iteration:
- Reports-first navigation, with central Quick Add, requisitions, Plot Tracker, Customer Care.
- Projects tab and floating Add Snag hidden; underlying snag database remains.
- Reports library with Plots / Trades, search, first chronological snag photograph as thumbnail, detail entry points and existing report actions.
- Home Demo time changed from native WebView time picker to editable HH:MM text, with validation.

**Not yet implemented or verified:**
- Native PDF generation and direct email attachments; existing export bridge is retained, but not verified.
- Durable photo storage migration out of localStorage and reliable full backup/restore. Existing backup JSON is not a verified cross-version full backup.
- Full report layout redesign, cover page correctness and multi-plot report controls.
- Android compilation, device smoke testing, existing data migration.

**Important:** Before installing any development APK over an app containing data, export a backup and preserve the original app. Keep application ID and signing identity unchanged. Do not uninstall or clear data.

Build on GitHub Actions using the existing workflow. This archive contains source, not a compiled APK.
