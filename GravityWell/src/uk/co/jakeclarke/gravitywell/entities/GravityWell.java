package uk.co.jakeclarke.gravitywell.entities;

import uk.co.jakeclarke.gravitywell.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class GravityWell extends Entity {
	
	public Vector2 position = new Vector2();
	
	public Texture texture;
	private Sprite wellSprite;
	private Sprite coreSprite;

	public float coreRadius;

	public float wellRadius;

	public SpriteBatch spriteBatch;

	private final static float SPRITE_SCALE = 1f / 64f;

	@Override
	public void initialise() {
		this.texture = new Texture(
				Gdx.files
						.internal(Constants.Files.Graphics.ROOT + "white-core.png"));
		
		TextureRegion region = new TextureRegion(this.texture, 0, 0,
				this.texture.getWidth(), this.texture.getHeight());

		this.wellSprite = new Sprite(region);

		this.wellSprite.setOrigin(region.getRegionWidth() / 2,
				region.getRegionHeight() / 2);

		this.coreSprite = new Sprite(this.wellSprite);

		this.coreSprite.setColor(Color.RED);
		
		Color c = Color.BLUE;
		c.a = 0.5f;
		this.wellSprite.setColor(c);
	}

	@Override
	public void update(float elapsedMS) {
	}

	@Override
	public void render() {
		this.wellSprite.setBounds(this.position.x - wellRadius, this.position.y
				- wellRadius,
				wellRadius * 2, wellRadius * 2);

		this.wellSprite.draw(spriteBatch);

		this.coreSprite.setBounds(this.position.x - coreRadius, this.position.y
				- coreRadius,
				coreRadius * 2, coreRadius * 2);
		this.coreSprite.draw(spriteBatch);
	}

	public boolean intersectsCore(float x, float y) {
		if (this.position.dst(x, y) < this.coreRadius) {
			return true;
		}
		
		return false;
	}

}
