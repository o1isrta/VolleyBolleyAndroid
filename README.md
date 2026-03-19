# VolleyBolleyAndroid

## MVI Архитектура

Проект использует паттерн MVI (Model-View-Intent) для построения UI. Каждый экран состоит из 5 файлов:

- `XxxState.kt` — data class с состоянием экрана
- `XxxEvent.kt` — sealed interface с событиями от UI
- `XxxEffect.kt` — sealed interface с одноразовыми эффектами (навигация, тосты)
- `XxxViewModel.kt` — ViewModel с единой точкой входа `obtainEvent()`
- `XxxScreen.kt` — Composable-функции для отображения

---

### ViewModel: Единая точка входа

ViewModel должен иметь **только один публичный метод** — `obtainEvent()`. Все события от UI проходят через него.

```kotlin
class RegistrationViewModel(
    private val someUseCase: SomeUseCase,
) : BaseViewModel<RegistrationState, RegistrationEvent, RegistrationEffect>(
    initialState = RegistrationState()
) {

    // ✅ Единственный публичный метод
    override fun obtainEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.NameChanged -> handleNameChanged(event.value)
            is RegistrationEvent.GetStartedClicked -> sendRegistrationRequest()
        }
    }

    // Все остальные методы — private
    private fun handleNameChanged(value: String) { ... }
    private fun sendRegistrationRequest() { ... }
}
```

**Почему только один публичный метод:**
- Чёткая граница между UI и бизнес-логикой
- Легко тестировать — один вход, предсказуемый выход
- UI не может случайно вызвать внутренние методы ViewModel

---

### Обработка ошибок в ViewModel

При использовании `launchSafe` и работе с UseCase **обязательно** обрабатывать ошибки:

```kotlin
private fun loadInitialData() {
    launchSafe(
        onError = { throwable ->
            // ✅ Обязательно обрабатывать ошибки корутины
            sendUiEffect(RegistrationEffect.ShowToast("Failed to load data"))
        },
        getErrorLogMessage = { throwable ->
            "Error loading data: ${throwable.message}"
        }
    ) {
        someUseCase.execute()
            .onSuccess { data ->
                uiStateMutable.update { it.copy(items = data) }
            }
            .onFailure { error ->
                // ✅ Обязательно обрабатывать onFailure от UseCase
                sendUiEffect(RegistrationEffect.ShowToast("Failed: $error"))
            }
    }
}
```

**Правила:**
- `onError` в `launchSafe` — ловит исключения в корутине
- `onFailure` в `Result` — обрабатывает бизнес-ошибки от UseCase
- Всегда сбрасывайте `isLoading = false` в обоих случаях

---

### UseCase, Repository, ViewModel: Где сохранять данные

**Ключевой принцип:** ViewModel должна получать только данные для отображения и взаимодействия с пользователем. Всё остальное (токены, внутренние флаги, кэш) должно сохраняться внутри Repository или UseCase.

#### Правильно: Repository сохраняет данные при получении

