package com.ygl.piggynote.bean.go;

/**
 * Created by yanggavin on 15/9/1.
 */
public class MonsterBean {
    private String id;
    private String name;
    private int life;
    private int attack;
    private int defend;
    private int speed;
    private float growFactor;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefend() {
        return defend;
    }

    public void setDefend(int defend) {
        this.defend = defend;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public float getGrowFactor() {
        return growFactor;
    }

    public void setGrowFactor(float growFactor) {
        this.growFactor = growFactor;
    }
}
