<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# Ideutils Changelog

## [Unreleased]

## [1.0.0]

### Added

- External file picker: run any configurable external command to pick a file and open it in
  the current IDE instance. The picker receives the project root as its argument and returns
  the chosen path on stdout.
- Settings page (Tools › IDE Utils) to configure the picker command; overridable via the
  `IDEUTILS_PICKER` environment variable. Warns when no picker is configured.
- "Copy Project Root" action: copies the current project's root directory path to the
  clipboard.
