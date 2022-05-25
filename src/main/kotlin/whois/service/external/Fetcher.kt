package whois.service.external

import org.json.JSONArray
import org.json.JSONObject
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import java.net.InetAddress
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

@Profile("whois")
@Service
class Fetcher {
    fun buildResponse(name: String?, queryLink: String?): Map<String, Map<String, String>?> {
        val domainInfo = getResponse(name, null, queryLink)
        var orgInfo: Map<String, String>? = null
        var clientInfo: Map<String, String>? = null
        if (domainInfo.containsKey("roleLink")) orgInfo = getResponse(null, domainInfo["roleLink"], null)
        if (domainInfo.containsKey("personLink")) clientInfo = getResponse(null, domainInfo["personLink"], null)
        else {
            if (orgInfo != null) {
                if (orgInfo.containsKey("personLink")) clientInfo = getResponse(null, orgInfo["personLink"], null)
            }
        }
        return mapOf(
            Pair("domainInfo", domainInfo),
            Pair("orgInfo", orgInfo),
            Pair("clientInfo", clientInfo)
        )
    }

    private fun getResponse(name: String?, link: String?, queryLink: String?): Map<String, String> {
        val address = InetAddress.getByName(name).hostAddress
        val client = HttpClient.newBuilder().build()
        val url: String?
        url = link
            ?: "$queryLink$address"
        val request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString()).body()
        val jsonObj = JSONObject(response)
        val map = jsonObj.toMap()

        return convertResponse(map)
    }

    private fun JSONObject.toMap(): Map<String, *> = keys().asSequence().associateWith { s ->
        when (val value = this[s]) {
            is JSONArray -> {
                val map = (0 until value.length()).associate { Pair(it.toString(), value[it]) }
                JSONObject(map).toMap().values.toList()
            }
            is JSONObject -> value.toMap()
            JSONObject.NULL -> null
            else -> value
        }
    }

    private fun convertResponse(response: Map<*, *>): Map<String, String> {
        val attrList = mutableMapOf<String, String>()
        val attrNameList = mutableListOf<String>()
        val attrValList = mutableListOf<String>()

        response.forEach { entry ->
            if (entry.key == "objects") {
                val tmpMap = response["objects"] as Map<String, *>
                tmpMap.forEach {
                    if (it.key == "object") {
                        val tmpList = tmpMap["object"] as List<Map<String, *>>
                        tmpList.forEach { obj ->
                            obj.forEach { el ->
                                when (el.key) {
                                    "attributes" -> {
                                        val attrs = obj["attributes"] as Map<String, *>
                                        attrs.forEach { attr ->
                                            if (attr.key == "attribute") {
                                                val at = attrs["attribute"] as List<Map<String, *>>
                                                at.forEach { j ->
                                                    j.forEach { t ->
                                                        if (t.value is String) {
                                                            if (t.key == "name") attrNameList.add(t.value as String)
                                                            if (t.key == "value") attrValList.add(t.value as String)
                                                        } else if (t.key == "link") {
                                                            (t.value as Map<String, String>).forEach { m ->
                                                                if (m.key == "href") {
                                                                    if (m.value.matches("^https://rest\\.db\\.ripe\\.net/ripe/role/.+".toRegex())) attrList["roleLink"] =
                                                                        m.value + ".json"
                                                                    if (m.value.matches("^https://rest\\.db\\.ripe\\.net/ripe/person/.+".toRegex())) attrList["personLink"] =
                                                                        m.value + ".json"
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (attrNameList.size != attrValList.size) throw Exception("Corrupted JSON")
        else {
            for (i in 0 until attrNameList.size) {
                if (attrList.containsKey(attrNameList[i]) && attrList[attrNameList[i]] != null) {
                    attrList[attrNameList[i]] += ";" + attrValList[i]
                } else {
                    attrList[attrNameList[i]] = attrValList[i]
                }
            }
        }
        return attrList.toMap()
    }
}