MiGestion Android App


¡Bienvenido a la documentación de la aplicación móvil MiGestion! Esta aplicación está diseñada para autónomos y pequeñas empresas, permitiéndoles gestionar clientes, facturas, productos y más de manera eficiente. 
A continuación, encontrarás detalles sobre las tecnologías principales utilizadas en el desarrollo de esta aplicación.

Tecnologías Utilizadas


Android Jetpack
Core-KTX (androidx.core:core-ktx:1.12.0): Extiende las funcionalidades de Android con extensiones concisas y potentes de Kotlin.
Lifecycle (androidx.lifecycle:lifecycle-runtime-ktx:2.7.0): Gestiona automáticamente el ciclo de vida de los componentes de UI, como actividades y fragmentos.
Activity Compose (androidx.activity:activity-compose:1.8.2): Integra Compose con las actividades de Android para la creación de interfaces de usuario declarativas.
Navigation (androidx.navigation:navigation-runtime-ktx:2.7.7): Facilita la navegación entre fragmentos y actividades de forma segura y coherente.
DataStore (androidx.datastore:datastore-preferences:1.0.0): Almacena datos clave-valor de forma segura y con el respaldo de un editor de preferencias.
Splashscreen (androidx.core:core-splashscreen:1.0.1): Ofrece una experiencia de lanzamiento personalizable y coherente para la aplicación.

Jetpack Compose
Compose UI (androidx.compose.ui:ui): Biblioteca moderna para la creación de interfaces de usuario nativas de Android de manera declarativa.
Navigation Compose (androidx.navigation:navigation-compose:2.7.7): Integración de Jetpack Navigation con Jetpack Compose para la navegación entre pantallas.

Kotlin Serialization (io.ktor:ktor-serialization-kotlinx-json:$ktorVersion): Serialización de datos en Kotlin para la comunicación con el backend.
Ktor Client (io.ktor:ktor-client-android:$ktorVersion): Cliente HTTP utilizado para realizar solicitudes al backend desarrollado con Ktor.
SQLDelight (app.cash.sqldelight:android-driver:2.0.0): Framework de persistencia de datos SQL para Android, que facilita la interacción con bases de datos SQLite.
Dagger Hilt (com.google.dagger:hilt-android:2.50): Biblioteca de inyección de dependencias para Android, facilitando la gestión de la dependencia.
Coil Compose (io.coil-kt:coil-compose:2.5.0): Biblioteca para la carga de imágenes en aplicaciones Compose.
