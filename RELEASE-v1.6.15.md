# TASMpro v1.6.15 RC1

Changes from v1.6.14 RC1:
- Reports library reads the full IndexedDB-backed in-memory snag records so the first photograph can be used as the project thumbnail, including after reopening the app.
- Report Preview generates an on-screen preview without triggering the native Android print/download workflow; Export/Print remains separate.
- Android system Back invokes in-app navigation: dismisses preview, Part L overlay, saved dialog, and report detail before returning to Reports.
- Branded exit confirmation on Reports: Stay in App or Exit App.
- Updated Android version metadata.

Validation: JavaScript syntax checked with Node. Android compilation and physical-device testing are still required. Back navigation in nested Part L iframe and every other modal should be checked on device.

## Requisition email attachment fix
Requisition PDF email now uses the same Android TASMAndroid.emailPdf native attachment bridge as snag reports, with browser sharing fallback.
