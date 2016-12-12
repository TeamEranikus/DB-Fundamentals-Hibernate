package escape.code.configurations;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import escape.code.controllers.*;
import escape.code.core.game.Game;
import escape.code.core.game.GameImpl;
import escape.code.core.stageManager.StageManager;
import escape.code.core.stageManager.StageManagerImpl;
import escape.code.core.engine.Engine;
import escape.code.core.engine.EngineImpl;
import escape.code.daos.PuzzleDao;
import escape.code.daos.PuzzleRectangleDao;
import escape.code.daos.ScoreDao;
import escape.code.daos.UserDao;
import escape.code.daos.impls.PuzzleDaoImpl;
import escape.code.daos.impls.PuzzleRectangleDaoImpl;
import escape.code.daos.impls.ScoreDaoImpl;
import escape.code.daos.impls.UserDaoImpl;
import escape.code.models.sprite.Sprite;
import escape.code.models.sprite.SpriteImpl;
import escape.code.services.PuzzleRectangleService;
import escape.code.services.PuzzleService;
import escape.code.services.ScoreService;
import escape.code.services.UserService;
import escape.code.services.impls.PuzzleRectangleServiceImpl;
import escape.code.services.impls.PuzzleServiceImpl;
import escape.code.services.impls.ScoreServiceImpl;
import escape.code.services.impls.UserServiceImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Injects game dependencies by binding interfaces to corresponding implementations
 */
public class InjectionModule extends AbstractModule {

    private static final ThreadLocal<EntityManager> ENTITY_MANAGER_CACHE = new ThreadLocal<>();

    /**
     * Binds interfaces tho corresponding implementations
     */
    public void configure() {
        this.bind(UserDao.class).to(UserDaoImpl.class);
        this.bind(PuzzleDao.class).to(PuzzleDaoImpl.class);
        this.bind(PuzzleRectangleDao.class).to(PuzzleRectangleDaoImpl.class);
        this.bind(ScoreDao.class).to(ScoreDaoImpl.class);
        this.bind(StageManager.class).to(StageManagerImpl.class);
        this.bind(Game.class).to(GameImpl.class);
        this.bind(Engine.class).to(EngineImpl.class);
        this.bind(UserService.class).to(UserServiceImpl.class);
        this.bind(PuzzleService.class).to(PuzzleServiceImpl.class);
        this.bind(PuzzleRectangleService.class).to(PuzzleRectangleServiceImpl.class);
        this.bind(ScoreService.class).to(ScoreServiceImpl.class);
        this.bind(Sprite.class).to(SpriteImpl.class);

        this.requestStaticInjection(LoginController.class);
        this.requestStaticInjection(MenuController.class);
        this.requestStaticInjection(HowToPlayController.class);
        this.requestStaticInjection(GameFinishedController.class);
        this.requestStaticInjection(ScoreboardController.class);
    }

    /**
     * Provides entity manager by given entity manager factory, singleton implementation
     * @param entityManagerFactory - creates entity manager
     * @return created entity manager
     */
    @Provides
    @Singleton
    public EntityManager provideEntityManager(EntityManagerFactory entityManagerFactory) {
        EntityManager entityManager = ENTITY_MANAGER_CACHE.get();
        if (entityManager == null) {
            ENTITY_MANAGER_CACHE.set(entityManager = entityManagerFactory.createEntityManager());
        }
        return entityManager;
    }

    /**
     * Provides entity manager factory, used to persist the entity manager
     * @return created entity manager factory
     */
    @Provides
    @Singleton
    private EntityManagerFactory provideEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("escape_code");
    }
}
