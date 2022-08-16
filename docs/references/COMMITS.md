# Conventional Commits
It is recommended that you read up on the [Conventional Commits  website](https://www.conventionalcommits.org/en/v1.0.0-beta.4/) first, then this file will help clarify how it relates to development.

## Types
The Aether project uses the following types for its commits, based on what version number a commit would increase. 

If a commit or PR falls under multiple types, it should attempt to be split up if there are contents not dependent on each other. If that's not possible, choose the highest relevant type for the changes.

### Updates Minor Version
1. `update:` Bumps dependency versions (Minecraft, Forge, etc.). 
2. `feat:` Adds content or API.
3. `improv:` Changes or improves existing content with code additions.
4. `perf:` Makes existing content more performant.

### Updates Patch Version
1. `fix:` Fixes incorrect behavior.
2. `refactor:` Changes or improves internal code.
3. `style:` Modifies code format without affecting function.
4. `docs:` Edits comments or JavaDocs.

### No Version Updates
1. `ci:` Modifies the CircleCI configuration.
2. `test:` Modifies any tests for the project.
3. `chore:` Edits markdown files, GitHub Action configs, anything that doesn't go into the mod.
4. `revert:` Reverts a previous commit.

## Scopes
Scopes are recommended, and should reflect what type of feature a commit implements.

If you're unsure what to put as a scope, look at what other developers have used to maintain consistency. A scope is not necessary if it can't encompass all of a commit's changes.

## Examples
```
update!: Update to Forge 41.1.0

BREAKING CHANGE: Client code has gone through renaming and reorganization according to Forge changes
```

```
feat(item): Add Ambrosium Shards
```

```
improv(recipe): Abstracted biome-based block conversion recipe code

Code is now abstracted into more superclasses and util methods for recipe classes to use instead of duplicating code

closes issue #456
```

```
perf(block): Icestone no longer greatly impacts TPS

Made use of GameEvents and recipe caching to minimize nested loops which were impacting TPS
```

```
fix(item): Swet Balls are no longer missing a usage animation
```

```
refactor: Make use of pattern matching for various checks

closes issue #615
```

```
style: Added this. to various field calls
```

```
docs: More vanilla copied methods have been labeled
```

```
ci: Updated config.yml to fix error with deployment to VPN
```

```
chore: Update README.md to fix broken link

[ci skip]
```