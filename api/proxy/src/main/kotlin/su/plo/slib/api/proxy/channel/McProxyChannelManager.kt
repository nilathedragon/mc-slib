package su.plo.slib.api.proxy.channel

/**
 * Manages custom Minecraft proxy channels and their associated handlers.
 */
interface McProxyChannelManager {

    /**
     * Registers a custom channel [handler] for the specified [channel].
     *
     * @param channel The name of the custom channel to register the handler for.
     * @param handler The [McProxyChannelHandler] implementation that will process messages received on the channel.
     */
    fun registerChannelHandler(channel: String, handler: McProxyChannelHandler)

    /**
     * Unregisters a custom channel [handler].
     *
     * @param channel The name of the custom channel to unregister the handler for.
     * @param handler The channel handler to unregister.
     */
    fun unregisterChannelHandler(channel: String, handler: McProxyChannelHandler)

    /**
     * Unregisters all custom channel handlers.
     */
    fun clear()
}
