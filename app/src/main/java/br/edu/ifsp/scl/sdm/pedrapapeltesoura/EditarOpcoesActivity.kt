package br.edu.ifsp.scl.sdm.pedrapapeltesoura

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import br.edu.ifsp.scl.sdm.pedrapapeltesoura.databinding.ActivityEditarOpcoesBinding

class EditarOpcoesActivity : AppCompatActivity() {

    private lateinit var activityEditarBinding: ActivityEditarOpcoesBinding
    private var jogadores = 0
    private var rodadas = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityEditarBinding = ActivityEditarOpcoesBinding.inflate(layoutInflater)
        setContentView(activityEditarBinding.root)

        supportActionBar?.title = "Opções de jogo"

        activityEditarBinding.rbDoisJogadores.setOnClickListener {
            jogadores = 0
        }
        activityEditarBinding.rbTresJogadores.setOnClickListener {
            jogadores = 1
        }

        activityEditarBinding.rbUmaRodada.setOnClickListener {
            rodadas = 1
        }
        activityEditarBinding.rbTresRodadas.setOnClickListener {
            rodadas = 3
        }
        activityEditarBinding.rbCincoRodadas.setOnClickListener {
            rodadas = 5
        }

        activityEditarBinding.btnSalvar.setOnClickListener {
            val retornoIntent: Intent = Intent()
            with(activityEditarBinding){
                retornoIntent.putExtra("RODADA",rodadas)
                retornoIntent.putExtra("JOGADORES",jogadores)
            }

            setResult(RESULT_OK, retornoIntent)
            finish()
        }
    }
}