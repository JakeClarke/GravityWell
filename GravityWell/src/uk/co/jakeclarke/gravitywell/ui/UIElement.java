package uk.co.jakeclarke.gravitywell.ui;

public abstract class UIElement {

	public UIManager uiManager;

	public boolean enabled = true;

	public abstract void initialise();

	public abstract boolean handleInput(int x, int y);

	public abstract void draw();

}
