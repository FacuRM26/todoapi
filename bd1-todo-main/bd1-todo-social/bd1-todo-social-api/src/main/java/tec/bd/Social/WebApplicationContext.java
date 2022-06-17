package tec.bd.Social;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zaxxer.hikari.HikariConfig;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tec.bd.Social.authentication.AuthenticationClient;
import tec.bd.Social.authentication.AuthenticationClientImpl;
import tec.bd.Social.authentication.AuthenticationResource;
import tec.bd.Social.datasource.DBManager;
import tec.bd.Social.datasource.HikariDBManager;
import tec.bd.Social.repository.RatingsRepository;
import tec.bd.Social.repository.RatingsRepositoryImpl;
import tec.bd.Social.repository.ReviewsRepository;
import tec.bd.Social.repository.ReviewsRepositoryImpl;
import tec.bd.Social.service.RatingsService;
import tec.bd.Social.service.RatingsServiceImpl;
import tec.bd.Social.service.ReviewsService;
import tec.bd.Social.service.ReviewsServiceImpl;
import tec.bd.todo.auth.repository.ClientRepositoryImpl;

public class WebApplicationContext {


    private AuthenticationClient authenticationClient;
    private DBManager dbManager;
    private RatingsRepository ratingsRepository;
    private RatingsService ratingsService;
    private ReviewsRepository reviewsRepository;
    private ReviewsService reviewsService;


    private WebApplicationContext() {

    }
    public static WebApplicationContext init() {
        WebApplicationContext webAppContext = new WebApplicationContext();
        initAuthenticationClient(webAppContext);
        initDBManager(webAppContext);
        initRatingsRepository(webAppContext);

        initRatingsService(webAppContext);
        initReviewsRepository(webAppContext);
        initReviewsService(webAppContext);


        return webAppContext;
    }
    private static void initDBManager(WebApplicationContext webApplicationContext) {
        HikariConfig hikariConfig = new HikariConfig();
        var jdbcUrl = System.getenv("JDBC_SOCIAL_DB_URL");
        var username = System.getenv("JDBC_SOCIAL_DB_USERNAME");
        var password = System.getenv("JDBC_SOCIAL_DB_PASSWORD");
        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.addDataSourceProperty("connectionTestQuery", "SELECT 1");
        hikariConfig.addDataSourceProperty("maximumPoolSize", "4");
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        DBManager dbManager = new HikariDBManager(hikariConfig);
        webApplicationContext.dbManager = dbManager;
    }
    private static void initAuthenticationClient(WebApplicationContext webApplicationContext) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AuthenticationResource authenticationResource = retrofit.create(AuthenticationResource.class);
        webApplicationContext.authenticationClient = new AuthenticationClientImpl(authenticationResource);
    }
    private static void initRatingsRepository(WebApplicationContext webApplicationContext) {
        var dbManager = webApplicationContext.dbManager;
        webApplicationContext.ratingsRepository = new RatingsRepositoryImpl(dbManager);
    }
    private static void initRatingsService(WebApplicationContext webApplicationContext) {
        var ratingsRepository = webApplicationContext.ratingsRepository;
        webApplicationContext.ratingsService = new RatingsServiceImpl(ratingsRepository);
    }
    private static void initReviewsRepository(WebApplicationContext webApplicationContext) {
        var dbManager = webApplicationContext.dbManager;
        webApplicationContext.reviewsRepository = new ReviewsRepositoryImpl(dbManager);
    }
    private static void initReviewsService(WebApplicationContext webApplicationContext) {
        var reviewsRepository = webApplicationContext.reviewsRepository;
        webApplicationContext.reviewsService = new ReviewsServiceImpl(reviewsRepository);
    }
    public Gson getGson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();
    }
    public RatingsRepository getRatingsRepository() {
        return ratingsRepository;
    }
    public RatingsService getRatingsService() {
        return ratingsService;
    }
    public AuthenticationClient getAuthenticationClient() {
        return authenticationClient;
    }
    public ReviewsRepository getReviewsRepository() {
        return reviewsRepository;
    }
    public ReviewsService getReviewsService() {
        return reviewsService;
    }

}


