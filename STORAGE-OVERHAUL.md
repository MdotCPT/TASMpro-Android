# TASMpro IndexedDB storage pass (development)

- `TASMproSnags` IndexedDB database, `records` object store, `snagDatabase` key is authoritative for snag records and photo data.
- `plotsnag_v3` in localStorage now contains metadata-only mirror (photo fields stripped) for existing modules that still query projects and snag counts.
- Main snagging screens load full records from IndexedDB on startup. Save operations await transaction completion before showing success.
- Legacy migration is intentionally omitted: user authorised fresh reset.
- Quick Add confirmation remains a single OK button.
- Part L v4 prototype remains integrated as separate HTML asset.

## Not yet verified / known gaps
- No Android device test yet. IndexedDB support and persistence must be tested on device.
- Part L prototype has its own storage implementation and must be separately stress-tested.
- Email attachment/ZIP export still needs Android device verification.
- Some independent modules write their own localStorage data (requisitions, Customer Care, settings). This change addresses photo-heavy snag storage only.
- The app should be treated as a development candidate, not a verified release.
