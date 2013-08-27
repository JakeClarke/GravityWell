package uk.co.jakeclarke.gravitywell;

import java.util.ArrayList;

import uk.co.jakeclarke.gravitywell.entities.EntityManager;
import uk.co.jakeclarke.gravitywell.entities.GravityWell;
import uk.co.jakeclarke.gravitywell.entities.SpawnEntity;
import uk.co.jakeclarke.gravitywell.ui.UIManager;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class GravityWellGame implements ApplicationListener {

	public static final int TARGET_WIDTH = 1280, TARGET_HEIGHT = 720;

	private OrthographicCamera camera, uicamera;
	private SpriteBatch batch, uiBatch;
	private EntityManager entityManager;
	private ArrayList<GravityWell> wells;
	private UIManager ui = new UIManager();
	private int lives = 10;
	
	@Override
	public void create() {
		Gdx.app.setLogLevel(Gdx.app.LOG_DEBUG);

		this.wells = new ArrayList<GravityWell>();

		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(TARGET_WIDTH, TARGET_HEIGHT);
		uicamera = new OrthographicCamera(TARGET_WIDTH, TARGET_HEIGHT);

		batch = new SpriteBatch();
		uiBatch = new SpriteBatch();
		uiBatch.setProjectionMatrix(this.uicamera.combined);

		this.entityManager = new EntityManager(this);

		SpawnEntity spawn = new SpawnEntity();
		spawn.batch = this.batch;
		spawn.position = new Vector2(-400f, 50f);
		spawn.spawnedVelocity = new Vector2(100f, 0f);
		this.entityManager.add(spawn);
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void render() {

		handleTouch();
		this.entityManager.update(Gdx.graphics.getDeltaTime());

		// Gdx.gl20.glEnable(GL20.GL_BLEND_SRC_ALPHA);
		Gdx.gl20.glClearColor(1, 1, 1, 1);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		


		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		this.entityManager.render();

		batch.end();
		uiBatch.begin();

		uiBatch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	public void gameOver() {
		Gdx.app.log("Gravity well", "Game over!");
	}

	public ArrayList<GravityWell> getWells() {
		return this.wells;
	}

	public void addWell(Vector2 pos, float coreRadius, float wellRadius) {
		GravityWell g = new GravityWell();
		g.coreRadius = coreRadius;
		g.wellRadius = wellRadius;
		g.spriteBatch = batch;
		g.position = pos;
		this.wells.add(g);
		this.entityManager.add(g);
	}

	private void handleTouch() {
		if (!Gdx.input.justTouched()) {
			return;
		}

		int x = Gdx.input.getX() - (Gdx.graphics.getWidth() / 2);
		int y = Gdx.input.getY() - (Gdx.graphics.getHeight() / 2);
		y = -y;

		Gdx.app.debug("Touch", "Touch at: " + x + ", " + y);

		if (this.ui.handleInput(x, y)) {
			return;
		}

		GravityWell[] ws = this.getWells().toArray(new GravityWell[0]);
		for (GravityWell gw : ws) {
			if (gw.intersects(x, y)) {
				this.wells.remove(gw);
				this.entityManager.remove(gw);
				return;
			}
		}

		this.addWell(new Vector2(x, y), 10f, 70f);
	}

	public void restartGame() {
		this.create();
	}

	public void destoybit() {
		if(this.lives == 0) {
			gameOver();
		}
	}
}
