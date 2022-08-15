## Semantic Versioning
It is recommended that you read up on the [Semantic Versioning website](https://semver.org/) first, then this file will help clarify points from it.

If you believe there are any examples left out of this file, feel free to suggest them.

### Major Version Changes
These are signified by any changes in a project that are considered **breaking changes**.

Examples:
- Changing package names.
- Changing the location of a public or protected method, field, or class.
- Renaming public or protected methods, fields, and classes.
- Changing the protection status of previously public or protected code to private.
- Deleting public or protected methods, fields, and classes.
- Changing the functionality of a method in a way that makes its usage different from how it was originally.
- Changing code according to breaking changes caused by a Minecraft or Forge update.
- Changing gameplay mechanic in a way which may affect addons that interact with them.
- Changing IDs leading to missing registry objects or deleted content.
- Breaking compatibility of previous worlds with the current version (not advised).

How this looks with versions is that if an addon is dependent on version 1.8.0 and The Aether is updated to 2.0.0, the addon will have to update its dependency to 2.0.0.

### Minor Version Changes
These are signified by **additions** to a codebase that are backwards-compatible.

Examples:
- Adding new packages.
- Adding new public or protected methods, fields, or classes.
- Annotating a public or protected method, field, or class as deprecated.
- Changing the protection status of private methods, fields, or classes to protected or public.
- Changing or modifying private methods, fields, or classes as a part of adding some kind of new behavior.
- Changing the required Minecraft or Forge version.
- Adding mod features.
- Adding mod mechanics.

How this looks with versions is that if an addon is dependent on 1.8.0 and The Aether is updated to 1.9.0, the addon should still be compatible unless it uses the new additions.

### Patch Version Changes
These are signified by **internal fixes or changes** that are backwards-compatible.

Examples:
- Adding new private methods, fields, or classes.
- Changing the location of a private method, field, or class.
- Renaming private methods, fields, and classes.
- Deleting private methods, fields, and classes.
- Changing or modifying private methods, fields, or classes for the purpose of fixing incorrect behavior or improving existing code in the internal codebase.
- Changing or modifying the internals of a public or protected method for the purpose of fixing incorrect behavior or improving existing code in the internal codebase.
- Adding new methods regardless of protection if they’re annotated as a @SubscribeEvent, as these are not usable externally.

How this looks with versions is that if an addon is dependent on 1.8.0 and The Aether is updated to 1.8.1, the addon should still be compatible.