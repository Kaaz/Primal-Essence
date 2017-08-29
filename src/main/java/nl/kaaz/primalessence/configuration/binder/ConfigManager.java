package nl.kaaz.primalessence.configuration.binder;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import nl.kaaz.primalessence.configuration.binder.annotations.Comment;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

public class ConfigManager {
	private static final Joiner NEW_LINE = Joiner.on('\n');
	private static final Joiner PIPE = Joiner.on('|');
	private static Map<Class<?>, ITypeAdapter> ADAPTERS = Maps.newHashMap();
	private static Map<Class<?>, ITypeAdapter.Map> MAP_ADAPTERS = Maps.newHashMap();

	static {
		register(boolean.class, TypeAdapters.bool);
		register(boolean[].class, TypeAdapters.boolA);
		register(Boolean.class, TypeAdapters.Bool);
		register(Boolean[].class, TypeAdapters.BoolA);
		register(float.class, TypeAdapters.flt);
		register(float[].class, TypeAdapters.fltA);
		register(Float.class, TypeAdapters.Flt);
		register(Float[].class, TypeAdapters.FltA);
		register(double.class, TypeAdapters.dbl);
		register(double[].class, TypeAdapters.dblA);
		register(Double.class, TypeAdapters.Dbl);
		register(Double[].class, TypeAdapters.DblA);
		register(byte.class, TypeAdapters.byt);
		register(byte[].class, TypeAdapters.bytA);
		register(Byte.class, TypeAdapters.Byt);
		register(Byte[].class, TypeAdapters.BytA);
		register(char.class, TypeAdapters.chr);
		register(char[].class, TypeAdapters.chrA);
		register(Character.class, TypeAdapters.Chr);
		register(Character[].class, TypeAdapters.ChrA);
		register(short.class, TypeAdapters.shrt);
		register(short[].class, TypeAdapters.shrtA);
		register(Short.class, TypeAdapters.Shrt);
		register(Short[].class, TypeAdapters.ShrtA);
		register(int.class, TypeAdapters.int_);
		register(int[].class, TypeAdapters.intA);
		register(Integer.class, TypeAdapters.Int);
		register(Integer[].class, TypeAdapters.IntA);
		register(String.class, TypeAdapters.Str);
		register(String[].class, TypeAdapters.StrA);
	}

	private static void register(Class<?> cls, ITypeAdapter adpt) {
		ADAPTERS.put(cls, adpt);
		if (adpt instanceof ITypeAdapter.Map)
			MAP_ADAPTERS.put(cls, (ITypeAdapter.Map) adpt);
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	public static void createConfig(String modid, String category, Configuration cfg, Class<?> ftype, Field f, Object instance) {
		Property prop = null;

		String comment = null;
		Comment ca = f.getAnnotation(Comment.class);
		if (ca != null)
			comment = NEW_LINE.join(ca.value());

		String langKey = modid + "." + category + "." + f.getName().toLowerCase(Locale.ENGLISH);

		ITypeAdapter adapter = ADAPTERS.get(ftype);

		if (adapter != null) {
			prop = adapter.getProp(cfg, category, f, instance, comment);
			set(instance, f, adapter.getValue(prop));
		} else if (ftype.getSuperclass() == Enum.class) {
			Enum enu = (Enum) get(instance, f);
			prop = cfg.get(category, f.getName(), enu.name(), comment);
			prop.setValidationPattern(makePattern((Class<? extends Enum>) ftype));
			set(instance, f, Enum.valueOf((Class<? extends Enum>) ftype, prop.getString()));
		} else if (ftype == Map.class) {
			String sub = category + "." + f.getName().toLowerCase(Locale.ENGLISH);
			Map<String, Object> m = (Map<String, Object>) get(instance, f);
			ParameterizedType type = (ParameterizedType) f.getGenericType();
			Type mtype = type.getActualTypeArguments()[1];

			cfg.getCategory(sub).setComment(comment);

			for (Map.Entry<String, Object> e : m.entrySet()) {
				ITypeAdapter.Map adpt = MAP_ADAPTERS.get(mtype);

				if (adpt != null) {
					prop = adpt.getProp(cfg, sub, e.getKey(), e.getValue());
				} else if (mtype instanceof Class && ((Class<?>) mtype).getSuperclass() == Enum.class) {
					prop = TypeAdapters.Str.getProp(cfg, sub, e.getKey(), ((Enum) e.getValue()).name());
					prop.setValidationPattern(makePattern((Class<? extends Enum>) mtype));
				} else
					throw new RuntimeException("Unknown type in map! " + f.getDeclaringClass() + "/" + f.getName() + " " + mtype);

				prop.setLanguageKey(langKey + "." + e.getKey().toLowerCase(Locale.ENGLISH));

			}
			prop = null;
		} else if (ftype.getSuperclass() == Object.class) //Only support classes that are one level below Object.
		{
			String sub = category + "." + f.getName().toLowerCase(Locale.ENGLISH);
			Object sinst = get(instance, f);
			for (Field sf : ftype.getDeclaredFields()) {
				if (!Modifier.isPublic(sf.getModifiers()))
					continue;

				createConfig(modid, sub, cfg, sf.getType(), sf, sinst);
			}
		} else
			throw new RuntimeException("Unknown type in config! " + f.getDeclaringClass() + "/" + f.getName() + " " + ftype);


		if (prop != null) {
			prop.setLanguageKey(langKey);
		}
	}

	private static void set(Object instance, Field f, Object v) {
		try {
			f.set(instance, v);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Object get(Object instance, Field f) {
		try {
			return f.get(instance);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	private static Pattern makePattern(Class<? extends Enum> cls) {
		List<String> lst = Lists.newArrayList();
		for (Enum e : cls.getEnumConstants())
			lst.add(e.name());
		return Pattern.compile(PIPE.join(lst));
	}

}