package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class ludek extends Rectangle {
	
	private Sound JumpSound;
	private Texture texture;
	public boolean CanJump = true;
	public float JumpVelocity;
	
	public ludek(Texture texture){
		this.texture = texture;
		this.height = texture.getHeight();
		this.width = texture.getWidth();
		JumpSound = Gdx.audio.newSound(Gdx.files.internal("jump.mp3"));
	}
	
	public void draw(SpriteBatch batch){
		batch.draw(texture,x,y);
	}

	public void jump(){
		if (CanJump = true && JumpVelocity >= -100){
			JumpVelocity += 600;
			CanJump = false;
			JumpSound.play();
		}
	}
	
}
