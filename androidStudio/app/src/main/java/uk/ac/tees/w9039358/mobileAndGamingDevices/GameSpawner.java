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

        // 1. Get weight from unique classes (polymorphism doesn't count) that are able to be spawned (set ensures uniqueness, can't add the same class to it).
        // We add entity to weight data instead of a class so we don't have to later go through spawnables again to find an entity of that class we want to spawn.

        ArrayList<Pair<Integer,Entity>> WeightData = new ArrayList<>();
        Set<Class> UniqueClasses = new HashSet<>();
        for(Entity entity : Spawnables)
        {
            if (!UniqueClasses.contains(entity.getClass()))
            {
                WeightData.add(new Pair<>(entity.GetSpawnWeight(),entity));
                UniqueClasses.add(entity.getClass());
            }

        }

        Log.d("GameSpawner.WhichEntity", "Size of weight data: " + WeightData.size());

        // 2. Get all spawn weights from WeightData and total them.


        TotalWeight = 0;

        for (Pair<Integer,Entity> entity : WeightData)
        {
            TotalWeight += entity.first.intValue();

        }

        // 3. Now we need to normalize (0.0f to 1.0f) the weights individually as a percentage of the total weight got earlier.

        ArrayList<Pair<Float,Entity>> PercentageData = new ArrayList<>();
        for (Pair<Integer,Entity> floatEntityPair : WeightData)
        {
            float Percentage = floatEntityPair.first.floatValue()/TotalWeight;
            PercentageData.add(new Pair<Float,Entity>(Percentage, floatEntityPair.second));
        }


        // 4. With the weights normalized as a percentage we can get a random float (0.0 to 1.0f) and take away the entities normalized weights from that until
        // we get equal to or less than 0.
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

        /* If float rounding problems or something else unexpected happens the entity reference will already be null
        from the end of SpawnEntityImplementation, and there's checks to stop a null entity from trying to spawn.
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
