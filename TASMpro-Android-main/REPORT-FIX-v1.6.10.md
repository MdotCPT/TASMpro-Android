# TASMpro v1.6.10 — report actions

Based on the user-provided `TASMpro-Android-main(2).zip`.

- Preview report now displays the generated snag report in an in-app A4 white preview.
- Export / Print PDF passes the generated report HTML directly to the native Android print bridge (`TASMAndroid.printHtml`), rather than relying on an overridden `window.print()` call and a subsequent DOM query.
- User-visible error messages appear when report generation fails.
- Existing camera/gallery, navigation, theme, Plot Tracker, and other app features are retained.

## Build
Upload the extracted contents to the existing `TASMpro-Android` GitHub repository, commit, then download the APK from GitHub Actions.

## Verify on Android
Create/select a plot with at least one outstanding snag. On Reports, select that plot and test Preview report, Close, Export / Print PDF, Save as PDF. Check photos and multi-page reports. Also regression-test requisitions, email PDF and camera.

**Not yet compiled or tested on a physical Android device.** The email PDF attachment path was not changed in this patch and requires a separate on-device verification.
