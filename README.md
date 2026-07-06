# IDE Utils

Some IDE utilities I personally use.

- **External file picker** — launch an external file-picker command and open the chosen file
  in the current IDE instance.
- **Copy Project Root** — copy the current project's root directory path to the system
  clipboard. I surprisingly did not find an easy way to do this in the IDE with a keybind.

Bind shortcuts to any of these in **Settings › Keymap**.

## The external file picker

The plugin ships no picker of its own — it runs any executable you configure and opens
whatever file that executable reports. The picker UI is entirely up to you: a TUI file
browser, a fuzzy finder, a GUI dialog, a script. The only requirement is that it can print
the path it picked to `stdout`.

### Configuring the picker

**Settings › Tools › IDE Utils › Picker command** — the executable to run. There is no
default; until it's set the picker actions do nothing but show a warning. The `IDEUTILS_PICKER`
environment variable overrides the setting when present.

### The picker contract

What the IDE gives your picker and expects back:

| Direction | Channel   | Value                                                             |
|-----------|-----------|-------------------------------------------------------------------|
| Input     | `argv[1]` | A directory to start in (the project root).                       |
| Output    | `stdout`  | The absolute path of the chosen file, or empty to cancel (no-op). |

Rules:

- Print exactly the chosen path (a trailing newline is fine; it's trimmed). Anything that
  is not an existing file is ignored.
- Empty stdout = "nothing picked": the IDE does nothing.
- Exit when done. For the external-terminal action, the terminal window closing is what
  ends the pick.
