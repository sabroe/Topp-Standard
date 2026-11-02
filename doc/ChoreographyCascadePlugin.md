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
5. **`local.properties` for module-local values.** It applies to its own module only, is not seen by
   child modules, also not through `findProperty` (§3, step 6), and wins over every other source,
   including `-P` on the command line.

## 1. Purpose

The plugin gives each project an **effective configuration** built from property files spread
through the directory tree. A module states only what differs from its parents, and the rest is
inherited.

In practice it does four things:

1. **Cascades** (inherits) property files down the project tree, with the child overriding the parent.
2. **Resolves `${...}` placeholders** in property values.
3. **Promotes `project.*` keys** to real Gradle project properties, for example `project.version` sets `project.version`.
4. **Overrides** Gradle project properties of the same name, for example a value from a `gradle.properties` file, see §7.

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
| Seen by child modules | Only for keys that no build-wide source (root `gradle.properties`, `-P`, `~/.gradle/gradle.properties`) also sets. Gradle's lookup reads through to parent projects, but a child finds its own copy of a build-wide value first. Real properties such as `version` are not inherited (§5.1). | No. Only the module's own file is read, and its keys are hidden from child modules, also from `findProperty` (§3, step 6). |
| `${...}` placeholders | Not resolved | Resolved |
| Precedence | Loses to `-P`, `ORG_GRADLE_PROJECT_*` and `~/.gradle/gradle.properties` in plain Gradle. | Highest. Applied last, so it wins over every other file and over `-P`. |
| Typical use | Values shared by a module and its children. | A setting for this one module, for example `convention.publish.enable = false` in `SLF4J-Proxy-Demo-Application`. |

**A child's own value wins over the parent's `local.properties`.** Checked with temporary files, read with
`findProperty`. `module/local.properties` set `probe.k1`, `probe.k2` and `probe.k3` to `parentLocal`, and the
child `:module:Core` set each key in a different file:

| Key | Child `:module:Core` sets it in | `:module` | `:module:Core` | Grandchild `…:Topp-Standard-Core-Language-Library` |
|---|---|---|---|---|
| `probe.k1` | its `gradle.properties` | `parentLocal` | `childGradleProperties` | `childGradleProperties`, inherited as normal |
| `probe.k2` | its `custom.properties` | `parentLocal` | `childCustom` | `childCustom`, cascaded as normal |
| `probe.k3` | its `local.properties` | `parentLocal` | `childLocal` | `null`, hidden as intended |

- **The child's own `gradle.properties`, or a build-wide source such as `-P`:** the child already has its own
  value when the parent's local keys are hidden (§3, step 6), so that value is kept.
- **The child's own `custom.properties` or `local.properties`:** the parent's value is hidden first, and the
  child's resolved values replace it later in the same step (§3, steps 9 and 10).
- **The child's build script** runs after the plugin, so a value set there wins as well.

The hiding only fills keys that would otherwise leak from the parent. Values inherited the normal way still
reach the grandchild, and `:module:Core`'s own `local.properties` value stays hidden from it.

### Build-time keys

`BuildTime` is computed once per build, in the plugin's `apply`, so every project sees the same instant.
The time comes from `BuildTimeValueSource`, a Gradle `ValueSource` that returns a `ZonedDateTime`:
an instant from a `java.time.InstantSource`, in the build's time zone. The keys below are formatted from that.
The keys are that one instant in different formats, not different granularities.
`sanitized-build-time` is the Zulu time cut to whole seconds, with `T` replaced by `-` and colons removed,
so it is safe in versions and file names. In the other keys, the number of fraction digits depends on the
JVM clock; the examples show three.

The Gradle property `build-time.tick` rounds the instant down to steps of an ISO-8601 duration, for example
`-Pbuild-time.tick=PT1H` or `build-time.tick = P1D` in the root `gradle.properties`. Steps are counted from
the epoch in UTC, so `P1D` steps at 00:00 UTC, not at local midnight. If the property is unset, the default
`PT3M` is used. An empty value, for example `-Pbuild-time.tick=`, turns rounding off.

