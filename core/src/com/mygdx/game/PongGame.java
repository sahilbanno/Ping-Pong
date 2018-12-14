package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class PongGame extends ApplicationAdapter {
	SpriteBatch batch;
	int ballStartFrameCounter;
	final int FRAMES_TO_WAIT_BEFORE_BALL_START = 60;
    SoundManager soundManager;
    Ball ball;
	Paddle paddle;

    @Override
	public void create () {
		batch = new SpriteBatch();
		soundManager = new SoundManager();
        ball = new Ball();
        soundManager.loadsounds();
        paddle =  new Paddle();
		paddle.x = (Gdx.graphics.getWidth() - paddle.texture.getWidth()) / 2;
		ball.x = paddle.x + paddle.texture.getWidth() / 2 - ball.texture.getWidth() / 2;
		ball.y = paddle.texture.getHeight() + paddle.y;

    }

	@Override
	public void render () {
		// input
		paddle.move();
		moveBall();
		collideBall();
		// when we loose the ball
		if(ball.y + ball.texture.getHeight() < 0){
			ball.x = paddle.x + paddle.texture.getWidth() / 2 - ball.texture.getWidth() / 2;
			ball.y = paddle.texture.getHeight() + paddle.y;
			ballStartFrameCounter = 0;
			soundManager.loseBallSound.play();
		}
		//DRAWING
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		paddle.draw(batch);
		ball.draw(batch);
		batch.end();
	}

	private void moveBall() {
		// LOGIC
		ballStartFrameCounter++;
		// if the ball already flies
		if(ballStartFrameCounter >= FRAMES_TO_WAIT_BEFORE_BALL_START) {
			ball.x += ball.velocityX;
			ball.y += ball.velocityY;
		}
		// if the ball does not fly yet and it moves along with the paddle
		else{
			ball.x = paddle.x + paddle.texture.getWidth() / 2 - ball.texture.getWidth() / 2;
		}
	}



	private void collideBall() {
		// the ball collides the right wall
		if(ball.x + ball.texture.getWidth() >= Gdx.graphics.getWidth()){
			ball.velocityX = -ball.velocityX;
            soundManager.playRandomBounceSound();
		}
		// the ball collides the top of the screen
		if(ball.y + ball.texture.getHeight() >= Gdx.graphics.getHeight()){
		    ball.velocityY = -ball.velocityY;
            soundManager.playRandomBounceSound();
        }
		//the ball hit the left side of the screen the ball hits the paddle
		if(ball.x < 0){
            ball.velocityX = -ball.velocityX;
            soundManager.playRandomBounceSound();
        }

		// the ball hit the paddle
		if(ball.x + ball.texture.getWidth() / 2 >= paddle.x &&
				ball.x - ball.texture.getWidth() / 2 <= paddle.x + paddle.texture.getWidth()){
			if(ball.y < paddle.texture.getHeight()){
				ball.velocityY = -ball.velocityY;
                soundManager.playRandomBounceSound();
			}
		}
		//The ball collides with left side of the paddle
		if(ball.x > paddle.x - ball.texture.getWidth() && ball.x < paddle.x - ball.texture.getWidth() / 2 + 1){
			if(ball.y < paddle.y + paddle.texture.getHeight()){
				if(ball.velocityX > 0) {
					ball.velocityX = -ball.velocityX;      }
			}
		}
		//The ball collides with right side of the paddle
		if(ball.x > paddle.x + paddle.texture.getWidth() - ball.texture.getWidth() / 2 - 1
				&& ball.x < paddle.x + paddle.texture.getWidth()){
			if(ball.y < paddle.y + paddle.texture.getHeight()){
				if(ball.velocityX < 0) {
					ball.velocityX = -ball.velocityX;
				}
			}
		}
	}
	

	@Override
	public void dispose () {
		batch.dispose();
		ball.dispose();
        soundManager.disposeSound();
        paddle.dispose();
	}
}
