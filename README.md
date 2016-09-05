# card-generator

## Overview

This is a Java program that programmatically generates playing cards for use in Tabletop Simulator (TTS), but specifically to put into the TTS deck builder, as I found the deck builder to be a little bit unstable.

## How it works

It goes out to the folder I specify and reads all the files within, which are little tank/airplane/boat images like the 'tank.png' here. 

It will combine that with the `blank_template-400x563.png` and apply some text that it gets from the `cards.json` IF that tank image has a corresponding json entry. 

I got the tank images from several different mod packs for World of Tanks, ones that alter the garage images of the tanks. I've also grabbed some from World of Warplanes/Warships, which the cards will look essentially the same. I've also done a few renders in Blender of various free models I've found online to fill the non-WW2 gaps that I have, and I've been using Gimp to photoshop a few other cards from suitable pictures I find in various places.

## What the heck is this for?

I'm essentially making a bit of an alternate DnD race, set in a sci-fi setting. Sorta. It's still evolving but that's a good an explinating as I can muster up at this point. I'll be using the standard DnD rules (v5?) and cards as representation of enemies, as well as players (although that is still to be done). We'll see how well that goes for us...

At some point I'll add in the background I have as to who these new race are, but suffice to say they are human sized sentient machines, whom have a walking (human) form and a travel (vehicle) form. They are known as the Machina.

## The final product

Take a look at the oddly names `01x china-Ch01_Type59.png` to see what it's currently outputting. The `01x` is part of the TTS deck builder, telling the program how many of each card to add to the deck. 

## Disclaimer

The tank/airplane/boat/etc images, at least the vast majority are not mine, they belong to Wargaming in realation to the game World of Tanks. I'm just using them for this little personal project. 