# 🍳 Kichen-Kit with ChefMate AI

Android recipe app with AI cooking assistant powered by Google Gemini.

## Features

- **Recipe Management** - Create, edit, view, and delete recipes
- **ChefMate AI** - Smart cooking assistant for recipes and cooking tips
- **YouTube Integration** - Watch cooking videos directly in YouTube app
- **Local Storage** - Save recipes offline with Room database
- **Modern UI** - Clean Material 3 design

## ChefMate AI

Smart cooking assistant that helps with:
- Recipe suggestions and modifications
- Cooking tips and techniques
- Ingredient substitutions
- Food safety advice

*Only responds to food-related questions!*

## Tech Stack

- **Jetpack Compose** - UI framework
- **Room Database** - Local storage
- **Hilt** - Dependency injection
- **Google Gemini AI** - Chat assistant
- **Clean Architecture** - MVVM pattern

## Setup

1. **Clone the repo**
   ```bash
   git clone https://github.com/yourusername/recipe-app.git
   ```

2. **Get Gemini API Key**
   - Visit [Google AI Studio](https://makersuite.google.com/)
   - Create API key

3. **Add API Key**
   ```kotlin
   // utils/Constants.kt
   const val API_KEY = "your_api_key_here"
   ```

4. **Build and Run**
   - Open in Android Studio
   - Sync project
   - Run app

## Requirements

- Android Studio Hedgehog+
- Minimum SDK 24
- Gemini API key
- Internet permission for AI chat

## App Structure

```
├── data/           # Database & repositories
├── domain/         # Business logic & use cases  
├── presentation/   # ViewModels
├── ui/             # Compose screens
├── di/             # Dependency injection
└── utils/          # Helper classes
```





---

**Made with ❤️ for cooking enthusiasts**
