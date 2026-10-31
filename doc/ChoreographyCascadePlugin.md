# Choreography Cascade Plugin (CC-plugin)

| | |
|---|---|
| Plugin id | `com.yelstream.topp.grasp.choreography-cascade` |
| Class | `com.yelstream.topp.grasp.cascade.plugin.ChoreographyCascadePlugin` (`buildSrc`) |
| Applied to | The root project only. It configures the root and all subprojects itself. |
| When it runs | At apply time, while the root build script is evaluated, before any subproject build script runs. |

## In short

What the plugin adds on top of plain Gradle:

1. **An inherited configuration you can list as one map.** `custom.properties` cascades down the project
   tree, with the child overriding the parent. Each project gets everything from the plugin's files,
   merged and resolved, as one map (`project.module.properties`). Plain Gradle can only look up
   inherited properties one name at a time.
2. **`${...}` substitution in property values.** Gradle reads property files as literal strings and has
   no substitution of its own. The plugin resolves `${key}`, with `default.key` and system-property fallbacks.
3. **`project.*` keys set the project's own properties.** `project.version = 0.4.9` in a property file
   sets `project.version`. This works for properties the project already has, such as `group`,
   `version` and `description`.
4. **A build timestamp shared by every project**, preset in several formats, for example
   `project.version = 0.4.9-${sanitized-build-time}`.
5. **`local.properties` for module-local values.** It applies to its own module only, is not inherited
   by child modules, and wins over every other source, including `-P` on the command line.

## 1. Purpose

The plugin gives each project an **effective configuration** built from property files spread
through the directory tree. A module states only what differs from its parents, and the rest is
inherited.

In practice it does four things:

1. **Cascades** (inherits) property files down the project tree, with the child overriding the parent.
2. **Resolves `${...}` placeholders** in property values.
3. **Promotes `project.*` keys** to real Gradle project properties, for example `project.version` sets `project.version`.
4. **Overrides** Gradle project properties of the same name, for example a value from a `gradle.properties` file, see §6.

`gradle.properties` files work exactly as in plain Gradle, with or without the plugin.
The plugin never reads them itself.

## 2. Property sources

| File | Location | Gradle concept? | Held in |
|---|---|---|---|
| `custom.properties` | Any project directory | No | `project.custom` |
| `module.properties` | Any project directory | No | `project.rawModule` |
| `local.properties` | Any project directory | No | applied directly |
| Build time | Computed once per build | No | `project.custom` |

Each holder is a `java.util.Properties` stored as an extra property with the same name
(`extra["custom"]`, `extra["rawModule"]`, `extra["local"]`).

### `gradle.properties`

The plugin does not read `gradle.properties` files. Gradle loads them as project properties when it
creates each project, before the plugin runs. This includes the folders between a module and the root,
because `include("a:b:c")` makes `a` and `a:b` projects too. So with or without the plugin:

- each file works as in plain Gradle, with child projects reading through to their parents' values;
- placeholders inside `gradle.properties` values are **not** resolved;
- its values **can** be used in placeholders in the plugin's files, through `project.findProperty` (§4);
- its values **can** be overridden by defining the same key in `custom.properties` or `local.properties` (§3, step 9).

### `local.properties` compared with `gradle.properties`

Both files are committed with the module. "Local" means local to the module, not to a developer.

| | Subproject `gradle.properties` | `local.properties` |
|---|---|---|
| Read by | Gradle | The plugin |
| Seen by child modules | Yes. Gradle's lookup reads through to parent projects. | No. Only the module's own file is read. |
| `${...}` placeholders | Not resolved | Resolved |
| Precedence | Loses to `-P`, `ORG_GRADLE_PROJECT_*` and `~/.gradle/gradle.properties` in plain Gradle. | Highest. Applied last, so it wins over every other file and over `-P`. |
| Typical use | Values shared by a module and its children. | A setting for this one module, for example `convention.publish.enable = false` in `SLF4J-Proxy-Demo-Application`. |

### Build-time keys

