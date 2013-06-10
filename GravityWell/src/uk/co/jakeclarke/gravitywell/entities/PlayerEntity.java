package uk.co.jakeclarke.gravitywell.entities;

import uk.co.jakeclarke.gravitywell.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class PlayerEntity extends Entity {
	
	public Vector2 position = new Vector2(0f, 0f);
	private Vector2 last = position;
	public Vector2 velocity = new Vector2(100f, 0f);
	public SpriteBatch batch;
	public Sprite sprite;
	
	private static final float GRAVITY_SCALER = 1f;
	private static final float PLAYER_SIZE = 10f;

	@Override
	public void initialise() {
		Texture texture = new Texture(
				Gdx.files.internal(Constants.Files.Graphics.BLANK_TEXTURE));
		this.sprite = new Sprite(texture);
		this.sprite.setColor(Color.GREEN);
		this.sprite.setSize(PLAYER_SIZE, PLAYER_SIZE);
	}
	

	@Override
	public void update(float elapsedMS) {
		Vector2 velo = new Vector2(this.velocity);
		this.position.add(velo.mul(elapsedMS));
		Vector2 posDelta = new Vector2(this.position).sub(last);
		Gdx.app.debug("Player pos", posDelta.toString());

		for (GravityWell gw : this.entityManager.game.getWells()) {
			if (gw.enabled) {
				final float distance = this.position.dst(gw.position);
				if (distance < gw.coreRadius) {
					this.entityManager.game.gameOver();
				} else if (distance < gw.wellRadius) {
					Vector2 delta = new Vector2(this.position).sub(gw.position);
					Vector2 deltaNorm = new Vector2(delta).nor();
					deltaNorm.set(-deltaNorm.x, -deltaNorm.y);
					
					this.velocity.add(deltaNorm.mul(GRAVITY_SCALER
							* (gw.wellRadius / distance)));
				}
			}
		}
	}

	@Override
	public void render() {
		this.sprite.setPosition(this.position.x - (PLAYER_SIZE / 2),
				this.position.y - (PLAYER_SIZE / 2));
		this.sprite.draw(batch);
	}

}