The Gradle property `build-time.zone` sets the time zone by ID, for example `-Pbuild-time.zone=UTC`.
If it is unset, the JVM's default time zone is used. Setting it gives the same values on every machine.
An invalid tick or zone fails the build with a message that names the property and the value.

The value source is obtained at configuration time, so the build time is a configuration-cache input.
Gradle obtains it again on every build and reuses a cache entry only while it is unchanged:

| `build-time.tick` | Without the configuration cache | With the configuration cache |
|---|---|---|
| unset (default `PT3M`) | Rounded down to 3 minutes, for example `2026-11-01T01:03:00Z` | Rounded down. A cache entry is reused within each 3 minutes. |
| empty | Exact time, new in every build | Exact time, new in every build. A cache entry is never reused. |
| `PT1M` / `PT1H` | Rounded down, for example `2026-11-01T01:00:00Z` | Rounded down. A cache entry is reused within the minute or hour. |
| `P1D` | Rounded down to `2026-11-01T00:00:00Z` | Rounded down. A cache entry is reused for the whole day. |

The time zone is part of the value, so a change of `build-time.zone` also invalidates a cache entry.
Without `build-time.zone`, the JVM's default zone is used. A running Gradle daemon keeps the default zone
it started with, even with `-Duser.timezone`, so a change of the machine's zone takes effect only in a new daemon.
It adds these keys to `custom`:

| Key | Example |
|---|---|
| `build-time` | `2026-10-31T10:15:30.123Z` |
| `build-time-zulu` | `2026-10-31T10:15:30.123Z` |
| `build-time-offset` | `2026-10-31T11:15:30.123+01:00` |
| `build-time-zoned` | `2026-10-31T11:15:30.123+01:00[Europe/Copenhagen]` |
| `sanitized-build-time` | `2026-10-31-101530Z` |

## 3. Processing order

The root project is processed first, then the subprojects in path order. Because a parent's
path sorts before its children's, each parent is complete before its children read from it.

For each project:

1. Reset the three holders to empty.
2. Put the build-time keys into `custom`.
3. Set the module defaults `default.project.group = com.example` and `default.project.version = <sanitized-build-time>`.
4. **`custom` cascade:** `custom = parent.custom + ./custom.properties`.
5. **`rawModule`:** `rawModule = parent.custom + ./module.properties`.
6. **`local.properties`:**
   1. **Hide the parent's local values.** Gradle's lookup reads through to parent projects, and an extra
      property can't be removed. So for each key the parent set from its `local.properties`, and that this
      project doesn't have itself, this project gets its own extra property. Its value is what the parent
      saw before applying the file, possibly `null`. Only the direct parent is looked at: it was configured
      first, so it already holds the right value for its own parent's local keys.
   2. **Read** this project's `local.properties`, and record what the project sees for each of its keys
      at this point. Child projects use these values in step 6.1.

   The values are applied in steps 9 and 10, like all other keys.
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
    Every other module key is set as an extra property on the project, so it can be read by name with
    `project.findProperty(key)`, see §5.1.

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
| `project.custom` / `project.properties["custom"]` | Cascaded `custom.properties` plus build-time keys. **Placeholders are not resolved.** The plugin's internal inheritance chain; conventions should not read it. |
| `project.module.properties` / `project.module.findProperty(k)` | Everything from the plugin's files, merged and resolved: the `custom.properties` cascade, `local.properties`, the build-time keys and the `default.project.*` values. Gradle's own project properties are **not** copied in. |
| `project.group`, `project.version`, ... | Set from `project.*` keys. |
| `project.findProperty(k)` | Gradle's project properties (`gradle.properties`, `-P`, ...) and every module key (§3, step 10), see §5.1. |

`project.module` is created by the plugin, not by Gradle. It is a Kotlin extension property on `Project`
that stores a `Module` object as the extra property `"module"`. Kotlin code needs
`import com.yelstream.topp.grasp.cascade.plugin.module`. Groovy scripts and `project.extra["module"]` work without it.

