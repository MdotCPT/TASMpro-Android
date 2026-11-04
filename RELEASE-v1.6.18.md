# TASMpro v1.6.18 RC1 — JSON backup import picker

- Android WebChromeClient now respects file input accept types. JSON import opens a document picker that can select backup files from Downloads; photo inputs still use image selection and camera capture.
- Backup restore checks the .json extension and validates project/snag record structure before confirmation.
- Restore confirmation displays project and snag counts and warns that existing records and photographs will be replaced.
- Existing IndexedDB write path remains in use; successful restore requires completed database write.
- Bumped Android versionName and versionCode.

## On-device tests required
1. Open Settings > Restore a backup. Browse Downloads and select a plotsnag-backup-YYYY-MM-DD.json file.
2. Check confirmation counts and cancel once; confirm existing data is unchanged.
3. For a destructive restore test, first export a separate fresh backup and verify it is accessible.
4. Confirm imported snag photos appear, and records persist after closing/reopening.
5. Verify Quick Add camera and gallery inputs still work.

Note: This release is not compiled or device-tested in this source package.
