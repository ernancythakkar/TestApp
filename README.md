<h1>Album listing app</h1>

The app fetches the albums from the server and display them.
Album data is stored locally for offline viewing.

**Language Used:**
-  Kotlin
-  Minimum supported android version: 24

**Architecture used:**
- MVVM (Model-View-ViewModel)
- Repository structure for single source of data
![architecture_diagram](https://user-images.githubusercontent.com/49024322/133936075-eb06d675-2841-4b40-8a1a-b5bb9533222b.png)


**Android Jetpack components used:**
- [x] AndroidX
- [x] LiveData
- [x] ViewModel
- [x] ConstraintLayout

**Other technologies used:**
-  Retrofit : For Network calls
-  Architecture : MVVM
-  Coroutines for background operations like fetching network response
-  Live Data : To notify view for change
