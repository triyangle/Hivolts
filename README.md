# Hivolts

Authors: Albert Ford, Kevin Li, William Yang

### Introduction

The purpose of this project is to reimplement the Hivolts game. 

### Description

The specification of this project was to reimplement the Hivolts game with updated graphics. We have successfully reproduced the game to play like the original as according to the specifications on paleyontology.com. We have designed new and updated graphics to meet the criterion. In addition, the game features were updated to include variable grid size, allowing the player to play on a bigger board. 

### Current Errors
There are no current errors in the game. There was an error with the varible grid size, in which a grid size that wasn't a square wasn't working, but the issue was resolved as of 10/19/2015. 


### Code Overview

 * The Main class initializes the JFrame and loads the game. In addition, it creates a new JFrame providing instructions for the game. 
 * The Grid class initializes all the game objects, loading sprites from files and placing them on the grid, and manages the player controls and turn system. 
 * The Entity class is a superclass that contains attributes common to Mhos, Fences, and Players as game objects on the grid, such as position, as well as the getter and setter methods for position. It also contains a basic draw class that draws game entities as solid colors, which is overridden by the subclass game objects. 
 * The Cell class initializes a cell on the grid, which can be either a fence of not. 
 * The Fence class initializes fence objects, which each have a position attribute as inherited from Entity, and its own draw method. 
 * The Mho class initializes Mho objects, which each have a position attribute as inherited from Entity, and its own draw method. It also contains the AI for Mho movement, as according to the paleyontology website. It has methods for moving based on player position, determining whether such a movement will land on a fence, and to remove it from the game after it dies. It also has a method declaring the end of the Mhos' turn to move. 
 * The DeadMho class replaces all Mhos that have been removed from the grid with a sprite of a dead Mho. 
 * The Player class initializes the player, which has a position attribute as inherited from Entity, and its own draw method. In addition, it has a method detecting whether it has moved or jumped on top of a Mho or fence, and if so, to end the game with a status message, and a method that declares the end of the player's turn to move. 

### Major Challenges
One challenge that was faced was implementing a draw method that imported images in the Entity superclass that could be inherited to all entities. This created conflicting images as it treated the imported sprite as part of Entity, and thus all game objects had to share a sprite. We overcame this by writing a specific draw method for each game object. 
Another challenge we faced was the random placement of the game objects. Originally the idea was to just place entities in the grid randomly. On Mr. Kuszmaul's suggestion, we developed a more efficient method of placing mhos. Our implementation differed from the one suggested by Mr. Kuszmaul, as described in the [RandomPlacement](https://github.com/as-f/RandomPlacement) project.

### Credits
Group members were Kevin Li, Albert Ford, and William Yang. Original basis for the grid and display were taken from William Yang's Conway's Game of Life project. Graphics were drawn and implemented by Kevin Li. Object placement, optional player controls, and turn system were implemented by Albert Ford. Player control, player death detection, and polishing were done by William Yang. Game messages were done by Kevin Li. 

### Acknowledgements
We would like to thank Chris Kuszmaul and Ben Cohen-Wang for giving us guidance for the implementation of random placement, and Lilia Tang, Nathan Kau, and William Meng for moral support. 