Two places to look:

- **The plugin's values** are listed in `project.module.properties`. Each one is also an extra property on the
  project, so a key such as `java.language-version` can be read with `project.findProperty`.
- **Gradle's values** (`gradle.properties`, `-P`, `~/.gradle/gradle.properties`) are read by name with
  `project.findProperty(k)`, `val k: String by project` or `project.hasProperty(k)`. Child projects read through
  to their parents' values. They are not in `project.module.properties`, unless the same key is also defined in
  one of the plugin's files.

`convention.java-options` (Java version, encoding, compiler and Javadoc options) and `convention.publish`
(the POM data and signing) read their values by name with `project.findProperty`. They don't read `custom`.
So they work with and without the plugin: plain Gradle supplies values from `gradle.properties` files and `-P`,
and the plugin adds its resolved values, including `local.properties` keys.

They use helpers in `com.yelstream.topp.grasp.util.Projects`, in the Topp-Gradle-Convention build:

| Helper | Result |
|---|---|
| `stringProperty(key)` | The trimmed value, or `null` when unset or blank |
| `stringProperty(key, default)` | The trimmed value, or `default` when unset or blank |
| `booleanProperty(key, default)` | `true` or `false`. Any other value fails the build and names the key. |
| `intProperty(key, default)` | An integer. Any other value fails the build and names the key. |
| `listProperty(key)` | The value split on whitespace, or an empty list when unset or blank |

A blank value, such as `key =`, counts as unset and gives the default, not `""`.

### 5.1 Reading a property by name: `findProperty`, `hasProperty`, `by project`

These three read the same thing:

| Call | Result when the property is missing |
|---|---|
| `project.findProperty(key)` | `null` |
| `project.hasProperty(key)` | `false` (returns only whether it exists) |
| `val x: String by project` | Fails with `MissingPropertyException`. `val x: String? by project` gives `null`. The property name is the variable name, so dotted keys can't be used. |

**Lookup order.** Gradle searches the project and stops at the first hit:

1. Real properties of the `Project` object, such as `name`, `group`, `version` and `description`.
2. The project's own extra properties (`project.extra`).
3. Extensions, by name, for example `java` or `publishing`.
4. Tasks, by name.
5. The parent project's extra properties, then its parent's, up to the root. These are read-only from the child.

**Where the values come from.** These were checked in this build with Gradle 9.8.2:

| Source | Stored as | `findProperty` in a child module | `providers.gradleProperty(key)` |
|---|---|---|---|
| Root `gradle.properties`, `~/.gradle/gradle.properties`, `-P`, `ORG_GRADLE_PROJECT_*` | An own extra property on **every** project | Found, as the module's own value | Found |
| A subproject's `gradle.properties` | An own extra property on **that** project only | Found by reading through to the parent | **Not** found |
| CC plugin module keys (`custom.properties`, `local.properties`, build time) | An own extra property on each project, set from that project's own module (§3, step 10) | Found, as the module's own value | **Not** found |
| A key that matches a real property, for example `version` | Sets the real property, not an extra | Found (step 1) | Root and `-P` sources only |

Consequences:

- **Plain Gradle and the plugin look the same.** A convention that reads `project.findProperty(key)` gets the
  value whether it comes from a `gradle.properties` file, `-P` or the CC plugin. It works with and without the plugin.
- **`providers.gradleProperty(key)` is not equivalent.** It sees only the build-wide sources Gradle loads at
  startup, before any plugin runs. It doesn't see a subproject's `gradle.properties` or anything the CC plugin sets.
  Use it only for build-wide settings.
- **The CC plugin wins.** It runs after Gradle has created the projects, so its extra property replaces a
  value of the same name from `gradle.properties` or `-P` on that project.
- **Reading through to the parent still applies.** If a module has no value for a key and its parent has one,
  `findProperty` in the module returns the parent's value. The exception is a parent's `local.properties` keys:
  the plugin hides them with the module's own value (§3, step 6). When no other source sets the key, that value
  is `null`, so `findProperty` returns `null`, but `hasProperty` returns `true`. Check with `findProperty`, not
  `hasProperty`. To read only the module's own value, use `project.extra.properties[key]`.
