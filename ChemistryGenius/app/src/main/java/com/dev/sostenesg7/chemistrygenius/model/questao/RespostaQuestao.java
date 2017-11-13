package com.dev.sostenesg7.chemistrygenius.model.questao;

/**
 * Created by Sostenes on 11/11/2017.
 */

public class RespostaQuestao {
    private String resposta;
    private boolean correta;

    public RespostaQuestao(String resposta, boolean correta) {
        this.resposta = resposta;
        this.correta = correta;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public boolean getCorreta() {
        return correta;
    }

    public void setCorreta(boolean correta) {
        this.correta = correta;
    }
}