```kotlin
// ✅ Repository получает токены с сервера и сразу сохраняет их
class AuthRepositoryImpl(
    private val networkClient: NetworkClient<AuthRequest, AuthResponse>,
    private val tokenStorage: TokenStorage
) : AuthRepository {

    override suspend fun loginWithGoogle(idToken: String): VolleyResult<LoginData, ErrorType> {
        val response = networkClient.getResponse(AuthRequest.Google(idToken))

        if (response.isSuccess) {
            val loginData = (response.body as? AuthResponse.GoogleResponse)?.toDomain()
            if (loginData != null) {
                // ✅ Сохраняем токены прямо здесь — ViewModel не должна об этом знать
                tokenStorage.saveAccessToken(loginData.accessToken)
                tokenStorage.saveRefreshToken(loginData.refreshToken)
                // Возвращаем только данные для отображения
                return VolleyResult.Success(loginData.copy(
                    accessToken = "", // Не передаём токены наверх
                    refreshToken = ""
                ))
            }
        }
        return VolleyResult.Failure(response.resultCode.mapToErrorType())
    }
}

// UseCase просто делегирует в Repository
class GoogleTokenAuthUseCaseImpl(
    private val authRepository: AuthRepository
) : GoogleTokenAuthUseCase {
    override suspend fun execute(idToken: String): VolleyResult<LoginData, ErrorType> {
        return authRepository.loginWithGoogle(idToken)
    }
}

// ViewModel получает только данные для UI
class AuthorizationViewModel(
    private val googleTokenAuthUseCase: GoogleTokenAuthUseCase
) : BaseViewModel<...>() {

    private fun handleGoogleAuth(idToken: String) {
        launchSafe(onError = { ... }) {
            googleTokenAuthUseCase.execute(idToken)
                .onSuccess { loginData ->
                    // ✅ loginData содержит только данные для отображения
                    // Токены уже сохранены в Repository — ViewModel о них не знает
                    sendUiEffect(AuthorizationEffect.NavigateToHome)
                }
                .onFailure { error ->
                    sendUiEffect(AuthorizationEffect.ShowToast("Error: $error"))
                }
        }
    }
}
```

#### Неправильно: Протаскивание внутренних данных в ViewModel

```kotlin
// ❌ Плохо: Repository возвращает токены, чтобы ViewModel их сохранила
class AuthRepositoryImpl : AuthRepository {
    override suspend fun loginWithGoogle(idToken: String): VolleyResult<LoginData, ErrorType> {
        val response = networkClient.getResponse(AuthRequest.Google(idToken))
        // ❌ Возвращаем токены наверх
        return VolleyResult.Success(loginData) // loginData содержит accessToken, refreshToken
    }
}

// ❌ ViewModel вынуждена сохранять токены
class AuthorizationViewModel(
    private val authRepository: AuthRepository,
    private val tokenStorage: TokenStorage  // ❌ Лишняя зависимость
) : BaseViewModel<...>() {

    private fun handleGoogleAuth(idToken: String) {
        launchSafe(onError = { ... }) {
            authRepository.loginWithGoogle(idToken)
                .onSuccess { loginData ->
                    // ❌ ViewModel занимается сохранением — это не её ответственность
                    tokenStorage.saveAccessToken(loginData.accessToken)
                    tokenStorage.saveRefreshToken(loginData.refreshToken)
                    sendUiEffect(AuthorizationEffect.NavigateToHome)
                }
        }
    }
}
```

#### UseCase с сохранением — это нормально

```kotlin
// ✅ UseCase может сохранять данные через Repository
class UpdateUserProfileUseCaseImpl(
    private val profileRepository: ProfileRepository,
    private val userStorage: UserStorage
) : UpdateUserProfileUseCase {

    override suspend fun execute(profile: UserProfile): VolleyResult<Unit, ErrorType> {
        val result = profileRepository.updateProfile(profile)

        if (result.isSuccess) {
            // ✅ UseCase обновляет локальный кэш после успешного обновления
            userStorage.saveUserProfile(profile)
        }

        return result.mapSuccess { }
    }
}
```

**Правила:**
- **Repository** — сохраняет данные сразу при получении (токены, кэш)
- **UseCase** — может сохранять данные через Repository после бизнес-логики
- **ViewModel** — получает только данные для отображения, не управляет хранением
- Если данные не нужны UI — они не должны подниматься выше Repository

---

### Навигация через Callback

Навигация не должна быть внутри ViewModel. ViewModel отправляет Effect, а Screen обрабатывает его через callback.

