package at.technikum.server;

import at.technikum.models.Battle;
import at.technikum.models.User;
import at.technikum.server.controller.BattleController;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.*;

public class BattleManager {
    private static final Map<Integer, Long> waitingClients = new ConcurrentHashMap<>();
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    static {
        scheduler.scheduleAtFixedRate(BattleManager::checkTimeouts, 0, 10, TimeUnit.SECONDS);
    }

    public static synchronized boolean enterBattleLobby(User user) {
        if (waitingClients.containsKey(user.getId())) {
            // Client is already in the queue
            return false;
        }

        waitingClients.put(user.getId(), System.currentTimeMillis());
        checkForBattle();

        return true;
    }

    private static synchronized void checkForBattle() {
        if (waitingClients.size() >= 2) {
            // Start a battle with the first two clients in the queue
            startBattle();
        }
    }

    private static synchronized void startBattle() {
        // Create a new battle queue
        BlockingQueue<Integer> battleQueue = new LinkedBlockingQueue<>();

        // Add the first two clients to the battle queue
        Iterator<Integer> iterator = waitingClients.keySet().iterator();
        Integer client1Id = iterator.next();
        Integer client2Id = iterator.next();
        battleQueue.add(client1Id);
        battleQueue.add(client2Id);

        // Remove clients from the waiting list
        waitingClients.keySet().removeAll(battleQueue);

        // Start the battle (implement your battle logic here)
        BattleController battleController = new BattleController();
        battleController.store(client1Id, client2Id);

        // Clear the waiting clients
        waitingClients.clear();
    }

    private static synchronized void checkTimeouts() {
        long currentTime = System.currentTimeMillis();
        waitingClients.entrySet().removeIf(entry -> (currentTime - entry.getValue()) > 60000);
    }

    public static synchronized boolean isClientInQueue(User user) {
        return waitingClients.containsKey(user.getId());
    }

}
