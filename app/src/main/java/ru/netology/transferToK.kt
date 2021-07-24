package ru.netology

fun transferToK(counts: Int): String {

    var conuntK: String
    conuntK = when (counts) {
        in 0..999 -> counts.toString()
        in 1000..9999 -> (counts / 1000).toString() + if ((counts % 1000) / 100 == 0) "" else {
            "." + (counts % 1000) / 100
        } + "K"
        in 10000..99999 -> (counts / 1000).toString() + "K"
        in 100000..999999 -> (counts / 100000).toString() + "." + (counts % 100000) / 10000 + "M"

        else -> "много"
    }
    return conuntK
}