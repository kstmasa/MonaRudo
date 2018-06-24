package com.mona.game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
//import com.mona.game.Enemy.Enemy;
import com.mona.game.Enemy.Enemy;
import com.mona.game.MainScene.GameOver;
import com.mona.game.MainScene.mapChoose;
import com.mona.game.character.HUD;
import com.mona.game.windowHandler.B2DVars;
import com.mona.game.windowHandler.BackGround;
import com.mona.game.windowHandler.bbContactListener;
import com.mona.game.windowHandler.boundedCamera;

import static com.mona.game.windowHandler.B2DVars.BIT_ENEMY;
import static com.mona.game.windowHandler.B2DVars.PPM;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mona.game.character.character;
import com.mona.game.MonaGame;
import com.mona.game.windowHandler.DirectionGestureDetector;
import com.badlogic.gdx.ApplicationListener;

/**
 * Created by TED on 2015/12/30.
 */
public class Play implements Screen{
    MonaGame monaGame;
    private World world;
    private bbContactListener cl;
    private Box2DDebugRenderer b2dRenderer;
    private boundedCamera b2dCam;
    private SpriteBatch spriteBatch;
    private OrthographicCamera camera;
    private character player;
    private HUD hud;
   private Array<Enemy> enemy;
    //////////////////////////////////
    //Tiled Map Block
    //////////////////////////////////
    private TiledMap tiledMap;
    private int tiledMapWidth;
    private int tiledMapHeight;
    private int tiledSize;
    private OrthogonalTiledMapRenderer tmRenderer;
    private boundedCamera bcam;
    //////////////////////////////////
    private BackGround backgrounds;
    public Play(MonaGame mona){
        this.monaGame = mona;
        this.setGestureDetector();
        world = new World(new Vector2(0,-7f),true);
        b2dRenderer = new Box2DDebugRenderer();
        cl = new bbContactListener();
        world.setContactListener(cl);
        // create player
        createCharater();
        //create view
        bcam = new boundedCamera();
        bcam.setToOrtho(false,MonaGame.V_WIDTH,MonaGame.V_HEIGHT);
        camera = new OrthographicCamera();
        camera.setToOrtho(false,MonaGame.V_WIDTH,MonaGame.V_HEIGHT);
        spriteBatch = new SpriteBatch();
        //create hud
        hud = new HUD(player);
        //create walls
        createWalls();
        bcam.setBounds(0, tiledMapWidth * tiledSize, 0, tiledMapHeight * tiledSize);
        //create enemy
        createEnemy();
        //crate background
        Texture bgs = new Texture(Gdx.files.internal("background.jpg"));
        TextureRegion bg = new TextureRegion(bgs);
        backgrounds = new BackGround(bg, camera,0.4f);
        b2dCam = new boundedCamera();
        b2dCam.setToOrtho(false, MonaGame.V_WIDTH / PPM, MonaGame.V_HEIGHT / PPM);
        b2dCam.setBounds(0, (tiledMapWidth * tiledSize) / PPM, 0, (tiledMapHeight * tiledSize) / PPM);

    }

    /**
     * Set up box2d bodies for crystals in tile map "crystals" layer
     */

