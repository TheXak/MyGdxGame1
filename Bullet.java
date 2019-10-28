package com.mygdx.game;
import com.badlogic.gdx.math.Vector2;

public class Bullet {
    private Vector2 position;//координаты
    private float speed;//скорость
    private boolean active;//активна пуля или нет

    public Vector2 getPosition() {
        return position;
    }

    public Bullet() {
        position = new Vector2(0, 0);//по-умолчанию лежат по координатам 0-0
        speed = 13.3f;//скорость пули
        active = false;//по-умолчанию пули не активны
    }

    public boolean isActive() {//проверка на активность пули
        return active;
    }

        public void annihilation() {//отключение активности пули
        active = false;
    }

    public void setup(float x, float y) {//активация пули из точки x,y
        position.x = x;
        position.y = y;
        active = true;//пуля полетела
    }

    public void updating() {
        position.x += speed;//на каждом кадре пуля летит вправо
        if (position.x > 1280) {//если пуля улетела справа за экран
            annihilation();//пуля больше не активна и отключена
        }
    }
}
