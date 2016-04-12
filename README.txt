MINOTAUR by EstrelSteel
version 1.2c-Gold
build 45

release date: 03.04.2016

###

Development info:
	Derived from: Engine1 (build 6), Chatroom (build 3)
	Period: 91 Days


###

Controls:
	Use "WASD", "OKL;", or Arrow Keys to move
	Use [Space] to attack
	Use [1], [2], [3], [4], and [5] for secondary actions
	
	Use the mouse to select and navigate menus
	
###

Administrator/Host Commands:
	force <map | gm | minotaur | start> [id | player]
		description: chooses map or game mode
		examples:
			“force map 0” / selects the map Mines to play
			“force gm 0” / selects the game mode Classic
			"force minotaur Test" / makes player Test the minotaur
			"force minotaur " / makes the minotaur a random player
			"force start" / starts the game immediately 
	
###

Maps:
	NAME		| ID
	-----------	|----
	INVALID		| -2
	LOBBY		| -1
	MINES		| 0
	ISLAND		| 1
	ISLAND_LOOP	| 2
	SAND_BAR	| 3
	ZIG_ZAG		| 4
	
###

Gamemodes:
	NAME	| ID
	-------	|---
	CLASSIC	| 0
	REVERSE	| 1