`BuildTime` is computed once per build, in the plugin's `apply`, so every project sees the same instant.
Without the configuration cache it is fresh in every build. With the configuration cache it keeps the
value from when the cache entry was stored, see §7, item 7.
The keys are that one instant in different formats, not different granularities.
`sanitized-build-time` is the Zulu time with `T` replaced by `-` and colons removed, so it is safe in
versions and file names. The number of fraction digits depends on the JVM clock; the examples show three.
It adds these keys to `custom`:

| Key | Example |
|---|---|
| `build-time` | `2026-10-31T10:15:30.123Z` |
| `build-time-zulu` | `2026-10-31T10:15:30.123Z` |
| `build-time-offset` | `2026-10-31T11:15:30.123+01:00` |
| `build-time-zoned` | `2026-10-31T11:15:30.123+01:00[Europe/Copenhagen]` |
| `sanitized-build-time` | `2026-10-31-101530.123Z` |

## 3. Processing order

The root project is processed first, then the subprojects in path order. Because a parent's
path sorts before its children's, each parent is complete before its children read from it.

For each project:

1. Reset the three holders to empty.
2. Put the build-time keys into `custom`.
3. Set the module defaults `default.project.group = com.example` and `default.project.version = <sanitized-build-time>`.
4. **`custom` cascade:** `custom = parent.custom + ./custom.properties`.
5. **`rawModule`:** `rawModule = parent.custom + ./module.properties`.
6. **`local.properties`:** each key is written as an extra property.
7. **Merge**, where later sources win: `custom` → `local.properties`.
8. **Resolve placeholders** in the merged set (§4).
9. **Store** every resolved key in the project's `Module` object (`project.module`).
   If the project already has a property with that name, for example from a `gradle.properties` file
   or from `-P`, it is overridden. A property of the project itself is set with `project.setProperty`.
   A property inherited from a parent project cannot be set that way, so an extra property with the
   same name is set on the project, which shadows the parent's value. The parent and its other
   children keep the original value.
10. **Promote** every module key that starts with `project.`:
    the prefix is removed and `project.setProperty(<rest>, value)` is called.
    So `project.group` and `project.version` set `group` and `version`, and `project.description` sets `description`.
    The name must be a property the project already has. An unknown name, such as `project.foo`,
    makes `setProperty` throw `MissingPropertyException`, which fails the build.

"`A + B`" means "start with A and let B overwrite".

## 4. Placeholder resolution

Values may contain `${key}`. Each placeholder is looked up in this order, and the first hit wins:

1. the merged set (`key`, then `default.key`)
2. `project.findProperty(key)`, then `default.key`. This covers every Gradle project property,
   including values from `gradle.properties` files, `-P` and `~/.gradle/gradle.properties`.
3. `System.getProperty(key)`, then `default.key`
4. no hit: the placeholder is left as written

Placeholders are resolved recursively. A key cannot refer to itself, so cycles stop.

Example:

```properties
# root custom.properties
project.version = 0.4.9-${sanitized-build-time}
```

## 5. Reading the result

| Accessor | Contents |
|---|---|
| `project.custom` / `project.properties["custom"]` | Cascaded `custom.properties` plus build-time keys. **Placeholders are not resolved.** |
| `project.module.properties` / `project.module.findProperty(k)` | Everything from the plugin's files, merged and resolved: the `custom.properties` cascade, `local.properties`, the build-time keys and the `default.project.*` values. Gradle's own project properties are **not** copied in. |
| `project.group`, `project.version`, ... | Set from `project.*` keys. |
| `project.findProperty(k)` | Gradle's project properties (`gradle.properties`, `-P`, ...), keys from `local.properties`, and overridden keys (§3, step 9). |

`project.module` is created by the plugin, not by Gradle. It is a Kotlin extension property on `Project`
that stores a `Module` object as the extra property `"module"`. Kotlin code needs
`import com.yelstream.topp.grasp.cascade.plugin.module`. Groovy scripts and `project.extra["module"]` work without it.

Two places to look:

- **The plugin's values** are in `project.module.properties`. A `custom.properties` key that is not also a Gradle
  property, such as `java.language-version`, is **not** visible through `project.findProperty`.
- **Gradle's values** (`gradle.properties`, `-P`, `~/.gradle/gradle.properties`) are read by name with
  `project.findProperty(k)`, `val k: String by project` or `project.hasProperty(k)`. Child projects read through
  to their parents' values. They are not in `project.module.properties`, unless the same key is also defined in
  one of the plugin's files.

