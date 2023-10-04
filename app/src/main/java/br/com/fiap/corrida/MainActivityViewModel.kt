package br.com.fiap.corrida

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.random.nextInt

class MainActivityViewModel : ViewModel() {
    private val _progressRed = MutableLiveData<Int>(0)
    val progressRed: LiveData<Int>
        get() = _progressRed

    private val _progressGreen = MutableLiveData<Int>(0)
    val progressGreen: LiveData<Int>
        get() = _progressGreen

    private val _winnerText = MutableLiveData<String>()
    val winnerText: LiveData<String> get() = _winnerText

    private var raceJob: Job? = null

    private fun getRandomProgress(): Int = Random.nextInt(0, 20)

    fun startRace() {

        raceJob = viewModelScope.launch(Dispatchers.IO) {
            while (progressRed.value!! < 100 || progressGreen.value!! < 100) {
                val newRedValue = _progressRed.value?.plus(getRandomProgress())
                _progressRed.postValue(newRedValue!!)
                if (newRedValue >= 100) {
                    _winnerText.postValue("Winner: Red Horse")
                    break
                }

                var newGreenValue = _progressGreen.value?.plus(getRandomProgress())
                _progressGreen.postValue(newGreenValue!!)
                if (newGreenValue >= 100) {
                    _winnerText.postValue("Winner: Green Horse")
                    break
                }

            }
        }

    }

    fun stopRace() {
        raceJob?.cancel()
    }

    fun resetProgressAllHorses() {
        _progressRed.value = 0
        _progressGreen.value = 0
    }
}
