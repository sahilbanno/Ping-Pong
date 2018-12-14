package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Ball {
    Texture texture;
    int x, y = 0;
    int velocityX = 10, velocityY = 10;
    public Ball(){
        texture = new Texture("ball_small.png");
    }
    public void dispose(){
        texture.dispose();
    }
    public void draw(SpriteBatch batch){
        batch.draw(texture, x, y);
    }
}