The conventions currently read `project.properties["custom"]`:
`convention.java-options` for Java version, encoding, compiler and Javadoc options, and
`convention.publish` for the POM data.

## 6. What plain Gradle cannot do

| Capability | Plain Gradle | CC-plugin |
|---|---|---|
| Per-module configuration file inherited from parent modules | No. Only `gradle.properties` exists, with read-through lookup to parent projects. | `custom.properties` cascades down the tree with the child overriding the parent. |
| Enumerate the effective merged configuration of a project | No. Inherited properties can be looked up one name at a time but cannot be listed as one map. | `project.module.properties` lists everything from the plugin's files, resolved. `project.custom` lists the `custom.properties` cascade alone, with placeholders unresolved. Gradle's own properties stay in Gradle and are read by name. |
| `${...}` interpolation in property values | No. Values are literal strings. | Yes, with `default.` fallbacks and system properties. |
| Set `group` or `version` per module from an inherited file | Only via each project's own `gradle.properties` (`version=...`), with no inheritance or interpolation. | `project.version` etc. in `custom.properties`, inherited and interpolated. |
| One build timestamp shared by all projects and usable as a value | No. | `build-time`, `sanitized-build-time`, ... |
| Module-local values that are not inherited and win over everything | No. A subproject's `gradle.properties` is seen by its child projects and loses to `-P` and `~/.gradle/gradle.properties`. | `local.properties`, see §2. |
| Override a Gradle property for one module and its children | Only with the module's own `gradle.properties`, which loses to `-P` and `~/.gradle/gradle.properties`. | Define the key in `custom.properties` (inherited) or `local.properties` (this module only). |

Gradle decides precedence once, when it creates each project. The plugin runs afterwards, so a key
in `custom.properties` or `local.properties` also overrides a value given with `-P`,
`ORG_GRADLE_PROJECT_*` or `~/.gradle/gradle.properties`.

## 7. Known issues in the current implementation

These are differences between what the code does and what the design appears to intend.

1. **Consumers read unresolved values.** `convention.java-options` and `convention.publish` read
   `custom`, not `module`, so `${...}` placeholders arrive there unresolved, and `local.properties`
   values never reach them.
2. **The defaults are never used.** `default.project.group` and `default.project.version` are stored in
   `module`. Placeholder lookup searches the merged set, project properties and system properties,
   not `module`, and `default.*` keys are not promoted. A project with no `project.version` keeps `unspecified`.
3. **`module.properties` is loaded but not used.** `rawModule` is filled but never merged or resolved.
4. **`local.properties` is read twice** (steps 6 and 7).
5. **Unused settings.** `ENABLE_PROPERTY_NAME`, `PROPERTY_NAME_PREFIX` and `DEFAULT_DELAY` are declared
   but not read. The `TODO` about `cascade.enable` is not implemented, so the plugin cannot be switched off per project.
6. **Holders are set twice.** `initializeExtensions` and the start of `configureProject` both set them.
7. **With the configuration cache, the build time is frozen in the cache entry.** On a cache hit the
   configuration does not run, so the build time and everything built from it keep the value from when the
   entry was stored. A new value only appears when some configuration input changes.

The build time was earlier kept in a static `lazy` value, which Gradle's daemon reused across builds.
It is now computed in `apply` and must not be moved back into a static value.

The configuration cache otherwise works with the plugin. Checked with `--configuration-cache` on
`:module:Topp-Standard-Core-Library:jar` and `generatePomFileForMavenJavaPublication`:

- 0 configuration cache problems. The second run reused the entry.
- Gradle tracks the plugin's reads as cache inputs, although they use plain `java.io`. The inputs include the
  `custom.properties` and `local.properties` reads, the existence checks for files that are missing, and the
  `findProperty`/`hasProperty` lookups. Editing a `custom.properties` invalidates the entry
  ("cannot be reused because file '…custom.properties' has changed").
- The plugin is **not** compatible with Isolated Projects, a stricter, still incubating mode. The root
  project configures every subproject, and each subproject reads its parent's `custom` holder.

The cache is still switched off in the root `gradle.properties` (`org.gradle.configuration-cache=false`).
