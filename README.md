# Java_SpaceGame
All images and almost all of the code is my own. 
Credit to https://zetcode.com/javagames/ tutorials for the basic gameloop code. I used the basic code from their game tutorials as the bones for mine in the gameLauncher.java file. 

Not that it would make any money, but this game is free and open, made purely for my own education and enjoyment. It uses a few lines of important code I didn't make anyways, and I like to cover my bases to make sure I don't get in trouble. 

This is a java game I made in my free time using Swing components. It's very basic, and I would do it very differently if I made this again. I'll update until I get bored and drop the project altogether. 

My biggest issue with how I coded this was that I did the absolute stupidest thing, and decided to put 85% of the important code in the screenpaint class, which extends JPanel. This means any code in the repaint class is limited to itself and anything passed into it, isolating all of my game code from the rest of my classes. How I should've done the code was have everything done in a game running class, which passes a RenderableObject ArrayList into the screenpainter to iterate through each call. 
