# Info
Here is some info about some IDE features.

## Copy-paste and imports
When you copy entity and paste it in another place IDE handles all imports for you. Here how it does it:
- You copy text.
- Copy handler stores transferrable data from file in clipboard. Data includes imports. (See `CopyHandler.execute`.)
- ...
