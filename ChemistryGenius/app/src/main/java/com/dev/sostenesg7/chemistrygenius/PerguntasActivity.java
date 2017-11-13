package com.dev.sostenesg7.chemistrygenius;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.sostenesg7.chemistrygenius.controller.questao.TaferaBuscaQuestao;
import com.dev.sostenesg7.chemistrygenius.model.questao.InterfaceRetornoQuestao;
import com.dev.sostenesg7.chemistrygenius.model.questao.Questao;
import com.dev.sostenesg7.chemistrygenius.model.questao.RespostaQuestao;
import com.dev.sostenesg7.chemistrygenius.persistencia.BancoDados;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PerguntasActivity extends AppCompatActivity implements InterfaceRetornoQuestao {

    @BindView(R.id.btnPausa)
    ImageButton btnPausa;
    @BindView(R.id.btnEstrela)
    ImageButton btnEstrela;
    @BindView(R.id.btnVida)
    ImageButton btnVida;
    @BindView(R.id.btnVida2)
    ImageButton btnVida2;
    @BindView(R.id.btnVida3)
    ImageButton btnVida3;
    @BindView(R.id.btnVida4)
    ImageButton btnVida4;
    @BindView(R.id.txtPergunta)
    TextView txtPergunta;
    @BindView(R.id.imgPergunta)
    ImageView imgPergunta;
    @BindView(R.id.btnResposta1)
    ImageButton btnResposta1;
    @BindView(R.id.txtResposta1)
    TextView txtResposta1;
    @BindView(R.id.btnResposta2)
    ImageButton btnResposta2;
    @BindView(R.id.txtResposta2)
    TextView txtResposta2;
    @BindView(R.id.btnResposta3)
    ImageButton btnResposta3;
    @BindView(R.id.txtResposta3)
    TextView txtResposta3;
    @BindView(R.id.btnResposta4)
    ImageButton btnResposta4;
    @BindView(R.id.txtResposta4)
    TextView txtResposta4;
    @BindView(R.id.btnTempo)
    ImageButton btnTempo;
    @BindView(R.id.btnTempo2)
    ImageButton btnTempo2;
    @BindView(R.id.btnPular)
    ImageButton btnPular;
    @BindView(R.id.txtEstrelas)
    TextView txtEstrelas;

    private final int VERSAO_DB = 1;
    private final String NOME_DB = "questoes.db";
    private List<RespostaQuestao> respostaLista;
    private BancoDados bancoDados;
    private PerguntasActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_perguntas);
        ButterKnife.bind(this);
        txtPergunta = findViewById(R.id.txtPergunta);
        TextViewCompat.setAutoSizeTextTypeWithDefaults(txtPergunta, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);

        activity = this;

        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    TaferaBuscaQuestao taferaBuscaQuestao;
                    bancoDados = new BancoDados(PerguntasActivity.this, NOME_DB, VERSAO_DB);
                    taferaBuscaQuestao = new TaferaBuscaQuestao(bancoDados, activity);
                    taferaBuscaQuestao.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        service.shutdown();
    }

    @OnClick({R.id.txtResposta1, R.id.txtResposta2, R.id.txtResposta3, R.id.txtResposta4})
    public void onViewClicked(View view) {
        try {
            switch (view.getId()) {
                case R.id.txtResposta1:
                    if (!respostaLista.get(0).getCorreta()) {
                        btnResposta1.setBackgroundResource(R.drawable.tubo_ensaio_vermelho_completo);
                        vibrar();
                    }
                    break;
                case R.id.txtResposta2:
                    if (!respostaLista.get(1).getCorreta()) {
                        btnResposta2.setBackgroundResource(R.drawable.tubo_ensaio_vermelho_completo);
                        vibrar();
                    }
                    break;
                case R.id.txtResposta3:
                    if (!respostaLista.get(2).getCorreta()) {
                        btnResposta3.setBackgroundResource(R.drawable.tubo_ensaio_vermelho_completo);
                        vibrar();
                    }
                    break;
                case R.id.txtResposta4:
                    if (!respostaLista.get(3).getCorreta()) {
                        btnResposta4.setBackgroundResource(R.drawable.tubo_ensaio_vermelho_completo);
                        vibrar();
                    }
                    break;
            }
            this.exibirRespostaCorreta();

            final ExecutorService service = Executors.newSingleThreadExecutor();
            service.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    TaferaBuscaQuestao taferaBuscaQuestao;
                    taferaBuscaQuestao = new TaferaBuscaQuestao(bancoDados, activity);
                    taferaBuscaQuestao.execute();
                }
            });
            service.shutdown();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void exibirRespostaCorreta(){
        if(respostaLista.get(0).getCorreta()){
            btnResposta1.setBackgroundResource(R.drawable.tubo_ensaio_verde_claro_completo);
        }else if(respostaLista.get(1).getCorreta()){
            btnResposta2.setBackgroundResource(R.drawable.tubo_ensaio_verde_claro_completo);
        }else if(respostaLista.get(2).getCorreta()){
            btnResposta3.setBackgroundResource(R.drawable.tubo_ensaio_verde_claro_completo);
        }else if(respostaLista.get(3).getCorreta()){
            btnResposta4.setBackgroundResource(R.drawable.tubo_ensaio_verde_claro_completo);
        }
    }

    private void vibrar(){
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        try {
            if(Build.VERSION.SDK_INT >= 26){
                vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
            }else{
                vibrator.vibrate(200);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void atualizarCampos(Questao questao) {
        respostaLista = questao.getListaRespostas();

        /*
        Gson gson;
        gson = new GsonBuilder().setPrettyPrinting().create();
        Questao q = questao;
        q.setImagem(null);
        System.out.println(gson.toJson(q));
        */

        if(respostaLista != null && respostaLista.size() == 4 ) {
            byte[] imagem;
            imagem = questao.getImagem();
            Collections.shuffle(respostaLista);

            txtPergunta.setText(questao.getTexto());
            txtResposta1.setText(respostaLista.get(0).getResposta());
            txtResposta2.setText(respostaLista.get(1).getResposta());
            txtResposta3.setText(respostaLista.get(2).getResposta());
            txtResposta4.setText(respostaLista.get(3).getResposta());

            btnResposta1.setBackgroundResource(R.drawable.tubo_ensaio_azul_completo);
            btnResposta2.setBackgroundResource(R.drawable.tubo_ensaio_roxo_completo);
            btnResposta3.setBackgroundResource(R.drawable.tubo_ensaio_verde_completo);
            btnResposta4.setBackgroundResource(R.drawable.tubo_ensaio_laranja_completo);

            if (imagem != null) {
                imgPergunta.setImageBitmap(Bitmap.createBitmap(BitmapFactory.decodeByteArray(imagem, 0, imagem.length)));
                imgPergunta.setVisibility(View.VISIBLE);
            } else {
                imgPergunta.setVisibility(View.INVISIBLE);
            }
        }
    }
}
