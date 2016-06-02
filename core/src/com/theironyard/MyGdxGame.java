package com.theironyard;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	float x, y, xv, yv;
	TextureRegion up, down, right, left, stand;
	TextureRegion runUp, runDown, runRight, runLeft;
	boolean faceRight, faceUp = false, upDown;
	boolean isRunning;
	Animation downA;
	float time;


	static final float MAX_VELOCITY = 200;
	static final float DECELERATION = 0.95f;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		Texture tiles = new Texture("tiles.png");
		TextureRegion[][] grid = TextureRegion.split(tiles, 16, 16);
		down = grid[6][0];
		TextureRegion downF = new TextureRegion(down);
		downF.flip(true, false);
		downA = new Animation(0.2f, grid[6][0], downF);
		up = grid[6][1];
		right = grid[6][3];
		left = new TextureRegion(right);
		left.flip(true, false);
		stand = grid[6][2];
		runDown = grid[7][0];
		runUp = grid [7][1];
		runRight = grid [7][3];
		runLeft = new TextureRegion(runRight);
		runLeft.flip(true, false);

	}

	@Override
	public void render () {

		time += Gdx.graphics.getDeltaTime();

		Gdx.gl.glClearColor(.5f, 1, .5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		//if (velocity = 0){
		//	batch.draw(stand, x, y);
		//}
		if(!isRunning) {
			if (upDown) {
				if (faceUp) {
					batch.draw(up, x, y);
				}
				if (!faceUp) {
					batch.draw(downA.getKeyFrame(time, true), x, y);
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
		}
		if(isRunning){
			if (upDown) {
				if (faceUp) {
					batch.draw(runUp, x, y);
				}
				if (!faceUp) {
					batch.draw(runDown, x, y);
				}
			}
			if (!upDown) {
				if (faceRight) {
					batch.draw(runRight, x, y);
				}
				if (!faceRight) {
					batch.draw(runLeft, x, y);
				}
			}
		}
		move();
		batch.end();
	}
	public void move() {
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
				yv = MAX_VELOCITY * 2;
				isRunning = true;
				faceUp = true;
				upDown = true;
			}
			else {
				yv = MAX_VELOCITY;
				isRunning = false;
				faceUp = true;
				upDown = true;
			}
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
				yv = MAX_VELOCITY * -2;
				isRunning = true;
				faceUp = false;
				upDown = true;
			} else {
				yv = -MAX_VELOCITY;
				isRunning = false;
				faceUp = false;
				upDown = true;
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
				xv = MAX_VELOCITY * 2;
				isRunning = true;
				faceRight = true;
				upDown = false;
			}
			else {
				xv = MAX_VELOCITY;
				isRunning = false;
				faceRight = true;
				upDown = false;
			}
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
				xv = MAX_VELOCITY * -2;
				isRunning = true;
				faceRight = false;
				upDown = false;
			}
			else {
				xv = -MAX_VELOCITY;
				isRunning = false;
				faceRight = false;
				upDown = false;
			}
		}

		float delta = Gdx.graphics.getDeltaTime();
		y += yv * delta;
		x += xv * delta;

		yv = decelerate(yv);
		xv = decelerate(xv);

		if (y > 800){
			y = 0;
		}
		else if (y < 0){
			y = 800;
		}

		if (x > 800){
			x = 0;
		}
		else if (x < 0){
			x = 800;
		}
	}

		public float decelerate(float velocity){
			velocity = velocity * DECELERATION;
			if (Math.abs(velocity) < 1) {
				velocity = 0;
			}
			return velocity;
		}
}
