package io.github.vichid.reporter

import com.mixpanel.mixpanelapi.ClientDelivery
import com.mixpanel.mixpanelapi.MessageBuilder
import com.mixpanel.mixpanelapi.MixpanelAPI
import org.json.JSONObject

class MixpanelReporter(
    private val mixpanelToken: String,
    private val mixpanelAPI: MixpanelAPI = MixpanelAPI()
) {
    fun report(event: MixpanelEvent) {
        val messageBuilder = MessageBuilder(mixpanelToken)
        val jsonObject: JSONObject =
            messageBuilder.event("build_main", event.name, JSONObject(event.properties))
        val delivery = ClientDelivery()
        delivery.addMessage(jsonObject)
        mixpanelAPI.deliver(delivery)
    }
}
