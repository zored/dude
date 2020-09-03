# Dev Notes

## Implementation
On IDE start generate [index](./src/main/java/gl/ro/dude/jetbrains/index) of values. This index is used in [completions](./src/main/java/gl/ro/dude/jetbrains/completion).

## Dictionary
Assuming this code:
```kotlin
val age: Integer
```

- **Value** - is `age: Integer`.
  - **Name** is `age`.
  - **TypeName** is `Integer`. This is explicit value type name. 
  - **Type** is *variable*. This type is to separate completion targets.