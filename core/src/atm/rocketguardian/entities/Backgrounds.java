package atm.rocketguardian.entities;

import java.util.Iterator;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

import atm.rocketguardian.helpers.AssetLoader;
import atm.rocketguardian.helpers.Constants;

public class Backgrounds extends Actor {
	private AtlasRegion moonLayer, cityLayer, lightsLayer;
	private Array<Sprite> cityArray, lightsArray;
	private Iterator<Sprite> cityIterator;
	private float cityVelocity, lightsVelocity, lightsLayerWidth, lightsLayerHeight;
	private int moonScaleFactor;

	public Backgrounds(int lightsHeight) {
		moonLayer = AssetLoader.moonLayer;
		cityLayer = AssetLoader.cityLayer;
		lightsLayer = AssetLoader.lightsLayer;
		lightsLayerWidth = lightsLayer.getRegionWidth() * 3.5f;
		lightsLayerHeight = lightsLayer.getRegionHeight() * 3.5f;
		moonScaleFactor = 10;

		// Scene2D actor properties
		setSize(Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
		setPosition(0, 0);
		// velocity of the scrolling
		cityVelocity = 2;
		lightsVelocity = 3f;

		// City Layer array
		cityArray = new Array<Sprite>();
		Sprite citySprite = new Sprite(cityLayer);
		citySprite.setSize(getWidth(), getHeight());
		citySprite.setX(0);
		cityArray.add(citySprite);

		citySprite = new Sprite(cityLayer);
		citySprite.setSize(getWidth(), getHeight());
		citySprite.setX(Constants.GAME_WIDTH);
		cityArray.add(citySprite);

		// Lights layer array
		lightsArray = new Array<Sprite>();
		Sprite lightSprite = new Sprite(lightsLayer);
		lightSprite.setSize(lightsLayerWidth, lightsLayerHeight);
		// 32px is floor's height
		lightSprite.setPosition(213, lightsHeight);
		lightsArray.add(lightSprite);

		lightSprite = new Sprite(lightsLayer);
		lightSprite.setSize(lightsLayerWidth, lightsLayerHeight);
		lightSprite.setPosition(640, lightsHeight);
		lightsArray.add(lightSprite);
	}

	@Override
	public void act(float delta) {
		// Iteration of the city layer
		cityIterator = cityArray.iterator();
		while (cityIterator.hasNext()) {
			Sprite sprite = cityIterator.next();
			sprite.setX(sprite.getX() - cityVelocity);
			if (sprite.getX() < -sprite.getWidth()) {
				cityIterator.remove();
				generateNewLayerSprite(cityLayer, cityArray);
			}
		}

		for (Sprite sprite : lightsArray) {
			sprite.setX(sprite.getX() - lightsVelocity);
			if (sprite.getX() < -sprite.getWidth()) {
				sprite.setX(Constants.GAME_WIDTH);
			}
		}
	}

	// Method used to generate a new sprite capable of handling background
	// repeating
	private void generateNewLayerSprite(AtlasRegion layer, Array<Sprite> array) {
		Sprite sprite = new Sprite(layer);
		sprite.setSize(getWidth(), getHeight());
		sprite.setX(Constants.GAME_WIDTH);
		array.add(sprite);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		// To center the moon, we set it coordinates like this:
		batch.draw(moonLayer, (Constants.GAME_WIDTH / 2) - (moonLayer.getRegionWidth() * moonScaleFactor / 2),
				(Constants.GAME_HEIGHT / 2) - (moonLayer.getRegionHeight() * moonScaleFactor / 2),
				moonLayer.getRegionWidth() * moonScaleFactor, moonLayer.getRegionHeight() * moonScaleFactor);
		for (Sprite sprite : cityArray) {
			sprite.draw(batch);
		}
		for (Sprite sprite : lightsArray) {
			sprite.draw(batch);
		}
	}
	
	public void setLightsVelocity(Integer velocity) {
		lightsVelocity = velocity;
	}
	
	public void setCityVelocity(Integer velocity) {
		cityVelocity = velocity;
	}
}
