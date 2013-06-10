package uk.co.jakeclarke.gravitywell.entities;

import java.util.ArrayList;

import uk.co.jakeclarke.gravitywell.GravityWellGame;

public final class EntityManager {
	
	public final GravityWellGame game;
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	private boolean isInitialised = false;

	public EntityManager(GravityWellGame game) {
		this.game = game;
	}

	public void add(Entity e) {
		e.entityManager = this;
		if (this.isInitialised)
			e.initialise();
		
		this.entities.add(e);
	}
	
	public void remove(Entity e) {
		this.entities.remove(e);
	}

	public void update(final float elapsedMS) {
		if (!this.isInitialised) {
			initialise();
		}

		for (Entity e : this.entities) {
			if (e.enabled)
				e.update(elapsedMS);
		}
	}

	public void initialise() {
		for (Entity e : this.entities) {
			e.initialise();
		}

		this.isInitialised = true;
	}
	
	public void render() {
		for (Entity e : this.entities) {
			if (e.drawEnabled)
				e.render();
		}
	}
}
