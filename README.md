# Guess Idea
IntelliJ IDEA plugin.

Supported languages:
- GoLang.

## What it does?
- Autocomplete default name by type and vice versa.
- Defaults are taken from project names and types.


## What IDE does?
- Creates [index](./src/main/java/gl/ro/guess_idea/index) for all type-value pairs.
- Uses this index in [completion](./src/main/java/gl/ro/guess_idea/completion).

## Dev notes
- This is Kotlin Jetbrains Intellij IDEA plugin example made with [actual advices](https://www.jetbrains.org/intellij/sdk/docs/basics/basics.html) on their site.
- There are some heuristic ways for plugin development inspiration:
  - Official docs.
  - Other plugins.
  - Debugger (for example, you can use "Find in path..." and use some unique string from UI).