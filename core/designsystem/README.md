# Design System

This module exposes a Compose-native design system centered on `TapTapTheme`. Wrap your screen tree with the theme once:

```kotlin
TapTapTheme {
    // your composables here
}
```

Use the thematic helpers instead of duplicating static values:

- `TapTapTheme.colors` − accesses the current `ColorScheme`.
- `TapTapTheme.typography` − references the shared `TapTapTypography` object (display → label hierarchy backed by `PPNeu`).
- `TapTapTheme.spacing` − exposes the spacing scale (`xSmall` → `xxLarge`, plus `iconButton`, `cardPadding`, `sectionSpacing`).

Each component should read these values instead of hard-coding colors, fonts, spacing, or corner radii to keep the visuals consistent and avoid boilerplate.

Besides the Material wrappers, the module exposes token sets for direct access:

- `TapTapTokens.lightColors` and `TapTapTokens.darkColors` hold the palette primitives (`primary`, `onPrimary`, `secondary`, `background`, `surface`, `error`, etc.).
- `TapTapTypography` lets you read the entire text style scale without rebuilding styles in every screen.
- `TapTapShape.corners` exposes semantic corner radii along with the Material `TapTapShapes` instance used for `MaterialTheme.shapes`.
