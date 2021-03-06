package com.artemis.system.iterating;//

import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.artemis.component.PlainPosition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

@Wire
public final class CompositionManglerSystem extends BaseSystem {

	int[] ids; // = new int[ENTITY_COUNT];

	private static int ENTITY_COUNT = 0;
	private static int RENEW = 0;

	private int index;

	private Random rng;

	ComponentMapper<PlainPosition> positionMapper;

	@SuppressWarnings("unchecked")
	public CompositionManglerSystem(long seed, int entityCount) {
		rng = new Random(seed);
		ENTITY_COUNT = entityCount;
		RENEW = ENTITY_COUNT / 4;

		ArrayList<Integer> idsList = new ArrayList<Integer>();
		for (int i = 0; ENTITY_COUNT > i; i++)
			idsList.add(i);
		Collections.shuffle(idsList);

		ids = new int[ENTITY_COUNT];
		for (int i = 0; ids.length > i; i++)
			ids[i] = idsList.get(i);
	}

	@Override
	protected void initialize() {
		for (int i = 0; ENTITY_COUNT > i; i++)
			world.createEntity();
	}

	@Override
	protected void processSystem() {

		for (int i = 0; RENEW > i; i++) {
			int e = ids[index++];
			if (positionMapper.has(e)) {
				world.edit(e).remove(PlainPosition.class);
			} else {
				world.edit(e).create(PlainPosition.class);
			}
			index = index % ENTITY_COUNT;
		}
	}
}
