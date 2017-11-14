package com.mygdx.game;

import java.util.ArrayList;

import sun.org.mozilla.javascript.internal.ast.Jump;
import sun.util.logging.PlatformLogger;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	
	private Music music;
	private Texture PlayerTexture, PlatformTexture, tlo;
	private ludek player;
	private Array<platformy> PlatformArray;
	private OrthographicCamera camera;
	private float gravity = -20;
	private float tlo_align = -100;
	
	@Override
	public void create () {
		LoadData();
		Init();
	}
	
	private void Init() {
		batch = new SpriteBatch();
		music.play();
		camera = new OrthographicCamera(480,800);
		player = new ludek(PlayerTexture);
		PlatformArray = new Array<platformy>();
		
		for (int i = 1; i<100; i++){
			platformy p = new platformy(PlatformTexture);
			p.x = new MathUtils().random(480) - p.width * 2;
			p.y = 200 * i;
			PlatformArray.add(p);
		}
		
	}

	
	private void LoadData(){
		PlayerTexture = new Texture("player.png");
		PlatformTexture = new Texture("platform.png");
		music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		tlo = new Texture("tlo.jpg");
	}

	
	@Override
	public void render () {
		update();
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		batch.draw(tlo,-200, player.y - 100);
		batch.setProjectionMatrix(camera.combined);
		for (platformy p : PlatformArray){
			p.draw(batch);
		}
		player.draw(batch);		
		batch.end();
	}

	private void update() {
		HandleInput();
		camera.update();
		camera.position.set(0 + player.width/2, player.y + 300, 0);
		
		
		player.y += player.JumpVelocity * Gdx.graphics.getDeltaTime();
		
		if(player.y > 0){
			player.JumpVelocity += gravity;
		} else{
			player.y = 0;
			player.CanJump = true;
			player.JumpVelocity = 0;
		}
		
		
		
		for(platformy p : PlatformArray){
			if (IsPlayerOnPlatform(p)){
				player.CanJump = true;
				player.JumpVelocity = 0 ;
				player.y = p.y + p.height;
			}
		}
		
	}

	private boolean IsPlayerOnPlatform(platformy p) {
		return player.JumpVelocity <= 0 && player.overlaps(p) && !(player.y <= p.y); 
		
	}

	private void HandleInput() {
		if(Gdx.input.isKeyPressed(Keys.A)){
			player.x -= 500 * Gdx.graphics.getDeltaTime();
		}
		
		if(Gdx.input.isKeyPressed(Keys.D)){
			player.x += 500 * Gdx.graphics.getDeltaTime();
		}
		
		if(Gdx.input.justTouched()){
			player.jump();
		}
		
	}
}
