# Performance Guidelines

- **Schematic Size**: Schematics can contain millions of blocks.
- **Execution**: Any full-schematic scan must be chunked or executed asynchronously.
- **Threading**: Scanning logic must **never** block the client render thread.
