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
}# CLAUDE.md

Add the plans to a new folder in the obsidian directory
This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build Commands

The app has three flavor dimensions: `environment` (production/noble), `service` (gms/noGms), and `debugging` (standard/noWebViewDebug).

```bash
# Common debug builds
./gradlew assembleNobleGmsDebug           # Test environment with GMS (used in CI)
./gradlew assembleNobleNoGmsDebug         # Test environment without GMS
./gradlew assembleProductionStandardGmsDebug  # Production debug with GMS

# Release builds
./gradlew bundleProductionStandardGmsRelease    # Production AAB (Play Store)
./gradlew assembleProductionStandardNoGmsRelease # Production APK (NoGMS/F-Droid)

# Static analysis
./gradlew detektNobleGmsDebug

# Unit tests
./gradlew testDebugUnitTest

# Run a single test class
./gradlew testDebugUnitTest --tests "me.proton.android.lumo.SomeTest"
```

- `noble` = test/staging environment; `production` = production
- `gms` = includes Google Play Billing + Sentry; `noGms` = GrapheneOS-compatible
- `noWebViewDebug` = disables WebView remote debugging (for GrapheneOS flavor)
- Release signing requires env vars: `LUMO_KEY_ALIAS`, `LUMO_KEY_PASSWORD`, `LUMO_KEYSTORE`, `LUMO_KEYSTORE_PASSWORD`

## Architecture

The app is a native Android shell around a web application served in a WebView. The main architectural flow:

```
MainActivity (single Activity)
  └── NavHost (Jetpack Compose)
       └── MainScreen → WebView (renders the Lumo web app)
                        ↑
               JS ↔ Android bridge (WebAppInterface)
```

**Key architectural layers:**

- **WebView layer** (`webview/`): `LumoWebClient` + `LumoChromeClient` manage the WebView lifecycle. `WebAppInterface` is the JS↔Android bridge — JavaScript calls Android methods here for payments, theme, prompts. `JsInjector` injects data/functions into the page via `evaluateJavascript`. The GMS variant uses `WebAppWithPaymentsInterface` (extends `WebAppInterface`) to add billing.

- **ViewModel layer**: `MainActivityViewModel` holds the primary UI state (`StateFlow<MainUiState>`) and one-time events (`Channel<UiEvent>.receiveAsFlow()`). `SpeechViewModel` manages speech recording state separately. Pattern: `@HiltViewModel`, coroutines, `StateFlow` for state, `Channel` for events.

- **Speech layer** (`speech/`): `SpeechRecognitionManager` auto-falls back across three engines: `OnDeviceSpeechRecognizer` (Android 13+ on-device ML) → `VoskSpeechRecognizer` (bundled model, offline) → `GoogleSpeechRecognizer`. `SpeechRepository` receives transcribed text and injects it into the WebView via `WebAppInterface.injectSpeechOutput()`.

- **Repository layer** (`data/repository/`): `ThemeRepository` persists theme via `SharedPreferences` and injects it via JS. `WebAppRepository` handles web app URL and page events.

- **DI** (`di/`): Hilt modules. `AppModule` provides `SharedPreferences`, `ActivityProvider`. `SpeechModule` provides speech recognizer implementations. Interfaces are bound in `AppBinder`/`SpeechBinder`.

- **AssistActivity** (`ui/assist/`): Translucent overlay Activity registered for `ACTION_ASSIST`. Shows a bottom-sheet `MessageComposer`, auto-starts recording. On submit, launches `MainActivity` with `EXTRA_PROMPT` — `MainActivity.onNewIntent` then injects the prompt via `webBridge.injectSpeechOutput()`.

## Key Conventions

- **UiText**: Use `UiText.ResText(R.string.x)` for string resources, `.asString()` inside composables, `.getText(context)` outside composables.
- **Events from ViewModel**: Collect in composables via `LaunchedEffect + repeatOnLifecycle + collectLatest`.
- **JS injection**: Escape `\`, `"`, `'`, `\n`, `\r` before passing strings to `injectSpeechOutput()` (see `formatTextForJsInjection()`).
- **Build flavor gating**: Use `BuildConfig` fields (e.g., `BuildConfig.PAYMENTS_ENABLED`) rather than checking flavor names directly.
- **Tests**: JUnit 5 (`@Test` from `org.junit.jupiter.api`), `useJUnitPlatform()` is configured. Use MockK for mocking, Turbine for Flow testing, Truth for assertions.
- **Detekt**: Config in `config/detekt/detekt.yml`. Max cyclomatic complexity is 14. Run detekt before submitting changes.
