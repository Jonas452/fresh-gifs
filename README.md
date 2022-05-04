## Instructions
- Android 7.0 or greater
- Rename the "base_apikey.properties" file to "apikey.properties" and add your giphy apikey in the file
- Build and run the app

## Know Issues
- When you remove a gif from favorite in the favorite screen it does not update the discover screen (this could be solved with the item two of Future Improvements)

## Future Improvements
- Cache the gifs in the local database using room, and use room as the single source of true of the data (this avoid bugs)
- Add UI tests and more unit tests
- Add animation when changing tabs or when adding a gif to favorite (like a different button with animation)
- Add infinite scroll using the Paging3 lib (right now is hardcoded to show only 20 items)