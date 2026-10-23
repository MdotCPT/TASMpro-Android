# TASMpro v1.6.9 Android fixes

- Separate Camera and Gallery intents. Camera uses FileProvider, Gallery uses ACTION_OPEN_DOCUMENT. Removed unnecessary CAMERA permission that can prevent launching a camera without runtime permission.
- Android printing now creates a separate white A4 WebView document from report content rather than printing the dark app UI. This deliberately suppresses dark cover art.
- Higher contrast for text in light dialogs and success screens.
- Replaced launcher icons with the white/gold T monogram on navy.

## Test on device
1. Quick Add > Camera > take a photo > confirm it appears.
2. Quick Add > Gallery > select a photo > confirm it appears.
3. Reports > preview > Print PDF > check white A4, text, photographs and pagination.
4. Requisitions > export/print > check white A4.
5. Test modal/success text and launcher icon.

This package has not been compiled or device-tested in this environment.
