# Litematica Raw Resource List: Core Architecture

This document explains the core data model and how the different interfaces fit together to calculate raw resource requirements. It is written with a focus on clean separation of concerns.

## A Quick Note on Java Idioms

If you're coming from Python, JavaScript, or C, you'll see a few modern Java constructs in the API:
- **`record`**: A concise way to define immutable data classes. Think of it like a `namedtuple` in Python or a pure `struct` in C, where the compiler automatically generates the constructor, getters, `equals()`, and `hashCode()`.
- **`sealed interface`**: This restricts exactly which classes/records are allowed to implement the interface (using the `permits` keyword). This is very similar to an Algebraic Data Type or a union type in TypeScript (e.g., `type Node = Leaf | Composite`). It guarantees that downstream logic only ever has to handle `Leaf` or `Composite` nodes, and nothing else.

## Core Concepts

### 1. SchematicSource
We need to know what blocks to count. Since Litematica might not be installed (or we might want to read `.litematic` files from disk), we use `SchematicSource`. This interface hides *how* the blocks were retrieved. It simply outputs a `Map<BlockState, Integer>` containing normalized block counts.

### 2. RecipeResolver and MaterialNode
To figure out how many raw logs make 100 chests, we have to recursively break items down. 
- `RecipeResolver` is responsible for querying Minecraft's recipe registry (or modded registries) to find out how an item is made.
- It returns a `MaterialNode`. A node is either a **`Leaf`** (an uncraftable raw material like Iron Ore or Oak Log) or a **`Composite`** (a crafted item with ingredients and a recipe yield).

### 3. SurvivalObtainability
Not all items in a schematic can be acquired in survival (e.g., Command Blocks, Spawners). `SurvivalObtainability` acts as our classifier. It defaults to checking if an item is in a creative tab, but also reads an external JSON deny-list. This lets players customize edge-cases without needing to recompile the mod.

### 4. MaterialAggregator
This is the engine that pulls it all together. It takes a `SchematicSource`, converts the blocks to items, walks the recipe tree for each required item using the `RecipeResolver`, and flattens everything into a final count of raw materials. To avoid computing everything twice, it returns an `AggregationResult` containing both the "All Materials" view and the "Survival-Friendly Only" view (filtered using `SurvivalObtainability`).

### 5. ProgressTracker
In the future, we want to subtract blocks that the player has already placed. `ProgressTracker` provides an extension point for this. Right now, it has a default no-op implementation that simply returns the full schematic counts, ensuring our current data model doesn't need to be rewritten when this feature arrives.
