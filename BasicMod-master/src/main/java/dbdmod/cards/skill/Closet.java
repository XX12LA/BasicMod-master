package dbdmod.cards.skill;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import dbdmod.action.BetterSelectCardsAction;
import dbdmod.cards.BaseCard;
import dbdmod.cards.attack.Blade;
import dbdmod.cards.attack.Hatchet;
import dbdmod.character.MyCharacter;
import dbdmod.util.CardStats;

import java.util.ArrayList;

public class Closet extends BaseCard {
    public static final String ID = makeID(Closet.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.NONE, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public Closet() {
        super(ID, info);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard hatchet = new Hatchet();
        AbstractCard blade = new Blade();
        if (this.upgraded) {
            hatchet.upgrade();
            blade.upgrade();
        }
        ArrayList<AbstractCard> choices = new ArrayList<>();
        choices.add(hatchet);
        choices.add(blade);
        addToTop(new SFXAction(makeID("OpenCloset")));
        addToBot(new BetterSelectCardsAction(choices, 1, cardStrings.EXTENDED_DESCRIPTION[0], cards -> {
            addToBot(new MakeTempCardInHandAction(cards.get(0), 1));
        }));
    }
}