- **Values are untyped.** Everything from a property file is a `String`. Convert it yourself, for example
  `findProperty(key)?.toString()?.trim()?.toBoolean()`. A cast such as `as? Boolean` always gives `null`.

**Overriding with a subproject's `gradle.properties`.** Plain Gradle's only override mechanism is the
parent lookup above, and it is limited. Checked with a temporary `module/gradle.properties`, without the CC plugin
setting these keys:

| Key | Root `gradle.properties` | `module/gradle.properties` | `-P` |
|---|---|---|---|
| `xxx.yyy` | `anything` | `fromSub` | – |
| `probe.cli` | – | `fromSub` | `fromCli` |
| `description` | – | `descFromSub` | – |

| Key | `findProperty` in `:module` | `findProperty` in `:module:Topp-Standard-Core-Library` (child) |
|---|---|---|
| `xxx.yyy` | `fromSub`: the subproject file beats the root file | `anything`: the root value is back |
| `probe.cli` | `fromCli`: `-P` beats the subproject file | `fromCli` |
| `description` | `descFromSub`: the real property is set | `null`: not inherited |

- **A key set only in the subproject's file** is inherited by the children, through the parent lookup.
- **A key also set by a build-wide source** (root `gradle.properties`, `-P`, `~/.gradle/gradle.properties`)
  can't be overridden for a subtree. Every project has its own copy of the build-wide value, and the child
  finds that copy before it looks at the parent. The subproject's file changes the value for that project only.
- **Real properties** such as `version`, `group` and `description` are set on that project only and are never inherited.
- **`-P` always wins** over the subproject's file.

The CC plugin's `custom.properties` doesn't have these limits. Values are copied down the tree, so each
child starts from its parent's values as its own and then applies its own overrides. This works for every
key, including keys also set at the root, and for real properties through `project.*` keys.

**Configuration cache.**

- **These calls run only while the build is configured.** On a cache hit, configuration is skipped. No build
  script, plugin or lookup runs, and tasks use the values stored in the cache entry.
- **Extra properties are not stored in the cache.** The cache holds the task graph and each task's configured
  state, not the `Project` objects. On a cache hit no extra property exists, neither from `gradle.properties`
  nor from the CC plugin. A value survives only if it was copied into a task during configuration, for example
  `java.compiler.default-args` into `compileJava.options.compilerArgs`.
- **So read properties at configuration time and copy the value into the task.** The conventions do this: the
  `stringProperty(...)` helpers run while the convention is applied. Don't call `project.findProperty` in a task
  action (`doFirst`/`doLast`). With the configuration cache on, Gradle reports that as a problem
  ("invocation of `Task.project` at execution time").
- **Gradle properties are cache inputs, one key at a time.** Changing a value that the build reads, from
  `gradle.properties` or `-P`, invalidates the entry, for example "cannot be reused because Gradle property
  'probe.gp' has changed". A missing key that is looked up is tracked as well. An unrelated `-P` that nothing
  reads does not invalidate the entry.
- **Listing all Gradle properties makes the whole set an input.** `providers.gradlePropertiesPrefixedBy("")`
  does that, and then adding any `-P` invalidates the entry. The property dump in `convention.java-options`
  uses it, so it runs only on request, as the task `dumpProperties` (group "help"). Only a build that runs
  that task depends on the whole set.
- **CC plugin values are not inputs by themselves.** They are extra properties computed during configuration.
  The cache depends on what they were computed from: the `custom.properties` and `local.properties` files (§8),
  and the Gradle properties and system properties used in placeholders. Editing any of these invalidates the entry.
- **The build time is obtained from a value source**, so it is an input as well. Without `build-time.tick`,
  it changes on every build, and a cache entry is never reused. With a tick, an entry is reused within each
  step (§2, "Build-time keys").
