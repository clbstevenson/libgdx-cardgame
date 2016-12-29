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

## User Interface

For this project, the plan is to use Scene2D for constructing the user interface. For the overview of the game area including the side areas, there should be three sections laid out horizontally. For reference, below is a sample layout for the user interface.

<img src="screenshots/cardgame-infographic-1c.png" alt="Sample Layout with highlighted columns left, middle, and right" width="600"/>

 1. **Left Section**
 
  The first section will be the left-section containing the two players' decks and discard piles. The player's own deck/discard piles will be at the bottom of this section, with their opponents displayed at the top.
 
  Whenever the player needs to draw a card from their deck (start of the game, filling an empty play area slot, etc) an animation will play for moving the card from the top of the deck to the destination. An idea would also to be rotating the card to "flip" it over to display the card. Perhaps to achieve this, the deck of cards could be physical Card objects that can be translated and thus "animated" as being flipped.
 
  The number of cards in each player's decks and discard piles should be displayed next to each pile.
 
  Also, player names could probably be displayed in the Left Section.
 
 2. **Middle Section**
 
  The second section is the middle-section containing the cards currently in play. The primary creature slot for each player is centered in the frame and close to the player-opponent divide. Two slots for the waiting area creature cards are below the primary slot.
 
  There is a possible issue with this layout. The pyramid style layout of the primary and the waiting area cards leaves some extra "white space" to the left and the right of the primary cards. This is less than ideal, but maybe it'd be nice to reduce UI overload. 
 
  Any creature animations, such as attacking or using their powers, will be displayed in this section as well. This may not really be a relevant point as any Entity System will be processing this, not the UI, but it is nice to know what will be happening in each section. 
 
 3. **Right Section**
 
  The third section is the right-section containing other player data. The main focus is displaying the number of "power tokens" remaining for the player. The tokens are used up whenever a power is activated from one of their creatures in play. To display the number of tokens, either a visible image can be drawn, or just a single image with a text counter. The latter may be preferable because a.) easier to add and remove tokens and b.) easier scaling or increasing total tokens for balancing, such as a higher starting count.
 
  Another element to be displayed is possibily "elemental power tokens", such as fire, water, and earth. This is something that is still a work in progress. Originally, these elemental tokens could be added to creatures to boost their attack. This may still be a viable option for gameplay. Also, another option for these tokens would be similar to the standard "power tokens". They could be used up to activate certain abilities. In this way they would be treated like mana from Magic: The Gathering.
  
  Other data or aspects can also be displayed in this section because of some extra space should the tokens can be reduced to labels and counters.
 
