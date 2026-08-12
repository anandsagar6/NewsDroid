# 📰 NewsDroid

A native Android news reader that delivers top headlines from around the world, powered by [NewsAPI.org](https://newsapi.org/).

Browse by category, search for any topic, read the full article in an in-app browser, and share stories with a tap — all with a built-in dark mode that persists across sessions.

![Platform](https://img.shields.io/badge/platform-Android-3DDC84)
![Language](https://img.shields.io/badge/language-Java-orange)
![Min SDK](https://img.shields.io/badge/minSdk-26-blue)
![Target SDK](https://img.shields.io/badge/targetSdk-34-blue)

---

## ✨ Features

| Feature | Description |
|---|---|
| **Animated splash screen** | Lottie-powered intro animation on launch |
| **Category browsing** | Seven one-tap filters: General, Business, Sports, Technology, Health, Science, Entertainment |
| **Keyword search** | Search bar queries top headlines for any term |
| **In-app article reader** | Full stories open in a `WebView` with a loading indicator and proper back-navigation |
| **Share anywhere** | Share article links from the feed, the reader, or invite friends from the drawer |
| **Dark mode** | Toggle in the navigation drawer header, persisted via `SharedPreferences` |
| **Navigation drawer** | Home, Invite a friend, Support, and About Us |
| **Image caching** | Article thumbnails loaded by Picasso with placeholder and error fallbacks |

---

## 🛠 Tech Stack

- **Language:** Java 8
- **Build:** Gradle 8.7 · Android Gradle Plugin 8.5.1
- **UI:** AndroidX AppCompat, Material Components 1.12, ConstraintLayout, RecyclerView, DrawerLayout
- **Networking:** [News-API-Java](https://github.com/KwabenBerko/News-API-Java) `1.0.2` (JitPack)
- **Images:** [Picasso](https://square.github.io/picasso/) `2.8`
- **Animation:** [Lottie](https://airbnb.io/lottie/) `6.3.0`

---

## 📂 Project Structure

```
NewsDroid/
├── app/
│   └── src/main/
│       ├── java/com/example/newnews/
│       │   ├── SplashActivity.java        # Lottie splash, 2s delay → MainActivity
│       │   ├── MainActivity.java          # Feed, categories, search, drawer, dark mode
│       │   ├── NewsRecyclerAdapter.java   # RecyclerView adapter + share intent
│       │   └── NewsFullActivity.java      # WebView article reader
│       ├── res/
│       │   ├── layout/                    # Activity, item, dialog, and drawer layouts
│       │   ├── menu/                      # Navigation drawer menu
│       │   ├── raw/                       # Lottie JSON animations
│       │   ├── values/ · values-night/    # Colors, strings, light & dark themes
│       │   └── drawable/ · mipmap-*/      # Icons and imagery
│       └── AndroidManifest.xml
├── build.gradle · settings.gradle · gradle.properties
└── gradlew · gradlew.bat
```

---

## 🚀 Getting Started

### Prerequisites

- **Android Studio** Koala (2024.1.1) or newer
- **JDK 17** (bundled with recent Android Studio releases)
- **Android SDK 34**
- A device or emulator running **Android 8.0 (API 26)** or higher
- A free **NewsAPI key** — grab one at [newsapi.org/register](https://newsapi.org/register)

### Installation

**1. Clone the repository**

```bash
git clone https://github.com/anandsagar6/NewsDroid.git
cd NewsDroid
```

**2. Add your NewsAPI key** (see the section below — the key is currently hardcoded)

**3. Build and run**

```bash
# macOS / Linux
./gradlew assembleDebug

# Windows
gradlew.bat assembleDebug
```

Or simply open the project in Android Studio, let Gradle sync, and hit **▶ Run**.

The debug APK lands in `app/build/outputs/apk/debug/`.

---

## 🔑 Configuring the API Key

> ⚠️ **Heads up:** the NewsAPI key is currently hardcoded in `MainActivity.getNews()` and committed to the repo. Anyone who clones this project can use — and exhaust — that key's quota. Rotate it at [newsapi.org](https://newsapi.org/) and move it out of source control using the steps below.

**1.** Add the key to `local.properties` (already listed in `.gitignore`):

```properties
NEWS_API_KEY=your_key_here
```

**2.** Expose it as a `BuildConfig` field in `app/build.gradle`:

```gradle
android {
    // ...
    buildFeatures {
        buildConfig true
    }

    defaultConfig {
        // ...
        def props = new Properties()
        def propsFile = rootProject.file('local.properties')
        if (propsFile.exists()) {
            props.load(new FileInputStream(propsFile))
        }
        buildConfigField "String", "NEWS_API_KEY",
                "\"${props.getProperty('NEWS_API_KEY', '')}\""
    }
}
```

**3.** Read it in `MainActivity.java`:

```java
NewsApiClient newsApiClient = new NewsApiClient(BuildConfig.NEWS_API_KEY);
```

---

## 📱 How It Works

1. **`SplashActivity`** plays a Lottie animation, then hands off to `MainActivity` after 2 seconds.
2. **`MainActivity`** reads the saved dark-mode preference, applies the theme, and calls `getNews("GENERAL", null)` on startup. Category buttons and the search bar both re-invoke `getNews()` with different arguments.
3. **`NewsApiClient`** fetches English-language top headlines asynchronously; results are pushed to the UI thread and handed to the adapter.
4. **`NewsRecyclerAdapter`** binds each `Article` to a card — title, source, and thumbnail — wires a share intent, and opens `NewsFullActivity` on tap.
5. **`NewsFullActivity`** loads the article URL in a `WebView`, hiding the progress indicator once the page finishes and supporting in-page back navigation.

---

## 🧭 Roadmap

Ideas for anyone extending the project:

- [ ] Move the API key out of source (see above)
- [ ] Offline caching with Room so the last feed survives a cold start
- [ ] Bookmarks / saved articles
- [ ] Pagination or infinite scroll on the headlines feed
- [ ] Pull-to-refresh with `SwipeRefreshLayout`
- [ ] Replace deprecated `onBackPressed()` with `OnBackPressedDispatcher`
- [ ] Migrate to a `ViewModel` + `LiveData` architecture
- [ ] Country/language pickers for regional headlines
- [ ] Rename the `com.example.newnews` package to a real application ID before publishing

---

## 🤝 Contributing

Contributions are welcome.

1. Fork the repository
2. Create a branch — `git checkout -b feature/your-feature`
3. Commit your changes — `git commit -m "Add your feature"`
4. Push the branch — `git push origin feature/your-feature`
5. Open a Pull Request

---

## 📄 License

No license file is currently included, which means all rights are reserved by default. If you'd like others to reuse this code, consider adding one — the [MIT License](https://choosealicense.com/licenses/mit/) is a common choice for projects like this.

---

## 🙏 Acknowledgements

- [NewsAPI.org](https://newsapi.org/) — headline data
- [KwabenBerko/News-API-Java](https://github.com/KwabenBerko/News-API-Java) — Java client
- [Picasso](https://square.github.io/picasso/) — image loading
- [Lottie](https://airbnb.io/lottie/) — animations

---

## 📬 Contact

**Anand Sagar** — anandsagar0006@gmail.com

Project link: [github.com/anandsagar6/NewsDroid](https://github.com/anandsagar6/NewsDroid)
