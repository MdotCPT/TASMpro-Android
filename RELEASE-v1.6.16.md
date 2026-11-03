# TASMpro v1.6.16 RC1

- Reports: replaces the email helper paragraph with a red Delete Project action for project reports only (not trade reports).
- Deletion requires a branded danger confirmation and removes the project and its snags/photos from the IndexedDB database, while leaving unrelated records alone.
- Refreshes the reports library and snag editor after deletion.
- Part L: small inline camera icon with existing Part L label.

## Test before release

1. Create two projects with multiple photo snags; delete one and restart app. Verify the deleted project's snags/photos do not return and the other project is intact.
2. Verify deletion is absent in trade reports, and Cancel preserves data.
3. Confirm Part L button fits on Samsung screen and still opens the module.
4. Regression test email and PDF sharing.

Source inspection and JS syntax checks do not replace Android compilation and device testing.
