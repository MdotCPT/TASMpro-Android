# TASMpro v1.6.23 RC1 — Trade report data consistency

- Report summary, project checkbox counts, and print/PDF report now read the current `window.TASMFullDB` snag data, matching the Trades overview after backup restore.
- Trade comparisons normalize whitespace and handle the `completed` status consistently.
- Email PDF selection uses the same trade/status matching.
- Removes orphaned Report type heading.
- Preserves existing storage, photos, plot lists, and purge controls.

Validation: JavaScript syntax check only; Android APK and on-device testing still required.
