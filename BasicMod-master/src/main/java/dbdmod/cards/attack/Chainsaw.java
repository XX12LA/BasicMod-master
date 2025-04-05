package dbdmod.cards.attack;

import com.brashmonkey.spriter.Player;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import dbdmod.cards.BaseCard;
import dbdmod.character.MyCharacter;
import dbdmod.util.CardStats;

public class Chainsaw extends BaseCard {
    public static final String ID = makeID(Chainsaw.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public Chainsaw() {
        super(ID, info);

        setDamage(6, 2);
    }

    public int getMult() {
        AbstractPlayer p = AbstractDungeon.player;
        int mult = 0;
        for (float i = 0.9f; i < 1; i -= 0.18f) {
            if (p.currentHealth <= p.maxHealth * i) {
                mult += 2;
            } else {
                break;
            }
        }
        if (mult == 0) {
            mult = 1;
        }
        return mult;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int mult = getMult();
        addToBot(new DamageAction(m, new DamageInfo(p, damage * mult, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        int mult = getMult();

        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[0] + mult + cardStrings.EXTENDED_DESCRIPTION[1];
        this.initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }
}
