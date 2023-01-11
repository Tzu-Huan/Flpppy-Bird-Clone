package com.mygdx.flappybird3;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.utils.MeshBuilder;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class flappybird3 extends ApplicationAdapter {
	SpriteBatch batch;
	BitmapFont font;
	Texture bg;
	ShapeRenderer shapeRenderer;
	Texture pass;
	Texture gameover;
	Texture birdup;
	float birdHeight = 0;
	float birddrop = 0;
	Texture birddown;
	Circle bcircle = new Circle();

	int score = 0;


	int pipedistance = 280;
	int passnumber = 4;  //change the difficulty over here//////////////////////////////////////////////////////////////////////////////////////////
	float[] randomlist;
	Texture[] pipeuplist;
	Texture[] pipedownlist;
	Rectangle[] pipeupRectangles;
	Rectangle[] pipedownRectangles;
	int move = 0;
	int repeatcounter = 0;
	Texture pipeup;
	Random random;
	float randomgap;
	int middle = 140;  // up and down gap
	Texture pipedown;
	int wings = 0;

	int gameoveryet = 0;

	@Override
	public void create () {
		batch = new SpriteBatch();
		//bg = new Texture("background-night.png");
		bg = new Texture("bgblack.png");
		birdup = new Texture("yellowbird-upflap4X.png");
		birddown = new Texture("yellowbird-downflap4X.png");
		bcircle = new Circle();
		pipeup = new Texture("pipe-green8X.png");
		pass = new Texture(("passsmallpng.png"));
		gameover = new Texture("gameover1.png");
		random = new Random();
		//randomgap = random.nextFloat()*60;
		pipedown = new Texture("pipe-green28X.png");
		pipeuplist= new Texture[passnumber];
		pipedownlist= new Texture[passnumber];
		randomlist = new float[passnumber];
		for(int i = 0; i<passnumber; i++){
			randomlist[i] = random.nextFloat()*200;
		}
		for(int i = 0; i<passnumber; i++){
			pipeuplist[i] = pipeup;
		}
		pipedownlist= new Texture[passnumber];
		for(int i = 0; i<passnumber; i++){
			pipedownlist[i] = pipedown;
		}
		pipeupRectangles = new Rectangle[passnumber];
		pipedownRectangles = new Rectangle[passnumber];
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(4);


		gameoveryet = 0;
	}

	@Override
	public void render () {
		//ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();




		//randomgap = random.nextFloat()*60;
		batch.draw(bg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());  // to full screen
		//Circle bCircle = new Circle();

		if(wings == 0){
			batch.draw(birdup, Gdx.graphics.getWidth()/2 - 60, Gdx.graphics.getHeight()/2 - 60 +birdHeight, birdup.getWidth()/2, birdup.getHeight()/2);

			wings = 1;


		}else{
			batch.draw(birddown, Gdx.graphics.getWidth()/2 - 60, Gdx.graphics.getHeight()/2 - 60 +birdHeight, birddown.getWidth()/2, birddown.getHeight()/2);

			wings = 0;

		}

		if(Gdx.input.justTouched()){
			birdHeight +=40;

		}
		birddrop = 4;
		birdHeight = birdHeight - birddrop;
		/*
		batch.draw(pipeup, Gdx.graphics.getWidth()/2, 0, Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()/2 - middle + random.nextFloat()*60);
		batch.draw(pipedown, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2 + middle + random.nextFloat()*60, Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()/2);
		*/
		if(gameoveryet ==0){
			for (int i = 1; i<passnumber; i++){  // start from i=1 due to i=0 the bird will appear between the pipes once the game start, it will be too hard to play
				batch.draw(pipeuplist[i], Gdx.graphics.getWidth()/2 +i*pipedistance + move, 0, Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()/2 - middle + randomlist[i]);
				batch.draw(pipedownlist[i], Gdx.graphics.getWidth()/2 + i * pipedistance + move, Gdx.graphics.getHeight()/2 - middle + randomlist[i]+ middle + randomlist[i], Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()/2 );
				pipeupRectangles[i] = new Rectangle(Gdx.graphics.getWidth()/2 +i*pipedistance + move, 0, Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()/2 - middle + randomlist[i]);
				pipedownRectangles[i] = new Rectangle(Gdx.graphics.getWidth()/2 + i * pipedistance + move, Gdx.graphics.getHeight()/2 - middle + randomlist[i]+ middle + randomlist[i], Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()/2);
				if(Gdx.graphics.getWidth()/2 +i*pipedistance + move == 0){
					Gdx.app.log("Socre", String.valueOf((repeatcounter)));
					repeatcounter ++;
				}
				if(repeatcounter >= passnumber -1){

					gameoveryet = 2;

				}



				//if(Intersector.overlaps(bcircle, pipeupRectangles[i]) || Intersector.overlaps(bcircle, pipedownRectangles[i])){
				//	Gdx.app.log("Collision", "yes");

				//}
			}

			move -= 4;

		}else if(gameoveryet == 1){

			batch.draw(gameover, Gdx.graphics.getWidth() /2 - gameover.getWidth()/2,  Gdx.graphics.getHeight() /2 - gameover.getHeight()/2);

		}else if(gameoveryet == 2){
			Gdx.app.log("pass", "pass");
			batch.draw(pass, Gdx.graphics.getWidth() /2 - pass.getWidth()/2,  Gdx.graphics.getHeight() /2 - pass.getHeight()/2);

		}

		//batch.draw(pass, Gdx.graphics.getWidth() /2 - gameover.getWidth()/2,  Gdx.graphics.getHeight() /2 - gameover.getHeight()/2);


		//shapeRenderer= new ShapeRenderer();
		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);  //all shape be filled in
		//shapeRenderer.setColor(Color.RED);
		bcircle.set( (Gdx.graphics.getWidth()/2 -20), (Gdx.graphics.getHeight()/2  +birdHeight-40), Gdx.graphics.getWidth()/16);

		//shapeRenderer.circle(bcircle.x, bcircle.y, bcircle.radius);
		//batch.draw(gameover, Gdx.graphics.getWidth() /2 - gameover.getWidth()/2,  Gdx.graphics.getHeight() /2 - gameover.getHeight()/2);

		for (int i = 1; i<passnumber; i++){
			//if(i == passnumber-1){
			//	Gdx.app.log("pass", "passsss");

			//}
			if(Intersector.overlaps(bcircle, pipeupRectangles[i]) || Intersector.overlaps(bcircle, pipedownRectangles[i])){
					Gdx.app.log("Collision", "yes");
					gameoveryet = 1;

				}
			//shapeRenderer.rect(Gdx.graphics.getWidth()/2 +i*pipedistance + move, 0, Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()/2 - middle + randomlist[i]);
			//shapeRenderer.rect(Gdx.graphics.getWidth()/2 + i * pipedistance + move, Gdx.graphics.getHeight()/2 - middle + randomlist[i]+ middle + randomlist[i], Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()/2);

		}

		//shapeRenderer.end();
		//ShapeRenderer shapeRenderer = new ShapeRenderer();
		 // for us can see it
		font.draw(batch, String.valueOf(repeatcounter), 80, 80);
		batch.end();




		}

	

}
