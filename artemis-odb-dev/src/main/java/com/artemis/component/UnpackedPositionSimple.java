package com.artemis.component;

import java.nio.ByteBuffer;
import java.util.IdentityHashMap;
import java.util.Map;

import com.artemis.Entity;
import com.artemis.PackedComponent;
import com.artemis.PackedComponent.DisposedWithWorld;
import com.artemis.World;
import com.artemis.util.Vec2f;
import com.artemis.utils.Bag;

public class UnpackedPositionSimple extends PackedComponent {

	private int $stride;
	private static int $_SIZE_OF = 8;
	private static ByteBuffer $data = ByteBuffer.allocateDirect(128 * $_SIZE_OF);
	

	@Override
	protected PackedComponent forEntity(Entity e) {
		this.$stride = $_SIZE_OF * e.getId();
		if (($data.capacity() - $_SIZE_OF) <= $stride) $grow();
		return this;
	}

	@Override
	protected void reset() {
		$data.putFloat($stride + 0, 0);
		$data.putFloat($stride + 4, 0);
	}
	
	private static void $grow()
	{
		ByteBuffer newBuffer = ByteBuffer.allocateDirect($data.capacity() * 2);
		
		for (int i = 0, s = $data.capacity(); s > i; i++)
			newBuffer.put(i, $data.get(i));
		
		$data = newBuffer;
	}
	
	public float x() {
		return $data.getFloat($stride + 0);
	}
	
	public float y() {
		return $data.getFloat($stride + 4);
	}
	
	public UnpackedPositionSimple x(float value) {
		$data.putFloat($stride + 0, value);
		return this;
	}
	
	public void y(float value) {
		$data.putFloat($stride + 4, value);
	}
}