```kotlin
// Effect.kt
sealed class RegistrationEffect : UiEffect {
    data object NavigateToHome : RegistrationEffect()
    data class ShowToast(val message: String) : RegistrationEffect()
}

// Screen.kt
@Composable
fun RegistrationScreen(
    paddingFromSystemUi: PaddingValues,
    viewModel: RegistrationViewModel,
    onRegistrationSuccessEvent: () -> Unit, // Callback для навигации
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val effect by viewModel.uiEffect.collectAsStateWithLifecycle(null)

    LaunchedEffect(effect) {
        when (val currentEffect = effect) {
            is RegistrationEffect.NavigateToHome -> onRegistrationSuccessEvent()
            is RegistrationEffect.ShowToast -> { /* показать тост */ }
            null -> {}
        }
    }

    RegistrationScreen(
        paddingFromSystemUi = paddingFromSystemUi,
        state = state,
        eventCallback = { viewModel.obtainEvent(it) }
    )
}
```

**Почему callback в Screen:**
- ViewModel не знает о Navigation controller
- Легко тестировать ViewModel изолированно
- Навигация — это UI-ответственность

---

### Две Composable-функции на экран

Каждый экран должен иметь **две Composable-функции**:

1. **С ViewModel** — для использования в приложении
2. private **Без ViewModel** — для Preview с передачей State

```kotlin
// 1. С ViewModel — публичная функция
@Composable
fun RegistrationScreen(
    paddingFromSystemUi: PaddingValues,
    viewModel: RegistrationViewModel,
    onRegistrationSuccessEvent: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val effect by viewModel.uiEffect.collectAsStateWithLifecycle(null)

    LaunchedEffect(effect) {
        when (val currentEffect = effect) {
            is RegistrationEffect.NavigateToHome -> onRegistrationSuccessEvent()
            is RegistrationEffect.ShowToast -> { /* ... */ }
            null -> {}
        }
    }

    // Вызываем вторую функцию
    RegistrationScreen(
        paddingFromSystemUi = paddingFromSystemUi,
        state = state,
        eventCallback = { viewModel.obtainEvent(it) }
    )
}

// 2. Без ViewModel — для Preview
@Composable
private fun RegistrationScreen(
    paddingFromSystemUi: PaddingValues,
    state: RegistrationState,
    eventCallback: (RegistrationEvent) -> Unit
) {
    // Stateless UI — только отображение
}
```

**Преимущества:**
- Preview работает без ViewModel и DI
- Можно легко протестировать разные состояния UI
- Разделение ответственности между состоянием и логикой

---

### Preview для каждого экрана

Каждый экран **обязан иметь Preview-функцию**:

```kotlin
@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_9_PRO)
@Composable
private fun PreviewAuthorizationScreen() {
    RootContainerForPreview(showTopBar = false, showBottomBar = false) {
        AuthorizationScreen(
            paddingFromSystemUi = it,
            state = AuthorizationState(),
            onGoogleSignInClick = {},
            onNavigateToRegisterByPhoneRequested = {},
            eventCallback = {}
        )
    }
}
```

**Правила:**
- Preview должна быть `private`
- Используйте `RootContainerForPreview` для обёртки (передаёт `PaddingValues` через lambda)
- Указывайте `device = Devices.PIXEL_9_PRO` для просмотра как выглядит экран с камерой по центру
- `showSystemUi = true` для отображения системных баров

---

### Шпаргалка: Структура экрана

```
feature/presentation/ui/screens/example/
├── ExampleState.kt      # data class ExampleState(...) : UiState
├── ExampleEvent.kt      # sealed interface ExampleEvent : UiEvent { ... }
├── ExampleEffect.kt     # sealed interface ExampleEffect : UiEffect { ... }
├── ExampleViewModel.kt  # class ExampleViewModel : BaseViewModel<...>
└── ExampleScreen.kt     # @Composable fun ExampleScreen(...) x2 + Preview
```

### Checklist для нового экрана

- [ ] State: data class с default значениями, implements UiState
- [ ] Event: sealed interface с object/class, implements UiEvent
- [ ] Effect: sealed interface для навигации и тостов, implements UiEffect
- [ ] ViewModel: только один публичный метод `obtainEvent()`
- [ ] ViewModel: обработаны `onError` в launchSafe и `onFailure` в Result
- [ ] Screen: две Composable-функции (с ViewModel и без)
- [ ] Screen: навигация через callback, не внутри ViewModel
- [ ] Screen: есть Preview-функция с разными состояниями

