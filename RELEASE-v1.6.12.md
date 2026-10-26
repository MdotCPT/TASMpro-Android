# TASMpro v1.6.12 RC1

Changes:
- Snag projects and photographs now save to IndexedDB rather than the quota-limited `plotsnag_v3` localStorage key. Existing localStorage records are imported on first run when available. Backups still export JSON with photographs.
- Report Export / Print uses the native Android bridge; Preview skips the print invocation.
- Plot Tracker > Next To Do: upcoming actions show completion / booking controls; tapping the action card also opens a choice panel, and booking uses the existing custom calendar.
- Android versionCode incremented to 3.

## Important testing
- This is a **release candidate**, not a verified APK. GitHub Actions must compile it and device testing is needed.
- Back up your existing data before updating. Uninstalling removes app data. Updates require the same package ID and signing certificate. The GitHub debug build signing key may vary between runs: configure persistent signing before relying on in-place updates.
- Test photo-heavy snag creation, app restart persistence, report preview/export, and Upcoming Actions > booked/completed.
- PDF email attachment and full report redesign are NOT part of this three-fix release.