- **Isolated Projects** (incubating) doesn't allow reading another project's state. Reading through to a parent's
  extra properties is not allowed there.

## 6. Setting version, group and artifact name

A published artifact is identified by `groupId:artifactId:version`. In this build these come from
`project.group`, the publication's `artifactId` (by default `project.name`) and `project.version`.
There are two ways to set `group` and `version`.

### 6.1 `group` and `version` in the root `gradle.properties`

```properties
# root gradle.properties
group = com.yelstream.topp.standard
version = 0.4.9
```

This is plain Gradle and works without the plugin.

- **It applies to every project in the build.** The root `gradle.properties` covers the whole build.
  When Gradle creates each project, it sets matching real properties, so `project.group` and
  `project.version` are set on the root and on every subproject.
- **It is set before any build script runs**, so every script and plugin sees the value.
- **Values are literal.** `${...}` is not resolved, so you can't build the version from the build time.
- **Per-module overrides are limited.** A subproject's own `gradle.properties` can set `version` for that
  project. `version` is a real property, not an extra property, so the subproject's children don't read
  through to it. They keep the root value.
- **`-Pversion=...` on the command line wins over the file**, using normal Gradle precedence.
- **It is overridden by the plugin.** A `project.version` from `custom.properties` or `local.properties`
  replaces it, see §6.2.

### 6.2 `project.group` and `project.version` in `custom.properties`

```properties
# root custom.properties
project.group = com.yelstream.topp.standard
project.version = 0.4.9
```

This needs the plugin. The keys are promoted to the real properties in §3, step 10.

- **It applies to every project in the build.** The `custom.properties` cascade passes the keys down
  to every subproject.
- **It can be overridden at any level of the hierarchy.** A `custom.properties` in any module, or in an
  intermediate folder project, overrides the value for that project and all of its children. Its
  siblings and parents keep their own value.
- **`local.properties` overrides it for one module only.** Its children are not affected.
- **`${...}` is resolved**, for example `project.version = 0.4.9-${sanitized-build-time}`.
- **It wins over Gradle's own sources.** The plugin runs after Gradle has set the properties, so it
  overrides `group` and `version` from any `gradle.properties`, `-Pversion=...`, `ORG_GRADLE_PROJECT_version`
  and `~/.gradle/gradle.properties`. As a result, **`-Pversion=...` on the command line has no effect** while
  `project.version` is set in a `custom.properties` file.
- **A subproject build script still wins.** `version = "..."` in a subproject's build script runs after
  the plugin. Conventions read `project.version` when they are applied (for example the publication in
  `convention.publish`), so a version set later in the same script doesn't reach them.
- **There is no fallback.** The `default.project.*` values are not used (§8, item 2). A project that gets no
  `project.version` from any file keeps `unspecified`. A project with no `project.group` keeps Gradle's
  derived group: `""` for the root, `Yelstream-Topp-Standard-Project` for a top-level child, and so on.

Use one of the two mechanisms, not both. The root `custom.properties` currently sets both values,
and the root `gradle.properties` sets neither.

### 6.3 `default.project.group` and `default.project.version`

> **TODO:** Describe the intended effect of `default.project.group` and `default.project.version`, and
> make the code match it.

What the code does today:

- The plugin sets `default.project.group = com.example` and
  `default.project.version = <sanitized-build-time>` in each project's `module` (§3, step 3).
- **They have no effect.** `default.*` keys are not promoted to project properties, and placeholder
  lookup does not search `module` (§8, item 2). A project with no `project.group` or `project.version`
  keeps Gradle's own defaults (§6.2).
- **They can't be overridden from a file.** They are written to `module`, not to `custom`. A
  `default.project.version` key in `custom.properties` is stored in `module` under the same name and
  replaces the built-in value there, but it still has no effect.

The apparent intent is a fallback: when no file sets `project.version`, the version becomes the
sanitized build time, and the group becomes `com.example`.

### 6.4 Artifact name

The name shows up in three places, each set separately:

