package uk.co.jakeclarke.gravitywell;

import java.util.ArrayList;

import uk.co.jakeclarke.gravitywell.entities.EntityManager;
import uk.co.jakeclarke.gravitywell.entities.GravityWell;
import uk.co.jakeclarke.gravitywell.entities.PlayerEntity;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class GravityWellGame implements ApplicationListener {

	public static final int TARGET_WIDTH = 1280, TARGET_HEIGHT = 720;

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private EntityManager entityManager;
	private ArrayList<GravityWell> wells;
	private PlayerEntity playerEntity;
	
	@Override
	public void create() {
		Gdx.app.setLogLevel(Gdx.app.LOG_DEBUG);

		this.wells = new ArrayList<GravityWell>();
		this.playerEntity = new PlayerEntity();

		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(TARGET_WIDTH, TARGET_HEIGHT);
		batch = new SpriteBatch();

		this.entityManager = new EntityManager(this);

		this.playerEntity.batch = this.batch;
		this.playerEntity.position = new Vector2(-400f, 50f);
		this.entityManager.add(playerEntity);
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
		this.playerEntity.enabled = false;
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

		GravityWell[] ws = this.getWells().toArray(new GravityWell[0]);
		for (GravityWell gw : ws) {
			if (gw.intersectsCore(x, y)) {
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
}
