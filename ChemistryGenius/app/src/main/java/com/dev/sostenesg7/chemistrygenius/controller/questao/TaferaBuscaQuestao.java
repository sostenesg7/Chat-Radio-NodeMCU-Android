package com.dev.sostenesg7.chemistrygenius.controller.questao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.dev.sostenesg7.chemistrygenius.persistencia.BancoDados;
import com.dev.sostenesg7.chemistrygenius.model.questao.InterfaceRetornoQuestao;
import com.dev.sostenesg7.chemistrygenius.model.questao.Questao;
import com.dev.sostenesg7.chemistrygenius.model.questao.RespostaQuestao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Sostenes on 11/11/2017.
 */

public class TaferaBuscaQuestao extends AsyncTask<Void, Void, Questao>{
    private BancoDados bancoDados;
    private InterfaceRetornoQuestao retornoQuestao;

    public TaferaBuscaQuestao(BancoDados db, InterfaceRetornoQuestao retornoQuestao) {
        this.bancoDados = db;
        this.retornoQuestao = retornoQuestao;
    }

    private Questao buscarQuestaoPorId(Integer id) {
        Cursor resultado;
        Questao questao = null;

        try {
            resultado = this.buscar(
                    "SELECT Q.ID, Q.TEXTO, Q.IMAGEM, RQ.RESPOSTA, RQ.CORRETA FROM QUESTAO Q " +
                            "INNER JOIN RESPOSTA_QUESTAO RQ " +
                            "ON Q.ID = RQ.ID_QUESTAO " +
                            "WHERE Q.ID = " + id);

            resultado.moveToFirst();

            if (resultado.getCount() > 0) {
                int idQuestao;
                String textoQuestao;
                List<RespostaQuestao> listaRespostas;
                byte[] imagemQuestao;

                idQuestao = resultado.getInt(resultado.getColumnIndex("id"));
                textoQuestao = resultado.getString(resultado.getColumnIndex("texto"));
                imagemQuestao = resultado.getBlob(resultado.getColumnIndex("imagem"));

                listaRespostas = new ArrayList<>();
                RespostaQuestao respostaQuestao;
                String resposta;
                boolean correta;
                do {
                    resposta = resultado.getString(resultado.getColumnIndex("resposta"));
                    correta = resultado.getInt(resultado.getColumnIndex("correta")) == 1 ? true : false;
                    respostaQuestao = new RespostaQuestao(resposta, correta);
                    listaRespostas.add(respostaQuestao);
                } while (resultado.moveToNext());
                questao = new Questao(idQuestao, textoQuestao, listaRespostas, imagemQuestao);
            }
        } catch (Exception ex) {
            //Log.d("ERRO: ", ex.getMessage());
            ex.printStackTrace();
        }
        return questao;
    }

    private Cursor buscar(String sql){
        SQLiteDatabase dbLeiura = bancoDados.getReadableDatabase();
        Cursor resultado = dbLeiura.rawQuery(sql, null);
        return resultado;
    }

    @Override
    protected Questao doInBackground(Void... objects) {
        Random random;
        Cursor resultado;
        Questao questao;
        String sql;
        questao = null;
        random = new Random();
        sql = "SELECT ID FROM QUESTAO";

        try {
            resultado = buscar(sql);
            System.out.println("QUANTIDADE: " + resultado.getCount());
            if (resultado.getCount() > 0) {
                int randomId;
                randomId = random.nextInt(resultado.getCount());
                resultado.moveToPosition(randomId);
                System.out.println("INDICE: " + randomId);
                int idQuestao;
                idQuestao = resultado.getInt(resultado.getColumnIndex("id"));
                questao = buscarQuestaoPorId(idQuestao);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return questao;
    }

    @Override
    protected void onPostExecute(Questao questao) {
        //super.onPostExecute(questao);
        if (questao != null) {
            retornoQuestao.atualizarCampos(questao);
        }
        this.cancel(true);
    }
}
