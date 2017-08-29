package nl.kaaz.primalessence.configuration.binder;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.lang.reflect.Field;

interface ITypeAdapter {
	Property getProp(Configuration cfg, String category, Field field, Object instance, String comment);

	Object getValue(Property prop);

	public interface Map extends ITypeAdapter {
		Property getProp(Configuration cfg, String category, String name, Object value);
	}
}
