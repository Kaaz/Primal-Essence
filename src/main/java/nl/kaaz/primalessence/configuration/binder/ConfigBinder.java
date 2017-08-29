package nl.kaaz.primalessence.configuration.binder;

import com.google.common.base.Joiner;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import nl.kaaz.primalessence.PrimalEssence;
import nl.kaaz.primalessence.configuration.binder.annotations.Comment;
import nl.kaaz.primalessence.configuration.binder.annotations.Ignore;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ConfigBinder {
	private static Configuration config;

	public static void preInit(FMLPreInitializationEvent event, Class classToBind) {
		config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		for (Class<?> sub : classToBind.getClasses()) {
			if (sub.getAnnotation(Ignore.class) != null) {
				continue;
			}
			String category = sub.getSimpleName();
			Comment catComment = sub.getAnnotation(Comment.class);
			if (catComment != null) {
				config.getCategory(category).setComment(Joiner.on("\n").join(catComment.value()));
			}
			for (Field field : sub.getFields()) {
				if (!Modifier.isStatic(field.getModifiers()) || !Modifier.isPublic(field.getModifiers())) {
					continue;
				}
				if (field.getAnnotation(Ignore.class) != null) {
					continue;
				}
				ConfigManager.createConfig(PrimalEssence.ID, category, config, field.getType(), field, null);
			}
		}
		config.save();
	}
}
