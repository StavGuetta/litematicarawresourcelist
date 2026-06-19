# Litematica Raw Resource List

A client-side-only Fabric mod that reads Litematica schematics to help builders plan material requirements in-game. This works directly by parsing `.litematic` files—no external websites or spreadsheets required, and no server component.

## Constraints & Rules
- **Java**: 21+
- **Minecraft Version**: 26.2
- **Fabric Loader & API**: Latest stable (Loader `0.19.3`, API `0.152.2+26.2`)
- **Mappings**: Use Mojang's official mappings (`loom.officialMojangMappings()`), NOT Yarn. Note that the Fabric Loom plugin ID changed accordingly for 26.1+.
- **Architecture**: The mod must work standalone. It reads `.litematic` files directly. However, it should optionally detect and use Litematica's API/data if Litematica is also installed. Do not hard-require Litematica as a dependency.
- **Reference**: The `reference/legacy-webapp/` folder contains a legacy web implementation. It is for behavioral reference only. Do not port it line-by-line and do not include it in the build.
