package com.binar.suit_game.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.binar.suit_game.R
import com.binar.suit_game.databinding.ActivityGameBinding
import com.binar.suit_game.enum.GameState
import com.binar.suit_game.manager.SuitGameListerner
import com.binar.suit_game.manager.SuitGameManager
import com.binar.suit_game.manager.SuitGameManagerImpl
import com.binar.suit_game.model.Player

class GameActivity : AppCompatActivity(), SuitGameListerner {

    private val binding:ActivityGameBinding by lazy {
        ActivityGameBinding.inflate(layoutInflater)
    }

    private val suitGameManager: SuitGameManager by lazy {
        SuitGameManagerImpl(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        setOnClickListener()
        suitGameManager.initGame()
    }

    private fun setOnClickListener() {
        binding.ivRockLeft.setOnClickListener{
            suitGameManager.playerRock()
        }
        binding.ivPaperLeft.setOnClickListener{
            suitGameManager.playerPaper()
        }
        binding.ivScissorLeft.setOnClickListener{
            suitGameManager.playerScissor()
        }
        binding.ivReset.setOnClickListener{
            suitGameManager.startOrReset()
        }
    }

    override fun onPlayerStatusChanged(player: Player, iconDrawableRes: Int) {
        setChooseCharacter(player,iconDrawableRes)
    }

    private fun setChooseCharacter(player: Player, iconDrawableRes: Int) {
    }

    override fun onGameChanged(gameState: GameState) {
    }

    override fun onGameFinished(gameState: GameState, winner: Player) {
    }
}