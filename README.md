# <!-- info.ts.textFromXml("src/main/resources/META-INF/plugin.xml", "//name[1]") { -->Dude<!-- } -->
Autocomplete by name.

## Description
<!-- info.ts.textFromXml("src/main/resources/META-INF/plugin.xml", "//description[1]") { -->This IDEA plugin autocompletes names used in project.
        - Of values based on type.
        - Of type based on value name.<!-- } -->

## Implementation
- Create [index](./src/main/java/gl/ro/guess_idea/index) name-definition.
- Use it in [completions](./src/main/java/gl/ro/guess_idea/completion).

## Change notes
<!-- info.ts.textFromXml("src/main/resources/META-INF/plugin.xml", "//change-notes[1]") { -->1.0.0
        - First version.
        - Change notes must be at least 40 symbols length.<!-- } -->

> Plugin ID: <!-- info.ts.textFromXml("src/main/resources/META-INF/plugin.xml", "//id[1]") { -->gl.ro.dude<!-- } -->