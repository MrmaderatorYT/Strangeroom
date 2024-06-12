package com.ccs.strangeroom;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final double CHANCE_OF_ANIMAL_ATTACK = 0.1; // 10% вероятность атаки животного

    private TextView statusTextView;
    private TextView resourcesTextView;
    private TextView day;
    private Button exploreButton;
    private Button buildFireButton;
    private Button restButton;
    private Button eatButton;
    private Button craft;
    private int hatDurability;
    private int jacketDurability;
    private boolean hasHat;
    private boolean hasJacket;


    private int woodCount;
    private int countDay = 1;
    private int meatCount;
    private int furCount;
    private int health;
    private int hunger;

    private boolean hasFire;
    private AdView mAdView;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        day = findViewById(R.id.day);
        statusTextView = findViewById(R.id.statusTextView);
        resourcesTextView = findViewById(R.id.resourcesTextView);
        exploreButton = findViewById(R.id.exploreButton);
        buildFireButton = findViewById(R.id.buildFireButton);
        restButton = findViewById(R.id.restButton);
        eatButton = findViewById(R.id.eatButton);
        craft = findViewById(R.id.craft);

        woodCount = PreferenceConfig.getWood(this);
        hatDurability = PreferenceConfig.getCap(this);
        jacketDurability = PreferenceConfig.getJacket(this);
        countDay = PreferenceConfig.getDay(this);
        meatCount = PreferenceConfig.getMeat(this);
        furCount = PreferenceConfig.getFur(this);
        health = PreferenceConfig.getHealth(this);
        hunger = PreferenceConfig.getHealth(this);
        AdView adView = new AdView(this);

        adView.setAdSize(AdSize.BANNER);

        adView.setAdUnitId("ca-app-pub-1595862743951192/4552912234");
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
// TODO: Add adView to your view hierarchy.
        day.setText("День №" + countDay);
        exploreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (health <= 0) {
                    health = 0;
                    updateStatusText("Вы умерли!");
                } else {
                    explore();

                }
            }
        });
        craft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                craftClothing();
            }
        });

        buildFireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildFire();
            }
        });

        restButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rest();
            }
        });

        eatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eatMeat();
            }
        });

        resetGame();
    }

    private void resetGame() {
        woodCount = 0;
        meatCount = 0;
        furCount = 0;
        health = 100;
        hunger = 10;
        hasFire = false;
        updateStatusText("Вы просыпаетесь в темном и холодном месте.\nВам нужно построить костер, чтобы выжить.");
        updateResourcesText();
        updateButtonsState(true, true, false);
    }

    private void explore() {

        Random random = new Random();
        if (random.nextDouble() < 0.5) {
            woodCount++;
            updateStatusText("Вы нашли немного дров.");
        } else if (random.nextDouble() < 0.3) {
            meatCount++;
            updateStatusText("Вы нашли мясо.");
        } else if (random.nextDouble() < 0.2) {
            furCount++;
            updateStatusText("Вы нашли шкуру.");
        } else {
            updateStatusText("Вы ничего не нашли.");
        }
        // Внезапное действие: атака животного
        if (random.nextDouble() < CHANCE_OF_ANIMAL_ATTACK) {
            AnimalType animal = getRandomAnimal();
            handleAnimalAttack(animal);
        }
        if (hunger > 0) {
            hunger--;
        } else {
            if (woodCount > 0) {
                updateStatusText("Вы голодны и нуждаетесь в еде. Постройте костер и отдохните.");
                updateButtonsState(false, true, true);
            } else {
                health--;
                updateStatusText("Вы голодны и нуждаетесь в еде. У вас нет дров для костра. Ваше здоровье уменьшилось.");
            }
        }
        updateResourcesText();
    }

    private void buildFire() {
        if (woodCount >= 2 && !hasFire) {
            hasFire = true;
            woodCount -= 2;
            updateResourcesText();
            updateStatusText("Вы построили костер. Он дает тепло и защиту.");
            updateButtonsState(false, false, true);
            exploreButton.setEnabled(true);
            restButton.setEnabled(true);
        } else if (!hasFire) {
            updateStatusText("У вас недостаточно дров, чтобы построить костер.");

        } else {
            updateStatusText("Костер уже горит.");
        }
    }

    private void rest() {
        if (hasFire) {
            health += 25;
            if (health > 100) {
                health = 100;
            }

            if (hunger + 4 >= 10) {
                hunger = 10;
            } else {
                hunger += 4;
            }
            countDay++;
            day.setText("День №" + countDay);
            hunger += 4;
            updateStatusText("Вы отдыхаете и восстанавливаете силы.");
            updateResourcesText();
            updateButtonsState(true, true, false);
            hasFire = false;
            if (meatCount > 0) {
                meatCount--;
            }
        } else {
            updateStatusText("Слишком холодно, чтобы отдыхать без костра.");
        }
    }

    private void eatMeat() {
        if (meatCount > 0) {
            meatCount--;
            hunger += 1;
            updateStatusText("Вы поели мясо и насытились.");
            updateResourcesText();
            updateButtonsState(true, true, true);
        } else {
            updateStatusText("У вас нет мяса для еды.");
        }
    }

    private void updateStatusText(String text) {
        statusTextView.setText(text);
    }

    private void updateResourcesText() {
        resourcesTextView.setText("Дрова: " + woodCount + " Мясо: " + meatCount + " Кожа: " + furCount + "\nШапка: " + (hasHat ? hatDurability + "%" : "нет") + " Куртка: " + (hasJacket ? jacketDurability + "%" : "нет") + "\nЗдоровье: " + health + " Голод: " + hunger);
        PreferenceConfig.setCap(getApplicationContext(), hatDurability);
        PreferenceConfig.setJacket(getApplicationContext(), jacketDurability);
        PreferenceConfig.setWood(getApplicationContext(), woodCount);
        PreferenceConfig.setDay(getApplicationContext(), countDay);
        PreferenceConfig.setFur(getApplicationContext(), furCount);
        PreferenceConfig.setMeat(getApplicationContext(), meatCount);
        PreferenceConfig.setHealth(getApplicationContext(), health);
        PreferenceConfig.setHungry(getApplicationContext(), hunger);

    }

    private void updateButtonsState(boolean exploreEnabled, boolean buildFireEnabled, boolean restEnabled) {
        exploreButton.setEnabled(exploreEnabled);
        buildFireButton.setEnabled(buildFireEnabled);
        restButton.setEnabled(restEnabled);
        eatButton.setEnabled(hunger < 10 && meatCount > 0);
    }

    private enum AnimalType {
        WOLF,
        BEAR
    }

    private AnimalType getRandomAnimal() {
        Random random = new Random();
        double randomValue = random.nextDouble();
        if (randomValue < 0.5) {
            return AnimalType.WOLF;
        } else {
            return AnimalType.BEAR;
        }
    }

    private void handleAnimalAttack(AnimalType animal) {
        switch (animal) {
            case WOLF:
                if (health - 20 < 0) {
                    health = 0;
                    updateStatusText("Вы умерли! Попробуйте ещё раз!");
                } else {
                    health -= 20;
                    updateStatusText("Вы были атакованы волком! Ваше здоровье уменьшилось.");
                }
                break;
            case BEAR:
                if (health - 40 < 0) {
                    health = 0;
                    updateStatusText("Вы умерли! Попробуйте ещё раз!");

                } else {
                    health -= 40;
                    updateStatusText("Вы были атакованы медведем! Ваше здоровье уменьшилось.");
                }
                break;
        }
    }

    private void craftClothing() {
        if (furCount >= 3) { // Проверяем, есть ли достаточно кожи для создания одежды
            furCount -= 3; // Снимаем кожу для создания одежды
            // Создаем одежду
            hasHat = true;
            hasJacket = true;
            hatDurability = 100; // Начальная прочность шапки
            jacketDurability = 100; // Начальная прочность куртки
            updateStatusText("Вы сделали шапку и куртку из кожи.");
            wearClothing();
        } else {
            updateStatusText("У вас недостаточно кожи для создания одежды.");
        }
    }

    private void wearClothing() {
        if (hasHat && hasJacket) {
            // Уменьшаем получаемый урон на 20%
            // Например, если урон равен 40, то после ношения одежды урон будет 32
            int damage = 40; // Пример урона
            int reducedDamage = (int) (damage * 0.8); // Уменьшаем урон на 20%
            // Обновляем здоровье с учетом уменьшенного урона
            health -= reducedDamage;
            // Уменьшаем прочность одежды
            hatDurability -= 10; // Примерное уменьшение прочности за одно использование
            jacketDurability -= 15; // Примерное уменьшение прочности за одно использование
            // Проверяем прочность шапки
            if (hatDurability <= 0) {
                hatDurability = 0;
                hasHat = false; // Шапка сломалась
            }
            // Проверяем прочность куртки
            if (jacketDurability <= 0) {
                jacketDurability = 0;
                hasJacket = false; // Куртка сломалась
            }
            // Обновляем UI, чтобы отобразить прочность одежды
            updateResourcesText();
        }

    }
}
