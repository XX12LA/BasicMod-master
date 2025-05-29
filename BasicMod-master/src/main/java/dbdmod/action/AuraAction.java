package dbdmod.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;

public class AuraAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("BetterToHandAction");
    public static final String ui1 = uiStrings.TEXT[1];
    public static final String ui2 = uiStrings.TEXT[2];
    private final int vC;
    private final int dC;

    public AuraAction(int visibleCards, int drawCards) {
        this.vC = visibleCards;
        this.dC = drawCards;
    }

    @Override
    public void update() {
        ArrayList<AbstractCard> grp = new ArrayList<>();
        AbstractPlayer p = AbstractDungeon.player;
        if (!p.drawPile.isEmpty()) {
            int last = p.drawPile.size()-1;
            for (int i = last; i > last-vC; i--) {
                if (i<0) break;
                grp.add(p.drawPile.group.get(i));
            }
            if (dC >= vC) {
                for (AbstractCard c : grp) {
                    p.drawPile.removeCard(c);
                    p.hand.addToHand(c);
                }
            } else {
                addToTop(new BetterSelectCardsAction(grp, dC, ui1+dC+ui2, cards -> {
                    for (AbstractCard c : cards) {
                        p.drawPile.removeCard(c);
                        p.hand.addToHand(c);
                    }
                }));
            }
        }
        isDone = true;
    }
}
