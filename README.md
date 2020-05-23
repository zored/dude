# Dude
Autocomplete by name.

## Description
<-- info.ts.textFromXml(`README.md`, `//description[1]`) { -->
This IDEA plugin autocompletes names used in project.
- Of values based on type.
- Of type based on value name.
<-- } -->

## Implementation
- Create [index](./src/main/java/gl/ro/guess_idea/index) name-definition.
- Use it in [completions](./src/main/java/gl/ro/guess_idea/completion).