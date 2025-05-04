package dbdmod.cards.skill;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import dbdmod.CustomTags;
import dbdmod.cards.BaseCard;
import dbdmod.character.MyCharacter;
import dbdmod.util.CardStats;

public class DevourHope extends BaseCard {
    public static final String ID = makeID(DevourHope.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public DevourHope() {
        super(ID, info);

        tags.add(CustomTags.HEX);
        setMagic(16, 4);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        p.decreaseMaxHealth(2);
        addToBot(new AddTemporaryHPAction(p, p, magicNumber));
    }
}
