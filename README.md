[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/iDQJgb-p)


- main.java

- controller/
  - auth/
    - LoginCommand.java
    - RegisterCommand.java
    - AuthController.java
  - menu/   
    - MainMenuController.java
    - LoginMenuController.java
    - RegisterMenuController.java
    - ProfileMenuController.java
    - etc
  - time/
    - TimeController.java
    - etc
  - skill/ 
    - SkillController.java
    - etc
  - etc
  
- model/
  - result/
    - Result<T>.java // T -> Data, private constructor, public static methods
    - error/
      - enum/
        - AuthError.java // implements Error
        - UserError.java // implements Error
        - GameError.java // implements Error
        - etc
      - Error.java //Interface
  - user/
    - User.java
    - UserSession.java // The user that is playing is stored here
    - etc
  - game/
    - GameSession.java // The game that is being played is stored here
    - Game.java // The game object that is being played is stored here
  - time/
    - enum/
      - Season.java
      - DayOfWeek.java
    - Time.java // The time object
    - TimeSession.java // The time of the game is stored here
  - skill/
    - enum/
      - SkillType.java
    - Skill.java // The skill object
    - SkillSession.java // The skills of the game is stored here
  - item/
    - enum/
      - ItemType.java
    - Item.java // The item object
    - ItemSession.java // The items of the game is stored here
  - world/
    - tile/
        - enum/
            - TileType.java
        - Tile.java
    - world/
        - World.java
        - WorldSession.java
    - rooms/
        - enum/
            - RoomType.java
        - Room.java
        - RoomSession.java // current room
  - menu/
    - enum/
        - MenuType.java
        - LoginMenuCommand.java
        - RegisterMenuCommand.java
        - MainMenuCommand.java
        - ProfileMenuCommand.java
        - etc
    - etc
- view/
  - menu/
    - Menu.java // Interface -> ErrorHandler, SuccessHandler
    - MainMenuView.java
    - LoginMenuView.java
    - RegisterMenuView.java
    - ProfileMenuView.java
    - CommandLineInterfaceView.java
    - etc
  - etc
- etc

TODO:
- [ ] Learn how to use Git for multiple people
- [ ] Add GameSave Logic
- [ ] Add DataBase Logic
- [ ] Add Recepies (Remember to use Json for Recepie)
- [ ] Add Inventory System
- [ ] Add Cooking System
- [ ] Add Crafting System
- [ ] Add NPC System
- [ ] Add Shop System
- [ ] Add Event System
- [ ] Add Achievement System
- [ ] Check Phase1.pdf again


## RECEPIE:
```json
// recipes/cooking.json
[
    {
        "id": "fruit_salad",
        "name": "Fruit Salad",
        "ingredients": [
            {"item": "apple", "quantity": 1},
            {"item": "orange", "quantity": 1},
            {"item": "blueberry", "quantity": 2}
        ],
        "result": "fruit_salad",
        "energyRestored": 50,
        "healthRestored": 30,
        "requiredSkill": "COOKING",
        "requiredSkillLevel": 2
    }
    // ... other recipes
]
```

## Result:
all functions should return Result<T>
Result<T> has two states:
- Success
- Error

Success has a value of type T
Error has a value of type Error

Error is one of the enums like AuthError, UserError, GameError, etc.
So code will look like this:
```java
Result<T> result = functionThatReturnsResult();
if (result.isError()) {
    return Result.failure(result.getError());
} else {
    T value = result.getValue();
    //handle logic...
}
```