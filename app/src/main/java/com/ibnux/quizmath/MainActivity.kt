package com.ibnux.quizmath

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ibnux.quizmath.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var textToSpeech: TextToSpeech
    private lateinit var binding: ActivityMainBinding
    val emojis = arrayOf(
        "\uD83D\uDC12",
        "\uD83E\uDEBF",
        "\uD83D\uDC25",
        "\uD83E\uDD86",
        "\uD83E\uDD85",
        "\uD83E\uDD89",
        "\uD83E\uDD87",
        "\uD83D\uDC17",
        "\uD83D\uDC22",
        "\uD83D\uDC1F",
        "\uD83D\uDC05",
        "\uD83D\uDC06",
        "\uD83E\uDD93",
        "\uD83E\uDD8D",
        "\uD83E\uDDA7",
        "\uD83D\uDC18",
        "\uD83E\uDD9B",
        "\uD83D\uDC2A",
        "\uD83D\uDC2B",
        "\uD83E\uDD92",
        "\uD83E\uDD98",
        "\uD83E\uDD8F",
        "\uD83E\uDDAC",
        "\uD83D\uDC03",
        "\uD83D\uDC02",
        "\uD83D\uDC04",
        "\uD83E\uDECF",
        "\uD83D\uDC0E",
        "\uD83D\uDC16",
        "\uD83D\uDC0F",
        "\uD83D\uDC11",
        "\uD83E\uDD99",
        "\uD83D\uDC10",
        "\uD83E\uDD8C",
        "\uD83D\uDC15",
        "\uD83D\uDC29",
        "\uD83E\uDDAE",
        "\uD83D\uDC15\u200D\uD83E\uDDBA",
        "\uD83D\uDC08",
        "\uD83D\uDC13",
        "\uD83E\uDD83",
        "\uD83E\uDDA4",
        "\uD83E\uDD9A",
        "\uD83E\uDD9C",
        "\uD83E\uDDA2",
        "\uD83E\uDDA9",
        "\uD83D\uDD4A️",
        "\uD83D\uDC07",
        "\uD83E\uDD9D",
        "\uD83E\uDDA8",
        "\uD83E\uDDA1",
        "\uD83E\uDDAB",
        "\uD83E\uDDA6",
        "\uD83E\uDDA5",
        "\uD83D\uDC01",
        "\uD83D\uDC00",
        "\uD83D\uDC3F️",
        "\uD83E\uDD94"
    )
    var hasil: Int = 0
    var emoji: String = ""
    private var timer : CountDownTimer? = null
    var count: Int = 61

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        textToSpeech = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                Log.d("TextToSpeech", "Initialization Success")
            } else {
                Log.d("TextToSpeech", "Initialization Failed")
            }
        }

        binding.txtJawaban.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                jawab()
            }
            true
        }

        binding.txtBenar.setText(Prefs.benar.toString())
        binding.txtSalah.setText(Prefs.salah.toString())
        binding.seekLevel.progress = Prefs.level

        binding.btnMulai.visibility = View.VISIBLE
        binding.txtJawaban.visibility = View.GONE

        binding.seekLevel.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // TODO Auto-generated method stub
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // TODO Auto-generated method stub
            }

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                var nilai = binding.seekLevel.progress
                if(nilai==0){
                    nilai = 1
                    binding.seekLevel.progress = nilai
                    return
                }
                Prefs.level = nilai
                if (nilai == 1) {
                    textToSpeech.speak(
                        "Hanya pertambahan",
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        null
                    )
                }else if (nilai == 2) {
                    textToSpeech.speak(
                        "Pertambahan dan pengurangan",
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        null
                    )
                }else if (nilai == 3) {
                    textToSpeech.speak(
                        "Pertambahan pengurangan dan perkalian",
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        null
                    )
                }else {
                    textToSpeech.speak(
                        "Pertambahan pengurangan perkalian dan pembagian",
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        null
                    )
                }
            }
        })
        binding.btnMulai.setOnClickListener {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
            mulaiQuiz();
        }


    }

    fun mulaiQuiz() {
        binding.btnMulai.visibility = View.GONE
        binding.txtJawaban.visibility = View.VISIBLE
        binding.txtJawaban.setText("")
        binding.txtJawaban.requestFocus()
        val tipe = (1..Prefs.level).random()
        var kiri = 0
        var kanan = 0
        if (tipe == 1) {
            // ditambah
            binding.txtApa.setText("+")
            kiri = (1..getLevel()).random()
            kanan = (1..getLevel()).random()
            hasil = kiri + kanan
            textToSpeech.speak(
                "$kiri ditambah $kanan sama dengan",
                TextToSpeech.QUEUE_ADD,
                null,
                null
            )
            if (kiri <= 25 && kanan <= 25) {
                binding.txtGambarKiri.visibility = View.VISIBLE
                binding.txtGambarKanan.visibility = View.VISIBLE
                emoji = emojis.get((1..emojis.size - 1).random())
                binding.txtAngkaKiri.text = kiri.toString()
                binding.txtAngkaKanan.text = kanan.toString()
                binding.txtGambarKiri.setText(emoji.repeat(kiri))
                binding.txtGambarKanan.setText(emoji.repeat(kanan))
            }
        } else if (tipe == 2) {
            // dikurang
            binding.txtApa.setText("-")
            hasil = -1
            while (hasil < 0) {
                kiri = (1..getLevel()).random()
                kanan = (0..kiri).random()
                hasil = kiri - kanan
            }
            textToSpeech.speak(
                "$kiri dikurangi $kanan sama dengan",
                TextToSpeech.QUEUE_ADD,
                null,
                null
            )
            if (kiri <= 25 && kanan <= 25) {
                binding.txtGambarKiri.visibility = View.VISIBLE
                binding.txtGambarKanan.visibility = View.VISIBLE
                emoji = emojis.get((1..emojis.size - 1).random())
                binding.txtGambarKiri.setText(emoji.repeat(kiri))
                binding.txtGambarKanan.setText(emoji.repeat(kanan))
            }
        } else if (tipe == 3) {
            // diperkalian
            binding.txtGambarKiri.visibility = View.INVISIBLE
            binding.txtGambarKanan.visibility = View.INVISIBLE
            binding.txtApa.setText("X")
            kiri = (1..getLevel()).random()
            kanan = (1..getLevel()).random()
            hasil = kiri * kanan
            textToSpeech.speak(
                "$kiri dikali $kanan sama dengan",
                TextToSpeech.QUEUE_ADD,
                null,
                null
            )
        } else {
            // dibagi
            binding.txtGambarKiri.visibility = View.INVISIBLE
            binding.txtGambarKanan.visibility = View.INVISIBLE
            binding.txtApa.setText(":")
            hasil = 999999
            while (hasil > getLevel() && hasil != 0) {
                kanan = (1..getLevel()).random()
                if (kanan == 0) {
                    kanan++;
                }
                hasil = kanan * (1..getLevel()).random()
                Log.d("MATH", "Hasil $hasil")
            }
            kiri = hasil
            Log.d("MATH", "$kiri dibagi $kanan sama dengan")
            hasil = kiri / kanan
            textToSpeech.speak(
                "$kiri dibagi $kanan sama dengan",
                TextToSpeech.QUEUE_ADD,
                null,
                null
            )
        }

        binding.txtAngkaKiri.text = kiri.toString()
        binding.txtAngkaKanan.text = kanan.toString()
        count = 61;
        startTimer();
    }

    fun startTimer(){
        try{
            (timer as CountDownTimer).cancel()
        }catch (e: Exception){
            e.printStackTrace()
        }
        timer = object: CountDownTimer(61000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                count--
                binding.txtCount.setText("$count")
            }

            override fun onFinish() {
                mulaiQuiz()
            }
        }
        (timer as CountDownTimer).start()
    }

    fun jawab() {
        val jawaban = binding.txtJawaban.text.toString()
        if (jawaban.equals(hasil.toString())) {
            textToSpeech.speak("jawaban benar", TextToSpeech.QUEUE_ADD, null, null)
            Prefs.benar = Prefs.benar + 1
            binding.txtCount.setText("BENAR!!!")
        } else {
            textToSpeech.speak("jawaban salah\n Yang Benar adalah\n$hasil", TextToSpeech.QUEUE_ADD, null, null)
            Prefs.salah = Prefs.salah + 1
            binding.txtCount.setText("SALAH :(")
        }
        binding.txtBenar.setText(Prefs.benar.toString())
        binding.txtSalah.setText(Prefs.salah.toString())
        try{
            (timer as CountDownTimer).cancel()
        }catch (e: Exception){
            e.printStackTrace()
        }
        Handler().postDelayed(Runnable {
            mulaiQuiz()
        }, 2000)
    }

    fun getLevel(): Int {
        if (Prefs.benar < 100) {
            return 9
        } else if (Prefs.benar < 1000) {
            return 99
        } else if (Prefs.benar < 10000) {
            return 999
        }
        return 9999
    }

    override fun onDestroy() {
        super.onDestroy()
        textToSpeech.shutdown()
    }

    override fun onBackPressed() {
        System.exit(0)
        super.onBackPressed()
    }
}