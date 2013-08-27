package uk.co.jakeclarke.gravitywell.ui;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public final class UIManager {

	private ArrayList<UIElement> elements = new ArrayList<UIElement>();
	
	public SpriteBatch uiBatch;

	public boolean handleInput(int x, int y) {
		for (UIElement e : this.elements) {
			if (e.enabled && e.handleInput(x, y))
				return true;
		}
		
		return false;
	}

	private UIElement[] buff = new UIElement[0];
	public void draw() {
		// using a buff means that an element can remove it self.
		this.elements.toArray(this.buff);

		for (UIElement e : buff) {
			if (e.enabled)
				e.draw();
		}
	}

	public void addElement(UIElement element) {
		element.uiManager = this;
		this.elements.add(element);
	}

	public void removeElement(UIElement element) {
		this.elements.remove(element);
	}
}
