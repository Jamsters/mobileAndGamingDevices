package uk.ac.tees.w9039358.mobileAndGamingDevices;

import android.os.SystemClock;
import android.util.Log;
import android.util.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GameSpawner {
    ArrayList<Entity> Spawnables = new ArrayList<>();

    float TotalWeight=0;

    private float RandomWeight = 0;
    private Entity EntityToSpawnReference;
    private Class EntityClassToSpawn;

    private long LastSpawnTime = SystemClock.elapsedRealtime();

    private long RandomSpawnDelay = 0;

    private static final int DefaultSpawnDelay = Settings.GetNormalSpawnDelay();

    private static final int MaxAddedSpawnDelay = Settings.GetNormalMaxAddedSpawnDelay();

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

        //private LinkedList<Class <?>> collectionOfClasses

        // Only need a set of ints, not set of classes
        // Would use triplet but it's apart of kotlin
        Set<Pair<Integer,Entity>> WeightData = new HashSet<>();
        for(Entity entity : Spawnables)
        {
            Log.d("GameSpawner.WhichEntity", "Super Class name " + entity.getClass().getGenericSuperclass());
            //entity.getClass().asSubclass()
            if (entity.GetSpawnWeight() != 0)
            {
                WeightData.add(new Pair<>(entity.GetSpawnWeight(),entity));
            }

        }

        Log.d("GameSpawner.WhichEntity", "Size of weight data: " + WeightData.size());






        // 1. Get all spawn weights from spawnables and total them


        TotalWeight = 0;

        for (Pair<Integer,Entity> entity : WeightData)
        {
            TotalWeight += entity.first.intValue();

        }

        // 2. Get random weight number based on total weights



        // 3. Go through the spawnables again and remove their weight from the random weight until we get 0. That is the entity we're returning.

        // This assumes that the order of the for is the same as when we got weight totals
        // TODO : Now that we have a total weight of uniques, how can we use that to select what we want?

        ArrayList<Pair<Float,Entity>> PercentageData = new ArrayList<>();
        // Normalize it in to a percentage
        for (Pair<Integer,Entity> floatEntityPair : WeightData)
        {
            float Percentage = floatEntityPair.first.floatValue()/TotalWeight;
            PercentageData.add(new Pair<Float,Entity>(Percentage, floatEntityPair.second));
        }

        Random RandomNumber = new Random();

        RandomWeight = RandomNumber.nextFloat();

        Log.d("GameSpawner.WhichEntity", "Total Weight: " + Float.toString(TotalWeight));

        for (Pair<Float,Entity> floatEntityPair : PercentageData)
        {
            RandomWeight -= floatEntityPair.first.floatValue();
            if (RandomWeight <= 0)
            {
                EntityToSpawnReference = floatEntityPair.second;
                return;
            }
        }

        /* If float rounding problems or something unexpected happens the entity reference will already be null
        from the end of SpawnEntityImplementation, and there's checks to stop a null entity from trying to spawn
         */





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
        if (TimeSinceLastSpawn >= RandomSpawnDelay)
        {
            SpawnEntityImplementation();

            // Set new random spawn delay
            Random RandomNumber = new Random();

            int randomSpawnDelay = RandomNumber.nextInt(MaxAddedSpawnDelay) + DefaultSpawnDelay;

            RandomSpawnDelay = Integer.toUnsignedLong(randomSpawnDelay);

            Log.d("GameSpawner.RandomSpawnDelay", "Spawn delay :" + Long.toString(RandomSpawnDelay));

        }

    }

    private void SpawnEntityImplementation()
    {
        UpdateSpawnables();
        WhichEntityToSpawn();
        if (IsEntityToSpawnValid())
        {
            EntityToSpawnReference.SetSpawning(true);

            Random RandomNumber = new Random();

            float RightMostSpawnInHorizontalBounds = GameControllerReference.Vis.GetScreenSize().GetX() - EntityToSpawnReference.Position.GetWidthSize();
            float RandomWidth = RandomNumber.nextFloat() * RightMostSpawnInHorizontalBounds;
            float BelowScreenHeight = GameControllerReference.Vis.GetScreenSize().GetY();

            EntityToSpawnReference.Position.SetXPos((float)RandomWidth);
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
            if (!entity.GetIsAlive() && !entity.GetIsSpawning() && entity.IsSpawnConditionMet())
            {
                // If dead and already spawned means that it is unused
                Spawnables.add(entity);
            }
        }
    }
}
