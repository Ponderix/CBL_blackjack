// Blackjack Game Pseudocode with Comments 

// Class: Card 
// Represents a card with value, type, and image. 
// Has methods to get value, check if it’s an Ace, and get image path. 

// Class: Person 
// Base class for both players and dealer. 
// Stores hand, hand value, and some basic methods for managing hands. 

// Class: Player (extends Person) 
// Represents a player. 
// Has methods for betting, hitting, and deciding when to stand. 

// Class: Dealer (extends Person) 
// Handles dealer-specific rules like drawing until 17+ and revealing hidden cards. 

// buildDeck() 
// Creates a deck of 52 cards with 4 suits and 13 values each. 
// Adds cards to a list. 

// shuffleDeck() 
// Shuffles the deck randomly. Uses simple swapping for shuffling. 

// startGame() 
// Sets up the game: builds the deck, shuffles it, and deals initial cards to both player and dealer. 

// hit() 
// Adds a new card to the player's hand. If the hand value is > 21, the player busts. 

// stay() 
// Player stops taking cards. Dealer starts playing according to its rules. 

// reduceAceValue() 
// Changes Ace values from 11 to 1 if the hand goes over 21. 

// calculateHandValue() 
// Sums up the hand value, handles Ace as 1 or 11 correctly. 

// main() 
// Runs the game loop and displays win/lose/tie results.