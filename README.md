# TASMpro Android — fresh v1.6.8 build

This is a **new Android wrapper** around the approved v1.6.8 HTML prototype. It is separate from the old repository.

## Publish to a new GitHub repository

1. On GitHub create an **empty** repository named `TASMpro-Android` (no README or .gitignore).
2. Extract this ZIP on your computer, including the hidden `.github` folder.
3. Open a terminal **inside the extracted folder** and run:

```bash
git init
git branch -M main
git add .
git commit -m "Fresh TASMpro Android v1.6.8"
git remote add origin https://github.com/MdotCPT/TASMpro-Android.git
git push -u origin main
```

4. In GitHub open **Actions → Build TASMpro APK**. Wait for a green check and download the `TASMpro-debug-APK` artifact ZIP.
5. Extract the artifact ZIP and install `app-debug.apk` on Android. It is a **debug APK**, not a Play Store release.

## Android features

- JavaScript + persistent WebView localStorage.
- Camera/gallery file chooser with FileProvider.
- Native Android print dialog for `window.print()`.
- Blob-link downloads intercepted and saved under Downloads/TASMpro.
- HTTPS file download support.

## Important limitations and testing

This package has **not been compiled or device-tested here**. GitHub Actions must compile it, then test all navigation, plot setup, calendars, image capture, requisition/report PDF export and email/share flows on the device. The existing HTML's `navigator.share` behaviour may need a dedicated native share bridge; Android WebView does not guarantee Web Share API support. Do not rely on it for live site data until these checks pass. Back up browser prototype data separately: app localStorage is isolated from Chrome and from the previous APK.

The artwork-derived icon is an approximation from the supplied branding montage; a clean original logo asset would produce a sharper launcher icon.
