package com.dev.sostenesg7.chemistrygenius.model.questao;

import java.util.List;

/**
 * Created by Sostenes on 08/11/2017.
 */

public class Questao{

    private int id;
    private String texto;
    private List<RespostaQuestao> listaRespostas;
    private byte[] imagem;

    public Questao() {}

    public Questao(int id, String texto, List<RespostaQuestao> respostas, byte[] imagem) {
        this.id = id;
        this.texto = texto;
        this.listaRespostas = respostas;
        this.imagem = imagem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public List<RespostaQuestao> getListaRespostas() {
        return listaRespostas.subList(0, listaRespostas.size());
    }

    private void setListaRespostas(List<RespostaQuestao> respostas) {
        this.listaRespostas = respostas;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public String getTextoResposta(int indiceResposta){
        String texto;
        texto = null;
        try{
            texto = this.listaRespostas.get(indiceResposta).getResposta();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return texto;
    }

    public RespostaQuestao getCorreta(){
        RespostaQuestao correta;
        correta = null;
        for (RespostaQuestao rq:listaRespostas) {
            if(rq.getCorreta()){
                correta = rq;
            }
        }
        return correta;
    }

}
