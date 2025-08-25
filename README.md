# 🎬 GenciDev Android Test Project

A simple Android application built with **Kotlin** and **Jetpack Compose** as part of the GenciDev Android Developer test.  
The app fetches data from **The Movie Database (TMDB) API**, displays a list of movies, supports searching/filtering, shows detailed information, and stores the data locally using **Room Database** for offline access.

---
## ✨ Features

- 📄 **Movie List** – Fetch and display movie data from TMDB API.  
- 🔎 **Search / Filter** – Search movies by title.  
- 📱 **Detail Page** – View detailed information about a selected movie.  
- 💾 **Local Storage** – Cache downloaded data with **Room Database**, available offline.  
- 🎨 **Modern UI** – Built entirely with **Jetpack Compose**.  
- 🏛 **Clean Architecture + MVI** – Modularization Architecture for clear separation of concerns with Data, Domain, and Presentation.

---

## 🚀 Getting Started

### 1. Clone the Repository
```bash
git clone https://github.com/galahseno/gencidev.git
cd gencidev
```
### 2. Open in Android Studio
### 3. Get a TMDB API Key
- This project uses [The Movie Database (TMDB) API](https://developer.themoviedb.org/reference/intro/getting-started) as its public API.
- Go to TMDB Sign Up and create an account.
- Navigate to Settings > API > Request API Key.
- Choose Developer and fill in the required details.
- Copy your API Key (v3 auth).
### 4. Add API Key to Project
Create a new file in your project:
- local.properties (in the root project directory):
```bash
api_key=YOUR_API_KEY_HERE (e.g 340xxx)
base_url=https://api.themoviedb.org/3/
base_image_url=https://image.tmdb.org/t/p/w500/
```
The app will automatically read the API key from this file.

⚠️ Do NOT commit your API key to GitHub.
### 5. Run the App
Connect your Android device or start an emulator.

Press Run ▶️ in Android Studio.

---
## 📂 Project Structure
```bash
Gencidev/
 ├─ app/              # Application Module
 ├─ build-logic/      # Gradle Plugin Module
 ├─ core/             # Utility & Shared Module
 ├─ home/             # Home Module
```

---
## 📦 Demo & APK
- [Download APK](https://github.com/galahseno/gencidev/releases/tag/v0.0.1)

- https://github.com/user-attachments/assets/f92361d2-e4d6-41ff-a7f6-c498e5d90c0e

---
## ⚙️ CI/CD — Auto Build (Debug) & Release on Tag

This project ships with a GitHub Actions workflow that **builds an unsigned _Debug_ APK** and **publishes a GitHub Release** whenever you push a tag like `v1.0.0`.

### What it does
- Triggers on tags matching `v*` (e.g., `v1.2.3`)
- Builds **Debug** variant (`assembleDebug`)
- Injects runtime values into `local.properties` from repo secrets:
  - `TMDB_API_KEY`
- Publishes a GitHub Release named after the tag and uploads the APK asset:
  - `app-<tag>-unsigned.apk` (e.g., `app-v1.0.0-unsigned.apk`)

### Required repository secrets
- `TMDB_API_KEY` — your TMDB API key

> Note: Because this builds **Debug**, the APK is **unsigned** and not optimized for distribution. That’s intentional.

### How to release
1. Commit all your changes to `main` (or your default branch)
2. Create a tag and push:
   ```bash
   git tag v1.0.0
   git push origin v1.0.0
   ```
