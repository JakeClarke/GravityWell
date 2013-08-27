package uk.co.jakeclarke.gravitywell.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import uk.co.jakeclarke.gravitywell.GravityWellGame;

public final class EntityManager {
	
	public final GravityWellGame game;
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	private ArrayList<Entity> drawSortedEntityies = new ArrayList<Entity>();
	private ArrayList<Entity> updateSortedEntityies = new ArrayList<Entity>();
	private boolean isInitialised = false;

	private boolean updateOrderDirty = false;
	private boolean drawOrderDirty = false;

	public EntityManager(GravityWellGame game) {
		this.game = game;
	}

	public void add(Entity e) {
		e.entityManager = this;
		if (this.isInitialised)
			e.initialise();
		
		this.entities.add(e);

		this.updateOrderDirty = true;
		this.drawOrderDirty = true;
	}
	
	public void remove(Entity e) {
		this.entities.remove(e);
	}

	private Entity[] buff = new Entity[0];
	public void update(final float elapsedMS) {
		if (!this.isInitialised) {
			initialise();
		}

		if (this.updateOrderDirty) {
			
			this.updateSortedEntityies.clear();

			this.updateSortedEntityies.addAll(this.entities);
			
			Collections.sort(this.updateSortedEntityies,
					new Comparator<Entity>() {

						@Override
						public int compare(Entity o1, Entity o2) {
							return o1.updateOrder - o2.updateOrder;
						}

			});
			

			this.updateOrderDirty = false;
		}

		this.buff = this.updateSortedEntityies.toArray(this.buff);
		for (Entity e : buff) {
			if (e == null)
				break;

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
		if (this.drawOrderDirty) {

			this.drawSortedEntityies.clear();

			this.drawSortedEntityies.addAll(this.entities);

			Collections.sort(this.drawSortedEntityies,
					new Comparator<Entity>() {

						@Override
						public int compare(Entity o1, Entity o2) {
							return o1.drawOrder - o2.drawOrder;
						}

					});

			this.drawOrderDirty = false;
		}

		for (Entity e : this.entities) {
			if (e.drawEnabled)
				e.render();
		}
	}

	public void updateOrderChanged() {
		this.updateOrderDirty = true;
	}

	public void drawOrderChanged() {
		this.drawOrderDirty = true;
	}
}
