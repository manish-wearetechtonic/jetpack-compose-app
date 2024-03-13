sealed class Screens(val route: String) {
    object ScreensHomeRoute : Screens(route = "home")
    object ScreensChatRoute : Screens(route = "chat")
    object ScreensSettingsRoute : Screens(route = "settings")
    object ScreensLoginRoute : Screens(route = "login")
    object ScreensLandingPageRoute : Screens(route = "landing-page")

    object ScreensProductsRoute : Screens(route = "products")



}
