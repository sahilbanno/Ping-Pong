package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class PongGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture paddleTexture;
	int paddleX, paddleY = 10;
	int ballStartFrameCounter;
	final int FRAMES_TO_WAIT_BEFORE_BALL_START = 60;
    SoundManager soundManager;
    Ball ball;


    @Override
	public void create () {
		batch = new SpriteBatch();
		paddleTexture = new Texture("paddle.bmp");
		soundManager = new SoundManager();
        ball = new Ball();
		paddleX = (Gdx.graphics.getWidth() - paddleTexture.getWidth()) / 2;
		ball.x = paddleX + paddleTexture.getWidth() / 2 - ball.texture.getWidth() / 2;
		ball.y = paddleTexture.getHeight() + paddleY;
        soundManager.loadsounds();

    }

	@Override
	public void render () {
		// input
		movePaddle();
		moveBall();
		collideBall();
		// when we loose the ball
		if(ball.y + ball.texture.getHeight() < 0){
			ball.x = paddleX + paddleTexture.getWidth() / 2 - ball.texture.getWidth() / 2;
			ball.y = paddleTexture.getHeight() + paddleY;
			ballStartFrameCounter = 0;
			soundManager.loseBallSound.play();
		}
		//DRAWING
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(paddleTexture, paddleX, paddleY);
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
			ball.x = paddleX + paddleTexture.getWidth() / 2 - ball.texture.getWidth() / 2;
		}
	}

	private void movePaddle() {
		if(Gdx.input.isTouched()){
			paddleX = Gdx.input.getX() - paddleTexture.getWidth() / 2;
			// preventing the paddle from moving left side of screen
			if(paddleX < 0){
				paddleX = 0;
			}
			// preventing the paddle from moving right side of screen
			if(paddleX > Gdx.graphics.getWidth() - paddleTexture.getWidth()){
				paddleX = Gdx.graphics.getWidth() - paddleTexture.getWidth();
			}
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
		if(ball.x + ball.texture.getWidth() / 2 >= paddleX &&
				ball.x - ball.texture.getWidth() / 2 <= paddleX + paddleTexture.getWidth()){
			if(ball.y < paddleTexture.getHeight()){
				ball.velocityY = -ball.velocityY;
                soundManager.playRandomBounceSound();
			}
		}
		//The ball collides with left side of the paddle
		if(ball.x > paddleX - ball.texture.getWidth() && ball.x < paddleX - ball.texture.getWidth() / 2 + 1){
			if(ball.y < paddleY + paddleTexture.getHeight()){
				if(ball.velocityX > 0) {
					ball.velocityX = -ball.velocityX;      }
			}
		}
		//The ball collides with right side of the paddle
		if(ball.x > paddleX + paddleTexture.getWidth() - ball.texture.getWidth() / 2 - 1
				&& ball.x < paddleX + paddleTexture.getWidth()){
			if(ball.y < paddleY + paddleTexture.getHeight()){
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
		paddleTexture.dispose();
        soundManager.disposeSound();
	}
}
