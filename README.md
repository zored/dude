# Dude
Autocomplete by name.

## Description
**Dude** (stands for "duplicate declarations") is IntelliJ IDEA plugin.

It autocompletes your named declarations.



Works with:
- Values:
    ```go
    var user *entities.User
    ```
- Types (and fields):
    ```go
    type Person struct { name string; }
    ```
- Functions (and parameters):
    ```go
    func FindById(id entities.Id) (err error)
    ```
- Imports:
    ```go
    import myEntities "app/entities"
    ```

Supported languages:
- GoLang.

## Implementation
- Create [index](./src/main/java/gl/ro/guess_idea/index) name-definition.
- Use it in [completions](./src/main/java/gl/ro/guess_idea/completion).