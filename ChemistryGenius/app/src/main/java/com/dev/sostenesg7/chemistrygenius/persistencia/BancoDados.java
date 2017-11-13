package com.dev.sostenesg7.chemistrygenius.persistencia;

import android.content.Context;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Sostenes on 09/11/2017.
 */

public class BancoDados extends SQLiteAssetHelper{

    public BancoDados(Context context, String nomeDb, Integer versaoDb) {
        super(context, nomeDb, null, versaoDb);
    }
}
