# tutorial_compose

Starter Jetpack Compose app with multi-module + Clean Architecture, Koin DI, and build logic conventions.

## Tech highlights
- Kotlin 2.2, Compose UI, Material3.
- Koin for DI, Kotlinx Serialization, Paging 3 (common + Compose UI in features).
- Modular Gradle setup with convention plugins under `build-logic/`.
- Kotlinx Coroutines, Room (database placeholder), Ktor client for network.

## Module map
```mermaid
graph TD
    App["app"] --> FeatureGame["feature:game"]
    App --> FeatureSearch["feature:search"]

    FeatureGame --> CoreDomain
    FeatureSearch --> CoreDomain
    App --> CoreDesign["core:designsystem"]
    App --> CoreNavigation
    App --> CoreViewModel
    App --> CoreData
    App --> CoreDatabase
    App --> CoreNetwork

    CoreDomain["core:domain (use cases + repo interfaces + models)"] --> CoreModel["core:model"]
    CoreData["core:data (repo impl)"] --> CoreDomain
    CoreData --> CoreNetwork["core:network"]
    CoreData --> CoreDatabase["core:database"]
    CoreViewModel["core:viewmodel (base VM helpers + loaders)"] --> CoreDesign
```

## Layering rules (Clean Architecture)
- **Domain**: use cases + repository interfaces + pure models; no Android/Compose/DI.
- **Data**: implements domain repositories, talks to network/database; depends on domain interfaces + model; no UI/navigation.
- **Presentation (feature modules)**: ViewModels/Compose screens; depend on domain use cases + designsystem + navigation + viewmodel helpers.
- **App**: DI wiring (Koin), navigation host, theming; pulls features + core modules.

## Data/flow examples

Search placeholder flow:
```mermaid
flowchart LR
    UI["Search/Game UI"] --> VM["Search/Game ViewModel"]
    VM --> UC["GetSearchPlaceholderFlowUseCase"]
    UC --> Repo["SearchRepository (domain iface)"]
    Repo --> Impl["SearchRepositoryImpl (data)"]
    Impl --> Net["TapTapService (network)"]
    Net --> Impl
    Impl --> Repo
    Repo --> UC
    UC --> VM
    VM --> UI
```

Games paging flow:
```mermaid
flowchart LR
    UI["Game UI (PagingItems)"] --> VM["GameViewModel"]
    VM --> UC["GetGameFlowUseCase"]
    UC --> Repo["GamesRepository impl (data)"]
    Repo --> Pager["PagingSource (CursorPagingSource)"]
    Pager --> Net["TapTapService"]
    Net --> Pager
    Pager --> Repo
    Repo --> UC
    UC --> VM
    VM --> UI
```

## Build & run
- Sync/build: `./gradlew :app:assembleDebug`
- Lint/format: KtLint plugin is applied in app; run `./gradlew ktlintCheck`

## Conventions
- Apply module plugins (`compose.taptap.android.*`) from `build-logic/`.
- Feature plugin auto-adds core deps (designsystem, navigation, viewmodel, domain) – add extra deps locally only when needed.
- Keep domain free of Android/Compose; map DTOs to domain models in data layer.

## F-Droid Deployment

This project is configured for F-Droid deployment.

### Metadata
The app metadata for F-Droid is located in `fastlane/metadata/android/en-US/`. You can update the following files:
- `title.txt`: The name of the app.
- `short_description.txt`: A short summary of the app.
- `full_description.txt`: The full description of the app.
- `images/`: (Optional) Add screenshots here (phoneScreenshots, sevenInchScreenshots, etc.) if you want to include them.

### License
This project is licensed under the Apache 2.0 License. See `LICENSE` for details.

### Building for F-Droid
F-Droid builds the app from source. Ensure all dependencies are open source and available in public repositories (Maven Central, Google Maven, etc.).
To verify the build locally:
```bash
./gradlew assembleRelease
```
