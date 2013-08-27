package uk.co.jakeclarke.gravitywell.entities;

import uk.co.jakeclarke.gravitywell.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;


public class SpawnEntity extends Entity {

	public float spawnRate = 1.0f;
	public Vector2 spawnedVelocity = new Vector2();
	public Vector2 position = new Vector2();
	public SpriteBatch batch;
	private float currentTime;
	private Sprite sprite;
	private static final float SPAWN_SIZE = 10f;

	@Override
	public void initialise() {
		Texture texture = new Texture(
				Gdx.files.internal(Constants.Files.Graphics.BLANK_TEXTURE));
		this.sprite = new Sprite(texture);
		this.sprite.setColor(Color.ORANGE);
		this.sprite.setSize(SPAWN_SIZE, SPAWN_SIZE);
	}

	@Override
	public void update(float elapsedMS) {
		this.currentTime += elapsedMS;
		while (this.currentTime > this.spawnRate) {
			PlayerEntity p = new PlayerEntity();
			p.velocity = this.spawnedVelocity.cpy();
			p.position = this.position.cpy();
			p.batch = this.batch;
			this.entityManager.add(p);
			this.currentTime -= this.spawnRate;
		}
	}

	@Override
	public void render() {
		this.sprite.setPosition(this.position.x - (SPAWN_SIZE / 2),
				this.position.y - (SPAWN_SIZE / 2));
		this.sprite.draw(this.batch);
	}

}