    private void createEnemy() {

        // create list of crystal
        enemy = new Array<Enemy>();

        // get all crystals in "crystals" layer,
        // create bodies for each, and add them
        // to the crystals list
        MapLayer ml = tiledMap.getLayers().get("enemy");
        if(ml == null) return;

        for(MapObject mo : ml.getObjects()) {
            BodyDef cdef = new BodyDef();
            cdef.type = BodyType.StaticBody;
            cdef.position.set(60 / PPM, 120 / PPM);
            float x =  mo.getProperties().get("x",Float.class) / PPM;
            float y = mo.getProperties().get("y",Float.class) / PPM;
           cdef.position.set(x, y+7/100f);
            Body body = world.createBody(cdef);
            FixtureDef cfdef = new FixtureDef();
            PolygonShape cshape = new PolygonShape();
           cshape.setAsBox(21/PPM,24/PPM+7/100f);
            cfdef.shape = cshape;
            cfdef.isSensor = true;
            cfdef.filter.categoryBits = B2DVars.BIT_ENEMY;
            cfdef.filter.maskBits = B2DVars.BIT_PLAYER;
            body.createFixture(cfdef).setUserData("enemy");
            Enemy e = new Enemy(body);
            body.setUserData(e);
            enemy.add(e);
            cshape.dispose();
        }
    }
     public void update(float dt)
     {
         // update box2d world

     }
    private void createCharater(){

        // create bodydef
        BodyDef bdef = new BodyDef();
        bdef.type = BodyType.DynamicBody;
        bdef.position.set(60 / PPM, 120 / PPM);
        bdef.fixedRotation = true;
        bdef.linearVelocity.set(1f, 0f);

        // create body from bodydef
        Body body = world.createBody(bdef);

        // create box shape for player collision box
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(32/PPM, 26/ PPM);

        // create fixturedef for player collision box
        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.density = 1;
        fdef.friction = 0;
        fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
        fdef.filter.maskBits = B2DVars.BIT_GRAPHICS_BLOCK |B2DVars.BIT_ENEMY;

        // create player collision box fixture
        body.createFixture(fdef);
        shape.dispose();

        // create box shape for player foot
        shape = new PolygonShape();
        shape.setAsBox(32/ PPM, 15/ PPM, new Vector2(0, -13 / PPM), 0);

        // create fixturedef for player foot
        fdef.shape = shape;
        fdef.isSensor = true;
        fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
        fdef.filter.maskBits = B2DVars.BIT_GRAPHICS_BLOCK;

        // create player foot fixture
        body.createFixture(fdef).setUserData("foot");;
        shape.dispose();

        // create new player
        player = new character(body);
        body.setUserData(player);

        // final tweaks, manually set the player body mass to 1 kg
        MassData md = body.getMassData();
        md.mass = 1;
        body.setMassData(md);

        // i need a ratio of 0.005
        // so at 1kg, i need 200 N jump force
    }
    private void createWalls() {
        tiledMap = new TmxMapLoader().load("tiled/level.tmx");

        tiledMapWidth = tiledMap.getProperties().get("width", Integer.class);
        tiledMapHeight = tiledMap.getProperties().get("height", Integer.class);
        tiledSize =  tiledMap.getProperties().get("tilewidth", Integer.class);
        tmRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        System.out.println(tiledMapWidth);
        System.out.println(tiledMapHeight);
        TiledMapTileLayer layer;
        layer = (TiledMapTileLayer) tiledMap.getLayers().get("Graphics");
        createBlocks(layer, B2DVars.BIT_GRAPHICS_BLOCK);
    }
    private void createBlocks(TiledMapTileLayer layer, short bits) {

        // tile size
        float ts = layer.getTileWidth();
        // go through all cells in layer
        for(int row = 0; row < layer.getHeight(); row++) {
            for(int col = 0; col < layer.getWidth(); col++) {
                // get cell
                TiledMapTileLayer.Cell cell = layer.getCell(col, row);
                // check that there is a cell
                if(cell == null) continue;
                if(cell.getTile() == null) continue;

                // create body from cell
                BodyDef bdef = new BodyDef();
                bdef.type = BodyType.StaticBody;
                bdef.position.set((col + 0.5f) * ts / PPM, (row + 0.5f) * ts / PPM);
                ChainShape cs = new ChainShape();
                Vector2[] v = new Vector2[3];
                v[0] = new Vector2(-ts / 2 / PPM, -ts / 2 / PPM);
                v[1] = new Vector2(-ts / 2 / PPM, ts / 2 / PPM);
                v[2] = new Vector2(ts / 2 / PPM, ts / 2 / PPM);
                cs.createChain(v);
                FixtureDef fd = new FixtureDef();
                fd.friction = 0;
                fd.shape = cs;
                fd.filter.categoryBits = bits;
                fd.filter.maskBits = B2DVars.BIT_PLAYER;
                world.createBody(bdef).createFixture(fd);
                cs.dispose();
            }
        }

    }
    @Override
    public void show() {
    }
    protected void swipeUpHandler() {
        player.getBody().setLinearVelocity(player.getBody().getLinearVelocity().x, 0);
        player.getBody().applyForceToCenter(0, 150, true);
    }
    /**
     * Set the gesture detector for the game
     */
    protected void setGestureDetector() {
        // Gesture detector for the game
        Gdx.input.setInputProcessor(new DirectionGestureDetector(
                new DirectionGestureDetector.DirectionListener() {

                    @Override
                    public void onUp() {
                        // TODO Auto-generated method stub
                        player.setShowAnimationFunction(B2DVars.JUMP, 1 / 2f);
                        swipeUpHandler();
                    }

                    @Override
                    public void onRight() {
                        // TODO Auto-generated method stub
                      //  swipeRightHandler();
                        if(player.getBody().getLinearVelocity().x==0)
                        {
                            player.getBody().setLinearVelocity(1,0);
                        }
                        player.setShowAnimationFunction(B2DVars.ATTACK,1/12f);
                    }

                    @Override
                    public void onLeft() {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onDown() {
                        // TODO Auto-generated method stub
                        //swipeDownHandler();
                    }
                }));
    }
    @Override
    public void render(float delta) {
        world.step(MonaGame.STEP, 1, 1);
        player.update(delta);

        // camera follow player
        bcam.setPosition(player.getPosition().x * PPM + MonaGame.V_WIDTH / 4, MonaGame.V_HEIGHT / 2);
        bcam.update();
        spriteBatch.setProjectionMatrix(camera.combined);
        backgrounds.render(spriteBatch);

        tmRenderer.setView(bcam);
        tmRenderer.render();

        //
        spriteBatch.setProjectionMatrix(camera.combined);
        hud.render(spriteBatch);


        // draw player
        spriteBatch.setProjectionMatrix(bcam.combined);
        player.render(spriteBatch);
        //player dead
        if(player.getBody().getPosition().y<0|| player.getHealth() == 0)
        {
            monaGame.setScreen(new GameOver(monaGame));
            monaGame.music.removeMusic("gameBGM");
        }
        //draw enemy
        for(int i = 0; i < enemy.size; i++)
        {
            enemy.get(i).update(delta);
        }
        //draw enemy
        for(int i = 0; i < enemy.size; i++)
        {
            enemy.get(i).render(spriteBatch);
        }

        Array<Body> bodies = cl.getBodies();

        for(int i = 0; i < bodies.size; i++) {
            if(player.currentAction == B2DVars.ATTACK) {
                Body b = bodies.get(i);
                enemy.removeValue((Enemy) b.getUserData(), true);
                world.destroyBody(bodies.get(i));
            }else{
                player.byAttack();
            }
        }
        bodies.clear();
      /*  b2dCam.setPosition(player.getPosition().x + MonaGame.V_WIDTH / 4 / PPM, MonaGame.V_HEIGHT / 2 / PPM);
        b2dCam.update();
        b2dRenderer.render(world, b2dCam.combined);
*/
        if(cl.getGesture() ==false){
            System.out.println(cl.getGesture());
            player.setShowAnimationFunction(B2DVars.NORML,1/10f);

            cl.setGesture();
        }}

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }


}
