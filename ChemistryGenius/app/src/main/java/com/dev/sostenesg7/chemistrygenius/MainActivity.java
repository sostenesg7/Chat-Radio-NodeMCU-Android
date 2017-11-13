package com.dev.sostenesg7.chemistrygenius;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageButton btnModoInfinito;
    private ImageButton btnModoTempo;
    private ImageButton btnSom;
    private ImageButton btnEstrela;
    private ImageButton btnMusica;
    private ImageButton btnInfo;
    private ImageView imgBolhaEsquerda;
    private ImageView imgBolhaDireita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnModoInfinito = findViewById(R.id.btnModoInfinito);
        btnModoTempo = findViewById(R.id.btnModoTempo);
        btnSom = findViewById(R.id.btnSom);
        btnEstrela = findViewById(R.id.btnEstrela);
        btnMusica = findViewById(R.id.btnMusica);
        btnInfo = findViewById(R.id.btnInfo);
        btnModoInfinito.setOnClickListener(this);
        btnModoTempo.setOnClickListener(this);
        btnSom.setOnClickListener(this);
        btnEstrela.setOnClickListener(this);
        btnMusica.setOnClickListener(this);
        btnInfo.setOnClickListener(this);
        imgBolhaDireita = findViewById(R.id.imgEfeitoTuboEnsaioDireita);
        imgBolhaEsquerda = findViewById(R.id.imgEfeitoTuboEnsaioEsquerda);

        imgBolhaEsquerda.setImageResource(R.drawable.anim_bolhas);
        imgBolhaDireita.setImageResource(R.drawable.anim_bolhas);
        AnimationDrawable animationDrawableEsquerda = (AnimationDrawable) imgBolhaEsquerda.getDrawable();
        AnimationDrawable animationDrawableDireita = (AnimationDrawable) imgBolhaDireita.getDrawable();
        animationDrawableDireita.start();
        animationDrawableEsquerda.start();
    }

    @Override
    public void onClick(View view) {
        if(view == btnEstrela){
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim);
            btnEstrela.startAnimation(animation);
        }else if(view == btnInfo){

        }else if(view == btnModoInfinito){
            startActivity(new Intent(MainActivity.this, FullscreenActivity.class));
        }else if(view == btnModoTempo){
            startActivity(new Intent(MainActivity.this, PerguntasActivity.class));
        }else if(view == btnMusica){

        }else if(view == btnSom){

        }
    }
}
