# Card Game (wip)
## General
The 'Card Game' (wip) project is an implemenation of a physical card game. The original was created by my brother when we were young.

The primary goals and features I am planning to test involve the User Interface and multiplayer networking for games. I am also hoping to have the art style to be based on the drawing on the original cards.

## Gameplay

The 'Card Game' (wip) is a turn-based two-player game. The closest relative could be the Pokemon card game. Each player has a deck of cards including creatures/items of different types and element [fire, water, etc].

At the start of each game, the decks for each player are shuffled then placed facedown at the edge of the play area. The play area contains three slots: one 'primary' slot and two 'waiting' slots below the primary slot. To begin, the players draw the top card of their decks and place it into the primary slot. The two waiting slots are also filled by drawing two cards. The winner of a coin toss (?) begins the game.



The steps for each turn are as follows:

  1. Beginning of turn events.
  2. Current player fills waiting slot with top card from deck.
  3. Player may use one power from one of his/her creatures in play.
  4. Player may switch primary creature with a waiting creature.
  5. Attack Phase
    1. Primary creature deals 'attack' damage to opposing primary creature.
    2. Opposing primary creature deals 'attack' damage.
  6. If player did not switch creatures at step 4, they may now.
  7. Player may use one power from one of his/her creatures in play **other than** the creature that used a power earlier this turn.
  8. End of turn events.
  9. Check if either player has no creatures in play.

The game ends when one player has no cards remaining in their play area after any end of turn events.

## User Interface

To preserve space, the design process and additional images of the User Interface have been moved to [README_UI]("README_UI.md").

For this project, the plan is to use Scene2D for constructing the user interface. For the overview of the game area including the side areas, there should be three sections laid out horizontally.

One goal of the User Interface is simplicity. The screen should not be messy or disorganized. All the necessary information should be displayed and additional information can appear as requested. For example, if the mouse is held over a card's power or keyword, then some form of tooltip or "Description" window should become visible, yet those details are not needed to be permanently visible.

An example of the current User Interface style is displayed below.

#### User Interface - Sample Preview
<img src="screenshots/cardgame-infographic-4.png" alt="Card Game sample view layout version 4" width="512"/>

## Card Design

### Function

The functional abilities and interactions of the cards will be similar to the original card game. However, the original game will not be a total limiting factor to the design of the cards' functions.

#### Powers and Abilities
Creature cards may have **powers** and **abilities**.

  * **Powers**

  Powers are activated skills/functions that may cost a certain number of the controlling player's power tokens. Most powers can be used once each turn, however they may be some more interesting powers that can only be used once per game. It may be necessary to label the per-turn and per-game powers separately, such as "power" vs. "superpower" (tbd). To activate a power, the player must first click on the creature and then click on the power. Once the power is used, it will be grayed out until it can be used again (next turn or next game).

  Examples of powers include:
   * Power [1]: Deal 2 damage to one enemy creature.
   * Power [2]: Switch opponents primary creature with a waiting creature of your choice.
   * Power [1]: Gain 2 power tokens
   * Power [4]: Sacrifice *this creature*: Kill your opponent's primary creature.
   * Super Power(tbd) [5]: Switch your opponent's primary creature with a waiting creature of your choice. Deal 5 damage to both of those creatures.

  * **Abilities**

  Abilities are passive skills that may trigger automatically and do not require manual activation by the player. The abilities can be triggered at the start of the turn, end of the turn, when the creature attacks, or any other sort of trigger. In order for the player to know when a power is triggered, the card may be displayed or enlarged in some manner to make it obvious (similar to Hearthstone displaying playing cards).

  Examples of abilities include:
   * Ability: Whenever *this creature* is dealt damage, deal 1 damage to all other creatures.
   * Ability: If *this creature* is your only creature in play at the beginning of your turn, *this creature* gains 5 Life.
   * Ability: When *this creature* is dealt damage, you *may* have another creature gain that much Life.
   * Ability: When *this creature* is put into the graveyard, you *may* switch your opponent's primary creature with a waiting creature of your choice.
   * Ability: At the beginning of your turn if *this creature* is in a waiting slot, gain 1 Power token.


### Aesthetics

The visual layout and design of the cards will be based on the layout and design from the original card game. Below is a sample layout based on the original cards.

#### Design Version 1
<img src="screenshots/card-design-1.png" alt="Card Design version 1" width="256"/>

Some of the features in this card layout can be changed or updated, but this is the same layout as the original game.

First, each card has a name and a type. The name is unique to the card. The type, drawn as a colored circle, is the element type of the card. The list of element types include fire, water, earth, and normal (which is a non-elemental type). The possible element types can also be updated to include additional types. Further, it could be necessary or useful to have creature types, such as Eggs, Dragons, or Humans. The original game had a general sense of these types but were not directly referenced. Including the explicit creature type of the cards could add more abilities or synergies.

Below the creature name is the creature image. The art for the original card game was drawn in a very simple art style using colored pencils. It would be interesting to first, include the original card art and second, base all future card art and designs with the colored pencil artwork. The  images would be drawn by hand and then scanned in to be including into the game.

Each creature will have a starting Life amount and an Attack value. The Attack is the amount of damage the creature will deliver to the opposing primary creature. During the Attack Step, each primary creature deals their attack damage to the opposing primary creature. The Life denotes how much damage the creature can take before it dies. When a creature dies, it is immediately placed into the owner's graveyard. However, if the creature that dies is in the primary slot, the waiting creatures do not move up until the owner is able to fill the slot. Some creatures may have an Attack value of zero, so they will deal no damage when attacking.

At the bottom of the card a list of any powers, abilities, or keywords will be displayed. This may show a brief description of the power or at least the cost and/or trigger event. A full description does not need to be displayed on the card. Instead, upon user event a tooltip or description window can appear to explain additional information for the card and its connected powers and abilities.
