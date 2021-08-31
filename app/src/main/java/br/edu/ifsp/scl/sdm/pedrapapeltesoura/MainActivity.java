package br.edu.ifsp.scl.sdm.pedrapapeltesoura;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import java.util.Random;

import br.edu.ifsp.scl.sdm.pedrapapeltesoura.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding activityMainBinding;
    private Integer jogadores = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(activityMainBinding.getRoot());

        activityMainBinding.rbDoisJogadores.setOnClickListener(view -> {
            activityMainBinding.layoutJogador3.setVisibility(View.GONE);
        });

        activityMainBinding.rbTresJogadores.setOnClickListener(view -> {
            activityMainBinding.layoutJogador3.setVisibility(View.VISIBLE);
            jogadores = 3;
        });

        activityMainBinding.btnPapel.setOnClickListener(this);
        activityMainBinding.btnPedra.setOnClickListener(this);
        activityMainBinding.btnTesoura.setOnClickListener(this);

    }

    @Override
    public void onClick(View view){
        int click;
        switch (view.getId()){
            case R.id.btnPedra:
                click = 0;
                break;
            case R.id.btnPapel:
                click = 1;
                break;
            case R.id.btnTesoura:
                click = 2;
                break;
            default:
                click = 0;
                break;
        }
        jogarPedraPapelTesoura(jogadores,click);

    }

    private void jogarPedraPapelTesoura (int jogadores, int jogada ){

        String resultadoSb = "";
        Integer resultadoVs = null;

        Random random2 = new Random(System.currentTimeMillis());
        int jogadaComputador2 = random2.nextInt(3);
        int jogadaComputador3 = random2.nextInt(3);

        switch (jogadaComputador2){
            case 0 :
                activityMainBinding.btnJogador2.setBackgroundResource(R.mipmap.pedra);
                break;
            case 1:
                activityMainBinding.btnJogador2.setBackgroundResource(R.mipmap.papel);
                break;
            case 2:
                activityMainBinding.btnJogador2.setBackgroundResource(R.mipmap.tesoura);
                break;
        }

        if (jogadores==3){
            switch (jogadaComputador3){
                case 0:
                    activityMainBinding.btnJogador3.setBackgroundResource(R.mipmap.pedra);
                    break;
                case 1:
                    activityMainBinding.btnJogador3.setBackgroundResource(R.mipmap.papel);
                    break;
                case 2:
                    activityMainBinding.btnJogador3.setBackgroundResource(R.mipmap.tesoura);
                    break;
            }
        }

        if (jogada == jogadaComputador2){
            resultadoVs = 1;
            resultadoSb = "Resultado: EMPATE";
        }
        else if((jogada - jogadaComputador2 == -2) || (jogada - jogadaComputador2 == 1)) {
            resultadoVs = 2;
            resultadoSb = "Resultado: GANHOU";
        }else{
            resultadoVs = 3;
            resultadoSb = "Resultado: PERDEU";
        }

        if (jogadores == 3) {
            switch (resultadoVs){
                case 1 ://empate
                    if (jogadaComputador2 == jogadaComputador3) {
                        resultadoSb = "Resultado: EMPATE";
                    }else if(((jogadaComputador2 - jogadaComputador3 == -2)
                            || (jogadaComputador2 - jogadaComputador3 == 1))
                            && ((jogada - jogadaComputador3 == -2) || (jogada - jogadaComputador3 == 1))){
                        resultadoSb = "Resultado: EMPATE";
                    }else if ((jogada - jogadaComputador3 != -2) || (jogada - jogadaComputador3 != 1)){
                        resultadoSb = "Resultado: PERDEU";
                    }
                    break;
                case 2: //ganhou
                    if (jogadaComputador2 == jogadaComputador3)
                        resultadoSb = "Resultado: GANHOU";
                    else if(((jogadaComputador2 - jogadaComputador3 == -2)
                            || (jogadaComputador2 - jogadaComputador3 == 1))
                            &&((jogada - jogadaComputador3 == -2) || (jogada - jogadaComputador3 == 1)))
                        resultadoSb = "Resultado: GANHOU";
                    else
                        resultadoSb = "Resultado: EMPATE";

                    break;
                case 3: // perdeu
                    if (jogadaComputador2 == jogadaComputador3)
                        resultadoSb = "Resultado: PERDEU";
                    else if((jogadaComputador2 - jogadaComputador3 == -2) || (jogadaComputador2 - jogadaComputador3 == 1))
                        resultadoSb = "Resultado: PERDEU";
                    else
                        resultadoSb = "Resultado: EMPATE";

                    break;
            }
        }
        activityMainBinding.tvResultado.setText(resultadoSb);
    }
}