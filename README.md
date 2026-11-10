# Command Limit Tracer

A server-side Fabric mod for Minecraft Java Edition 1.20.4. When a function
command chain reaches the execution limit, the mod adds a trace of recent
function calls to the server log to help identify recursive or excessive
function execution.

## Requirements

- Minecraft Java Edition 1.20.4
- Fabric Loader
- Java 17 or newer

## Install

1. Install Fabric Loader for Minecraft 1.20.4 on the server.
2. Copy `commandlimittracer-0.3.0.jar` into the server's `mods` folder.
3. Restart the server.

The mod applies mixins during startup. `/reload` reloads datapacks, but does
not load or update mods.

## Build

Run the Gradle wrapper from the project directory:

```sh
./gradlew build
```

The mod jar is created at `build/libs/commandlimittracer-0.3.0.jar`.

## Test

A test datapack that deliberately recurses until the command limit is available
in [`test-datapack/`](test-datapack/). Follow its
[instructions](test-datapack/README.md); lower the command-chain limit before
running its function, then restore the original value afterward.
