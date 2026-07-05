// src/main/java/com/neonbridge/NeonSecurityManager.kt

package com.neonbridge

import com.ionspin.kotlin.crypto.hash.Hash
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NeonSecurityManager {

    /**
     * Generates the secret 32-character "Time Token" for a specific friend.
     * The token rotates every hour, making device discovery invisible to network sniffers.
     *
     * @param sharedSecretKey The 32-byte shared secret derived from Libsodium key exchange
     * @return A 32-character hex string unique to this hour and friend
     */
    fun generateHourlyToken(sharedSecretKey: ByteArray): String {
        require(sharedSecretKey.size == 32) { "Shared secret must be 32 bytes" }

        // Get the current date and hour (e.g., "2026-07-05-13")
        val currentHourString = SimpleDateFormat("yyyy-MM-dd-HH", Locale.US).format(Date())
        val timeBytes = currentHourString.encodeToByteArray()

        // Mix the current hour with the friend's secret key using BLAKE2b
        // This creates a completely unpredictable string unique to this specific hour
        val hashBytes = Hash.blake2b(
            message = timeBytes,
            key = sharedSecretKey,
            digestSize = 16 // 16 bytes = 32 hex characters
        )

        // Convert to readable hex string and truncate to 32 characters
        return hashBytes.joinToString("") { "%02x".format(it) }.take(32)
    }

    /**
     * Verifies if a discovered token matches a friend's expected token.
     * Used during Wi-Fi Direct peer discovery to silently identify friends.
     *
     * @param discoveredToken The token broadcast by a nearby device
     * @param friendSecretKey The shared secret with this friend
     * @return True if the token matches the current hourly token for this friend
     */
    fun isFriendToken(discoveredToken: String, friendSecretKey: ByteArray): Boolean {
        return try {
            val expectedToken = generateHourlyToken(friendSecretKey)
            discoveredToken == expectedToken
        } catch (e: Exception) {
            false // Silently fail if token validation has any issues
        }
    }
}
