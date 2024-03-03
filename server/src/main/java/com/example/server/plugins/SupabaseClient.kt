package com.example.server.plugins
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.cdimascio.dotenv.Dotenv
import io.github.jan.supabase.serializer.JacksonSerializer

object SupabaseClient {
    val dotenv = Dotenv.load()
    private val supabaseUrl = dotenv["SUPABASE_URL"] ?: error("SUPABASE_URL not set")
    private val supabaseKey = dotenv["SUPABASE_ANON_KEY"] ?: error("SUPABASE_ANON_KEY not set")

    val supabase = createSupabaseClient(supabaseUrl, supabaseKey) {
        defaultSerializer = JacksonSerializer()
        install(Auth) {
            alwaysAutoRefresh = false // default: true
            autoLoadFromStorage = false // default: true
        }
    }
}