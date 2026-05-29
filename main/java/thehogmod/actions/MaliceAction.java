package thehogmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.HashSet;

public class MaliceAction extends AbstractGameAction {
    private final boolean upgraded;

    public MaliceAction(AbstractCreature target, boolean upgraded) {
        this.actionType = ActionType.DRAW;
        this.target = target;
        this.upgraded = upgraded;
    }

    public void update() {
        HashSet<String> debuffSet = new HashSet<>();

        for (AbstractPower power : target.powers) {
            if (power.type == AbstractPower.PowerType.DEBUFF && !power.ID.equals("Shackled")) {
                debuffSet.add(power.ID);
            }
        }

        if (this.upgraded) {
            addToBot(new DrawCardAction(debuffSet.size() + 1));
        } else {
            if (!debuffSet.isEmpty()) {
                addToBot(new DrawCardAction(debuffSet.size()));
            }
        }

        this.isDone = true;
    }
}