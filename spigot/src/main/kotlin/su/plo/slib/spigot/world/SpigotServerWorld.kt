package su.plo.slib.spigot.world

import org.bukkit.World
import org.bukkit.entity.Entity
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.util.Vector
import su.plo.slib.api.server.entity.McServerEntity
import su.plo.slib.api.server.world.McServerWorld
import su.plo.slib.spigot.extension.runSync
import su.plo.slib.spigot.util.GameEventUtil.parseGameEvent
import java.util.*

class SpigotServerWorld(
    private val loader: JavaPlugin,
    private val level: World
) : McServerWorld {

    override val name: String = level.name

    override fun sendGameEvent(entity: McServerEntity, gameEvent: String) {
        val gameEventClass = try {
            Class.forName("org.bukkit.GameEvent")
        } catch (e: ClassNotFoundException) {
            return
        }

        val paperEntity = entity.getInstance<Entity>()
        loader.runSync(paperEntity) {
            val sendGameEventMethod = level.javaClass.getMethod(
                "sendGameEvent",
                Entity::class.java,
                gameEventClass,
                Vector::class.java
            )

            sendGameEventMethod.invoke(level, paperEntity, parseGameEvent(gameEvent), paperEntity.location.toVector())
        }
    }

    override fun <T> getInstance() = level as T

    override fun equals(other: Any?) =
        if (this === other) {
            true
        } else if (other != null && this.javaClass == other.javaClass) {
            val world = other as SpigotServerWorld
            level === world.level
        } else {
            false
        }

    override fun hashCode() = Objects.hash(level)
}
