# Architecture.md

---

#####Description:
The app has 3 activities: `DisplayMemeActivity`, `ExploreActivity`, `FavouritesActivity`. `DisplayMemeActivity` is for viewing a single meme in isolation. `ExploreActivity` is for scrolling through a selection of memes. `FavouritesActivity` is for scrolling through a selection of memes. Memes that show up in the `FavouritesActivity` are ones that users favorourite.
The Persistence layer has 2 interfaces: `ExploreActivity` and `TagsPersistence`. The database is accessed through these interfaces. 
Four domain objects exist, but they are all relevant to the `Meme` object. `Meme` is an `Abstract` class, which contains meta data. `Meme` is extended by the `ImageMeme` object. We chose this abstraction so that different meme formats can be supported in future iterations. `Author` and `Tag` are domain objects, included in the meta data of the `Meme` class. Meme creation will be implemented in a future iteration. The code will primarily sit in the Presentation layer. It will have minimal presence in the Business layer. And for the Persistence layer, the user created memes will be added to the database.

---
#####Diagram:
![ArchitectureDiagram not found](./ArchitectureDiagram.png)
