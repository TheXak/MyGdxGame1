package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Meteoroid {
    private static Texture texture;
    private Vector2 position;
    private float speed;
    private Rectangle hitBox;
    private float angle;
    private int hp;

    public Rectangle getHitBox() {
        return hitBox;
    }

    public Meteoroid() {
        if (texture == null) {
            texture = new Texture("Meteoroid60x60.png");
        }
        position = new Vector2((float) Math.random() * 1280 + 1280, (float) Math.random() * 720);
        speed = 3.0f + (float) Math.random() * 6.0f;
        hitBox = new Rectangle(position.x, position.y, 60, 60);//хитбокс метеороида
        angle = (float) Math.random() * 360;
        hp = 5;
    }

    public void Meteoroid_Recreation() {//пересоздание метеороидов
        speed = 6.0f + (float) Math.random() * 6.0f;//скорость метеороида
        angle = (float) Math.random() * 360;//угол вращения
        hp = 5 + MyGdxGame.level;//здоровье
        position.x = (float) Math.random() * 1280 + 1280;//расположение справа за границей экрана
        position.y = (float) Math.random() * 720;//расположение справа за границей экрана
    }

    public void render(SpriteBatch batch) {
        float m = hp / 5.0f;
        batch.draw(texture, position.x, position.y, 30, 30, 60, 60, m, m, angle, 0, 0, 60, 60, false, false);//перемещение метеороидов
    }

    public void receivedDamage(int dmg) {
        hp -= dmg;
        if (hp <= 0) {
            Meteoroid_Recreation();
        }
    }

    public void update(Hero h) {//силовое поле для кораблей
        Vector2 v = h.getPosition().cpy().sub(position).nor().scl(-10.0f);
        if(h.getPosition().cpy().sub(position).len() < 200) {//область срабатывания поля
            position.add(v);
        }
        position.x -= speed;
        angle += speed / 2;
        if (position.x < -60) {
            Meteoroid_Recreation();
        }
        hitBox.x = position.x;
        hitBox.y = position.y;
    }
}
