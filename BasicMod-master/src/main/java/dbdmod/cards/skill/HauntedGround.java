package dbdmod.cards.skill;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import dbdmod.CustomTags;
import dbdmod.cards.BaseCard;
import dbdmod.character.MyCharacter;
import dbdmod.powers.DangerPower;
import dbdmod.util.CardStats;

public class HauntedGround extends BaseCard {
    public static final String ID = makeID(HauntedGround.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.NONE, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            3 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public HauntedGround() {
        super(ID, info);

        setMagic(1, 1);
        tags.add(CustomTags.HEX);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            addToBot(new ApplyPowerAction(mo, p, new DangerPower(mo, magicNumber)));
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return isInAutoplay;
    }

    @Override
    public void tookDamage() {
        CardGroup hand = AbstractDungeon.player.hand;
        if (hand.contains(this)) {
            setCostForTurn(costForTurn - 1);
            if (costForTurn == 0) {
                addToTop(new SFXAction(makeID("Exposed")));
                addToTop(new NewQueueCardAction(this, null, true, true));
            }
        }
    }
}
