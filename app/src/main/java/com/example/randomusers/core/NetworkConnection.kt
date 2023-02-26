package com.example.randomusers.core

import android.util.Log
import java.net.InetAddress

class NetworkConnection {
    fun isConnected(): Boolean {
        return try {
            val ipAddress: InetAddress = InetAddress.getByName("google.com")
            //You can replace it with your name
            !ipAddress.equals("")
        } catch (e: Exception) {
            Log.e("NetworkConnection","$e error")
            false
        }
    }
}