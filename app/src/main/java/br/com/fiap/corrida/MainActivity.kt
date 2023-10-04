package br.com.fiap.corrida

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.fiap.corrida.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)


        binding.buttonIniciar.setOnClickListener {
            viewModel.startRace()
        }

        binding.buttonParar.setOnClickListener {
            viewModel.stopRace()
        }

        binding.buttonRestart.setOnClickListener {
            viewModel.clearTextWinner()
            viewModel.resetProgressAllHorses()
        }

        viewModel.progressRed.observe(this, Observer { progress ->
            binding.progressRed.setProgressCompat(progress, true)
        })

        viewModel.progressGreen.observe(this, Observer { progress ->
            binding.progressGreen.setProgressCompat(progress, true)
        })

        viewModel.winnerText.observe(this, Observer { winner ->
            binding.tvWinner.text = winner
            binding.tvWinner.visibility = if (winner.isNotEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }

            val winningHorseColor = when(winner){
                "Winner: Red Horse" -> "#FF0000"
                "Winner: Green Horse" -> "#00FF00"
                else ->  "#000000"
            }
            binding.tvWinner.setTextColor(Color.parseColor(winningHorseColor))
        })
    }
}
