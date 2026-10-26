# TASMpro v1.6.13 RC1 — PDF bridge correction

- Email report now reads snag data from IndexedDB and generates PDF using existing JavaScript PDF engine.
- Android native email sharing writes the PDF to app cache and attaches it using FileProvider.
- FileProvider reports cache path added.
- Native print uses A4 PrintAttributes and displays immediate feedback when invoked.
- Existing preview, camera, storage and Plot Tracker source preserved.

## Test
1. Preview report.
2. Tap Export / Print PDF: look for Preparing Android print preview toast and Android print dialog.
3. Tap Email report: select email client and verify the PDF is attached.

This release candidate has not been compiled or tested on Android.
