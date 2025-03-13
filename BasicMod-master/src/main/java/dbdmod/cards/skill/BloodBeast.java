package dbdmod.cards.skill;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import dbdmod.cards.BaseCard;
import dbdmod.character.MyCharacter;
import dbdmod.powers.Brutal;
import dbdmod.util.CardStats;

public class BloodBeast extends BaseCard {
    public static final String ID = makeID(BloodBeast.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            0
    );

    public BloodBeast() {
        super(ID, info);

        setMagic(2, 1);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new Brutal(p, this.magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new BloodBeast();
    }
}