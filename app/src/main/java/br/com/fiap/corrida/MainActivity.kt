package br.com.fiap.corrida

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
        })
    }
}
