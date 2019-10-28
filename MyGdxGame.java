package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Background background;
	Hero Gamer_1;
	Hero Gamer_2;
	final int meteoroids_Count = 30;//макс кол-во метеороидов
	Meteoroid[] meteoroids;
	final int Bullets_Count = 222;//макс кол-во пуль до перезарядки
	public static Bullet[] bullets;//массив пуль
	Texture textureBullet;
	//	float timer;
	//
	public static int level = 1;

	@Override
	public void create() {
//		timer = 0.0f;
		batch = new SpriteBatch();
		background = new Background();
		Gamer_1 = new Hero(Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.UP, Input.Keys.DOWN, Input.Keys.NUMPAD_0);//Инициируем управление первого игрока
		Gamer_2 = new Hero(Input.Keys.A, Input.Keys.D, Input.Keys.W, Input.Keys.S, Input.Keys.SPACE);//Инициируем управление второго игрока
		meteoroids = new Meteoroid[meteoroids_Count];
		bullets = new Bullet[Bullets_Count];
		textureBullet = new Texture("Bullet20x20.png");//присваиваем текстуру пули
		for (int i = 0; i < meteoroids.length; i++) {//создаем метеороиды
			meteoroids[i] = new Meteoroid();
		}
		for (int i = 0; i < bullets.length; i++) {//инициализируем пули
			bullets[i] = new Bullet();
		}
	}

	@Override
	public void render() {
		update();
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f); // RGBA
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();//начать отрисовку
		background.drawing(batch);//рисуем задний фон
		Gamer_1.render(batch);//1 игрок
		Gamer_2.render(batch);//2 игрок
		for (int i = 0; i < meteoroids.length; i++) {
			meteoroids[i].render(batch);
		}
		for (int i = 0; i < bullets.length; i++) {//перебираем все пули
			if (bullets[i].isActive()) {//если пуля активна
				batch.draw(textureBullet, bullets[i].getPosition().x, bullets[i].getPosition().y);//отрисовать пулю
			}
		}
		batch.end();
	}

	public void update() {
//		timer += Gdx.graphics.getDeltaTime();
//		if (timer > 20.0f) {
//			timer = 0.0f;
//			level++;
//		}
		background.updating();
		Gamer_1.update();
		Gamer_2.update();
		for (int i = 0; i < meteoroids.length; i++) {//силовое поле для корабля 1
			meteoroids[i].update(Gamer_1);
		}
		for (int i = 0; i < meteoroids.length; i++) {//силовое поле для корабля 2
			meteoroids[i].update(Gamer_2);
		}

		for (int i = 0; i < bullets.length; i++) {
			if (bullets[i].isActive()) {//если пуля активна
				bullets[i].updating();//апдейт пули
				for (int j = 0; j < meteoroids.length; j++) {
					if (meteoroids[j].getHitBox().contains(bullets[i].getPosition())) {//если хитбокс метеороида имеет в себе пулю
						meteoroids[j].receivedDamage(1);//метеороид получает 1 урона,
						bullets[i].annihilation();//а пуля уничтожается
						break;
					}
				}
			}
		}
	}
}
