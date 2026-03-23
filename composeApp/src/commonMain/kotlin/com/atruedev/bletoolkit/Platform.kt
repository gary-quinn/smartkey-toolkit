package com.atruedev.bletoolkit

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform