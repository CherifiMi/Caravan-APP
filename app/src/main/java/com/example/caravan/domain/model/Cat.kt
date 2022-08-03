package com.example.caravan.domain.model

data class Cat(
    val id: String?,
    val name: String,
    val subCats: List<String>,
    val picUrl: String
)

data class CatList(val catList: List<Cat>)

val mokeCats = CatList(
    listOf(
        Cat(
            id = null,
            name = "Games",
            subCats = listOf("mito", "phone", "mito2"),
            picUrl = "https://cdn-icons-png.flaticon.com/512/5260/5260498.png"
        ),
        Cat(
            id = null,
            name = "Home",
            subCats = listOf(),
            picUrl = "https://cdn-icons-png.flaticon.com/512/1299/1299961.png"
        ),
        Cat(
            id = null,
            name = "Electronics",
            subCats = listOf(),
            picUrl = "https://cdn-icons-png.flaticon.com/512/4752/4752790.png"
        ),
        Cat(
            id = null,
            name = "Books",
            subCats = listOf(),
            picUrl = "https://cdn-icons-png.flaticon.com/512/3296/3296160.png"
        ),
        Cat(
            id = null,
            name = "Cloths",
            subCats = listOf(),
            picUrl = "https://cdn-icons-png.flaticon.com/512/1461/1461304.png"
        )
    )
)