---

## Автоматический Refresh Token

Проект использует Ktor Auth plugin для автоматического обновления access token при его истечении.

### Два HttpClient

В проекте зарегистрировано **два HttpClient**:

| Клиент | Qualifier | Назначение |
|--------|-----------|------------|
| **Основной** | `HttpClient` (по умолчанию) | Для всех API кроме Auth. Содержит Auth plugin с auto-refresh |
| **Без токена** | `HttpClientQualifier.NO_ACCESS_TOKEN` | Для Auth endpoints (login, refresh) |

### Как работает Auto-Refresh

При конфигурации основного HttpClient используется Ktor Auth plugin:

```kotlin
// CoreModule.kt
single<HttpClient> {
    HttpClient(OkHttp) {
        // ... timeout, logging, contentNegotiation ...

        install(Auth) {
            bearer {
                // 1. Загрузка токенов при каждом запросе
                loadTokens {
                    val accessToken = loginDataRepo.getAccessToken()
                    val refreshToken = loginDataRepo.getRefreshToken()
                    if (accessToken != null && refreshToken != null) {
                        BearerTokens(accessToken, refreshToken)
                    } else {
                        null
                    }
                }

                // 2. Автоматический refresh при 401
                refreshTokens {
                    val refreshToken = tokenStorage.getRefreshToken()

                    if (refreshToken == null) {
                        loginDataRepo.clearAll() // Logout
                        null
                    } else {
                        when (val result = authRepository.refreshAccessToken(refreshToken)) {
                            is VolleyResult.Success -> {
                                tokenStorage.saveAccessToken(result.data)
                                BearerTokens(result.data, refreshToken)
                            }
                            is VolleyResult.Failure -> {
                                loginDataRepo.clearAll() // Logout
                                null
                            }
                        }
                    }
                }

                // 3. Не добавлять токен для auth endpoints
                sendWithoutRequest { request ->
                    !request.url.pathSegments.contains("auth")
                }
            }
        }
    }
}
```

### Алгоритм работы

```
1. Запрос к API
      ↓
2. Auth plugin проверяет: нужно ли добавлять токен?
   - Если URL содержит "auth" → пропускаем токен
   - Иначе → добавляем Authorization: Bearer {accessToken}
      ↓
3. Сервер отвечает 401 Unauthorized
      ↓
4. Auth plugin вызывает refreshTokens {}
   - Получаем refresh token из TokenStorage
   - Если refresh token отсутствует → logout (clearAll)
   - Иначе → вызываем AuthRepository.refreshAccessToken()
      ↓
5. При успешном refresh:
   - Сохраняем новый access token
   - Повторяем оригинальный запрос с новым токеном
      ↓
6. При ошибке refresh:
   - Logout (clearAll)
   - Пользователь перенаправляется на экран авторизации
```




---

## Сетевой слой

Проект использует Ktor 3.x для работы с сетью. Архитектура сетевого слоя построена на паттерне **Sealed Request/Response**.

### Структура сетевого слоя

```
feature/data/network/
├── XxxNetworkClient.kt      # KtorNetworkClient с логикой запросов
├── model/
│   ├── XxxRequest.kt        # Sealed class с типами запросов
│   └── XxxResponse.kt       # Sealed class с типами ответов
```

### NetworkClient Interface

```kotlin
interface NetworkClient<SealedRequest, SealedResponse> {
    suspend fun getResponse(sealedRequest: SealedRequest): Response<SealedResponse>
}
```

### Sealed Request/Response Pattern

Все запросы и ответы инкапсулированы в sealed classes:

```kotlin
// XxxRequest.kt
sealed class GamesRequest {
    data class CreateGame(val game: GameDto) : GamesRequest()
    data class GetGameDetails(val gameId: Int) : GamesRequest()
    data class JoinGame(val gameId: Int) : GamesRequest()
    // ...
}

// XxxResponse.kt
sealed class GamesResponse {
    data class CreateGame(val game: GameDto) : GamesResponse()
    data class GetGameDetails(val game: GameDetailsDto) : GamesResponse()
    data object JoinGame : GamesResponse()
    // ...
}
```

