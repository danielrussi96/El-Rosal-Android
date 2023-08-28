package com.app.elrosal

import com.app.data.EnvironmentConfig
import com.app.data.control.TimeStampResource
import com.app.data.datasource.CategoriesLocalDataSource
import com.app.data.datasource.CategoriesRemoteDataSource
import com.app.data.datasource.DetailRemoteDataSource
import com.app.data.datasource.ProductsRemoteDataSource
import com.app.data.datasource.UserLocalDataSource
import com.app.data.datasource.UserRemoteDataSource
import com.app.domain.categories.remote.Category
import com.app.domain.categories.remote.SubCategories
import com.app.domain.details.DetailProduct
import com.app.domain.details.ProductDescription
import com.app.domain.details.RecommendedProduct
import com.app.domain.products.Detail as DetailProductImages
import com.app.domain.products.Product
import com.app.domain.products.Products
import com.app.domain.user.Whatsapp
import com.app.elrosal.ui.main.sampleWhatsapp
import com.app.library.data.server.CategoriesServerDataSource
import com.app.library.data.server.DetailsServerDataSource
import com.app.library.di.dataModule
import com.app.library.di.useCaseModule
import com.app.library.shared.network.KtorClientFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

fun initMockedDi(vararg module: Module) {
    startKoin {
        modules(listOf(mockedAppModule, dataModule, useCaseModule) + module)
    }
}


private val mockedAppModule = module {
    single<CategoriesRemoteDataSource> { FakeCategoriesServerDataSource() }
    single<ProductsRemoteDataSource> { FakeProductsServerDataSource() }
    single<DetailRemoteDataSource> { FakeDetailsServerDataSource() }
    single<CategoriesLocalDataSource> { FakeCategoriesDatabaseDataSource() }
    single<TimeStampResource> { FakeTimeStampDatabaseDataSource() }
    single<UserLocalDataSource> { FakeUserDatabaseDataSource() }
    single<UserRemoteDataSource> { FakeUserServerDataSource() }
}


class FakeUserServerDataSource : UserRemoteDataSource {
    override suspend fun sendMessageWhatsapp(
        environmentConfig: EnvironmentConfig,
        whatsapp: Whatsapp,
        success: () -> Unit,
        error: (String) -> Unit
    ) {
        success()
    }

}

class FakeUserDatabaseDataSource : UserLocalDataSource {

    private var phoneNumber: String = sampleWhatsapp.phoneNumber
    override fun getUser(): Flow<String?> {
        return flowOf(phoneNumber)
    }

    override fun insertUser(phoneNumber: String?) {
        this.phoneNumber = phoneNumber ?: ""
    }

}

class FakeTimeStampDatabaseDataSource : TimeStampResource {

    private var timeStamp: Long? = null
    override fun getTimeStamp(): Long? {
        return timeStamp
    }

    override fun insertTimeStamp(timeStamp: Long) {
        this.timeStamp = timeStamp
    }

    override fun updateTimeStamp(timeStamp: Long) {
        this.timeStamp = timeStamp
    }

}

class FakeCategoriesDatabaseDataSource : CategoriesLocalDataSource {

    private var categories: List<Category> = sampleCategories
    override fun deleteCategories() {
        categories = emptyList()
    }

    override fun getCategories(): List<Category> {
        return categories
    }

    override fun insertCategories(categories: List<Category>) {
        this.categories = categories
    }

}

class FakeDetailsServerDataSource : DetailRemoteDataSource {
    override suspend fun getDetail(
        environmentConfig: EnvironmentConfig,
        id: String
    ): ProductDescription = ProductDescription(sampleDetailProduct, sampleRecommendedProduct)

}

class FakeProductsServerDataSource : ProductsRemoteDataSource {
    override suspend fun getProducts(
        environmentConfig: EnvironmentConfig,
        id: String,
        paginate: Int
    ): Products = Products(
        listProducts = sampleProducts,
        statusCode = 200,
        totalPages = 0
    )
}


class FakeCategoriesServerDataSource : CategoriesRemoteDataSource {

    private var categories: List<Category> = sampleCategories

    private var subCategories: SubCategories = sampleSubCategories
    override suspend fun getCategories(environmentConfig: EnvironmentConfig): List<Category> =
        categories

    override suspend fun getSubCategories(
        environmentConfig: EnvironmentConfig,
        id: String
    ): SubCategories = subCategories

}

private val sampleDetail = com.app.domain.details.Detail(
    image = "https://firebasestorage.googleapis.com/v0/b/el-rosal-177df.appspot.com/o/RosalStorage%",
    title = "PSG1vz4CLYLpEkY8XNpJ"
)

val sampleDetailProduct = DetailProduct(
    description = "La Sirenita",
    details = listOf(sampleDetail),
    image = "https://firebasestorage.googleapis.com/v0/b/el-rosal-177df.appspot.com/o/RosalStorage%",
    name = "SI003"
)

val sampleRecommendedProduct = listOf(
    RecommendedProduct(
        description = "Diseño Paw Patrol",
        id = "69KNxcLlLY9z2msM5wYC",
        image = "https://firebasestorage.googleapis.com/v0/b/el-rosal-177df.appspot.com/o/RosalStorage%",
        name = "PP004"
    ),
    RecommendedProduct(
        description = "Micky Mouse",
        id = "8X40mw0hcMcVvcoNCSZQ",
        image = "https://firebasestorage.googleapis.com/v0/b/el-rosal-177df.appspot.com/o/RosalStorage%",
        name = "MM004"
    ),
    RecommendedProduct(
        description = "Diseño Paw Patrol",
        id = "32w6KInhaHQe5Zg3LWAp",
        image = "https://firebasestorage.googleapis.com/v0/b/el-rosal-177df.appspot.com/o/RosalStorage%",
        name = "PP002"
    ),
)

private val sampleCategories = listOf(
    Category(
        id = "85PXtMUmI3pIHUQ33P1D",
        image = "https://firebasestorage.googleapis.com/v0/b/el-rosal-177df.appspot.com/o/RosalStorage%2FCategories",
        name = "Desayunos",
        position = 0
    ),
    Category(
        id = "DXvVyEZBfarCKiHDtLd6",
        image = "https://firebasestorage.googleapis.com/v0/b/el-rosal-177df.appspot.com/o/RosalStorage%2FCategories",
        name = "Antojos Tradicionales",
        position = 1
    ),
    Category(
        id = "JUsz09rqDKpM2xMkdf1A",
        image = "https://firebasestorage.googleapis.com/v0/b/el-rosal-177df.appspot.com/o/RosalStorage%2FCategories",
        name = "Postres",
        position = 2
    ),
    Category(
        id = "MwFFRSojj8cqJz8e2GGX",
        image = "https://firebasestorage.googleapis.com/v0/b/el-rosal-177df.appspot.com/o/RosalStorage%2FCategories",
        name = "Bebidas",
        position = 3
    )
)

val subCategories = listOf(
    Category(
        id = "AtrwqDDlU40PyhipMhkr",
        image = "https://firebasestorage.googleapis.com/v0/b/el-rosal-177df.appspot.com/o/RosalStorage%2FCategories",
        name = "Saludable",
        position = 0
    ),
    Category(
        id = "PSG1vz4CLYLpEkY8XNpJ",
        image = "https://firebasestorage.googleapis.com/v0/b/el-rosal-177df.appspot.com/o/RosalStorage%2FCategories",
        name = "Caldos - Changuas",
        position = 1
    ),
    Category(
        id = "SnyziXq1DyVrIjPUOGoQ",
        image = "https://firebasestorage.googleapis.com/v0/b/el-rosal-177df.appspot.com/o/RosalStorage%2FCategories",
        name = "Arepas",
        position = 2
    ),
    Category(
        id = "cu82ILqbXGcwG4Q6zNAN",
        image = "https://firebasestorage.googleapis.com/v0/b/el-rosal-177df.appspot.com/o/RosalStorage%2FCategories",
        name = "Combos",
        position = 3
    ),
    Category(
        id = "jr8XpMmlmvyv5x7jjlSh",
        image = "https://firebasestorage.googleapis.com/v0/b/el-rosal-177df.appspot.com/o/RosalStorage%2FCategories",
        name = "Bebidas Calientes",
        position = 4
    ),
    Category(
        id = "lS9Lthyn9XnbvsYeYqf6",
        image = "https://firebasestorage.googleapis.com/v0/b/el-rosal-177df.appspot.com/o/RosalStorage%2FCategories",
        name = "Calentados",
        position = 5
    ),
    Category(
        id = "w6eRmxZufr6K0KnNFcGM",
        image = "https://firebasestorage.googleapis.com/v0/b/el-rosal-177df.appspot.com/o/RosalStorage%2FCategories%",
        name = "Huevos",
        position = 6
    ),
)

val sampleSubCategories: SubCategories = SubCategories(
    subCategories = subCategories,
    name = sampleCategories[0].name,
)

private val sampleDetailProducts = DetailProductImages(
    image = "https://firebasestorage.googleapis.com/v0/b/el-rosal-177df.appspot.com/o/RosalStorage%",
    title = "PSG1vz4CLYLpEkY8XNpJ"
)

val sampleProducts = listOf(
    Product(
        categoryId = "y6TobdgcYGT5YQ6z4p0A",
        description = "description",
        details = listOf(sampleDetailProducts),
        id = "32w6KInhaHQe5Zg3LWAp",
        image = "https://firebasestorage.googleapis.com/v0/b/el-rosal-177df.appspot.com/o/RosalStorage%",
        name = "PP002",
        position = 0,
        subCategoryId = "ZFiEtElNkToRxfFi3Glu"
    ),
    Product(
        categoryId = "y6TobdgcYGT5YQ6z4p0A",
        description = "La Sirenita",
        details = listOf(sampleDetailProducts),
        id = "56SZxrcDUbklUztkPinj",
        image = "https://firebasestorage.googleapis.com/v0/b/el-rosal-177df.appspot.com/o/RosalStorage%",
        name = "SI003",
        position = 2,
        subCategoryId = "0SGpe0A0jOyXySF9Wb6p"
    ),
    Product(
        categoryId = "y6TobdgcYGT5YQ6z4p0A",
        description = "Diseño Paw Patrol",
        details = listOf(sampleDetailProducts),
        id = "69KNxcLlLY9z2msM5wYC",
        image = "https://firebasestorage.googleapis.com/v0/b/el-rosal-177df.appspot.com/o/RosalStorage%",
        name = "PP004",
        position = 3,
        subCategoryId = "ZFiEtElNkToRxfFi3Glu"
    ),
    Product(
        categoryId = "y6TobdgcYGT5YQ6z4p0A",
        description = "Micky Mouse",
        details = listOf(sampleDetailProducts),
        id = "8X40mw0hcMcVvcoNCSZQ",
        image = "https://firebasestorage.googleapis.com/v0/b/el-rosal-177df.appspot.com/o/RosalStorage%",
        name = "MM004",
        position = 4,
        subCategoryId = "y3TMDSd3fgZAx494uHWx"
    ),
)