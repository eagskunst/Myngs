# Dependencies
### Using Gradle Kotlin DSL
I decided to use Gradle's Kotlin plugin to manage the dependencies. 
Being able to use variables on the module's dependencies removed errors by copy-pasting different version 
that will clash when the full app is compiled. 
All the dependencies are in the _dependencies.kt_ file on the _buildSrc_ module, which serves as a single source
of truth for the dependencies.

### Libraries

#### AndroidX and Jetpack
Jetpack libraries are used on this project. They integrate perfectly
between themselves and Android in general.

* Activity-KTX: Used for ActivityResultContract, the new startActivityForResult API.
This removes ugly callbacks inside the onActivityResult function to a higher
order function in the variable that launches the intent.
* Room: An ORM for SQLite databases. Incremental by default on the version used,
which optimized compilation time. The compile time checking for queries and relationships
also helped for optimization. Seamless integration with coroutines and support a
separate dependency for testing purposes.
* Lifecycle: Support for hot streams via LiveData values. Also, ViewModels 
removes the problem of Activities lifecycle, like recreation on orientation changes.
* Paging: Integration with Room to support paged queries automatically. This make 
the change to single queries to paged queries a little less painful. Bad thing is that
it does not have good support for testing.
* Test: Rules for test Loopers and dispatching results immediately.

#### Network
* Retrofit: Typesafe HTTP client. Main reason for choosing Retrofit was the support for 
annotations and coroutines.
* Moshi: Used for conversion of JSONObject returned by Retrofit to models.
* OkHttp: The only OkHttp dependency is used for logging network calls.

#### Coroutines
Asynchronous code for long running operations without blocking the main thread. This make applications 
super fast while running read and write operations from the network and to the database,
which is the main use-case of this app.

#### Koin
Kotlin first dependency injection framework. The reason to use Koin instead of Dagger2 is
the injection of the modules inside Unit/Instrumented test without defining
custom Application objects or different test runners. It also have a dependency for injection
in lazy ViewModels.

#### Google
* Material: Default UI kit for Android, with support for toolbar behaviors and
elevation shadows used on this app
* ExoPlayer: Used for the audio playback feature. Easy plug and play library that
needed little setup for the use case of Myngs. Also, provided the playback controls on its ui
module.

#### Exoplayer
AirBnb library for building RecyclerViews in a declarative way. Has modules that support
Data-binding. Its annotation processor created the models for the integration of the
view holders inside the recycler views.
From [Epoxy](https://github.com/airbnb/epoxy)'s repository: 
> Abstracts the boilerplate of view holders, diffing items and binding payload changes, item types, item ids, span counts, and more, in order to simplify building screens with multiple view types. Additionally, Epoxy adds support for saving view state and automatic diffing of item changes.

The EpoxyRecyclerView's `withModels` extension function also removed the need of controller's creation*,
So almost all the activities's used views can be seem directly on its Kotlin file.

*Paging library needs a special adapter, which Epoxy supports on a different type of controller. This is the only controller that lives on a different file.

#### Coil
Image loading with coroutines. Has better support for Kotlin as it is Kotlin first.

#### Test dependencies
* Hamcrest: Matchers that throw useful messages in case of failure.
* MockK: Kotlin first mocking library. It also has support for mocking suspend functions.