| Name | Default | Override | Set by |
|---|---|---|---|
| Project name | Folder name from `include(...)`. The root uses `rootProject.name` in `settings.gradle.kts`. | `project(":x").name = "..."` in settings | Gradle |
| Archive file name (`<archivesName>-<version>.jar`) | `project.name` with spaces replaced by `-` | `archives.base-name-prefix` is prefixed | `convention.archive-naming` |
| Maven `artifactId` | `project.name` | `publishing.publication.artifactId` | `convention.publish` |

- **Set `artifactId` in every published module.** Every module currently sets
  `publishing.publication.artifactId` in its `custom.properties`. Because `custom.properties` is
  inherited, a module nested under another module would inherit its parent's `artifactId`. Two
  publications would then share a name (§8, item 8).
- **The archive name and `artifactId` are independent.** For example, the jar can be named
  `Topp-Standard-Nillable-Library-0.4.9.jar` while it is published as `topp-standard-nillable`.
- **`archives.base-name-prefix` is read with `project.extra.properties`.** A key in `custom.properties`
  isn't seen there (§8, item 9). Set it in `local.properties` or a `gradle.properties`. No module sets it today.

### 6.5 Values that default to `group` and `version`

These read `project.group` and `project.version` unless they are overridden:

| Where | Override keys |
|---|---|
| Maven publication `groupId` and `version` (`convention.publish`) | `publishing.publication.groupId`, `publishing.publication.version`, from any source read by `findProperty` |
| Jar manifest `Implementation-Group` and `Implementation-Version` (`convention.jar-manifest`) | `jar.manifest.implementation-group` / `implementation.group`, and `jar.manifest.implementation-version` / `implementation.version` |
| Java module version (`javaModuleVersion`, `convention.java-options`) | none |
| `description` | `project.description` in `custom.properties` |

- **`convention.publish` reads by name with `project.findProperty`** (§5). `publishing.publication.*` values
  can come from `custom.properties`, `local.properties`, a `gradle.properties` file or `-P`, and `${...}`
  placeholders arrive resolved. A blank value, such as `publishing.publication.version =`, counts as unset,
  so the default `project.group` or `project.version` is used.
- **`convention.jar-manifest` reads `project.extra.properties`.** The `implementation.*` keys in the
  root `custom.properties` aren't seen there, so they have no effect on the manifest. For example, the
  vendor falls back to `Yelstream` (§8, item 10).

## 7. What plain Gradle cannot do

| Capability | Plain Gradle | CC-plugin |
|---|---|---|
| Per-module configuration file inherited from parent modules | No. Only `gradle.properties` exists, with read-through lookup to parent projects. | `custom.properties` cascades down the tree with the child overriding the parent. |
| Enumerate the effective merged configuration of a project | No. Inherited properties can be looked up one name at a time but cannot be listed as one map. | `project.module.properties` lists everything from the plugin's files, resolved. `project.custom` lists the `custom.properties` cascade alone, with placeholders unresolved. Gradle's own properties stay in Gradle and are read by name. |
| `${...}` interpolation in property values | No. Values are literal strings. | Yes, with `default.` fallbacks and system properties. |
| Set `group` or `version` per module from an inherited file | Only via each project's own `gradle.properties` (`version=...`), with no inheritance or interpolation. | `project.version` etc. in `custom.properties`, inherited and interpolated. |
| One build timestamp shared by all projects and usable as a value | No. | `build-time`, `sanitized-build-time`, ... |
| Module-local values that are not inherited and win over everything | No. A subproject's `gradle.properties` is seen by its child projects and loses to `-P` and `~/.gradle/gradle.properties`. | `local.properties`, see §2. Its keys are hidden from child modules, also from `findProperty` (§3, step 6). |
| Override a Gradle property for one module and its children | Only partly, with the module's own `gradle.properties`. It loses to `-P` and `~/.gradle/gradle.properties`. It reaches the children only for keys that no build-wide source sets. A key that is also in the root `gradle.properties` is overridden for the module alone, and real properties such as `version` are never inherited (§5.1). | Define the key in `custom.properties` (inherited, for every key) or `local.properties` (this module only). |

