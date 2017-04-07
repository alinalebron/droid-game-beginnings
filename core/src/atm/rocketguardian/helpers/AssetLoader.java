package atm.rocketguardian.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

public class AssetLoader {
	public static Animation<TextureAtlas.AtlasRegion> miniZombieAnimation, zombieGuyAnimation, guardianAnimation,
			platformAnimation, whiteGuyAnimation, whiteGuyGreenAnimation, blackGuyAnimation, girlAnimation,
			whiteBabyAnimation, blackBabyAnimation, iceExplosionAnimation;
	public static TextureAtlas atlas;
	public static AtlasRegion moonLayer, cityLayer, lightsLayer, floor, lifeIcon, logo;
	public static Skin skin;
	public static Array<Sprite> cityArray;
	public static Music menuMusic, gameMusic;
	public static Sound clickSound, newRecordSound, hitSound;
	public static Music explosionSound;

	public static void load() {

		// Initialize assets
		atlas = new TextureAtlas(Gdx.files.internal("img/rocketguardian.atlas"));
		skin = new Skin(Gdx.files.internal("img/skin.json"));
		moonLayer = new AtlasRegion(atlas.findRegion("moon"));
		cityLayer = new AtlasRegion(atlas.findRegion("city"));
		lightsLayer = new AtlasRegion(atlas.findRegion("light"));
		floor = new AtlasRegion(atlas.findRegion("floor"));
		lifeIcon = new AtlasRegion(atlas.findRegion("life"));
		logo = new AtlasRegion(atlas.findRegion("logo"));
		miniZombieAnimation = new Animation<TextureAtlas.AtlasRegion>(0.15f, atlas.findRegions("minizombie"),
				PlayMode.LOOP);
		zombieGuyAnimation = new Animation<TextureAtlas.AtlasRegion>(0.2f, atlas.findRegions("zombieguy"),
				PlayMode.LOOP);
		guardianAnimation = new Animation<TextureAtlas.AtlasRegion>(0.1f, atlas.findRegions("rocketguard"),
				PlayMode.LOOP);
		platformAnimation = new Animation<TextureAtlas.AtlasRegion>(0.15f, atlas.findRegions("longplatform"),
				PlayMode.LOOP_PINGPONG);
		whiteGuyAnimation = new Animation<TextureAtlas.AtlasRegion>(0.2f, atlas.findRegions("whiteguy"), PlayMode.LOOP);
		whiteGuyGreenAnimation = new Animation<TextureAtlas.AtlasRegion>(0.2f, atlas.findRegions("whiteguy_green"),
				PlayMode.LOOP);
		blackGuyAnimation = new Animation<TextureAtlas.AtlasRegion>(0.2f, atlas.findRegions("blackguy"), PlayMode.LOOP);
		girlAnimation = new Animation<TextureAtlas.AtlasRegion>(0.2f, atlas.findRegions("girl"), PlayMode.LOOP);
		whiteBabyAnimation = new Animation<TextureAtlas.AtlasRegion>(0.15f, atlas.findRegions("whitebaby"),
				PlayMode.LOOP);
		blackBabyAnimation = new Animation<TextureAtlas.AtlasRegion>(0.2f, atlas.findRegions("blackbaby"),
				PlayMode.LOOP);
		iceExplosionAnimation = new Animation<TextureAtlas.AtlasRegion>(0.1f, atlas.findRegions("iceexplosion"),
				PlayMode.NORMAL);
		
		// Music
		menuMusic = Gdx.audio.newMusic(Gdx.files.internal("music/menu_music.ogg"));
		//gameMusic = Gdx.audio.newMusic(Gdx.files.internal("music/actionmusic.ogg"));
		
		menuMusic.setLooping(false);
		//gameMusic.setLooping(false);
		
		// SFX
		clickSound = Gdx.audio.newSound(Gdx.files.internal("sound/clickbutton.wav"));
		explosionSound = Gdx.audio.newMusic(Gdx.files.internal("sound/explosion.ogg"));
		newRecordSound = Gdx.audio.newSound(Gdx.files.internal("sound/newrecord.wav"));
		hitSound = Gdx.audio.newSound(Gdx.files.internal("sound/hit.wav"));

		// City Layer array
		cityArray = new Array<Sprite>();
		Sprite citySprite = new Sprite(cityLayer);
		citySprite.setSize(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
		citySprite.setX(0);
		cityArray.add(citySprite);

		citySprite = new Sprite(cityLayer);
		citySprite.setSize(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
		citySprite.setX(Constants.GAME_WIDTH);
		cityArray.add(citySprite);
	}

	public static void dispose() {
		atlas.dispose();
	}

}
