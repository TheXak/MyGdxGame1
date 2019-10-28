package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Hero {
    private Texture texture;
    private Vector2 position;
    private float speed;
    private int fireCounter;
    private int fireRate;

    public Vector2 getPosition() {
        return position;
    }

    private int moveUp, moveDown, moveRight, moveLeft, isFire;

    public Hero(int moveLeft, int moveRight, int moveUp, int moveDown, int isFire) {
        texture = new Texture("Ship80x60.png");
        speed = 10.0f;//скорость
        position = new Vector2(100, 100);
        fireRate = 2;//частота выстрела пули
        fireCounter = 0;//Задержка. Как только счетчик дохдит до fireRate, идет выстрел.

        this.moveLeft = moveLeft;
        this.moveRight = moveRight;
        this.moveUp = moveUp;
        this.moveDown = moveDown;
        this.isFire = isFire;
    }



    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);//рисуем корабль по координатам
    }

    public void update() {
        if (Gdx.input.isKeyPressed(moveUp)) {//уход за границу экрана с учетом модельки корабля
            position.y += speed;
            if (position.y > 720) {//ограничение сверху
                position.y = -60;//телепортируем корабль с другой стороны
            }
        }
        if (Gdx.input.isKeyPressed(moveDown)) {
            position.y -= speed;
            if (position.y < -60) {//ограничение снизу
                position.y = 720;
            }
        }
        if (Gdx.input.isKeyPressed(moveLeft)) {
            position.x -= speed;
            if (position.x < 0) {//ограничение слева
                position.x = 0;
            }
        }
        if (Gdx.input.isKeyPressed(moveRight)) {//ограничение справа
            position.x += speed;
            if (position.x > 1200) {
                position.x = 1200;
            }
        }
        if(Gdx.input.isKeyPressed(isFire)) {//счетчик задержки выстрела
            fireCounter++;
            if(fireCounter > fireRate) {//если счетчик больше частоты
                fireCounter = 0;//сбрасываем счетчик
                fire();//стреляем
            }
        }
    }

    public void fire() {//огонь из орудий
        for (int i = 0; i < MyGdxGame.bullets.length; i++) {//перебираем массив пуль
            if(!MyGdxGame.bullets[i].isActive()) {//если какая-то из пуль является не активной
                MyGdxGame.bullets[i].setup(position.x+35, position.y+5);//то нарисовать пулю по кординатам корабля со смещением, чтобы стрельба шла из орудия, а не воздуха
                break;//остановить перебор, чтобы была 1 пуля за 1 выстрел
            }
        }
    }
}
