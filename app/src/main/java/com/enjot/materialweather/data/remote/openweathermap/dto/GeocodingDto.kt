package com.enjot.materialweather.data.remote.openweathermap.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeocodingDto(
    @SerialName("name") val name: String,
    @SerialName("local_names") val localNames: LocalNames? = null,
    @SerialName("lat") val lat: Double,
    @SerialName("lon") val lon: Double,
    @SerialName("country") val country: String? = null,
    @SerialName("state") val state: String? = null
) {
    @Serializable
    data class LocalNames(
        @SerialName("feature_name") val featureName: String? = null,
        @SerialName("ascii") val ascii: String? = null,
        @SerialName("ms") val ms: String? = null,
        @SerialName("gu") val gu: String? = null,
        @SerialName("is") val isX: String? = null,
        @SerialName("wa") val wa: String? = null,
        @SerialName("mg") val mg: String? = null,
        @SerialName("gl") val gl: String? = null,
        @SerialName("om") val om: String? = null,
        @SerialName("ku") val ku: String? = null,
        @SerialName("tw") val tw: String? = null,
        @SerialName("mk") val mk: String? = null,
        @SerialName("ee") val ee: String? = null,
        @SerialName("fj") val fj: String? = null,
        @SerialName("gd") val gd: String? = null,
        @SerialName("ky") val ky: String? = null,
        @SerialName("yo") val yo: String? = null,
        @SerialName("zu") val zu: String? = null,
        @SerialName("bg") val bg: String? = null,
        @SerialName("tk") val tk: String? = null,
        @SerialName("co") val co: String? = null,
        @SerialName("sh") val sh: String? = null,
        @SerialName("de") val de: String? = null,
        @SerialName("kl") val kl: String? = null,
        @SerialName("bi") val bi: String? = null,
        @SerialName("km") val km: String? = null,
        @SerialName("lt") val lt: String? = null,
        @SerialName("fi") val fi: String? = null,
        @SerialName("fy") val fy: String? = null,
        @SerialName("ba") val ba: String? = null,
        @SerialName("sc") val sc: String? = null,
        @SerialName("ja") val ja: String? = null,
        @SerialName("am") val am: String? = null,
        @SerialName("sk") val sk: String? = null,
        @SerialName("mr") val mr: String? = null,
        @SerialName("es") val es: String? = null,
        @SerialName("sq") val sq: String? = null,
        @SerialName("te") val te: String? = null,
        @SerialName("br") val br: String? = null,
        @SerialName("uz") val uz: String? = null,
        @SerialName("da") val da: String? = null,
        @SerialName("sw") val sw: String? = null,
        @SerialName("fa") val fa: String? = null,
        @SerialName("sr") val sr: String? = null,
        @SerialName("cu") val cu: String? = null,
        @SerialName("ln") val ln: String? = null,
        @SerialName("na") val na: String? = null,
        @SerialName("wo") val wo: String? = null,
        @SerialName("ig") val ig: String? = null,
        @SerialName("to") val to: String? = null,
        @SerialName("ta") val ta: String? = null,
        @SerialName("mt") val mt: String? = null,
        @SerialName("ar") val ar: String? = null,
        @SerialName("su") val su: String? = null,
        @SerialName("ab") val ab: String? = null,
        @SerialName("ps") val ps: String? = null,
        @SerialName("bm") val bm: String? = null,
        @SerialName("mi") val mi: String? = null,
        @SerialName("kn") val kn: String? = null,
        @SerialName("kv") val kv: String? = null,
        @SerialName("os") val os: String? = null,
        @SerialName("bn") val bn: String? = null,
        @SerialName("li") val li: String? = null,
        @SerialName("vi") val vi: String? = null,
        @SerialName("zh") val zh: String? = null,
        @SerialName("eo") val eo: String? = null,
        @SerialName("ha") val ha: String? = null,
        @SerialName("tt") val tt: String? = null,
        @SerialName("lb") val lb: String? = null,
        @SerialName("ce") val ce: String? = null,
        @SerialName("hu") val hu: String? = null,
        @SerialName("it") val it: String? = null,
        @SerialName("tl") val tl: String? = null,
        @SerialName("pl") val pl: String? = null,
        @SerialName("sm") val sm: String? = null,
        @SerialName("en") val en: String? = null,
        @SerialName("vo") val vo: String? = null,
        @SerialName("el") val el: String? = null,
        @SerialName("sn") val sn: String? = null,
        @SerialName("fr") val fr: String? = null,
        @SerialName("cs") val cs: String? = null,
        @SerialName("io") val io: String? = null,
        @SerialName("hi") val hi: String? = null,
        @SerialName("et") val et: String? = null,
        @SerialName("pa") val pa: String? = null,
        @SerialName("av") val av: String? = null,
        @SerialName("ko") val ko: String? = null,
        @SerialName("bh") val bh: String? = null,
        @SerialName("yi") val yi: String? = null,
        @SerialName("sa") val sa: String? = null,
        @SerialName("sl") val sl: String? = null,
        @SerialName("hr") val hr: String? = null,
        @SerialName("si") val si: String? = null,
        @SerialName("so") val so: String? = null,
        @SerialName("gn") val gn: String? = null,
        @SerialName("ay") val ay: String? = null,
        @SerialName("se") val se: String? = null,
        @SerialName("sd") val sd: String? = null,
        @SerialName("af") val af: String? = null,
        @SerialName("ga") val ga: String? = null,
        @SerialName("or") val or: String? = null,
        @SerialName("ia") val ia: String? = null,
        @SerialName("ie") val ie: String? = null,
        @SerialName("ug") val ug: String? = null,
        @SerialName("nl") val nl: String? = null,
        @SerialName("gv") val gv: String? = null,
        @SerialName("qu") val qu: String? = null,
        @SerialName("be") val be: String? = null,
        @SerialName("an") val an: String? = null,
        @SerialName("fo") val fo: String? = null,
        @SerialName("hy") val hy: String? = null,
        @SerialName("nv") val nv: String? = null,
        @SerialName("bo") val bo: String? = null,
        @SerialName("id") val id: String? = null,
        @SerialName("lv") val lv: String? = null,
        @SerialName("ca") val ca: String? = null,
        @SerialName("no") val no: String? = null,
        @SerialName("nn") val nn: String? = null,
        @SerialName("ml") val ml: String? = null,
        @SerialName("my") val my: String? = null,
        @SerialName("ne") val ne: String? = null,
        @SerialName("he") val he: String? = null,
        @SerialName("cy") val cy: String? = null,
        @SerialName("lo") val lo: String? = null,
        @SerialName("jv") val jv: String? = null,
        @SerialName("sv") val sv: String? = null,
        @SerialName("mn") val mn: String? = null,
        @SerialName("tg") val tg: String? = null,
        @SerialName("kw") val kw: String? = null,
        @SerialName("cv") val cv: String? = null,
        @SerialName("az") val az: String? = null,
        @SerialName("oc") val oc: String? = null,
        @SerialName("th") val th: String? = null,
        @SerialName("ru") val ru: String? = null,
        @SerialName("ny") val ny: String? = null,
        @SerialName("bs") val bs: String? = null,
        @SerialName("st") val st: String? = null,
        @SerialName("ro") val ro: String? = null,
        @SerialName("rm") val rm: String? = null,
        @SerialName("ff") val ff: String? = null,
        @SerialName("kk") val kk: String? = null,
        @SerialName("uk") val uk: String? = null,
        @SerialName("pt") val pt: String? = null,
        @SerialName("tr") val tr: String? = null,
        @SerialName("eu") val eu: String? = null,
        @SerialName("ht") val ht: String? = null,
        @SerialName("ka") val ka: String? = null,
        @SerialName("ur") val ur: String? = null
    )
}