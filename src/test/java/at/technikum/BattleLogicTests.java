package at.technikum;

import at.technikum.models.card.ACard;
import at.technikum.models.card.MonsterCard;
import at.technikum.models.card.SpellCard;
import at.technikum.enums.ECardName;
import at.technikum.enums.EElementType;
import at.technikum.server.BattleLogic;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BattleLogicTests {
    @Test
    void testGetDamage() {
        // Basic MonsterCard fight
        ACard card1 = new MonsterCard(
                1,
                "12345",
                ECardName.WaterGoblin.name,
                20,
                EElementType.WATER,
                1
        );
        ACard card2 = new MonsterCard(
                1,
                "12345",
                ECardName.FireElf.name,
                30,
                EElementType.FIRE,
                1
        );
        int expectedDamageCard1 = 20;
        int expectedDamageCard2 = 30;
        assertEquals(expectedDamageCard1, BattleLogic.getDamage(card1, card2));
        assertEquals(expectedDamageCard2, BattleLogic.getDamage(card2, card1));

        // MonsterCard fight with specialities
        ACard card3 = new MonsterCard(
                1,
                "12345",
                ECardName.WaterGoblin.name,
                20,
                EElementType.WATER,
                1
        );
        ACard card4 = new MonsterCard(
                1,
                "12345",
                ECardName.Dragon.name,
                30,
                EElementType.FIRE,
                1
        );
        int expectedDamageCard3 = 0;
        int expectedDamageCard4 = 30;
        assertEquals(expectedDamageCard3, BattleLogic.getDamage(card3, card4));
        assertEquals(expectedDamageCard4, BattleLogic.getDamage(card4, card3));

        // Spell fight
        // WaterGoblin damage should get halved
        // Knight damage should get doubled
        ACard card5 = new SpellCard(
                1,
                "12345",
                ECardName.Knight.name,
                20,
                EElementType.NORMAL,
                1
        );
        expectedDamageCard3 = 10;
        int expectedDamageCard5 = 40;
        assertEquals(expectedDamageCard3, BattleLogic.getDamage(card3, card5));
        assertEquals(expectedDamageCard5, BattleLogic.getDamage(card5, card3));
    }
}