### KtorNetworkClient Implementation

```kotlin
class GamesNetworkClient : KtorNetworkClient<GamesRequest, GamesResponse>() {

    // 1. Отправка запроса в зависимости от типа
    override suspend fun sendRequestByType(request: GamesRequest): HttpResponse {
        return httpClient.request {
            when (request) {
                is GamesRequest.CreateGame -> {
                    method = HttpMethod.Post
                    requestConfigure(request.path, body = request.game)
                }
                is GamesRequest.GetGameDetails -> {
                    method = HttpMethod.Get
                    requestConfigure(request.fullPath())
                }
                // ...
            }
        }
    }

    // 2. Парсинг ответа в зависимости от типа запроса
    override suspend fun getResponseBodyByRequestType(
        requestType: GamesRequest,
        httpResponse: HttpResponse
    ): GamesResponse {
        return when (requestType) {
            is GamesRequest.CreateGame -> httpResponse.body<GamesResponse.CreateGame>()
            is GamesRequest.GetGameDetails -> httpResponse.body<GamesResponse.GetGameDetails>()
            // ...
        }
    }
}
```

### Response Model

```kotlin
class Response<T>(
    var isSuccess: Boolean = false,
    var resultCode: StatusCode = StatusCode(0),
    var body: T? = null
)
```

### Repository: Преобразование Response → VolleyResult

Repository преобразует сетевой Response в доменный VolleyResult:

```kotlin
class GamesRepositoryImpl(
    private val networkClient: NetworkClient<GamesRequest, GamesResponse>
) : GamesRepository {

    override suspend fun getGameDetails(gameId: Int): VolleyResult<GameDetails, ErrorType> {
        val response = networkClient.getResponse(GamesRequest.GetGameDetails(gameId))

        if (!response.isSuccess) {
            return VolleyResult.Failure(response.resultCode.mapToErrorType())
        }

        val gameDetails = (response.body as? GamesResponse.GetGameDetails)?.toDomain()
        return gameDetails?.let { VolleyResult.Success(it) }
            ?: VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
    }
}
```

### VolleyResult: Доменный Result

```kotlin
sealed interface VolleyResult<Data, Error> {
    data class Success<Data, Error>(val data: Data) : VolleyResult<Data, Error>
    data class Failure<Data, Error>(val error: Error) : VolleyResult<Data, Error>
}

// Утилиты для удобной обработки
inline fun <Data, Error> VolleyResult<Data, Error>.onSuccess(action: (Data) -> Unit): VolleyResult<Data, Error>
inline fun <Data, Error> VolleyResult<Data, Error>.onFailure(action: (Error) -> Unit): VolleyResult<Data, Error>
```

### ErrorType

```kotlin
enum class ErrorType {
    NO_CONNECTION,    // Нет интернета
    NOT_FOUND,        // 404
    BAD_REQUEST,      // 400
    SERVER_ERROR,     // 500
    UNAUTHORIZED,     // 401
    NO_REFRESH_TOKEN, // Нет refresh token
    UNKNOWN_ERROR     // Неизвестная ошибка
}
```

---

## Потоки данных (Data Flow)

Проект следует принципам Clean Architecture с однонаправленным потоком данных.

### Общая схема

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                              UI Layer                                        │
│  ┌──────────────┐    events    ┌──────────────┐    effects    ┌───────────┐ │
│  │   Screen     │ ──────────►  │   ViewModel  │ ────────────► │   Screen  │ │
│  │ (Composable) │ ◄──────────  │              │ ◄──────────── │           │ │
│  └──────────────┘    state     └──────────────┘               └───────────┘ │
└───────────────────────────────────────────┬─────────────────────────────────┘
                                            │
                                            ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                            Domain Layer                                      │
