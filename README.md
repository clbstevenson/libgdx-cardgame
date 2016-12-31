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

For this project, the plan is to use Scene2D for constructing the user interface. For the overview of the game area including the side areas, there should be three sections laid out horizontally. For reference, below is a sample layout for the user interface.

Below are some preliminary images of the proposed layout for the gameplay areas.

#### Layout Version 1
<img src="screenshots/cardgame-infographic-1.png" alt="Card Game sample view layout" width="480"/>

#### Layout Version 2
<img src="screenshots/cardgame-infographic-2.png" alt="Card Game sample view layout version 2" width="480"/>

#### Layout Version 3
<img src="screenshots/cardgame-infographic-3.png" alt="Card Game sample view layout version 3" width="480"/>


### Frame Analysis of Version 3
Update: The current version to be used for the layout is **Version 3**. This version includes an updates right-side section for displaying the current step in the turn. The other main aspect is that when a card is viewed, the contents will be displayed on the bottom right with more details in addition to the card scaling slightly.

<!--<img src="screenshots/cardgame-infographic-1c.png" alt="Sample Layout with highlighted columns left, middle, and right" width="600"/>-->

<img src="screenshots/cardgame-infographic-3b.png" alt="Sample Layout version 3 with highlighted frames" width="600"/>

 1. **Frame 1** - Decks and Discard Piles
   
    The first section will contain the two players' decks and discard piles. The player's own deck/discard piles iwll be at the bottom of this section, with their opponents displayed at the top.

    Whenever the player needs to draw a card from their deck (start of the game, filling an empty play area slot, etc) an animation will play for moving the card from the top of the deck to the destination. An idea would also to be rotating the card to "flip" it over to display the card. Perhaps to achieve this, the deck of cards could be physical Card objects that can be translated and thus "animated" as being flipped.
 
    The number of cards in each player's decks and discard piles could be displayed next to each pile.
 
    Also, player names could probably be displayed in *Frame 1*.
  
 2. **Frame 2** - Play Area or Battlefield
 
    The second section is the middle-section containing the cards currently in play. The primary creature slot for each player is centered in the frame and close to the player-opponent divide. Two slots for the waiting area creature cards are below the primary slot.
   
    When a card is mousedover or touched, the card will scale slightly. The contents of the card will be shown in *Frame 5* along with an explanation of any abilities, keywords, or powers. The scaling may cause an issue with overlap with nearby elements, so this should be considered and checked.
   
    Any card animations, such as attacking or using their powers, will be displayed in this section as well. 
   
    The remaining number of power tokens and elemental tokens will also be displayed in *Frame 2*. They will be shown most likely as a text counter for each type to reduce space.
   
 3. **Frame 3** - Match Log
  
    A small frame in the top left will display the actions of the game. Events in the log can include: drawing cards into play, activating powers, attacking, dealing damage, and using elemental or power tokens. The size of this frame is currently set as 1/3rd of the column, however it could be reduced because it is just some lines of text.
    
 4. **Frame 4** - Turn Tracker
  
    In *Frame 4*, the list of steps for each turn as displayed. The current step for each player will be highlighted. It may be necessary for some turns to require approval by the player to continue. In these cases, a button with a countdown timer can be displayed along the bottom of *Frame 4*.
    
 5. **Frame 5** - Mouseover Contents
  
    The fifth frame will be used to show the contents of cards that are touched or moused over. It will also provide some explanation for abilities, powers, and keywords to assist the players. The image of the card could be ignored, but it would be a nice touch. 
    
    This frame will need to be examined so that enough space is provided for explanations.
    
    One note to consider is if events in the log should trigger an update to *Frame 5*. It would be nice, but not immediately necessary.
