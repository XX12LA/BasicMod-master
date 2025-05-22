package dbdmod.cards.power;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import dbdmod.cards.BaseCard;
import dbdmod.character.MyCharacter;
import dbdmod.powers.BestForLastPower;
import dbdmod.util.CardStats;

public class BestForLast extends BaseCard {
    public static final String ID = makeID(BestForLast.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            2
    );

    public BestForLast() {
        super(ID, info);

        setCostUpgrade(1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new BestForLastPower(p, 0)));
    }
}
