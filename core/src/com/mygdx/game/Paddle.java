package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Paddle {
    Texture texture;
    int x, y = 10;
    public Paddle(){
        texture = new Texture("paddle.bmp");
    }

    public void dispose(){
        texture.dispose();
    }

    public void draw(SpriteBatch batch){
        batch.draw(texture, x, y);
    }

    public void move() {
        if(Gdx.input.isTouched()){
            x = Gdx.input.getX() - texture.getWidth() / 2;
            // preventing the paddle from moving left side of screen
            if(x < 0){
                x = 0;
            }
            // preventing the paddle from moving right side of screen
            if(x > Gdx.graphics.getWidth() - texture.getWidth()){
                x = Gdx.graphics.getWidth() - texture.getWidth();
            }
        }
    }
}
