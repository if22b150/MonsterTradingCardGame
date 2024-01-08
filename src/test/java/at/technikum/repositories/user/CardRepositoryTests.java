package at.technikum.repositories.user;

import at.technikum.enums.ECardType;
import at.technikum.enums.EElementType;
import at.technikum.models.Package;
import at.technikum.models.Transaction;
import at.technikum.models.User;
import at.technikum.models.card.ACard;
import at.technikum.repositories.card.CardRepository;
import at.technikum.repositories.card.ICardRepository;
import at.technikum.repositories.packages.IPackageRepository;
import at.technikum.repositories.packages.PackageRepository;
import at.technikum.repositories.transaction.ITransactionRepository;
import at.technikum.repositories.transaction.TransactionRepository;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CardRepositoryTests {
    private static final ICardRepository cardRepository = new CardRepository();
    private static final IPackageRepository packageRepository = new PackageRepository();
    private static final ITransactionRepository transactionRepository = new TransactionRepository();
    private static final IUserRepository userRepository = new UserRepository();

    ArrayList<ACard> testCards = new ArrayList<>();
    User testUser;
    Package testPackage;
    Transaction testTransaction;

    @BeforeAll
    void setup() {
        testPackage = packageRepository.create();
        if(testPackage == null)
            fail("Package creation failed");
        testUser = userRepository.create("testuser", "testpassword");
        if(testUser == null)
            fail("User creation failed");

        testCards.add(cardRepository.create("1", "TESTCARD1", 100, ECardType.MONSTER, EElementType.FIRE, testPackage.getId()));
        testCards.add(cardRepository.create("2", "TESTCARD2", 200, ECardType.MONSTER, EElementType.FIRE, testPackage.getId()));
        testCards.add(cardRepository.create("3", "TESTCARD3", 300, ECardType.MONSTER, EElementType.FIRE, testPackage.getId()));
        testCards.add(cardRepository.create("4", "TESTCARD4", 400, ECardType.MONSTER, EElementType.FIRE, testPackage.getId()));
        testCards.add(cardRepository.create("5", "TESTCARD5", 500, ECardType.MONSTER, EElementType.FIRE, testPackage.getId()));

        testTransaction = transactionRepository.create(testUser.getId(), testPackage.getId());
        if(testTransaction == null)
            fail("Transaction creation failed");
    }

    @Test
    void testCreate() {
        int index = 1;
        for(ACard card : testCards) {
            assertNotNull(card.getId());
            assertEquals("" + index, card.getPublicId());
            assertEquals("TESTCARD" + index, card.getName());
            assertEquals(100 * index, card.getDamage());
            assertEquals(ECardType.MONSTER, card.getType());
            assertEquals(EElementType.FIRE, card.getElementType());
            assertEquals(testPackage.getId(), card.getPackageId());
            index++;
        }
    }

    @Test
    void testGetByPublicId() {
        ACard c = cardRepository.getByPublicId("1");
        assertNotNull(c);
        assertEquals(testCards.get(0).getId(), c.getId());
        assertEquals(testCards.get(0).getPublicId(), c.getPublicId());
        assertEquals(testCards.get(0).getName(), c.getName());
        assertEquals(testCards.get(0).getDamage(), c.getDamage());
        assertEquals(testCards.get(0).getType(), c.getType());
        assertEquals(testCards.get(0).getElementType(), c.getElementType());
    }

    @Test
    void testGetAllByUser() {
        ArrayList<ACard> cards = cardRepository.getAllByUser(testUser.getId());
        cards.sort(Comparator.comparing(ACard::getId));
        assertEquals(cards.size(), testCards.size());

        int index = 0;
        for(ACard c : cards) {
            assertEquals(c.getId(), testCards.get(index).getId());
            index++;
        }
    }

    @Test
    void testSetAndGetUserDeck() {
        int index = 0;
        // cards 2 to 5 are set to deck
        for (ACard card : testCards) {
            index++;
            if(index == 1)
                continue;
            cardRepository.setDeck(card.getId(), true);
        }

        // check if only cards 2 to 5 are returned
        ArrayList<ACard> deck = cardRepository.getUserDeck(testUser.getId());
        int i = 1;
        for(ACard deckCard : deck) {
            assertTrue(deckCard.isDeck());
            assertEquals(deckCard.getId(), testCards.get(i).getId());
            assertEquals(deckCard.getPublicId(), testCards.get(i).getPublicId());
            assertEquals(deckCard.getName(), testCards.get(i).getName());
            assertEquals(deckCard.getDamage(), testCards.get(i).getDamage());
            assertEquals(deckCard.getType(), testCards.get(i).getType());
            assertEquals(deckCard.getElementType(), testCards.get(i).getElementType());
            assertEquals(deckCard.getPackageId(), testCards.get(i).getPackageId());
            i++;
        }
        assertFalse(testCards.get(0).isDeck());
    }

    @Test
    void testSetUserId() {
        ACard card = cardRepository.create("100", "TESTCARD100", 100, ECardType.MONSTER, EElementType.FIRE, testPackage.getId());

        assertNull(card.getUserId());

        cardRepository.setUserId(card.getId(), testUser.getId());
        card = cardRepository.get(card.getId());

        assertEquals(card.getUserId(), testUser.getId());

        cardRepository.delete(card.getId());
    }

    @AfterAll()
    void deleteUser() {
        for(ACard card : testCards) {
            cardRepository.delete(card.getId());
        }
        transactionRepository.delete(testTransaction.getId());
        packageRepository.delete(testPackage.getId());
        userRepository.delete(testUser.getId());
    }
}
