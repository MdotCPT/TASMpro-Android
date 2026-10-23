# TASMpro Android v1.6.10 — report actions and contrast

Based on the user-supplied TASMpro-Android-main(2).zip.

- Restored Android print handoff using a direct JavaScript-to-native `printHtml(html, requisition)` call instead of retrieving the report via an asynchronous JavaScript evaluation.
- Kept a strong reference to the print WebView while Android's print preview is active.
- Preview Report now opens a white, A4-proportioned in-app preview with a separate Export / Print PDF button.
- Report printing uses a standalone white document with black text rather than the app UI background.
- Replaced the Quick Add unsaved-snag browser confirm with the existing TASMpro custom confirmation dialog.
- Strengthened contrast for dark buttons on light Quick Add panels and the success dialog.
- Preserved camera/file-picker integration, navigation, and existing app data storage.

## Validation
All embedded JavaScript blocks passed `node --check`. The Android APK has not been compiled or device-tested in this environment. After GitHub Actions builds, test report Preview, Export/Print, Email PDF, requisition export, camera/gallery and Quick Add cancellation on a real device. Existing installed data may not survive uninstall/reinstall; install as an update if the application ID/signature match.
