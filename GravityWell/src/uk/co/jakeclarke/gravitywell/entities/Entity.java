package uk.co.jakeclarke.gravitywell.entities;

public abstract class Entity {
	
	public abstract void initialise();
	
	public abstract void update(float elapsedMS);
	
	public abstract void render();
	
	public EntityManager entityManager;
	
	public boolean isInitialised = false;
	
	public boolean enabled = true;
	
	public boolean drawEnabled = true;
}
