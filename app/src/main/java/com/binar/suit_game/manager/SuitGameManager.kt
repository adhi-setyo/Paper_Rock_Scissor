package com.binar.suit_game.manager

import android.graphics.drawable.AdaptiveIconDrawable
import com.binar.suit_game.R
import com.binar.suit_game.enum.GameState
import com.binar.suit_game.enum.PlayerCharacter
import com.binar.suit_game.enum.PlayerSide
import com.binar.suit_game.enum.PlayerState
import com.binar.suit_game.model.Player
import kotlin.random.Random

interface SuitGameManager {
    fun initGame()
    fun startOrReset()
    fun playerRock()
    fun playerPaper()
    fun playerScissor()
}

interface SuitGameListerner{
    fun onPlayerStatusChanged(player:Player, iconDrawableRes:Int)
    fun onGameChanged(gameState: GameState)
    fun onGameFinished(gameState: GameState, winner:Player)
}

class SuitGameManagerImpl(
    private val listener:SuitGameListerner
):SuitGameManager{

    private lateinit var playerOne:Player
    private lateinit var playerTwo:Player
    private lateinit var playerDraw:Player
    private lateinit var state: GameState

    override fun initGame() {
        setGameState(GameState.IDLE)
        playerOne = Player(PlayerSide.PLAYER_ONE,PlayerState.IDLE,PlayerCharacter.PAPER)
        playerTwo = Player(PlayerSide.PLAYER_TWO,PlayerState.IDLE,PlayerCharacter.PAPER)
        playerDraw = Player(PlayerSide.PLAYER_DRAW,PlayerState.IDLE,PlayerCharacter.PAPER)
        notifyPlayerDataChanged()
        setGameState(GameState.STARTED)
    }

    private fun notifyPlayerDataChanged() {
        listener.onPlayerStatusChanged(playerOne,
        getPlayerOneDrawableByState(playerOne.playerCharacter))
        listener.onPlayerStatusChanged(playerTwo,
        getPlayerTwoDrawableByState(playerTwo.playerCharacter))
    }

    private fun getPlayerTwoDrawableByState(playerCharacter: PlayerCharacter): Int {
        return when(playerCharacter){
            PlayerCharacter.ROCK -> R.drawable.ic_rock
            PlayerCharacter.PAPER -> R.drawable.ic_paper
            PlayerCharacter.SCISSOR -> R.drawable.ic_scissor
        }
    }

    private fun getPlayerOneDrawableByState(playerCharacter: PlayerCharacter): Int {
        return when(playerCharacter){
            PlayerCharacter.ROCK -> R.drawable.ic_rock
            PlayerCharacter.PAPER -> R.drawable.ic_paper
            PlayerCharacter.SCISSOR -> R.drawable.ic_scissor
        }
    }

    private fun setGameState(newGameState: GameState) {
        state = newGameState
        listener.onGameChanged(state)
    }

    override fun playerRock() {
        if (state!=GameState.FINISHED && playerOne.playerCharacter.ordinal > PlayerCharacter.ROCK.ordinal){
            val currentIndex = playerOne.playerCharacter.ordinal
            setPlayerOneCharacter(getPlayerCharacterByOrdinal(currentIndex -1))
        }
    }

    override fun playerPaper() {
        if (state!=GameState.FINISHED && playerOne.playerCharacter.ordinal > PlayerCharacter.PAPER.ordinal){
        val currentIndex = playerOne.playerCharacter.ordinal
        setPlayerOneCharacter(getPlayerCharacterByOrdinal(currentIndex))
    }
    }

    override fun playerScissor() {
        if (state!=GameState.FINISHED && playerOne.playerCharacter.ordinal > PlayerCharacter.SCISSOR.ordinal){
            val currentIndex = playerOne.playerCharacter.ordinal
            setPlayerOneCharacter(getPlayerCharacterByOrdinal(currentIndex +1))
        }
    }

    private fun getPlayerCharacterByOrdinal(index: Int): PlayerCharacter {
        return PlayerCharacter.values()[index]
    }

    private fun setPlayerOneCharacter(
        playerCharacter: PlayerCharacter = playerOne.playerCharacter) {
        playerOne.apply {
            this.playerCharacter = playerCharacter
        }
        listener.onPlayerStatusChanged(
            playerOne,
            getPlayerOneDrawableByState(playerOne.playerCharacter)
        )
    }

    private fun setPlayerTwoCharacter(
        playerCharacter: PlayerCharacter = playerTwo.playerCharacter) {
        playerTwo.apply {
            this.playerCharacter = playerCharacter
        }
        listener.onPlayerStatusChanged(
            playerTwo,
            getPlayerOneDrawableByState(playerTwo.playerCharacter)
        )
    }

    override fun startOrReset() {
        if(state !=GameState.FINISHED){
            startGame()
        }else{
            resetGame()
        }
    }

    private fun resetGame() {
        initGame()
    }

    private fun startGame() {
        playerTwo.apply {
            playerCharacter = getPlayerTwoPosition()
        }
        CheckPlayerWinner()
    }

    private fun CheckPlayerWinner() {
        val rock = PlayerCharacter.ROCK
        val paper = PlayerCharacter.PAPER
        val scissor = PlayerCharacter.SCISSOR

        if(playerOne.playerCharacter.ordinal == 0 && playerTwo.playerCharacter.ordinal == 2){
            setPlayerOneCharacter(playerCharacter = rock)
            setPlayerTwoCharacter(playerCharacter = scissor)
            playerOne
        }
        else if(playerOne.playerCharacter.ordinal == 1 && playerTwo.playerCharacter.ordinal == 0){
            setPlayerOneCharacter(playerCharacter = paper)
            setPlayerTwoCharacter(playerCharacter = rock)
            playerOne
        }
        else if(playerOne.playerCharacter.ordinal == 2 && playerTwo.playerCharacter.ordinal == 1){
            setPlayerOneCharacter(playerCharacter = scissor)
            setPlayerTwoCharacter(playerCharacter = paper)
            playerOne
        }
        else if(playerOne.playerCharacter.ordinal == 2 && playerTwo.playerCharacter.ordinal == 0){
            setPlayerOneCharacter(playerCharacter = scissor)
            setPlayerTwoCharacter(playerCharacter = rock)
            playerTwo
        }
        else if(playerOne.playerCharacter.ordinal == 1 && playerTwo.playerCharacter.ordinal == 2){
            setPlayerOneCharacter(playerCharacter = paper)
            setPlayerTwoCharacter(playerCharacter = scissor)
            playerTwo
        }
        else if(playerOne.playerCharacter.ordinal == 0 && playerTwo.playerCharacter.ordinal == 1){
            setPlayerOneCharacter(playerCharacter = rock)
            setPlayerTwoCharacter(playerCharacter = paper)
            playerTwo
        }
        else(
            playerDraw
        )
    }

    private fun getPlayerTwoPosition(): PlayerCharacter {
        val randomCharacter = Random.nextInt(0, until = PlayerCharacter.values().size)
        return getPlayerCharacterByOrdinal(randomCharacter)
    }


}
