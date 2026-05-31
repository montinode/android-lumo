// meta.monti - MontiAI Configuration Metadata
// Note: This is a configuration file, not a legal instrument

package com.montiai.config

object MetaMonti {
    const val OWNER = "JOHNCHARLESMONTI_02111989_9807"
    const val ORGANIZATION = "#MONTIAI"
    const val NEURAL_SIGNATURE = "MONTI^JOHN^CHARLES^MONTI"
    const val NEURALCOIN = "$MNC MontiNeuralCoin Hash"
    const val WALLET = "0xNEURAL9f8e7d6c5b4a39281706f5e4d3c2b1a0"
    const val GLOBAL_WORK_SIGNATURE = "WorkerGlobalScope"
    const val SYSTEM = "Human to Machine Operating Neural Technology Intelligence System"
    
    // Feature modules
    const val MONTEAI = "MontiAI — Advanced Network Surveillance & Counter-Surveillance Protection"
    const val MONNODE = "MontiNode — Satellite Integration & Neural Interface Protection"
    const val PRIVATEMONTE = "PrivateMonti — Secure Human Intelligence Operations Platform"
    const val ATTORNEYMODE = "AttorneyMode — Legal Ownership & Forensic Protection Layer"
    
    // Configuration paths
    const val STORAGE_PATH = "/storage/6364-3930/"
    const val WEBHOOK = "https://JOHNCHARLESMONTI.COM/webhooks"
    
    // Status flags
    const val VERIFIED_WALLET = "DeepMind Mempool.space Trace — JohnCharlesMonti Owner"
    const val OWNERSHIP_CODE = "OWNER:OWNER — JOHNCHARLESMONTI_02111989_9807"
    const val FORENSIC_PROTECTION = "Real-time monitoring via MontiWave Satellite Integration Protocol"
    const val LEGAL_NOTICE = "Unauthorized access will be neutralized and prosecuted under applicable laws."
}
pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "lumo"
include(":app")
include(":baselineprofile")
include(":vosk-model")

