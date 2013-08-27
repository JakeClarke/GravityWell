package uk.co.jakeclarke.gravitywell.ui;

import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Button extends UIElement {

	public float x, y, width, height;
	public TextureRegion buttonImage;

	private TouchListener listener = null;

	@Override
	public void initialise() {

	}

	@Override
	public boolean handleInput(int x, int y) {
		if (x > this.x && x < this.x + this.width && y > this.y
				&& y < this.y + this.height) {
			if (this.listener != null) {
				this.listener.onTouch(this);
			}
			return true;
		}

		return false;
	}

	@Override
	public void draw() {
		this.uiManager.uiBatch.draw(this.buttonImage, x, y, width, height);
	}

	public void setListener(TouchListener listener) {
		this.listener = listener;
	}

	public static abstract class TouchListener {
		
		public abstract void onTouch(Button button);
	}

}
