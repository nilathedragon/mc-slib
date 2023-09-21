package su.plo.slib.spigot.world

import org.bukkit.World
import org.bukkit.entity.Entity
import org.bukkit.plugin.java.JavaPlugin
import su.plo.slib.api.server.entity.McServerEntity
import su.plo.slib.api.server.world.McServerWorld
import su.plo.slib.spigot.extension.runSync
import su.plo.slib.spigot.util.GameEventUtil
import su.plo.slib.spigot.util.GameEventUtil.parseGameEvent
import java.util.*

class SpigotServerWorld(
    private val loader: JavaPlugin,
    private val level: World
) : McServerWorld {

    override val key: String
        get() = level.key.toString()

    override fun sendGameEvent(entity: McServerEntity, gameEvent: String) {
        if (!GameEventUtil.isGameEventsSupported()) return

        val paperEntity = entity.getInstance<Entity>()
        loader.runSync(paperEntity) {
            level.sendGameEvent(paperEntity, parseGameEvent(gameEvent), paperEntity.location.toVector())
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
