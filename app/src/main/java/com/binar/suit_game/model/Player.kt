package com.binar.suit_game.model

import com.binar.suit_game.enum.PlayerCharacter
import com.binar.suit_game.enum.PlayerSide
import com.binar.suit_game.enum.PlayerState

data class Player(
    val playerSide: PlayerSide,
    var playerState: PlayerState,
    var playerCharacter: PlayerCharacter
)
