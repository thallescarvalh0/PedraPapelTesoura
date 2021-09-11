package br.edu.ifsp.scl.sdm.pedrapapeltesoura

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import br.edu.ifsp.scl.sdm.pedrapapeltesoura.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var editarActivityResultLauncher: ActivityResultLauncher<Intent>

    private var jogadores = 2
    private var rodadas = 1
    private var rodadaGame = 0
    private var pontJ1 = 0
    private var pontJ2 = 0
    private var pontJ1_vs_3 = 0
    private var pontJ2_vs_3 = 0
    private var pontJ3_vs_3 = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)

        jogadores = 0
        rodadaGame = 0
        rodadas = 1
        pontJ1 = 0
        pontJ2 = 0
        pontJ1_vs_3 = 0
        pontJ2_vs_3 = 0
        pontJ3_vs_3 = 0

        savedInstanceState?.getInt("RODADA").takeIf { it != null }.let {
            if (it != null) {
                rodadaGame = it
                rodadas = 1
            }
        }
        savedInstanceState?.getInt("JOGADORES").takeIf { it != null }.let {
            if (it != null) {
                jogadores = it
            }
        }

        activityMainBinding.btnPapel.setOnClickListener(this)
        activityMainBinding.btnPedra.setOnClickListener(this)
        activityMainBinding.btnTesoura.setOnClickListener(this)

        editarActivityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
            if (result?.resultCode == RESULT_OK){
                with(result){
                    data?.getIntExtra("RODADA",0).takeIf { it!=null }.let{
                        when(it){
                            1 -> {rodadaGame = 1
                                Log.v("rodada", "$rodadaGame")}
                            3 -> {rodadaGame = 3
                                Log.v("rodada", "$rodadaGame")}
                            5 -> {rodadaGame = 5
                                Log.v("rodada", "$rodadaGame")}
                            else -> {}
                        }
                        rodadas = 1
                        pontJ1 = 0
                        pontJ2 = 0
                        pontJ1_vs_3 = 0
                        pontJ2_vs_3 = 0
                        pontJ3_vs_3 = 0
                    }
                    data?.getIntExtra("JOGADORES",0).takeIf { it!=null }.let{
                        when(it){
                            0 -> {jogadores = 2
                                Log.v("jogadores", "$jogadores")
                            }
                            1 -> {jogadores = 3
                                Log.v("jogadores", "$jogadores")
                            }
                            else -> {}
                        }
                    }
                }
            }
        }
    }

    override fun onClick(view: View) {
        val click: Int = when (view.id) {
            R.id.btnPedra -> 0
            R.id.btnPapel -> 1
            R.id.btnTesoura -> 2
            else -> 0
        }
        if (rodadas <= rodadaGame) {
            jogarPedraPapelTesoura(jogadores, click)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun jogarPedraPapelTesoura(jogadores: Int, jogada: Int) {
        var resultadoSb: String
        val resultadoVs: Int
        val random2 = Random(System.currentTimeMillis())
        val jogadaComputador2 = random2.nextInt(3)
        val jogadaComputador3 = random2.nextInt(3)

        activityMainBinding.tvJogador2.visibility = View.VISIBLE
        when (jogadaComputador2) {
            0 -> activityMainBinding.btnJogador2.setBackgroundResource(R.mipmap.pedra)
            1 -> activityMainBinding.btnJogador2.setBackgroundResource(R.mipmap.papel)
            2 -> activityMainBinding.btnJogador2.setBackgroundResource(R.mipmap.tesoura)
        }

        if (jogadores == 3) {
            when (jogadaComputador3) {
                0 -> activityMainBinding.btnJogador3.setBackgroundResource(R.mipmap.pedra)
                1 -> activityMainBinding.btnJogador3.setBackgroundResource(R.mipmap.papel)
                2 -> activityMainBinding.btnJogador3.setBackgroundResource(R.mipmap.tesoura)
            }
            activityMainBinding.tvJogador3.visibility = View.VISIBLE
        }

        if (jogada == jogadaComputador2) {
            resultadoVs = 1
            resultadoSb = "Rodada $rodadas: EMPATE J1 e J2"
        } else if (jogada - jogadaComputador2 == -2 || jogada - jogadaComputador2 == 1) {
            resultadoVs = 2
            resultadoSb = "Rodada $rodadas: GANHOU J1"
            pontJ1 += 1
        } else {
            resultadoVs = 3
            resultadoSb = "Rodada $rodadas: GANHOU J2"
            pontJ2 += 1
        }
        if (jogadores == 3) {
            when (resultadoVs) {
                1 -> if (jogadaComputador2 == jogadaComputador3) {
                    resultadoSb = "Rodada $rodadas: EMPATE J2 e J3"
                } else if ((jogadaComputador2 - jogadaComputador3 == -2
                            || jogadaComputador2 - jogadaComputador3 == 1)
                    && (jogada - jogadaComputador3 == -2 || jogada - jogadaComputador3 == 1)
                ) {
                    resultadoSb = "Rodada $rodadas: EMPATE J2 e J3"
                } else if (jogada - jogadaComputador3 != -2 || jogada - jogadaComputador3 != 1) {
                    resultadoSb = "Rodada $rodadas: GANHOU J3"
                    pontJ3_vs_3 += 1
                }
                2 -> if (jogadaComputador2 == jogadaComputador3) {
                    resultadoSb ="Rodada $rodadas: GANHOU J1"
                    pontJ1_vs_3 += 1
                }else if ((jogadaComputador2 - jogadaComputador3 == -2 || jogadaComputador2 - jogadaComputador3 == 1)
                        && (jogada - jogadaComputador3 == -2 || jogada - jogadaComputador3 == 1)
                ) {
                    resultadoSb = "Rodada $rodadas: GANHOU J1"
                     pontJ1_vs_3 += 1
                } else {
                    resultadoSb = "Rodada $rodadas: EMPATE J1 e J3"
                }

                3 -> if (jogadaComputador2 == jogadaComputador3) {
                    resultadoSb = "Rodada $rodadas: EMPATE J2 e J3"
                } else if (jogadaComputador2 - jogadaComputador3 == -2 || jogadaComputador2 - jogadaComputador3 == 1) {
                    resultadoSb = "Rodada $rodadas: GANHOU J2"
                    pontJ2_vs_3 += 1
                } else {
                    resultadoSb = "Rodada $rodadas: EMPATE J2 e J3"
                }
            }
        }
        if (rodadas <= rodadaGame) {

            if (jogadores == 3){
                resultadoSb = "$resultadoSb \n" +
                        "Pontuação J1: $pontJ1_vs_3\n" +
                        "Pontuação J2: $pontJ2_vs_3\n" +
                        "Pontuação J3: $pontJ3_vs_3"}
            else{
                resultadoSb = "$resultadoSb \n" +
                        "Pontuação J1: $pontJ1\n" +
                        "Pontuação J2: $pontJ2"
            }
            activityMainBinding.tvResultado.text = resultadoSb
            rodadas += 1
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.editarMi -> {
                val editarOpcoesIntent = Intent(this, EditarOpcoesActivity::class.java)
                editarActivityResultLauncher.launch(editarOpcoesIntent)
                true
            }else -> {
                false
            }

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("RODADA", rodadaGame)
        outState.putInt("JOGADORES", jogadores)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.getInt("RODADA").takeIf { true }.let {
            if (it != null) {
                rodadaGame = it
            }
        }
        savedInstanceState.getInt("JOGADORES").takeIf { true }.let {
            if (it != null) {
                jogadores = it
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        with(activityMainBinding) {
            tvResultado.text = ""
            btnJogador2.setBackgroundResource(android.R.color.transparent)
            tvJogador2.visibility = View.INVISIBLE
            btnJogador3.setBackgroundResource(android.R.color.transparent)
            tvJogador3.visibility = View.INVISIBLE
        }
    }
}