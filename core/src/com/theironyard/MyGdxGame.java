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
	TextureRegion up, down, right, left, stand, leftF;
	TextureRegion runUp, runDown, runRight, runLeft, runStand;
	boolean faceRight, faceUp = false, upDown;
	boolean isRunning;
	Animation downA, upA, rightA, leftA;
	Animation runUpA, runDownA, runRightA, runLeftA;
	float time;


	static final float MAX_VELOCITY = 200;
	static final float DECELERATION = 0.80f;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		Texture tiles = new Texture("tiles.png");
		TextureRegion[][] grid = TextureRegion.split(tiles, 16, 16);

		down = grid[6][0];
		up = grid[6][1];
		stand = grid[6][2];
		right = grid[6][3];
		runDown = grid[7][0];
		runUp = grid [7][1];
		runStand = grid[7][2];
		runRight = grid [7][3];
		left = new TextureRegion(right);
		left.flip(true, false);
		leftF = new TextureRegion(stand);
		leftF.flip(true, false);
		runLeft = new TextureRegion(runRight);
		runLeft.flip(true, false);

		TextureRegion downF = new TextureRegion(down);
		downF.flip(true, false);
		downA = new Animation(0.2f, down, downF);

		TextureRegion upF = new TextureRegion(up);
		upF.flip(true, false);
		upA = new Animation(0.2f, up, upF);

		rightA = new Animation(0.2f, right, stand);

		leftA = new Animation(0.2f, left, leftF);

		TextureRegion rundownF = new TextureRegion(runDown);
		rundownF.flip(true, false);
		runDownA = new Animation(0.2f, runDown, rundownF);

		TextureRegion runUpF = new TextureRegion(runUp);
		runUpF.flip(true, false);
		runUpA = new Animation(0.2f, runUp, runUpF);

		runRightA = new Animation(0.2f, runStand, runRight);


		TextureRegion runLeftF = new TextureRegion(runStand);
		runLeftF.flip(true, false);
		runLeftA = new Animation(0.2f, runLeft, runLeftF);
	}

	@Override
	public void render () {

		time += Gdx.graphics.getDeltaTime();

		Gdx.gl.glClearColor(.5f, 1, .5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		if (!isRunning) {
			if (upDown) {
				if (faceUp) {
					batch.draw(upA.getKeyFrame(time, true), x, y);
				}
				if (!faceUp) {
					batch.draw(downA.getKeyFrame(time, true), x, y);
				}
			}
			if (!upDown) {
				if (faceRight) {
					batch.draw(rightA.getKeyFrame(time, true), x, y);
				}
				if (!faceRight) {
					batch.draw(leftA.getKeyFrame(time, true), x, y);
				}
			}
		}
			if (isRunning) {
				if (upDown) {
					if (faceUp) {
						batch.draw(runUpA.getKeyFrame(time, true), x, y);
					}
					if (!faceUp) {
						batch.draw(runDownA.getKeyFrame(time, true), x, y);
					}
				}
				if (!upDown) {
					if (faceRight) {
						batch.draw(runRightA.getKeyFrame(time, true), x, y);
					}
					if (!faceRight) {
						batch.draw(runLeftA.getKeyFrame(time, true), x, y);
					}
				}
			}
			move();
			batch.end();
		}


	public void move() {
			if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
				if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
					yv = MAX_VELOCITY * 2;
					isRunning = true;
					faceUp = true;
					upDown = true;
				} else {
					yv = MAX_VELOCITY;
					isRunning = false;
					faceUp = true;
					upDown = true;
				}
			} else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
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
				if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
					xv = MAX_VELOCITY * 2;
					isRunning = true;
					faceRight = true;
					upDown = false;
				} else {
					xv = MAX_VELOCITY;
					isRunning = false;
					faceRight = true;
					upDown = false;
				}
			} else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
				if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
					xv = MAX_VELOCITY * -2;
					isRunning = true;
					faceRight = false;
					upDown = false;
				} else {
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
			if (Math.abs(velocity) < 2) {
				velocity = 0f;
			}
			return velocity;
		}
}
