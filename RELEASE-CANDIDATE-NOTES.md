# TASMpro 1.6.14 RC1 — source release candidate

## Implemented
- Snag photo data in IndexedDB (`TASMproSnags` / `records` / `snagDatabase`), with lightweight metadata mirror for legacy consumers.
- Save confirmation is shown only after IndexedDB write completes. Startup waits for database hydration.
- Part L v4 integrated as a separate local HTML asset with embedded photographic examples.
- Part L ZIP sharing uses Android FileProvider ACTION_SEND with a ZIP attachment, falling back to browser sharing outside Android.
- Snagging PDF email uses Android native emailPdf bridge, with PDF attachment and prefilled subject/body/recipient where available.
- FileProvider includes camera, reports and shares cache folders.
- Quick Add success screen shows one OK button.

## Tests performed
- JavaScript syntax checks: all inline scripts in both HTML assets passed node --check.
- Archive integrity: validated after packaging.

## Tests NOT performed
- No Android APK compilation or on-device test in this environment.
- Storage stress tests with 30+ photo snags and persistence after process termination.
- Native email attachment reception and Part L ZIP attachment opening on a real device.

## Important
- This is a release candidate for testing, not a proven stable APK.
- User explicitly permitted a clean reset; no old snag migration is implemented.
- If the existing app's signature differs from a new build, uninstalling will erase old app data.
