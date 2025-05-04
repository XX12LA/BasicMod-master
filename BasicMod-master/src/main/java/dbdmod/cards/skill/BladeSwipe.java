package dbdmod.cards.skill;

import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BrutalityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import dbdmod.cards.BaseCard;
import dbdmod.cards.attack.ChargedSlash;
import dbdmod.character.MyCharacter;
import dbdmod.util.CardStats;

@NoCompendium
public class BladeSwipe extends BaseCard {
    public static final String ID = makeID(BladeSwipe.class.getSimpleName());
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.SPECIAL, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.NONE, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public BladeSwipe() {
        super(ID, info); //Pass the required information to the BaseCard constructor.

        this.exhaust = true;
        this.selfRetain = true;
        setCostUpgrade(0);
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        int brutalAmount = AbstractDungeon.player.getPower(BrutalityPower.POWER_ID).amount;
        if (brutalAmount >= 1) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ChargedSlash card = new ChargedSlash();
        if (this.upgraded) {
            card.upgrade();
        }
        addToBot(new MakeTempCardInHandAction(card, 1));

        int brutalAmount = p.getPower(BrutalityPower.POWER_ID).amount;
        if (brutalAmount >= 1) {
            addToBot(new ApplyPowerAction(p, p, new BrutalityPower(p, -1)));
            addToBot(new GainEnergyAction(1));
        }
    }
}