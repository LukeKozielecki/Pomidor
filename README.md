# Pomidor - pomodoro timer

This app is a timer that notifies the user when a specified amount of time has passed. It is styled like a tomato, as a nod to the original Pomodoro Technique, a time management method that uses a timer to break down work into intervals, traditionally 25 minutes in length, separated by short breaks.

## Features

- **Timer**: Utiliez the interface to set the amount of time you chose to study/rest/do whatever.
- **Visual indicator**: check the time by the state of circular indicator or exactly by looking at the number

## Architecture

The app uses a single module, inside of which files are divided by purpose in packages. This structure was chosen because the project was meant to function as proof of concept and introduction to permission and notification handling.

## Stack

- **Kotlin programming language** - The core programming language
- **Jetpack Compose** - Modern Android UI framework
- **Kotlin Coroutines & Flow** - Modern management of asynchronous code and observing dynamic data
- **Manual dependency injection** - Chosen over external frameworks (e.g., Dagger-Hilt, Koin) to simplify the project setup given its limited scope

## Getting Started

To get a local copy running on your machine:
1. **Clone the repository**
```git clone https://github.com/LukeKozielecki/Pomidor/```
2. **Open in Android Studio**
3. **Build and Run**
Select emulator or physical device sufficient for minimal Gradle requirements.

## License

MIT License

Copyright (c) 2025 Lucjan Kozielecki

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
