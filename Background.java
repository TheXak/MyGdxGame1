package com.mygdx.game;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Background {
    private Texture texture;
    private Texture starTexture;
    private Stars[] stars;
    private final int stars_Count = 222;//кол-во звезд

    class Stars {
        private Vector2 position;
        private float speed;

        public Stars() {
            position = new Vector2((float) Math.random() * 1280, (float) Math.random() * 720);//позиционирование звезд через рандом
            speed = 0.8f + (float) Math.random() * 11.2f;//скорость полета звезд
        }

        public void Refresh() {//пересоздание звезд в случайной точке справа от экрана, когда они уходят за границу экрана
            position.x -= speed;
            if (position.x < -20) {//если ушло за экран слева
                position.x = 1280;//рисуем справа
                position.y = (float) Math.random() * 720;
                speed = 3.0f + (float) Math.random() * 8.0f;//задавая случайную скорость
            }
        }
    }

    public Background() {
        texture = new Texture("Background.png");//закинули в память картинку заднего фона
        starTexture = new Texture("Star12x12.png");//закинули в пямять картунку звезды
        stars = new Stars[stars_Count];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Stars();
        }
    }

    public void drawing(SpriteBatch batch) {//отрисовка звезд по координатам
        batch.draw(texture, 0, 0);
        for (int i = 0; i < stars.length; i++) {
            batch.draw(starTexture, stars[i].position.x, stars[i].position.y);
        }
    }

    public void updating() {//обновление звезд
        for (int i = 0; i < stars.length; i++) {
            stars[i].Refresh();
        }
    }
}
