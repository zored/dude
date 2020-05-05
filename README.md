# Dude
**Dude** (stands for "duplicate declarations") is IntelliJ IDEA plugin which autocompletes your duplicate declarations (and even definitions!).

It will autocomplete for you:
- Variable and field definition with visibility: `var user *entities.User`.
- Type name: `type Person struct {}`.
- Abstract and specific function definitions: `func FindById(id entities.Id) {}`.

Supported languages:
- GoLang.

## What IDE does?
- Creates [index](./src/main/java/gl/ro/guess_idea/index) for all type-value pairs.
- Uses this index in [completion](./src/main/java/gl/ro/guess_idea/completion).

## Dev notes
- This is Kotlin Jetbrains Intellij IDEA plugin example made with [actual advices](https://www.jetbrains.org/intellij/sdk/docs/basics/basics.html) on their site.
- There are some heuristic ways for plugin development inspiration:
  - Official docs.
  - Other plugins.
  - Debugger (for example, you can use `"Find in path..."` and use some unique string from the UI).