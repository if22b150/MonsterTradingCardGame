package at.technikum;

import at.technikum.models.User;
import at.technikum.models.card.ACard;
import at.technikum.utils.enums.ECardType;

import java.util.Objects;

public class BattleLogic {
    public static Integer getRoundWinner(ACard card1, ACard card2) {
        int damage1 = getDamage(card1, card2);
        int damage2 = getDamage(card2, card1);
        if(damage1 > damage2) {
            // card1 won
            return card1.getUserId();
        } else if (damage1 == damage2) {
            return null;
        } else
            return card2.getUserId();
    }

    public static int getDamage(ACard card1, ACard card2) {
        // specialties
        if((Objects.equals(card1.getName(), CardName.WaterGoblin.name) && Objects.equals(card2.getName(), CardName.Dragon.name))
        || (Objects.equals(card1.getName(), CardName.Ork.name) && Objects.equals(card2.getName(), CardName.Wizard.name))
        || (Objects.equals(card1.getName(), CardName.Knight.name) && Objects.equals(card2.getName(), CardName.WaterSpell.name))
        || (Objects.equals(card1.getType(), ECardType.SPELL) && Objects.equals(card2.getName(), CardName.Kraken.name))
        || (Objects.equals(card1.getName(), CardName.Dragon.name) && Objects.equals(card2.getName(), CardName.FireElf.name)))
            return 0;

        return 1;
//        if(card1.getType() == ECardType.MONSTER && card2.getType() == ECardType.MONSTER) {
//
//        } else {
//
//        }
    }
}