│  ┌──────────────┐                   ┌──────────────┐                        │
│  │   UseCase    │ ────────────────► │  Repository  │                        │
│  │   (impl)     │ ◄──────────────── │  (interface) │                        │
│  └──────────────┘   VolleyResult    └──────────────┘                        │
└───────────────────────────────────────────┬─────────────────────────────────┘
                                            │
                                            ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                             Data Layer                                       │
│  ┌──────────────┐    Response    ┌──────────────┐                          │
│  │  Repository  │ ◄────────────  │ NetworkClient│                          │
│  │   (impl)     │ ────────────►  │    (Ktor)    │                          │
│  └──────────────┘    Request     └──────────────┘                          │
│        │                                                                     │
│        │ VolleyResult                                                        │
│        ▼                                                                     │
│  ┌──────────────┐                                                           │
│  │   Storage    │  (SharedPreferences, EncryptedPrefs)                      │
│  └──────────────┘                                                           │
└─────────────────────────────────────────────────────────────────────────────┘
```

### MVI Data Flow (UI ↔ ViewModel)

```
         User Action
              │
              ▼
    ┌─────────────────┐
    │     Event       │  (UiEvent)
    │  NameChanged    │
    │  ButtonClicked  │
    └────────┬────────┘
             │ viewModel.obtainEvent(event)
             ▼
    ┌─────────────────┐
    │   ViewModel     │
    │                 │
    │  1. Обработка   │
    │     события     │
    │  2. Вызов       │
    │     UseCase     │
    │  3. Обновление  │
    │     State       │
    └────┬───────┬────┘
         │       │
  State  │       │  Effect (UiEffect)
         │       │
         ▼       ▼
    ┌─────────┐ ┌─────────────┐
    │  State  │ │   Effect    │
    │ (Flow)  │ │ (SharedFlow)│
    └────┬────┘ └──────┬──────┘
         │             │
         ▼             ▼
    ┌─────────────────────┐
    │       Screen        │
    │  collectAsState()   │
    │  LaunchedEffect()   │
    └─────────────────────┘
```

### Domain Data Flow (UseCase ↔ Repository)

```kotlin
// 1. ViewModel вызывает UseCase
class GameViewModel(
    private val getGameDetailsUseCase: GetGameDetailsUseCase
) : BaseViewModel<...>() {

    private fun loadGame(gameId: Int) {
        launchSafe(onError = { ... }) {
            getGameDetailsUseCase.execute(gameId)
                .onSuccess { gameDetails ->
                    uiStateMutable.update { it.copy(game = gameDetails) }
                }
                .onFailure { error ->
                    sendUiEffect(ShowToast("Error: $error"))
                }
        }
    }
}

// 2. UseCase делегирует в Repository
class GetGameDetailsUseCaseImpl(
    private val gamesRepository: GamesRepository
) : GetGameDetailsUseCase {
    override suspend fun execute(gameId: Int): VolleyResult<GameDetails, ErrorType> {
        return gamesRepository.getGameDetails(gameId)
    }
}

// 3. Repository работает с NetworkClient
class GamesRepositoryImpl(
    private val networkClient: NetworkClient<GamesRequest, GamesResponse>
) : GamesRepository {
    override suspend fun getGameDetails(gameId: Int): VolleyResult<GameDetails, ErrorType> {
        val response = networkClient.getResponse(GamesRequest.GetGameDetails(gameId))

        if (!response.isSuccess) {
            return VolleyResult.Failure(response.resultCode.mapToErrorType())
        }

        return (response.body as? GamesResponse.GetGameDetails)
            ?.toDomain()
            ?.let { VolleyResult.Success(it) }
            ?: VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
    }
}
```

### Правила потока данных

1. **UI → ViewModel**: Только через `obtainEvent()`
2. **ViewModel → UseCase**: Через `execute()` с возвратом `VolleyResult`
3. **UseCase → Repository**: Через interface, возвращает `VolleyResult`
4. **Repository → NetworkClient**: Через `getResponse()`, возвращает `Response`
5. **ViewModel → UI**: State через `StateFlow`, Effects через `SharedFlow`

---

## Навигация

Проект использует Jetpack Navigation Compose с type-safe routes через `kotlinx.serialization`.

### Структура навигации

```
core/presentation/ui/navigation/
├── NavMap.kt              # Все routes (sealed interface)
├── NavHostContainer.kt    # NavHost с графом навигации
└── model/
    ├── TopLevelRoute.kt   # Модель для bottom nav
    └── NoBarsRoutes.kt    # Правила скрытия баров