Gradle decides precedence once, when it creates each project. The plugin runs afterwards, so a key
in `custom.properties` or `local.properties` also overrides a value given with `-P`,
`ORG_GRADLE_PROJECT_*` or `~/.gradle/gradle.properties`.

## 8. Known issues in the current implementation

These are differences between what the code does and what the design appears to intend.

1. ~~**Fixed: consumers read unresolved values.** `convention.java-options` and `convention.publish` used to
   read `custom`, so `${...}` placeholders arrived unresolved, `local.properties` values never reached
   them, and every `javadoc.*` boolean was ignored (an `as? Boolean` cast on a string). They now read
   by name with `project.findProperty`, which sees the plugin's resolved values (§5).~~
2. **The defaults are never used.** `default.project.group` and `default.project.version` are stored in
   `module`. Placeholder lookup searches the merged set, project properties and system properties,
   not `module`, and `default.*` keys are not promoted. A project with no `project.version` keeps `unspecified`.
3. **`module.properties` is loaded but not used.** `rawModule` is filled but never merged or resolved.
4. ~~**Fixed: `local.properties` was read twice** (steps 6 and 7). It was also written as extra properties
   before its placeholders were resolved. It is now read once and applied with the other keys.~~
5. **Unused settings.** `ENABLE_PROPERTY_NAME`, `PROPERTY_NAME_PREFIX` and `DEFAULT_DELAY` are declared
   but not read. The `TODO` about `cascade.enable` is not implemented, so the plugin cannot be switched off per project.
6. **Holders are set twice.** `initializeExtensions` and the start of `configureProject` both set them.
7. ~~**Fixed: with the configuration cache, the build time was frozen in the cache entry.** It came from the
   clock, which Gradle can't track, so a reused entry kept the time from when it was stored. It now comes from
   a value source, which Gradle checks on every build (§2, "Build-time keys"). The price: without
   `build-time.tick`, a cache entry is never reused, because the time always changes.~~
8. **`publishing.publication.artifactId` is inherited.** Each module sets it in its `custom.properties`,
   which cascades. A module nested under another published module, without its own value, gets the
   parent's `artifactId`, so two publications share a name. Moving the key to each module's
   `local.properties` avoids this, because those keys are hidden from child modules (§3, step 6).
9. ~~**Fixed: `archives.base-name-prefix` couldn't be set from `custom.properties`.** `convention.archive-naming`
   reads it with `project.extra.properties`, which used to miss `custom.properties` keys. Every module key is
   now set as an extra property on each project (§3, step 10), so the key works from `custom.properties`.
   No module sets it yet, so the prefix is still empty.~~
10. ~~**The `implementation.*` keys in the root `custom.properties` have no effect.**
    `convention.jar-manifest` reads `jar.manifest.*` and `implementation.*` with `project.extra.properties`,
    which doesn't see `custom.properties` keys (same reason as item 9). The manifest falls back to
    `project.name`, `project.group`, `project.version` and the vendor `Yelstream`, so
    `implementation.vendor-name` and the other values in the file are ignored.~~
11. ~~**Fixed: empty values in the jar manifest.** The root `custom.properties` has `implementation.title =`,
    `implementation.version =` and `implementation.group =`. `convention.jar-manifest` read them with
    `project.extra.properties[...] as? String`, and an empty string is not `null`, so the fallbacks
    `project.name`, `project.version` and `project.group` were never used, and the jar got empty
    `Implementation-Title`, `Implementation-Version` and `Implementation-Group`.
    `Implementation-Java-Language-Version` was empty too, because `java.language-version.tool-chain = *`
    sets no toolchain. The convention now reads every key with `project.stringProperty`, which treats a blank
    value as unset, as `convention.java-options` and `convention.publish` do. Without a toolchain, the Java
    version falls back to `targetCompatibility`. The fix is in the `Topp-Gradle-Convention` checkout, which is
    the convention used, not in the copy in this repository's `convention/` folder.~~

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
