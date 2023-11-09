# Coffes Bar
Aplicativo markplace onde usuário pode selecionar os cafés para realizar a compra e retirar nas lojas credenciadas, após ele fazer a compra poderá olhar na aba de favoritos suas compras recentes

## Feature
- Para trabalhar com bottomTab vamos concentrar a lógica em um arquivo com o esqueleto Scaffold
- Nesse Scaffold iremos implementar todos os componentes necessários para criar a bottomTab, abaixo a linha de fluxo
- Eu criei um nesting de rotas com bottomTap e topBar , ambas usam o mesmo NavGraph
- Para lidar com o item selecionado na navegação usei a lógica recomendada pela própria [docs](https://developer.android.com/jetpack/compose/navigation?hl=pt-br) do compose
- Com popUpTo iremos sempre resetar  a rota anterior que está salvo no graph, assim evitamos comportamentos estranhos ao clicar na próxima bottomTab

```kotlin
//criamos o Scaffold principal

Scaffold(
 bottomBar = {
            if (stringBottomRoute.contains(currentRoute?.get(0))) BottomCustomNavigation(
                navHostController = navController,
                navDestination = currentDestination
            )
        }) {
        NavGraphApp(navController = navController, isAnonymous = isAnonymous)
    }

)


//Nav Graph
fun NavGraphApp(navController: NavHostController, isAnonymous: MutableState<Boolean>) {

    NavHost(
        navController = navController,
        startDestination = if (isAnonymous.value) StackScreensApp.Login.name else BottomBarScreen.Home.route
    ) {

        composable(BottomBarScreen.Home.route) {
            HomeScreen(navController)
        }

        composable(BottomBarScreen.Home.route) {
            HomeScreen(navController)
        }


        composable(BottomBarScreen.Favorite.route) { entry ->
            val parentEntry = remember(entry) {
                navController.getBackStackEntry(BottomBarScreen.Home.route)
                //tem que ser uma rota ja existente se colocar rota do  carrinho nao vai funcionar, porque o usuario nao inicia nessas tela, ele iria precisar navegar para eu ter a instancia e  favoritos posso ir direto da home
            }
            val parentViewModel = hiltViewModel<CartViewModel>(parentEntry)
            FavoriteScreen(cartViewModel = parentViewModel, navController)
        }


        composable(BottomBarScreen.Cart.route) { entry ->
            val parentEntry = remember(entry) {
                navController.getBackStackEntry(BottomBarScreen.Home.route)
            }
            val parentViewModel = hiltViewModel<CartViewModel>(parentEntry)
            CartScreen(navController, parentViewModel)
        }

        composable(
            StackScreensApp.DetailsScreen.name + "/{coffeeId}",
            arguments = listOf(navArgument("coffeeId") { type = NavType.StringType })
        ) { entry ->
            val parentEntry = remember(entry) {
                navController.getBackStackEntry(BottomBarScreen.Home.route)
            }
            val parentCartViewModel = hiltViewModel<CartViewModel>(parentEntry)
            val parentCoffeesViewModel = hiltViewModel<CoffeesViewModel>(parentEntry)
            DetailsScreen(
                coffeeId = entry.arguments?.getString("coffeeId"),
                cartViewModel = parentCartViewModel,
                coffeesViewModel = parentCoffeesViewModel
            )
        }
        composable(route = StackScreensApp.PaymentResume.name) { entry ->
            val parentEntry = remember(entry) {
                navController.getBackStackEntry(BottomBarScreen.Home.route)
            }
            val parentViewModel = hiltViewModel<CartViewModel>(parentEntry)
            PaymentResume(navController, parentViewModel = parentViewModel)
        }

        composable(route = StackScreensApp.PaymentFinished.name) {
            PaymentFinished(navController)
        }


        composable(StackScreensApp.Login.name) {
            LogInScreen(navController = navController)
        }
        composable(StackScreensApp.SignIn.name) {
            SignIn(navController = navController)
        }
        composable(StackScreensApp.MainScreen.name) {
            MainScreen()
        }

        composable(BottomBarScreen.Favorite.route) { entry ->
            val parentEntry = remember(entry) {
                navController.getBackStackEntry(BottomBarScreen.Home.route)
            }
            val parentViewModel = hiltViewModel<CartViewModel>(parentEntry)
            FavoriteScreen(cartViewModel = parentViewModel, navController)
        }

       
        composable(BottomBarScreen.Cart.route) { entry ->
            val parentEntry = remember(entry) {
                navController.getBackStackEntry(BottomBarScreen.Home.route)
            }
            val parentViewModel = hiltViewModel<CartViewModel>(parentEntry)
            CartScreen(navController, parentViewModel)
        }

        composable(
            StackScreensApp.DetailsScreen.name + "/{coffeeId}",
            arguments = listOf(navArgument("coffeeId") { type = NavType.StringType })
        ) { entry ->
            val parentEntry = remember(entry) {
                navController.getBackStackEntry(BottomBarScreen.Home.route)
            }
            val parentCartViewModel = hiltViewModel<CartViewModel>(parentEntry)
            val parentCoffeesViewModel = hiltViewModel<CoffeesViewModel>(parentEntry)
            DetailsScreen(
                coffeeId = entry.arguments?.getString("coffeeId"),
                cartViewModel = parentCartViewModel,
                coffeesViewModel = parentCoffeesViewModel
            )
        }
        composable(route = StackScreensApp.PaymentResume.name) { entry ->
            val parentEntry = remember(entry) {
                navController.getBackStackEntry(BottomBarScreen.Home.route)
            }
            val parentViewModel = hiltViewModel<CartViewModel>(parentEntry)
            PaymentResume(navController, parentViewModel = parentViewModel)
        }

        composable(route = StackScreensApp.PaymentFinished.name) {
            PaymentFinished(navController)
        }


        composable(StackScreensApp.Login.name) {
            LogInScreen(navController = navController)
        }
        composable(StackScreensApp.SignIn.name) {
            SignIn(navController = navController)
        }
        composable(StackScreensApp.MainScreen.name) {
            MainScreen()
        }
    }
}

// crio o componente BottomTab
@Composable
fun BottomCustomNavigation(navHostController: NavHostController, navDestination: NavDestination?) {

    BottomNavigation(backgroundColor = MaterialTheme.colorScheme.tertiaryContainer) {

        BottomScreens.screens().forEach {
            AddItem(
                navController = navHostController,
                screen = it,
                currentDestination = navDestination
            )
        }

    }

}

@Composable
fun RowScope.AddItem(
    navController: NavHostController,
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
) {

    BottomNavigationItem(
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        onClick = {
           navController.navigate(screen.route) {
                     (navController.graph.findStartDestination().id) {
                        saveState = true
                    }

                launchSingleTop = true
                restoreState = true
            }

        },
        icon = {
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = screen.icon),
                contentDescription = "Icon Navigation"
            )
        },
        unselectedContentColor =  MaterialTheme.colorScheme.secondary.copy(0.3f),
        selectedContentColor = MaterialTheme.colorScheme.secondary,
        )

}

//crio  um enum para as rotas do bottom tab

class BottomScreens {
    companion object {
        fun screens(): List<BottomBarScreen> = listOf(
            BottomBarScreen.Home,
            BottomBarScreen.Cart,
            BottomBarScreen.Favorite
        )

    }

}

// preciso tambem criar os item que iram na bottomBar
sealed class BottomBarScreen(
    val route: String,
    val icon: Int,
) {

    object Home : BottomBarScreen(
        route = "home",
        icon = R.drawable.home
    )

    object Favorite : BottomBarScreen(
        route = "favorite",
        icon = R.drawable.heart
    )

    object Cart : BottomBarScreen(
        route = "cart",
        icon = R.drawable.cart
    )

}

```

##
- Em algumas rotas eu não poderia dar opção do usuário retornar, dai existe algumas alternativas como esconder o botão de voltar e deletar  a rota que está salvo no graph
- Para esconder o botão de voltar,topBar ou bottomBar posso pegar a rota atual e verificar se bate com algum do enum que desejo que esteja escondida, para deletar usamos o popUp
- Para pegar a rota atual podemos usar o currentBackStackEntryAsState nele possui o destination
- Em alguns momentos pode ser essencial deletar, por exemplo, se eu não deletar da tela de login até a home, quando a pessoa estiver em detalhes e acionar popBackStack ira retornar para login, nesse caso e um comportamento que não desejamos
  
```kotlin
//para esconder
//estou usando split porque tem rotas minhas que precisam de parametros alguma rota/parametro
val navController = rememberNavController()
val navBackStackEntry by navController.currentBackStackEntryAsState()
val currentDestination = navBackStackEntry?.destination
val currentRoute =
        navBackStackEntry?.destination?.route?.split("/") //porque a rota tem argumento estou fazendo  o split nmo /
val stringRoutesStack = StackScreensApp.values().map { it.toString() }
val stringBottomRoute = BottomScreens.screens().map { it.route }
val isAnonymous = userViewModel.isAnonymous



Scaffold(
        topBar = {
            if (
                stringRoutesStack.contains(currentRoute?.get(0)) && currentRoute?.get(0) != StackScreensApp.PaymentFinished.name && currentRoute?.get(
                    0
                ) != BottomBarScreen.Home.route && currentRoute?.get(0) != StackScreensApp.Login.name
            ) TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    Image(
                        modifier = Modifier
                            .size(15.dp)
                            .clickable {
                                navController.popBackStack()
                            },
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "image back",
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        bottomBar = {
            if (stringBottomRoute.contains(currentRoute?.get(0))) BottomCustomNavigation(
                navHostController = navController,
                navDestination = currentDestination
            )
        }) {
        NavGraphApp(navController = navController, isAnonymous = isAnonymous)
    }
}


// para resetar
    fun handleLogin() {
        val login = UserLoginModel(email, password)
        usersViewModel.loginUser(login) {
            if (it.data != null) {
                navController.navigate(BottomBarScreen.Home.route) {
                    popUpTo(StackScreensApp.Login.name) {
                        inclusive = true
                    }
                }
            } else {
                errorMessage = "Email ou senha incorretos"
            }
        }
    }


```
##

- Para eu [recuperar dados](https://developer.android.com/jetpack/compose/libraries?hl=pt-br#hilt-navigation) de uma view model posso usar o hilt com injeção dependência
- Por exemplo, eu precisava ao clicar em adicionar produtos na tela de detalhes os dados persistia até o carrinho, com o Hitl consigo fazer isso, parecido com gerecimento de estado ou EnviromentObject
- Nesse caso não posso instanciar a viewModel no construtor na tela com hitlViewModel()

```kotlin

  composable(StackScreensApp.MainScreen.name) {
            MainScreen()
        }

        composable(BottomBarScreen.Favorite.route) { entry ->
            val parentEntry = remember(entry) {
                navController.getBackStackEntry(BottomBarScreen.Home.route)
                //tem que ser uma rota ja existente se colocar rota do  carrinho nao vai funcionar, porque o usuario nao inicia nessas tela, ele iria precisar navegar para eu ter a instancia e  favoritos posso ir direto da home
            }
            val parentViewModel = hiltViewModel<CartViewModel>(parentEntry)
            FavoriteScreen(cartViewModel = parentViewModel, navController)
        }

 composable(BottomBarScreen.Cart.route) { entry ->
            val parentEntry = remember(entry) {
                navController.getBackStackEntry(BottomBarScreen.Home.route)
            }
            val parentViewModel = hiltViewModel<CartViewModel>(parentEntry)
            CartScreen(navController, parentViewModel)
        }

        composable(
            StackScreensApp.DetailsScreen.name + "/{coffeeId}",
            arguments = listOf(navArgument("coffeeId") { type = NavType.StringType })
        ) { entry ->
            val parentEntry = remember(entry) {
                navController.getBackStackEntry(BottomBarScreen.Home.route)
            }
            val parentCartViewModel = hiltViewModel<CartViewModel>(parentEntry)
            val parentCoffeesViewModel = hiltViewModel<CoffeesViewModel>(parentEntry)
            DetailsScreen(
                coffeeId = entry.arguments?.getString("coffeeId"),
                cartViewModel = parentCartViewModel,
                coffeesViewModel = parentCoffeesViewModel
            )
        }




//CarScreen

fun CartScreen(navController: NavController, parentViewModel: CartViewModel) {

....
}

```



##
- Existe N maneiras de compartilhar dados em Compose, pode dar uma olhada nesse [tutorial](https://www.youtube.com/watch?v=h61Wqy3qcKg), depende do seu caso de uso
- Para caso do usuário melhor no meu ponto de vista é o   SharedPreferences assim consigo manter o usuário logado
- Abaixo exemplo de uso com Hilt
- Para transformar minha classe em json e usar como string, utilizei o [Mushi](https://github.com/square/moshi), assim consigo salvar meu model como string no SharedPreferences


```kotlin
// crio uma classe view model central
class UserCacheViewModel @Inject constructor(private val sharedPreferences: SharedPreferences) :
    ViewModel(), UserCache {

    
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val adapter = moshi.adapter(UserModel::class.java)

    @SuppressLint("CommitPrefEdits")
    override fun saveUser(user: UserModel) {
        sharedPreferences.edit().putString("user", adapter.toJson(user)).apply()
    }

    override fun getUser(): UserModel? {
        val json = sharedPreferences.getString("user", null) ?: return null
        return adapter.fromJson(json)
    }

    override fun clearUser() {
        sharedPreferences.edit().remove("user").apply()
    }


}

// dai comn singleton compartilho no meu AppModule


 @Provides
    @Singleton
    fun coffeessProvideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("session_prefs", MODE_PRIVATE)

    @Provides
    @Singleton
    fun coffeesUserCache(sharedPreferences: SharedPreferences): UserCacheViewModel =
        UserCacheViewModel(sharedPreferences)



```
##
- Neste aplicativo foi essencial trabalhar com os [ciclos](https://medium.com/@mohammadjoumani/life-cycle-in-jetpack-compose-2e96136ab936) de vida do compose abaixo como podemos implementar
```kotlin
// crio uma funcao composable


@Composable
fun ComposableLifecycle(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onEvent: (LifecycleOwner, Lifecycle.Event) -> Unit
) {

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { source, event ->
            onEvent(source, event)
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }


}

//depois so usar
  ComposableLifecycle { _, event ->
        if (event == Lifecycle.Event.ON_CREATE) {
            coffeesViewModel.getAllCoffees()
            avatarViewModel.getAllAvatars()
        }
    }

```


##
- Para lidar com placeholder criei um modificador


```kotlin

//modificador
fun Modifier.shimmerBackground(shape: Shape = RectangleShape): Modifier = composed {
    val transition = rememberInfiniteTransition(label = "")

    val translateAnimation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 400f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1500, easing = LinearOutSlowInEasing),
            RepeatMode.Restart
        ),
        label = "",
    )
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.9f),
        Color.LightGray.copy(alpha = 0.4f),
    )
    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnimation, translateAnimation),
        end = Offset(translateAnimation + 100f, translateAnimation + 100f),
        tileMode = TileMode.Mirror,
    )
    return@composed this.then(background(brush, shape))
}

//como usar


@Composable
fun TitlePlaceHolder(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(25.dp)
            .width(150.dp)
            .shimmerBackground(RoundedCornerShape(5.dp))
    )
}

```

##
- Para criar um outline  personalizado pode usar o BasicTextField, nele possibilita personalizarmos um decoration e também container
- Para modificar o textfield  onde o usuário não visualiza a senha digitada pode usar o visualTransformation, nele temos a opção de passar PasswordVisualTransformation

```kotlin
@OptIn(
    ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun CustomOutlineTextField(
    placeHolder: String,
    value: String,
    onValueChange: (text: String) -> Unit,
    actionKeyboard: (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leadingIcon:  @Composable() (() -> Unit)? = null,
    singleLine: Boolean = false
) {
    val interactionSource = remember { MutableInteractionSource() }
    val enabled = true
    val keyboardController = LocalSoftwareKeyboardController.current

    //abaixo como contornar altura dinamica no outlineTextField e tambem a largura do width
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        interactionSource = interactionSource,
        visualTransformation = visualTransformation,
        enabled = enabled,
        textStyle = TextStyle(
            fontFamily = fontsInter,
            fontSize = 17.sp,
            color = MaterialTheme.colorScheme.primary
        ),
        keyboardActions = KeyboardActions(onDone = {
            actionKeyboard?.invoke()
            keyboardController?.hide()
        }),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            autoCorrect = false,
            capitalization = KeyboardCapitalization.None
        ),
        singleLine = false,
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth(0.95f)
    ) {
        TextFieldDefaults.OutlinedTextFieldDecorationBox(
            value = value,
            visualTransformation = VisualTransformation.None,
            innerTextField = it,
            leadingIcon = leadingIcon,
            placeholder = {
                Text(
                    text = placeHolder, style = TextStyle(
                        fontSize = 17.sp,
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f),

                        )
                )
            },
            singleLine = singleLine,
            enabled = enabled,
            interactionSource = interactionSource,
            // keep vertical paddings but change the horizontal
            contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                top = 4.dp, bottom = 4.dp, end = 8.dp, start = 8.dp
            ),
        ) {
            TextFieldDefaults.OutlinedBorderContainerBox(
                enabled = enabled,
                isError = false,
                interactionSource = interactionSource,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    focusedBorderColor = MaterialTheme.colorScheme.outline,
                ),
                focusedBorderThickness = 1.dp,
                unfocusedBorderThickness = 1.dp,
            )


        }
    }

}




```

##
- Para criar um swipe onde o usuário pode excluir um item ao arrastar para esquerda ou direita,tempo, opção nativa usando o componente SwipeToDimiss, mas implementei próprio
- Para colocar o ícone no fundo do componnte usei o modificador zIndex
- Box na hierarquia maior que engloba o surface e o responsável pelo lógica do swipeable usando um rememberSwipeAbleState
- Anchors são de onde para onde o card ira andar, no caso reverseDirection, porque meu card movimentava da direita para esquerda
- LaunchedEffect e para lidar com swappableState que é uma função assíncrona coroutine, poderia usar o rememberCoroutineScope


```kotlin
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RowOrders(
    modifier: Modifier = Modifier,
    order: Orders,
    actionAdd: (() -> Unit)? = null,
    actionRemove: (() -> Unit)? = null,
    actionDeleteOrder: (() -> Unit)? = null,
    enableSwipe: Boolean = true,
    actionClickable: (() -> Unit)? = null
) {

    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val width = configuration.screenWidthDp
    val widthPx = with(density) {
        configuration.screenWidthDp.dp.roundToPx()
    }

    val widthBody = LocalConfiguration.current.screenWidthDp * 0.3
    val swappableState = rememberSwipeableState(0)
    val sizePx = with(LocalDensity.current) { width.dp.toPx() }
    val anchors = mapOf(0f to 0, sizePx to 1) // Maps



    //preciso retonar o valor para 0 assim nao corro risco deletar outro da lista que nao e oque desejo
    //launchedEffect permite trabalhar com courtines
    if (swappableState.offset.value.toInt() >= (widthPx - 100)) {
        LaunchedEffect(Unit) {
            actionDeleteOrder?.invoke()

            swappableState.snapTo(0)
        }
    }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .swipeable(
                state = swappableState,
                enabled = enableSwipe,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.3f) },
                orientation = Orientation.Horizontal,
                reverseDirection = true,
            )
            .clickable {
                actionClickable?.invoke()
            }

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.zIndex(-1f), contentAlignment = Alignment.CenterStart) {
                Image(
                    modifier = Modifier.size(25.dp),
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.error)
                )
            }
        }

        Surface(
            modifier = modifier
                .fillMaxWidth()
                .height(75.dp)
                .offset {
                    IntOffset(
                        swappableState.offset.value
                            .roundToInt()
                            .unaryMinus(), 0
                    )
                },
            color = MaterialTheme.colorScheme.secondaryContainer,
            shape = RoundedCornerShape(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(all = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(80.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    model = ImageRequest.Builder(LocalContext.current).data(order.urlImage).build(),
                    contentDescription = "Image Card Row",
                    contentScale = ContentScale.FillHeight
                )

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(widthBody.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = order.title,
                        style = TextStyle(
                            fontFamily = fontsInter,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 15.sp
                        ),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = order.price,
                        style = TextStyle(
                            fontFamily = fontsInter,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 13.sp
                        ),
                    )
                }
                Row(
                    modifier = Modifier
                        .height(25.dp)
                        .width(80.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ButtonWithIcon(
                        modifier = Modifier
                            .width(25.dp)
                            .height(25.dp),
                        icon = painterResource(id = R.drawable.pluss),
                        sizeIcon = 15,
                        action = { actionAdd?.invoke() },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary
                        ),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                    )
                    Text(
                        text = "${order.quantity}",
                        style = TextStyle(
                            fontFamily = fontsInter,
                            fontWeight = FontWeight.Light,
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 15.sp
                        ),
                    )
                    ButtonWithIcon(
                        modifier = Modifier
                            .width(25.dp)
                            .height(25.dp),
                        icon = painterResource(id = R.drawable.minus),
                        sizeIcon = 15,
                        action = { actionRemove?.invoke() },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary
                        ),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                    )
                }
            }
        }

    }
}





```


##
- Para lidar com Modal pode usar o ModalBottomSheetLayout,  precisa estar englobando o restante da árvore
- Atenção o conteúdo que ira mostrar quando o modal abrir e dentro de sheetContent e não o conteúdo que esta englobado nos colchetes
- Para sempre fechar o modal após o usuário clicar o botão  back do Android pode usar o BackHandler que é uma função disponível no Compose 
- Preciso sempre chamar o coroutineScope, pois as funções são assíncronas do modal usando coroutiness

```kotlin

   val coroutineScope = rememberCoroutineScope()
   val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = false
    )


     BackHandler(modalSheetState.isVisible) {
        coroutineScope.launch { modalSheetState.hide() }
    }


    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(12.dp),
        sheetContent = {

            Column(
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 15.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                when (currentField) {
                    "street" -> CustomOutlineTextField(
                        placeHolder = address.street,
                        value = addressStreet,
                        onValueChange = { text -> addressStreet = text },
                        actionKeyboard = {
                            coroutineScope.launch {
                                modalSheetState.hide()
                            }
                        })

                    "streetNumber" -> CustomOutlineTextField(
                        placeHolder = address.streetNumber,
                        value = addressStreetNumber,
                        onValueChange = { text -> addressStreetNumber = text }, actionKeyboard = {
                            coroutineScope.launch {
                                modalSheetState.hide()
                            }
                        })

                    "neighBoard" -> CustomOutlineTextField(
                        placeHolder = address.neighBoard,
                        value = addressNeighborhood,
                        onValueChange = { text -> addressNeighborhood = text }, actionKeyboard = {
                            coroutineScope.launch {
                                modalSheetState.hide()
                            }
                        })

                    else -> CustomOutlineTextField(
                        placeHolder = address.city,
                        value = addressCity,
                        onValueChange = { text -> addressCity = text },
                        actionKeyboard = {
                            coroutineScope.launch {
                                modalSheetState.hide()
                            }
                        })
                }


            }
        }) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.primary) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 12.dp, start = 12.dp, top = 50.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ButtonCustomOutline(
                        modifier = Modifier
                            .fillMaxWidth(0.5f),
                        action = {
                            handleSheetAddress(
                                field = "street"
                            )
                        },
                        text = addressStreet.ifEmpty { address.street.ifEmpty { "Rua" } }
                    )
                    ButtonCustomOutline(
                        modifier = Modifier.fillMaxWidth(0.6f),
                        action = { handleSheetAddress(field = "streetNumber") },
                        text = addressStreetNumber.ifEmpty { address.streetNumber.ifEmpty { "Numero" } }
                    )
                }
                ButtonCustomOutline(
                    action = { handleSheetAddress(field = "neighBoard") },
                    text = addressNeighborhood.ifEmpty { address.neighBoard.ifEmpty { "Bairro" } })
                ButtonCustomOutline(
                    modifier = Modifier.padding(bottom = 10.dp),
                    action = { handleSheetAddress(field = "city") },
                    text = addressCity.ifEmpty { address.city.ifEmpty { "Cidade" } }
                )

                Surface(
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = MaterialTheme.colorScheme.tertiary
                ) {
                    if (isLoading) CircularProgressIndicator(
                        modifier = Modifier
                            .padding(all = 3.dp),
                        color = MaterialTheme.colorScheme.primary,
                        strokeWidth = 1.dp
                    ) else Image(
                        modifier = Modifier
                            .padding(all = 2.dp)
                            .clickable { handleLocation() },
                        painter = painterResource(id = R.drawable.location),
                        contentDescription = "Icon Location",
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.padding(vertical = 5.dp))
                RowTitleAndSubTitle(
                    tile = "Taxa de entrega",
                    subTitle = parentViewModel.orderCart.value.tax
                )
                RowTitleAndSubTitle(
                    tile = "Valor",
                    subTitle = parentViewModel.orderCart.value.priceCartTotal
                )
                Spacer(modifier = Modifier.padding(vertical = 5.dp))
                Divider(thickness = 0.2.dp, color = MaterialTheme.colorScheme.outline)
                Spacer(modifier = Modifier.padding(vertical = 5.dp))
                RowTitleAndSubTitle(tile = "Total", subTitle = totalPrice)
                ButtonCommon(
                    modifier = Modifier.padding(bottom = 24.dp, top = 10.dp),
                    title = "Tudo certo",
                    action = { handleNavigate() },
                    enable = addressCity.isNotEmpty() || addressStreet.isNotEmpty() || addressStreetNumber.isNotEmpty() || addressNeighborhood.isNotEmpty())

            }
        }
    }



```

##
- Para lidar com localização do usuário usando viewModel, primeiro preciso garantir as permissões de acesso à localização
- Lidar com ReserveGeocode pode usar API própria do Android o problema que usuário precisa estar usando versão TIRAMISU
- Para evitar incompatibilidades usei uma [api](https://trueway-geocoding.p.rapidapi.com) de terceiro

```kotlin
   val launcherMultiplePermissions =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions()) { it ->
            val areGranted = it.values.reduce { acc, next -> acc && next }
            if (areGranted) {
                isLoading = true
                userViewModel.getLocation(activity, context) { geocoding ->
                    if (geocoding.data.toString().isNotEmpty()) {
                        val results = geocoding.data?.results?.get(0)
                        val newAddress = AddressUserModel(
                            city = results?.area ?: "",
                            neighBoard = results?.neighborhood ?: "",
                            streetNumber = results?.house ?: "",
                            street = results?.street ?: ""
                        )
                        address = newAddress
                        isLoading = false
                    }
                }
            } else {
                Toast.makeText(
                    context,
                    "Nos precisamos da permissão para acessar localização",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


 fun handleLocation() {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        launcherMultiplePermissions.launch(permissions)

    }

//usei o FusedLocationProviderClient
 private var fusedLocationClient: FusedLocationProviderClient? = null

   fun getLocation(
        activity: Activity,
        context: Context,
        completion: (DataOrException<GeoCodingModel, Boolean, Exception>) -> Unit
    ) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

 
            fusedLocationClient!!.lastLocation.addOnSuccessListener {


                viewModelScope.launch {
                    dataAddressUser.value.isLoading = true
                    completion(geoCodingRepository.getAddress(location = "${it.latitude},${it.longitude}"))
                    if (dataAddressUser.value.toString().isNotEmpty()) {
                        dataAddressUser.value.isLoading = false
                    }
                }
            }
        }


//                @RequiresApi(Build.VERSION_CODES.TIRAMISU)

//                //precisa ser a versao tiramisu
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
//
//                    Geocoder(context).getFromLocation(it.latitude, it.longitude, 3) { address ->
//                        val firstAddress = address.first()
//                        Log.d("subAdmin", "${firstAddress.subAdminArea}") // cidade
//                        Log.d("subThroughfare", "${firstAddress.subThoroughfare}") // numero da rua
//                        Log.d("adminArea", "${firstAddress.subLocality}") // bairro
//                        Log.d("subLocality", "${firstAddress.subLocality}")
//                        Log.d("tourhfare", "${firstAddress.thoroughfare}") //returna nome da rua
//                        Log.d("", firstAddress.locality)
//                    }
//                } else {
//                    Toast.makeText(
//                        context,
//                        "Versão mínima para usar e a 33 precisa atualizar celular",
//                        Toast.LENGTH_LONG
//                    ).show()
//                

```

##
- Para evitar que o texto faça, overflow no compose e preciso usar maxLines com a propriedade overflow
```kotlin

  Text(
            text,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(
                fontFamily = fontsInter,
                fontSize = 17.sp,
                color = MaterialTheme.colorScheme.outline.copy(0.5f),

            ), modifier = Modifier.padding(bottom = 5.dp)
        )
        Divider(
            modifier = Modifier.height(0.5.dp),
            color = MaterialTheme.colorScheme.outline.copy(0.5f)
        )


```


## Link publicacao
- [Linkedin](https://www.linkedin.com/posts/kenjimaeda1233_compose-android-swiftui-activity-7127826513076400129-6vAc?utm_source=share&utm_medium=member_desktop)

  


