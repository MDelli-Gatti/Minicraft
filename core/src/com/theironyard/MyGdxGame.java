package com.theironyard;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	float x, y, xv, yv;
	TextureRegion up, down, right, left;
	boolean faceRight, faceUp = false, upDown;



	static final float MAX_VELOCITY = 200;
	static final float DECELERATION = 0.95f;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		Texture tiles = new Texture("tiles.png");
		TextureRegion[][] grid = TextureRegion.split(tiles, 16, 16);
		down = grid[6][0];
		up = grid[6][1];
		right = grid[6][3];
		left = new TextureRegion(right);
		left.flip(true, false);
	}

	@Override
	public void render () {



		Gdx.gl.glClearColor(.5f, 1, .5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		if (upDown) {
			if (faceUp) {
				batch.draw(up, x, y);
			}
			if (!faceUp) {
				batch.draw(down, x, y);
			}
		}
		if (!upDown) {
			if (faceRight) {
				batch.draw(right, x, y);
			}
			if (!faceRight) {
				batch.draw(left, x, y);
			}
		}
		move();
		batch.end();
	}
	public void move() {
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
				yv = MAX_VELOCITY * 2;
			}
			else {
				yv = MAX_VELOCITY;
				faceUp = true;
				upDown = true;
			}
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
				yv = MAX_VELOCITY * -2;
			} else {
				yv = -MAX_VELOCITY;
				faceUp = false;
				upDown = true;
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
				xv = MAX_VELOCITY * 2;
			}
			else {
				xv = MAX_VELOCITY;
				faceRight = true;
				upDown = false;
			}
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
				xv = MAX_VELOCITY * -2;
			}
			else {
				xv = -MAX_VELOCITY;
				faceRight = false;
				upDown = false;
			}
		}

		float delta = Gdx.graphics.getDeltaTime();
		y += yv * delta;
		x += xv * delta;

		yv = decelerate(yv);
		xv = decelerate(xv);
	}

		public float decelerate(float velocity){
			velocity = velocity * DECELERATION;
			if (Math.abs(velocity) < 1) {
				velocity = 0;
			}
			return velocity;
		}
}