```

### NavMap: Type-Safe Routes

Все маршруты определены как `@Serializable` объекты:

```kotlin
@Serializable
sealed interface NavMap

// Top-level routes (для BottomNav)
@Serializable
object HomeTopLevelRoute : NavMap

@Serializable
object GameHomeTopLevelRoute : NavMap

@Serializable
object ProfileTopLevelRoute : NavMap

// Routes с аргументами
@Serializable
data class PlayerProfileRoute(val playerId: Int) : NavMap

@Serializable
data class JoinTheGameRoute(val gameId: Int) : NavMap

@Serializable
data class SearchCourtRoute(val eventType: EventType) : NavMap
```

### Навигационный граф

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                              NavHost                                         │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  ┌─ Authorization Flow ─────────────────────────────────────────────────────┤
│  │  LaunchRoute → OnboardingRoute → AuthorizationRoute → RegistrationRoute  │
│  │       ↓                                          ↓                       │
│  │  (authenticated)                          AuthorizationByPhoneRoute       │
│  │       ↓                                                                   │
│  └──────► HomeTopLevelRoute                                                  │
│                                                                              │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  ┌─ HomeTopLevelRoute (nested graph) ───────────────────────────────────────┤
│  │  HomeRoute (start)                                                        │
│  │      ├── SearchCourtRoute                                                 │
│  │      │       ├── BasicGameSetupRoute → GameEnteringConditionsRoute →     │
│  │      │       │                                PrivacyOptionsRoute         │
│  │      │       └── BasicTourneySetupRoute → TourneyEnteringConditionsRoute │
│  │      ├── SuccessRoute                                                     │
│  │      ├── RatePlayersRoute                                                 │
│  │      ├── JoinTheGameRoute                                                 │
│  │      ├── ChooseTeamRoute → IndividualPlayersRoute → InvitePlayersRoute   │
│  │      └── JoinIndividualRoute → JoinTeamRoute                              │
│  └──────────────────────────────────────────────────────────────────────────┘
│                                                                              │
│  ┌─ GameHomeTopLevelRoute (nested graph) ───────────────────────────────────┤
│  │  GameHomeRoute (start)                                                    │
│  │      ├── MyGamesRoute → MyGameRoute / MyTourneyRoute                      │
│  │      │               ├── ManagePlayersRoute                               │
│  │      │               └── ChangeTeamRoute                                  │
│  │      ├── UpcomingGamesRoute → UpcomingGameDetailsRoute /                  │
│  │      │                         UpcomingTourneyDetailsRoute                │
│  │      ├── GameInvitesRoute → JoinTheTourneyRoute                           │
│  │      └── ArchiveRoute → PastGameRoute / PastTourneyRoute → TeamsRoute    │
│  └──────────────────────────────────────────────────────────────────────────┘
│                                                                              │
│  ┌─ ProfileTopLevelRoute (nested graph) ────────────────────────────────────┤
│  │  ProfileRoute (start)                                                     │
│  │      ├── PersonalDataRoute → ChangePhotoRoute                             │
│  │      ├── PlayersRoute → PlayerProfileRoute                                │
│  │      ├── PaymentsRoute → EnterPaymentDataRoute                            │
│  │      ├── FaqRoute                                                         │
│  │      └── AboutRoute                                                       │
│  └──────────────────────────────────────────────────────────────────────────┘
│                                                                              │
│  ┌─ Other Routes ───────────────────────────────────────────────────────────┤
│  │  NotificationsRoute                                                       │
│  │  ShareLinkRoute (deep link: volleybolley://invite/{type}/{id})           │
│  └──────────────────────────────────────────────────────────────────────────┘
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘
```

