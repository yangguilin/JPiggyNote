package com.ygl.piggynote.bean.dingup;

import java.util.List;

/**
 * Created by yanggavin on 16/3/4.
 */
public class CollinsSense {
    private int id;
    private String posp;
    private String def;
    private String tran;
    private List<CollinsExample> examples;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosp() {
        return posp;
    }

    public void setPosp(String posp) {
        this.posp = posp;
    }

    public String getDef() {
        return def;
    }

    public void setDef(String def) {
        this.def = def;
    }

    public String getTran() {
        return tran;
    }

    public void setTran(String tran) {
        this.tran = tran;
    }

    public List<CollinsExample> getExamples() {
        return examples;
    }

    public void setExamples(List<CollinsExample> examples) {
        this.examples = examples;
    }
}
