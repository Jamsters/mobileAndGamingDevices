package uk.ac.tees.w9039358.mobileAndGamingDevices;

import android.os.SystemClock;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class GameSpawner {
    ArrayList<Entity> Spawnables = new ArrayList<>();

    private int WeightTotal = 0;
    private int RandomWeight = 0;
    private Entity EntityToSpawnReference;

    private long LastSpawnTime = SystemClock.elapsedRealtime();

    // Attempts to spawn every second
    private long AttemptToSpawnEntityTime = 2000;

    private static final int DynamicSpawningInitializationAmount = 20;

    private GameController GameControllerReference;
    private ArrayList<Entity> AllEntitiesReference;
    GameSpawner(GameController gameControllerReference)
    {
        GameControllerReference = gameControllerReference;
        AllEntitiesReference = GameControllerReference.Entities;
    }

    public static int GetDynamicSpawningInitializationAmount() {
        return DynamicSpawningInitializationAmount;
    }


    private void WhichEntityToSpawn()
    {
        if (Spawnables.isEmpty())
        {
            return;
        }

        // 1. Get all spawn weights from spawnables and total them
        WeightTotal = 0;



        for (Entity entity : Spawnables)
        {
            WeightTotal += entity.GetSpawnWeight();

        }

        // 2. Get random weight number based on total weights

        Random RandomNumber = new Random();

        RandomWeight = RandomNumber.nextInt(WeightTotal);

        // 3. Go through the spawnables again and remove their weight from the random weight until we get 0. That is the entity we're returning.

        // This assumes that the order of the for is the same as when we got weight totals
        for (Entity entity : Spawnables)
        {
            if (RandomWeight <= 0)
            {
                EntityToSpawnReference = entity;
                return;
            }
            else
            {
            RandomWeight -= entity.GetSpawnWeight();
            }
        }
    }

    private boolean IsEntityToSpawnValid()
    {
        // Spawnables is already setup to contain already spawned entities that are out of play, don't need to check that for validity
        if (EntityToSpawnReference == null)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    protected void SpawnEntityCall()
    {
        long TimeSinceLastSpawn = SystemClock.elapsedRealtime() - LastSpawnTime;
        if (TimeSinceLastSpawn >= AttemptToSpawnEntityTime)
        {
            SpawnEntityImplementation();
        }

    }

    private void SpawnEntityImplementation()
    {
        UpdateSpawnables();
        WhichEntityToSpawn();
        if (IsEntityToSpawnValid())
        {
            EntityToSpawnReference.SetSpawning(true);

            // Won't guarantee true centre of screen because we're dividing a float, it shouldn't matter much though

            // TODO : Instead of using the centre screen width, use random + screen width to determine a place where the entity can spawn with every part of its' sprite inbounds
            float CentreScreenWidth = GameControllerReference.Vis.GetScreenSize().GetX()/2 - EntityToSpawnReference.Position.GetCentreSize().GetX();
            float BelowScreenHeight = GameControllerReference.Vis.GetScreenSize().GetY();

            EntityToSpawnReference.Position.SetXPos(CentreScreenWidth);
            EntityToSpawnReference.Position.SetYPos(BelowScreenHeight);

            EntityToSpawnReference.AliveToggle(true);

            // Remove from list of spawnables
            Spawnables.remove(EntityToSpawnReference);

            // Set our entity to spawn reference to null
            EntityToSpawnReference = null;

            LastSpawnTime = SystemClock.elapsedRealtime();


        }
    }

    private void UpdateSpawnables()
    {
        // TODO : This could be optimzied, making a new array everytime isn't needed
        Spawnables = new ArrayList<>();
        for (Entity entity : AllEntitiesReference)
        {
            if (!entity.GetIsAlive() && !entity.GetIsSpawning())
            {
                // If dead and already spawned means that it is unused
                Spawnables.add(entity);
            }
        }
    }
}