### Nested Navigation Graphs

Используются `navigation {}` для группировки связанных экранов:

```kotlin
NavHost(navController, startDestination = LaunchRoute) {

    // Nested graph для Home
    navigation<HomeTopLevelRoute>(startDestination = HomeRoute) {
        composable<HomeRoute> { HomeScreen(...) }
        composable<SearchCourtRoute> { SearchCourtScreen(...) }
        composable<BasicGameSetupRoute> { BasicGameSetupScreen(...) }
        // ...
    }

    // Nested graph для Games
    navigation<GameHomeTopLevelRoute>(startDestination = GameHomeRoute) {
        composable<GameHomeRoute> { GameHomeScreen(...) }
        composable<MyGamesRoute> { MyGamesScreen(...) }
        // ...
    }

    // Nested graph для Profile
    navigation<ProfileTopLevelRoute>(startDestination = ProfileRoute) {
        composable<ProfileRoute> { ProfileScreen(...) }
        // ...
    }
}
```

### Передача аргументов

```kotlin
// Route с аргументами
@Serializable
data class PlayerProfileRoute(val playerId: Int) : NavMap

// Получение аргументов в composable
composable<PlayerProfileRoute> { backStackEntry ->
    val playerId = backStackEntry.toRoute<PlayerProfileRoute>().playerId
    PlayerProfileScreen(playerId = playerId, ...)
}
```

### Deep Links

```kotlin
composable<ShareLinkRoute>(
    deepLinks = listOf(
        navDeepLink { uriPattern = "volleybolley://invite/{type}/{id}" }
    )
) { backStackEntry ->
    val route = backStackEntry.toRoute<ShareLinkRoute>()
    // route.type, route.id доступны
}
```

### Bottom Navigation

Top-level routes используются для Bottom Navigation:

```kotlin
val topLevelRoutes = listOf(
    TopLevelRoute("Home", HomeTopLevelRoute, ...),
    TopLevelRoute("My Games", GameHomeTopLevelRoute, ...),
    TopLevelRoute("Profile", ProfileTopLevelRoute, ...)
)

// Навигация с сохранением состояния
navController.navigate(topRoute.route) {
    popUpTo(navController.graph.findStartDestination().id) {
        saveState = true
    }
    launchSingleTop = true
    restoreState = true
}
```

### NoBarsRoutes: Управление Top/Bottom Bar

```kotlin
enum class NoBarsRoutes(val className: String, val noBottomBar: Boolean, val noTopBar: Boolean) {
    LAUNCH(LaunchRoute::class.qualifiedName.toString(), true, true),
    ONBOARDING(OnboardingRoute::class.qualifiedName.toString(), true, true),
    SIGNUP(AuthorizationRoute::class.qualifiedName.toString(), true, true),
    REGISTRATION(RegistrationRoute::class.qualifiedName.toString(), true, true),
    PERSONALDATA(PersonalDataRoute::class.qualifiedName.toString(), false, true),
    // ...
}

// Использование
val showBottomNav = NoBarsRoutes.showBottomBar(currentDestinationRoute)
val showTopNav = NoBarsRoutes.showTopBar(currentDestinationRoute)
```

### Навигация из ViewModel через Effect

```kotlin
// ViewModel
sealed class GameEffect : UiEffect {
    data object NavigateToMyGames : GameEffect()
}

// Screen
LaunchedEffect(effect) {
    when (effect) {
        is GameEffect.NavigateToMyGames -> onNavigateToMyGames()
        null -> {}
    }
}

// NavHostContainer
composable<MyGameRoute> {
    MyGameScreen(
        onNavigateBack = { navController.popBackStack() }
    )
}
```
