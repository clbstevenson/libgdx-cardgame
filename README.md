# Card Game (wip)
## General
The 'Card Game' (wip) project is an implemenation of a physical card game. The original was created by my brother when we were young. 

The primary goals and features I am planning to test involve the User Interface and multiplayer networking for games. I am also hoping to have the art style to be based on the drawing on the original cards. 

## Gameplay

The 'Card Game' (wip) is a turn-based two-player game. The closest relative could be the Pokemon card game. Each player has a deck of cards including creatures/items of different types and element [fire, water, etc].

At the start of each game, the decks for each player are shuffled then placed facedown at the edge of the play area. The play area contains three slots: one 'primary' slot and two 'waiting' slots below the primary slot. To begin, the players draw the top card of their decks and place it into the primary slot. The two waiting slots are also filled by drawing two cards. The winner of a coin toss (?) begins the game. 

Below is a preliminary image of the proposed layout for the gameplay areas.

<img src="screenshots/cardgame-infographic-1.png" alt="Card Game sample view layout" width="600"/>

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
 
